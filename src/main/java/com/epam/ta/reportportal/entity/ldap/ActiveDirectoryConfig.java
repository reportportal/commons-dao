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

import com.epam.ta.reportportal.entity.ldap.validation.AdSequenceProvider;
import com.epam.ta.reportportal.entity.ldap.validation.IfEnabled;
import com.google.common.base.MoreObjects;
import org.hibernate.validator.group.GroupSequenceProvider;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * Active Directory auth config
 *
 * @author Andrei Varabyeu
 */
@GroupSequenceProvider(AdSequenceProvider.class)
@Entity
@Table(name = "active_directory_config", schema = "public")
public class ActiveDirectoryConfig extends AbstractLdapIntegration {

	@NotNull(groups = { IfEnabled.class })
	@Column(name = "domain", length = 256)
	private String domain;

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this).add("SUPER", super.toString()).add("domain", domain).toString();
	}
}
