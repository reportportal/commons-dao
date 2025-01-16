/*
 * Copyright 2025 EPAM Systems
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
import java.time.Instant;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.jclouds.aws.domain.SessionCredentials;
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
public class IAMCredentialSupplier implements Supplier<Credentials> {

  private static final Logger LOGGER = LoggerFactory.getLogger(IAMCredentialSupplier.class);
  private volatile SessionCredentials cachedCredentials;
  private volatile Instant expirationTime;
  private final Lock lock = new ReentrantLock();

  @Override
  public Credentials get() {

    LOGGER.error("Try to get credentials");
    if (credentialsAreExpired()) {
      lock.lock();
      try {
        if (credentialsAreExpired()) {
          refreshCredentials();
        }
      } finally {
        lock.unlock();
      }
    }
    return cachedCredentials;
  }

  private boolean credentialsAreExpired() {
    return cachedCredentials == null || Instant.now().isAfter(expirationTime);
  }

  private void refreshCredentials() {
    LOGGER.error("refreshCredentials");
    AwsSessionCredentials awsCredentials = obtainAwsSessionCredentials();

    if (awsCredentials != null) {
      cachedCredentials = SessionCredentials.builder()
          .accessKeyId(awsCredentials.accessKeyId())
          .secretAccessKey(awsCredentials.secretAccessKey())
          .sessionToken(awsCredentials.sessionToken())
          .build();

      expirationTime = Instant.now().plusSeconds(3600);
    }
  }

  private AwsSessionCredentials obtainAwsSessionCredentials() {
    LOGGER.error("obtainAwsSessionCredentials");
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
    if (awsCredentials instanceof AwsSessionCredentials sessionCredentials) {
      return sessionCredentials;
    }
    return null;
  }

}
