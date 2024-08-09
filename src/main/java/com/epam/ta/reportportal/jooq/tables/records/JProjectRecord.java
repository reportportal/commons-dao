/*
 * This file is generated by jOOQ.
 */
package com.epam.ta.reportportal.jooq.tables.records;


import com.epam.ta.reportportal.jooq.tables.JProject;

import java.time.Instant;

import javax.annotation.processing.Generated;

import org.jooq.Field;
import org.jooq.JSONB;
import org.jooq.Record1;
import org.jooq.Record10;
import org.jooq.Row10;
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
public class JProjectRecord extends UpdatableRecordImpl<JProjectRecord> implements Record10<Long, String, String, Instant, JSONB, Long, Long, String, String, Instant> {

    private static final long serialVersionUID = 963926100;

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
    // Record10 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row10<Long, String, String, Instant, JSONB, Long, Long, String, String, Instant> fieldsRow() {
        return (Row10) super.fieldsRow();
    }

    @Override
    public Row10<Long, String, String, Instant, JSONB, Long, Long, String, String, Instant> valuesRow() {
        return (Row10) super.valuesRow();
    }

    @Override
    public Field<Long> field1() {
        return JProject.PROJECT.ID;
    }

    @Override
    public Field<String> field2() {
        return JProject.PROJECT.NAME;
    }

    @Override
    public Field<String> field3() {
        return JProject.PROJECT.ORGANIZATION;
    }

    @Override
    public Field<Instant> field4() {
        return JProject.PROJECT.CREATED_AT;
    }

    @Override
    public Field<JSONB> field5() {
        return JProject.PROJECT.METADATA;
    }

    @Override
    public Field<Long> field6() {
        return JProject.PROJECT.ALLOCATED_STORAGE;
    }

    @Override
    public Field<Long> field7() {
        return JProject.PROJECT.ORGANIZATION_ID;
    }

    @Override
    public Field<String> field8() {
        return JProject.PROJECT.SLUG;
    }

    @Override
    public Field<String> field9() {
        return JProject.PROJECT.KEY;
    }

    @Override
    public Field<Instant> field10() {
        return JProject.PROJECT.UPDATED_AT;
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
        return getOrganization();
    }

    @Override
    public Instant component4() {
        return getCreatedAt();
    }

    @Override
    public JSONB component5() {
        return getMetadata();
    }

    @Override
    public Long component6() {
        return getAllocatedStorage();
    }

    @Override
    public Long component7() {
        return getOrganizationId();
    }

    @Override
    public String component8() {
        return getSlug();
    }

    @Override
    public String component9() {
        return getKey();
    }

    @Override
    public Instant component10() {
        return getUpdatedAt();
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
        return getOrganization();
    }

    @Override
    public Instant value4() {
        return getCreatedAt();
    }

    @Override
    public JSONB value5() {
        return getMetadata();
    }

    @Override
    public Long value6() {
        return getAllocatedStorage();
    }

    @Override
    public Long value7() {
        return getOrganizationId();
    }

    @Override
    public String value8() {
        return getSlug();
    }

    @Override
    public String value9() {
        return getKey();
    }

    @Override
    public Instant value10() {
        return getUpdatedAt();
    }

    @Override
    public JProjectRecord value1(Long value) {
        setId(value);
        return this;
    }

    @Override
    public JProjectRecord value2(String value) {
        setName(value);
        return this;
    }

    @Override
    public JProjectRecord value3(String value) {
        setOrganization(value);
        return this;
    }

    @Override
    public JProjectRecord value4(Instant value) {
        setCreatedAt(value);
        return this;
    }

    @Override
    public JProjectRecord value5(JSONB value) {
        setMetadata(value);
        return this;
    }

    @Override
    public JProjectRecord value6(Long value) {
        setAllocatedStorage(value);
        return this;
    }

    @Override
    public JProjectRecord value7(Long value) {
        setOrganizationId(value);
        return this;
    }

    @Override
    public JProjectRecord value8(String value) {
        setSlug(value);
        return this;
    }

    @Override
    public JProjectRecord value9(String value) {
        setKey(value);
        return this;
    }

    @Override
    public JProjectRecord value10(Instant value) {
        setUpdatedAt(value);
        return this;
    }

    @Override
    public JProjectRecord values(Long value1, String value2, String value3, Instant value4, JSONB value5, Long value6, Long value7, String value8, String value9, Instant value10) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        value8(value8);
        value9(value9);
        value10(value10);
        return this;
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

        set(0, id);
        set(1, name);
        set(2, organization);
        set(3, createdAt);
        set(4, metadata);
        set(5, allocatedStorage);
        set(6, organizationId);
        set(7, slug);
        set(8, key);
        set(9, updatedAt);
    }
}
