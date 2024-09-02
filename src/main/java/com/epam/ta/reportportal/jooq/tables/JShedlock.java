/*
 * This file is generated by jOOQ.
 */
package com.epam.ta.reportportal.jooq.tables;


import com.epam.ta.reportportal.dao.converters.JooqInstantConverter;
import com.epam.ta.reportportal.jooq.Indexes;
import com.epam.ta.reportportal.jooq.JPublic;
import com.epam.ta.reportportal.jooq.Keys;
import com.epam.ta.reportportal.jooq.tables.records.JShedlockRecord;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import javax.annotation.processing.Generated;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Index;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row4;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;


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
public class JShedlock extends TableImpl<JShedlockRecord> {

    private static final long serialVersionUID = -2077089538;

    /**
     * The reference instance of <code>public.shedlock</code>
     */
    public static final JShedlock SHEDLOCK = new JShedlock();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<JShedlockRecord> getRecordType() {
        return JShedlockRecord.class;
    }

    /**
     * The column <code>public.shedlock.name</code>.
     */
    public final TableField<JShedlockRecord, String> NAME = createField(DSL.name("name"), org.jooq.impl.SQLDataType.VARCHAR(64).nullable(false), this, "");

    /**
     * The column <code>public.shedlock.lock_until</code>.
     */
    public final TableField<JShedlockRecord, Instant> LOCK_UNTIL = createField(DSL.name("lock_until"), org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false), this, "", new JooqInstantConverter());

    /**
     * The column <code>public.shedlock.locked_at</code>.
     */
    public final TableField<JShedlockRecord, Instant> LOCKED_AT = createField(DSL.name("locked_at"), org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false), this, "", new JooqInstantConverter());

    /**
     * The column <code>public.shedlock.locked_by</code>.
     */
    public final TableField<JShedlockRecord, String> LOCKED_BY = createField(DSL.name("locked_by"), org.jooq.impl.SQLDataType.VARCHAR(255).nullable(false), this, "");

    /**
     * Create a <code>public.shedlock</code> table reference
     */
    public JShedlock() {
        this(DSL.name("shedlock"), null);
    }

    /**
     * Create an aliased <code>public.shedlock</code> table reference
     */
    public JShedlock(String alias) {
        this(DSL.name(alias), SHEDLOCK);
    }

    /**
     * Create an aliased <code>public.shedlock</code> table reference
     */
    public JShedlock(Name alias) {
        this(alias, SHEDLOCK);
    }

    private JShedlock(Name alias, Table<JShedlockRecord> aliased) {
        this(alias, aliased, null);
    }

    private JShedlock(Name alias, Table<JShedlockRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> JShedlock(Table<O> child, ForeignKey<O, JShedlockRecord> key) {
        super(child, key, SHEDLOCK);
    }

    @Override
    public Schema getSchema() {
        return JPublic.PUBLIC;
    }

    @Override
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.SHEDLOCK_PKEY);
    }

    @Override
    public UniqueKey<JShedlockRecord> getPrimaryKey() {
        return Keys.SHEDLOCK_PKEY;
    }

    @Override
    public List<UniqueKey<JShedlockRecord>> getKeys() {
        return Arrays.<UniqueKey<JShedlockRecord>>asList(Keys.SHEDLOCK_PKEY);
    }

    @Override
    public JShedlock as(String alias) {
        return new JShedlock(DSL.name(alias), this);
    }

    @Override
    public JShedlock as(Name alias) {
        return new JShedlock(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public JShedlock rename(String name) {
        return new JShedlock(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public JShedlock rename(Name name) {
        return new JShedlock(name, null);
    }

    // -------------------------------------------------------------------------
    // Row4 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row4<String, Instant, Instant, String> fieldsRow() {
        return (Row4) super.fieldsRow();
    }
}
