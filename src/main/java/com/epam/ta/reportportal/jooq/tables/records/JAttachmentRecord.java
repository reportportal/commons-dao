/*
 * This file is generated by jOOQ.
 */
package com.epam.ta.reportportal.jooq.tables.records;


import com.epam.ta.reportportal.jooq.tables.JAttachment;

import java.time.Instant;

import org.jooq.Record1;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes", "this-escape" })
public class JAttachmentRecord extends UpdatableRecordImpl<JAttachmentRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>public.attachment.id</code>.
     */
    public void setId(Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.attachment.id</code>.
     */
    public Long getId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>public.attachment.file_id</code>.
     */
    public void setFileId(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.attachment.file_id</code>.
     */
    public String getFileId() {
        return (String) get(1);
    }

    /**
     * Setter for <code>public.attachment.thumbnail_id</code>.
     */
    public void setThumbnailId(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.attachment.thumbnail_id</code>.
     */
    public String getThumbnailId() {
        return (String) get(2);
    }

    /**
     * Setter for <code>public.attachment.content_type</code>.
     */
    public void setContentType(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>public.attachment.content_type</code>.
     */
    public String getContentType() {
        return (String) get(3);
    }

    /**
     * Setter for <code>public.attachment.project_id</code>.
     */
    public void setProjectId(Long value) {
        set(4, value);
    }

    /**
     * Getter for <code>public.attachment.project_id</code>.
     */
    public Long getProjectId() {
        return (Long) get(4);
    }

    /**
     * Setter for <code>public.attachment.launch_id</code>.
     */
    public void setLaunchId(Long value) {
        set(5, value);
    }

    /**
     * Getter for <code>public.attachment.launch_id</code>.
     */
    public Long getLaunchId() {
        return (Long) get(5);
    }

    /**
     * Setter for <code>public.attachment.item_id</code>.
     */
    public void setItemId(Long value) {
        set(6, value);
    }

    /**
     * Getter for <code>public.attachment.item_id</code>.
     */
    public Long getItemId() {
        return (Long) get(6);
    }

    /**
     * Setter for <code>public.attachment.file_size</code>.
     */
    public void setFileSize(Long value) {
        set(7, value);
    }

    /**
     * Getter for <code>public.attachment.file_size</code>.
     */
    public Long getFileSize() {
        return (Long) get(7);
    }

    /**
     * Setter for <code>public.attachment.creation_date</code>.
     */
    public void setCreationDate(Instant value) {
        set(8, value);
    }

    /**
     * Getter for <code>public.attachment.creation_date</code>.
     */
    public Instant getCreationDate() {
        return (Instant) get(8);
    }

    /**
     * Setter for <code>public.attachment.file_name</code>.
     */
    public void setFileName(String value) {
        set(9, value);
    }

    /**
     * Getter for <code>public.attachment.file_name</code>.
     */
    public String getFileName() {
        return (String) get(9);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<Long> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached JAttachmentRecord
     */
    public JAttachmentRecord() {
        super(JAttachment.ATTACHMENT);
    }

    /**
     * Create a detached, initialised JAttachmentRecord
     */
    public JAttachmentRecord(Long id, String fileId, String thumbnailId, String contentType, Long projectId, Long launchId, Long itemId, Long fileSize, Instant creationDate, String fileName) {
        super(JAttachment.ATTACHMENT);

        setId(id);
        setFileId(fileId);
        setThumbnailId(thumbnailId);
        setContentType(contentType);
        setProjectId(projectId);
        setLaunchId(launchId);
        setItemId(itemId);
        setFileSize(fileSize);
        setCreationDate(creationDate);
        setFileName(fileName);
        resetChangedOnNotNull();
    }
}
