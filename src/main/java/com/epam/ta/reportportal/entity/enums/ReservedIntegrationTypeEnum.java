package com.epam.ta.reportportal.entity.enums;

import java.util.Arrays;
import java.util.Optional;

/**
 * @author <a href="mailto:ihar_kahadouski@epam.com">Ihar Kahadouski</a>
 */
public enum ReservedIntegrationTypeEnum {

	EMAIL("email"),
	AD("ad"),
	LDAP("ldap"),
	SAML("saml");

	private String name;

	ReservedIntegrationTypeEnum(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public static Optional<ReservedIntegrationTypeEnum> fromName(String name) {
		return Arrays.stream(values()).filter(it -> it.getName().equalsIgnoreCase(name)).findAny();
	}
}
