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

import com.epam.ta.reportportal.entity.enums.FeatureFlag;
import com.epam.ta.reportportal.exception.ReportPortalException;
import com.epam.ta.reportportal.filesystem.DataStore;
import com.epam.ta.reportportal.util.FeatureFlagHandler;
import com.epam.ta.reportportal.ws.model.ErrorType;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.jclouds.blobstore.BlobStore;
import org.jclouds.blobstore.domain.Blob;
import org.jclouds.domain.Location;
import org.jclouds.domain.LocationBuilder;
import org.jclouds.domain.LocationScope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implimitation of basic operations with blob storages.
 *
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

  private final FeatureFlagHandler featureFlagHandler;

  /**
   * Initialises {@link S3DataStore}.
   *
   * @param blobStore          {@link BlobStore}
   * @param bucketPrefix       Prefix for bucket name
   * @param bucketPostfix      Postfix for bucket name
   * @param defaultBucketName  Name of default bucket to use
   * @param region             Region to use
   * @param featureFlagHandler {@link FeatureFlagHandler}
   */
  public S3DataStore(BlobStore blobStore, String bucketPrefix, String bucketPostfix,
      String defaultBucketName, String region, FeatureFlagHandler featureFlagHandler) {
    this.blobStore = blobStore;
    this.bucketPrefix = bucketPrefix;
    this.bucketPostfix = Objects.requireNonNullElse(bucketPostfix, "");
    this.defaultBucketName = defaultBucketName;
    this.location = getLocationFromString(region);
    this.featureFlagHandler = featureFlagHandler;
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

      Blob objectBlob = blobStore.blobBuilder(s3File.getFilePath()).payload(inputStream)
          .contentDisposition(s3File.getFilePath()).contentLength(inputStream.available()).build();
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
      Blob fileBlob = blobStore.getBlob(s3File.getBucket(), s3File.getFilePath());
      if (fileBlob != null) {
        return fileBlob.getPayload().openStream();
      } else {
        throw new Exception();
      }
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

  @Override
  public void deleteAll(List<String> filePaths, String bucketName) {
    if (!featureFlagHandler.isEnabled(FeatureFlag.SINGLE_BUCKET)) {
      blobStore.removeBlobs(bucketPrefix + bucketName + bucketPostfix, filePaths);
    } else {
      blobStore.removeBlobs(bucketName, filePaths);
    }
  }

  @Override
  public void deleteContainer(String bucketName) {
    if (!featureFlagHandler.isEnabled(FeatureFlag.SINGLE_BUCKET)) {
      blobStore.deleteContainer(bucketPrefix + bucketName + bucketPostfix);
    } else {
      blobStore.deleteContainer(bucketName);
    }
  }

  private S3File getS3File(String filePath) {
    if (featureFlagHandler.isEnabled(FeatureFlag.SINGLE_BUCKET)) {
      return new S3File(defaultBucketName, filePath);
    }
    Path targetPath = Paths.get(filePath);
    int nameCount = targetPath.getNameCount();
    String bucketName;
    if (nameCount > 1) {
      bucketName = bucketPrefix + retrievePath(targetPath, 0, 1) + bucketPostfix;
      return new S3File(bucketName, retrievePath(targetPath, 1, nameCount));
    } else {
      bucketName = bucketPrefix + defaultBucketName + bucketPostfix;
      return new S3File(bucketName, retrievePath(targetPath, 0, 1));
    }
  }

  private Location getLocationFromString(String locationString) {
    Location location = null;
    if (locationString != null) {
      location =
          new LocationBuilder().scope(LocationScope.REGION).id(locationString).description("region")
              .build();
    }
    return location;
  }

  private String retrievePath(Path path, int beginIndex, int endIndex) {
    return String.valueOf(path.subpath(beginIndex, endIndex));
  }
}