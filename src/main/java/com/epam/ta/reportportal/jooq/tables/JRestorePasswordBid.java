/*
 * This file is generated by jOOQ.
 */
package com.epam.ta.reportportal.jooq.tables;


import com.epam.ta.reportportal.dao.converters.InstantConverter;
import com.epam.ta.reportportal.jooq.Indexes;
import com.epam.ta.reportportal.jooq.JPublic;
import com.epam.ta.reportportal.jooq.Keys;
import com.epam.ta.reportportal.jooq.tables.records.JRestorePasswordBidRecord;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import javax.annotation.processing.Generated;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Index;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row3;
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
public class JRestorePasswordBid extends TableImpl<JRestorePasswordBidRecord> {

    private static final long serialVersionUID = 422602805;

    /**
     * The reference instance of <code>public.restore_password_bid</code>
     */
    public static final JRestorePasswordBid RESTORE_PASSWORD_BID = new JRestorePasswordBid();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<JRestorePasswordBidRecord> getRecordType() {
        return JRestorePasswordBidRecord.class;
    }

    /**
     * The column <code>public.restore_password_bid.uuid</code>.
     */
    public final TableField<JRestorePasswordBidRecord, String> UUID = createField(DSL.name("uuid"), org.jooq.impl.SQLDataType.VARCHAR.nullable(false), this, "");

    /**
     * The column <code>public.restore_password_bid.last_modified</code>.
     */
    public final TableField<JRestorePasswordBidRecord, Instant> LAST_MODIFIED = createField(DSL.name("last_modified"), org.jooq.impl.SQLDataType.TIMESTAMP.defaultValue(org.jooq.impl.DSL.field("now()", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "", new InstantConverter());

    /**
     * The column <code>public.restore_password_bid.email</code>.
     */
    public final TableField<JRestorePasswordBidRecord, String> EMAIL = createField(DSL.name("email"), org.jooq.impl.SQLDataType.VARCHAR.nullable(false), this, "");

    /**
     * Create a <code>public.restore_password_bid</code> table reference
     */
    public JRestorePasswordBid() {
        this(DSL.name("restore_password_bid"), null);
    }

    /**
     * Create an aliased <code>public.restore_password_bid</code> table reference
     */
    public JRestorePasswordBid(String alias) {
        this(DSL.name(alias), RESTORE_PASSWORD_BID);
    }

    /**
     * Create an aliased <code>public.restore_password_bid</code> table reference
     */
    public JRestorePasswordBid(Name alias) {
        this(alias, RESTORE_PASSWORD_BID);
    }

    private JRestorePasswordBid(Name alias, Table<JRestorePasswordBidRecord> aliased) {
        this(alias, aliased, null);
    }

    private JRestorePasswordBid(Name alias, Table<JRestorePasswordBidRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> JRestorePasswordBid(Table<O> child, ForeignKey<O, JRestorePasswordBidRecord> key) {
        super(child, key, RESTORE_PASSWORD_BID);
    }

    @Override
    public Schema getSchema() {
        return JPublic.PUBLIC;
    }

    @Override
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.RESTORE_PASSWORD_BID_EMAIL_KEY, Indexes.RESTORE_PASSWORD_BID_PK);
    }

    @Override
    public UniqueKey<JRestorePasswordBidRecord> getPrimaryKey() {
        return Keys.RESTORE_PASSWORD_BID_PK;
    }

    @Override
    public List<UniqueKey<JRestorePasswordBidRecord>> getKeys() {
        return Arrays.<UniqueKey<JRestorePasswordBidRecord>>asList(Keys.RESTORE_PASSWORD_BID_PK, Keys.RESTORE_PASSWORD_BID_EMAIL_KEY);
    }

    @Override
    public JRestorePasswordBid as(String alias) {
        return new JRestorePasswordBid(DSL.name(alias), this);
    }

    @Override
    public JRestorePasswordBid as(Name alias) {
        return new JRestorePasswordBid(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public JRestorePasswordBid rename(String name) {
        return new JRestorePasswordBid(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public JRestorePasswordBid rename(Name name) {
        return new JRestorePasswordBid(name, null);
    }

    // -------------------------------------------------------------------------
    // Row3 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row3<String, Instant, String> fieldsRow() {
        return (Row3) super.fieldsRow();
    }
}
