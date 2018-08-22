/*
 * This file is generated by jOOQ.
 */
package com.epam.ta.reportportal.jooq.tables;


import com.epam.ta.reportportal.jooq.Indexes;
import com.epam.ta.reportportal.jooq.JPublic;
import com.epam.ta.reportportal.jooq.Keys;
import com.epam.ta.reportportal.jooq.tables.records.JDashboardRecord;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.Index;
import org.jooq.Name;
import org.jooq.Record;
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
        "jOOQ version:3.11.4"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class JDashboard extends TableImpl<JDashboardRecord> {

    private static final long serialVersionUID = 300526209;

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
    public final TableField<JDashboardRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.field("nextval('dashboard_id_seq'::regclass)", org.jooq.impl.SQLDataType.INTEGER)), this, "");

    /**
     * The column <code>public.dashboard.name</code>.
     */
    public final TableField<JDashboardRecord, String> NAME = createField("name", org.jooq.impl.SQLDataType.VARCHAR.nullable(false), this, "");

    /**
     * The column <code>public.dashboard.description</code>.
     */
    public final TableField<JDashboardRecord, String> DESCRIPTION = createField("description", org.jooq.impl.SQLDataType.VARCHAR, this, "");

    /**
     * The column <code>public.dashboard.project_id</code>.
     */
    public final TableField<JDashboardRecord, Integer> PROJECT_ID = createField("project_id", org.jooq.impl.SQLDataType.INTEGER, this, "");

    /**
     * The column <code>public.dashboard.creation_date</code>.
     */
    public final TableField<JDashboardRecord, Timestamp> CREATION_DATE = createField("creation_date", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.field("now()", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * Create a <code>public.dashboard</code> table reference
     */
    public JDashboard() {
        this(DSL.name("dashboard"), null);
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

    private JDashboard(Name alias, Table<JDashboardRecord> aliased) {
        this(alias, aliased, null);
    }

    private JDashboard(Name alias, Table<JDashboardRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> JDashboard(Table<O> child, ForeignKey<O, JDashboardRecord> key) {
        super(child, key, DASHBOARD);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return JPublic.PUBLIC;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.DASHBOARD_PK, Indexes.UNQ_NAME_PROJECT);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<JDashboardRecord, Integer> getIdentity() {
        return Keys.IDENTITY_DASHBOARD;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<JDashboardRecord> getPrimaryKey() {
        return Keys.DASHBOARD_PK;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<JDashboardRecord>> getKeys() {
        return Arrays.<UniqueKey<JDashboardRecord>>asList(Keys.DASHBOARD_PK, Keys.UNQ_NAME_PROJECT);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<JDashboardRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<JDashboardRecord, ?>>asList(Keys.DASHBOARD__DASHBOARD_PROJECT_ID_FKEY);
    }

    public JProject project() {
        return new JProject(this, Keys.DASHBOARD__DASHBOARD_PROJECT_ID_FKEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JDashboard as(String alias) {
        return new JDashboard(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JDashboard as(Name alias) {
        return new JDashboard(alias, this);
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
}
