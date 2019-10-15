/*
 * This file is generated by jOOQ.
 */
package com.epam.ta.reportportal.jooq.tables.records;


import com.epam.ta.reportportal.jooq.tables.JLdapSynchronizationAttributes;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record4;
import org.jooq.Row4;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.11.11"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class JLdapSynchronizationAttributesRecord extends UpdatableRecordImpl<JLdapSynchronizationAttributesRecord> implements Record4<Long, String, String, String> {

    private static final long serialVersionUID = -1998097415;

    /**
     * Setter for <code>public.ldap_synchronization_attributes.id</code>.
     */
    public void setId(Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.ldap_synchronization_attributes.id</code>.
     */
    public Long getId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>public.ldap_synchronization_attributes.email</code>.
     */
    public void setEmail(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.ldap_synchronization_attributes.email</code>.
     */
    public String getEmail() {
        return (String) get(1);
    }

    /**
     * Setter for <code>public.ldap_synchronization_attributes.full_name</code>.
     */
    public void setFullName(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.ldap_synchronization_attributes.full_name</code>.
     */
    public String getFullName() {
        return (String) get(2);
    }

    /**
     * Setter for <code>public.ldap_synchronization_attributes.photo</code>.
     */
    public void setPhoto(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>public.ldap_synchronization_attributes.photo</code>.
     */
    public String getPhoto() {
        return (String) get(3);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Record1<Long> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record4 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row4<Long, String, String, String> fieldsRow() {
        return (Row4) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row4<Long, String, String, String> valuesRow() {
        return (Row4) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field1() {
        return JLdapSynchronizationAttributes.LDAP_SYNCHRONIZATION_ATTRIBUTES.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return JLdapSynchronizationAttributes.LDAP_SYNCHRONIZATION_ATTRIBUTES.EMAIL;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return JLdapSynchronizationAttributes.LDAP_SYNCHRONIZATION_ATTRIBUTES.FULL_NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return JLdapSynchronizationAttributes.LDAP_SYNCHRONIZATION_ATTRIBUTES.PHOTO;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long component1() {
        return getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component2() {
        return getEmail();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component3() {
        return getFullName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component4() {
        return getPhoto();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long value1() {
        return getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value2() {
        return getEmail();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value3() {
        return getFullName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value4() {
        return getPhoto();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JLdapSynchronizationAttributesRecord value1(Long value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JLdapSynchronizationAttributesRecord value2(String value) {
        setEmail(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JLdapSynchronizationAttributesRecord value3(String value) {
        setFullName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JLdapSynchronizationAttributesRecord value4(String value) {
        setPhoto(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JLdapSynchronizationAttributesRecord values(Long value1, String value2, String value3, String value4) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached JLdapSynchronizationAttributesRecord
     */
    public JLdapSynchronizationAttributesRecord() {
        super(JLdapSynchronizationAttributes.LDAP_SYNCHRONIZATION_ATTRIBUTES);
    }

    /**
     * Create a detached, initialised JLdapSynchronizationAttributesRecord
     */
    public JLdapSynchronizationAttributesRecord(Long id, String email, String fullName, String photo) {
        super(JLdapSynchronizationAttributes.LDAP_SYNCHRONIZATION_ATTRIBUTES);

        set(0, id);
        set(1, email);
        set(2, fullName);
        set(3, photo);
    }
}
