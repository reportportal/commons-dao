/*
 * This file is generated by jOOQ.
 */
package com.epam.ta.reportportal.jooq.tables;


import com.epam.ta.reportportal.dao.converters.JooqInstantConverter;
import com.epam.ta.reportportal.jooq.JPublic;
import com.epam.ta.reportportal.jooq.Keys;
import com.epam.ta.reportportal.jooq.tables.JGroups.JGroupsPath;
import com.epam.ta.reportportal.jooq.tables.JProject.JProjectPath;
import com.epam.ta.reportportal.jooq.tables.records.JGroupsProjectsRecord;

import java.time.Instant;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.ForeignKey;
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
public class JGroupsProjects extends TableImpl<JGroupsProjectsRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>public.groups_projects</code>
     */
    public static final JGroupsProjects GROUPS_PROJECTS = new JGroupsProjects();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<JGroupsProjectsRecord> getRecordType() {
        return JGroupsProjectsRecord.class;
    }

    /**
     * The column <code>public.groups_projects.group_id</code>.
     */
    public final TableField<JGroupsProjectsRecord, Long> GROUP_ID = createField(DSL.name("group_id"), SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>public.groups_projects.project_id</code>.
     */
    public final TableField<JGroupsProjectsRecord, Long> PROJECT_ID = createField(DSL.name("project_id"), SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>public.groups_projects.project_role</code>.
     */
    public final TableField<JGroupsProjectsRecord, String> PROJECT_ROLE = createField(DSL.name("project_role"), SQLDataType.VARCHAR(255).nullable(false), this, "");

    /**
     * The column <code>public.groups_projects.created_at</code>.
     */
    public final TableField<JGroupsProjectsRecord, Instant> CREATED_AT = createField(DSL.name("created_at"), SQLDataType.LOCALDATETIME(6).nullable(false), this, "", new JooqInstantConverter());

    /**
     * The column <code>public.groups_projects.updated_at</code>.
     */
    public final TableField<JGroupsProjectsRecord, Instant> UPDATED_AT = createField(DSL.name("updated_at"), SQLDataType.LOCALDATETIME(6).nullable(false).defaultValue(DSL.field(DSL.raw("now()"), SQLDataType.LOCALDATETIME)), this, "", new JooqInstantConverter());

    private JGroupsProjects(Name alias, Table<JGroupsProjectsRecord> aliased) {
        this(alias, aliased, (Field<?>[]) null, null);
    }

    private JGroupsProjects(Name alias, Table<JGroupsProjectsRecord> aliased, Field<?>[] parameters, Condition where) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table(), where);
    }

    /**
     * Create an aliased <code>public.groups_projects</code> table reference
     */
    public JGroupsProjects(String alias) {
        this(DSL.name(alias), GROUPS_PROJECTS);
    }

    /**
     * Create an aliased <code>public.groups_projects</code> table reference
     */
    public JGroupsProjects(Name alias) {
        this(alias, GROUPS_PROJECTS);
    }

    /**
     * Create a <code>public.groups_projects</code> table reference
     */
    public JGroupsProjects() {
        this(DSL.name("groups_projects"), null);
    }

    public <O extends Record> JGroupsProjects(Table<O> path, ForeignKey<O, JGroupsProjectsRecord> childPath, InverseForeignKey<O, JGroupsProjectsRecord> parentPath) {
        super(path, childPath, parentPath, GROUPS_PROJECTS);
    }

    /**
     * A subtype implementing {@link Path} for simplified path-based joins.
     */
    public static class JGroupsProjectsPath extends JGroupsProjects implements Path<JGroupsProjectsRecord> {

        private static final long serialVersionUID = 1L;
        public <O extends Record> JGroupsProjectsPath(Table<O> path, ForeignKey<O, JGroupsProjectsRecord> childPath, InverseForeignKey<O, JGroupsProjectsRecord> parentPath) {
            super(path, childPath, parentPath);
        }
        private JGroupsProjectsPath(Name alias, Table<JGroupsProjectsRecord> aliased) {
            super(alias, aliased);
        }

        @Override
        public JGroupsProjectsPath as(String alias) {
            return new JGroupsProjectsPath(DSL.name(alias), this);
        }

        @Override
        public JGroupsProjectsPath as(Name alias) {
            return new JGroupsProjectsPath(alias, this);
        }

        @Override
        public JGroupsProjectsPath as(Table<?> alias) {
            return new JGroupsProjectsPath(alias.getQualifiedName(), this);
        }
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : JPublic.PUBLIC;
    }

    @Override
    public UniqueKey<JGroupsProjectsRecord> getPrimaryKey() {
        return Keys.GROUPS_PROJECTS_PKEY;
    }

    @Override
    public List<ForeignKey<JGroupsProjectsRecord, ?>> getReferences() {
        return Arrays.asList(Keys.GROUPS_PROJECTS__GROUPS_PROJECTS_GROUP_ID_FKEY, Keys.GROUPS_PROJECTS__GROUPS_PROJECTS_PROJECT_ID_FKEY);
    }

    private transient JGroupsPath _groups;

    /**
     * Get the implicit join path to the <code>public.groups</code> table.
     */
    public JGroupsPath groups() {
        if (_groups == null)
            _groups = new JGroupsPath(this, Keys.GROUPS_PROJECTS__GROUPS_PROJECTS_GROUP_ID_FKEY, null);

        return _groups;
    }

    private transient JProjectPath _project;

    /**
     * Get the implicit join path to the <code>public.project</code> table.
     */
    public JProjectPath project() {
        if (_project == null)
            _project = new JProjectPath(this, Keys.GROUPS_PROJECTS__GROUPS_PROJECTS_PROJECT_ID_FKEY, null);

        return _project;
    }

    @Override
    public JGroupsProjects as(String alias) {
        return new JGroupsProjects(DSL.name(alias), this);
    }

    @Override
    public JGroupsProjects as(Name alias) {
        return new JGroupsProjects(alias, this);
    }

    @Override
    public JGroupsProjects as(Table<?> alias) {
        return new JGroupsProjects(alias.getQualifiedName(), this);
    }

    /**
     * Rename this table
     */
    @Override
    public JGroupsProjects rename(String name) {
        return new JGroupsProjects(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public JGroupsProjects rename(Name name) {
        return new JGroupsProjects(name, null);
    }

    /**
     * Rename this table
     */
    @Override
    public JGroupsProjects rename(Table<?> name) {
        return new JGroupsProjects(name.getQualifiedName(), null);
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public JGroupsProjects where(Condition condition) {
        return new JGroupsProjects(getQualifiedName(), aliased() ? this : null, null, condition);
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public JGroupsProjects where(Collection<? extends Condition> conditions) {
        return where(DSL.and(conditions));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public JGroupsProjects where(Condition... conditions) {
        return where(DSL.and(conditions));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public JGroupsProjects where(Field<Boolean> condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public JGroupsProjects where(SQL condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public JGroupsProjects where(@Stringly.SQL String condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public JGroupsProjects where(@Stringly.SQL String condition, Object... binds) {
        return where(DSL.condition(condition, binds));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public JGroupsProjects where(@Stringly.SQL String condition, QueryPart... parts) {
        return where(DSL.condition(condition, parts));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public JGroupsProjects whereExists(Select<?> select) {
        return where(DSL.exists(select));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public JGroupsProjects whereNotExists(Select<?> select) {
        return where(DSL.notExists(select));
    }
}
