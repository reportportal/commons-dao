/*
 * This file is generated by jOOQ.
 */
package com.epam.ta.reportportal.jooq.tables;


import com.epam.ta.reportportal.dao.converters.JooqInstantConverter;
import com.epam.ta.reportportal.jooq.JPublic;
import com.epam.ta.reportportal.jooq.Keys;
import com.epam.ta.reportportal.jooq.tables.records.JShedlockRecord;

import java.time.Instant;
import java.util.Collection;

import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.Name;
import org.jooq.PlainSQL;
import org.jooq.QueryPart;
import org.jooq.SQL;
import org.jooq.Schema;
import org.jooq.Select;
import org.jooq.Stringly;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes", "this-escape" })
public class JShedlock extends TableImpl<JShedlockRecord> {

    private static final long serialVersionUID = 1L;

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
    public final TableField<JShedlockRecord, String> NAME = createField(DSL.name("name"), SQLDataType.VARCHAR(64).nullable(false), this, "");

    /**
     * The column <code>public.shedlock.lock_until</code>.
     */
    public final TableField<JShedlockRecord, Instant> LOCK_UNTIL = createField(DSL.name("lock_until"), SQLDataType.LOCALDATETIME(6).nullable(false), this, "", new JooqInstantConverter());

    /**
     * The column <code>public.shedlock.locked_at</code>.
     */
    public final TableField<JShedlockRecord, Instant> LOCKED_AT = createField(DSL.name("locked_at"), SQLDataType.LOCALDATETIME(6).nullable(false), this, "", new JooqInstantConverter());

    /**
     * The column <code>public.shedlock.locked_by</code>.
     */
    public final TableField<JShedlockRecord, String> LOCKED_BY = createField(DSL.name("locked_by"), SQLDataType.VARCHAR(255).nullable(false), this, "");

    private JShedlock(Name alias, Table<JShedlockRecord> aliased) {
        this(alias, aliased, (Field<?>[]) null, null);
    }

    private JShedlock(Name alias, Table<JShedlockRecord> aliased, Field<?>[] parameters, Condition where) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table(), where);
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

    /**
     * Create a <code>public.shedlock</code> table reference
     */
    public JShedlock() {
        this(DSL.name("shedlock"), null);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : JPublic.PUBLIC;
    }

    @Override
    public UniqueKey<JShedlockRecord> getPrimaryKey() {
        return Keys.SHEDLOCK_PKEY;
    }

    @Override
    public JShedlock as(String alias) {
        return new JShedlock(DSL.name(alias), this);
    }

    @Override
    public JShedlock as(Name alias) {
        return new JShedlock(alias, this);
    }

    @Override
    public JShedlock as(Table<?> alias) {
        return new JShedlock(alias.getQualifiedName(), this);
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

    /**
     * Rename this table
     */
    @Override
    public JShedlock rename(Table<?> name) {
        return new JShedlock(name.getQualifiedName(), null);
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public JShedlock where(Condition condition) {
        return new JShedlock(getQualifiedName(), aliased() ? this : null, null, condition);
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public JShedlock where(Collection<? extends Condition> conditions) {
        return where(DSL.and(conditions));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public JShedlock where(Condition... conditions) {
        return where(DSL.and(conditions));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public JShedlock where(Field<Boolean> condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public JShedlock where(SQL condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public JShedlock where(@Stringly.SQL String condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public JShedlock where(@Stringly.SQL String condition, Object... binds) {
        return where(DSL.condition(condition, binds));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public JShedlock where(@Stringly.SQL String condition, QueryPart... parts) {
        return where(DSL.condition(condition, parts));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public JShedlock whereExists(Select<?> select) {
        return where(DSL.exists(select));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public JShedlock whereNotExists(Select<?> select) {
        return where(DSL.notExists(select));
    }
}
