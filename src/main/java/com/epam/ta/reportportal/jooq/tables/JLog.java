/*
 * This file is generated by jOOQ.
 */
package com.epam.ta.reportportal.jooq.tables;


import com.epam.ta.reportportal.dao.converters.JooqInstantConverter;
import com.epam.ta.reportportal.jooq.Indexes;
import com.epam.ta.reportportal.jooq.JPublic;
import com.epam.ta.reportportal.jooq.Keys;
import com.epam.ta.reportportal.jooq.tables.JAttachment.JAttachmentPath;
import com.epam.ta.reportportal.jooq.tables.JLaunch.JLaunchPath;
import com.epam.ta.reportportal.jooq.tables.JTestItem.JTestItemPath;
import com.epam.ta.reportportal.jooq.tables.records.JLogRecord;

import java.time.Instant;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.jooq.Check;
import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Identity;
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
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.Internal;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes", "this-escape" })
public class JLog extends TableImpl<JLogRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>public.log</code>
     */
    public static final JLog LOG = new JLog();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<JLogRecord> getRecordType() {
        return JLogRecord.class;
    }

    /**
     * The column <code>public.log.id</code>.
     */
    public final TableField<JLogRecord, Long> ID = createField(DSL.name("id"), SQLDataType.BIGINT.nullable(false).identity(true), this, "");

    /**
     * The column <code>public.log.uuid</code>.
     */
    public final TableField<JLogRecord, String> UUID = createField(DSL.name("uuid"), SQLDataType.VARCHAR(36).nullable(false), this, "");

    /**
     * The column <code>public.log.log_time</code>.
     */
    public final TableField<JLogRecord, Instant> LOG_TIME = createField(DSL.name("log_time"), SQLDataType.LOCALDATETIME(6).nullable(false), this, "", new JooqInstantConverter());

    /**
     * The column <code>public.log.log_message</code>.
     */
    public final TableField<JLogRecord, String> LOG_MESSAGE = createField(DSL.name("log_message"), SQLDataType.CLOB.nullable(false), this, "");

    /**
     * The column <code>public.log.item_id</code>.
     */
    public final TableField<JLogRecord, Long> ITEM_ID = createField(DSL.name("item_id"), SQLDataType.BIGINT, this, "");

    /**
     * The column <code>public.log.launch_id</code>.
     */
    public final TableField<JLogRecord, Long> LAUNCH_ID = createField(DSL.name("launch_id"), SQLDataType.BIGINT, this, "");

    /**
     * The column <code>public.log.last_modified</code>.
     */
    public final TableField<JLogRecord, Instant> LAST_MODIFIED = createField(DSL.name("last_modified"), SQLDataType.LOCALDATETIME(6).nullable(false), this, "", new JooqInstantConverter());

    /**
     * The column <code>public.log.log_level</code>.
     */
    public final TableField<JLogRecord, Integer> LOG_LEVEL = createField(DSL.name("log_level"), SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>public.log.attachment_id</code>.
     */
    public final TableField<JLogRecord, Long> ATTACHMENT_ID = createField(DSL.name("attachment_id"), SQLDataType.BIGINT, this, "");

    /**
     * The column <code>public.log.project_id</code>.
     */
    public final TableField<JLogRecord, Long> PROJECT_ID = createField(DSL.name("project_id"), SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>public.log.cluster_id</code>.
     */
    public final TableField<JLogRecord, Long> CLUSTER_ID = createField(DSL.name("cluster_id"), SQLDataType.BIGINT, this, "");

    private JLog(Name alias, Table<JLogRecord> aliased) {
        this(alias, aliased, (Field<?>[]) null, null);
    }

    private JLog(Name alias, Table<JLogRecord> aliased, Field<?>[] parameters, Condition where) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table(), where);
    }

    /**
     * Create an aliased <code>public.log</code> table reference
     */
    public JLog(String alias) {
        this(DSL.name(alias), LOG);
    }

    /**
     * Create an aliased <code>public.log</code> table reference
     */
    public JLog(Name alias) {
        this(alias, LOG);
    }

    /**
     * Create a <code>public.log</code> table reference
     */
    public JLog() {
        this(DSL.name("log"), null);
    }

    public <O extends Record> JLog(Table<O> path, ForeignKey<O, JLogRecord> childPath, InverseForeignKey<O, JLogRecord> parentPath) {
        super(path, childPath, parentPath, LOG);
    }

    /**
     * A subtype implementing {@link Path} for simplified path-based joins.
     */
    public static class JLogPath extends JLog implements Path<JLogRecord> {

        private static final long serialVersionUID = 1L;
        public <O extends Record> JLogPath(Table<O> path, ForeignKey<O, JLogRecord> childPath, InverseForeignKey<O, JLogRecord> parentPath) {
            super(path, childPath, parentPath);
        }
        private JLogPath(Name alias, Table<JLogRecord> aliased) {
            super(alias, aliased);
        }

        @Override
        public JLogPath as(String alias) {
            return new JLogPath(DSL.name(alias), this);
        }

        @Override
        public JLogPath as(Name alias) {
            return new JLogPath(alias, this);
        }

        @Override
        public JLogPath as(Table<?> alias) {
            return new JLogPath(alias.getQualifiedName(), this);
        }
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : JPublic.PUBLIC;
    }

    @Override
    public List<Index> getIndexes() {
        return Arrays.asList(Indexes.LOG_ATTACH_ID_IDX, Indexes.LOG_CLUSTER_IDX, Indexes.LOG_LAUNCH_ID_IDX, Indexes.LOG_PROJECT_ID_LOG_TIME_IDX, Indexes.LOG_PROJECT_IDX, Indexes.LOG_TI_IDX);
    }

    @Override
    public Identity<JLogRecord, Long> getIdentity() {
        return (Identity<JLogRecord, Long>) super.getIdentity();
    }

    @Override
    public UniqueKey<JLogRecord> getPrimaryKey() {
        return Keys.LOG_PK;
    }

    @Override
    public List<ForeignKey<JLogRecord, ?>> getReferences() {
        return Arrays.asList(Keys.LOG__LOG_ATTACHMENT_ID_FKEY, Keys.LOG__LOG_ITEM_ID_FKEY, Keys.LOG__LOG_LAUNCH_ID_FKEY);
    }

    private transient JAttachmentPath _attachment;

    /**
     * Get the implicit join path to the <code>public.attachment</code> table.
     */
    public JAttachmentPath attachment() {
        if (_attachment == null)
            _attachment = new JAttachmentPath(this, Keys.LOG__LOG_ATTACHMENT_ID_FKEY, null);

        return _attachment;
    }

    private transient JTestItemPath _testItem;

    /**
     * Get the implicit join path to the <code>public.test_item</code> table.
     */
    public JTestItemPath testItem() {
        if (_testItem == null)
            _testItem = new JTestItemPath(this, Keys.LOG__LOG_ITEM_ID_FKEY, null);

        return _testItem;
    }

    private transient JLaunchPath _launch;

    /**
     * Get the implicit join path to the <code>public.launch</code> table.
     */
    public JLaunchPath launch() {
        if (_launch == null)
            _launch = new JLaunchPath(this, Keys.LOG__LOG_LAUNCH_ID_FKEY, null);

        return _launch;
    }

    @Override
    public List<Check<JLogRecord>> getChecks() {
        return Arrays.asList(
            Internal.createCheck(this, DSL.name("log_check"), "((((item_id IS NOT NULL) AND (launch_id IS NULL)) OR ((item_id IS NULL) AND (launch_id IS NOT NULL))))", true)
        );
    }

    @Override
    public JLog as(String alias) {
        return new JLog(DSL.name(alias), this);
    }

    @Override
    public JLog as(Name alias) {
        return new JLog(alias, this);
    }

    @Override
    public JLog as(Table<?> alias) {
        return new JLog(alias.getQualifiedName(), this);
    }

    /**
     * Rename this table
     */
    @Override
    public JLog rename(String name) {
        return new JLog(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public JLog rename(Name name) {
        return new JLog(name, null);
    }

    /**
     * Rename this table
     */
    @Override
    public JLog rename(Table<?> name) {
        return new JLog(name.getQualifiedName(), null);
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public JLog where(Condition condition) {
        return new JLog(getQualifiedName(), aliased() ? this : null, null, condition);
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public JLog where(Collection<? extends Condition> conditions) {
        return where(DSL.and(conditions));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public JLog where(Condition... conditions) {
        return where(DSL.and(conditions));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public JLog where(Field<Boolean> condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public JLog where(SQL condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public JLog where(@Stringly.SQL String condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public JLog where(@Stringly.SQL String condition, Object... binds) {
        return where(DSL.condition(condition, binds));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public JLog where(@Stringly.SQL String condition, QueryPart... parts) {
        return where(DSL.condition(condition, parts));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public JLog whereExists(Select<?> select) {
        return where(DSL.exists(select));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public JLog whereNotExists(Select<?> select) {
        return where(DSL.notExists(select));
    }
}
