/*
 * This file is generated by jOOQ.
 */
package com.epam.ta.reportportal.jooq.tables;


import com.epam.ta.reportportal.dao.converters.InstantConverter;
import com.epam.ta.reportportal.jooq.Indexes;
import com.epam.ta.reportportal.jooq.JPublic;
import com.epam.ta.reportportal.jooq.Keys;
import com.epam.ta.reportportal.jooq.tables.records.JIntegrationRecord;

import java.time.Instant;
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
import org.jooq.Row8;
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
public class JIntegration extends TableImpl<JIntegrationRecord> {

    private static final long serialVersionUID = -1251108530;

    /**
     * The reference instance of <code>public.integration</code>
     */
    public static final JIntegration INTEGRATION = new JIntegration();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<JIntegrationRecord> getRecordType() {
        return JIntegrationRecord.class;
    }

    /**
     * The column <code>public.integration.id</code>.
     */
    public final TableField<JIntegrationRecord, Integer> ID = createField(DSL.name("id"), org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.field("nextval('integration_id_seq'::regclass)", org.jooq.impl.SQLDataType.INTEGER)), this, "");

    /**
     * The column <code>public.integration.name</code>.
     */
    public final TableField<JIntegrationRecord, String> NAME = createField(DSL.name("name"), org.jooq.impl.SQLDataType.VARCHAR.nullable(false), this, "");

    /**
     * The column <code>public.integration.project_id</code>.
     */
    public final TableField<JIntegrationRecord, Long> PROJECT_ID = createField(DSL.name("project_id"), org.jooq.impl.SQLDataType.BIGINT, this, "");

    /**
     * The column <code>public.integration.type</code>.
     */
    public final TableField<JIntegrationRecord, Integer> TYPE = createField(DSL.name("type"), org.jooq.impl.SQLDataType.INTEGER, this, "");

    /**
     * The column <code>public.integration.enabled</code>.
     */
    public final TableField<JIntegrationRecord, Boolean> ENABLED = createField(DSL.name("enabled"), org.jooq.impl.SQLDataType.BOOLEAN.nullable(false), this, "");

    /**
     * The column <code>public.integration.params</code>.
     */
    public final TableField<JIntegrationRecord, JSONB> PARAMS = createField(DSL.name("params"), org.jooq.impl.SQLDataType.JSONB, this, "");

    /**
     * The column <code>public.integration.creator</code>.
     */
    public final TableField<JIntegrationRecord, String> CREATOR = createField(DSL.name("creator"), org.jooq.impl.SQLDataType.VARCHAR.nullable(false), this, "");

    /**
     * The column <code>public.integration.creation_date</code>.
     */
    public final TableField<JIntegrationRecord, Instant> CREATION_DATE = createField(DSL.name("creation_date"), org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.field("now()", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "", new InstantConverter());

    /**
     * Create a <code>public.integration</code> table reference
     */
    public JIntegration() {
        this(DSL.name("integration"), null);
    }

    /**
     * Create an aliased <code>public.integration</code> table reference
     */
    public JIntegration(String alias) {
        this(DSL.name(alias), INTEGRATION);
    }

    /**
     * Create an aliased <code>public.integration</code> table reference
     */
    public JIntegration(Name alias) {
        this(alias, INTEGRATION);
    }

    private JIntegration(Name alias, Table<JIntegrationRecord> aliased) {
        this(alias, aliased, null);
    }

    private JIntegration(Name alias, Table<JIntegrationRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> JIntegration(Table<O> child, ForeignKey<O, JIntegrationRecord> key) {
        super(child, key, INTEGRATION);
    }

    @Override
    public Schema getSchema() {
        return JPublic.PUBLIC;
    }

    @Override
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.INTEGR_PROJECT_IDX, Indexes.INTEGRATION_PK, Indexes.UNIQUE_GLOBAL_INTEGRATION_NAME, Indexes.UNIQUE_PROJECT_INTEGRATION_NAME);
    }

    @Override
    public Identity<JIntegrationRecord, Integer> getIdentity() {
        return Keys.IDENTITY_INTEGRATION;
    }

    @Override
    public UniqueKey<JIntegrationRecord> getPrimaryKey() {
        return Keys.INTEGRATION_PK;
    }

    @Override
    public List<UniqueKey<JIntegrationRecord>> getKeys() {
        return Arrays.<UniqueKey<JIntegrationRecord>>asList(Keys.INTEGRATION_PK);
    }

    @Override
    public List<ForeignKey<JIntegrationRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<JIntegrationRecord, ?>>asList(Keys.INTEGRATION__INTEGRATION_PROJECT_ID_FKEY, Keys.INTEGRATION__INTEGRATION_TYPE_FKEY);
    }

    public JProject project() {
        return new JProject(this, Keys.INTEGRATION__INTEGRATION_PROJECT_ID_FKEY);
    }

    public JIntegrationType integrationType() {
        return new JIntegrationType(this, Keys.INTEGRATION__INTEGRATION_TYPE_FKEY);
    }

    @Override
    public JIntegration as(String alias) {
        return new JIntegration(DSL.name(alias), this);
    }

    @Override
    public JIntegration as(Name alias) {
        return new JIntegration(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public JIntegration rename(String name) {
        return new JIntegration(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public JIntegration rename(Name name) {
        return new JIntegration(name, null);
    }

    // -------------------------------------------------------------------------
    // Row8 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row8<Integer, String, Long, Integer, Boolean, JSONB, String, Instant> fieldsRow() {
        return (Row8) super.fieldsRow();
    }
}
