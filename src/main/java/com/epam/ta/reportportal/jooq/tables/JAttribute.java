/*
 * This file is generated by jOOQ.
 */
package com.epam.ta.reportportal.jooq.tables;


import com.epam.ta.reportportal.jooq.JPublic;
import com.epam.ta.reportportal.jooq.Keys;
import com.epam.ta.reportportal.jooq.tables.JProject.JProjectPath;
import com.epam.ta.reportportal.jooq.tables.JProjectAttribute.JProjectAttributePath;
import com.epam.ta.reportportal.jooq.tables.records.JAttributeRecord;

import java.util.Collection;

import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.InverseForeignKey;
import org.jooq.Name;
import org.jooq.Path;
import org.jooq.PlainSQL;
import org.jooq.QueryPart;
import org.jooq.Record;
import org.jooq.SQL;
import org.jooq.Schema;
import org.jooq.Select;
import org.jooq.Stringly;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes", "this-escape" })
public class JAttribute extends TableImpl<JAttributeRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>public.attribute</code>
     */
    public static final JAttribute ATTRIBUTE = new JAttribute();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<JAttributeRecord> getRecordType() {
        return JAttributeRecord.class;
    }

    /**
     * The column <code>public.attribute.id</code>.
     */
    public final TableField<JAttributeRecord, Long> ID = createField(DSL.name("id"), SQLDataType.BIGINT.nullable(false).identity(true), this, "");

    /**
     * The column <code>public.attribute.name</code>.
     */
    public final TableField<JAttributeRecord, String> NAME = createField(DSL.name("name"), SQLDataType.VARCHAR(256), this, "");

    private JAttribute(Name alias, Table<JAttributeRecord> aliased) {
        this(alias, aliased, (Field<?>[]) null, null);
    }

    private JAttribute(Name alias, Table<JAttributeRecord> aliased, Field<?>[] parameters, Condition where) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table(), where);
    }

    /**
     * Create an aliased <code>public.attribute</code> table reference
     */
    public JAttribute(String alias) {
        this(DSL.name(alias), ATTRIBUTE);
    }

    /**
     * Create an aliased <code>public.attribute</code> table reference
     */
    public JAttribute(Name alias) {
        this(alias, ATTRIBUTE);
    }

    /**
     * Create a <code>public.attribute</code> table reference
     */
    public JAttribute() {
        this(DSL.name("attribute"), null);
    }

    public <O extends Record> JAttribute(Table<O> path, ForeignKey<O, JAttributeRecord> childPath, InverseForeignKey<O, JAttributeRecord> parentPath) {
        super(path, childPath, parentPath, ATTRIBUTE);
    }

    /**
     * A subtype implementing {@link Path} for simplified path-based joins.
     */
    public static class JAttributePath extends JAttribute implements Path<JAttributeRecord> {

        private static final long serialVersionUID = 1L;
        public <O extends Record> JAttributePath(Table<O> path, ForeignKey<O, JAttributeRecord> childPath, InverseForeignKey<O, JAttributeRecord> parentPath) {
            super(path, childPath, parentPath);
        }
        private JAttributePath(Name alias, Table<JAttributeRecord> aliased) {
            super(alias, aliased);
        }

        @Override
        public JAttributePath as(String alias) {
            return new JAttributePath(DSL.name(alias), this);
        }

        @Override
        public JAttributePath as(Name alias) {
            return new JAttributePath(alias, this);
        }

        @Override
        public JAttributePath as(Table<?> alias) {
            return new JAttributePath(alias.getQualifiedName(), this);
        }
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : JPublic.PUBLIC;
    }

    @Override
    public Identity<JAttributeRecord, Long> getIdentity() {
        return (Identity<JAttributeRecord, Long>) super.getIdentity();
    }

    @Override
    public UniqueKey<JAttributeRecord> getPrimaryKey() {
        return Keys.ATTRIBUTE_PK;
    }

    private transient JProjectAttributePath _projectAttribute;

    /**
     * Get the implicit to-many join path to the
     * <code>public.project_attribute</code> table
     */
    public JProjectAttributePath projectAttribute() {
        if (_projectAttribute == null)
            _projectAttribute = new JProjectAttributePath(this, null, Keys.PROJECT_ATTRIBUTE__PROJECT_ATTRIBUTE_ATTRIBUTE_ID_FKEY.getInverseKey());

        return _projectAttribute;
    }

    /**
     * Get the implicit many-to-many join path to the
     * <code>public.project</code> table
     */
    public JProjectPath project() {
        return projectAttribute().project();
    }

    @Override
    public JAttribute as(String alias) {
        return new JAttribute(DSL.name(alias), this);
    }

    @Override
    public JAttribute as(Name alias) {
        return new JAttribute(alias, this);
    }

    @Override
    public JAttribute as(Table<?> alias) {
        return new JAttribute(alias.getQualifiedName(), this);
    }

    /**
     * Rename this table
     */
    @Override
    public JAttribute rename(String name) {
        return new JAttribute(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public JAttribute rename(Name name) {
        return new JAttribute(name, null);
    }

    /**
     * Rename this table
     */
    @Override
    public JAttribute rename(Table<?> name) {
        return new JAttribute(name.getQualifiedName(), null);
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public JAttribute where(Condition condition) {
        return new JAttribute(getQualifiedName(), aliased() ? this : null, null, condition);
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public JAttribute where(Collection<? extends Condition> conditions) {
        return where(DSL.and(conditions));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public JAttribute where(Condition... conditions) {
        return where(DSL.and(conditions));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public JAttribute where(Field<Boolean> condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public JAttribute where(SQL condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public JAttribute where(@Stringly.SQL String condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public JAttribute where(@Stringly.SQL String condition, Object... binds) {
        return where(DSL.condition(condition, binds));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public JAttribute where(@Stringly.SQL String condition, QueryPart... parts) {
        return where(DSL.condition(condition, parts));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public JAttribute whereExists(Select<?> select) {
        return where(DSL.exists(select));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public JAttribute whereNotExists(Select<?> select) {
        return where(DSL.notExists(select));
    }
}
