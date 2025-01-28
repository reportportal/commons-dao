/*
 * This file is generated by jOOQ.
 */
package com.epam.ta.reportportal.jooq.tables;


import com.epam.ta.reportportal.dao.converters.JooqInstantConverter;
import com.epam.ta.reportportal.jooq.JPublic;
import com.epam.ta.reportportal.jooq.Keys;
import com.epam.ta.reportportal.jooq.tables.JActivity.JActivityPath;
import com.epam.ta.reportportal.jooq.tables.JAttribute.JAttributePath;
import com.epam.ta.reportportal.jooq.tables.JIntegration.JIntegrationPath;
import com.epam.ta.reportportal.jooq.tables.JIssueType.JIssueTypePath;
import com.epam.ta.reportportal.jooq.tables.JIssueTypeProject.JIssueTypeProjectPath;
import com.epam.ta.reportportal.jooq.tables.JLaunch.JLaunchPath;
import com.epam.ta.reportportal.jooq.tables.JLaunchNumber.JLaunchNumberPath;
import com.epam.ta.reportportal.jooq.tables.JOwnedEntity.JOwnedEntityPath;
import com.epam.ta.reportportal.jooq.tables.JPatternTemplate.JPatternTemplatePath;
import com.epam.ta.reportportal.jooq.tables.JProjectAttribute.JProjectAttributePath;
import com.epam.ta.reportportal.jooq.tables.JProjectUser.JProjectUserPath;
import com.epam.ta.reportportal.jooq.tables.JSenderCase.JSenderCasePath;
import com.epam.ta.reportportal.jooq.tables.JUserPreference.JUserPreferencePath;
import com.epam.ta.reportportal.jooq.tables.JUsers.JUsersPath;
import com.epam.ta.reportportal.jooq.tables.records.JProjectRecord;

import java.time.Instant;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.InverseForeignKey;
import org.jooq.JSONB;
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
public class JProject extends TableImpl<JProjectRecord> {

    private static final long serialVersionUID = 1L;

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
    public final TableField<JProjectRecord, Long> ID = createField(DSL.name("id"), SQLDataType.BIGINT.nullable(false).identity(true), this, "");

    /**
     * The column <code>public.project.name</code>.
     */
    public final TableField<JProjectRecord, String> NAME = createField(DSL.name("name"), SQLDataType.VARCHAR.nullable(false), this, "");

    /**
     * The column <code>public.project.project_type</code>.
     */
    public final TableField<JProjectRecord, String> PROJECT_TYPE = createField(DSL.name("project_type"), SQLDataType.VARCHAR.nullable(false), this, "");

    /**
     * The column <code>public.project.organization</code>.
     */
    public final TableField<JProjectRecord, String> ORGANIZATION = createField(DSL.name("organization"), SQLDataType.VARCHAR, this, "");

    /**
     * The column <code>public.project.creation_date</code>.
     */
    public final TableField<JProjectRecord, Instant> CREATION_DATE = createField(DSL.name("creation_date"), SQLDataType.LOCALDATETIME(6).nullable(false).defaultValue(DSL.field(DSL.raw("now()"), SQLDataType.LOCALDATETIME)), this, "", new JooqInstantConverter());

    /**
     * The column <code>public.project.metadata</code>.
     */
    public final TableField<JProjectRecord, JSONB> METADATA = createField(DSL.name("metadata"), SQLDataType.JSONB, this, "");

    /**
     * The column <code>public.project.allocated_storage</code>.
     */
    public final TableField<JProjectRecord, Long> ALLOCATED_STORAGE = createField(DSL.name("allocated_storage"), SQLDataType.BIGINT.nullable(false).defaultValue(DSL.field(DSL.raw("0"), SQLDataType.BIGINT)), this, "");

    private JProject(Name alias, Table<JProjectRecord> aliased) {
        this(alias, aliased, (Field<?>[]) null, null);
    }

    private JProject(Name alias, Table<JProjectRecord> aliased, Field<?>[] parameters, Condition where) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table(), where);
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

    /**
     * Create a <code>public.project</code> table reference
     */
    public JProject() {
        this(DSL.name("project"), null);
    }

    public <O extends Record> JProject(Table<O> path, ForeignKey<O, JProjectRecord> childPath, InverseForeignKey<O, JProjectRecord> parentPath) {
        super(path, childPath, parentPath, PROJECT);
    }

    /**
     * A subtype implementing {@link Path} for simplified path-based joins.
     */
    public static class JProjectPath extends JProject implements Path<JProjectRecord> {

        private static final long serialVersionUID = 1L;
        public <O extends Record> JProjectPath(Table<O> path, ForeignKey<O, JProjectRecord> childPath, InverseForeignKey<O, JProjectRecord> parentPath) {
            super(path, childPath, parentPath);
        }
        private JProjectPath(Name alias, Table<JProjectRecord> aliased) {
            super(alias, aliased);
        }

        @Override
        public JProjectPath as(String alias) {
            return new JProjectPath(DSL.name(alias), this);
        }

        @Override
        public JProjectPath as(Name alias) {
            return new JProjectPath(alias, this);
        }

        @Override
        public JProjectPath as(Table<?> alias) {
            return new JProjectPath(alias.getQualifiedName(), this);
        }
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : JPublic.PUBLIC;
    }

    @Override
    public Identity<JProjectRecord, Long> getIdentity() {
        return (Identity<JProjectRecord, Long>) super.getIdentity();
    }

    @Override
    public UniqueKey<JProjectRecord> getPrimaryKey() {
        return Keys.PROJECT_PK;
    }

    @Override
    public List<UniqueKey<JProjectRecord>> getUniqueKeys() {
        return Arrays.asList(Keys.PROJECT_NAME_KEY);
    }

    private transient JActivityPath _activity;

    /**
     * Get the implicit to-many join path to the <code>public.activity</code>
     * table
     */
    public JActivityPath activity() {
        if (_activity == null)
            _activity = new JActivityPath(this, null, Keys.ACTIVITY__ACTIVITY_PROJECT_ID_FKEY.getInverseKey());

        return _activity;
    }

    private transient JIntegrationPath _integration;

    /**
     * Get the implicit to-many join path to the <code>public.integration</code>
     * table
     */
    public JIntegrationPath integration() {
        if (_integration == null)
            _integration = new JIntegrationPath(this, null, Keys.INTEGRATION__INTEGRATION_PROJECT_ID_FKEY.getInverseKey());

        return _integration;
    }

    private transient JIssueTypeProjectPath _issueTypeProject;

    /**
     * Get the implicit to-many join path to the
     * <code>public.issue_type_project</code> table
     */
    public JIssueTypeProjectPath issueTypeProject() {
        if (_issueTypeProject == null)
            _issueTypeProject = new JIssueTypeProjectPath(this, null, Keys.ISSUE_TYPE_PROJECT__ISSUE_TYPE_PROJECT_PROJECT_ID_FKEY.getInverseKey());

        return _issueTypeProject;
    }

    private transient JLaunchNumberPath _launchNumber;

    /**
     * Get the implicit to-many join path to the
     * <code>public.launch_number</code> table
     */
    public JLaunchNumberPath launchNumber() {
        if (_launchNumber == null)
            _launchNumber = new JLaunchNumberPath(this, null, Keys.LAUNCH_NUMBER__LAUNCH_NUMBER_PROJECT_ID_FKEY.getInverseKey());

        return _launchNumber;
    }

    private transient JLaunchPath _launch;

    /**
     * Get the implicit to-many join path to the <code>public.launch</code>
     * table
     */
    public JLaunchPath launch() {
        if (_launch == null)
            _launch = new JLaunchPath(this, null, Keys.LAUNCH__LAUNCH_PROJECT_ID_FKEY.getInverseKey());

        return _launch;
    }

    private transient JPatternTemplatePath _patternTemplate;

    /**
     * Get the implicit to-many join path to the
     * <code>public.pattern_template</code> table
     */
    public JPatternTemplatePath patternTemplate() {
        if (_patternTemplate == null)
            _patternTemplate = new JPatternTemplatePath(this, null, Keys.PATTERN_TEMPLATE__PATTERN_TEMPLATE_PROJECT_ID_FKEY.getInverseKey());

        return _patternTemplate;
    }

    private transient JProjectAttributePath _projectAttribute;

    /**
     * Get the implicit to-many join path to the
     * <code>public.project_attribute</code> table
     */
    public JProjectAttributePath projectAttribute() {
        if (_projectAttribute == null)
            _projectAttribute = new JProjectAttributePath(this, null, Keys.PROJECT_ATTRIBUTE__PROJECT_ATTRIBUTE_PROJECT_ID_FKEY.getInverseKey());

        return _projectAttribute;
    }

    private transient JProjectUserPath _projectUser;

    /**
     * Get the implicit to-many join path to the
     * <code>public.project_user</code> table
     */
    public JProjectUserPath projectUser() {
        if (_projectUser == null)
            _projectUser = new JProjectUserPath(this, null, Keys.PROJECT_USER__PROJECT_USER_PROJECT_ID_FKEY.getInverseKey());

        return _projectUser;
    }

    private transient JSenderCasePath _senderCase;

    /**
     * Get the implicit to-many join path to the <code>public.sender_case</code>
     * table
     */
    public JSenderCasePath senderCase() {
        if (_senderCase == null)
            _senderCase = new JSenderCasePath(this, null, Keys.SENDER_CASE__SENDER_CASE_PROJECT_ID_FKEY.getInverseKey());

        return _senderCase;
    }

    private transient JOwnedEntityPath _ownedEntity;

    /**
     * Get the implicit to-many join path to the
     * <code>public.owned_entity</code> table
     */
    public JOwnedEntityPath ownedEntity() {
        if (_ownedEntity == null)
            _ownedEntity = new JOwnedEntityPath(this, null, Keys.OWNED_ENTITY__SHAREABLE_ENTITY_PROJECT_ID_FKEY.getInverseKey());

        return _ownedEntity;
    }

    private transient JUserPreferencePath _userPreference;

    /**
     * Get the implicit to-many join path to the
     * <code>public.user_preference</code> table
     */
    public JUserPreferencePath userPreference() {
        if (_userPreference == null)
            _userPreference = new JUserPreferencePath(this, null, Keys.USER_PREFERENCE__USER_PREFERENCE_PROJECT_ID_FKEY.getInverseKey());

        return _userPreference;
    }

    /**
     * Get the implicit many-to-many join path to the
     * <code>public.issue_type</code> table
     */
    public JIssueTypePath issueType() {
        return issueTypeProject().issueType();
    }

    /**
     * Get the implicit many-to-many join path to the
     * <code>public.attribute</code> table
     */
    public JAttributePath attribute() {
        return projectAttribute().attribute();
    }

    /**
     * Get the implicit many-to-many join path to the <code>public.users</code>
     * table
     */
    public JUsersPath users() {
        return projectUser().users();
    }

    @Override
    public JProject as(String alias) {
        return new JProject(DSL.name(alias), this);
    }

    @Override
    public JProject as(Name alias) {
        return new JProject(alias, this);
    }

    @Override
    public JProject as(Table<?> alias) {
        return new JProject(alias.getQualifiedName(), this);
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

    /**
     * Rename this table
     */
    @Override
    public JProject rename(Table<?> name) {
        return new JProject(name.getQualifiedName(), null);
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public JProject where(Condition condition) {
        return new JProject(getQualifiedName(), aliased() ? this : null, null, condition);
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public JProject where(Collection<? extends Condition> conditions) {
        return where(DSL.and(conditions));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public JProject where(Condition... conditions) {
        return where(DSL.and(conditions));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public JProject where(Field<Boolean> condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public JProject where(SQL condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public JProject where(@Stringly.SQL String condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public JProject where(@Stringly.SQL String condition, Object... binds) {
        return where(DSL.condition(condition, binds));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public JProject where(@Stringly.SQL String condition, QueryPart... parts) {
        return where(DSL.condition(condition, parts));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public JProject whereExists(Select<?> select) {
        return where(DSL.exists(select));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public JProject whereNotExists(Select<?> select) {
        return where(DSL.notExists(select));
    }
}
