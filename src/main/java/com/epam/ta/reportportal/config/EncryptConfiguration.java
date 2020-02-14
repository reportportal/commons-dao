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

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.util.text.BasicTextEncryptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

/**
 * Encrypt beans configuration for password values
 *
 * @author Andrei_Ramanchuk
 */
@Configuration
@DependsOn()
public class EncryptConfiguration {

	@Value("${rp.encryptor.secret}")
	private String secret;

	@Bean(name = "basicEncryptor")
	public BasicTextEncryptor getBasicEncrypt() {
		BasicTextEncryptor basic = new BasicTextEncryptor();
		basic.setPassword(secret);
		return basic;
	}

	@Bean(name = "strongEncryptor")
	public StandardPBEStringEncryptor getStrongEncryptor() {
		StandardPBEStringEncryptor strong = new StandardPBEStringEncryptor();
		strong.setPassword(secret);
		strong.setAlgorithm("PBEWithMD5AndTripleDES");
		return strong;
	}
}