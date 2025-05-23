/*
 * This file is generated by jOOQ.
 */
package com.epam.ta.reportportal.jooq.tables;


import com.epam.ta.reportportal.dao.converters.JooqInstantConverter;
import com.epam.ta.reportportal.jooq.JPublic;
import com.epam.ta.reportportal.jooq.Keys;
import com.epam.ta.reportportal.jooq.enums.JStatusEnum;
import com.epam.ta.reportportal.jooq.tables.JIssue.JIssuePath;
import com.epam.ta.reportportal.jooq.tables.JTestItem.JTestItemPath;
import com.epam.ta.reportportal.jooq.tables.records.JTestItemResultsRecord;

import java.time.Instant;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.ForeignKey;
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
public class JTestItemResults extends TableImpl<JTestItemResultsRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>public.test_item_results</code>
     */
    public static final JTestItemResults TEST_ITEM_RESULTS = new JTestItemResults();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<JTestItemResultsRecord> getRecordType() {
        return JTestItemResultsRecord.class;
    }

    /**
     * The column <code>public.test_item_results.result_id</code>.
     */
    public final TableField<JTestItemResultsRecord, Long> RESULT_ID = createField(DSL.name("result_id"), SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>public.test_item_results.status</code>.
     */
    public final TableField<JTestItemResultsRecord, JStatusEnum> STATUS = createField(DSL.name("status"), SQLDataType.VARCHAR.nullable(false).asEnumDataType(JStatusEnum.class), this, "");

    /**
     * The column <code>public.test_item_results.end_time</code>.
     */
    public final TableField<JTestItemResultsRecord, Instant> END_TIME = createField(DSL.name("end_time"), SQLDataType.LOCALDATETIME(6), this, "", new JooqInstantConverter());

    /**
     * The column <code>public.test_item_results.duration</code>.
     */
    public final TableField<JTestItemResultsRecord, Double> DURATION = createField(DSL.name("duration"), SQLDataType.DOUBLE, this, "");

    private JTestItemResults(Name alias, Table<JTestItemResultsRecord> aliased) {
        this(alias, aliased, (Field<?>[]) null, null);
    }

    private JTestItemResults(Name alias, Table<JTestItemResultsRecord> aliased, Field<?>[] parameters, Condition where) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table(), where);
    }

    /**
     * Create an aliased <code>public.test_item_results</code> table reference
     */
    public JTestItemResults(String alias) {
        this(DSL.name(alias), TEST_ITEM_RESULTS);
    }

    /**
     * Create an aliased <code>public.test_item_results</code> table reference
     */
    public JTestItemResults(Name alias) {
        this(alias, TEST_ITEM_RESULTS);
    }

    /**
     * Create a <code>public.test_item_results</code> table reference
     */
    public JTestItemResults() {
        this(DSL.name("test_item_results"), null);
    }

    public <O extends Record> JTestItemResults(Table<O> path, ForeignKey<O, JTestItemResultsRecord> childPath, InverseForeignKey<O, JTestItemResultsRecord> parentPath) {
        super(path, childPath, parentPath, TEST_ITEM_RESULTS);
    }

    /**
     * A subtype implementing {@link Path} for simplified path-based joins.
     */
    public static class JTestItemResultsPath extends JTestItemResults implements Path<JTestItemResultsRecord> {

        private static final long serialVersionUID = 1L;
        public <O extends Record> JTestItemResultsPath(Table<O> path, ForeignKey<O, JTestItemResultsRecord> childPath, InverseForeignKey<O, JTestItemResultsRecord> parentPath) {
            super(path, childPath, parentPath);
        }
        private JTestItemResultsPath(Name alias, Table<JTestItemResultsRecord> aliased) {
            super(alias, aliased);
        }

        @Override
        public JTestItemResultsPath as(String alias) {
            return new JTestItemResultsPath(DSL.name(alias), this);
        }

        @Override
        public JTestItemResultsPath as(Name alias) {
            return new JTestItemResultsPath(alias, this);
        }

        @Override
        public JTestItemResultsPath as(Table<?> alias) {
            return new JTestItemResultsPath(alias.getQualifiedName(), this);
        }
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : JPublic.PUBLIC;
    }

    @Override
    public UniqueKey<JTestItemResultsRecord> getPrimaryKey() {
        return Keys.TEST_ITEM_RESULTS_PK;
    }

    @Override
    public List<ForeignKey<JTestItemResultsRecord, ?>> getReferences() {
        return Arrays.asList(Keys.TEST_ITEM_RESULTS__TEST_ITEM_RESULTS_RESULT_ID_FKEY);
    }

    private transient JTestItemPath _testItem;

    /**
     * Get the implicit join path to the <code>public.test_item</code> table.
     */
    public JTestItemPath testItem() {
        if (_testItem == null)
            _testItem = new JTestItemPath(this, Keys.TEST_ITEM_RESULTS__TEST_ITEM_RESULTS_RESULT_ID_FKEY, null);

        return _testItem;
    }

    private transient JIssuePath _issue;

    /**
     * Get the implicit to-many join path to the <code>public.issue</code> table
     */
    public JIssuePath issue() {
        if (_issue == null)
            _issue = new JIssuePath(this, null, Keys.ISSUE__ISSUE_ISSUE_ID_FKEY.getInverseKey());

        return _issue;
    }

    @Override
    public JTestItemResults as(String alias) {
        return new JTestItemResults(DSL.name(alias), this);
    }

    @Override
    public JTestItemResults as(Name alias) {
        return new JTestItemResults(alias, this);
    }

    @Override
    public JTestItemResults as(Table<?> alias) {
        return new JTestItemResults(alias.getQualifiedName(), this);
    }

    /**
     * Rename this table
     */
    @Override
    public JTestItemResults rename(String name) {
        return new JTestItemResults(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public JTestItemResults rename(Name name) {
        return new JTestItemResults(name, null);
    }

    /**
     * Rename this table
     */
    @Override
    public JTestItemResults rename(Table<?> name) {
        return new JTestItemResults(name.getQualifiedName(), null);
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public JTestItemResults where(Condition condition) {
        return new JTestItemResults(getQualifiedName(), aliased() ? this : null, null, condition);
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public JTestItemResults where(Collection<? extends Condition> conditions) {
        return where(DSL.and(conditions));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public JTestItemResults where(Condition... conditions) {
        return where(DSL.and(conditions));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public JTestItemResults where(Field<Boolean> condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public JTestItemResults where(SQL condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public JTestItemResults where(@Stringly.SQL String condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public JTestItemResults where(@Stringly.SQL String condition, Object... binds) {
        return where(DSL.condition(condition, binds));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public JTestItemResults where(@Stringly.SQL String condition, QueryPart... parts) {
        return where(DSL.condition(condition, parts));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public JTestItemResults whereExists(Select<?> select) {
        return where(DSL.exists(select));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public JTestItemResults whereNotExists(Select<?> select) {
        return where(DSL.notExists(select));
    }
}
