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

package com.epam.ta.reportportal.commons;

import java.util.ResourceBundle;

/**
 * Injector for global data values for initial RP items
 *
 * @author Andrei_Ramanchuk
 */
public enum Constants {
	//@formatter:off
	NONAME_USER("Users.noname"),
	
	DEFAULT_USER("Users.DefaultUser"),
	DEFAULT_USER_PASS("Users.DefaultUser.Pass"),

	USER("Users.User"),
	USER_PASS("Users.User.Pass"),
	USER_UUID("Users.User.UUID"),
	
	DEFAULT_ADMIN("Users.DefaultAdmin"),
	DEFAULT_ADMIN_PASS("Users.DefaultAdmin.Pass");
	//@formatter:on

	private final static ResourceBundle ITEMS = ResourceBundle.getBundle("init.initial-items");
	private final String ref;

	Constants(final String msgReference) {
		ref = msgReference;
	}

	@Override
	public String toString() {
		return ITEMS.getString(ref).toLowerCase();
	}
}