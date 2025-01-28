/*
 * This file is generated by jOOQ.
 */
package com.epam.ta.reportportal.jooq.tables.records;


import com.epam.ta.reportportal.jooq.tables.JStaleMaterializedView;

import java.time.Instant;

import org.jooq.Record1;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes", "this-escape" })
public class JStaleMaterializedViewRecord extends UpdatableRecordImpl<JStaleMaterializedViewRecord> {

    private static final long serialVersionUID = 1L;

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

        setId(id);
        setName(name);
        setCreationDate(creationDate);
        resetChangedOnNotNull();
    }
}
