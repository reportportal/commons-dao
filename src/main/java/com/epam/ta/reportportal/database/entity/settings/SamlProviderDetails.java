package com.epam.ta.reportportal.database.entity.settings;

import java.io.Serializable;

public class SamlProviderDetails implements Serializable {

    private static final long serialVersionUID = 809129422453984L;

    private String idpName;
    private String idpMetadata;
    private String idpNameId;
    private String idpAlias;
    private String idpUrl;
    private String fullNameAttributeId;
    private String firstNameAttributeId;
    private String lastNameAttributeId;
    private String emailAttributeId;
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
