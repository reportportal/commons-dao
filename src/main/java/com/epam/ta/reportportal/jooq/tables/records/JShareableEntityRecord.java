/*
 * This file is generated by jOOQ.
 */
package com.epam.ta.reportportal.jooq.tables.records;


import com.epam.ta.reportportal.jooq.tables.JShareableEntity;

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
        "jOOQ version:3.12.3"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class JShareableEntityRecord extends UpdatableRecordImpl<JShareableEntityRecord> implements Record4<Long, Boolean, String, Long> {

    private static final long serialVersionUID = 905114;

    /**
     * Setter for <code>public.shareable_entity.id</code>.
     */
    public void setId(Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.shareable_entity.id</code>.
     */
    public Long getId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>public.shareable_entity.shared</code>.
     */
    public void setShared(Boolean value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.shareable_entity.shared</code>.
     */
    public Boolean getShared() {
        return (Boolean) get(1);
    }

    /**
     * Setter for <code>public.shareable_entity.owner</code>.
     */
    public void setOwner(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.shareable_entity.owner</code>.
     */
    public String getOwner() {
        return (String) get(2);
    }

    /**
     * Setter for <code>public.shareable_entity.project_id</code>.
     */
    public void setProjectId(Long value) {
        set(3, value);
    }

    /**
     * Getter for <code>public.shareable_entity.project_id</code>.
     */
    public Long getProjectId() {
        return (Long) get(3);
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
    public Row4<Long, Boolean, String, Long> fieldsRow() {
        return (Row4) super.fieldsRow();
    }

    @Override
    public Row4<Long, Boolean, String, Long> valuesRow() {
        return (Row4) super.valuesRow();
    }

    @Override
    public Field<Long> field1() {
        return JShareableEntity.SHAREABLE_ENTITY.ID;
    }

    @Override
    public Field<Boolean> field2() {
        return JShareableEntity.SHAREABLE_ENTITY.SHARED;
    }

    @Override
    public Field<String> field3() {
        return JShareableEntity.SHAREABLE_ENTITY.OWNER;
    }

    @Override
    public Field<Long> field4() {
        return JShareableEntity.SHAREABLE_ENTITY.PROJECT_ID;
    }

    @Override
    public Long component1() {
        return getId();
    }

    @Override
    public Boolean component2() {
        return getShared();
    }

    @Override
    public String component3() {
        return getOwner();
    }

    @Override
    public Long component4() {
        return getProjectId();
    }

    @Override
    public Long value1() {
        return getId();
    }

    @Override
    public Boolean value2() {
        return getShared();
    }

    @Override
    public String value3() {
        return getOwner();
    }

    @Override
    public Long value4() {
        return getProjectId();
    }

    @Override
    public JShareableEntityRecord value1(Long value) {
        setId(value);
        return this;
    }

    @Override
    public JShareableEntityRecord value2(Boolean value) {
        setShared(value);
        return this;
    }

    @Override
    public JShareableEntityRecord value3(String value) {
        setOwner(value);
        return this;
    }

    @Override
    public JShareableEntityRecord value4(Long value) {
        setProjectId(value);
        return this;
    }

    @Override
    public JShareableEntityRecord values(Long value1, Boolean value2, String value3, Long value4) {
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
     * Create a detached JShareableEntityRecord
     */
    public JShareableEntityRecord() {
        super(JShareableEntity.SHAREABLE_ENTITY);
    }

    /**
     * Create a detached, initialised JShareableEntityRecord
     */
    public JShareableEntityRecord(Long id, Boolean shared, String owner, Long projectId) {
        super(JShareableEntity.SHAREABLE_ENTITY);

        set(0, id);
        set(1, shared);
        set(2, owner);
        set(3, projectId);
    }
}
