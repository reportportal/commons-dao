/*
 * This file is generated by jOOQ.
 */
package com.epam.ta.reportportal.jooq.tables;

import com.epam.ta.reportportal.jooq.Indexes;
import com.epam.ta.reportportal.jooq.JPublic;
import com.epam.ta.reportportal.jooq.Keys;
import com.epam.ta.reportportal.jooq.tables.records.JProjectAttributeRecord;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;

import javax.annotation.Generated;
import java.util.Arrays;
import java.util.List;


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
public class JProjectAttribute extends TableImpl<JProjectAttributeRecord> {

    private static final long serialVersionUID = -1706040162;

    /**
     * The reference instance of <code>public.project_attribute</code>
     */
    public static final JProjectAttribute PROJECT_ATTRIBUTE = new JProjectAttribute();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<JProjectAttributeRecord> getRecordType() {
        return JProjectAttributeRecord.class;
    }

    /**
     * The column <code>public.project_attribute.attribute_id</code>.
     */
    public final TableField<JProjectAttributeRecord, Long> ATTRIBUTE_ID = createField("attribute_id", org.jooq.impl.SQLDataType.BIGINT.nullable(false).defaultValue(org.jooq.impl.DSL.field("nextval('project_attribute_attribute_id_seq'::regclass)", org.jooq.impl.SQLDataType.BIGINT)), this, "");

    /**
     * The column <code>public.project_attribute.value</code>.
     */
    public final TableField<JProjectAttributeRecord, String> VALUE = createField("value", org.jooq.impl.SQLDataType.VARCHAR(256).nullable(false), this, "");

    /**
     * The column <code>public.project_attribute.project_id</code>.
     */
    public final TableField<JProjectAttributeRecord, Long> PROJECT_ID = createField("project_id", org.jooq.impl.SQLDataType.BIGINT.nullable(false).defaultValue(org.jooq.impl.DSL.field("nextval('project_attribute_project_id_seq'::regclass)", org.jooq.impl.SQLDataType.BIGINT)), this, "");

    /**
     * Create a <code>public.project_attribute</code> table reference
     */
    public JProjectAttribute() {
        this(DSL.name("project_attribute"), null);
    }

    /**
     * Create an aliased <code>public.project_attribute</code> table reference
     */
    public JProjectAttribute(String alias) {
        this(DSL.name(alias), PROJECT_ATTRIBUTE);
    }

    /**
     * Create an aliased <code>public.project_attribute</code> table reference
     */
    public JProjectAttribute(Name alias) {
        this(alias, PROJECT_ATTRIBUTE);
    }

    private JProjectAttribute(Name alias, Table<JProjectAttributeRecord> aliased) {
        this(alias, aliased, null);
    }

    private JProjectAttribute(Name alias, Table<JProjectAttributeRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> JProjectAttribute(Table<O> child, ForeignKey<O, JProjectAttributeRecord> key) {
        super(child, key, PROJECT_ATTRIBUTE);
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
        return Arrays.<Index>asList(Indexes.UNIQUE_ATTRIBUTE_PER_PROJECT);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<JProjectAttributeRecord, Long> getIdentity() {
        return Keys.IDENTITY_PROJECT_ATTRIBUTE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<JProjectAttributeRecord> getPrimaryKey() {
        return Keys.UNIQUE_ATTRIBUTE_PER_PROJECT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<JProjectAttributeRecord>> getKeys() {
        return Arrays.<UniqueKey<JProjectAttributeRecord>>asList(Keys.UNIQUE_ATTRIBUTE_PER_PROJECT);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<JProjectAttributeRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<JProjectAttributeRecord, ?>>asList(Keys.PROJECT_ATTRIBUTE__PROJECT_ATTRIBUTE_ATTRIBUTE_ID_FKEY, Keys.PROJECT_ATTRIBUTE__PROJECT_ATTRIBUTE_PROJECT_ID_FKEY);
    }

    public JAttribute attribute() {
        return new JAttribute(this, Keys.PROJECT_ATTRIBUTE__PROJECT_ATTRIBUTE_ATTRIBUTE_ID_FKEY);
    }

    public JProject project() {
        return new JProject(this, Keys.PROJECT_ATTRIBUTE__PROJECT_ATTRIBUTE_PROJECT_ID_FKEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JProjectAttribute as(String alias) {
        return new JProjectAttribute(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JProjectAttribute as(Name alias) {
        return new JProjectAttribute(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public JProjectAttribute rename(String name) {
        return new JProjectAttribute(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public JProjectAttribute rename(Name name) {
        return new JProjectAttribute(name, null);
    }
}
