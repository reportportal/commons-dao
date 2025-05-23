/*
 * This file is generated by jOOQ.
 */
package com.epam.ta.reportportal.jooq.tables.records;


import com.epam.ta.reportportal.jooq.tables.JPgpArmorHeaders;

import org.jooq.impl.TableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes", "this-escape" })
public class JPgpArmorHeadersRecord extends TableRecordImpl<JPgpArmorHeadersRecord> {

    private static final long serialVersionUID = 1L;

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

        setKey(key);
        setValue(value);
        resetChangedOnNotNull();
    }
}
