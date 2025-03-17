/*
 * This file is generated by jOOQ.
 */
package com.epam.ta.reportportal.jooq.tables;


import com.epam.ta.reportportal.jooq.Indexes;
import com.epam.ta.reportportal.jooq.JPublic;
import com.epam.ta.reportportal.jooq.Keys;
import com.epam.ta.reportportal.jooq.tables.JSenderCase.JSenderCasePath;
import com.epam.ta.reportportal.jooq.tables.records.JLaunchNamesRecord;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Index;
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
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes", "this-escape" })
public class JLaunchNames extends TableImpl<JLaunchNamesRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>public.launch_names</code>
     */
    public static final JLaunchNames LAUNCH_NAMES = new JLaunchNames();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<JLaunchNamesRecord> getRecordType() {
        return JLaunchNamesRecord.class;
    }

    /**
     * The column <code>public.launch_names.sender_case_id</code>.
     */
    public final TableField<JLaunchNamesRecord, Long> SENDER_CASE_ID = createField(DSL.name("sender_case_id"), SQLDataType.BIGINT, this, "");

    /**
     * The column <code>public.launch_names.launch_name</code>.
     */
    public final TableField<JLaunchNamesRecord, String> LAUNCH_NAME = createField(DSL.name("launch_name"), SQLDataType.VARCHAR(256), this, "");

    private JLaunchNames(Name alias, Table<JLaunchNamesRecord> aliased) {
        this(alias, aliased, (Field<?>[]) null, null);
    }

    private JLaunchNames(Name alias, Table<JLaunchNamesRecord> aliased, Field<?>[] parameters, Condition where) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table(), where);
    }

    /**
     * Create an aliased <code>public.launch_names</code> table reference
     */
    public JLaunchNames(String alias) {
        this(DSL.name(alias), LAUNCH_NAMES);
    }

    /**
     * Create an aliased <code>public.launch_names</code> table reference
     */
    public JLaunchNames(Name alias) {
        this(alias, LAUNCH_NAMES);
    }

    /**
     * Create a <code>public.launch_names</code> table reference
     */
    public JLaunchNames() {
        this(DSL.name("launch_names"), null);
    }

    public <O extends Record> JLaunchNames(Table<O> path, ForeignKey<O, JLaunchNamesRecord> childPath, InverseForeignKey<O, JLaunchNamesRecord> parentPath) {
        super(path, childPath, parentPath, LAUNCH_NAMES);
    }

    /**
     * A subtype implementing {@link Path} for simplified path-based joins.
     */
    public static class JLaunchNamesPath extends JLaunchNames implements Path<JLaunchNamesRecord> {

        private static final long serialVersionUID = 1L;
        public <O extends Record> JLaunchNamesPath(Table<O> path, ForeignKey<O, JLaunchNamesRecord> childPath, InverseForeignKey<O, JLaunchNamesRecord> parentPath) {
            super(path, childPath, parentPath);
        }
        private JLaunchNamesPath(Name alias, Table<JLaunchNamesRecord> aliased) {
            super(alias, aliased);
        }

        @Override
        public JLaunchNamesPath as(String alias) {
            return new JLaunchNamesPath(DSL.name(alias), this);
        }

        @Override
        public JLaunchNamesPath as(Name alias) {
            return new JLaunchNamesPath(alias, this);
        }

        @Override
        public JLaunchNamesPath as(Table<?> alias) {
            return new JLaunchNamesPath(alias.getQualifiedName(), this);
        }
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : JPublic.PUBLIC;
    }

    @Override
    public List<Index> getIndexes() {
        return Arrays.asList(Indexes.LN_SEND_CASE_IDX);
    }

    @Override
    public List<ForeignKey<JLaunchNamesRecord, ?>> getReferences() {
        return Arrays.asList(Keys.LAUNCH_NAMES__LAUNCH_NAMES_SENDER_CASE_ID_FKEY);
    }

    private transient JSenderCasePath _senderCase;

    /**
     * Get the implicit join path to the <code>public.sender_case</code> table.
     */
    public JSenderCasePath senderCase() {
        if (_senderCase == null)
            _senderCase = new JSenderCasePath(this, Keys.LAUNCH_NAMES__LAUNCH_NAMES_SENDER_CASE_ID_FKEY, null);

        return _senderCase;
    }

    @Override
    public JLaunchNames as(String alias) {
        return new JLaunchNames(DSL.name(alias), this);
    }

    @Override
    public JLaunchNames as(Name alias) {
        return new JLaunchNames(alias, this);
    }

    @Override
    public JLaunchNames as(Table<?> alias) {
        return new JLaunchNames(alias.getQualifiedName(), this);
    }

    /**
     * Rename this table
     */
    @Override
    public JLaunchNames rename(String name) {
        return new JLaunchNames(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public JLaunchNames rename(Name name) {
        return new JLaunchNames(name, null);
    }

    /**
     * Rename this table
     */
    @Override
    public JLaunchNames rename(Table<?> name) {
        return new JLaunchNames(name.getQualifiedName(), null);
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public JLaunchNames where(Condition condition) {
        return new JLaunchNames(getQualifiedName(), aliased() ? this : null, null, condition);
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public JLaunchNames where(Collection<? extends Condition> conditions) {
        return where(DSL.and(conditions));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public JLaunchNames where(Condition... conditions) {
        return where(DSL.and(conditions));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public JLaunchNames where(Field<Boolean> condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public JLaunchNames where(SQL condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public JLaunchNames where(@Stringly.SQL String condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public JLaunchNames where(@Stringly.SQL String condition, Object... binds) {
        return where(DSL.condition(condition, binds));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public JLaunchNames where(@Stringly.SQL String condition, QueryPart... parts) {
        return where(DSL.condition(condition, parts));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public JLaunchNames whereExists(Select<?> select) {
        return where(DSL.exists(select));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public JLaunchNames whereNotExists(Select<?> select) {
        return where(DSL.notExists(select));
    }
}
