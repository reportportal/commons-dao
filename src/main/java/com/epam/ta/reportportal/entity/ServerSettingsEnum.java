package com.epam.ta.reportportal.entity;

import java.util.Arrays;
import java.util.Optional;

/**
 * @author Ivan Budaev
 */
public enum ServerSettingsEnum {

	ENABLED("server.email.enabled"),
	HOST("server.email.host"),
	PORT("server.email.port"),
	PROTOCOL("server.email.protocol"),
	AUTH_ENABLED("server.email.auth_enabled"),
	STAR_TLS_ENABLED("server.email.star_tls_enabled"),
	SSL_ENABLED("server.email.ssl_enabled"),
	USERNAME("server.email.username"),
	PASSWORD("server.email.password"),
	FROM("server.email.from"),
	ANALYTICS("server.analytics.all");

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
}
