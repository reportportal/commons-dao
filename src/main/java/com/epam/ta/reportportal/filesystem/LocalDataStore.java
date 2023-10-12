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

package com.epam.ta.reportportal.filesystem;

import com.epam.ta.reportportal.exception.ReportPortalException;
import com.epam.ta.reportportal.ws.model.ErrorType;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Dzianis_Shybeka
 */
public class LocalDataStore implements DataStore {

  private static final Logger logger = LoggerFactory.getLogger(LocalDataStore.class);

  private final String storageRootPath;

  public LocalDataStore(String storageRootPath) {
    this.storageRootPath = storageRootPath;
  }

  @Override
  public String save(String filePath, InputStream inputStream) {

    try {

      Path targetPath = Paths.get(storageRootPath, filePath);
      Path targetDirectory = targetPath.getParent();

      if (Objects.nonNull(targetDirectory) && !Files.isDirectory(targetDirectory)) {
        Files.createDirectories(targetDirectory);
      }

      logger.debug("Saving to: {} ", targetPath.toAbsolutePath());

      Files.copy(inputStream, targetPath, StandardCopyOption.REPLACE_EXISTING);

      return filePath;
    } catch (IOException e) {

      logger.error("Unable to save log file '{}'", filePath, e);

      throw new ReportPortalException(ErrorType.INCORRECT_REQUEST, "Unable to save log file");
    }
  }

  @Override
  public InputStream load(String filePath) {

    try {

      return Files.newInputStream(Paths.get(storageRootPath, filePath));
    } catch (IOException e) {

      logger.error("Unable to find file '{}'", filePath, e);

      throw new ReportPortalException(ErrorType.UNABLE_TO_LOAD_BINARY_DATA, "Unable to find file");
    }
  }

  @Override
  public boolean exists(String filePath) {
    return Files.exists(Paths.get(storageRootPath, filePath));
  }

  @Override
  public void delete(String filePath) {

    try {

      Files.deleteIfExists(Paths.get(storageRootPath, filePath));
    } catch (IOException e) {

      logger.error("Unable to delete file '{}'", filePath, e);

      throw new ReportPortalException(ErrorType.INCORRECT_REQUEST, "Unable to delete file");
    }
  }

  @Override
  public void deleteAll(List<String> filePaths, String bucketName) {
    for (String filePath : filePaths) {
      delete(filePath);
    }
  }

  @Override
  public void deleteContainer(String bucketName) {
    try {
      Files.deleteIfExists(Paths.get(bucketName));
    } catch (IOException e) {

      logger.error("Unable to delete bucket '{}'", bucketName, e);

      throw new ReportPortalException(ErrorType.INCORRECT_REQUEST, "Unable to delete file");
    }
  }
}
