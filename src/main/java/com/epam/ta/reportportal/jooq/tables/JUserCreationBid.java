/*
 * This file is generated by jOOQ.
 */
package com.epam.ta.reportportal.jooq.tables;


import com.epam.ta.reportportal.jooq.Indexes;
import com.epam.ta.reportportal.jooq.JPublic;
import com.epam.ta.reportportal.jooq.Keys;
import com.epam.ta.reportportal.jooq.enums.JOrganizationRoleEnum;
import com.epam.ta.reportportal.jooq.enums.JProjectRoleEnum;
import com.epam.ta.reportportal.jooq.tables.records.JUserCreationBidRecord;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import javax.annotation.processing.Generated;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Index;
import org.jooq.JSONB;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row9;
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
        "jOOQ version:3.12.4"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class JUserCreationBid extends TableImpl<JUserCreationBidRecord> {

    private static final long serialVersionUID = -1816621891;

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
    public final TableField<JUserCreationBidRecord, String> UUID = createField(DSL.name("uuid"), org.jooq.impl.SQLDataType.VARCHAR.nullable(false), this, "");

    /**
     * The column <code>public.user_creation_bid.last_modified</code>.
     */
    public final TableField<JUserCreationBidRecord, Timestamp> LAST_MODIFIED = createField(DSL.name("last_modified"), org.jooq.impl.SQLDataType.TIMESTAMP.defaultValue(org.jooq.impl.DSL.field("now()", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * The column <code>public.user_creation_bid.email</code>.
     */
    public final TableField<JUserCreationBidRecord, String> EMAIL = createField(DSL.name("email"), org.jooq.impl.SQLDataType.VARCHAR.nullable(false), this, "");

    /**
     * The column <code>public.user_creation_bid.inviting_user_id</code>.
     */
    public final TableField<JUserCreationBidRecord, Long> INVITING_USER_ID = createField(DSL.name("inviting_user_id"), org.jooq.impl.SQLDataType.BIGINT, this, "");

    /**
     * The column <code>public.user_creation_bid.project_name</code>.
     */
    public final TableField<JUserCreationBidRecord, String> PROJECT_NAME = createField(DSL.name("project_name"), org.jooq.impl.SQLDataType.VARCHAR.nullable(false), this, "");

    /**
     * The column <code>public.user_creation_bid.metadata</code>.
     */
    public final TableField<JUserCreationBidRecord, JSONB> METADATA = createField(DSL.name("metadata"), org.jooq.impl.SQLDataType.JSONB, this, "");

    /**
     * The column <code>public.user_creation_bid.role</code>.
     */
    public final TableField<JUserCreationBidRecord, JProjectRoleEnum> ROLE = createField(DSL.name("role"), org.jooq.impl.SQLDataType.VARCHAR.asEnumDataType(com.epam.ta.reportportal.jooq.enums.JProjectRoleEnum.class), this, "");

    /**
     * The column <code>public.user_creation_bid.organization_id</code>.
     */
    public final TableField<JUserCreationBidRecord, Long> ORGANIZATION_ID = createField(DSL.name("organization_id"), org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>public.user_creation_bid.organization_role</code>.
     */
    public final TableField<JUserCreationBidRecord, JOrganizationRoleEnum> ORGANIZATION_ROLE = createField(DSL.name("organization_role"), org.jooq.impl.SQLDataType.VARCHAR.nullable(false).asEnumDataType(com.epam.ta.reportportal.jooq.enums.JOrganizationRoleEnum.class), this, "");

    /**
     * Create a <code>public.user_creation_bid</code> table reference
     */
    public JUserCreationBid() {
        this(DSL.name("user_creation_bid"), null);
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

    private JUserCreationBid(Name alias, Table<JUserCreationBidRecord> aliased) {
        this(alias, aliased, null);
    }

    private JUserCreationBid(Name alias, Table<JUserCreationBidRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> JUserCreationBid(Table<O> child, ForeignKey<O, JUserCreationBidRecord> key) {
        super(child, key, USER_CREATION_BID);
    }

    @Override
    public Schema getSchema() {
        return JPublic.PUBLIC;
    }

    @Override
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.USER_CREATION_BID_PK);
    }

    @Override
    public UniqueKey<JUserCreationBidRecord> getPrimaryKey() {
        return Keys.USER_CREATION_BID_PK;
    }

    @Override
    public List<UniqueKey<JUserCreationBidRecord>> getKeys() {
        return Arrays.<UniqueKey<JUserCreationBidRecord>>asList(Keys.USER_CREATION_BID_PK);
    }

    @Override
    public List<ForeignKey<JUserCreationBidRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<JUserCreationBidRecord, ?>>asList(Keys.USER_CREATION_BID__USER_CREATION_BID_INVITING_USER_ID_FKEY, Keys.USER_CREATION_BID__USER_CREATION_BID_ORGANIZATION_ID_FKEY);
    }

    public JUsers users() {
        return new JUsers(this, Keys.USER_CREATION_BID__USER_CREATION_BID_INVITING_USER_ID_FKEY);
    }

    public JOrganization organization() {
        return new JOrganization(this, Keys.USER_CREATION_BID__USER_CREATION_BID_ORGANIZATION_ID_FKEY);
    }

    @Override
    public JUserCreationBid as(String alias) {
        return new JUserCreationBid(DSL.name(alias), this);
    }

    @Override
    public JUserCreationBid as(Name alias) {
        return new JUserCreationBid(alias, this);
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

    // -------------------------------------------------------------------------
    // Row9 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row9<String, Timestamp, String, Long, String, JSONB, JProjectRoleEnum, Long, JOrganizationRoleEnum> fieldsRow() {
        return (Row9) super.fieldsRow();
    }
}
