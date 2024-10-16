/*
 * This file is generated by jOOQ.
 */
package com.epam.ta.reportportal.jooq.tables;


import com.epam.ta.reportportal.dao.converters.JooqInstantConverter;
import com.epam.ta.reportportal.jooq.JPublic;
import com.epam.ta.reportportal.jooq.Keys;
import com.epam.ta.reportportal.jooq.tables.JUsers.JUsersPath;
import com.epam.ta.reportportal.jooq.tables.records.JUserCreationBidRecord;

import java.time.Instant;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.ForeignKey;
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
public class JUserCreationBid extends TableImpl<JUserCreationBidRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>public.user_creation_bid</code>
     */
    public static final JUserCreationBid USER_CREATION_BID = new JUserCreationBid();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<JUserCreationBidRecord> getRecordType() {
        return JUserCreationBidRecord.class;
    }

    /**
     * The column <code>public.user_creation_bid.uuid</code>.
     */
    public final TableField<JUserCreationBidRecord, String> UUID = createField(DSL.name("uuid"), SQLDataType.VARCHAR.nullable(false), this, "");

    /**
     * The column <code>public.user_creation_bid.last_modified</code>.
     */
    public final TableField<JUserCreationBidRecord, Instant> LAST_MODIFIED = createField(DSL.name("last_modified"), SQLDataType.LOCALDATETIME(6).defaultValue(DSL.field(DSL.raw("now()"), SQLDataType.LOCALDATETIME)), this, "", new JooqInstantConverter());

    /**
     * The column <code>public.user_creation_bid.email</code>.
     */
    public final TableField<JUserCreationBidRecord, String> EMAIL = createField(DSL.name("email"), SQLDataType.VARCHAR.nullable(false), this, "");

    /**
     * The column <code>public.user_creation_bid.inviting_user_id</code>.
     */
    public final TableField<JUserCreationBidRecord, Long> INVITING_USER_ID = createField(DSL.name("inviting_user_id"), SQLDataType.BIGINT, this, "");

    /**
     * The column <code>public.user_creation_bid.project_name</code>.
     */
    public final TableField<JUserCreationBidRecord, String> PROJECT_NAME = createField(DSL.name("project_name"), SQLDataType.VARCHAR.nullable(false), this, "");

    /**
     * The column <code>public.user_creation_bid.metadata</code>.
     */
    public final TableField<JUserCreationBidRecord, JSONB> METADATA = createField(DSL.name("metadata"), SQLDataType.JSONB, this, "");

    /**
     * The column <code>public.user_creation_bid.role</code>.
     */
    public final TableField<JUserCreationBidRecord, String> ROLE = createField(DSL.name("role"), SQLDataType.VARCHAR.nullable(false), this, "");

    private JUserCreationBid(Name alias, Table<JUserCreationBidRecord> aliased) {
        this(alias, aliased, (Field<?>[]) null, null);
    }

    private JUserCreationBid(Name alias, Table<JUserCreationBidRecord> aliased, Field<?>[] parameters, Condition where) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table(), where);
    }

    /**
     * Create an aliased <code>public.user_creation_bid</code> table reference
     */
    public JUserCreationBid(String alias) {
        this(DSL.name(alias), USER_CREATION_BID);
    }

    /**
     * Create an aliased <code>public.user_creation_bid</code> table reference
     */
    public JUserCreationBid(Name alias) {
        this(alias, USER_CREATION_BID);
    }

    /**
     * Create a <code>public.user_creation_bid</code> table reference
     */
    public JUserCreationBid() {
        this(DSL.name("user_creation_bid"), null);
    }

    public <O extends Record> JUserCreationBid(Table<O> path, ForeignKey<O, JUserCreationBidRecord> childPath, InverseForeignKey<O, JUserCreationBidRecord> parentPath) {
        super(path, childPath, parentPath, USER_CREATION_BID);
    }

    /**
     * A subtype implementing {@link Path} for simplified path-based joins.
     */
    public static class JUserCreationBidPath extends JUserCreationBid implements Path<JUserCreationBidRecord> {

        private static final long serialVersionUID = 1L;
        public <O extends Record> JUserCreationBidPath(Table<O> path, ForeignKey<O, JUserCreationBidRecord> childPath, InverseForeignKey<O, JUserCreationBidRecord> parentPath) {
            super(path, childPath, parentPath);
        }
        private JUserCreationBidPath(Name alias, Table<JUserCreationBidRecord> aliased) {
            super(alias, aliased);
        }

        @Override
        public JUserCreationBidPath as(String alias) {
            return new JUserCreationBidPath(DSL.name(alias), this);
        }

        @Override
        public JUserCreationBidPath as(Name alias) {
            return new JUserCreationBidPath(alias, this);
        }

        @Override
        public JUserCreationBidPath as(Table<?> alias) {
            return new JUserCreationBidPath(alias.getQualifiedName(), this);
        }
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : JPublic.PUBLIC;
    }

    @Override
    public UniqueKey<JUserCreationBidRecord> getPrimaryKey() {
        return Keys.USER_CREATION_BID_PK;
    }

    @Override
    public List<ForeignKey<JUserCreationBidRecord, ?>> getReferences() {
        return Arrays.asList(Keys.USER_CREATION_BID__USER_CREATION_BID_INVITING_USER_ID_FKEY);
    }

    private transient JUsersPath _users;

    /**
     * Get the implicit join path to the <code>public.users</code> table.
     */
    public JUsersPath users() {
        if (_users == null)
            _users = new JUsersPath(this, Keys.USER_CREATION_BID__USER_CREATION_BID_INVITING_USER_ID_FKEY, null);

        return _users;
    }

    @Override
    public JUserCreationBid as(String alias) {
        return new JUserCreationBid(DSL.name(alias), this);
    }

    @Override
    public JUserCreationBid as(Name alias) {
        return new JUserCreationBid(alias, this);
    }

    @Override
    public JUserCreationBid as(Table<?> alias) {
        return new JUserCreationBid(alias.getQualifiedName(), this);
    }

    /**
     * Rename this table
     */
    @Override
    public JUserCreationBid rename(String name) {
        return new JUserCreationBid(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public JUserCreationBid rename(Name name) {
        return new JUserCreationBid(name, null);
    }

    /**
     * Rename this table
     */
    @Override
    public JUserCreationBid rename(Table<?> name) {
        return new JUserCreationBid(name.getQualifiedName(), null);
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public JUserCreationBid where(Condition condition) {
        return new JUserCreationBid(getQualifiedName(), aliased() ? this : null, null, condition);
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public JUserCreationBid where(Collection<? extends Condition> conditions) {
        return where(DSL.and(conditions));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public JUserCreationBid where(Condition... conditions) {
        return where(DSL.and(conditions));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public JUserCreationBid where(Field<Boolean> condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public JUserCreationBid where(SQL condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public JUserCreationBid where(@Stringly.SQL String condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public JUserCreationBid where(@Stringly.SQL String condition, Object... binds) {
        return where(DSL.condition(condition, binds));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public JUserCreationBid where(@Stringly.SQL String condition, QueryPart... parts) {
        return where(DSL.condition(condition, parts));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public JUserCreationBid whereExists(Select<?> select) {
        return where(DSL.exists(select));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public JUserCreationBid whereNotExists(Select<?> select) {
        return where(DSL.notExists(select));
    }
}
