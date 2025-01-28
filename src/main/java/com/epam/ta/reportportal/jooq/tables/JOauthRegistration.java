/*
 * This file is generated by jOOQ.
 */
package com.epam.ta.reportportal.jooq.tables;


import com.epam.ta.reportportal.jooq.JPublic;
import com.epam.ta.reportportal.jooq.Keys;
import com.epam.ta.reportportal.jooq.tables.JOauthRegistrationRestriction.JOauthRegistrationRestrictionPath;
import com.epam.ta.reportportal.jooq.tables.JOauthRegistrationScope.JOauthRegistrationScopePath;
import com.epam.ta.reportportal.jooq.tables.records.JOauthRegistrationRecord;

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
public class JOauthRegistration extends TableImpl<JOauthRegistrationRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>public.oauth_registration</code>
     */
    public static final JOauthRegistration OAUTH_REGISTRATION = new JOauthRegistration();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<JOauthRegistrationRecord> getRecordType() {
        return JOauthRegistrationRecord.class;
    }

    /**
     * The column <code>public.oauth_registration.id</code>.
     */
    public final TableField<JOauthRegistrationRecord, String> ID = createField(DSL.name("id"), SQLDataType.VARCHAR(64).nullable(false), this, "");

    /**
     * The column <code>public.oauth_registration.client_id</code>.
     */
    public final TableField<JOauthRegistrationRecord, String> CLIENT_ID = createField(DSL.name("client_id"), SQLDataType.VARCHAR(128).nullable(false), this, "");

    /**
     * The column <code>public.oauth_registration.client_secret</code>.
     */
    public final TableField<JOauthRegistrationRecord, String> CLIENT_SECRET = createField(DSL.name("client_secret"), SQLDataType.VARCHAR(256), this, "");

    /**
     * The column <code>public.oauth_registration.client_auth_method</code>.
     */
    public final TableField<JOauthRegistrationRecord, String> CLIENT_AUTH_METHOD = createField(DSL.name("client_auth_method"), SQLDataType.VARCHAR(64).nullable(false), this, "");

    /**
     * The column <code>public.oauth_registration.auth_grant_type</code>.
     */
    public final TableField<JOauthRegistrationRecord, String> AUTH_GRANT_TYPE = createField(DSL.name("auth_grant_type"), SQLDataType.VARCHAR(64), this, "");

    /**
     * The column <code>public.oauth_registration.redirect_uri_template</code>.
     */
    public final TableField<JOauthRegistrationRecord, String> REDIRECT_URI_TEMPLATE = createField(DSL.name("redirect_uri_template"), SQLDataType.VARCHAR(256), this, "");

    /**
     * The column <code>public.oauth_registration.authorization_uri</code>.
     */
    public final TableField<JOauthRegistrationRecord, String> AUTHORIZATION_URI = createField(DSL.name("authorization_uri"), SQLDataType.VARCHAR(256), this, "");

    /**
     * The column <code>public.oauth_registration.token_uri</code>.
     */
    public final TableField<JOauthRegistrationRecord, String> TOKEN_URI = createField(DSL.name("token_uri"), SQLDataType.VARCHAR(256), this, "");

    /**
     * The column <code>public.oauth_registration.user_info_endpoint_uri</code>.
     */
    public final TableField<JOauthRegistrationRecord, String> USER_INFO_ENDPOINT_URI = createField(DSL.name("user_info_endpoint_uri"), SQLDataType.VARCHAR(256), this, "");

    /**
     * The column
     * <code>public.oauth_registration.user_info_endpoint_name_attr</code>.
     */
    public final TableField<JOauthRegistrationRecord, String> USER_INFO_ENDPOINT_NAME_ATTR = createField(DSL.name("user_info_endpoint_name_attr"), SQLDataType.VARCHAR(256), this, "");

    /**
     * The column <code>public.oauth_registration.jwk_set_uri</code>.
     */
    public final TableField<JOauthRegistrationRecord, String> JWK_SET_URI = createField(DSL.name("jwk_set_uri"), SQLDataType.VARCHAR(256), this, "");

    /**
     * The column <code>public.oauth_registration.client_name</code>.
     */
    public final TableField<JOauthRegistrationRecord, String> CLIENT_NAME = createField(DSL.name("client_name"), SQLDataType.VARCHAR(128), this, "");

    private JOauthRegistration(Name alias, Table<JOauthRegistrationRecord> aliased) {
        this(alias, aliased, (Field<?>[]) null, null);
    }

    private JOauthRegistration(Name alias, Table<JOauthRegistrationRecord> aliased, Field<?>[] parameters, Condition where) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table(), where);
    }

    /**
     * Create an aliased <code>public.oauth_registration</code> table reference
     */
    public JOauthRegistration(String alias) {
        this(DSL.name(alias), OAUTH_REGISTRATION);
    }

    /**
     * Create an aliased <code>public.oauth_registration</code> table reference
     */
    public JOauthRegistration(Name alias) {
        this(alias, OAUTH_REGISTRATION);
    }

    /**
     * Create a <code>public.oauth_registration</code> table reference
     */
    public JOauthRegistration() {
        this(DSL.name("oauth_registration"), null);
    }

    public <O extends Record> JOauthRegistration(Table<O> path, ForeignKey<O, JOauthRegistrationRecord> childPath, InverseForeignKey<O, JOauthRegistrationRecord> parentPath) {
        super(path, childPath, parentPath, OAUTH_REGISTRATION);
    }

    /**
     * A subtype implementing {@link Path} for simplified path-based joins.
     */
    public static class JOauthRegistrationPath extends JOauthRegistration implements Path<JOauthRegistrationRecord> {

        private static final long serialVersionUID = 1L;
        public <O extends Record> JOauthRegistrationPath(Table<O> path, ForeignKey<O, JOauthRegistrationRecord> childPath, InverseForeignKey<O, JOauthRegistrationRecord> parentPath) {
            super(path, childPath, parentPath);
        }
        private JOauthRegistrationPath(Name alias, Table<JOauthRegistrationRecord> aliased) {
            super(alias, aliased);
        }

        @Override
        public JOauthRegistrationPath as(String alias) {
            return new JOauthRegistrationPath(DSL.name(alias), this);
        }

        @Override
        public JOauthRegistrationPath as(Name alias) {
            return new JOauthRegistrationPath(alias, this);
        }

        @Override
        public JOauthRegistrationPath as(Table<?> alias) {
            return new JOauthRegistrationPath(alias.getQualifiedName(), this);
        }
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : JPublic.PUBLIC;
    }

    @Override
    public UniqueKey<JOauthRegistrationRecord> getPrimaryKey() {
        return Keys.OAUTH_REGISTRATION_PKEY;
    }

    @Override
    public List<UniqueKey<JOauthRegistrationRecord>> getUniqueKeys() {
        return Arrays.asList(Keys.OAUTH_REGISTRATION_CLIENT_ID_KEY);
    }

    private transient JOauthRegistrationRestrictionPath _oauthRegistrationRestriction;

    /**
     * Get the implicit to-many join path to the
     * <code>public.oauth_registration_restriction</code> table
     */
    public JOauthRegistrationRestrictionPath oauthRegistrationRestriction() {
        if (_oauthRegistrationRestriction == null)
            _oauthRegistrationRestriction = new JOauthRegistrationRestrictionPath(this, null, Keys.OAUTH_REGISTRATION_RESTRICTION__OAUTH_REGISTRATION_RESTRICTION_OAUTH_REGISTRATION_FK_FKEY.getInverseKey());

        return _oauthRegistrationRestriction;
    }

    private transient JOauthRegistrationScopePath _oauthRegistrationScope;

    /**
     * Get the implicit to-many join path to the
     * <code>public.oauth_registration_scope</code> table
     */
    public JOauthRegistrationScopePath oauthRegistrationScope() {
        if (_oauthRegistrationScope == null)
            _oauthRegistrationScope = new JOauthRegistrationScopePath(this, null, Keys.OAUTH_REGISTRATION_SCOPE__OAUTH_REGISTRATION_SCOPE_OAUTH_REGISTRATION_FK_FKEY.getInverseKey());

        return _oauthRegistrationScope;
    }

    @Override
    public JOauthRegistration as(String alias) {
        return new JOauthRegistration(DSL.name(alias), this);
    }

    @Override
    public JOauthRegistration as(Name alias) {
        return new JOauthRegistration(alias, this);
    }

    @Override
    public JOauthRegistration as(Table<?> alias) {
        return new JOauthRegistration(alias.getQualifiedName(), this);
    }

    /**
     * Rename this table
     */
    @Override
    public JOauthRegistration rename(String name) {
        return new JOauthRegistration(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public JOauthRegistration rename(Name name) {
        return new JOauthRegistration(name, null);
    }

    /**
     * Rename this table
     */
    @Override
    public JOauthRegistration rename(Table<?> name) {
        return new JOauthRegistration(name.getQualifiedName(), null);
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public JOauthRegistration where(Condition condition) {
        return new JOauthRegistration(getQualifiedName(), aliased() ? this : null, null, condition);
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public JOauthRegistration where(Collection<? extends Condition> conditions) {
        return where(DSL.and(conditions));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public JOauthRegistration where(Condition... conditions) {
        return where(DSL.and(conditions));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public JOauthRegistration where(Field<Boolean> condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public JOauthRegistration where(SQL condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public JOauthRegistration where(@Stringly.SQL String condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public JOauthRegistration where(@Stringly.SQL String condition, Object... binds) {
        return where(DSL.condition(condition, binds));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public JOauthRegistration where(@Stringly.SQL String condition, QueryPart... parts) {
        return where(DSL.condition(condition, parts));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public JOauthRegistration whereExists(Select<?> select) {
        return where(DSL.exists(select));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public JOauthRegistration whereNotExists(Select<?> select) {
        return where(DSL.notExists(select));
    }
}
