/*
 * This file is generated by jOOQ.
 */
package com.epam.ta.reportportal.jooq.tables;


import com.epam.ta.reportportal.dao.converters.JooqInstantConverter;
import com.epam.ta.reportportal.jooq.Indexes;
import com.epam.ta.reportportal.jooq.JPublic;
import com.epam.ta.reportportal.jooq.Keys;
import com.epam.ta.reportportal.jooq.enums.JTestItemTypeEnum;
import com.epam.ta.reportportal.jooq.tables.JItemAttribute.JItemAttributePath;
import com.epam.ta.reportportal.jooq.tables.JLaunch.JLaunchPath;
import com.epam.ta.reportportal.jooq.tables.JLog.JLogPath;
import com.epam.ta.reportportal.jooq.tables.JParameter.JParameterPath;
import com.epam.ta.reportportal.jooq.tables.JPatternTemplate.JPatternTemplatePath;
import com.epam.ta.reportportal.jooq.tables.JPatternTemplateTestItem.JPatternTemplateTestItemPath;
import com.epam.ta.reportportal.jooq.tables.JStatistics.JStatisticsPath;
import com.epam.ta.reportportal.jooq.tables.JStatisticsField.JStatisticsFieldPath;
import com.epam.ta.reportportal.jooq.tables.JTestItem.JTestItemPath;
import com.epam.ta.reportportal.jooq.tables.JTestItemResults.JTestItemResultsPath;
import com.epam.ta.reportportal.jooq.tables.records.JTestItemRecord;

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
import org.jooq.impl.DefaultDataType;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes", "this-escape" })
public class JTestItem extends TableImpl<JTestItemRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>public.test_item</code>
     */
    public static final JTestItem TEST_ITEM = new JTestItem();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<JTestItemRecord> getRecordType() {
        return JTestItemRecord.class;
    }

    /**
     * The column <code>public.test_item.item_id</code>.
     */
    public final TableField<JTestItemRecord, Long> ITEM_ID = createField(DSL.name("item_id"), SQLDataType.BIGINT.nullable(false).identity(true), this, "");

    /**
     * The column <code>public.test_item.uuid</code>.
     */
    public final TableField<JTestItemRecord, String> UUID = createField(DSL.name("uuid"), SQLDataType.VARCHAR(36).nullable(false), this, "");

    /**
     * The column <code>public.test_item.name</code>.
     */
    public final TableField<JTestItemRecord, String> NAME = createField(DSL.name("name"), SQLDataType.VARCHAR(1024), this, "");

    /**
     * The column <code>public.test_item.code_ref</code>.
     */
    public final TableField<JTestItemRecord, String> CODE_REF = createField(DSL.name("code_ref"), SQLDataType.VARCHAR, this, "");

    /**
     * The column <code>public.test_item.type</code>.
     */
    public final TableField<JTestItemRecord, JTestItemTypeEnum> TYPE = createField(DSL.name("type"), SQLDataType.VARCHAR.nullable(false).asEnumDataType(JTestItemTypeEnum.class), this, "");

    /**
     * The column <code>public.test_item.start_time</code>.
     */
    public final TableField<JTestItemRecord, Instant> START_TIME = createField(DSL.name("start_time"), SQLDataType.LOCALDATETIME(6).nullable(false), this, "", new JooqInstantConverter());

    /**
     * The column <code>public.test_item.description</code>.
     */
    public final TableField<JTestItemRecord, String> DESCRIPTION = createField(DSL.name("description"), SQLDataType.CLOB, this, "");

    /**
     * The column <code>public.test_item.last_modified</code>.
     */
    public final TableField<JTestItemRecord, Instant> LAST_MODIFIED = createField(DSL.name("last_modified"), SQLDataType.LOCALDATETIME(6).nullable(false), this, "", new JooqInstantConverter());

    /**
     * The column <code>public.test_item.path</code>.
     */
    public final TableField<JTestItemRecord, Object> PATH = createField(DSL.name("path"), DefaultDataType.getDefaultDataType("\"public\".\"ltree\""), this, "");

    /**
     * The column <code>public.test_item.unique_id</code>.
     */
    public final TableField<JTestItemRecord, String> UNIQUE_ID = createField(DSL.name("unique_id"), SQLDataType.VARCHAR(1024), this, "");

    /**
     * The column <code>public.test_item.test_case_id</code>.
     */
    public final TableField<JTestItemRecord, String> TEST_CASE_ID = createField(DSL.name("test_case_id"), SQLDataType.VARCHAR(1024), this, "");

    /**
     * The column <code>public.test_item.has_children</code>.
     */
    public final TableField<JTestItemRecord, Boolean> HAS_CHILDREN = createField(DSL.name("has_children"), SQLDataType.BOOLEAN.defaultValue(DSL.field(DSL.raw("false"), SQLDataType.BOOLEAN)), this, "");

    /**
     * The column <code>public.test_item.has_retries</code>.
     */
    public final TableField<JTestItemRecord, Boolean> HAS_RETRIES = createField(DSL.name("has_retries"), SQLDataType.BOOLEAN.defaultValue(DSL.field(DSL.raw("false"), SQLDataType.BOOLEAN)), this, "");

    /**
     * The column <code>public.test_item.has_stats</code>.
     */
    public final TableField<JTestItemRecord, Boolean> HAS_STATS = createField(DSL.name("has_stats"), SQLDataType.BOOLEAN.defaultValue(DSL.field(DSL.raw("true"), SQLDataType.BOOLEAN)), this, "");

    /**
     * The column <code>public.test_item.parent_id</code>.
     */
    public final TableField<JTestItemRecord, Long> PARENT_ID = createField(DSL.name("parent_id"), SQLDataType.BIGINT, this, "");

    /**
     * The column <code>public.test_item.retry_of</code>.
     */
    public final TableField<JTestItemRecord, Long> RETRY_OF = createField(DSL.name("retry_of"), SQLDataType.BIGINT, this, "");

    /**
     * The column <code>public.test_item.launch_id</code>.
     */
    public final TableField<JTestItemRecord, Long> LAUNCH_ID = createField(DSL.name("launch_id"), SQLDataType.BIGINT, this, "");

    /**
     * The column <code>public.test_item.test_case_hash</code>.
     */
    public final TableField<JTestItemRecord, Integer> TEST_CASE_HASH = createField(DSL.name("test_case_hash"), SQLDataType.INTEGER.nullable(false), this, "");

    private JTestItem(Name alias, Table<JTestItemRecord> aliased) {
        this(alias, aliased, (Field<?>[]) null, null);
    }

    private JTestItem(Name alias, Table<JTestItemRecord> aliased, Field<?>[] parameters, Condition where) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table(), where);
    }

    /**
     * Create an aliased <code>public.test_item</code> table reference
     */
    public JTestItem(String alias) {
        this(DSL.name(alias), TEST_ITEM);
    }

    /**
     * Create an aliased <code>public.test_item</code> table reference
     */
    public JTestItem(Name alias) {
        this(alias, TEST_ITEM);
    }

    /**
     * Create a <code>public.test_item</code> table reference
     */
    public JTestItem() {
        this(DSL.name("test_item"), null);
    }

    public <O extends Record> JTestItem(Table<O> path, ForeignKey<O, JTestItemRecord> childPath, InverseForeignKey<O, JTestItemRecord> parentPath) {
        super(path, childPath, parentPath, TEST_ITEM);
    }

    /**
     * A subtype implementing {@link Path} for simplified path-based joins.
     */
    public static class JTestItemPath extends JTestItem implements Path<JTestItemRecord> {

        private static final long serialVersionUID = 1L;
        public <O extends Record> JTestItemPath(Table<O> path, ForeignKey<O, JTestItemRecord> childPath, InverseForeignKey<O, JTestItemRecord> parentPath) {
            super(path, childPath, parentPath);
        }
        private JTestItemPath(Name alias, Table<JTestItemRecord> aliased) {
            super(alias, aliased);
        }

        @Override
        public JTestItemPath as(String alias) {
            return new JTestItemPath(DSL.name(alias), this);
        }

        @Override
        public JTestItemPath as(Name alias) {
            return new JTestItemPath(alias, this);
        }

        @Override
        public JTestItemPath as(Table<?> alias) {
            return new JTestItemPath(alias.getQualifiedName(), this);
        }
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : JPublic.PUBLIC;
    }

    @Override
    public List<Index> getIndexes() {
        return Arrays.asList(Indexes.IDX_TEST_ITEM_NAME_TRGM, Indexes.ITEM_TEST_CASE_ID_LAUNCH_ID_IDX, Indexes.PATH_GIST_IDX, Indexes.TEST_CASE_HASH_LAUNCH_ID_IDX, Indexes.TEST_ITEM_START_TIME_IDX, Indexes.TEST_ITEM_UNIQUE_ID_LAUNCH_ID_IDX, Indexes.TI_LAUNCH_IDX, Indexes.TI_PARENT_IDX, Indexes.TI_RETRY_OF_IDX);
    }

    @Override
    public Identity<JTestItemRecord, Long> getIdentity() {
        return (Identity<JTestItemRecord, Long>) super.getIdentity();
    }

    @Override
    public UniqueKey<JTestItemRecord> getPrimaryKey() {
        return Keys.TEST_ITEM_PK;
    }

    @Override
    public List<UniqueKey<JTestItemRecord>> getUniqueKeys() {
        return Arrays.asList(Keys.TEST_ITEM_UUID_KEY);
    }

    @Override
    public List<ForeignKey<JTestItemRecord, ?>> getReferences() {
        return Arrays.asList(Keys.TEST_ITEM__TEST_ITEM_LAUNCH_ID_FKEY, Keys.TEST_ITEM__TEST_ITEM_PARENT_ID_FKEY, Keys.TEST_ITEM__TEST_ITEM_RETRY_OF_FKEY);
    }

    private transient JLaunchPath _launch;

    /**
     * Get the implicit join path to the <code>public.launch</code> table.
     */
    public JLaunchPath launch() {
        if (_launch == null)
            _launch = new JLaunchPath(this, Keys.TEST_ITEM__TEST_ITEM_LAUNCH_ID_FKEY, null);

        return _launch;
    }

    private transient JTestItemPath _testItemParentIdFkey;

    /**
     * Get the implicit join path to the <code>public.test_item</code> table,
     * via the <code>test_item_parent_id_fkey</code> key.
     */
    public JTestItemPath testItemParentIdFkey() {
        if (_testItemParentIdFkey == null)
            _testItemParentIdFkey = new JTestItemPath(this, Keys.TEST_ITEM__TEST_ITEM_PARENT_ID_FKEY, null);

        return _testItemParentIdFkey;
    }

    private transient JTestItemPath _testItemRetryOfFkey;

    /**
     * Get the implicit join path to the <code>public.test_item</code> table,
     * via the <code>test_item_retry_of_fkey</code> key.
     */
    public JTestItemPath testItemRetryOfFkey() {
        if (_testItemRetryOfFkey == null)
            _testItemRetryOfFkey = new JTestItemPath(this, Keys.TEST_ITEM__TEST_ITEM_RETRY_OF_FKEY, null);

        return _testItemRetryOfFkey;
    }

    private transient JItemAttributePath _itemAttribute;

    /**
     * Get the implicit to-many join path to the
     * <code>public.item_attribute</code> table
     */
    public JItemAttributePath itemAttribute() {
        if (_itemAttribute == null)
            _itemAttribute = new JItemAttributePath(this, null, Keys.ITEM_ATTRIBUTE__ITEM_ATTRIBUTE_ITEM_ID_FKEY.getInverseKey());

        return _itemAttribute;
    }

    private transient JLogPath _log;

    /**
     * Get the implicit to-many join path to the <code>public.log</code> table
     */
    public JLogPath log() {
        if (_log == null)
            _log = new JLogPath(this, null, Keys.LOG__LOG_ITEM_ID_FKEY.getInverseKey());

        return _log;
    }

    private transient JParameterPath _parameter;

    /**
     * Get the implicit to-many join path to the <code>public.parameter</code>
     * table
     */
    public JParameterPath parameter() {
        if (_parameter == null)
            _parameter = new JParameterPath(this, null, Keys.PARAMETER__PARAMETER_ITEM_ID_FKEY.getInverseKey());

        return _parameter;
    }

    private transient JPatternTemplateTestItemPath _patternTemplateTestItem;

    /**
     * Get the implicit to-many join path to the
     * <code>public.pattern_template_test_item</code> table
     */
    public JPatternTemplateTestItemPath patternTemplateTestItem() {
        if (_patternTemplateTestItem == null)
            _patternTemplateTestItem = new JPatternTemplateTestItemPath(this, null, Keys.PATTERN_TEMPLATE_TEST_ITEM__PATTERN_TEMPLATE_TEST_ITEM_ITEM_ID_FKEY.getInverseKey());

        return _patternTemplateTestItem;
    }

    private transient JStatisticsPath _statistics;

    /**
     * Get the implicit to-many join path to the <code>public.statistics</code>
     * table
     */
    public JStatisticsPath statistics() {
        if (_statistics == null)
            _statistics = new JStatisticsPath(this, null, Keys.STATISTICS__STATISTICS_ITEM_ID_FKEY.getInverseKey());

        return _statistics;
    }

    private transient JTestItemResultsPath _testItemResults;

    /**
     * Get the implicit to-many join path to the
     * <code>public.test_item_results</code> table
     */
    public JTestItemResultsPath testItemResults() {
        if (_testItemResults == null)
            _testItemResults = new JTestItemResultsPath(this, null, Keys.TEST_ITEM_RESULTS__TEST_ITEM_RESULTS_RESULT_ID_FKEY.getInverseKey());

        return _testItemResults;
    }

    /**
     * Get the implicit many-to-many join path to the
     * <code>public.pattern_template</code> table
     */
    public JPatternTemplatePath patternTemplate() {
        return patternTemplateTestItem().patternTemplate();
    }

    /**
     * Get the implicit many-to-many join path to the
     * <code>public.statistics_field</code> table
     */
    public JStatisticsFieldPath statisticsField() {
        return statistics().statisticsField();
    }

    @Override
    public JTestItem as(String alias) {
        return new JTestItem(DSL.name(alias), this);
    }

    @Override
    public JTestItem as(Name alias) {
        return new JTestItem(alias, this);
    }

    @Override
    public JTestItem as(Table<?> alias) {
        return new JTestItem(alias.getQualifiedName(), this);
    }

    /**
     * Rename this table
     */
    @Override
    public JTestItem rename(String name) {
        return new JTestItem(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public JTestItem rename(Name name) {
        return new JTestItem(name, null);
    }

    /**
     * Rename this table
     */
    @Override
    public JTestItem rename(Table<?> name) {
        return new JTestItem(name.getQualifiedName(), null);
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public JTestItem where(Condition condition) {
        return new JTestItem(getQualifiedName(), aliased() ? this : null, null, condition);
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public JTestItem where(Collection<? extends Condition> conditions) {
        return where(DSL.and(conditions));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public JTestItem where(Condition... conditions) {
        return where(DSL.and(conditions));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public JTestItem where(Field<Boolean> condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public JTestItem where(SQL condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public JTestItem where(@Stringly.SQL String condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public JTestItem where(@Stringly.SQL String condition, Object... binds) {
        return where(DSL.condition(condition, binds));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public JTestItem where(@Stringly.SQL String condition, QueryPart... parts) {
        return where(DSL.condition(condition, parts));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public JTestItem whereExists(Select<?> select) {
        return where(DSL.exists(select));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public JTestItem whereNotExists(Select<?> select) {
        return where(DSL.notExists(select));
    }
}
