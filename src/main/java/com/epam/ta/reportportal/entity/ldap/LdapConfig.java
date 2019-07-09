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

import com.epam.ta.reportportal.entity.enums.PostgreSQLEnumType;
import com.epam.ta.reportportal.entity.ldap.validation.LdapSequenceProvider;
import com.google.common.base.MoreObjects;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.validator.group.GroupSequenceProvider;

import javax.persistence.*;

/**
 * LDAP auth config
 *
 * @author Andrei Varabyeu
 */

@GroupSequenceProvider(LdapSequenceProvider.class)
@Entity
@TypeDef(name = "pgsql_enum", typeClass = PostgreSQLEnumType.class)
@Table(name = "ldap_config", schema = "public")
public class LdapConfig extends AbstractLdapIntegration {

	@Column(name = "user_dn_pattern", length = 256)
	private String userDnPattern;

	@Column(name = "user_search_filter", length = 256)
	private String userSearchFilter;

	@Column(name = "group_search_base", length = 256)
	private String groupSearchBase;

	@Column(name = "group_search_filter", length = 256)
	private String groupSearchFilter;

	@Enumerated(EnumType.STRING)
	@Type(type = "pqsql_enum")
	@Column(name = "passwordencodertype", nullable = false)
	private PasswordEncoderType passwordEncoderType;

	@Column(name = "password_attributes", length = 256)
	private String passwordAttribute;

	@Column(name = "manager_dn", length = 256)
	private String managerDn;

	@Column(name = "manager_password", length = 256)
	private String managerPassword;

	public String getUserDnPattern() {
		return userDnPattern;
	}

	public void setUserDnPattern(String userDnPattern) {
		this.userDnPattern = userDnPattern;
	}

	public String getGroupSearchBase() {
		return groupSearchBase;
	}

	public void setGroupSearchBase(String groupSearchBase) {
		this.groupSearchBase = groupSearchBase;
	}

	public PasswordEncoderType getPasswordEncoderType() {
		return passwordEncoderType;
	}

	public void setPasswordEncoderType(PasswordEncoderType passwordEncoderType) {
		this.passwordEncoderType = passwordEncoderType;
	}

	public String getPasswordAttribute() {
		return passwordAttribute;
	}

	public void setPasswordAttribute(String passwordAttribute) {
		this.passwordAttribute = passwordAttribute;
	}

	public String getManagerDn() {
		return managerDn;
	}

	public void setManagerDn(String managerDn) {
		this.managerDn = managerDn;
	}

	public String getManagerPassword() {
		return managerPassword;
	}

	public void setManagerPassword(String managerPassword) {
		this.managerPassword = managerPassword;
	}

	public String getUserSearchFilter() {
		return userSearchFilter;
	}

	public void setUserSearchFilter(String userSearchFilter) {
		this.userSearchFilter = userSearchFilter;
	}

	public String getGroupSearchFilter() {
		return groupSearchFilter;
	}

	public void setGroupSearchFilter(String groupSearchFilter) {
		this.groupSearchFilter = groupSearchFilter;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("userDnPattern", userDnPattern)
				.add("userSearchFilter", userSearchFilter)
				.add("groupSearchBase", groupSearchBase)
				.add("groupSearchFilter", groupSearchFilter)
				.add("passwordEncoderType", passwordEncoderType)
				.add("passwordAttribute", passwordAttribute)
				.add("managerDn", managerDn)
				.add("managerPassword", managerPassword)
				.toString();
	}
}
