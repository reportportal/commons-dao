/*
 * This file is generated by jOOQ.
 */
package com.epam.ta.reportportal.jooq.tables.records;


import com.epam.ta.reportportal.jooq.tables.JOwnedEntity;

import org.jooq.Record1;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes", "this-escape" })
public class JOwnedEntityRecord extends UpdatableRecordImpl<JOwnedEntityRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>public.owned_entity.id</code>.
     */
    public void setId(Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.owned_entity.id</code>.
     */
    public Long getId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>public.owned_entity.owner</code>.
     */
    public void setOwner(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.owned_entity.owner</code>.
     */
    public String getOwner() {
        return (String) get(1);
    }

    /**
     * Setter for <code>public.owned_entity.project_id</code>.
     */
    public void setProjectId(Long value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.owned_entity.project_id</code>.
     */
    public Long getProjectId() {
        return (Long) get(2);
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
     * Create a detached JOwnedEntityRecord
     */
    public JOwnedEntityRecord() {
        super(JOwnedEntity.OWNED_ENTITY);
    }

    /**
     * Create a detached, initialised JOwnedEntityRecord
     */
    public JOwnedEntityRecord(Long id, String owner, Long projectId) {
        super(JOwnedEntity.OWNED_ENTITY);

        setId(id);
        setOwner(owner);
        setProjectId(projectId);
        resetChangedOnNotNull();
    }
}
