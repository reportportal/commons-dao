/*
 * This file is generated by jOOQ.
 */
package com.epam.ta.reportportal.jooq.tables;


import com.epam.ta.reportportal.dao.converters.InstantConverter;
import com.epam.ta.reportportal.jooq.Indexes;
import com.epam.ta.reportportal.jooq.JPublic;
import com.epam.ta.reportportal.jooq.Keys;
import com.epam.ta.reportportal.jooq.tables.records.JAttachmentDeletionRecord;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import javax.annotation.processing.Generated;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Index;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row5;
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
public class JAttachmentDeletion extends TableImpl<JAttachmentDeletionRecord> {

    private static final long serialVersionUID = 2100753798;

    /**
     * The reference instance of <code>public.attachment_deletion</code>
     */
    public static final JAttachmentDeletion ATTACHMENT_DELETION = new JAttachmentDeletion();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<JAttachmentDeletionRecord> getRecordType() {
        return JAttachmentDeletionRecord.class;
    }

    /**
     * The column <code>public.attachment_deletion.id</code>.
     */
    public final TableField<JAttachmentDeletionRecord, Long> ID = createField(DSL.name("id"), org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>public.attachment_deletion.file_id</code>.
     */
    public final TableField<JAttachmentDeletionRecord, String> FILE_ID = createField(DSL.name("file_id"), org.jooq.impl.SQLDataType.CLOB.nullable(false), this, "");

    /**
     * The column <code>public.attachment_deletion.thumbnail_id</code>.
     */
    public final TableField<JAttachmentDeletionRecord, String> THUMBNAIL_ID = createField(DSL.name("thumbnail_id"), org.jooq.impl.SQLDataType.CLOB, this, "");

    /**
     * The column <code>public.attachment_deletion.creation_attachment_date</code>.
     */
    public final TableField<JAttachmentDeletionRecord, Instant> CREATION_ATTACHMENT_DATE = createField(DSL.name("creation_attachment_date"), org.jooq.impl.SQLDataType.TIMESTAMP, this, "", new InstantConverter());

    /**
     * The column <code>public.attachment_deletion.deletion_date</code>.
     */
    public final TableField<JAttachmentDeletionRecord, Instant> DELETION_DATE = createField(DSL.name("deletion_date"), org.jooq.impl.SQLDataType.TIMESTAMP, this, "", new InstantConverter());

    /**
     * Create a <code>public.attachment_deletion</code> table reference
     */
    public JAttachmentDeletion() {
        this(DSL.name("attachment_deletion"), null);
    }

    /**
     * Create an aliased <code>public.attachment_deletion</code> table reference
     */
    public JAttachmentDeletion(String alias) {
        this(DSL.name(alias), ATTACHMENT_DELETION);
    }

    /**
     * Create an aliased <code>public.attachment_deletion</code> table reference
     */
    public JAttachmentDeletion(Name alias) {
        this(alias, ATTACHMENT_DELETION);
    }

    private JAttachmentDeletion(Name alias, Table<JAttachmentDeletionRecord> aliased) {
        this(alias, aliased, null);
    }

    private JAttachmentDeletion(Name alias, Table<JAttachmentDeletionRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> JAttachmentDeletion(Table<O> child, ForeignKey<O, JAttachmentDeletionRecord> key) {
        super(child, key, ATTACHMENT_DELETION);
    }

    @Override
    public Schema getSchema() {
        return JPublic.PUBLIC;
    }

    @Override
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.ATTACHMENT_DELETION_PKEY);
    }

    @Override
    public UniqueKey<JAttachmentDeletionRecord> getPrimaryKey() {
        return Keys.ATTACHMENT_DELETION_PKEY;
    }

    @Override
    public List<UniqueKey<JAttachmentDeletionRecord>> getKeys() {
        return Arrays.<UniqueKey<JAttachmentDeletionRecord>>asList(Keys.ATTACHMENT_DELETION_PKEY);
    }

    @Override
    public JAttachmentDeletion as(String alias) {
        return new JAttachmentDeletion(DSL.name(alias), this);
    }

    @Override
    public JAttachmentDeletion as(Name alias) {
        return new JAttachmentDeletion(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public JAttachmentDeletion rename(String name) {
        return new JAttachmentDeletion(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public JAttachmentDeletion rename(Name name) {
        return new JAttachmentDeletion(name, null);
    }

    // -------------------------------------------------------------------------
    // Row5 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row5<Long, String, String, Instant, Instant> fieldsRow() {
        return (Row5) super.fieldsRow();
    }
}
