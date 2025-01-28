/*
 * This file is generated by jOOQ.
 */
package com.epam.ta.reportportal.jooq.tables.records;


import com.epam.ta.reportportal.jooq.tables.JAnalyticsData;

import java.time.Instant;

import org.jooq.JSONB;
import org.jooq.Record1;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes", "this-escape" })
public class JAnalyticsDataRecord extends UpdatableRecordImpl<JAnalyticsDataRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>public.analytics_data.id</code>.
     */
    public void setId(Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.analytics_data.id</code>.
     */
    public Long getId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>public.analytics_data.type</code>.
     */
    public void setType(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.analytics_data.type</code>.
     */
    public String getType() {
        return (String) get(1);
    }

    /**
     * Setter for <code>public.analytics_data.created_at</code>.
     */
    public void setCreatedAt(Instant value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.analytics_data.created_at</code>.
     */
    public Instant getCreatedAt() {
        return (Instant) get(2);
    }

    /**
     * Setter for <code>public.analytics_data.metadata</code>.
     */
    public void setMetadata(JSONB value) {
        set(3, value);
    }

    /**
     * Getter for <code>public.analytics_data.metadata</code>.
     */
    public JSONB getMetadata() {
        return (JSONB) get(3);
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
     * Create a detached JAnalyticsDataRecord
     */
    public JAnalyticsDataRecord() {
        super(JAnalyticsData.ANALYTICS_DATA);
    }

    /**
     * Create a detached, initialised JAnalyticsDataRecord
     */
    public JAnalyticsDataRecord(Long id, String type, Instant createdAt, JSONB metadata) {
        super(JAnalyticsData.ANALYTICS_DATA);

        setId(id);
        setType(type);
        setCreatedAt(createdAt);
        setMetadata(metadata);
        resetChangedOnNotNull();
    }
}
