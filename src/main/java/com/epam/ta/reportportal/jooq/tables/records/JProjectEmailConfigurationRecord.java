/*
 * This file is generated by jOOQ.
 */
package com.epam.ta.reportportal.jooq.tables.records;


import com.epam.ta.reportportal.jooq.tables.JProjectEmailConfiguration;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record3;
import org.jooq.Row3;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.11.4"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class JProjectEmailConfigurationRecord extends UpdatableRecordImpl<JProjectEmailConfigurationRecord> implements Record3<Long, Boolean, String> {

    private static final long serialVersionUID = 519711487;

    /**
     * Setter for <code>public.project_email_configuration.id</code>.
     */
    public void setId(Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.project_email_configuration.id</code>.
     */
    public Long getId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>public.project_email_configuration.enabled</code>.
     */
    public void setEnabled(Boolean value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.project_email_configuration.enabled</code>.
     */
    public Boolean getEnabled() {
        return (Boolean) get(1);
    }

    /**
     * Setter for <code>public.project_email_configuration.email_from</code>.
     */
    public void setEmailFrom(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.project_email_configuration.email_from</code>.
     */
    public String getEmailFrom() {
        return (String) get(2);
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
    // Record3 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row3<Long, Boolean, String> fieldsRow() {
        return (Row3) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row3<Long, Boolean, String> valuesRow() {
        return (Row3) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field1() {
        return JProjectEmailConfiguration.PROJECT_EMAIL_CONFIGURATION.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Boolean> field2() {
        return JProjectEmailConfiguration.PROJECT_EMAIL_CONFIGURATION.ENABLED;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return JProjectEmailConfiguration.PROJECT_EMAIL_CONFIGURATION.EMAIL_FROM;
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
    public Boolean component2() {
        return getEnabled();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component3() {
        return getEmailFrom();
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
    public Boolean value2() {
        return getEnabled();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value3() {
        return getEmailFrom();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JProjectEmailConfigurationRecord value1(Long value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JProjectEmailConfigurationRecord value2(Boolean value) {
        setEnabled(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JProjectEmailConfigurationRecord value3(String value) {
        setEmailFrom(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JProjectEmailConfigurationRecord values(Long value1, Boolean value2, String value3) {
        value1(value1);
        value2(value2);
        value3(value3);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached JProjectEmailConfigurationRecord
     */
    public JProjectEmailConfigurationRecord() {
        super(JProjectEmailConfiguration.PROJECT_EMAIL_CONFIGURATION);
    }

    /**
     * Create a detached, initialised JProjectEmailConfigurationRecord
     */
    public JProjectEmailConfigurationRecord(Long id, Boolean enabled, String emailFrom) {
        super(JProjectEmailConfiguration.PROJECT_EMAIL_CONFIGURATION);

        set(0, id);
        set(1, enabled);
        set(2, emailFrom);
    }
}
