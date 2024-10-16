/*
 * This file is generated by jOOQ.
 */
package com.epam.ta.reportportal.jooq.tables.records;


import com.epam.ta.reportportal.jooq.enums.JFilterConditionEnum;
import com.epam.ta.reportportal.jooq.tables.JFilterCondition;

import org.jooq.Record1;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes", "this-escape" })
public class JFilterConditionRecord extends UpdatableRecordImpl<JFilterConditionRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>public.filter_condition.id</code>.
     */
    public void setId(Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.filter_condition.id</code>.
     */
    public Long getId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>public.filter_condition.filter_id</code>.
     */
    public void setFilterId(Long value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.filter_condition.filter_id</code>.
     */
    public Long getFilterId() {
        return (Long) get(1);
    }

    /**
     * Setter for <code>public.filter_condition.condition</code>.
     */
    public void setCondition(JFilterConditionEnum value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.filter_condition.condition</code>.
     */
    public JFilterConditionEnum getCondition() {
        return (JFilterConditionEnum) get(2);
    }

    /**
     * Setter for <code>public.filter_condition.value</code>.
     */
    public void setValue(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>public.filter_condition.value</code>.
     */
    public String getValue() {
        return (String) get(3);
    }

    /**
     * Setter for <code>public.filter_condition.search_criteria</code>.
     */
    public void setSearchCriteria(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>public.filter_condition.search_criteria</code>.
     */
    public String getSearchCriteria() {
        return (String) get(4);
    }

    /**
     * Setter for <code>public.filter_condition.negative</code>.
     */
    public void setNegative(Boolean value) {
        set(5, value);
    }

    /**
     * Getter for <code>public.filter_condition.negative</code>.
     */
    public Boolean getNegative() {
        return (Boolean) get(5);
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
     * Create a detached JFilterConditionRecord
     */
    public JFilterConditionRecord() {
        super(JFilterCondition.FILTER_CONDITION);
    }

    /**
     * Create a detached, initialised JFilterConditionRecord
     */
    public JFilterConditionRecord(Long id, Long filterId, JFilterConditionEnum condition, String value, String searchCriteria, Boolean negative) {
        super(JFilterCondition.FILTER_CONDITION);

        setId(id);
        setFilterId(filterId);
        setCondition(condition);
        setValue(value);
        setSearchCriteria(searchCriteria);
        setNegative(negative);
        resetChangedOnNotNull();
    }
}
