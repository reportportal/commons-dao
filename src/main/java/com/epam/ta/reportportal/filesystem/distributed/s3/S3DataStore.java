/*
 * Copyright 2019 EPAM Systems
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.epam.ta.reportportal.filesystem.distributed.s3;

import com.epam.ta.reportportal.exception.ReportPortalException;
import com.epam.ta.reportportal.filesystem.DataStore;
import com.epam.ta.reportportal.ws.model.ErrorType;
import org.jclouds.blobstore.BlobStore;
import org.jclouds.blobstore.domain.Blob;
import org.jclouds.domain.Location;
import org.jclouds.domain.LocationBuilder;
import org.jclouds.domain.LocationScope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author <a href="mailto:ivan_budayeu@epam.com">Ivan Budayeu</a>
 */
public class S3DataStore implements DataStore {

	private static final Logger LOGGER = LoggerFactory.getLogger(S3DataStore.class);
	private static final Lock CREATE_BUCKET_LOCK = new ReentrantLock();

	private final BlobStore blobStore;
	private final String bucketPrefix;
	private final String bucketPostfix;
	private final String defaultBucketName;
	private final Location location;

	public S3DataStore(BlobStore blobStore, String bucketPrefix, String bucketPostfix, String defaultBucketName, String region) {
		this.blobStore = blobStore;
		this.bucketPrefix = bucketPrefix;
		this.bucketPostfix = bucketPostfix;
		this.defaultBucketName = defaultBucketName;
		this.location = getLocationFromString(region);
	}

	@Override
	public String save(String filePath, InputStream inputStream) {
		S3File s3File = getS3File(filePath);
		try {
			if (!blobStore.containerExists(s3File.getBucket())) {
				CREATE_BUCKET_LOCK.lock();
				try {
					if (!blobStore.containerExists(s3File.getBucket())) {
						blobStore.createContainerInLocation(location, s3File.getBucket());
					}
				} finally {
					CREATE_BUCKET_LOCK.unlock();
				}
			}

			Blob objectBlob = blobStore.blobBuilder(s3File.getFilePath())
					.payload(inputStream)
					.contentDisposition(s3File.getFilePath())
					.contentLength(inputStream.available())
					.build();
			blobStore.putBlob(s3File.getBucket(), objectBlob);
			return Paths.get(filePath).toString();
		} catch (IOException e) {
			LOGGER.error("Unable to save file '{}'", filePath, e);
			throw new ReportPortalException(ErrorType.INCORRECT_REQUEST, "Unable to save file");
		}
	}

	@Override
	public InputStream load(String filePath) {
		S3File s3File = getS3File(filePath);
		try {
			return blobStore.getBlob(s3File.getBucket(), s3File.getFilePath()).getPayload().openStream();
		} catch (Exception e) {
			LOGGER.error("Unable to find file '{}'", filePath, e);
			throw new ReportPortalException(ErrorType.UNABLE_TO_LOAD_BINARY_DATA, "Unable to find file");
		}
	}

	@Override
	public void delete(String filePath) {
		S3File s3File = getS3File(filePath);
		try {
			blobStore.removeBlob(s3File.getBucket(), s3File.getFilePath());
		} catch (Exception e) {
			LOGGER.error("Unable to delete file '{}'", filePath, e);
			throw new ReportPortalException(ErrorType.INCORRECT_REQUEST, "Unable to delete file");
		}
	}

	private S3File getS3File(String filePath) {
		Path targetPath = Paths.get(filePath);
		int nameCount = targetPath.getNameCount();
		if (nameCount > 1) {
			return new S3File(bucketPrefix + retrievePath(targetPath, 0, 1) + bucketPostfix, retrievePath(targetPath, 1, nameCount));
		} else {
			return new S3File(defaultBucketName, retrievePath(targetPath, 0, 1));
		}

	}

	private Location getLocationFromString(String locationString) {
		Location location = null;
		if (locationString != null) {
			location = new LocationBuilder()
					.scope(LocationScope.REGION)
					.id(locationString)
					.description("region")
					.build();
		}
		return location;
	}

	private String retrievePath(Path path, int beginIndex, int endIndex) {
		return String.valueOf(path.subpath(beginIndex, endIndex));
	}
}
