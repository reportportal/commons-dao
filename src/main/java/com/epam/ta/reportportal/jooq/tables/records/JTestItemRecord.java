/*
 * This file is generated by jOOQ.
 */
package com.epam.ta.reportportal.jooq.tables.records;


import com.epam.ta.reportportal.jooq.enums.JTestItemTypeEnum;
import com.epam.ta.reportportal.jooq.tables.JTestItem;

import java.time.Instant;

import javax.annotation.processing.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record18;
import org.jooq.Row18;
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
public class JTestItemRecord extends UpdatableRecordImpl<JTestItemRecord> implements Record18<Long, String, String, String, JTestItemTypeEnum, Instant, String, Instant, Object, String, String, Boolean, Boolean, Boolean, Long, Long, Long, Integer> {

    private static final long serialVersionUID = 675716782;

    /**
     * Setter for <code>public.test_item.item_id</code>.
     */
    public void setItemId(Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.test_item.item_id</code>.
     */
    public Long getItemId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>public.test_item.uuid</code>.
     */
    public void setUuid(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.test_item.uuid</code>.
     */
    public String getUuid() {
        return (String) get(1);
    }

    /**
     * Setter for <code>public.test_item.name</code>.
     */
    public void setName(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.test_item.name</code>.
     */
    public String getName() {
        return (String) get(2);
    }

    /**
     * Setter for <code>public.test_item.code_ref</code>.
     */
    public void setCodeRef(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>public.test_item.code_ref</code>.
     */
    public String getCodeRef() {
        return (String) get(3);
    }

    /**
     * Setter for <code>public.test_item.type</code>.
     */
    public void setType(JTestItemTypeEnum value) {
        set(4, value);
    }

    /**
     * Getter for <code>public.test_item.type</code>.
     */
    public JTestItemTypeEnum getType() {
        return (JTestItemTypeEnum) get(4);
    }

    /**
     * Setter for <code>public.test_item.start_time</code>.
     */
    public void setStartTime(Instant value) {
        set(5, value);
    }

    /**
     * Getter for <code>public.test_item.start_time</code>.
     */
    public Instant getStartTime() {
        return (Instant) get(5);
    }

    /**
     * Setter for <code>public.test_item.description</code>.
     */
    public void setDescription(String value) {
        set(6, value);
    }

    /**
     * Getter for <code>public.test_item.description</code>.
     */
    public String getDescription() {
        return (String) get(6);
    }

    /**
     * Setter for <code>public.test_item.last_modified</code>.
     */
    public void setLastModified(Instant value) {
        set(7, value);
    }

    /**
     * Getter for <code>public.test_item.last_modified</code>.
     */
    public Instant getLastModified() {
        return (Instant) get(7);
    }

    /**
     * Setter for <code>public.test_item.path</code>.
     */
    public void setPath(Object value) {
        set(8, value);
    }

    /**
     * Getter for <code>public.test_item.path</code>.
     */
    public Object getPath() {
        return get(8);
    }

    /**
     * Setter for <code>public.test_item.unique_id</code>.
     */
    public void setUniqueId(String value) {
        set(9, value);
    }

    /**
     * Getter for <code>public.test_item.unique_id</code>.
     */
    public String getUniqueId() {
        return (String) get(9);
    }

    /**
     * Setter for <code>public.test_item.test_case_id</code>.
     */
    public void setTestCaseId(String value) {
        set(10, value);
    }

    /**
     * Getter for <code>public.test_item.test_case_id</code>.
     */
    public String getTestCaseId() {
        return (String) get(10);
    }

    /**
     * Setter for <code>public.test_item.has_children</code>.
     */
    public void setHasChildren(Boolean value) {
        set(11, value);
    }

    /**
     * Getter for <code>public.test_item.has_children</code>.
     */
    public Boolean getHasChildren() {
        return (Boolean) get(11);
    }

    /**
     * Setter for <code>public.test_item.has_retries</code>.
     */
    public void setHasRetries(Boolean value) {
        set(12, value);
    }

    /**
     * Getter for <code>public.test_item.has_retries</code>.
     */
    public Boolean getHasRetries() {
        return (Boolean) get(12);
    }

    /**
     * Setter for <code>public.test_item.has_stats</code>.
     */
    public void setHasStats(Boolean value) {
        set(13, value);
    }

    /**
     * Getter for <code>public.test_item.has_stats</code>.
     */
    public Boolean getHasStats() {
        return (Boolean) get(13);
    }

    /**
     * Setter for <code>public.test_item.parent_id</code>.
     */
    public void setParentId(Long value) {
        set(14, value);
    }

    /**
     * Getter for <code>public.test_item.parent_id</code>.
     */
    public Long getParentId() {
        return (Long) get(14);
    }

    /**
     * Setter for <code>public.test_item.retry_of</code>.
     */
    public void setRetryOf(Long value) {
        set(15, value);
    }

    /**
     * Getter for <code>public.test_item.retry_of</code>.
     */
    public Long getRetryOf() {
        return (Long) get(15);
    }

    /**
     * Setter for <code>public.test_item.launch_id</code>.
     */
    public void setLaunchId(Long value) {
        set(16, value);
    }

    /**
     * Getter for <code>public.test_item.launch_id</code>.
     */
    public Long getLaunchId() {
        return (Long) get(16);
    }

    /**
     * Setter for <code>public.test_item.test_case_hash</code>.
     */
    public void setTestCaseHash(Integer value) {
        set(17, value);
    }

    /**
     * Getter for <code>public.test_item.test_case_hash</code>.
     */
    public Integer getTestCaseHash() {
        return (Integer) get(17);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<Long> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record18 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row18<Long, String, String, String, JTestItemTypeEnum, Instant, String, Instant, Object, String, String, Boolean, Boolean, Boolean, Long, Long, Long, Integer> fieldsRow() {
        return (Row18) super.fieldsRow();
    }

    @Override
    public Row18<Long, String, String, String, JTestItemTypeEnum, Instant, String, Instant, Object, String, String, Boolean, Boolean, Boolean, Long, Long, Long, Integer> valuesRow() {
        return (Row18) super.valuesRow();
    }

    @Override
    public Field<Long> field1() {
        return JTestItem.TEST_ITEM.ITEM_ID;
    }

    @Override
    public Field<String> field2() {
        return JTestItem.TEST_ITEM.UUID;
    }

    @Override
    public Field<String> field3() {
        return JTestItem.TEST_ITEM.NAME;
    }

    @Override
    public Field<String> field4() {
        return JTestItem.TEST_ITEM.CODE_REF;
    }

    @Override
    public Field<JTestItemTypeEnum> field5() {
        return JTestItem.TEST_ITEM.TYPE;
    }

    @Override
    public Field<Instant> field6() {
        return JTestItem.TEST_ITEM.START_TIME;
    }

    @Override
    public Field<String> field7() {
        return JTestItem.TEST_ITEM.DESCRIPTION;
    }

    @Override
    public Field<Instant> field8() {
        return JTestItem.TEST_ITEM.LAST_MODIFIED;
    }

    @Override
    public Field<Object> field9() {
        return JTestItem.TEST_ITEM.PATH;
    }

    @Override
    public Field<String> field10() {
        return JTestItem.TEST_ITEM.UNIQUE_ID;
    }

    @Override
    public Field<String> field11() {
        return JTestItem.TEST_ITEM.TEST_CASE_ID;
    }

    @Override
    public Field<Boolean> field12() {
        return JTestItem.TEST_ITEM.HAS_CHILDREN;
    }

    @Override
    public Field<Boolean> field13() {
        return JTestItem.TEST_ITEM.HAS_RETRIES;
    }

    @Override
    public Field<Boolean> field14() {
        return JTestItem.TEST_ITEM.HAS_STATS;
    }

    @Override
    public Field<Long> field15() {
        return JTestItem.TEST_ITEM.PARENT_ID;
    }

    @Override
    public Field<Long> field16() {
        return JTestItem.TEST_ITEM.RETRY_OF;
    }

    @Override
    public Field<Long> field17() {
        return JTestItem.TEST_ITEM.LAUNCH_ID;
    }

    @Override
    public Field<Integer> field18() {
        return JTestItem.TEST_ITEM.TEST_CASE_HASH;
    }

    @Override
    public Long component1() {
        return getItemId();
    }

    @Override
    public String component2() {
        return getUuid();
    }

    @Override
    public String component3() {
        return getName();
    }

    @Override
    public String component4() {
        return getCodeRef();
    }

    @Override
    public JTestItemTypeEnum component5() {
        return getType();
    }

    @Override
    public Instant component6() {
        return getStartTime();
    }

    @Override
    public String component7() {
        return getDescription();
    }

    @Override
    public Instant component8() {
        return getLastModified();
    }

    @Override
    public Object component9() {
        return getPath();
    }

    @Override
    public String component10() {
        return getUniqueId();
    }

    @Override
    public String component11() {
        return getTestCaseId();
    }

    @Override
    public Boolean component12() {
        return getHasChildren();
    }

    @Override
    public Boolean component13() {
        return getHasRetries();
    }

    @Override
    public Boolean component14() {
        return getHasStats();
    }

    @Override
    public Long component15() {
        return getParentId();
    }

    @Override
    public Long component16() {
        return getRetryOf();
    }

    @Override
    public Long component17() {
        return getLaunchId();
    }

    @Override
    public Integer component18() {
        return getTestCaseHash();
    }

    @Override
    public Long value1() {
        return getItemId();
    }

    @Override
    public String value2() {
        return getUuid();
    }

    @Override
    public String value3() {
        return getName();
    }

    @Override
    public String value4() {
        return getCodeRef();
    }

    @Override
    public JTestItemTypeEnum value5() {
        return getType();
    }

    @Override
    public Instant value6() {
        return getStartTime();
    }

    @Override
    public String value7() {
        return getDescription();
    }

    @Override
    public Instant value8() {
        return getLastModified();
    }

    @Override
    public Object value9() {
        return getPath();
    }

    @Override
    public String value10() {
        return getUniqueId();
    }

    @Override
    public String value11() {
        return getTestCaseId();
    }

    @Override
    public Boolean value12() {
        return getHasChildren();
    }

    @Override
    public Boolean value13() {
        return getHasRetries();
    }

    @Override
    public Boolean value14() {
        return getHasStats();
    }

    @Override
    public Long value15() {
        return getParentId();
    }

    @Override
    public Long value16() {
        return getRetryOf();
    }

    @Override
    public Long value17() {
        return getLaunchId();
    }

    @Override
    public Integer value18() {
        return getTestCaseHash();
    }

    @Override
    public JTestItemRecord value1(Long value) {
        setItemId(value);
        return this;
    }

    @Override
    public JTestItemRecord value2(String value) {
        setUuid(value);
        return this;
    }

    @Override
    public JTestItemRecord value3(String value) {
        setName(value);
        return this;
    }

    @Override
    public JTestItemRecord value4(String value) {
        setCodeRef(value);
        return this;
    }

    @Override
    public JTestItemRecord value5(JTestItemTypeEnum value) {
        setType(value);
        return this;
    }

    @Override
    public JTestItemRecord value6(Instant value) {
        setStartTime(value);
        return this;
    }

    @Override
    public JTestItemRecord value7(String value) {
        setDescription(value);
        return this;
    }

    @Override
    public JTestItemRecord value8(Instant value) {
        setLastModified(value);
        return this;
    }

    @Override
    public JTestItemRecord value9(Object value) {
        setPath(value);
        return this;
    }

    @Override
    public JTestItemRecord value10(String value) {
        setUniqueId(value);
        return this;
    }

    @Override
    public JTestItemRecord value11(String value) {
        setTestCaseId(value);
        return this;
    }

    @Override
    public JTestItemRecord value12(Boolean value) {
        setHasChildren(value);
        return this;
    }

    @Override
    public JTestItemRecord value13(Boolean value) {
        setHasRetries(value);
        return this;
    }

    @Override
    public JTestItemRecord value14(Boolean value) {
        setHasStats(value);
        return this;
    }

    @Override
    public JTestItemRecord value15(Long value) {
        setParentId(value);
        return this;
    }

    @Override
    public JTestItemRecord value16(Long value) {
        setRetryOf(value);
        return this;
    }

    @Override
    public JTestItemRecord value17(Long value) {
        setLaunchId(value);
        return this;
    }

    @Override
    public JTestItemRecord value18(Integer value) {
        setTestCaseHash(value);
        return this;
    }

    @Override
    public JTestItemRecord values(Long value1, String value2, String value3, String value4, JTestItemTypeEnum value5, Instant value6, String value7, Instant value8, Object value9, String value10, String value11, Boolean value12, Boolean value13, Boolean value14, Long value15, Long value16, Long value17, Integer value18) {
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
        value11(value11);
        value12(value12);
        value13(value13);
        value14(value14);
        value15(value15);
        value16(value16);
        value17(value17);
        value18(value18);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached JTestItemRecord
     */
    public JTestItemRecord() {
        super(JTestItem.TEST_ITEM);
    }

    /**
     * Create a detached, initialised JTestItemRecord
     */
    public JTestItemRecord(Long itemId, String uuid, String name, String codeRef, JTestItemTypeEnum type, Instant startTime, String description, Instant lastModified, Object path, String uniqueId, String testCaseId, Boolean hasChildren, Boolean hasRetries, Boolean hasStats, Long parentId, Long retryOf, Long launchId, Integer testCaseHash) {
        super(JTestItem.TEST_ITEM);

        set(0, itemId);
        set(1, uuid);
        set(2, name);
        set(3, codeRef);
        set(4, type);
        set(5, startTime);
        set(6, description);
        set(7, lastModified);
        set(8, path);
        set(9, uniqueId);
        set(10, testCaseId);
        set(11, hasChildren);
        set(12, hasRetries);
        set(13, hasStats);
        set(14, parentId);
        set(15, retryOf);
        set(16, launchId);
        set(17, testCaseHash);
    }
}
