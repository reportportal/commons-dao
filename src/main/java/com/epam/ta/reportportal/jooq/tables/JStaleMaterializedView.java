/*
 * This file is generated by jOOQ.
 */
package com.epam.ta.reportportal.jooq.tables;


import com.epam.ta.reportportal.dao.converters.JooqInstantConverter;
import com.epam.ta.reportportal.jooq.Indexes;
import com.epam.ta.reportportal.jooq.JPublic;
import com.epam.ta.reportportal.jooq.Keys;
import com.epam.ta.reportportal.jooq.tables.records.JStaleMaterializedViewRecord;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import javax.annotation.processing.Generated;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.Index;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row3;
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
public class JStaleMaterializedView extends TableImpl<JStaleMaterializedViewRecord> {

    private static final long serialVersionUID = -70601346;

    /**
     * The reference instance of <code>public.stale_materialized_view</code>
     */
    public static final JStaleMaterializedView STALE_MATERIALIZED_VIEW = new JStaleMaterializedView();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<JStaleMaterializedViewRecord> getRecordType() {
        return JStaleMaterializedViewRecord.class;
    }

    /**
     * The column <code>public.stale_materialized_view.id</code>.
     */
    public final TableField<JStaleMaterializedViewRecord, Long> ID = createField(DSL.name("id"), org.jooq.impl.SQLDataType.BIGINT.nullable(false).defaultValue(org.jooq.impl.DSL.field("nextval('stale_materialized_view_id_seq'::regclass)", org.jooq.impl.SQLDataType.BIGINT)), this, "");

    /**
     * The column <code>public.stale_materialized_view.name</code>.
     */
    public final TableField<JStaleMaterializedViewRecord, String> NAME = createField(DSL.name("name"), org.jooq.impl.SQLDataType.VARCHAR(128).nullable(false), this, "");

    /**
     * The column <code>public.stale_materialized_view.creation_date</code>.
     */
    public final TableField<JStaleMaterializedViewRecord, Instant> CREATION_DATE = createField(DSL.name("creation_date"), org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false), this, "", new JooqInstantConverter());

    /**
     * Create a <code>public.stale_materialized_view</code> table reference
     */
    public JStaleMaterializedView() {
        this(DSL.name("stale_materialized_view"), null);
    }

    /**
     * Create an aliased <code>public.stale_materialized_view</code> table reference
     */
    public JStaleMaterializedView(String alias) {
        this(DSL.name(alias), STALE_MATERIALIZED_VIEW);
    }

    /**
     * Create an aliased <code>public.stale_materialized_view</code> table reference
     */
    public JStaleMaterializedView(Name alias) {
        this(alias, STALE_MATERIALIZED_VIEW);
    }

    private JStaleMaterializedView(Name alias, Table<JStaleMaterializedViewRecord> aliased) {
        this(alias, aliased, null);
    }

    private JStaleMaterializedView(Name alias, Table<JStaleMaterializedViewRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> JStaleMaterializedView(Table<O> child, ForeignKey<O, JStaleMaterializedViewRecord> key) {
        super(child, key, STALE_MATERIALIZED_VIEW);
    }

    @Override
    public Schema getSchema() {
        return JPublic.PUBLIC;
    }

    @Override
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.STALE_MATERIALIZED_VIEW_NAME_KEY, Indexes.STALE_MATERIALIZED_VIEW_PKEY, Indexes.STALE_MV_CREATION_DATE_IDX);
    }

    @Override
    public Identity<JStaleMaterializedViewRecord, Long> getIdentity() {
        return Keys.IDENTITY_STALE_MATERIALIZED_VIEW;
    }

    @Override
    public UniqueKey<JStaleMaterializedViewRecord> getPrimaryKey() {
        return Keys.STALE_MATERIALIZED_VIEW_PKEY;
    }

    @Override
    public List<UniqueKey<JStaleMaterializedViewRecord>> getKeys() {
        return Arrays.<UniqueKey<JStaleMaterializedViewRecord>>asList(Keys.STALE_MATERIALIZED_VIEW_PKEY, Keys.STALE_MATERIALIZED_VIEW_NAME_KEY);
    }

    @Override
    public JStaleMaterializedView as(String alias) {
        return new JStaleMaterializedView(DSL.name(alias), this);
    }

    @Override
    public JStaleMaterializedView as(Name alias) {
        return new JStaleMaterializedView(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public JStaleMaterializedView rename(String name) {
        return new JStaleMaterializedView(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public JStaleMaterializedView rename(Name name) {
        return new JStaleMaterializedView(name, null);
    }

    // -------------------------------------------------------------------------
    // Row3 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row3<Long, String, Instant> fieldsRow() {
        return (Row3) super.fieldsRow();
    }
}
