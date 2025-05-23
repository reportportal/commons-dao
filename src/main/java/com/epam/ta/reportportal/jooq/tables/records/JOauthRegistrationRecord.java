/*
 * This file is generated by jOOQ.
 */
package com.epam.ta.reportportal.jooq.tables.records;


import com.epam.ta.reportportal.jooq.tables.JOauthRegistration;

import org.jooq.Record1;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes", "this-escape" })
public class JOauthRegistrationRecord extends UpdatableRecordImpl<JOauthRegistrationRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>public.oauth_registration.id</code>.
     */
    public void setId(String value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.oauth_registration.id</code>.
     */
    public String getId() {
        return (String) get(0);
    }

    /**
     * Setter for <code>public.oauth_registration.client_id</code>.
     */
    public void setClientId(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.oauth_registration.client_id</code>.
     */
    public String getClientId() {
        return (String) get(1);
    }

    /**
     * Setter for <code>public.oauth_registration.client_secret</code>.
     */
    public void setClientSecret(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.oauth_registration.client_secret</code>.
     */
    public String getClientSecret() {
        return (String) get(2);
    }

    /**
     * Setter for <code>public.oauth_registration.client_auth_method</code>.
     */
    public void setClientAuthMethod(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>public.oauth_registration.client_auth_method</code>.
     */
    public String getClientAuthMethod() {
        return (String) get(3);
    }

    /**
     * Setter for <code>public.oauth_registration.auth_grant_type</code>.
     */
    public void setAuthGrantType(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>public.oauth_registration.auth_grant_type</code>.
     */
    public String getAuthGrantType() {
        return (String) get(4);
    }

    /**
     * Setter for <code>public.oauth_registration.redirect_uri_template</code>.
     */
    public void setRedirectUriTemplate(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>public.oauth_registration.redirect_uri_template</code>.
     */
    public String getRedirectUriTemplate() {
        return (String) get(5);
    }

    /**
     * Setter for <code>public.oauth_registration.authorization_uri</code>.
     */
    public void setAuthorizationUri(String value) {
        set(6, value);
    }

    /**
     * Getter for <code>public.oauth_registration.authorization_uri</code>.
     */
    public String getAuthorizationUri() {
        return (String) get(6);
    }

    /**
     * Setter for <code>public.oauth_registration.token_uri</code>.
     */
    public void setTokenUri(String value) {
        set(7, value);
    }

    /**
     * Getter for <code>public.oauth_registration.token_uri</code>.
     */
    public String getTokenUri() {
        return (String) get(7);
    }

    /**
     * Setter for <code>public.oauth_registration.user_info_endpoint_uri</code>.
     */
    public void setUserInfoEndpointUri(String value) {
        set(8, value);
    }

    /**
     * Getter for <code>public.oauth_registration.user_info_endpoint_uri</code>.
     */
    public String getUserInfoEndpointUri() {
        return (String) get(8);
    }

    /**
     * Setter for
     * <code>public.oauth_registration.user_info_endpoint_name_attr</code>.
     */
    public void setUserInfoEndpointNameAttr(String value) {
        set(9, value);
    }

    /**
     * Getter for
     * <code>public.oauth_registration.user_info_endpoint_name_attr</code>.
     */
    public String getUserInfoEndpointNameAttr() {
        return (String) get(9);
    }

    /**
     * Setter for <code>public.oauth_registration.jwk_set_uri</code>.
     */
    public void setJwkSetUri(String value) {
        set(10, value);
    }

    /**
     * Getter for <code>public.oauth_registration.jwk_set_uri</code>.
     */
    public String getJwkSetUri() {
        return (String) get(10);
    }

    /**
     * Setter for <code>public.oauth_registration.client_name</code>.
     */
    public void setClientName(String value) {
        set(11, value);
    }

    /**
     * Getter for <code>public.oauth_registration.client_name</code>.
     */
    public String getClientName() {
        return (String) get(11);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<String> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached JOauthRegistrationRecord
     */
    public JOauthRegistrationRecord() {
        super(JOauthRegistration.OAUTH_REGISTRATION);
    }

    /**
     * Create a detached, initialised JOauthRegistrationRecord
     */
    public JOauthRegistrationRecord(String id, String clientId, String clientSecret, String clientAuthMethod, String authGrantType, String redirectUriTemplate, String authorizationUri, String tokenUri, String userInfoEndpointUri, String userInfoEndpointNameAttr, String jwkSetUri, String clientName) {
        super(JOauthRegistration.OAUTH_REGISTRATION);

        setId(id);
        setClientId(clientId);
        setClientSecret(clientSecret);
        setClientAuthMethod(clientAuthMethod);
        setAuthGrantType(authGrantType);
        setRedirectUriTemplate(redirectUriTemplate);
        setAuthorizationUri(authorizationUri);
        setTokenUri(tokenUri);
        setUserInfoEndpointUri(userInfoEndpointUri);
        setUserInfoEndpointNameAttr(userInfoEndpointNameAttr);
        setJwkSetUri(jwkSetUri);
        setClientName(clientName);
        resetChangedOnNotNull();
    }
}
