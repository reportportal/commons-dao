/*
 * This file is generated by jOOQ.
 */
package com.epam.ta.reportportal.jooq.tables;


import com.epam.ta.reportportal.jooq.Indexes;
import com.epam.ta.reportportal.jooq.JPublic;
import com.epam.ta.reportportal.jooq.Keys;
import com.epam.ta.reportportal.jooq.tables.records.JFilterRecord;

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
public class JFilter extends TableImpl<JFilterRecord> {

    private static final long serialVersionUID = -1242846460;

    /**
     * The reference instance of <code>public.filter</code>
     */
    public static final JFilter FILTER = new JFilter();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<JFilterRecord> getRecordType() {
        return JFilterRecord.class;
    }

    /**
     * The column <code>public.filter.id</code>.
     */
    public final TableField<JFilterRecord, Long> ID = createField(DSL.name("id"), org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>public.filter.name</code>.
     */
    public final TableField<JFilterRecord, String> NAME = createField(DSL.name("name"), org.jooq.impl.SQLDataType.VARCHAR.nullable(false), this, "");

    /**
     * The column <code>public.filter.target</code>.
     */
    public final TableField<JFilterRecord, String> TARGET = createField(DSL.name("target"), org.jooq.impl.SQLDataType.VARCHAR.nullable(false), this, "");

    /**
     * The column <code>public.filter.description</code>.
     */
    public final TableField<JFilterRecord, String> DESCRIPTION = createField(DSL.name("description"), org.jooq.impl.SQLDataType.VARCHAR, this, "");

    /**
     * Create a <code>public.filter</code> table reference
     */
    public JFilter() {
        this(DSL.name("filter"), null);
    }

    /**
     * Create an aliased <code>public.filter</code> table reference
     */
    public JFilter(String alias) {
        this(DSL.name(alias), FILTER);
    }

    /**
     * Create an aliased <code>public.filter</code> table reference
     */
    public JFilter(Name alias) {
        this(alias, FILTER);
    }

    private JFilter(Name alias, Table<JFilterRecord> aliased) {
        this(alias, aliased, null);
    }

    private JFilter(Name alias, Table<JFilterRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> JFilter(Table<O> child, ForeignKey<O, JFilterRecord> key) {
        super(child, key, FILTER);
    }

    @Override
    public Schema getSchema() {
        return JPublic.PUBLIC;
    }

    @Override
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.FILTER_PKEY);
    }

    @Override
    public UniqueKey<JFilterRecord> getPrimaryKey() {
        return Keys.FILTER_PKEY;
    }

    @Override
    public List<UniqueKey<JFilterRecord>> getKeys() {
        return Arrays.<UniqueKey<JFilterRecord>>asList(Keys.FILTER_PKEY);
    }

    @Override
    public List<ForeignKey<JFilterRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<JFilterRecord, ?>>asList(Keys.FILTER__FILTER_ID_FK);
    }

    public JOwnedEntity ownedEntity() {
        return new JOwnedEntity(this, Keys.FILTER__FILTER_ID_FK);
    }

    @Override
    public JFilter as(String alias) {
        return new JFilter(DSL.name(alias), this);
    }

    @Override
    public JFilter as(Name alias) {
        return new JFilter(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public JFilter rename(String name) {
        return new JFilter(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public JFilter rename(Name name) {
        return new JFilter(name, null);
    }

    // -------------------------------------------------------------------------
    // Row4 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row4<Long, String, String, String> fieldsRow() {
        return (Row4) super.fieldsRow();
    }
}
