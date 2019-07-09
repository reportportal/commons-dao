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
package com.epam.ta.reportportal.entity.ldap;

import java.util.Arrays;
import java.util.Optional;

import static java.util.Optional.ofNullable;

/**
 * @author Andrei Varabyeu
 */
public enum PasswordEncoderType {
	PLAIN,
	SHA,
	LDAP_SHA,
	MD4,
	MD5;

	public static Optional<PasswordEncoderType> findByType(String type) {

		return ofNullable(type).flatMap(t -> Arrays.stream(values()).filter(it -> it.name().equalsIgnoreCase(t)).findAny());
	}

}
