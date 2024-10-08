/*
 * This file is generated by jOOQ.
 */
package com.epam.ta.reportportal.jooq.tables.records;


import com.epam.ta.reportportal.jooq.tables.JApiKeys;

import java.sql.Date;
import java.time.Instant;

import javax.annotation.processing.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record6;
import org.jooq.Row6;
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
public class JApiKeysRecord extends UpdatableRecordImpl<JApiKeysRecord> implements Record6<Long, String, String, Instant, Long, Date> {

    private static final long serialVersionUID = 1506027464;

    /**
     * Setter for <code>public.api_keys.id</code>.
     */
    public void setId(Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.api_keys.id</code>.
     */
    public Long getId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>public.api_keys.name</code>.
     */
    public void setName(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.api_keys.name</code>.
     */
    public String getName() {
        return (String) get(1);
    }

    /**
     * Setter for <code>public.api_keys.hash</code>.
     */
    public void setHash(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.api_keys.hash</code>.
     */
    public String getHash() {
        return (String) get(2);
    }

    /**
     * Setter for <code>public.api_keys.created_at</code>.
     */
    public void setCreatedAt(Instant value) {
        set(3, value);
    }

    /**
     * Getter for <code>public.api_keys.created_at</code>.
     */
    public Instant getCreatedAt() {
        return (Instant) get(3);
    }

    /**
     * Setter for <code>public.api_keys.user_id</code>.
     */
    public void setUserId(Long value) {
        set(4, value);
    }

    /**
     * Getter for <code>public.api_keys.user_id</code>.
     */
    public Long getUserId() {
        return (Long) get(4);
    }

    /**
     * Setter for <code>public.api_keys.last_used_at</code>.
     */
    public void setLastUsedAt(Date value) {
        set(5, value);
    }

    /**
     * Getter for <code>public.api_keys.last_used_at</code>.
     */
    public Date getLastUsedAt() {
        return (Date) get(5);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<Long> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record6 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row6<Long, String, String, Instant, Long, Date> fieldsRow() {
        return (Row6) super.fieldsRow();
    }

    @Override
    public Row6<Long, String, String, Instant, Long, Date> valuesRow() {
        return (Row6) super.valuesRow();
    }

    @Override
    public Field<Long> field1() {
        return JApiKeys.API_KEYS.ID;
    }

    @Override
    public Field<String> field2() {
        return JApiKeys.API_KEYS.NAME;
    }

    @Override
    public Field<String> field3() {
        return JApiKeys.API_KEYS.HASH;
    }

    @Override
    public Field<Instant> field4() {
        return JApiKeys.API_KEYS.CREATED_AT;
    }

    @Override
    public Field<Long> field5() {
        return JApiKeys.API_KEYS.USER_ID;
    }

    @Override
    public Field<Date> field6() {
        return JApiKeys.API_KEYS.LAST_USED_AT;
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
        return getHash();
    }

    @Override
    public Instant component4() {
        return getCreatedAt();
    }

    @Override
    public Long component5() {
        return getUserId();
    }

    @Override
    public Date component6() {
        return getLastUsedAt();
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
        return getHash();
    }

    @Override
    public Instant value4() {
        return getCreatedAt();
    }

    @Override
    public Long value5() {
        return getUserId();
    }

    @Override
    public Date value6() {
        return getLastUsedAt();
    }

    @Override
    public JApiKeysRecord value1(Long value) {
        setId(value);
        return this;
    }

    @Override
    public JApiKeysRecord value2(String value) {
        setName(value);
        return this;
    }

    @Override
    public JApiKeysRecord value3(String value) {
        setHash(value);
        return this;
    }

    @Override
    public JApiKeysRecord value4(Instant value) {
        setCreatedAt(value);
        return this;
    }

    @Override
    public JApiKeysRecord value5(Long value) {
        setUserId(value);
        return this;
    }

    @Override
    public JApiKeysRecord value6(Date value) {
        setLastUsedAt(value);
        return this;
    }

    @Override
    public JApiKeysRecord values(Long value1, String value2, String value3, Instant value4, Long value5, Date value6) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached JApiKeysRecord
     */
    public JApiKeysRecord() {
        super(JApiKeys.API_KEYS);
    }

    /**
     * Create a detached, initialised JApiKeysRecord
     */
    public JApiKeysRecord(Long id, String name, String hash, Instant createdAt, Long userId, Date lastUsedAt) {
        super(JApiKeys.API_KEYS);

        set(0, id);
        set(1, name);
        set(2, hash);
        set(3, createdAt);
        set(4, userId);
        set(5, lastUsedAt);
    }
}
