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

import com.epam.reportportal.rules.exception.ErrorType;
import com.epam.reportportal.rules.exception.ReportPortalException;
import com.epam.ta.reportportal.entity.enums.FeatureFlag;
import com.epam.ta.reportportal.filesystem.DataStore;
import com.epam.ta.reportportal.util.FeatureFlagHandler;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.core.sync.ResponseTransformer;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.CreateBucketRequest;
import software.amazon.awssdk.services.s3.model.Delete;
import software.amazon.awssdk.services.s3.model.DeleteBucketRequest;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.DeleteObjectsRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.HeadBucketRequest;
import software.amazon.awssdk.services.s3.model.HeadObjectRequest;
import software.amazon.awssdk.services.s3.model.NoSuchBucketException;
import software.amazon.awssdk.services.s3.model.NoSuchKeyException;
import software.amazon.awssdk.services.s3.model.ObjectIdentifier;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

/**
 * Implementation of basic operations with blob storages using AWS SDK v2.
 *
 * @author <a href="mailto:ivan_budayeu@epam.com">Ivan Budayeu</a>
 */
public class S3DataStore implements DataStore {

  private static final Logger LOGGER = LoggerFactory.getLogger(S3DataStore.class);
  private static final Lock CREATE_BUCKET_LOCK = new ReentrantLock();

  private final S3Client s3Client;
  private final String bucketPrefix;
  private final String bucketPostfix;
  private final String defaultBucketName;
  private final FeatureFlagHandler featureFlagHandler;

  /**
   * Initialises {@link S3DataStore}.
   *
   * @param s3Client           {@link S3Client}
   * @param bucketPrefix       Prefix for bucket name
   * @param bucketPostfix      Postfix for bucket name
   * @param defaultBucketName  Name of default bucket to use
   * @param featureFlagHandler {@link FeatureFlagHandler}
   */
  public S3DataStore(S3Client s3Client, String bucketPrefix, String bucketPostfix,
      String defaultBucketName, FeatureFlagHandler featureFlagHandler) {
    this.s3Client = s3Client;
    this.bucketPrefix = bucketPrefix;
    this.bucketPostfix = Objects.requireNonNullElse(bucketPostfix, "");
    this.defaultBucketName = defaultBucketName;
    this.featureFlagHandler = featureFlagHandler;
  }

  @Override
  public String save(String filePath, InputStream inputStream) {
    if (filePath == null) {
      return "";
    }
    StoredFile storedFile = getStoredFile(filePath);
    try {
      ensureBucketExists(storedFile.bucket());

      PutObjectRequest putObjectRequest = PutObjectRequest.builder()
          .bucket(storedFile.bucket())
          .key(storedFile.filePath())
          .build();

      s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(inputStream, inputStream.available()));
      return Paths.get(filePath).toString();
    } catch (IOException e) {
      LOGGER.error("Unable to save file '{}'", filePath, e);
      throw new ReportPortalException(ErrorType.INCORRECT_REQUEST, "Unable to save file");
    } catch (S3Exception e) {
      LOGGER.error("Unable to save file '{}' to S3", filePath, e);
      throw new ReportPortalException(ErrorType.INCORRECT_REQUEST, "Unable to save file to S3");
    }
  }

  @Override
  public InputStream load(String filePath) {
    if (filePath == null) {
      LOGGER.error("Unable to find file");
      throw new ReportPortalException(ErrorType.UNABLE_TO_LOAD_BINARY_DATA, "Unable to find file");
    }
    StoredFile storedFile = getStoredFile(filePath);
    try {
      GetObjectRequest getObjectRequest = GetObjectRequest.builder()
          .bucket(storedFile.bucket())
          .key(storedFile.filePath())
          .build();
      return s3Client.getObject(getObjectRequest, ResponseTransformer.toInputStream());
    } catch (NoSuchKeyException e) {
      LOGGER.error("Unable to find file '{}'", filePath);
      throw new ReportPortalException(ErrorType.UNABLE_TO_LOAD_BINARY_DATA, "Unable to find file");
    } catch (S3Exception e) {
      LOGGER.error("Unable to load file '{}' from S3", filePath, e);
      throw new ReportPortalException(ErrorType.UNABLE_TO_LOAD_BINARY_DATA, e.getMessage());
    }
  }

  @Override
  public boolean exists(String filePath) {
    if (filePath == null) {
      return false;
    }
    StoredFile storedFile = getStoredFile(filePath);
    try {
      HeadObjectRequest headObjectRequest = HeadObjectRequest.builder()
          .bucket(storedFile.bucket())
          .key(storedFile.filePath())
          .build();
      s3Client.headObject(headObjectRequest);
      return true;
    } catch (NoSuchKeyException e) {
      return false;
    } catch (S3Exception e) {
      if (e.statusCode() == 404) {
        return false;
      }
      LOGGER.error("Unable to check existence of file '{}'", filePath, e);
      throw new ReportPortalException(ErrorType.INCORRECT_REQUEST, "Unable to check existence of file");
    }
  }

  @Override
  public void delete(String filePath) {
    if (filePath == null) {
      return;
    }
    StoredFile storedFile = getStoredFile(filePath);
    try {
      DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
          .bucket(storedFile.bucket())
          .key(storedFile.filePath())
          .build();
      s3Client.deleteObject(deleteObjectRequest);
    } catch (S3Exception e) {
      LOGGER.error("Unable to delete file '{}'", filePath, e);
      throw new ReportPortalException(ErrorType.INCORRECT_REQUEST, "Unable to delete file");
    }
  }

  @Override
  public void deleteAll(List<String> filePaths, String bucketName) {
    String resolvedBucketName;
    if (!featureFlagHandler.isEnabled(FeatureFlag.SINGLE_BUCKET)) {
      resolvedBucketName = bucketPrefix + bucketName + bucketPostfix;
    } else {
      resolvedBucketName = bucketName;
    }

    try {
      // AWS S3 deleteObjects limit is 1000
      for (List<String> batch : com.google.common.collect.Lists.partition(filePaths, 1000)) {
        List<ObjectIdentifier> keys = new ArrayList<>();
        for (String filePath : batch) {
          keys.add(ObjectIdentifier.builder().key(filePath).build());
        }

        if (!keys.isEmpty()) {
          DeleteObjectsRequest deleteObjectsRequest = DeleteObjectsRequest.builder()
              .bucket(resolvedBucketName)
              .delete(Delete.builder().objects(keys).build())
              .build();
          s3Client.deleteObjects(deleteObjectsRequest);
        }
      }
    } catch (S3Exception e) {
      LOGGER.error("Unable to delete files in bucket '{}'", resolvedBucketName, e);
    }
  }

  @Override
  public void deleteContainer(String bucketName) {
    String resolvedBucketName;
    if (!featureFlagHandler.isEnabled(FeatureFlag.SINGLE_BUCKET)) {
      resolvedBucketName = bucketPrefix + bucketName + bucketPostfix;
    } else {
      resolvedBucketName = bucketName;
    }

    try {
      DeleteBucketRequest deleteBucketRequest = DeleteBucketRequest.builder()
          .bucket(resolvedBucketName)
          .build();
      s3Client.deleteBucket(deleteBucketRequest);
    } catch (S3Exception e) {
      LOGGER.error("Unable to delete bucket '{}'", resolvedBucketName, e);
    }
  }

  private StoredFile getStoredFile(String filePath) {
    if (featureFlagHandler.isEnabled(FeatureFlag.SINGLE_BUCKET)) {
      return new StoredFile(defaultBucketName, filePath);
    }
    Path targetPath = Paths.get(filePath);
    int nameCount = targetPath.getNameCount();
    String bucketName;
    if (nameCount > 1) {
      bucketName = bucketPrefix + retrievePath(targetPath, 0, 1) + bucketPostfix;
      return new StoredFile(bucketName, retrievePath(targetPath, 1, nameCount));
    } else {
      bucketName = defaultBucketName;
      return new StoredFile(bucketName, retrievePath(targetPath, 0, 1));
    }
  }

  private String retrievePath(Path path, int beginIndex, int endIndex) {
    return String.valueOf(path.subpath(beginIndex, endIndex));
  }

  private void ensureBucketExists(String bucketName) {
    try {
      HeadBucketRequest headBucketRequest = HeadBucketRequest.builder()
          .bucket(bucketName)
          .build();
      s3Client.headBucket(headBucketRequest);
    } catch (S3Exception e) {
      // If 404 or verify failed, try to create
      if (e instanceof NoSuchBucketException || e.statusCode() == 404) {
        CREATE_BUCKET_LOCK.lock();
        try {
          try {
            HeadBucketRequest headBucketRequest = HeadBucketRequest.builder()
                .bucket(bucketName)
                .build();
            s3Client.headBucket(headBucketRequest);
          } catch (S3Exception ex) {
            if (ex instanceof NoSuchBucketException || ex.statusCode() == 404) {
              CreateBucketRequest createBucketRequest = CreateBucketRequest.builder()
                  .bucket(bucketName)
                  .build();
              s3Client.createBucket(createBucketRequest);
            } else {
              throw ex;
            }
          }
        } finally {
          CREATE_BUCKET_LOCK.unlock();
        }
      } else {
        throw e;
      }
    }
  }
}
