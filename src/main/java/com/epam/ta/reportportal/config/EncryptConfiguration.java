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

import com.epam.ta.reportportal.exception.ReportPortalException;
import com.epam.ta.reportportal.filesystem.DataStore;
import org.apache.commons.io.IOUtils;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.util.text.BasicTextEncryptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * Encrypt beans configuration for password values
 *
 * @author Andrei_Ramanchuk
 */
@Configuration
public class EncryptConfiguration implements InitializingBean {

	private static final Logger LOGGER = LoggerFactory.getLogger(EncryptConfiguration.class);

	@Value("${rp.integration.salt.path:keystore}")
	private String integrationSaltPath;

	@Value("${rp.integration.salt.file:secret-integration-salt}")
	private String integrationSaltFile;

	@Value("${rp.integration.salt.migration:migration}")
	private String migrationFile;

	private String secretFilePath;
	private String migrationFilePath;

	private DataStore dataStore;

	@Autowired
	public EncryptConfiguration(DataStore dataStore) {
		this.dataStore = dataStore;
	}

	@Bean(name = "basicEncryptor")
	public BasicTextEncryptor getBasicEncrypt() throws IOException {
		BasicTextEncryptor basic = new BasicTextEncryptor();
		basic.setPassword(IOUtils.toString(dataStore.load(secretFilePath), StandardCharsets.UTF_8));
		return basic;
	}

	@Bean(name = "strongEncryptor")
	public StandardPBEStringEncryptor getStrongEncryptor() throws IOException {
		StandardPBEStringEncryptor strong = new StandardPBEStringEncryptor();
		strong.setPassword(IOUtils.toString(dataStore.load(secretFilePath), StandardCharsets.UTF_8));
		strong.setAlgorithm("PBEWithMD5AndTripleDES");
		return strong;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		secretFilePath = integrationSaltPath + File.separator + integrationSaltFile;
		migrationFilePath = integrationSaltPath + File.separator + migrationFile;
		loadOrGenerateIntegrationSalt(dataStore);
	}

	private void loadOrGenerateIntegrationSalt(DataStore dataStore) {
		try {
			dataStore.load(secretFilePath);
		} catch (ReportPortalException ex) {
			byte[] bytes = new byte[20];
			new SecureRandom().nextBytes(bytes);
			try (InputStream secret = new ByteArrayInputStream(Base64.getUrlEncoder().withoutPadding().encode(bytes));
					InputStream empty = new ByteArrayInputStream(new byte[1])) {
				dataStore.save(secretFilePath, secret);
				dataStore.save(migrationFilePath, empty);
			} catch (IOException ioEx) {
				LOGGER.error("Unable to generate secret file", ioEx);
			}
		}
	}
}