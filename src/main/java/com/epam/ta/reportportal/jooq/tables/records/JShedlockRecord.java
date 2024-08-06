/*
 * This file is generated by jOOQ.
 */
package com.epam.ta.reportportal.jooq.tables.records;


import com.epam.ta.reportportal.jooq.tables.JShedlock;

import java.time.Instant;

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
public class JShedlockRecord extends UpdatableRecordImpl<JShedlockRecord> implements Record4<String, Instant, Instant, String> {

    private static final long serialVersionUID = -1480718162;

    /**
     * Setter for <code>public.shedlock.name</code>.
     */
    public void setName(String value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.shedlock.name</code>.
     */
    public String getName() {
        return (String) get(0);
    }

    /**
     * Setter for <code>public.shedlock.lock_until</code>.
     */
    public void setLockUntil(Instant value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.shedlock.lock_until</code>.
     */
    public Instant getLockUntil() {
        return (Instant) get(1);
    }

    /**
     * Setter for <code>public.shedlock.locked_at</code>.
     */
    public void setLockedAt(Instant value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.shedlock.locked_at</code>.
     */
    public Instant getLockedAt() {
        return (Instant) get(2);
    }

    /**
     * Setter for <code>public.shedlock.locked_by</code>.
     */
    public void setLockedBy(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>public.shedlock.locked_by</code>.
     */
    public String getLockedBy() {
        return (String) get(3);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<String> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record4 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row4<String, Instant, Instant, String> fieldsRow() {
        return (Row4) super.fieldsRow();
    }

    @Override
    public Row4<String, Instant, Instant, String> valuesRow() {
        return (Row4) super.valuesRow();
    }

    @Override
    public Field<String> field1() {
        return JShedlock.SHEDLOCK.NAME;
    }

    @Override
    public Field<Instant> field2() {
        return JShedlock.SHEDLOCK.LOCK_UNTIL;
    }

    @Override
    public Field<Instant> field3() {
        return JShedlock.SHEDLOCK.LOCKED_AT;
    }

    @Override
    public Field<String> field4() {
        return JShedlock.SHEDLOCK.LOCKED_BY;
    }

    @Override
    public String component1() {
        return getName();
    }

    @Override
    public Instant component2() {
        return getLockUntil();
    }

    @Override
    public Instant component3() {
        return getLockedAt();
    }

    @Override
    public String component4() {
        return getLockedBy();
    }

    @Override
    public String value1() {
        return getName();
    }

    @Override
    public Instant value2() {
        return getLockUntil();
    }

    @Override
    public Instant value3() {
        return getLockedAt();
    }

    @Override
    public String value4() {
        return getLockedBy();
    }

    @Override
    public JShedlockRecord value1(String value) {
        setName(value);
        return this;
    }

    @Override
    public JShedlockRecord value2(Instant value) {
        setLockUntil(value);
        return this;
    }

    @Override
    public JShedlockRecord value3(Instant value) {
        setLockedAt(value);
        return this;
    }

    @Override
    public JShedlockRecord value4(String value) {
        setLockedBy(value);
        return this;
    }

    @Override
    public JShedlockRecord values(String value1, Instant value2, Instant value3, String value4) {
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
     * Create a detached JShedlockRecord
     */
    public JShedlockRecord() {
        super(JShedlock.SHEDLOCK);
    }

    /**
     * Create a detached, initialised JShedlockRecord
     */
    public JShedlockRecord(String name, Instant lockUntil, Instant lockedAt, String lockedBy) {
        super(JShedlock.SHEDLOCK);

        set(0, name);
        set(1, lockUntil);
        set(2, lockedAt);
        set(3, lockedBy);
    }
}
