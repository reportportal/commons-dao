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

package com.epam.ta.reportportal.entity.enums;

import com.epam.ta.reportportal.exception.ReportPortalException;

import java.util.Arrays;

import static com.epam.ta.reportportal.ws.model.ErrorType.INCORRECT_AUTHENTICATION_TYPE;

/**
 * Authentication mechanics enum for external system
 *
 * @author Andrei_Ramanchuk
 */
public enum AuthType {

	//@formatter:off
	OAUTH(false),
	NTLM(true),
	APIKEY(true),
	BASIC(true);
	//@formatter:on

	final boolean requiresPassword;

	AuthType(boolean requiresPassword) {
		this.requiresPassword = requiresPassword;
	}

	public boolean requiresPassword() {
		return requiresPassword;
	}

	public static AuthType findByName(String name) {
		return Arrays.stream(AuthType.values())
				.filter(type -> type.name().equalsIgnoreCase(name))
				.findAny()
				.orElseThrow(() -> new ReportPortalException(INCORRECT_AUTHENTICATION_TYPE, name));
	}

	public static boolean isPresent(String name) {
		return null != findByName(name);
	}
}