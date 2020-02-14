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

import com.epam.ta.reportportal.filesystem.DataStore;
import org.apache.commons.io.IOUtils;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.util.text.BasicTextEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Encrypt beans configuration for password values
 *
 * @author Andrei_Ramanchuk
 */
@Configuration
public class EncryptConfiguration {

	private DataStore dataStore;

	@Autowired
	public EncryptConfiguration(DataStore dataStore) {
		this.dataStore = dataStore;
	}

	@Bean(name = "basicEncryptor")
	public BasicTextEncryptor getBasicEncrypt() throws IOException {
		BasicTextEncryptor basic = new BasicTextEncryptor();
		basic.setPassword(IOUtils.toString(dataStore.load(DataStore.SECRET_INTEGRATION_SALT), StandardCharsets.UTF_8));
		return basic;
	}

	@Bean(name = "strongEncryptor")
	public StandardPBEStringEncryptor getStrongEncryptor() throws IOException {
		StandardPBEStringEncryptor strong = new StandardPBEStringEncryptor();
		strong.setPassword(IOUtils.toString(dataStore.load(DataStore.SECRET_INTEGRATION_SALT), StandardCharsets.UTF_8));
		strong.setAlgorithm("PBEWithMD5AndTripleDES");
		return strong;
	}
}