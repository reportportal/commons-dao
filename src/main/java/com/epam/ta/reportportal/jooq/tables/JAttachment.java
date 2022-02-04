/*
 * This file is generated by jOOQ.
 */
package com.epam.ta.reportportal.jooq.tables;


import com.epam.ta.reportportal.jooq.Indexes;
import com.epam.ta.reportportal.jooq.JPublic;
import com.epam.ta.reportportal.jooq.Keys;
import com.epam.ta.reportportal.jooq.tables.records.JAttachmentRecord;

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
public class JAttachment extends TableImpl<JAttachmentRecord> {

    private static final long serialVersionUID = -1988681978;

    /**
     * The reference instance of <code>public.attachment</code>
     */
    public static final JAttachment ATTACHMENT = new JAttachment();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<JAttachmentRecord> getRecordType() {
        return JAttachmentRecord.class;
    }

    /**
     * The column <code>public.attachment.id</code>.
     */
    public final TableField<JAttachmentRecord, Long> ID = createField(DSL.name("id"), org.jooq.impl.SQLDataType.BIGINT.nullable(false).defaultValue(org.jooq.impl.DSL.field("nextval('attachment_id_seq'::regclass)", org.jooq.impl.SQLDataType.BIGINT)), this, "");

    /**
     * The column <code>public.attachment.file_id</code>.
     */
    public final TableField<JAttachmentRecord, String> FILE_ID = createField(DSL.name("file_id"), org.jooq.impl.SQLDataType.CLOB.nullable(false), this, "");

    /**
     * The column <code>public.attachment.thumbnail_id</code>.
     */
    public final TableField<JAttachmentRecord, String> THUMBNAIL_ID = createField(DSL.name("thumbnail_id"), org.jooq.impl.SQLDataType.CLOB, this, "");

    /**
     * The column <code>public.attachment.content_type</code>.
     */
    public final TableField<JAttachmentRecord, String> CONTENT_TYPE = createField(DSL.name("content_type"), org.jooq.impl.SQLDataType.CLOB, this, "");

    /**
     * The column <code>public.attachment.project_id</code>.
     */
    public final TableField<JAttachmentRecord, Long> PROJECT_ID = createField(DSL.name("project_id"), org.jooq.impl.SQLDataType.BIGINT, this, "");

    /**
     * The column <code>public.attachment.launch_id</code>.
     */
    public final TableField<JAttachmentRecord, Long> LAUNCH_ID = createField(DSL.name("launch_id"), org.jooq.impl.SQLDataType.BIGINT, this, "");

    /**
     * The column <code>public.attachment.item_id</code>.
     */
    public final TableField<JAttachmentRecord, Long> ITEM_ID = createField(DSL.name("item_id"), org.jooq.impl.SQLDataType.BIGINT, this, "");

    /**
     * The column <code>public.attachment.file_size</code>.
     */
    public final TableField<JAttachmentRecord, Long> FILE_SIZE = createField(DSL.name("file_size"), org.jooq.impl.SQLDataType.BIGINT.nullable(false).defaultValue(org.jooq.impl.DSL.field("0", org.jooq.impl.SQLDataType.BIGINT)), this, "");

    /**
     * The column <code>public.attachment.creation_date</code>.
     */
    public final TableField<JAttachmentRecord, Timestamp> CREATION_DATE = createField(DSL.name("creation_date"), org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false), this, "");

    /**
     * Create a <code>public.attachment</code> table reference
     */
    public JAttachment() {
        this(DSL.name("attachment"), null);
    }

    /**
     * Create an aliased <code>public.attachment</code> table reference
     */
    public JAttachment(String alias) {
        this(DSL.name(alias), ATTACHMENT);
    }

    /**
     * Create an aliased <code>public.attachment</code> table reference
     */
    public JAttachment(Name alias) {
        this(alias, ATTACHMENT);
    }

    private JAttachment(Name alias, Table<JAttachmentRecord> aliased) {
        this(alias, aliased, null);
    }

    private JAttachment(Name alias, Table<JAttachmentRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> JAttachment(Table<O> child, ForeignKey<O, JAttachmentRecord> key) {
        super(child, key, ATTACHMENT);
    }

    @Override
    public Schema getSchema() {
        return JPublic.PUBLIC;
    }

    @Override
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.ATT_ITEM_IDX, Indexes.ATT_LAUNCH_IDX, Indexes.ATT_PROJECT_IDX, Indexes.ATTACHMENT_PK, Indexes.ATTACHMENT_PROJECT_ID_CREATION_TIME_IDX);
    }

    @Override
    public Identity<JAttachmentRecord, Long> getIdentity() {
        return Keys.IDENTITY_ATTACHMENT;
    }

    @Override
    public UniqueKey<JAttachmentRecord> getPrimaryKey() {
        return Keys.ATTACHMENT_PK;
    }

    @Override
    public List<UniqueKey<JAttachmentRecord>> getKeys() {
        return Arrays.<UniqueKey<JAttachmentRecord>>asList(Keys.ATTACHMENT_PK);
    }

    @Override
    public JAttachment as(String alias) {
        return new JAttachment(DSL.name(alias), this);
    }

    @Override
    public JAttachment as(Name alias) {
        return new JAttachment(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public JAttachment rename(String name) {
        return new JAttachment(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public JAttachment rename(Name name) {
        return new JAttachment(name, null);
    }

    // -------------------------------------------------------------------------
    // Row9 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row9<Long, String, String, String, Long, Long, Long, Long, Timestamp> fieldsRow() {
        return (Row9) super.fieldsRow();
    }
}
