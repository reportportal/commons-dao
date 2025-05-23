/*
 * This file is generated by jOOQ.
 */
package com.epam.ta.reportportal.jooq.tables.records;


import com.epam.ta.reportportal.jooq.tables.JParameter;

import org.jooq.impl.TableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes", "this-escape" })
public class JParameterRecord extends TableRecordImpl<JParameterRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>public.parameter.item_id</code>.
     */
    public void setItemId(Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.parameter.item_id</code>.
     */
    public Long getItemId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>public.parameter.key</code>.
     */
    public void setKey(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.parameter.key</code>.
     */
    public String getKey() {
        return (String) get(1);
    }

    /**
     * Setter for <code>public.parameter.value</code>.
     */
    public void setValue(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.parameter.value</code>.
     */
    public String getValue() {
        return (String) get(2);
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached JParameterRecord
     */
    public JParameterRecord() {
        super(JParameter.PARAMETER);
    }

    /**
     * Create a detached, initialised JParameterRecord
     */
    public JParameterRecord(Long itemId, String key, String value) {
        super(JParameter.PARAMETER);

        setItemId(itemId);
        setKey(key);
        setValue(value);
        resetChangedOnNotNull();
    }
}
