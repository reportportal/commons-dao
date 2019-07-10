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

/*
 * This file is generated by jOOQ.
 */
package com.epam.ta.reportportal.jooq.tables.records;

import com.epam.ta.reportportal.jooq.tables.JOauthRegistration;
import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record12;
import org.jooq.Row12;
import org.jooq.impl.UpdatableRecordImpl;

import javax.annotation.Generated;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.11.7"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class JOauthRegistrationRecord extends UpdatableRecordImpl<JOauthRegistrationRecord> implements Record12<String, String, String, String, String, String, String, String, String, String, String, String> {

    private static final long serialVersionUID = -498807789;

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
     * Setter for <code>public.oauth_registration.user_info_endpoint_name_attr</code>.
     */
    public void setUserInfoEndpointNameAttr(String value) {
        set(9, value);
    }

    /**
     * Getter for <code>public.oauth_registration.user_info_endpoint_name_attr</code>.
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

    /**
     * {@inheritDoc}
     */
    @Override
    public Record1<String> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record12 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row12<String, String, String, String, String, String, String, String, String, String, String, String> fieldsRow() {
        return (Row12) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row12<String, String, String, String, String, String, String, String, String, String, String, String> valuesRow() {
        return (Row12) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field1() {
        return JOauthRegistration.OAUTH_REGISTRATION.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return JOauthRegistration.OAUTH_REGISTRATION.CLIENT_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return JOauthRegistration.OAUTH_REGISTRATION.CLIENT_SECRET;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return JOauthRegistration.OAUTH_REGISTRATION.CLIENT_AUTH_METHOD;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field5() {
        return JOauthRegistration.OAUTH_REGISTRATION.AUTH_GRANT_TYPE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field6() {
        return JOauthRegistration.OAUTH_REGISTRATION.REDIRECT_URI_TEMPLATE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field7() {
        return JOauthRegistration.OAUTH_REGISTRATION.AUTHORIZATION_URI;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field8() {
        return JOauthRegistration.OAUTH_REGISTRATION.TOKEN_URI;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field9() {
        return JOauthRegistration.OAUTH_REGISTRATION.USER_INFO_ENDPOINT_URI;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field10() {
        return JOauthRegistration.OAUTH_REGISTRATION.USER_INFO_ENDPOINT_NAME_ATTR;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field11() {
        return JOauthRegistration.OAUTH_REGISTRATION.JWK_SET_URI;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field12() {
        return JOauthRegistration.OAUTH_REGISTRATION.CLIENT_NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component1() {
        return getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component2() {
        return getClientId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component3() {
        return getClientSecret();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component4() {
        return getClientAuthMethod();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component5() {
        return getAuthGrantType();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component6() {
        return getRedirectUriTemplate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component7() {
        return getAuthorizationUri();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component8() {
        return getTokenUri();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component9() {
        return getUserInfoEndpointUri();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component10() {
        return getUserInfoEndpointNameAttr();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component11() {
        return getJwkSetUri();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component12() {
        return getClientName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value1() {
        return getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value2() {
        return getClientId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value3() {
        return getClientSecret();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value4() {
        return getClientAuthMethod();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value5() {
        return getAuthGrantType();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value6() {
        return getRedirectUriTemplate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value7() {
        return getAuthorizationUri();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value8() {
        return getTokenUri();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value9() {
        return getUserInfoEndpointUri();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value10() {
        return getUserInfoEndpointNameAttr();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value11() {
        return getJwkSetUri();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value12() {
        return getClientName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JOauthRegistrationRecord value1(String value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JOauthRegistrationRecord value2(String value) {
        setClientId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JOauthRegistrationRecord value3(String value) {
        setClientSecret(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JOauthRegistrationRecord value4(String value) {
        setClientAuthMethod(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JOauthRegistrationRecord value5(String value) {
        setAuthGrantType(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JOauthRegistrationRecord value6(String value) {
        setRedirectUriTemplate(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JOauthRegistrationRecord value7(String value) {
        setAuthorizationUri(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JOauthRegistrationRecord value8(String value) {
        setTokenUri(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JOauthRegistrationRecord value9(String value) {
        setUserInfoEndpointUri(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JOauthRegistrationRecord value10(String value) {
        setUserInfoEndpointNameAttr(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JOauthRegistrationRecord value11(String value) {
        setJwkSetUri(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JOauthRegistrationRecord value12(String value) {
        setClientName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JOauthRegistrationRecord values(String value1, String value2, String value3, String value4, String value5, String value6, String value7, String value8, String value9, String value10, String value11, String value12) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        value8(value8);
        value9(value9);
        value10(value10);
        value11(value11);
        value12(value12);
        return this;
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

        set(0, id);
        set(1, clientId);
        set(2, clientSecret);
        set(3, clientAuthMethod);
        set(4, authGrantType);
        set(5, redirectUriTemplate);
        set(6, authorizationUri);
        set(7, tokenUri);
        set(8, userInfoEndpointUri);
        set(9, userInfoEndpointNameAttr);
        set(10, jwkSetUri);
        set(11, clientName);
    }
}
