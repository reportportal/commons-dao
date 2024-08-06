/*
 * This file is generated by jOOQ.
 */
package com.epam.ta.reportportal.jooq.tables.records;


import com.epam.ta.reportportal.jooq.tables.JAttachmentDeletion;

import java.time.Instant;

import javax.annotation.processing.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record5;
import org.jooq.Row5;
import org.jooq.impl.UpdatableRecordImpl;


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
public class JAttachmentDeletionRecord extends UpdatableRecordImpl<JAttachmentDeletionRecord> implements Record5<Long, String, String, Instant, Instant> {

    private static final long serialVersionUID = 1575536078;

    /**
     * Setter for <code>public.attachment_deletion.id</code>.
     */
    public void setId(Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.attachment_deletion.id</code>.
     */
    public Long getId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>public.attachment_deletion.file_id</code>.
     */
    public void setFileId(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.attachment_deletion.file_id</code>.
     */
    public String getFileId() {
        return (String) get(1);
    }

    /**
     * Setter for <code>public.attachment_deletion.thumbnail_id</code>.
     */
    public void setThumbnailId(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.attachment_deletion.thumbnail_id</code>.
     */
    public String getThumbnailId() {
        return (String) get(2);
    }

    /**
     * Setter for <code>public.attachment_deletion.creation_attachment_date</code>.
     */
    public void setCreationAttachmentDate(Instant value) {
        set(3, value);
    }

    /**
     * Getter for <code>public.attachment_deletion.creation_attachment_date</code>.
     */
    public Instant getCreationAttachmentDate() {
        return (Instant) get(3);
    }

    /**
     * Setter for <code>public.attachment_deletion.deletion_date</code>.
     */
    public void setDeletionDate(Instant value) {
        set(4, value);
    }

    /**
     * Getter for <code>public.attachment_deletion.deletion_date</code>.
     */
    public Instant getDeletionDate() {
        return (Instant) get(4);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<Long> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record5 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row5<Long, String, String, Instant, Instant> fieldsRow() {
        return (Row5) super.fieldsRow();
    }

    @Override
    public Row5<Long, String, String, Instant, Instant> valuesRow() {
        return (Row5) super.valuesRow();
    }

    @Override
    public Field<Long> field1() {
        return JAttachmentDeletion.ATTACHMENT_DELETION.ID;
    }

    @Override
    public Field<String> field2() {
        return JAttachmentDeletion.ATTACHMENT_DELETION.FILE_ID;
    }

    @Override
    public Field<String> field3() {
        return JAttachmentDeletion.ATTACHMENT_DELETION.THUMBNAIL_ID;
    }

    @Override
    public Field<Instant> field4() {
        return JAttachmentDeletion.ATTACHMENT_DELETION.CREATION_ATTACHMENT_DATE;
    }

    @Override
    public Field<Instant> field5() {
        return JAttachmentDeletion.ATTACHMENT_DELETION.DELETION_DATE;
    }

    @Override
    public Long component1() {
        return getId();
    }

    @Override
    public String component2() {
        return getFileId();
    }

    @Override
    public String component3() {
        return getThumbnailId();
    }

    @Override
    public Instant component4() {
        return getCreationAttachmentDate();
    }

    @Override
    public Instant component5() {
        return getDeletionDate();
    }

    @Override
    public Long value1() {
        return getId();
    }

    @Override
    public String value2() {
        return getFileId();
    }

    @Override
    public String value3() {
        return getThumbnailId();
    }

    @Override
    public Instant value4() {
        return getCreationAttachmentDate();
    }

    @Override
    public Instant value5() {
        return getDeletionDate();
    }

    @Override
    public JAttachmentDeletionRecord value1(Long value) {
        setId(value);
        return this;
    }

    @Override
    public JAttachmentDeletionRecord value2(String value) {
        setFileId(value);
        return this;
    }

    @Override
    public JAttachmentDeletionRecord value3(String value) {
        setThumbnailId(value);
        return this;
    }

    @Override
    public JAttachmentDeletionRecord value4(Instant value) {
        setCreationAttachmentDate(value);
        return this;
    }

    @Override
    public JAttachmentDeletionRecord value5(Instant value) {
        setDeletionDate(value);
        return this;
    }

    @Override
    public JAttachmentDeletionRecord values(Long value1, String value2, String value3, Instant value4, Instant value5) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached JAttachmentDeletionRecord
     */
    public JAttachmentDeletionRecord() {
        super(JAttachmentDeletion.ATTACHMENT_DELETION);
    }

    /**
     * Create a detached, initialised JAttachmentDeletionRecord
     */
    public JAttachmentDeletionRecord(Long id, String fileId, String thumbnailId, Instant creationAttachmentDate, Instant deletionDate) {
        super(JAttachmentDeletion.ATTACHMENT_DELETION);

        set(0, id);
        set(1, fileId);
        set(2, thumbnailId);
        set(3, creationAttachmentDate);
        set(4, deletionDate);
    }
}
