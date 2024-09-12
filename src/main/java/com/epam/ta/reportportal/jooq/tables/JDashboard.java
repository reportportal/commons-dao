/*
 * This file is generated by jOOQ.
 */
package com.epam.ta.reportportal.jooq.tables;


import com.epam.ta.reportportal.dao.converters.InstantConverter;
import com.epam.ta.reportportal.jooq.Indexes;
import com.epam.ta.reportportal.jooq.JPublic;
import com.epam.ta.reportportal.jooq.Keys;
import com.epam.ta.reportportal.jooq.tables.records.JDashboardRecord;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import javax.annotation.processing.Generated;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Index;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row4;
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
        "jOOQ version:3.12.4"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class JDashboard extends TableImpl<JDashboardRecord> {

    private static final long serialVersionUID = 396879431;

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
    public final TableField<JDashboardRecord, Long> ID = createField(DSL.name("id"), org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>public.dashboard.name</code>.
     */
    public final TableField<JDashboardRecord, String> NAME = createField(DSL.name("name"), org.jooq.impl.SQLDataType.VARCHAR.nullable(false), this, "");

    /**
     * The column <code>public.dashboard.description</code>.
     */
    public final TableField<JDashboardRecord, String> DESCRIPTION = createField(DSL.name("description"), org.jooq.impl.SQLDataType.VARCHAR, this, "");

    /**
     * The column <code>public.dashboard.creation_date</code>.
     */
    public final TableField<JDashboardRecord, Instant> CREATION_DATE = createField(DSL.name("creation_date"), org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.field("now()", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "", new InstantConverter());

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

    @Override
    public Schema getSchema() {
        return JPublic.PUBLIC;
    }

    @Override
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.DASHBOARD_PKEY);
    }

    @Override
    public UniqueKey<JDashboardRecord> getPrimaryKey() {
        return Keys.DASHBOARD_PKEY;
    }

    @Override
    public List<UniqueKey<JDashboardRecord>> getKeys() {
        return Arrays.<UniqueKey<JDashboardRecord>>asList(Keys.DASHBOARD_PKEY);
    }

    @Override
    public List<ForeignKey<JDashboardRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<JDashboardRecord, ?>>asList(Keys.DASHBOARD__DASHBOARD_ID_FK);
    }

    public JOwnedEntity ownedEntity() {
        return new JOwnedEntity(this, Keys.DASHBOARD__DASHBOARD_ID_FK);
    }

    @Override
    public JDashboard as(String alias) {
        return new JDashboard(DSL.name(alias), this);
    }

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

    // -------------------------------------------------------------------------
    // Row4 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row4<Long, String, String, Instant> fieldsRow() {
        return (Row4) super.fieldsRow();
    }
}
