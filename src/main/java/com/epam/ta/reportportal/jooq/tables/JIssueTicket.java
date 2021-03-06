/*
 * This file is generated by jOOQ.
 */
package com.epam.ta.reportportal.jooq.tables;


import com.epam.ta.reportportal.jooq.Indexes;
import com.epam.ta.reportportal.jooq.JPublic;
import com.epam.ta.reportportal.jooq.Keys;
import com.epam.ta.reportportal.jooq.tables.records.JIssueTicketRecord;

import java.util.Arrays;
import java.util.List;

import javax.annotation.processing.Generated;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Index;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row2;
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
public class JIssueTicket extends TableImpl<JIssueTicketRecord> {

    private static final long serialVersionUID = -1026586967;

    /**
     * The reference instance of <code>public.issue_ticket</code>
     */
    public static final JIssueTicket ISSUE_TICKET = new JIssueTicket();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<JIssueTicketRecord> getRecordType() {
        return JIssueTicketRecord.class;
    }

    /**
     * The column <code>public.issue_ticket.issue_id</code>.
     */
    public final TableField<JIssueTicketRecord, Long> ISSUE_ID = createField(DSL.name("issue_id"), org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>public.issue_ticket.ticket_id</code>.
     */
    public final TableField<JIssueTicketRecord, Long> TICKET_ID = createField(DSL.name("ticket_id"), org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * Create a <code>public.issue_ticket</code> table reference
     */
    public JIssueTicket() {
        this(DSL.name("issue_ticket"), null);
    }

    /**
     * Create an aliased <code>public.issue_ticket</code> table reference
     */
    public JIssueTicket(String alias) {
        this(DSL.name(alias), ISSUE_TICKET);
    }

    /**
     * Create an aliased <code>public.issue_ticket</code> table reference
     */
    public JIssueTicket(Name alias) {
        this(alias, ISSUE_TICKET);
    }

    private JIssueTicket(Name alias, Table<JIssueTicketRecord> aliased) {
        this(alias, aliased, null);
    }

    private JIssueTicket(Name alias, Table<JIssueTicketRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> JIssueTicket(Table<O> child, ForeignKey<O, JIssueTicketRecord> key) {
        super(child, key, ISSUE_TICKET);
    }

    @Override
    public Schema getSchema() {
        return JPublic.PUBLIC;
    }

    @Override
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.ISSUE_TICKET_PK);
    }

    @Override
    public UniqueKey<JIssueTicketRecord> getPrimaryKey() {
        return Keys.ISSUE_TICKET_PK;
    }

    @Override
    public List<UniqueKey<JIssueTicketRecord>> getKeys() {
        return Arrays.<UniqueKey<JIssueTicketRecord>>asList(Keys.ISSUE_TICKET_PK);
    }

    @Override
    public List<ForeignKey<JIssueTicketRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<JIssueTicketRecord, ?>>asList(Keys.ISSUE_TICKET__ISSUE_TICKET_ISSUE_ID_FKEY, Keys.ISSUE_TICKET__ISSUE_TICKET_TICKET_ID_FKEY);
    }

    public JIssue issue() {
        return new JIssue(this, Keys.ISSUE_TICKET__ISSUE_TICKET_ISSUE_ID_FKEY);
    }

    public JTicket ticket() {
        return new JTicket(this, Keys.ISSUE_TICKET__ISSUE_TICKET_TICKET_ID_FKEY);
    }

    @Override
    public JIssueTicket as(String alias) {
        return new JIssueTicket(DSL.name(alias), this);
    }

    @Override
    public JIssueTicket as(Name alias) {
        return new JIssueTicket(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public JIssueTicket rename(String name) {
        return new JIssueTicket(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public JIssueTicket rename(Name name) {
        return new JIssueTicket(name, null);
    }

    // -------------------------------------------------------------------------
    // Row2 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row2<Long, Long> fieldsRow() {
        return (Row2) super.fieldsRow();
    }
}
