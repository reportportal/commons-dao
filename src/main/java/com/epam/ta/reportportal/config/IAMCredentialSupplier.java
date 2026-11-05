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
import org.jclouds.aws.domain.SessionCredentials;
import org.jclouds.domain.Credentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.AwsSessionCredentials;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;

/**
 * Supplies S3 credentials from the AWS default credential chain (environment, web identity,
 * container credentials, or EC2 instance metadata) for deployments that run with empty
 * datastore access keys.
 *
 * <p>The supplier keeps no state of its own. The AWS SDK caches credentials and refreshes them
 * shortly before they expire, but only when {@code resolveCredentials()} is called. Caching the
 * result here as well holds the credential until its own expiry and never calls the SDK in
 * between, so the SDK never gets the chance to refresh and S3 calls fail with
 * {@code ExpiredToken} while instance metadata is already serving a valid credential. Resolving
 * on every call avoids that; the SDK serves the cached value, so no extra requests are made.
 *
 * @author <a href="mailto:andrei_piankouski@epam.com">Andrei Piankouski</a>
 */
public class IAMCredentialSupplier implements Supplier<Credentials> {

  private final AwsCredentialsProvider credentialsProvider;

  public IAMCredentialSupplier() {
    this(DefaultCredentialsProvider.create());
  }

  IAMCredentialSupplier(AwsCredentialsProvider credentialsProvider) {
    this.credentialsProvider = credentialsProvider;
  }

  @Override
  public Credentials get() {
    AwsCredentials awsCredentials = credentialsProvider.resolveCredentials();
    if (awsCredentials instanceof AwsSessionCredentials sessionCredentials) {
      return SessionCredentials.builder()
          .accessKeyId(sessionCredentials.accessKeyId())
          .secretAccessKey(sessionCredentials.secretAccessKey())
          .sessionToken(sessionCredentials.sessionToken())
          .build();
    }
    return new Credentials(awsCredentials.accessKeyId(), awsCredentials.secretAccessKey());
  }

}
