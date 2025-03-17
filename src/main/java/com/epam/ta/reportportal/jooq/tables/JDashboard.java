/*
 * This file is generated by jOOQ.
 */
package com.epam.ta.reportportal.jooq.tables;


import com.epam.ta.reportportal.dao.converters.JooqInstantConverter;
import com.epam.ta.reportportal.jooq.JPublic;
import com.epam.ta.reportportal.jooq.Keys;
import com.epam.ta.reportportal.jooq.tables.JDashboardWidget.JDashboardWidgetPath;
import com.epam.ta.reportportal.jooq.tables.JOwnedEntity.JOwnedEntityPath;
import com.epam.ta.reportportal.jooq.tables.JWidget.JWidgetPath;
import com.epam.ta.reportportal.jooq.tables.records.JDashboardRecord;

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
public class JDashboard extends TableImpl<JDashboardRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>public.dashboard</code>
     */
    public static final JDashboard DASHBOARD = new JDashboard();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<JDashboardRecord> getRecordType() {
        return JDashboardRecord.class;
    }

    /**
     * The column <code>public.dashboard.id</code>.
     */
    public final TableField<JDashboardRecord, Long> ID = createField(DSL.name("id"), SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>public.dashboard.name</code>.
     */
    public final TableField<JDashboardRecord, String> NAME = createField(DSL.name("name"), SQLDataType.VARCHAR.nullable(false), this, "");

    /**
     * The column <code>public.dashboard.description</code>.
     */
    public final TableField<JDashboardRecord, String> DESCRIPTION = createField(DSL.name("description"), SQLDataType.VARCHAR, this, "");

    /**
     * The column <code>public.dashboard.creation_date</code>.
     */
    public final TableField<JDashboardRecord, Instant> CREATION_DATE = createField(DSL.name("creation_date"), SQLDataType.LOCALDATETIME(6).nullable(false).defaultValue(DSL.field(DSL.raw("now()"), SQLDataType.LOCALDATETIME)), this, "", new JooqInstantConverter());

    private JDashboard(Name alias, Table<JDashboardRecord> aliased) {
        this(alias, aliased, (Field<?>[]) null, null);
    }

    private JDashboard(Name alias, Table<JDashboardRecord> aliased, Field<?>[] parameters, Condition where) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table(), where);
    }

    /**
     * Create an aliased <code>public.dashboard</code> table reference
     */
    public JDashboard(String alias) {
        this(DSL.name(alias), DASHBOARD);
    }

    /**
     * Create an aliased <code>public.dashboard</code> table reference
     */
    public JDashboard(Name alias) {
        this(alias, DASHBOARD);
    }

    /**
     * Create a <code>public.dashboard</code> table reference
     */
    public JDashboard() {
        this(DSL.name("dashboard"), null);
    }

    public <O extends Record> JDashboard(Table<O> path, ForeignKey<O, JDashboardRecord> childPath, InverseForeignKey<O, JDashboardRecord> parentPath) {
        super(path, childPath, parentPath, DASHBOARD);
    }

    /**
     * A subtype implementing {@link Path} for simplified path-based joins.
     */
    public static class JDashboardPath extends JDashboard implements Path<JDashboardRecord> {

        private static final long serialVersionUID = 1L;
        public <O extends Record> JDashboardPath(Table<O> path, ForeignKey<O, JDashboardRecord> childPath, InverseForeignKey<O, JDashboardRecord> parentPath) {
            super(path, childPath, parentPath);
        }
        private JDashboardPath(Name alias, Table<JDashboardRecord> aliased) {
            super(alias, aliased);
        }

        @Override
        public JDashboardPath as(String alias) {
            return new JDashboardPath(DSL.name(alias), this);
        }

        @Override
        public JDashboardPath as(Name alias) {
            return new JDashboardPath(alias, this);
        }

        @Override
        public JDashboardPath as(Table<?> alias) {
            return new JDashboardPath(alias.getQualifiedName(), this);
        }
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : JPublic.PUBLIC;
    }

    @Override
    public UniqueKey<JDashboardRecord> getPrimaryKey() {
        return Keys.DASHBOARD_PKEY;
    }

    @Override
    public List<ForeignKey<JDashboardRecord, ?>> getReferences() {
        return Arrays.asList(Keys.DASHBOARD__DASHBOARD_ID_FK);
    }

    private transient JOwnedEntityPath _ownedEntity;

    /**
     * Get the implicit join path to the <code>public.owned_entity</code> table.
     */
    public JOwnedEntityPath ownedEntity() {
        if (_ownedEntity == null)
            _ownedEntity = new JOwnedEntityPath(this, Keys.DASHBOARD__DASHBOARD_ID_FK, null);

        return _ownedEntity;
    }

    private transient JDashboardWidgetPath _dashboardWidget;

    /**
     * Get the implicit to-many join path to the
     * <code>public.dashboard_widget</code> table
     */
    public JDashboardWidgetPath dashboardWidget() {
        if (_dashboardWidget == null)
            _dashboardWidget = new JDashboardWidgetPath(this, null, Keys.DASHBOARD_WIDGET__DASHBOARD_WIDGET_DASHBOARD_ID_FKEY.getInverseKey());

        return _dashboardWidget;
    }

    /**
     * Get the implicit many-to-many join path to the <code>public.widget</code>
     * table
     */
    public JWidgetPath widget() {
        return dashboardWidget().widget();
    }

    @Override
    public JDashboard as(String alias) {
        return new JDashboard(DSL.name(alias), this);
    }

    @Override
    public JDashboard as(Name alias) {
        return new JDashboard(alias, this);
    }

    @Override
    public JDashboard as(Table<?> alias) {
        return new JDashboard(alias.getQualifiedName(), this);
    }

    /**
     * Rename this table
     */
    @Override
    public JDashboard rename(String name) {
        return new JDashboard(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public JDashboard rename(Name name) {
        return new JDashboard(name, null);
    }

    /**
     * Rename this table
     */
    @Override
    public JDashboard rename(Table<?> name) {
        return new JDashboard(name.getQualifiedName(), null);
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public JDashboard where(Condition condition) {
        return new JDashboard(getQualifiedName(), aliased() ? this : null, null, condition);
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public JDashboard where(Collection<? extends Condition> conditions) {
        return where(DSL.and(conditions));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public JDashboard where(Condition... conditions) {
        return where(DSL.and(conditions));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public JDashboard where(Field<Boolean> condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public JDashboard where(SQL condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public JDashboard where(@Stringly.SQL String condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public JDashboard where(@Stringly.SQL String condition, Object... binds) {
        return where(DSL.condition(condition, binds));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public JDashboard where(@Stringly.SQL String condition, QueryPart... parts) {
        return where(DSL.condition(condition, parts));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public JDashboard whereExists(Select<?> select) {
        return where(DSL.exists(select));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public JDashboard whereNotExists(Select<?> select) {
        return where(DSL.notExists(select));
    }
}
