package com.epam.ta.reportportal.entity;

/**
 * @author Ivan Budaev
 */
public enum  ServerSettingsEnum {

	ENABLED("server.enabled"),
	HOST("server.host"),
	PORT("server.port"),
	PROTOCOL("server.protocol"),
	AUTH_ENABLED("server.auth_enabled"),
	START_TLS_ENABLED("server.star_tls_enabled"),
	SSL_ENABLED("server.ssl_enabled"),
	USERNAME("server.username"),
	PASSWORD("server.password"),
	FROM("server.from");

	private String attribute;

	ServerSettingsEnum(String attribute) {
		this.attribute = attribute;
	}

	public String getAttribute() {
		return attribute;
	}
}
