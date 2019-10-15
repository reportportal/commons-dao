/*
 * This file is generated by jOOQ.
 */
package com.epam.ta.reportportal.jooq.tables.records;


import com.epam.ta.reportportal.jooq.tables.JProjectAttribute;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record2;
import org.jooq.Record3;
import org.jooq.Row3;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.11.11"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class JProjectAttributeRecord extends UpdatableRecordImpl<JProjectAttributeRecord> implements Record3<Long, String, Long> {

    private static final long serialVersionUID = 1341017906;

    /**
     * Setter for <code>public.project_attribute.attribute_id</code>.
     */
    public void setAttributeId(Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.project_attribute.attribute_id</code>.
     */
    public Long getAttributeId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>public.project_attribute.value</code>.
     */
    public void setValue(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.project_attribute.value</code>.
     */
    public String getValue() {
        return (String) get(1);
    }

    /**
     * Setter for <code>public.project_attribute.project_id</code>.
     */
    public void setProjectId(Long value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.project_attribute.project_id</code>.
     */
    public Long getProjectId() {
        return (Long) get(2);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Record2<Long, Long> key() {
        return (Record2) super.key();
    }

    // -------------------------------------------------------------------------
    // Record3 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row3<Long, String, Long> fieldsRow() {
        return (Row3) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row3<Long, String, Long> valuesRow() {
        return (Row3) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field1() {
        return JProjectAttribute.PROJECT_ATTRIBUTE.ATTRIBUTE_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return JProjectAttribute.PROJECT_ATTRIBUTE.VALUE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field3() {
        return JProjectAttribute.PROJECT_ATTRIBUTE.PROJECT_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long component1() {
        return getAttributeId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component2() {
        return getValue();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long component3() {
        return getProjectId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long value1() {
        return getAttributeId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value2() {
        return getValue();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long value3() {
        return getProjectId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JProjectAttributeRecord value1(Long value) {
        setAttributeId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JProjectAttributeRecord value2(String value) {
        setValue(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JProjectAttributeRecord value3(Long value) {
        setProjectId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JProjectAttributeRecord values(Long value1, String value2, Long value3) {
        value1(value1);
        value2(value2);
        value3(value3);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached JProjectAttributeRecord
     */
    public JProjectAttributeRecord() {
        super(JProjectAttribute.PROJECT_ATTRIBUTE);
    }

    /**
     * Create a detached, initialised JProjectAttributeRecord
     */
    public JProjectAttributeRecord(Long attributeId, String value, Long projectId) {
        super(JProjectAttribute.PROJECT_ATTRIBUTE);

        set(0, attributeId);
        set(1, value);
        set(2, projectId);
    }
}
