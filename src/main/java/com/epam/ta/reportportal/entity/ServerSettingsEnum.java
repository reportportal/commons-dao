/*
 * Copyright (C) 2018 EPAM Systems
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

package com.epam.ta.reportportal.entity;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

import static com.epam.ta.reportportal.entity.ServerSettingsConstants.ANALYTICS_CONFIG_PREFIX;
import static com.epam.ta.reportportal.entity.ServerSettingsConstants.EMAIL_CONFIG_PREFIX;

/**
 * @author Ivan Budaev
 */
public enum ServerSettingsEnum {

	ENABLED(EMAIL_CONFIG_PREFIX + "enabled"),
	HOST(EMAIL_CONFIG_PREFIX + "host"),
	PORT(EMAIL_CONFIG_PREFIX + "port"),
	PROTOCOL(EMAIL_CONFIG_PREFIX + "protocol"),
	AUTH_ENABLED(EMAIL_CONFIG_PREFIX + "auth_enabled"),
	STAR_TLS_ENABLED(EMAIL_CONFIG_PREFIX + "star_tls_enabled"),
	SSL_ENABLED(EMAIL_CONFIG_PREFIX + "ssl_enabled"),
	USERNAME(EMAIL_CONFIG_PREFIX + "username"),
	PASSWORD(EMAIL_CONFIG_PREFIX + "password"),
	FROM(EMAIL_CONFIG_PREFIX + "from"),
	ANALYTICS(ANALYTICS_CONFIG_PREFIX + "all");

	private String attribute;

	ServerSettingsEnum(String attribute) {
		this.attribute = attribute;
	}

	public String getAttribute() {
		return attribute;
	}

	public Optional<String> getAttribute(Map<String, Object> params) {
		return Optional.ofNullable(params.get(this.attribute)).map(o -> (String) o);
	}

	public static Optional<ServerSettingsEnum> findByAttribute(String attribute) {
		return Optional.ofNullable(attribute)
				.flatMap(attr -> Arrays.stream(values()).filter(it -> it.attribute.equalsIgnoreCase(attr)).findAny());
	}

	public static boolean isPresent(String attribute) {
		return findByAttribute(attribute).isPresent();
	}
}
