/*
 * This file is generated by jOOQ.
 */
package com.epam.ta.reportportal.jooq.tables;


import com.epam.ta.reportportal.jooq.Indexes;
import com.epam.ta.reportportal.jooq.JPublic;
import com.epam.ta.reportportal.jooq.Keys;
import com.epam.ta.reportportal.jooq.tables.records.JAclClassRecord;

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
public class JAclClass extends TableImpl<JAclClassRecord> {

    private static final long serialVersionUID = 1751246445;

    /**
     * The reference instance of <code>public.acl_class</code>
     */
    public static final JAclClass ACL_CLASS = new JAclClass();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<JAclClassRecord> getRecordType() {
        return JAclClassRecord.class;
    }

    /**
     * The column <code>public.acl_class.id</code>.
     */
    public final TableField<JAclClassRecord, Long> ID = createField("id", org.jooq.impl.SQLDataType.BIGINT.nullable(false).defaultValue(org.jooq.impl.DSL.field("nextval('acl_class_id_seq'::regclass)", org.jooq.impl.SQLDataType.BIGINT)), this, "");

    /**
     * The column <code>public.acl_class.class</code>.
     */
    public final TableField<JAclClassRecord, String> CLASS = createField("class", org.jooq.impl.SQLDataType.VARCHAR(100).nullable(false), this, "");

    /**
     * Create a <code>public.acl_class</code> table reference
     */
    public JAclClass() {
        this(DSL.name("acl_class"), null);
    }

    /**
     * Create an aliased <code>public.acl_class</code> table reference
     */
    public JAclClass(String alias) {
        this(DSL.name(alias), ACL_CLASS);
    }

    /**
     * Create an aliased <code>public.acl_class</code> table reference
     */
    public JAclClass(Name alias) {
        this(alias, ACL_CLASS);
    }

    private JAclClass(Name alias, Table<JAclClassRecord> aliased) {
        this(alias, aliased, null);
    }

    private JAclClass(Name alias, Table<JAclClassRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> JAclClass(Table<O> child, ForeignKey<O, JAclClassRecord> key) {
        super(child, key, ACL_CLASS);
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
        return Arrays.<Index>asList(Indexes.ACL_CLASS_PKEY, Indexes.UNIQUE_UK_2);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<JAclClassRecord, Long> getIdentity() {
        return Keys.IDENTITY_ACL_CLASS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<JAclClassRecord> getPrimaryKey() {
        return Keys.ACL_CLASS_PKEY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<JAclClassRecord>> getKeys() {
        return Arrays.<UniqueKey<JAclClassRecord>>asList(Keys.ACL_CLASS_PKEY, Keys.UNIQUE_UK_2);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JAclClass as(String alias) {
        return new JAclClass(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JAclClass as(Name alias) {
        return new JAclClass(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public JAclClass rename(String name) {
        return new JAclClass(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public JAclClass rename(Name name) {
        return new JAclClass(name, null);
    }
}
