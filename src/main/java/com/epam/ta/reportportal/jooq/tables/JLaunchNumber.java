/*
 * This file is generated by jOOQ.
 */
package com.epam.ta.reportportal.jooq.tables;


import com.epam.ta.reportportal.jooq.Indexes;
import com.epam.ta.reportportal.jooq.JPublic;
import com.epam.ta.reportportal.jooq.Keys;
import com.epam.ta.reportportal.jooq.tables.records.JLaunchNumberRecord;

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
        "jOOQ version:3.11.11"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class JLaunchNumber extends TableImpl<JLaunchNumberRecord> {

    private static final long serialVersionUID = -925876683;

    /**
     * The reference instance of <code>public.launch_number</code>
     */
    public static final JLaunchNumber LAUNCH_NUMBER = new JLaunchNumber();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<JLaunchNumberRecord> getRecordType() {
        return JLaunchNumberRecord.class;
    }

    /**
     * The column <code>public.launch_number.id</code>.
     */
    public final TableField<JLaunchNumberRecord, Long> ID = createField("id", org.jooq.impl.SQLDataType.BIGINT.nullable(false).defaultValue(org.jooq.impl.DSL.field("nextval('launch_number_id_seq'::regclass)", org.jooq.impl.SQLDataType.BIGINT)), this, "");

    /**
     * The column <code>public.launch_number.project_id</code>.
     */
    public final TableField<JLaunchNumberRecord, Long> PROJECT_ID = createField("project_id", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>public.launch_number.launch_name</code>.
     */
    public final TableField<JLaunchNumberRecord, String> LAUNCH_NAME = createField("launch_name", org.jooq.impl.SQLDataType.VARCHAR(256).nullable(false), this, "");

    /**
     * The column <code>public.launch_number.number</code>.
     */
    public final TableField<JLaunchNumberRecord, Integer> NUMBER = createField("number", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * Create a <code>public.launch_number</code> table reference
     */
    public JLaunchNumber() {
        this(DSL.name("launch_number"), null);
    }

    /**
     * Create an aliased <code>public.launch_number</code> table reference
     */
    public JLaunchNumber(String alias) {
        this(DSL.name(alias), LAUNCH_NUMBER);
    }

    /**
     * Create an aliased <code>public.launch_number</code> table reference
     */
    public JLaunchNumber(Name alias) {
        this(alias, LAUNCH_NUMBER);
    }

    private JLaunchNumber(Name alias, Table<JLaunchNumberRecord> aliased) {
        this(alias, aliased, null);
    }

    private JLaunchNumber(Name alias, Table<JLaunchNumberRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> JLaunchNumber(Table<O> child, ForeignKey<O, JLaunchNumberRecord> key) {
        super(child, key, LAUNCH_NUMBER);
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
        return Arrays.<Index>asList(Indexes.LAUNCH_NUMBER_PK, Indexes.UNQ_PROJECT_NAME);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<JLaunchNumberRecord, Long> getIdentity() {
        return Keys.IDENTITY_LAUNCH_NUMBER;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<JLaunchNumberRecord> getPrimaryKey() {
        return Keys.LAUNCH_NUMBER_PK;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<JLaunchNumberRecord>> getKeys() {
        return Arrays.<UniqueKey<JLaunchNumberRecord>>asList(Keys.LAUNCH_NUMBER_PK, Keys.UNQ_PROJECT_NAME);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<JLaunchNumberRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<JLaunchNumberRecord, ?>>asList(Keys.LAUNCH_NUMBER__LAUNCH_NUMBER_PROJECT_ID_FKEY);
    }

    public JProject project() {
        return new JProject(this, Keys.LAUNCH_NUMBER__LAUNCH_NUMBER_PROJECT_ID_FKEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JLaunchNumber as(String alias) {
        return new JLaunchNumber(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JLaunchNumber as(Name alias) {
        return new JLaunchNumber(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public JLaunchNumber rename(String name) {
        return new JLaunchNumber(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public JLaunchNumber rename(Name name) {
        return new JLaunchNumber(name, null);
    }
}
