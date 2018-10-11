package com.epam.ta.reportportal.entity;

import java.util.Arrays;
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

	public static Optional<ServerSettingsEnum> findByAttribute(String attribute) {
		return Optional.ofNullable(attribute)
				.flatMap(attr -> Arrays.stream(values()).filter(it -> it.attribute.equalsIgnoreCase(attr)).findAny());
	}

	public static boolean isPresent(String attribute) {
		return findByAttribute(attribute).isPresent();
	}
}
