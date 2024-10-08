/*
 * This file is generated by jOOQ.
 */
package com.epam.ta.reportportal.jooq.tables.records;


import com.epam.ta.reportportal.jooq.tables.JDashboard;

import java.time.Instant;

import javax.annotation.processing.Generated;

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
        "jOOQ version:3.12.4"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class JDashboardRecord extends UpdatableRecordImpl<JDashboardRecord> implements Record4<Long, String, String, Instant> {

    private static final long serialVersionUID = -2135476637;

    /**
     * Setter for <code>public.dashboard.id</code>.
     */
    public void setId(Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.dashboard.id</code>.
     */
    public Long getId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>public.dashboard.name</code>.
     */
    public void setName(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.dashboard.name</code>.
     */
    public String getName() {
        return (String) get(1);
    }

    /**
     * Setter for <code>public.dashboard.description</code>.
     */
    public void setDescription(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.dashboard.description</code>.
     */
    public String getDescription() {
        return (String) get(2);
    }

    /**
     * Setter for <code>public.dashboard.creation_date</code>.
     */
    public void setCreationDate(Instant value) {
        set(3, value);
    }

    /**
     * Getter for <code>public.dashboard.creation_date</code>.
     */
    public Instant getCreationDate() {
        return (Instant) get(3);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<Long> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record4 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row4<Long, String, String, Instant> fieldsRow() {
        return (Row4) super.fieldsRow();
    }

    @Override
    public Row4<Long, String, String, Instant> valuesRow() {
        return (Row4) super.valuesRow();
    }

    @Override
    public Field<Long> field1() {
        return JDashboard.DASHBOARD.ID;
    }

    @Override
    public Field<String> field2() {
        return JDashboard.DASHBOARD.NAME;
    }

    @Override
    public Field<String> field3() {
        return JDashboard.DASHBOARD.DESCRIPTION;
    }

    @Override
    public Field<Instant> field4() {
        return JDashboard.DASHBOARD.CREATION_DATE;
    }

    @Override
    public Long component1() {
        return getId();
    }

    @Override
    public String component2() {
        return getName();
    }

    @Override
    public String component3() {
        return getDescription();
    }

    @Override
    public Instant component4() {
        return getCreationDate();
    }

    @Override
    public Long value1() {
        return getId();
    }

    @Override
    public String value2() {
        return getName();
    }

    @Override
    public String value3() {
        return getDescription();
    }

    @Override
    public Instant value4() {
        return getCreationDate();
    }

    @Override
    public JDashboardRecord value1(Long value) {
        setId(value);
        return this;
    }

    @Override
    public JDashboardRecord value2(String value) {
        setName(value);
        return this;
    }

    @Override
    public JDashboardRecord value3(String value) {
        setDescription(value);
        return this;
    }

    @Override
    public JDashboardRecord value4(Instant value) {
        setCreationDate(value);
        return this;
    }

    @Override
    public JDashboardRecord values(Long value1, String value2, String value3, Instant value4) {
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
     * Create a detached JDashboardRecord
     */
    public JDashboardRecord() {
        super(JDashboard.DASHBOARD);
    }

    /**
     * Create a detached, initialised JDashboardRecord
     */
    public JDashboardRecord(Long id, String name, String description, Instant creationDate) {
        super(JDashboard.DASHBOARD);

        set(0, id);
        set(1, name);
        set(2, description);
        set(3, creationDate);
    }
}
