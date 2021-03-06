/*
 * This file is generated by jOOQ.
 */
package com.epam.ta.reportportal.jooq.tables.records;


import com.epam.ta.reportportal.jooq.tables.JPgpArmorHeaders;

import javax.annotation.processing.Generated;

import org.jooq.Field;
import org.jooq.Record2;
import org.jooq.Row2;
import org.jooq.impl.TableRecordImpl;


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
public class JPgpArmorHeadersRecord extends TableRecordImpl<JPgpArmorHeadersRecord> implements Record2<String, String> {

    private static final long serialVersionUID = 483081900;

    /**
     * Setter for <code>public.pgp_armor_headers.key</code>.
     */
    public void setKey(String value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.pgp_armor_headers.key</code>.
     */
    public String getKey() {
        return (String) get(0);
    }

    /**
     * Setter for <code>public.pgp_armor_headers.value</code>.
     */
    public void setValue(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.pgp_armor_headers.value</code>.
     */
    public String getValue() {
        return (String) get(1);
    }

    // -------------------------------------------------------------------------
    // Record2 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row2<String, String> fieldsRow() {
        return (Row2) super.fieldsRow();
    }

    @Override
    public Row2<String, String> valuesRow() {
        return (Row2) super.valuesRow();
    }

    @Override
    public Field<String> field1() {
        return JPgpArmorHeaders.PGP_ARMOR_HEADERS.KEY;
    }

    @Override
    public Field<String> field2() {
        return JPgpArmorHeaders.PGP_ARMOR_HEADERS.VALUE;
    }

    @Override
    public String component1() {
        return getKey();
    }

    @Override
    public String component2() {
        return getValue();
    }

    @Override
    public String value1() {
        return getKey();
    }

    @Override
    public String value2() {
        return getValue();
    }

    @Override
    public JPgpArmorHeadersRecord value1(String value) {
        setKey(value);
        return this;
    }

    @Override
    public JPgpArmorHeadersRecord value2(String value) {
        setValue(value);
        return this;
    }

    @Override
    public JPgpArmorHeadersRecord values(String value1, String value2) {
        value1(value1);
        value2(value2);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached JPgpArmorHeadersRecord
     */
    public JPgpArmorHeadersRecord() {
        super(JPgpArmorHeaders.PGP_ARMOR_HEADERS);
    }

    /**
     * Create a detached, initialised JPgpArmorHeadersRecord
     */
    public JPgpArmorHeadersRecord(String key, String value) {
        super(JPgpArmorHeaders.PGP_ARMOR_HEADERS);

        set(0, key);
        set(1, value);
    }
}
