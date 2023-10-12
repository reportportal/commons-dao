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

package com.epam.ta.reportportal.config;

import static com.epam.ta.reportportal.binary.impl.DataStoreUtils.INTEGRATION_SECRETS_PATH;

import com.epam.ta.reportportal.entity.enums.FeatureFlag;
import com.epam.ta.reportportal.exception.ReportPortalException;
import com.epam.ta.reportportal.filesystem.DataStore;
import com.epam.ta.reportportal.util.FeatureFlagHandler;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Optional;
import org.apache.commons.io.IOUtils;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.util.text.BasicTextEncryptor;
import org.jooq.tools.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Encrypt beans configuration for password values
 *
 * @author Andrei_Ramanchuk
 */
@Configuration
public class EncryptConfiguration implements InitializingBean {

  private static final Logger LOGGER = LoggerFactory.getLogger(EncryptConfiguration.class);

  @Value("${rp.encryptor.password}")
  private String password;

  @Value("${rp.integration.salt.path:keystore}")
  private String integrationSaltPath;

  @Value("${rp.integration.salt.file:secret-integration-salt}")
  private String integrationSaltFile;

  private String secretFilePath;

  private final DataStore dataStore;

  private final FeatureFlagHandler featureFlagHandler;

  @Autowired
  public EncryptConfiguration(DataStore dataStore, FeatureFlagHandler featureFlagHandler) {
    this.dataStore = dataStore;
    this.featureFlagHandler = featureFlagHandler;
  }

  /**
   * Creates bean of {@link BasicTextEncryptor} for encrypting purposes.
   *
   * @return {@link BasicTextEncryptor} instance
   */
  @Bean(name = "basicEncryptor")
  public BasicTextEncryptor getBasicEncrypt() throws IOException {
    BasicTextEncryptor basic = new BasicTextEncryptor();
    basic.setPassword(getPassword());
    return basic;
  }

  /**
   * Creates bean of {@link StandardPBEStringEncryptor} for encrypting purposes.
   *
   * @return {@link StandardPBEStringEncryptor} instance
   */
  @Bean(name = "strongEncryptor")
  public StandardPBEStringEncryptor getStrongEncryptor() throws IOException {
    StandardPBEStringEncryptor strong = new StandardPBEStringEncryptor();
    strong.setPassword(getPassword());
    strong.setAlgorithm("PBEWithMD5AndTripleDES");
    return strong;
  }

  @Override
  public void afterPropertiesSet() {
    if (featureFlagHandler.isEnabled(FeatureFlag.SINGLE_BUCKET)) {
      secretFilePath = Paths.get(INTEGRATION_SECRETS_PATH, integrationSaltFile).toString();
    } else {
      secretFilePath = integrationSaltPath + File.separator + integrationSaltFile;
    }
    if (password == null) {
      loadOrGenerateEncryptorPassword();
    }
  }

  private String getPassword() throws IOException {
    return Optional.ofNullable(password)
        .orElse(IOUtils.toString(dataStore.load(secretFilePath), StandardCharsets.UTF_8));
  }

  private void loadOrGenerateEncryptorPassword() {
    try {
      dataStore.load(secretFilePath);
    } catch (ReportPortalException ex) {
      byte[] bytes = new byte[20];
      new SecureRandom().nextBytes(bytes);
      try (InputStream secret = new ByteArrayInputStream(
          Base64.getUrlEncoder().withoutPadding().encode(bytes))) {
        dataStore.save(secretFilePath, secret);
      } catch (IOException ioEx) {
        LOGGER.error("Unable to generate secret file", ioEx);
      }
    }
  }
}