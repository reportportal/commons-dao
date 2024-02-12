/*
 * This file is generated by jOOQ.
 */
package com.epam.ta.reportportal.jooq.tables.records;


import com.epam.ta.reportportal.jooq.tables.JOrganization;

import java.sql.Timestamp;

import javax.annotation.processing.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record5;
import org.jooq.Row5;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.12.4"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class JOrganizationRecord extends UpdatableRecordImpl<JOrganizationRecord> implements Record5<Long, Timestamp, String, String, String> {

    private static final long serialVersionUID = 909327136;

    /**
     * Setter for <code>public.organization.id</code>.
     */
    public void setId(Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.organization.id</code>.
     */
    public Long getId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>public.organization.creation_date</code>.
     */
    public void setCreationDate(Timestamp value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.organization.creation_date</code>.
     */
    public Timestamp getCreationDate() {
        return (Timestamp) get(1);
    }

    /**
     * Setter for <code>public.organization.name</code>.
     */
    public void setName(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.organization.name</code>.
     */
    public String getName() {
        return (String) get(2);
    }

    /**
     * Setter for <code>public.organization.organization_type</code>.
     */
    public void setOrganizationType(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>public.organization.organization_type</code>.
     */
    public String getOrganizationType() {
        return (String) get(3);
    }

    /**
     * Setter for <code>public.organization.slug</code>.
     */
    public void setSlug(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>public.organization.slug</code>.
     */
    public String getSlug() {
        return (String) get(4);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<Long> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record5 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row5<Long, Timestamp, String, String, String> fieldsRow() {
        return (Row5) super.fieldsRow();
    }

    @Override
    public Row5<Long, Timestamp, String, String, String> valuesRow() {
        return (Row5) super.valuesRow();
    }

    @Override
    public Field<Long> field1() {
        return JOrganization.ORGANIZATION.ID;
    }

    @Override
    public Field<Timestamp> field2() {
        return JOrganization.ORGANIZATION.CREATION_DATE;
    }

    @Override
    public Field<String> field3() {
        return JOrganization.ORGANIZATION.NAME;
    }

    @Override
    public Field<String> field4() {
        return JOrganization.ORGANIZATION.ORGANIZATION_TYPE;
    }

    @Override
    public Field<String> field5() {
        return JOrganization.ORGANIZATION.SLUG;
    }

    @Override
    public Long component1() {
        return getId();
    }

    @Override
    public Timestamp component2() {
        return getCreationDate();
    }

    @Override
    public String component3() {
        return getName();
    }

    @Override
    public String component4() {
        return getOrganizationType();
    }

    @Override
    public String component5() {
        return getSlug();
    }

    @Override
    public Long value1() {
        return getId();
    }

    @Override
    public Timestamp value2() {
        return getCreationDate();
    }

    @Override
    public String value3() {
        return getName();
    }

    @Override
    public String value4() {
        return getOrganizationType();
    }

    @Override
    public String value5() {
        return getSlug();
    }

    @Override
    public JOrganizationRecord value1(Long value) {
        setId(value);
        return this;
    }

    @Override
    public JOrganizationRecord value2(Timestamp value) {
        setCreationDate(value);
        return this;
    }

    @Override
    public JOrganizationRecord value3(String value) {
        setName(value);
        return this;
    }

    @Override
    public JOrganizationRecord value4(String value) {
        setOrganizationType(value);
        return this;
    }

    @Override
    public JOrganizationRecord value5(String value) {
        setSlug(value);
        return this;
    }

    @Override
    public JOrganizationRecord values(Long value1, Timestamp value2, String value3, String value4, String value5) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached JOrganizationRecord
     */
    public JOrganizationRecord() {
        super(JOrganization.ORGANIZATION);
    }

    /**
     * Create a detached, initialised JOrganizationRecord
     */
    public JOrganizationRecord(Long id, Timestamp creationDate, String name, String organizationType, String slug) {
        super(JOrganization.ORGANIZATION);

        set(0, id);
        set(1, creationDate);
        set(2, name);
        set(3, organizationType);
        set(4, slug);
    }
}
