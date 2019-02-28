/*
 * This file is generated by jOOQ.
 */
package com.epam.ta.reportportal.jooq.tables;


import com.epam.ta.reportportal.jooq.JPublic;
import com.epam.ta.reportportal.jooq.Keys;
import com.epam.ta.reportportal.jooq.tables.records.JAttachmentRecord;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.11.7"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class JAttachment extends TableImpl<JAttachmentRecord> {

    private static final long serialVersionUID = -1509566270;

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
    public final TableField<JAttachmentRecord, Long> ID = createField("id", org.jooq.impl.SQLDataType.BIGINT.nullable(false).defaultValue(org.jooq.impl.DSL.field("nextval('attachment_id_seq'::regclass)", org.jooq.impl.SQLDataType.BIGINT)), this, "");

    /**
     * The column <code>public.attachment.path</code>.
     */
    public final TableField<JAttachmentRecord, String> PATH = createField("path", org.jooq.impl.SQLDataType.CLOB.nullable(false), this, "");

    /**
     * The column <code>public.attachment.thumbnail_path</code>.
     */
    public final TableField<JAttachmentRecord, String> THUMBNAIL_PATH = createField("thumbnail_path", org.jooq.impl.SQLDataType.CLOB, this, "");

    /**
     * The column <code>public.attachment.content_type</code>.
     */
    public final TableField<JAttachmentRecord, String> CONTENT_TYPE = createField("content_type", org.jooq.impl.SQLDataType.CLOB, this, "");

    /**
     * The column <code>public.attachment.project_id</code>.
     */
    public final TableField<JAttachmentRecord, Long> PROJECT_ID = createField("project_id", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>public.attachment.launch_id</code>.
     */
    public final TableField<JAttachmentRecord, Long> LAUNCH_ID = createField("launch_id", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>public.attachment.item_id</code>.
     */
    public final TableField<JAttachmentRecord, Long> ITEM_ID = createField("item_id", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

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
    public Identity<JAttachmentRecord, Long> getIdentity() {
        return Keys.IDENTITY_ATTACHMENT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<JAttachmentRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<JAttachmentRecord, ?>>asList(Keys.ATTACHMENT__ATTACHMENT_ID_FKEY, Keys.ATTACHMENT__ATTACHMENT_PROJECT_ID_FKEY, Keys.ATTACHMENT__ATTACHMENT_LAUNCH_ID_FKEY, Keys.ATTACHMENT__ATTACHMENT_ITEM_ID_FKEY);
    }

    public JLog log() {
        return new JLog(this, Keys.ATTACHMENT__ATTACHMENT_ID_FKEY);
    }

    public JProject project() {
        return new JProject(this, Keys.ATTACHMENT__ATTACHMENT_PROJECT_ID_FKEY);
    }

    public JLaunch launch() {
        return new JLaunch(this, Keys.ATTACHMENT__ATTACHMENT_LAUNCH_ID_FKEY);
    }

    public JTestItem testItem() {
        return new JTestItem(this, Keys.ATTACHMENT__ATTACHMENT_ITEM_ID_FKEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JAttachment as(String alias) {
        return new JAttachment(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
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
}