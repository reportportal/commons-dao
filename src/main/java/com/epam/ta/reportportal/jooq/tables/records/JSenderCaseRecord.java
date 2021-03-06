/*
 * This file is generated by jOOQ.
 */
package com.epam.ta.reportportal.jooq.tables.records;


import com.epam.ta.reportportal.jooq.tables.JSenderCase;

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
        "jOOQ version:3.12.4"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class JSenderCaseRecord extends UpdatableRecordImpl<JSenderCaseRecord> implements Record4<Long, String, Long, Boolean> {

    private static final long serialVersionUID = 794748140;

    /**
     * Setter for <code>public.sender_case.id</code>.
     */
    public void setId(Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.sender_case.id</code>.
     */
    public Long getId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>public.sender_case.send_case</code>.
     */
    public void setSendCase(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.sender_case.send_case</code>.
     */
    public String getSendCase() {
        return (String) get(1);
    }

    /**
     * Setter for <code>public.sender_case.project_id</code>.
     */
    public void setProjectId(Long value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.sender_case.project_id</code>.
     */
    public Long getProjectId() {
        return (Long) get(2);
    }

    /**
     * Setter for <code>public.sender_case.enabled</code>.
     */
    public void setEnabled(Boolean value) {
        set(3, value);
    }

    /**
     * Getter for <code>public.sender_case.enabled</code>.
     */
    public Boolean getEnabled() {
        return (Boolean) get(3);
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
    public Row4<Long, String, Long, Boolean> fieldsRow() {
        return (Row4) super.fieldsRow();
    }

    @Override
    public Row4<Long, String, Long, Boolean> valuesRow() {
        return (Row4) super.valuesRow();
    }

    @Override
    public Field<Long> field1() {
        return JSenderCase.SENDER_CASE.ID;
    }

    @Override
    public Field<String> field2() {
        return JSenderCase.SENDER_CASE.SEND_CASE;
    }

    @Override
    public Field<Long> field3() {
        return JSenderCase.SENDER_CASE.PROJECT_ID;
    }

    @Override
    public Field<Boolean> field4() {
        return JSenderCase.SENDER_CASE.ENABLED;
    }

    @Override
    public Long component1() {
        return getId();
    }

    @Override
    public String component2() {
        return getSendCase();
    }

    @Override
    public Long component3() {
        return getProjectId();
    }

    @Override
    public Boolean component4() {
        return getEnabled();
    }

    @Override
    public Long value1() {
        return getId();
    }

    @Override
    public String value2() {
        return getSendCase();
    }

    @Override
    public Long value3() {
        return getProjectId();
    }

    @Override
    public Boolean value4() {
        return getEnabled();
    }

    @Override
    public JSenderCaseRecord value1(Long value) {
        setId(value);
        return this;
    }

    @Override
    public JSenderCaseRecord value2(String value) {
        setSendCase(value);
        return this;
    }

    @Override
    public JSenderCaseRecord value3(Long value) {
        setProjectId(value);
        return this;
    }

    @Override
    public JSenderCaseRecord value4(Boolean value) {
        setEnabled(value);
        return this;
    }

    @Override
    public JSenderCaseRecord values(Long value1, String value2, Long value3, Boolean value4) {
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
     * Create a detached JSenderCaseRecord
     */
    public JSenderCaseRecord() {
        super(JSenderCase.SENDER_CASE);
    }

    /**
     * Create a detached, initialised JSenderCaseRecord
     */
    public JSenderCaseRecord(Long id, String sendCase, Long projectId, Boolean enabled) {
        super(JSenderCase.SENDER_CASE);

        set(0, id);
        set(1, sendCase);
        set(2, projectId);
        set(3, enabled);
    }
}
