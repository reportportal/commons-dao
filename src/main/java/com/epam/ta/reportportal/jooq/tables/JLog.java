/*
 * This file is generated by jOOQ.
 */
package com.epam.ta.reportportal.jooq.tables;


import com.epam.ta.reportportal.jooq.Indexes;
import com.epam.ta.reportportal.jooq.JPublic;
import com.epam.ta.reportportal.jooq.Keys;
import com.epam.ta.reportportal.jooq.tables.records.JLogRecord;

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
import org.jooq.Row11;
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
public class JLog extends TableImpl<JLogRecord> {

    private static final long serialVersionUID = 231270563;

    /**
     * The reference instance of <code>public.log</code>
     */
    public static final JLog LOG = new JLog();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<JLogRecord> getRecordType() {
        return JLogRecord.class;
    }

    /**
     * The column <code>public.log.id</code>.
     */
    public final TableField<JLogRecord, Long> ID = createField(DSL.name("id"), org.jooq.impl.SQLDataType.BIGINT.nullable(false).defaultValue(org.jooq.impl.DSL.field("nextval('log_id_seq'::regclass)", org.jooq.impl.SQLDataType.BIGINT)), this, "");

    /**
     * The column <code>public.log.uuid</code>.
     */
    public final TableField<JLogRecord, String> UUID = createField(DSL.name("uuid"), org.jooq.impl.SQLDataType.VARCHAR(36).nullable(false), this, "");

    /**
     * The column <code>public.log.log_time</code>.
     */
    public final TableField<JLogRecord, Timestamp> LOG_TIME = createField(DSL.name("log_time"), org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false), this, "");

    /**
     * The column <code>public.log.log_message</code>.
     */
    public final TableField<JLogRecord, String> LOG_MESSAGE = createField(DSL.name("log_message"), org.jooq.impl.SQLDataType.CLOB.nullable(false), this, "");

    /**
     * The column <code>public.log.item_id</code>.
     */
    public final TableField<JLogRecord, Long> ITEM_ID = createField(DSL.name("item_id"), org.jooq.impl.SQLDataType.BIGINT, this, "");

    /**
     * The column <code>public.log.launch_id</code>.
     */
    public final TableField<JLogRecord, Long> LAUNCH_ID = createField(DSL.name("launch_id"), org.jooq.impl.SQLDataType.BIGINT, this, "");

    /**
     * The column <code>public.log.last_modified</code>.
     */
    public final TableField<JLogRecord, Timestamp> LAST_MODIFIED = createField(DSL.name("last_modified"), org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false), this, "");

    /**
     * The column <code>public.log.log_level</code>.
     */
    public final TableField<JLogRecord, Integer> LOG_LEVEL = createField(DSL.name("log_level"), org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>public.log.attachment_id</code>.
     */
    public final TableField<JLogRecord, Long> ATTACHMENT_ID = createField(DSL.name("attachment_id"), org.jooq.impl.SQLDataType.BIGINT, this, "");

    /**
     * The column <code>public.log.project_id</code>.
     */
    public final TableField<JLogRecord, Long> PROJECT_ID = createField(DSL.name("project_id"), org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>public.log.cluster_id</code>.
     */
    public final TableField<JLogRecord, Long> CLUSTER_ID = createField(DSL.name("cluster_id"), org.jooq.impl.SQLDataType.BIGINT, this, "");

    /**
     * Create a <code>public.log</code> table reference
     */
    public JLog() {
        this(DSL.name("log"), null);
    }

    /**
     * Create an aliased <code>public.log</code> table reference
     */
    public JLog(String alias) {
        this(DSL.name(alias), LOG);
    }

    /**
     * Create an aliased <code>public.log</code> table reference
     */
    public JLog(Name alias) {
        this(alias, LOG);
    }

    private JLog(Name alias, Table<JLogRecord> aliased) {
        this(alias, aliased, null);
    }

    private JLog(Name alias, Table<JLogRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> JLog(Table<O> child, ForeignKey<O, JLogRecord> key) {
        super(child, key, LOG);
    }

    @Override
    public Schema getSchema() {
        return JPublic.PUBLIC;
    }

    @Override
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.LOG_ATTACH_ID_IDX, Indexes.LOG_CLUSTER_IDX, Indexes.LOG_LAUNCH_ID_IDX, Indexes.LOG_PK, Indexes.LOG_PROJECT_ID_LOG_TIME_IDX, Indexes.LOG_PROJECT_IDX, Indexes.LOG_TI_IDX);
    }

    @Override
    public Identity<JLogRecord, Long> getIdentity() {
        return Keys.IDENTITY_LOG;
    }

    @Override
    public UniqueKey<JLogRecord> getPrimaryKey() {
        return Keys.LOG_PK;
    }

    @Override
    public List<UniqueKey<JLogRecord>> getKeys() {
        return Arrays.<UniqueKey<JLogRecord>>asList(Keys.LOG_PK);
    }

    @Override
    public List<ForeignKey<JLogRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<JLogRecord, ?>>asList(Keys.LOG__LOG_ITEM_ID_FKEY, Keys.LOG__LOG_LAUNCH_ID_FKEY, Keys.LOG__LOG_ATTACHMENT_ID_FKEY);
    }

    public JTestItem testItem() {
        return new JTestItem(this, Keys.LOG__LOG_ITEM_ID_FKEY);
    }

    public JLaunch launch() {
        return new JLaunch(this, Keys.LOG__LOG_LAUNCH_ID_FKEY);
    }

    public JAttachment attachment() {
        return new JAttachment(this, Keys.LOG__LOG_ATTACHMENT_ID_FKEY);
    }

    @Override
    public JLog as(String alias) {
        return new JLog(DSL.name(alias), this);
    }

    @Override
    public JLog as(Name alias) {
        return new JLog(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public JLog rename(String name) {
        return new JLog(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public JLog rename(Name name) {
        return new JLog(name, null);
    }

    // -------------------------------------------------------------------------
    // Row11 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row11<Long, String, Timestamp, String, Long, Long, Timestamp, Integer, Long, Long, Long> fieldsRow() {
        return (Row11) super.fieldsRow();
    }
}
