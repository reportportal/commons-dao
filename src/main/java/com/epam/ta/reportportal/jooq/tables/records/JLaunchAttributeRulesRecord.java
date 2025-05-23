/*
 * This file is generated by jOOQ.
 */
package com.epam.ta.reportportal.jooq.tables.records;


import com.epam.ta.reportportal.jooq.tables.JLaunchAttributeRules;

import org.jooq.Record1;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes", "this-escape" })
public class JLaunchAttributeRulesRecord extends UpdatableRecordImpl<JLaunchAttributeRulesRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>public.launch_attribute_rules.id</code>.
     */
    public void setId(Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.launch_attribute_rules.id</code>.
     */
    public Long getId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>public.launch_attribute_rules.sender_case_id</code>.
     */
    public void setSenderCaseId(Long value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.launch_attribute_rules.sender_case_id</code>.
     */
    public Long getSenderCaseId() {
        return (Long) get(1);
    }

    /**
     * Setter for <code>public.launch_attribute_rules.key</code>.
     */
    public void setKey(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.launch_attribute_rules.key</code>.
     */
    public String getKey() {
        return (String) get(2);
    }

    /**
     * Setter for <code>public.launch_attribute_rules.value</code>.
     */
    public void setValue(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>public.launch_attribute_rules.value</code>.
     */
    public String getValue() {
        return (String) get(3);
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
     * Create a detached JLaunchAttributeRulesRecord
     */
    public JLaunchAttributeRulesRecord() {
        super(JLaunchAttributeRules.LAUNCH_ATTRIBUTE_RULES);
    }

    /**
     * Create a detached, initialised JLaunchAttributeRulesRecord
     */
    public JLaunchAttributeRulesRecord(Long id, Long senderCaseId, String key, String value) {
        super(JLaunchAttributeRules.LAUNCH_ATTRIBUTE_RULES);

        setId(id);
        setSenderCaseId(senderCaseId);
        setKey(key);
        setValue(value);
        resetChangedOnNotNull();
    }
}
