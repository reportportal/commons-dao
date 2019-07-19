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

package com.epam.ta.reportportal.entity.saml;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * @author <a href="mailto:ihar_kahadouski@epam.com">Ihar Kahadouski</a>
 */
@Entity
@Table(name = "saml_provider_details", schema = "public")
public class SamlProviderDetails implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	/**
	 * Name of IDP displayed on login page
	 */
	@Column(name = "idp_name")
	private String idpName;
	/**
	 * URL for getting IDP metadata information
	 */
	@Column(name = "idp_metadata_url")
	private String idpMetadata;
	/**
	 * Attribute Name Format Id associated with IDP for user identification and extracted from metadata
	 * https://www.oasis-open.org/committees/download.php/35711/sstc-saml-core-errata-2.0-wd-06-diff.pdf
	 * Page 82, Line 3528
	 */
	@Column(name = "idp_name_id")
	private String idpNameId;
	/**
	 * Alias associated with IDP extracted from metadata
	 */
	@Column(name = "idp_alias")
	private String idpAlias;
	/**
	 * URL of IDP extracted from metadata
	 */
	@Column(name = "idp_url")
	private String idpUrl;
	/**
	 * Name of attribute used for extracting full name from SAML response
	 */
	@Column(name = "full_name_attribute_id")
	private String fullNameAttributeId;
	/**
	 * Name of attribute used for extracting first name from SAML response
	 */
	@Column(name = "first_name_attribute_id")
	private String firstNameAttributeId;
	/**
	 * Name of attribute used for extracting last name from SAML response
	 */
	@Column(name = "last_name_attribute_id")
	private String lastNameAttributeId;
	/**
	 * Name of attribute used for extracting email from SAML response
	 */
	@Column(name = "email_attribute_id")
	private String emailAttributeId;
	/**
	 * Identifies IDP is enabled for authentication
	 */
	@Column(name = "enabled")
	private boolean enabled;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIdpName() {
		return idpName;
	}

	public void setIdpName(String idpName) {
		this.idpName = idpName;
	}

	public String getIdpMetadata() {
		return idpMetadata;
	}

	public void setIdpMetadata(String idpMetadata) {
		this.idpMetadata = idpMetadata;
	}

	public String getIdpNameId() {
		return idpNameId;
	}

	public void setIdpNameId(String idpNameId) {
		this.idpNameId = idpNameId;
	}

	public String getIdpAlias() {
		return idpAlias;
	}

	public void setIdpAlias(String idpAlias) {
		this.idpAlias = idpAlias;
	}

	public String getIdpUrl() {
		return idpUrl;
	}

	public void setIdpUrl(String idpUrl) {
		this.idpUrl = idpUrl;
	}

	public String getFullNameAttributeId() {
		return fullNameAttributeId;
	}

	public void setFullNameAttributeId(String fullNameAttributeId) {
		this.fullNameAttributeId = fullNameAttributeId;
	}

	public String getFirstNameAttributeId() {
		return firstNameAttributeId;
	}

	public void setFirstNameAttributeId(String firstNameAttributeId) {
		this.firstNameAttributeId = firstNameAttributeId;
	}

	public String getLastNameAttributeId() {
		return lastNameAttributeId;
	}

	public void setLastNameAttributeId(String lastNameAttributeId) {
		this.lastNameAttributeId = lastNameAttributeId;
	}

	public String getEmailAttributeId() {
		return emailAttributeId;
	}

	public void setEmailAttributeId(String emailAttributeId) {
		this.emailAttributeId = emailAttributeId;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		SamlProviderDetails that = (SamlProviderDetails) o;
		return enabled == that.enabled && id.equals(that.id) && idpName.equals(that.idpName) && idpMetadata.equals(that.idpMetadata)
				&& Objects.equals(idpNameId, that.idpNameId) && Objects.equals(idpAlias, that.idpAlias) && Objects.equals(idpUrl,
				that.idpUrl
		) && Objects.equals(fullNameAttributeId, that.fullNameAttributeId) && Objects.equals(firstNameAttributeId,
				that.firstNameAttributeId
		) && Objects.equals(lastNameAttributeId, that.lastNameAttributeId) && emailAttributeId.equals(that.emailAttributeId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id,
				idpName,
				idpMetadata,
				idpNameId,
				idpAlias,
				idpUrl,
				fullNameAttributeId,
				firstNameAttributeId,
				lastNameAttributeId,
				emailAttributeId,
				enabled
		);
	}
}
