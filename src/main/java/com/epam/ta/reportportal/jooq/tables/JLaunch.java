/*
 * This file is generated by jOOQ.
 */
package com.epam.ta.reportportal.jooq.tables;


import com.epam.ta.reportportal.dao.converters.JooqInstantConverter;
import com.epam.ta.reportportal.jooq.Indexes;
import com.epam.ta.reportportal.jooq.JPublic;
import com.epam.ta.reportportal.jooq.Keys;
import com.epam.ta.reportportal.jooq.enums.JLaunchModeEnum;
import com.epam.ta.reportportal.jooq.enums.JRetentionPolicyEnum;
import com.epam.ta.reportportal.jooq.enums.JStatusEnum;
import com.epam.ta.reportportal.jooq.tables.JItemAttribute.JItemAttributePath;
import com.epam.ta.reportportal.jooq.tables.JLog.JLogPath;
import com.epam.ta.reportportal.jooq.tables.JProject.JProjectPath;
import com.epam.ta.reportportal.jooq.tables.JStatistics.JStatisticsPath;
import com.epam.ta.reportportal.jooq.tables.JStatisticsField.JStatisticsFieldPath;
import com.epam.ta.reportportal.jooq.tables.JTestItem.JTestItemPath;
import com.epam.ta.reportportal.jooq.tables.JUsers.JUsersPath;
import com.epam.ta.reportportal.jooq.tables.records.JLaunchRecord;

import java.time.Instant;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

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
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes", "this-escape" })
public class JLaunch extends TableImpl<JLaunchRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>public.launch</code>
     */
    public static final JLaunch LAUNCH = new JLaunch();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<JLaunchRecord> getRecordType() {
        return JLaunchRecord.class;
    }

    /**
     * The column <code>public.launch.id</code>.
     */
    public final TableField<JLaunchRecord, Long> ID = createField(DSL.name("id"), SQLDataType.BIGINT.nullable(false).identity(true), this, "");

    /**
     * The column <code>public.launch.uuid</code>.
     */
    public final TableField<JLaunchRecord, String> UUID = createField(DSL.name("uuid"), SQLDataType.VARCHAR(36).nullable(false), this, "");

    /**
     * The column <code>public.launch.project_id</code>.
     */
    public final TableField<JLaunchRecord, Long> PROJECT_ID = createField(DSL.name("project_id"), SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>public.launch.user_id</code>.
     */
    public final TableField<JLaunchRecord, Long> USER_ID = createField(DSL.name("user_id"), SQLDataType.BIGINT, this, "");

    /**
     * The column <code>public.launch.name</code>.
     */
    public final TableField<JLaunchRecord, String> NAME = createField(DSL.name("name"), SQLDataType.VARCHAR(256).nullable(false), this, "");

    /**
     * The column <code>public.launch.description</code>.
     */
    public final TableField<JLaunchRecord, String> DESCRIPTION = createField(DSL.name("description"), SQLDataType.CLOB, this, "");

    /**
     * The column <code>public.launch.start_time</code>.
     */
    public final TableField<JLaunchRecord, Instant> START_TIME = createField(DSL.name("start_time"), SQLDataType.LOCALDATETIME(6).nullable(false), this, "", new JooqInstantConverter());

    /**
     * The column <code>public.launch.end_time</code>.
     */
    public final TableField<JLaunchRecord, Instant> END_TIME = createField(DSL.name("end_time"), SQLDataType.LOCALDATETIME(6), this, "", new JooqInstantConverter());

    /**
     * The column <code>public.launch.number</code>.
     */
    public final TableField<JLaunchRecord, Integer> NUMBER = createField(DSL.name("number"), SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>public.launch.last_modified</code>.
     */
    public final TableField<JLaunchRecord, Instant> LAST_MODIFIED = createField(DSL.name("last_modified"), SQLDataType.LOCALDATETIME(6).nullable(false).defaultValue(DSL.field(DSL.raw("now()"), SQLDataType.LOCALDATETIME)), this, "", new JooqInstantConverter());

    /**
     * The column <code>public.launch.mode</code>.
     */
    public final TableField<JLaunchRecord, JLaunchModeEnum> MODE = createField(DSL.name("mode"), SQLDataType.VARCHAR.nullable(false).asEnumDataType(JLaunchModeEnum.class), this, "");

    /**
     * The column <code>public.launch.status</code>.
     */
    public final TableField<JLaunchRecord, JStatusEnum> STATUS = createField(DSL.name("status"), SQLDataType.VARCHAR.nullable(false).asEnumDataType(JStatusEnum.class), this, "");

    /**
     * The column <code>public.launch.has_retries</code>.
     */
    public final TableField<JLaunchRecord, Boolean> HAS_RETRIES = createField(DSL.name("has_retries"), SQLDataType.BOOLEAN.nullable(false).defaultValue(DSL.field(DSL.raw("false"), SQLDataType.BOOLEAN)), this, "");

    /**
     * The column <code>public.launch.rerun</code>.
     */
    public final TableField<JLaunchRecord, Boolean> RERUN = createField(DSL.name("rerun"), SQLDataType.BOOLEAN.nullable(false).defaultValue(DSL.field(DSL.raw("false"), SQLDataType.BOOLEAN)), this, "");

    /**
     * The column <code>public.launch.approximate_duration</code>.
     */
    public final TableField<JLaunchRecord, Double> APPROXIMATE_DURATION = createField(DSL.name("approximate_duration"), SQLDataType.DOUBLE.defaultValue(DSL.field(DSL.raw("0.0"), SQLDataType.DOUBLE)), this, "");

    /**
     * The column <code>public.launch.retention_policy</code>.
     */
    public final TableField<JLaunchRecord, JRetentionPolicyEnum> RETENTION_POLICY = createField(DSL.name("retention_policy"), SQLDataType.VARCHAR.defaultValue(DSL.field(DSL.raw("'REGULAR'::retention_policy_enum"), SQLDataType.VARCHAR)).asEnumDataType(JRetentionPolicyEnum.class), this, "");

    private JLaunch(Name alias, Table<JLaunchRecord> aliased) {
        this(alias, aliased, (Field<?>[]) null, null);
    }

    private JLaunch(Name alias, Table<JLaunchRecord> aliased, Field<?>[] parameters, Condition where) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table(), where);
    }

    /**
     * Create an aliased <code>public.launch</code> table reference
     */
    public JLaunch(String alias) {
        this(DSL.name(alias), LAUNCH);
    }

    /**
     * Create an aliased <code>public.launch</code> table reference
     */
    public JLaunch(Name alias) {
        this(alias, LAUNCH);
    }

    /**
     * Create a <code>public.launch</code> table reference
     */
    public JLaunch() {
        this(DSL.name("launch"), null);
    }

    public <O extends Record> JLaunch(Table<O> path, ForeignKey<O, JLaunchRecord> childPath, InverseForeignKey<O, JLaunchRecord> parentPath) {
        super(path, childPath, parentPath, LAUNCH);
    }

    /**
     * A subtype implementing {@link Path} for simplified path-based joins.
     */
    public static class JLaunchPath extends JLaunch implements Path<JLaunchRecord> {

        private static final long serialVersionUID = 1L;
        public <O extends Record> JLaunchPath(Table<O> path, ForeignKey<O, JLaunchRecord> childPath, InverseForeignKey<O, JLaunchRecord> parentPath) {
            super(path, childPath, parentPath);
        }
        private JLaunchPath(Name alias, Table<JLaunchRecord> aliased) {
            super(alias, aliased);
        }

        @Override
        public JLaunchPath as(String alias) {
            return new JLaunchPath(DSL.name(alias), this);
        }

        @Override
        public JLaunchPath as(Name alias) {
            return new JLaunchPath(alias, this);
        }

        @Override
        public JLaunchPath as(Table<?> alias) {
            return new JLaunchPath(alias.getQualifiedName(), this);
        }
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : JPublic.PUBLIC;
    }

    @Override
    public List<Index> getIndexes() {
        return Arrays.asList(Indexes.LAUNCH_PROJECT_START_TIME_IDX, Indexes.LAUNCH_USER_IDX, Indexes.MODE_IDX);
    }

    @Override
    public Identity<JLaunchRecord, Long> getIdentity() {
        return (Identity<JLaunchRecord, Long>) super.getIdentity();
    }

    @Override
    public UniqueKey<JLaunchRecord> getPrimaryKey() {
        return Keys.LAUNCH_PK;
    }

    @Override
    public List<UniqueKey<JLaunchRecord>> getUniqueKeys() {
        return Arrays.asList(Keys.LAUNCH_UUID_KEY, Keys.UNQ_NAME_NUMBER);
    }

    @Override
    public List<ForeignKey<JLaunchRecord, ?>> getReferences() {
        return Arrays.asList(Keys.LAUNCH__LAUNCH_PROJECT_ID_FKEY, Keys.LAUNCH__LAUNCH_USER_ID_FKEY);
    }

    private transient JProjectPath _project;

    /**
     * Get the implicit join path to the <code>public.project</code> table.
     */
    public JProjectPath project() {
        if (_project == null)
            _project = new JProjectPath(this, Keys.LAUNCH__LAUNCH_PROJECT_ID_FKEY, null);

        return _project;
    }

    private transient JUsersPath _users;

    /**
     * Get the implicit join path to the <code>public.users</code> table.
     */
    public JUsersPath users() {
        if (_users == null)
            _users = new JUsersPath(this, Keys.LAUNCH__LAUNCH_USER_ID_FKEY, null);

        return _users;
    }

    private transient JItemAttributePath _itemAttribute;

    /**
     * Get the implicit to-many join path to the
     * <code>public.item_attribute</code> table
     */
    public JItemAttributePath itemAttribute() {
        if (_itemAttribute == null)
            _itemAttribute = new JItemAttributePath(this, null, Keys.ITEM_ATTRIBUTE__ITEM_ATTRIBUTE_LAUNCH_ID_FKEY.getInverseKey());

        return _itemAttribute;
    }

    private transient JLogPath _log;

    /**
     * Get the implicit to-many join path to the <code>public.log</code> table
     */
    public JLogPath log() {
        if (_log == null)
            _log = new JLogPath(this, null, Keys.LOG__LOG_LAUNCH_ID_FKEY.getInverseKey());

        return _log;
    }

    private transient JStatisticsPath _statistics;

    /**
     * Get the implicit to-many join path to the <code>public.statistics</code>
     * table
     */
    public JStatisticsPath statistics() {
        if (_statistics == null)
            _statistics = new JStatisticsPath(this, null, Keys.STATISTICS__STATISTICS_LAUNCH_ID_FKEY.getInverseKey());

        return _statistics;
    }

    private transient JTestItemPath _testItem;

    /**
     * Get the implicit to-many join path to the <code>public.test_item</code>
     * table
     */
    public JTestItemPath testItem() {
        if (_testItem == null)
            _testItem = new JTestItemPath(this, null, Keys.TEST_ITEM__TEST_ITEM_LAUNCH_ID_FKEY.getInverseKey());

        return _testItem;
    }

    /**
     * Get the implicit many-to-many join path to the
     * <code>public.statistics_field</code> table
     */
    public JStatisticsFieldPath statisticsField() {
        return statistics().statisticsField();
    }

    @Override
    public JLaunch as(String alias) {
        return new JLaunch(DSL.name(alias), this);
    }

    @Override
    public JLaunch as(Name alias) {
        return new JLaunch(alias, this);
    }

    @Override
    public JLaunch as(Table<?> alias) {
        return new JLaunch(alias.getQualifiedName(), this);
    }

    /**
     * Rename this table
     */
    @Override
    public JLaunch rename(String name) {
        return new JLaunch(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public JLaunch rename(Name name) {
        return new JLaunch(name, null);
    }

    /**
     * Rename this table
     */
    @Override
    public JLaunch rename(Table<?> name) {
        return new JLaunch(name.getQualifiedName(), null);
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public JLaunch where(Condition condition) {
        return new JLaunch(getQualifiedName(), aliased() ? this : null, null, condition);
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public JLaunch where(Collection<? extends Condition> conditions) {
        return where(DSL.and(conditions));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public JLaunch where(Condition... conditions) {
        return where(DSL.and(conditions));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public JLaunch where(Field<Boolean> condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public JLaunch where(SQL condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public JLaunch where(@Stringly.SQL String condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public JLaunch where(@Stringly.SQL String condition, Object... binds) {
        return where(DSL.condition(condition, binds));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public JLaunch where(@Stringly.SQL String condition, QueryPart... parts) {
        return where(DSL.condition(condition, parts));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public JLaunch whereExists(Select<?> select) {
        return where(DSL.exists(select));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public JLaunch whereNotExists(Select<?> select) {
        return where(DSL.notExists(select));
    }
}
