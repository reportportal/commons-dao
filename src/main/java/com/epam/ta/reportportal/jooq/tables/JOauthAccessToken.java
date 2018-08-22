/*
 * This file is generated by jOOQ.
 */
package com.epam.ta.reportportal.jooq.tables;


import com.epam.ta.reportportal.jooq.Indexes;
import com.epam.ta.reportportal.jooq.JPublic;
import com.epam.ta.reportportal.jooq.Keys;
import com.epam.ta.reportportal.jooq.enums.JAccessTokenTypeEnum;
import com.epam.ta.reportportal.jooq.tables.records.JOauthAccessTokenRecord;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.ForeignKey;
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
public class JOauthAccessToken extends TableImpl<JOauthAccessTokenRecord> {

    private static final long serialVersionUID = 498038789;

    /**
     * The reference instance of <code>public.oauth_access_token</code>
     */
    public static final JOauthAccessToken OAUTH_ACCESS_TOKEN = new JOauthAccessToken();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<JOauthAccessTokenRecord> getRecordType() {
        return JOauthAccessTokenRecord.class;
    }

    /**
     * The column <code>public.oauth_access_token.user_id</code>.
     */
    public final TableField<JOauthAccessTokenRecord, Long> USER_ID = createField("user_id", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>public.oauth_access_token.token</code>.
     */
    public final TableField<JOauthAccessTokenRecord, String> TOKEN = createField("token", org.jooq.impl.SQLDataType.VARCHAR.nullable(false), this, "");

    /**
     * The column <code>public.oauth_access_token.token_type</code>.
     */
    public final TableField<JOauthAccessTokenRecord, JAccessTokenTypeEnum> TOKEN_TYPE = createField("token_type", org.jooq.impl.SQLDataType.VARCHAR.nullable(false).asEnumDataType(com.epam.ta.reportportal.jooq.enums.JAccessTokenTypeEnum.class), this, "");

    /**
     * Create a <code>public.oauth_access_token</code> table reference
     */
    public JOauthAccessToken() {
        this(DSL.name("oauth_access_token"), null);
    }

    /**
     * Create an aliased <code>public.oauth_access_token</code> table reference
     */
    public JOauthAccessToken(String alias) {
        this(DSL.name(alias), OAUTH_ACCESS_TOKEN);
    }

    /**
     * Create an aliased <code>public.oauth_access_token</code> table reference
     */
    public JOauthAccessToken(Name alias) {
        this(alias, OAUTH_ACCESS_TOKEN);
    }

    private JOauthAccessToken(Name alias, Table<JOauthAccessTokenRecord> aliased) {
        this(alias, aliased, null);
    }

    private JOauthAccessToken(Name alias, Table<JOauthAccessTokenRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> JOauthAccessToken(Table<O> child, ForeignKey<O, JOauthAccessTokenRecord> key) {
        super(child, key, OAUTH_ACCESS_TOKEN);
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
        return Arrays.<Index>asList(Indexes.ACCESS_TOKENS_PK);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<JOauthAccessTokenRecord> getPrimaryKey() {
        return Keys.ACCESS_TOKENS_PK;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<JOauthAccessTokenRecord>> getKeys() {
        return Arrays.<UniqueKey<JOauthAccessTokenRecord>>asList(Keys.ACCESS_TOKENS_PK);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<JOauthAccessTokenRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<JOauthAccessTokenRecord, ?>>asList(Keys.OAUTH_ACCESS_TOKEN__OAUTH_ACCESS_TOKEN_USER_ID_FKEY);
    }

    public JUsers users() {
        return new JUsers(this, Keys.OAUTH_ACCESS_TOKEN__OAUTH_ACCESS_TOKEN_USER_ID_FKEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JOauthAccessToken as(String alias) {
        return new JOauthAccessToken(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JOauthAccessToken as(Name alias) {
        return new JOauthAccessToken(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public JOauthAccessToken rename(String name) {
        return new JOauthAccessToken(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public JOauthAccessToken rename(Name name) {
        return new JOauthAccessToken(name, null);
    }
}
