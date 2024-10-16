/*
 * This file is generated by jOOQ.
 */
package com.epam.ta.reportportal.jooq.tables;


import com.epam.ta.reportportal.jooq.JPublic;
import com.epam.ta.reportportal.jooq.Keys;
import com.epam.ta.reportportal.jooq.tables.JProject.JProjectPath;
import com.epam.ta.reportportal.jooq.tables.records.JLaunchNumberRecord;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.InverseForeignKey;
import org.jooq.Name;
import org.jooq.Path;
import org.jooq.PlainSQL;
import org.jooq.QueryPart;
import org.jooq.Record;
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
public class JLaunchNumber extends TableImpl<JLaunchNumberRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>public.launch_number</code>
     */
    public static final JLaunchNumber LAUNCH_NUMBER = new JLaunchNumber();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<JLaunchNumberRecord> getRecordType() {
        return JLaunchNumberRecord.class;
    }

    /**
     * The column <code>public.launch_number.id</code>.
     */
    public final TableField<JLaunchNumberRecord, Long> ID = createField(DSL.name("id"), SQLDataType.BIGINT.nullable(false).identity(true), this, "");

    /**
     * The column <code>public.launch_number.project_id</code>.
     */
    public final TableField<JLaunchNumberRecord, Long> PROJECT_ID = createField(DSL.name("project_id"), SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>public.launch_number.launch_name</code>.
     */
    public final TableField<JLaunchNumberRecord, String> LAUNCH_NAME = createField(DSL.name("launch_name"), SQLDataType.VARCHAR(256).nullable(false), this, "");

    /**
     * The column <code>public.launch_number.number</code>.
     */
    public final TableField<JLaunchNumberRecord, Integer> NUMBER = createField(DSL.name("number"), SQLDataType.INTEGER.nullable(false), this, "");

    private JLaunchNumber(Name alias, Table<JLaunchNumberRecord> aliased) {
        this(alias, aliased, (Field<?>[]) null, null);
    }

    private JLaunchNumber(Name alias, Table<JLaunchNumberRecord> aliased, Field<?>[] parameters, Condition where) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table(), where);
    }

    /**
     * Create an aliased <code>public.launch_number</code> table reference
     */
    public JLaunchNumber(String alias) {
        this(DSL.name(alias), LAUNCH_NUMBER);
    }

    /**
     * Create an aliased <code>public.launch_number</code> table reference
     */
    public JLaunchNumber(Name alias) {
        this(alias, LAUNCH_NUMBER);
    }

    /**
     * Create a <code>public.launch_number</code> table reference
     */
    public JLaunchNumber() {
        this(DSL.name("launch_number"), null);
    }

    public <O extends Record> JLaunchNumber(Table<O> path, ForeignKey<O, JLaunchNumberRecord> childPath, InverseForeignKey<O, JLaunchNumberRecord> parentPath) {
        super(path, childPath, parentPath, LAUNCH_NUMBER);
    }

    /**
     * A subtype implementing {@link Path} for simplified path-based joins.
     */
    public static class JLaunchNumberPath extends JLaunchNumber implements Path<JLaunchNumberRecord> {

        private static final long serialVersionUID = 1L;
        public <O extends Record> JLaunchNumberPath(Table<O> path, ForeignKey<O, JLaunchNumberRecord> childPath, InverseForeignKey<O, JLaunchNumberRecord> parentPath) {
            super(path, childPath, parentPath);
        }
        private JLaunchNumberPath(Name alias, Table<JLaunchNumberRecord> aliased) {
            super(alias, aliased);
        }

        @Override
        public JLaunchNumberPath as(String alias) {
            return new JLaunchNumberPath(DSL.name(alias), this);
        }

        @Override
        public JLaunchNumberPath as(Name alias) {
            return new JLaunchNumberPath(alias, this);
        }

        @Override
        public JLaunchNumberPath as(Table<?> alias) {
            return new JLaunchNumberPath(alias.getQualifiedName(), this);
        }
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : JPublic.PUBLIC;
    }

    @Override
    public Identity<JLaunchNumberRecord, Long> getIdentity() {
        return (Identity<JLaunchNumberRecord, Long>) super.getIdentity();
    }

    @Override
    public UniqueKey<JLaunchNumberRecord> getPrimaryKey() {
        return Keys.LAUNCH_NUMBER_PK;
    }

    @Override
    public List<UniqueKey<JLaunchNumberRecord>> getUniqueKeys() {
        return Arrays.asList(Keys.UNQ_PROJECT_NAME);
    }

    @Override
    public List<ForeignKey<JLaunchNumberRecord, ?>> getReferences() {
        return Arrays.asList(Keys.LAUNCH_NUMBER__LAUNCH_NUMBER_PROJECT_ID_FKEY);
    }

    private transient JProjectPath _project;

    /**
     * Get the implicit join path to the <code>public.project</code> table.
     */
    public JProjectPath project() {
        if (_project == null)
            _project = new JProjectPath(this, Keys.LAUNCH_NUMBER__LAUNCH_NUMBER_PROJECT_ID_FKEY, null);

        return _project;
    }

    @Override
    public JLaunchNumber as(String alias) {
        return new JLaunchNumber(DSL.name(alias), this);
    }

    @Override
    public JLaunchNumber as(Name alias) {
        return new JLaunchNumber(alias, this);
    }

    @Override
    public JLaunchNumber as(Table<?> alias) {
        return new JLaunchNumber(alias.getQualifiedName(), this);
    }

    /**
     * Rename this table
     */
    @Override
    public JLaunchNumber rename(String name) {
        return new JLaunchNumber(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public JLaunchNumber rename(Name name) {
        return new JLaunchNumber(name, null);
    }

    /**
     * Rename this table
     */
    @Override
    public JLaunchNumber rename(Table<?> name) {
        return new JLaunchNumber(name.getQualifiedName(), null);
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public JLaunchNumber where(Condition condition) {
        return new JLaunchNumber(getQualifiedName(), aliased() ? this : null, null, condition);
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public JLaunchNumber where(Collection<? extends Condition> conditions) {
        return where(DSL.and(conditions));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public JLaunchNumber where(Condition... conditions) {
        return where(DSL.and(conditions));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public JLaunchNumber where(Field<Boolean> condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public JLaunchNumber where(SQL condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public JLaunchNumber where(@Stringly.SQL String condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public JLaunchNumber where(@Stringly.SQL String condition, Object... binds) {
        return where(DSL.condition(condition, binds));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public JLaunchNumber where(@Stringly.SQL String condition, QueryPart... parts) {
        return where(DSL.condition(condition, parts));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public JLaunchNumber whereExists(Select<?> select) {
        return where(DSL.exists(select));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public JLaunchNumber whereNotExists(Select<?> select) {
        return where(DSL.notExists(select));
    }
}
