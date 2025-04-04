/*
 * This file is generated by jOOQ.
 */
package com.epam.ta.reportportal.jooq.tables.records;


import com.epam.ta.reportportal.jooq.tables.JIntegration;

import java.time.Instant;

import org.jooq.JSONB;
import org.jooq.Record1;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes", "this-escape" })
public class JIntegrationRecord extends UpdatableRecordImpl<JIntegrationRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>public.integration.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.integration.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>public.integration.name</code>.
     */
    public void setName(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.integration.name</code>.
     */
    public String getName() {
        return (String) get(1);
    }

    /**
     * Setter for <code>public.integration.project_id</code>.
     */
    public void setProjectId(Long value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.integration.project_id</code>.
     */
    public Long getProjectId() {
        return (Long) get(2);
    }

    /**
     * Setter for <code>public.integration.type</code>.
     */
    public void setType(Integer value) {
        set(3, value);
    }

    /**
     * Getter for <code>public.integration.type</code>.
     */
    public Integer getType() {
        return (Integer) get(3);
    }

    /**
     * Setter for <code>public.integration.enabled</code>.
     */
    public void setEnabled(Boolean value) {
        set(4, value);
    }

    /**
     * Getter for <code>public.integration.enabled</code>.
     */
    public Boolean getEnabled() {
        return (Boolean) get(4);
    }

    /**
     * Setter for <code>public.integration.params</code>.
     */
    public void setParams(JSONB value) {
        set(5, value);
    }

    /**
     * Getter for <code>public.integration.params</code>.
     */
    public JSONB getParams() {
        return (JSONB) get(5);
    }

    /**
     * Setter for <code>public.integration.creator</code>.
     */
    public void setCreator(String value) {
        set(6, value);
    }

    /**
     * Getter for <code>public.integration.creator</code>.
     */
    public String getCreator() {
        return (String) get(6);
    }

    /**
     * Setter for <code>public.integration.creation_date</code>.
     */
    public void setCreationDate(Instant value) {
        set(7, value);
    }

    /**
     * Getter for <code>public.integration.creation_date</code>.
     */
    public Instant getCreationDate() {
        return (Instant) get(7);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<Integer> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached JIntegrationRecord
     */
    public JIntegrationRecord() {
        super(JIntegration.INTEGRATION);
    }

    /**
     * Create a detached, initialised JIntegrationRecord
     */
    public JIntegrationRecord(Integer id, String name, Long projectId, Integer type, Boolean enabled, JSONB params, String creator, Instant creationDate) {
        super(JIntegration.INTEGRATION);

        setId(id);
        setName(name);
        setProjectId(projectId);
        setType(type);
        setEnabled(enabled);
        setParams(params);
        setCreator(creator);
        setCreationDate(creationDate);
        resetChangedOnNotNull();
    }
}
