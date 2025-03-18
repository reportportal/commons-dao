/*
 * This file is generated by jOOQ.
 */
package com.epam.ta.reportportal.jooq.tables;


import com.epam.ta.reportportal.dao.converters.JooqInstantConverter;
import com.epam.ta.reportportal.jooq.JPublic;
import com.epam.ta.reportportal.jooq.Keys;
import com.epam.ta.reportportal.jooq.enums.JOrganizationRoleEnum;
import com.epam.ta.reportportal.jooq.tables.JOrganization.JOrganizationPath;
import com.epam.ta.reportportal.jooq.tables.JUsers.JUsersPath;
import com.epam.ta.reportportal.jooq.tables.records.JOrganizationUserRecord;

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
public class JOrganizationUser extends TableImpl<JOrganizationUserRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>public.organization_user</code>
     */
    public static final JOrganizationUser ORGANIZATION_USER = new JOrganizationUser();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<JOrganizationUserRecord> getRecordType() {
        return JOrganizationUserRecord.class;
    }

    /**
     * The column <code>public.organization_user.user_id</code>.
     */
    public final TableField<JOrganizationUserRecord, Long> USER_ID = createField(DSL.name("user_id"), SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>public.organization_user.organization_id</code>.
     */
    public final TableField<JOrganizationUserRecord, Long> ORGANIZATION_ID = createField(DSL.name("organization_id"), SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>public.organization_user.organization_role</code>.
     */
    public final TableField<JOrganizationUserRecord, JOrganizationRoleEnum> ORGANIZATION_ROLE = createField(DSL.name("organization_role"), SQLDataType.VARCHAR.nullable(false).asEnumDataType(JOrganizationRoleEnum.class), this, "");

    /**
     * The column <code>public.organization_user.assigned_at</code>.
     */
    public final TableField<JOrganizationUserRecord, Instant> ASSIGNED_AT = createField(DSL.name("assigned_at"), SQLDataType.LOCALDATETIME(6).nullable(false).defaultValue(DSL.field(DSL.raw("now()"), SQLDataType.LOCALDATETIME)), this, "", new JooqInstantConverter());

    private JOrganizationUser(Name alias, Table<JOrganizationUserRecord> aliased) {
        this(alias, aliased, (Field<?>[]) null, null);
    }

    private JOrganizationUser(Name alias, Table<JOrganizationUserRecord> aliased, Field<?>[] parameters, Condition where) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table(), where);
    }

    /**
     * Create an aliased <code>public.organization_user</code> table reference
     */
    public JOrganizationUser(String alias) {
        this(DSL.name(alias), ORGANIZATION_USER);
    }

    /**
     * Create an aliased <code>public.organization_user</code> table reference
     */
    public JOrganizationUser(Name alias) {
        this(alias, ORGANIZATION_USER);
    }

    /**
     * Create a <code>public.organization_user</code> table reference
     */
    public JOrganizationUser() {
        this(DSL.name("organization_user"), null);
    }

    public <O extends Record> JOrganizationUser(Table<O> path, ForeignKey<O, JOrganizationUserRecord> childPath, InverseForeignKey<O, JOrganizationUserRecord> parentPath) {
        super(path, childPath, parentPath, ORGANIZATION_USER);
    }

    /**
     * A subtype implementing {@link Path} for simplified path-based joins.
     */
    public static class JOrganizationUserPath extends JOrganizationUser implements Path<JOrganizationUserRecord> {

        private static final long serialVersionUID = 1L;
        public <O extends Record> JOrganizationUserPath(Table<O> path, ForeignKey<O, JOrganizationUserRecord> childPath, InverseForeignKey<O, JOrganizationUserRecord> parentPath) {
            super(path, childPath, parentPath);
        }
        private JOrganizationUserPath(Name alias, Table<JOrganizationUserRecord> aliased) {
            super(alias, aliased);
        }

        @Override
        public JOrganizationUserPath as(String alias) {
            return new JOrganizationUserPath(DSL.name(alias), this);
        }

        @Override
        public JOrganizationUserPath as(Name alias) {
            return new JOrganizationUserPath(alias, this);
        }

        @Override
        public JOrganizationUserPath as(Table<?> alias) {
            return new JOrganizationUserPath(alias.getQualifiedName(), this);
        }
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : JPublic.PUBLIC;
    }

    @Override
    public UniqueKey<JOrganizationUserRecord> getPrimaryKey() {
        return Keys.ORGANIZATION_USER_PK;
    }

    @Override
    public List<ForeignKey<JOrganizationUserRecord, ?>> getReferences() {
        return Arrays.asList(Keys.ORGANIZATION_USER__ORGANIZATION_USER_ORGANIZATION_ID_FKEY, Keys.ORGANIZATION_USER__ORGANIZATION_USER_USER_ID_FKEY);
    }

    private transient JOrganizationPath _organization;

    /**
     * Get the implicit join path to the <code>public.organization</code> table.
     */
    public JOrganizationPath organization() {
        if (_organization == null)
            _organization = new JOrganizationPath(this, Keys.ORGANIZATION_USER__ORGANIZATION_USER_ORGANIZATION_ID_FKEY, null);

        return _organization;
    }

    private transient JUsersPath _users;

    /**
     * Get the implicit join path to the <code>public.users</code> table.
     */
    public JUsersPath users() {
        if (_users == null)
            _users = new JUsersPath(this, Keys.ORGANIZATION_USER__ORGANIZATION_USER_USER_ID_FKEY, null);

        return _users;
    }

    @Override
    public JOrganizationUser as(String alias) {
        return new JOrganizationUser(DSL.name(alias), this);
    }

    @Override
    public JOrganizationUser as(Name alias) {
        return new JOrganizationUser(alias, this);
    }

    @Override
    public JOrganizationUser as(Table<?> alias) {
        return new JOrganizationUser(alias.getQualifiedName(), this);
    }

    /**
     * Rename this table
     */
    @Override
    public JOrganizationUser rename(String name) {
        return new JOrganizationUser(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public JOrganizationUser rename(Name name) {
        return new JOrganizationUser(name, null);
    }

    /**
     * Rename this table
     */
    @Override
    public JOrganizationUser rename(Table<?> name) {
        return new JOrganizationUser(name.getQualifiedName(), null);
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public JOrganizationUser where(Condition condition) {
        return new JOrganizationUser(getQualifiedName(), aliased() ? this : null, null, condition);
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public JOrganizationUser where(Collection<? extends Condition> conditions) {
        return where(DSL.and(conditions));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public JOrganizationUser where(Condition... conditions) {
        return where(DSL.and(conditions));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public JOrganizationUser where(Field<Boolean> condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public JOrganizationUser where(SQL condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public JOrganizationUser where(@Stringly.SQL String condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public JOrganizationUser where(@Stringly.SQL String condition, Object... binds) {
        return where(DSL.condition(condition, binds));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public JOrganizationUser where(@Stringly.SQL String condition, QueryPart... parts) {
        return where(DSL.condition(condition, parts));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public JOrganizationUser whereExists(Select<?> select) {
        return where(DSL.exists(select));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public JOrganizationUser whereNotExists(Select<?> select) {
        return where(DSL.notExists(select));
    }
}
