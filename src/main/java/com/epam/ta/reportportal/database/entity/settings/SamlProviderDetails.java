/*
 * Copyright 2019 EPAM Systems
 *
 *
 * This file is part of EPAM Report Portal.
 * https://github.com/reportportal/commons-dao
 *
 * Report Portal is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Report Portal is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Report Portal.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.epam.ta.reportportal.database.entity.settings;

import java.io.Serializable;

/**
 * Represents settings for SAML identity providers.
 *
 * @author Yevgeniy Svalukhin
 */
public class SamlProviderDetails implements Serializable {

    private static final long serialVersionUID = 809129422453984L;

    /**
     * Name of IDP displayed on login page
     */
    private String idpName;
    /**
     * URL for getting IDP metadata information
     */
    private String idpMetadata;
    /**
     * Attribute Name Format Id associated with IDP for user identification and extracted from metadata
     * https://www.oasis-open.org/committees/download.php/35711/sstc-saml-core-errata-2.0-wd-06-diff.pdf
     * Page 82, Line 3528
     */
    private String idpNameId;
    /**
     * Alias associated with IDP extracted from metadata
     */
    private String idpAlias;
    /**
     * URL of IDP extracted from metadata
     */
    private String idpUrl;
    /**
     * Name of attribute used for extracting full name from SAML response
     */
    private String fullNameAttributeId;
    /**
     * Name of attribute used for extracting first name from SAML response
     */
    private String firstNameAttributeId;
    /**
     * Name of attribute used for extracting last name from SAML response
     */
    private String lastNameAttributeId;
    /**
     * Name of attribute used for extracting email from SAML response
     */
    private String emailAttributeId;
    /**
     * Identifies IDP is enabled for authentication
     */
    private boolean enabled;

    public String getIdpName() {
        return idpName;
    }

    public SamlProviderDetails setIdpName(String idpName) {
        this.idpName = idpName;
        return this;
    }

    public String getIdpMetadata() {
        return idpMetadata;
    }

    public SamlProviderDetails setIdpMetadata(String idpMetadata) {
        this.idpMetadata = idpMetadata;
        return this;
    }

    public String getIdpNameId() {
        return idpNameId;
    }

    public SamlProviderDetails setIdpNameId(String idpNameId) {
        this.idpNameId = idpNameId;
        return this;
    }

    public String getIdpAlias() {
        return idpAlias;
    }

    public SamlProviderDetails setIdpAlias(String idpAlias) {
        this.idpAlias = idpAlias;
        return this;
    }

    public String getFullNameAttributeId() {
        return fullNameAttributeId;
    }

    public SamlProviderDetails setFullNameAttributeId(String fullNameAttributeId) {
        this.fullNameAttributeId = fullNameAttributeId;
        return this;
    }

    public String getFirstNameAttributeId() {
        return firstNameAttributeId;
    }

    public SamlProviderDetails setFirstNameAttributeId(String firstNameAttributeId) {
        this.firstNameAttributeId = firstNameAttributeId;
        return this;
    }

    public String getLastNameAttributeId() {
        return lastNameAttributeId;
    }

    public SamlProviderDetails setLastNameAttributeId(String lastNameAttributeId) {
        this.lastNameAttributeId = lastNameAttributeId;
        return this;
    }

    public String getEmailAttributeId() {
        return emailAttributeId;
    }

    public SamlProviderDetails setEmailAttributeId(String emailAttributeId) {
        this.emailAttributeId = emailAttributeId;
        return this;
    }

    public String getIdpUrl() {
        return idpUrl;
    }

    public SamlProviderDetails setIdpUrl(String idpUrl) {
        this.idpUrl = idpUrl;
        return this;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public SamlProviderDetails setEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }
}
