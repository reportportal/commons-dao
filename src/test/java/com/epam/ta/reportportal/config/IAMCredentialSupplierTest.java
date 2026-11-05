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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.Instant;
import org.jclouds.aws.domain.SessionCredentials;
import org.jclouds.domain.Credentials;
import org.junit.jupiter.api.Test;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.AwsSessionCredentials;

class IAMCredentialSupplierTest {

  @Test
  void resolvesSessionCredentialsOnEveryCall() {
    AwsCredentialsProvider provider = mock(AwsCredentialsProvider.class);
    when(provider.resolveCredentials()).thenReturn(
        sessionCredentials("AKIATEST00000000001", "secret-one", "token-one"),
        sessionCredentials("AKIATEST00000000002", "secret-two", "token-two"));

    IAMCredentialSupplier supplier = new IAMCredentialSupplier(provider);

    Credentials first = supplier.get();
    Credentials second = supplier.get();

    assertSessionCredentials(first, "AKIATEST00000000001", "secret-one", "token-one");
    assertSessionCredentials(second, "AKIATEST00000000002", "secret-two", "token-two");
    verify(provider, times(2)).resolveCredentials();
  }

  @Test
  void resolvesBasicCredentialsWithoutSessionToken() {
    AwsCredentialsProvider provider = mock(AwsCredentialsProvider.class);
    when(provider.resolveCredentials())
        .thenReturn(AwsBasicCredentials.create("AKIATEST00000000003", "secret-three"));

    Credentials credentials = new IAMCredentialSupplier(provider).get();

    assertFalse(credentials instanceof SessionCredentials);
    assertEquals("AKIATEST00000000003", credentials.identity);
    assertEquals("secret-three", credentials.credential);
  }

  private static AwsSessionCredentials sessionCredentials(String accessKeyId, String secretAccessKey,
      String sessionToken) {
    return AwsSessionCredentials.builder()
        .accessKeyId(accessKeyId)
        .secretAccessKey(secretAccessKey)
        .sessionToken(sessionToken)
        .expirationTime(Instant.parse("2026-11-05T16:00:00Z"))
        .build();
  }

  private static void assertSessionCredentials(Credentials credentials, String accessKeyId,
      String secretAccessKey, String sessionToken) {
    SessionCredentials sessionCredentials = assertInstanceOf(SessionCredentials.class, credentials);
    assertEquals(accessKeyId, sessionCredentials.identity);
    assertEquals(secretAccessKey, sessionCredentials.credential);
    assertEquals(sessionToken, sessionCredentials.getSessionToken());
  }

}
