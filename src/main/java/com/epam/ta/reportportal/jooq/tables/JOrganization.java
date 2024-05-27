/*
 * This file is generated by jOOQ.
 */
package com.epam.ta.reportportal.jooq.tables;


import com.epam.ta.reportportal.jooq.Indexes;
import com.epam.ta.reportportal.jooq.JPublic;
import com.epam.ta.reportportal.jooq.Keys;
import com.epam.ta.reportportal.jooq.tables.records.JOrganizationRecord;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import javax.annotation.processing.Generated;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.Index;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row7;
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
public class JOrganization extends TableImpl<JOrganizationRecord> {

    private static final long serialVersionUID = 116167718;

    /**
     * The reference instance of <code>public.organization</code>
     */
    public static final JOrganization ORGANIZATION = new JOrganization();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<JOrganizationRecord> getRecordType() {
        return JOrganizationRecord.class;
    }

    /**
     * The column <code>public.organization.id</code>.
     */
    public final TableField<JOrganizationRecord, Long> ID = createField(DSL.name("id"), org.jooq.impl.SQLDataType.BIGINT.nullable(false).defaultValue(org.jooq.impl.DSL.field("nextval('organization_id_seq'::regclass)", org.jooq.impl.SQLDataType.BIGINT)), this, "");

    /**
     * The column <code>public.organization.created_at</code>.
     */
    public final TableField<JOrganizationRecord, Timestamp> CREATED_AT = createField(DSL.name("created_at"), org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.field("now()", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * The column <code>public.organization.name</code>.
     */
    public final TableField<JOrganizationRecord, String> NAME = createField(DSL.name("name"), org.jooq.impl.SQLDataType.CLOB.nullable(false), this, "");

    /**
     * The column <code>public.organization.organization_type</code>.
     */
    public final TableField<JOrganizationRecord, String> ORGANIZATION_TYPE = createField(DSL.name("organization_type"), org.jooq.impl.SQLDataType.CLOB.nullable(false), this, "");

    /**
     * The column <code>public.organization.slug</code>.
     */
    public final TableField<JOrganizationRecord, String> SLUG = createField(DSL.name("slug"), org.jooq.impl.SQLDataType.CLOB.nullable(false), this, "");

    /**
     * The column <code>public.organization.updated_at</code>.
     */
    public final TableField<JOrganizationRecord, Timestamp> UPDATED_AT = createField(DSL.name("updated_at"), org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.field("now()", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * The column <code>public.organization.external_id</code>.
     */
    public final TableField<JOrganizationRecord, String> EXTERNAL_ID = createField(DSL.name("external_id"), org.jooq.impl.SQLDataType.CLOB, this, "");

    /**
     * Create a <code>public.organization</code> table reference
     */
    public JOrganization() {
        this(DSL.name("organization"), null);
    }

    /**
     * Create an aliased <code>public.organization</code> table reference
     */
    public JOrganization(String alias) {
        this(DSL.name(alias), ORGANIZATION);
    }

    /**
     * Create an aliased <code>public.organization</code> table reference
     */
    public JOrganization(Name alias) {
        this(alias, ORGANIZATION);
    }

    private JOrganization(Name alias, Table<JOrganizationRecord> aliased) {
        this(alias, aliased, null);
    }

    private JOrganization(Name alias, Table<JOrganizationRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> JOrganization(Table<O> child, ForeignKey<O, JOrganizationRecord> key) {
        super(child, key, ORGANIZATION);
    }

    @Override
    public Schema getSchema() {
        return JPublic.PUBLIC;
    }

    @Override
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.ORGANIZATION_EXTERNAL_ID_KEY, Indexes.ORGANIZATION_NAME_KEY, Indexes.ORGANIZATION_PKEY, Indexes.ORGANIZATION_SLUG_IDX, Indexes.ORGANIZATION_SLUG_KEY);
    }

    @Override
    public Identity<JOrganizationRecord, Long> getIdentity() {
        return Keys.IDENTITY_ORGANIZATION;
    }

    @Override
    public UniqueKey<JOrganizationRecord> getPrimaryKey() {
        return Keys.ORGANIZATION_PKEY;
    }

    @Override
    public List<UniqueKey<JOrganizationRecord>> getKeys() {
        return Arrays.<UniqueKey<JOrganizationRecord>>asList(Keys.ORGANIZATION_PKEY, Keys.ORGANIZATION_NAME_KEY, Keys.ORGANIZATION_SLUG_KEY, Keys.ORGANIZATION_EXTERNAL_ID_KEY);
    }

    @Override
    public JOrganization as(String alias) {
        return new JOrganization(DSL.name(alias), this);
    }

    @Override
    public JOrganization as(Name alias) {
        return new JOrganization(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public JOrganization rename(String name) {
        return new JOrganization(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public JOrganization rename(Name name) {
        return new JOrganization(name, null);
    }

    // -------------------------------------------------------------------------
    // Row7 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row7<Long, Timestamp, String, String, String, Timestamp, String> fieldsRow() {
        return (Row7) super.fieldsRow();
    }
}
