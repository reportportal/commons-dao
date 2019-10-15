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
package com.epam.ta.reportportal.entity.ldap;

import com.epam.ta.reportportal.entity.integration.Integration;
import com.epam.ta.reportportal.entity.ldap.validation.IfEnabled;
import com.google.common.base.MoreObjects;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * General properties for both LDAP and AD authentication types
 *
 * @author Andrei Varabyeu
 */

@MappedSuperclass
@PrimaryKeyJoinColumn(name = "id")
public class AbstractLdapIntegration extends Integration {

	@Pattern(regexp = "^ldaps?://.*")
	@NotEmpty(groups = { IfEnabled.class })
	@Column(name = "url", length = 256)
	private String url;

	@NotNull(groups = { IfEnabled.class })
	@Column(name = "base_dn", length = 256)
	private String baseDn;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "sync_attributes_id")
	private SynchronizationAttributes synchronizationAttributes;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getBaseDn() {
		return baseDn;
	}

	public void setBaseDn(String baseDn) {
		this.baseDn = baseDn;
	}

	public SynchronizationAttributes getSynchronizationAttributes() {
		return synchronizationAttributes;
	}

	public void setSynchronizationAttributes(SynchronizationAttributes synchronizationAttributes) {
		this.synchronizationAttributes = synchronizationAttributes;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("url", url)
				.add("baseDn", baseDn)
				.add("synchronizationAttributes", synchronizationAttributes)
				.add("super1", super.isEnabled())
				.toString();
	}
}
