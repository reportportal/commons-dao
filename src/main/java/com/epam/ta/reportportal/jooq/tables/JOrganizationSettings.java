/*
 * This file is generated by jOOQ.
 */
package com.epam.ta.reportportal.jooq.tables;


import com.epam.ta.reportportal.jooq.JPublic;
import com.epam.ta.reportportal.jooq.Keys;
import com.epam.ta.reportportal.jooq.tables.records.JOrganizationSettingsRecord;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.Identity;
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
public class JOrganizationSettings extends TableImpl<JOrganizationSettingsRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>public.organization_settings</code>
     */
    public static final JOrganizationSettings ORGANIZATION_SETTINGS = new JOrganizationSettings();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<JOrganizationSettingsRecord> getRecordType() {
        return JOrganizationSettingsRecord.class;
    }

    /**
     * The column <code>public.organization_settings.id</code>.
     */
    public final TableField<JOrganizationSettingsRecord, Long> ID = createField(DSL.name("id"), SQLDataType.BIGINT.nullable(false).identity(true), this, "");

    /**
     * The column <code>public.organization_settings.organization_id</code>.
     */
    public final TableField<JOrganizationSettingsRecord, Long> ORGANIZATION_ID = createField(DSL.name("organization_id"), SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>public.organization_settings.setting_key</code>.
     */
    public final TableField<JOrganizationSettingsRecord, String> SETTING_KEY = createField(DSL.name("setting_key"), SQLDataType.VARCHAR(255).nullable(false), this, "");

    /**
     * The column <code>public.organization_settings.setting_value</code>.
     */
    public final TableField<JOrganizationSettingsRecord, String> SETTING_VALUE = createField(DSL.name("setting_value"), SQLDataType.CLOB.nullable(false), this, "");

    private JOrganizationSettings(Name alias, Table<JOrganizationSettingsRecord> aliased) {
        this(alias, aliased, (Field<?>[]) null, null);
    }

    private JOrganizationSettings(Name alias, Table<JOrganizationSettingsRecord> aliased, Field<?>[] parameters, Condition where) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table(), where);
    }

    /**
     * Create an aliased <code>public.organization_settings</code> table
     * reference
     */
    public JOrganizationSettings(String alias) {
        this(DSL.name(alias), ORGANIZATION_SETTINGS);
    }

    /**
     * Create an aliased <code>public.organization_settings</code> table
     * reference
     */
    public JOrganizationSettings(Name alias) {
        this(alias, ORGANIZATION_SETTINGS);
    }

    /**
     * Create a <code>public.organization_settings</code> table reference
     */
    public JOrganizationSettings() {
        this(DSL.name("organization_settings"), null);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : JPublic.PUBLIC;
    }

    @Override
    public Identity<JOrganizationSettingsRecord, Long> getIdentity() {
        return (Identity<JOrganizationSettingsRecord, Long>) super.getIdentity();
    }

    @Override
    public UniqueKey<JOrganizationSettingsRecord> getPrimaryKey() {
        return Keys.ORGANIZATION_SETTINGS_PKEY;
    }

    @Override
    public List<UniqueKey<JOrganizationSettingsRecord>> getUniqueKeys() {
        return Arrays.asList(Keys.ORGANIZATION_SETTINGS_ORGANIZATION_ID_SETTING_KEY_KEY);
    }

    @Override
    public JOrganizationSettings as(String alias) {
        return new JOrganizationSettings(DSL.name(alias), this);
    }

    @Override
    public JOrganizationSettings as(Name alias) {
        return new JOrganizationSettings(alias, this);
    }

    @Override
    public JOrganizationSettings as(Table<?> alias) {
        return new JOrganizationSettings(alias.getQualifiedName(), this);
    }

    /**
     * Rename this table
     */
    @Override
    public JOrganizationSettings rename(String name) {
        return new JOrganizationSettings(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public JOrganizationSettings rename(Name name) {
        return new JOrganizationSettings(name, null);
    }

    /**
     * Rename this table
     */
    @Override
    public JOrganizationSettings rename(Table<?> name) {
        return new JOrganizationSettings(name.getQualifiedName(), null);
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public JOrganizationSettings where(Condition condition) {
        return new JOrganizationSettings(getQualifiedName(), aliased() ? this : null, null, condition);
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public JOrganizationSettings where(Collection<? extends Condition> conditions) {
        return where(DSL.and(conditions));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public JOrganizationSettings where(Condition... conditions) {
        return where(DSL.and(conditions));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public JOrganizationSettings where(Field<Boolean> condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public JOrganizationSettings where(SQL condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public JOrganizationSettings where(@Stringly.SQL String condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public JOrganizationSettings where(@Stringly.SQL String condition, Object... binds) {
        return where(DSL.condition(condition, binds));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public JOrganizationSettings where(@Stringly.SQL String condition, QueryPart... parts) {
        return where(DSL.condition(condition, parts));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public JOrganizationSettings whereExists(Select<?> select) {
        return where(DSL.exists(select));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public JOrganizationSettings whereNotExists(Select<?> select) {
        return where(DSL.notExists(select));
    }
}
