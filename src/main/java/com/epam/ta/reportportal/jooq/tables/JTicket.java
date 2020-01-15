/*
 * This file is generated by jOOQ.
 */
package com.epam.ta.reportportal.jooq.tables;


import com.epam.ta.reportportal.jooq.Indexes;
import com.epam.ta.reportportal.jooq.JPublic;
import com.epam.ta.reportportal.jooq.Keys;
import com.epam.ta.reportportal.jooq.tables.records.JTicketRecord;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import javax.annotation.processing.Generated;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.Index;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row7;
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
        "jOOQ version:3.12.3"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class JTicket extends TableImpl<JTicketRecord> {

    private static final long serialVersionUID = -1059787045;

    /**
     * The reference instance of <code>public.ticket</code>
     */
    public static final JTicket TICKET = new JTicket();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<JTicketRecord> getRecordType() {
        return JTicketRecord.class;
    }

    /**
     * The column <code>public.ticket.id</code>.
     */
    public final TableField<JTicketRecord, Long> ID = createField(DSL.name("id"), org.jooq.impl.SQLDataType.BIGINT.nullable(false).defaultValue(org.jooq.impl.DSL.field("nextval('ticket_id_seq'::regclass)", org.jooq.impl.SQLDataType.BIGINT)), this, "");

    /**
     * The column <code>public.ticket.ticket_id</code>.
     */
    public final TableField<JTicketRecord, String> TICKET_ID = createField(DSL.name("ticket_id"), org.jooq.impl.SQLDataType.VARCHAR(256).nullable(false), this, "");

    /**
     * The column <code>public.ticket.submitter</code>.
     */
    public final TableField<JTicketRecord, String> SUBMITTER = createField(DSL.name("submitter"), org.jooq.impl.SQLDataType.VARCHAR.nullable(false), this, "");

    /**
     * The column <code>public.ticket.submit_date</code>.
     */
    public final TableField<JTicketRecord, Timestamp> SUBMIT_DATE = createField(DSL.name("submit_date"), org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.field("now()", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * The column <code>public.ticket.bts_url</code>.
     */
    public final TableField<JTicketRecord, String> BTS_URL = createField(DSL.name("bts_url"), org.jooq.impl.SQLDataType.VARCHAR(1024).nullable(false), this, "");

    /**
     * The column <code>public.ticket.bts_project</code>.
     */
    public final TableField<JTicketRecord, String> BTS_PROJECT = createField(DSL.name("bts_project"), org.jooq.impl.SQLDataType.VARCHAR(1024).nullable(false), this, "");

    /**
     * The column <code>public.ticket.url</code>.
     */
    public final TableField<JTicketRecord, String> URL = createField(DSL.name("url"), org.jooq.impl.SQLDataType.VARCHAR(1024).nullable(false), this, "");

    /**
     * Create a <code>public.ticket</code> table reference
     */
    public JTicket() {
        this(DSL.name("ticket"), null);
    }

    /**
     * Create an aliased <code>public.ticket</code> table reference
     */
    public JTicket(String alias) {
        this(DSL.name(alias), TICKET);
    }

    /**
     * Create an aliased <code>public.ticket</code> table reference
     */
    public JTicket(Name alias) {
        this(alias, TICKET);
    }

    private JTicket(Name alias, Table<JTicketRecord> aliased) {
        this(alias, aliased, null);
    }

    private JTicket(Name alias, Table<JTicketRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> JTicket(Table<O> child, ForeignKey<O, JTicketRecord> key) {
        super(child, key, TICKET);
    }

    @Override
    public Schema getSchema() {
        return JPublic.PUBLIC;
    }

    @Override
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.TICKET_PK, Indexes.TICKET_SUBMITTER_IDX);
    }

    @Override
    public Identity<JTicketRecord, Long> getIdentity() {
        return Keys.IDENTITY_TICKET;
    }

    @Override
    public UniqueKey<JTicketRecord> getPrimaryKey() {
        return Keys.TICKET_PK;
    }

    @Override
    public List<UniqueKey<JTicketRecord>> getKeys() {
        return Arrays.<UniqueKey<JTicketRecord>>asList(Keys.TICKET_PK);
    }

    @Override
    public JTicket as(String alias) {
        return new JTicket(DSL.name(alias), this);
    }

    @Override
    public JTicket as(Name alias) {
        return new JTicket(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public JTicket rename(String name) {
        return new JTicket(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public JTicket rename(Name name) {
        return new JTicket(name, null);
    }

    // -------------------------------------------------------------------------
    // Row7 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row7<Long, String, String, Timestamp, String, String, String> fieldsRow() {
        return (Row7) super.fieldsRow();
    }
}
