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

package com.epam.ta.reportportal.commons.querygen.constant;

/**
 * @author Ivan Budaev
 */
public final class UserCriteriaConstant {

	public static final String CRITERIA_USER = "user";
	public static final String CRITERIA_ROLE = "role";
	public static final String CRITERIA_TYPE = "type";
	public static final String CRITERIA_FULL_NAME = "fullName";
	public static final String CRITERIA_EMAIL = "email";
	public static final String CRITERIA_EXPIRED = "expired";
	public static final String CRITERIA_LAST_LOGIN = "lastLogin";
	public static final String CRITERIA_SYNCHRONIZATION_DATE = "synchronizationDate";
	public static final String CRITERIA_USER_PROJECT = "project";

	private UserCriteriaConstant() {
		//static only
	}
}
