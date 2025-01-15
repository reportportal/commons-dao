/*
 * Copyright 2023 EPAM Systems
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.epam.ta.reportportal.config;

import com.google.common.base.Supplier;
import org.jclouds.domain.Credentials;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.AwsSessionCredentials;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;

/**
 * @author <a href="mailto:andrei_piankouski@epam.com">Andrei Piankouski</a>
 */
public class IAMCredentialSupplier implements Supplier<AwsCredentials> {

  private static final Logger LOGGER =
      LoggerFactory.getLogger(IAMCredentialSupplier.class);

  @Override
  public AwsCredentials get() {
    DefaultCredentialsProvider defaultCredentialsProvider = DefaultCredentialsProvider.create();
    AwsCredentials awsCredentials = defaultCredentialsProvider.resolveCredentials();

    if (awsCredentials instanceof AwsBasicCredentials basicCredentials) {
      LOGGER.info("Access Key: {}", basicCredentials.accessKeyId());
      LOGGER.info("Secret Key: {}", basicCredentials.secretAccessKey());
      LOGGER.info("providerName: {}", basicCredentials.providerName());
      LOGGER.info("accountId: {}", basicCredentials.accountId());
    } else if (awsCredentials instanceof AwsSessionCredentials sessionCredentials) {
      LOGGER.info("Access Key: {}", sessionCredentials.accessKeyId());
      LOGGER.info("Secret Key: {}", sessionCredentials.secretAccessKey());
      LOGGER.info("providerName: {}", sessionCredentials.providerName());
      LOGGER.info("expirationTime: {}", sessionCredentials.expirationTime());
      LOGGER.info("accountId: {}", sessionCredentials.accountId());
      LOGGER.info("Session Token: {}", sessionCredentials.sessionToken());
    } else {
      LOGGER.warn("Unknown credentials type.");
    }

    return awsCredentials;
  }

}
