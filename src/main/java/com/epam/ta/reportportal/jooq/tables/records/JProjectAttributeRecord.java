/*
 * This file is generated by jOOQ.
 */
package com.epam.ta.reportportal.jooq.tables.records;


import com.epam.ta.reportportal.jooq.tables.JProjectAttribute;

import org.jooq.Record2;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes", "this-escape" })
public class JProjectAttributeRecord extends UpdatableRecordImpl<JProjectAttributeRecord> {

    private static final long serialVersionUID = 1L;

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

    @Override
    public Record2<Long, Long> key() {
        return (Record2) super.key();
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

        setAttributeId(attributeId);
        setValue(value);
        setProjectId(projectId);
        resetChangedOnNotNull();
    }
}
