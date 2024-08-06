/*
 * This file is generated by jOOQ.
 */
package com.epam.ta.reportportal.jooq.tables.records;


import com.epam.ta.reportportal.jooq.tables.JStaleMaterializedView;

import java.time.Instant;

import javax.annotation.processing.Generated;

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
        "jOOQ version:3.12.4"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class JStaleMaterializedViewRecord extends UpdatableRecordImpl<JStaleMaterializedViewRecord> implements Record3<Long, String, Instant> {

    private static final long serialVersionUID = 1317537151;

    /**
     * Setter for <code>public.stale_materialized_view.id</code>.
     */
    public void setId(Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.stale_materialized_view.id</code>.
     */
    public Long getId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>public.stale_materialized_view.name</code>.
     */
    public void setName(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.stale_materialized_view.name</code>.
     */
    public String getName() {
        return (String) get(1);
    }

    /**
     * Setter for <code>public.stale_materialized_view.creation_date</code>.
     */
    public void setCreationDate(Instant value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.stale_materialized_view.creation_date</code>.
     */
    public Instant getCreationDate() {
        return (Instant) get(2);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<Long> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record3 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row3<Long, String, Instant> fieldsRow() {
        return (Row3) super.fieldsRow();
    }

    @Override
    public Row3<Long, String, Instant> valuesRow() {
        return (Row3) super.valuesRow();
    }

    @Override
    public Field<Long> field1() {
        return JStaleMaterializedView.STALE_MATERIALIZED_VIEW.ID;
    }

    @Override
    public Field<String> field2() {
        return JStaleMaterializedView.STALE_MATERIALIZED_VIEW.NAME;
    }

    @Override
    public Field<Instant> field3() {
        return JStaleMaterializedView.STALE_MATERIALIZED_VIEW.CREATION_DATE;
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
    public Instant component3() {
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
    public Instant value3() {
        return getCreationDate();
    }

    @Override
    public JStaleMaterializedViewRecord value1(Long value) {
        setId(value);
        return this;
    }

    @Override
    public JStaleMaterializedViewRecord value2(String value) {
        setName(value);
        return this;
    }

    @Override
    public JStaleMaterializedViewRecord value3(Instant value) {
        setCreationDate(value);
        return this;
    }

    @Override
    public JStaleMaterializedViewRecord values(Long value1, String value2, Instant value3) {
        value1(value1);
        value2(value2);
        value3(value3);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached JStaleMaterializedViewRecord
     */
    public JStaleMaterializedViewRecord() {
        super(JStaleMaterializedView.STALE_MATERIALIZED_VIEW);
    }

    /**
     * Create a detached, initialised JStaleMaterializedViewRecord
     */
    public JStaleMaterializedViewRecord(Long id, String name, Instant creationDate) {
        super(JStaleMaterializedView.STALE_MATERIALIZED_VIEW);

        set(0, id);
        set(1, name);
        set(2, creationDate);
    }
}
