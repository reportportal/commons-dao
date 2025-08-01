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
     * Setter for <code>public.project.organization</code>.
     */
    public void setOrganization(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.project.organization</code>.
     */
    public String getOrganization() {
        return (String) get(2);
    }

    /**
     * Setter for <code>public.project.created_at</code>.
     */
    public void setCreatedAt(Instant value) {
        set(3, value);
    }

    /**
     * Getter for <code>public.project.created_at</code>.
     */
    public Instant getCreatedAt() {
        return (Instant) get(3);
    }

    /**
     * Setter for <code>public.project.metadata</code>.
     */
    public void setMetadata(JSONB value) {
        set(4, value);
    }

    /**
     * Getter for <code>public.project.metadata</code>.
     */
    public JSONB getMetadata() {
        return (JSONB) get(4);
    }

    /**
     * Setter for <code>public.project.allocated_storage</code>.
     */
    public void setAllocatedStorage(Long value) {
        set(5, value);
    }

    /**
     * Getter for <code>public.project.allocated_storage</code>.
     */
    public Long getAllocatedStorage() {
        return (Long) get(5);
    }

    /**
     * Setter for <code>public.project.organization_id</code>.
     */
    public void setOrganizationId(Long value) {
        set(6, value);
    }

    /**
     * Getter for <code>public.project.organization_id</code>.
     */
    public Long getOrganizationId() {
        return (Long) get(6);
    }

    /**
     * Setter for <code>public.project.slug</code>.
     */
    public void setSlug(String value) {
        set(7, value);
    }

    /**
     * Getter for <code>public.project.slug</code>.
     */
    public String getSlug() {
        return (String) get(7);
    }

    /**
     * Setter for <code>public.project.key</code>.
     */
    public void setKey(String value) {
        set(8, value);
    }

    /**
     * Getter for <code>public.project.key</code>.
     */
    public String getKey() {
        return (String) get(8);
    }

    /**
     * Setter for <code>public.project.updated_at</code>.
     */
    public void setUpdatedAt(Instant value) {
        set(9, value);
    }

    /**
     * Getter for <code>public.project.updated_at</code>.
     */
    public Instant getUpdatedAt() {
        return (Instant) get(9);
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
    public JProjectRecord(Long id, String name, String organization, Instant createdAt, JSONB metadata, Long allocatedStorage, Long organizationId, String slug, String key, Instant updatedAt) {
        super(JProject.PROJECT);

        setId(id);
        setName(name);
        setOrganization(organization);
        setCreatedAt(createdAt);
        setMetadata(metadata);
        setAllocatedStorage(allocatedStorage);
        setOrganizationId(organizationId);
        setSlug(slug);
        setKey(key);
        setUpdatedAt(updatedAt);
        resetChangedOnNotNull();
    }
}
