/*
 * Copyright 2022 EPAM Systems
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

package com.epam.ta.reportportal.entity.organization;

import com.epam.ta.reportportal.entity.user.User;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

/**
 * @author Andrei Piankouski
 */
@Entity
@Table(name = "organization_user", schema = "public")
public class OrganizationUser implements Serializable {

	private static final long serialVersionUID = 7313055792392238124L;

	@EmbeddedId
	private OrganizationUserId id;

	@ManyToOne(fetch = FetchType.EAGER)
	@MapsId("organization_id")
	private Organization organization;

	@ManyToOne(fetch = FetchType.EAGER)
	@MapsId("user_id")
	private User user;

	@Column(name = "organization_role")
	private OrganizationRole organizationRole;

	public OrganizationUser() {
	}

	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public OrganizationRole getOrganizationRole() {
		return organizationRole;
	}

	public void setOrganizationRole(OrganizationRole organizationRole) {
		this.organizationRole = organizationRole;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof OrganizationUser)) {
			return false;
		}
		OrganizationUser that = (OrganizationUser) o;
		return organization.equals(that.organization) && user.equals(that.user) && organizationRole == that.organizationRole;
	}

	@Override
	public int hashCode() {
		return Objects.hash(organization, user, organizationRole);
	}
}
