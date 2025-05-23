/*
 * This file is generated by jOOQ.
 */
package com.epam.ta.reportportal.jooq.tables;


import com.epam.ta.reportportal.dao.converters.JooqInstantConverter;
import com.epam.ta.reportportal.jooq.Indexes;
import com.epam.ta.reportportal.jooq.JPublic;
import com.epam.ta.reportportal.jooq.Keys;
import com.epam.ta.reportportal.jooq.tables.JIssue.JIssuePath;
import com.epam.ta.reportportal.jooq.tables.JIssueTicket.JIssueTicketPath;
import com.epam.ta.reportportal.jooq.tables.records.JTicketRecord;

import java.time.Instant;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.Index;
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
public class JTicket extends TableImpl<JTicketRecord> {

    private static final long serialVersionUID = 1L;

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
    public final TableField<JTicketRecord, Long> ID = createField(DSL.name("id"), SQLDataType.BIGINT.nullable(false).identity(true), this, "");

    /**
     * The column <code>public.ticket.ticket_id</code>.
     */
    public final TableField<JTicketRecord, String> TICKET_ID = createField(DSL.name("ticket_id"), SQLDataType.VARCHAR(256).nullable(false), this, "");

    /**
     * The column <code>public.ticket.submitter</code>.
     */
    public final TableField<JTicketRecord, String> SUBMITTER = createField(DSL.name("submitter"), SQLDataType.VARCHAR.nullable(false), this, "");

    /**
     * The column <code>public.ticket.submit_date</code>.
     */
    public final TableField<JTicketRecord, Instant> SUBMIT_DATE = createField(DSL.name("submit_date"), SQLDataType.LOCALDATETIME(6).nullable(false).defaultValue(DSL.field(DSL.raw("now()"), SQLDataType.LOCALDATETIME)), this, "", new JooqInstantConverter());

    /**
     * The column <code>public.ticket.bts_url</code>.
     */
    public final TableField<JTicketRecord, String> BTS_URL = createField(DSL.name("bts_url"), SQLDataType.VARCHAR(1024).nullable(false), this, "");

    /**
     * The column <code>public.ticket.bts_project</code>.
     */
    public final TableField<JTicketRecord, String> BTS_PROJECT = createField(DSL.name("bts_project"), SQLDataType.VARCHAR(1024).nullable(false), this, "");

    /**
     * The column <code>public.ticket.url</code>.
     */
    public final TableField<JTicketRecord, String> URL = createField(DSL.name("url"), SQLDataType.VARCHAR(1024).nullable(false), this, "");

    /**
     * The column <code>public.ticket.plugin_name</code>.
     */
    public final TableField<JTicketRecord, String> PLUGIN_NAME = createField(DSL.name("plugin_name"), SQLDataType.VARCHAR(128), this, "");

    private JTicket(Name alias, Table<JTicketRecord> aliased) {
        this(alias, aliased, (Field<?>[]) null, null);
    }

    private JTicket(Name alias, Table<JTicketRecord> aliased, Field<?>[] parameters, Condition where) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table(), where);
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

    /**
     * Create a <code>public.ticket</code> table reference
     */
    public JTicket() {
        this(DSL.name("ticket"), null);
    }

    public <O extends Record> JTicket(Table<O> path, ForeignKey<O, JTicketRecord> childPath, InverseForeignKey<O, JTicketRecord> parentPath) {
        super(path, childPath, parentPath, TICKET);
    }

    /**
     * A subtype implementing {@link Path} for simplified path-based joins.
     */
    public static class JTicketPath extends JTicket implements Path<JTicketRecord> {

        private static final long serialVersionUID = 1L;
        public <O extends Record> JTicketPath(Table<O> path, ForeignKey<O, JTicketRecord> childPath, InverseForeignKey<O, JTicketRecord> parentPath) {
            super(path, childPath, parentPath);
        }
        private JTicketPath(Name alias, Table<JTicketRecord> aliased) {
            super(alias, aliased);
        }

        @Override
        public JTicketPath as(String alias) {
            return new JTicketPath(DSL.name(alias), this);
        }

        @Override
        public JTicketPath as(Name alias) {
            return new JTicketPath(alias, this);
        }

        @Override
        public JTicketPath as(Table<?> alias) {
            return new JTicketPath(alias.getQualifiedName(), this);
        }
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : JPublic.PUBLIC;
    }

    @Override
    public List<Index> getIndexes() {
        return Arrays.asList(Indexes.TICKET_ID_IDX, Indexes.TICKET_SUBMITTER_IDX);
    }

    @Override
    public Identity<JTicketRecord, Long> getIdentity() {
        return (Identity<JTicketRecord, Long>) super.getIdentity();
    }

    @Override
    public UniqueKey<JTicketRecord> getPrimaryKey() {
        return Keys.TICKET_PK;
    }

    private transient JIssueTicketPath _issueTicket;

    /**
     * Get the implicit to-many join path to the
     * <code>public.issue_ticket</code> table
     */
    public JIssueTicketPath issueTicket() {
        if (_issueTicket == null)
            _issueTicket = new JIssueTicketPath(this, null, Keys.ISSUE_TICKET__ISSUE_TICKET_TICKET_ID_FKEY.getInverseKey());

        return _issueTicket;
    }

    /**
     * Get the implicit many-to-many join path to the <code>public.issue</code>
     * table
     */
    public JIssuePath issue() {
        return issueTicket().issue();
    }

    @Override
    public JTicket as(String alias) {
        return new JTicket(DSL.name(alias), this);
    }

    @Override
    public JTicket as(Name alias) {
        return new JTicket(alias, this);
    }

    @Override
    public JTicket as(Table<?> alias) {
        return new JTicket(alias.getQualifiedName(), this);
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

    /**
     * Rename this table
     */
    @Override
    public JTicket rename(Table<?> name) {
        return new JTicket(name.getQualifiedName(), null);
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public JTicket where(Condition condition) {
        return new JTicket(getQualifiedName(), aliased() ? this : null, null, condition);
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public JTicket where(Collection<? extends Condition> conditions) {
        return where(DSL.and(conditions));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public JTicket where(Condition... conditions) {
        return where(DSL.and(conditions));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public JTicket where(Field<Boolean> condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public JTicket where(SQL condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public JTicket where(@Stringly.SQL String condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public JTicket where(@Stringly.SQL String condition, Object... binds) {
        return where(DSL.condition(condition, binds));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public JTicket where(@Stringly.SQL String condition, QueryPart... parts) {
        return where(DSL.condition(condition, parts));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public JTicket whereExists(Select<?> select) {
        return where(DSL.exists(select));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public JTicket whereNotExists(Select<?> select) {
        return where(DSL.notExists(select));
    }
}
