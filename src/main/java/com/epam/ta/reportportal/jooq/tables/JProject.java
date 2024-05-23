/*
 * This file is generated by jOOQ.
 */
package com.epam.ta.reportportal.jooq.tables;


import com.epam.ta.reportportal.jooq.Indexes;
import com.epam.ta.reportportal.jooq.JPublic;
import com.epam.ta.reportportal.jooq.Keys;
import com.epam.ta.reportportal.jooq.tables.records.JProjectRecord;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import javax.annotation.processing.Generated;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.Index;
import org.jooq.JSONB;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row10;
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
public class JProject extends TableImpl<JProjectRecord> {

    private static final long serialVersionUID = 1291621622;

    /**
     * The reference instance of <code>public.project</code>
     */
    public static final JProject PROJECT = new JProject();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<JProjectRecord> getRecordType() {
        return JProjectRecord.class;
    }

    /**
     * The column <code>public.project.id</code>.
     */
    public final TableField<JProjectRecord, Long> ID = createField(DSL.name("id"), org.jooq.impl.SQLDataType.BIGINT.nullable(false).defaultValue(org.jooq.impl.DSL.field("nextval('project_id_seq'::regclass)", org.jooq.impl.SQLDataType.BIGINT)), this, "");

    /**
     * The column <code>public.project.name</code>.
     */
    public final TableField<JProjectRecord, String> NAME = createField(DSL.name("name"), org.jooq.impl.SQLDataType.VARCHAR.nullable(false), this, "");

    /**
     * The column <code>public.project.project_type</code>.
     */
    public final TableField<JProjectRecord, String> PROJECT_TYPE = createField(DSL.name("project_type"), org.jooq.impl.SQLDataType.VARCHAR.nullable(false), this, "");

    /**
     * The column <code>public.project.organization</code>.
     */
    public final TableField<JProjectRecord, String> ORGANIZATION = createField(DSL.name("organization"), org.jooq.impl.SQLDataType.VARCHAR, this, "");

    /**
     * The column <code>public.project.creation_date</code>.
     */
    public final TableField<JProjectRecord, Timestamp> CREATION_DATE = createField(DSL.name("creation_date"), org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.field("now()", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * The column <code>public.project.metadata</code>.
     */
    public final TableField<JProjectRecord, JSONB> METADATA = createField(DSL.name("metadata"), org.jooq.impl.SQLDataType.JSONB, this, "");

    /**
     * The column <code>public.project.allocated_storage</code>.
     */
    public final TableField<JProjectRecord, Long> ALLOCATED_STORAGE = createField(DSL.name("allocated_storage"), org.jooq.impl.SQLDataType.BIGINT.nullable(false).defaultValue(org.jooq.impl.DSL.field("0", org.jooq.impl.SQLDataType.BIGINT)), this, "");

    /**
     * The column <code>public.project.organization_id</code>.
     */
    public final TableField<JProjectRecord, Long> ORGANIZATION_ID = createField(DSL.name("organization_id"), org.jooq.impl.SQLDataType.BIGINT, this, "");

    /**
     * The column <code>public.project.slug</code>.
     */
    public final TableField<JProjectRecord, String> SLUG = createField(DSL.name("slug"), org.jooq.impl.SQLDataType.CLOB, this, "");

    /**
     * The column <code>public.project.key</code>.
     */
    public final TableField<JProjectRecord, String> KEY = createField(DSL.name("key"), org.jooq.impl.SQLDataType.CLOB, this, "");

    /**
     * Create a <code>public.project</code> table reference
     */
    public JProject() {
        this(DSL.name("project"), null);
    }

    /**
     * Create an aliased <code>public.project</code> table reference
     */
    public JProject(String alias) {
        this(DSL.name(alias), PROJECT);
    }

    /**
     * Create an aliased <code>public.project</code> table reference
     */
    public JProject(Name alias) {
        this(alias, PROJECT);
    }

    private JProject(Name alias, Table<JProjectRecord> aliased) {
        this(alias, aliased, null);
    }

    private JProject(Name alias, Table<JProjectRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> JProject(Table<O> child, ForeignKey<O, JProjectRecord> key) {
        super(child, key, PROJECT);
    }

    @Override
    public Schema getSchema() {
        return JPublic.PUBLIC;
    }

    @Override
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.PROJECT_KEY_IDX, Indexes.PROJECT_KEY_KEY, Indexes.PROJECT_NAME_KEY, Indexes.PROJECT_PK);
    }

    @Override
    public Identity<JProjectRecord, Long> getIdentity() {
        return Keys.IDENTITY_PROJECT;
    }

    @Override
    public UniqueKey<JProjectRecord> getPrimaryKey() {
        return Keys.PROJECT_PK;
    }

    @Override
    public List<UniqueKey<JProjectRecord>> getKeys() {
        return Arrays.<UniqueKey<JProjectRecord>>asList(Keys.PROJECT_PK, Keys.PROJECT_NAME_KEY, Keys.PROJECT_KEY_KEY);
    }

    @Override
    public JProject as(String alias) {
        return new JProject(DSL.name(alias), this);
    }

    @Override
    public JProject as(Name alias) {
        return new JProject(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public JProject rename(String name) {
        return new JProject(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public JProject rename(Name name) {
        return new JProject(name, null);
    }

    // -------------------------------------------------------------------------
    // Row10 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row10<Long, String, String, String, Timestamp, JSONB, Long, Long, String, String> fieldsRow() {
        return (Row10) super.fieldsRow();
    }
}
