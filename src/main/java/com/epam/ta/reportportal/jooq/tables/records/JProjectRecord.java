/*
 * This file is generated by jOOQ.
 */
package com.epam.ta.reportportal.jooq.tables.records;


import com.epam.ta.reportportal.jooq.tables.JProject;

import java.time.Instant;

import org.jooq.JSONB;
import org.jooq.Record1;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes", "this-escape" })
public class JProjectRecord extends UpdatableRecordImpl<JProjectRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>public.project.id</code>.
     */
    public void setId(Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.project.id</code>.
     */
    public Long getId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>public.project.name</code>.
     */
    public void setName(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.project.name</code>.
     */
    public String getName() {
        return (String) get(1);
    }

    /**
     * Setter for <code>public.project.project_type</code>.
     */
    public void setProjectType(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.project.project_type</code>.
     */
    public String getProjectType() {
        return (String) get(2);
    }

    /**
     * Setter for <code>public.project.organization</code>.
     */
    public void setOrganization(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>public.project.organization</code>.
     */
    public String getOrganization() {
        return (String) get(3);
    }

    /**
     * Setter for <code>public.project.creation_date</code>.
     */
    public void setCreationDate(Instant value) {
        set(4, value);
    }

    /**
     * Getter for <code>public.project.creation_date</code>.
     */
    public Instant getCreationDate() {
        return (Instant) get(4);
    }

    /**
     * Setter for <code>public.project.metadata</code>.
     */
    public void setMetadata(JSONB value) {
        set(5, value);
    }

    /**
     * Getter for <code>public.project.metadata</code>.
     */
    public JSONB getMetadata() {
        return (JSONB) get(5);
    }

    /**
     * Setter for <code>public.project.allocated_storage</code>.
     */
    public void setAllocatedStorage(Long value) {
        set(6, value);
    }

    /**
     * Getter for <code>public.project.allocated_storage</code>.
     */
    public Long getAllocatedStorage() {
        return (Long) get(6);
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
     * Create a detached JProjectRecord
     */
    public JProjectRecord() {
        super(JProject.PROJECT);
    }

    /**
     * Create a detached, initialised JProjectRecord
     */
    public JProjectRecord(Long id, String name, String projectType, String organization, Instant creationDate, JSONB metadata, Long allocatedStorage) {
        super(JProject.PROJECT);

        setId(id);
        setName(name);
        setProjectType(projectType);
        setOrganization(organization);
        setCreationDate(creationDate);
        setMetadata(metadata);
        setAllocatedStorage(allocatedStorage);
        resetChangedOnNotNull();
    }
}
