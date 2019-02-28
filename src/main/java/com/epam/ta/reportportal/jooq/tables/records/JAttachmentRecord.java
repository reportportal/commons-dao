/*
 * This file is generated by jOOQ.
 */
package com.epam.ta.reportportal.jooq.tables.records;


import com.epam.ta.reportportal.jooq.tables.JAttachment;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record7;
import org.jooq.Row7;
import org.jooq.impl.TableRecordImpl;


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
public class JAttachmentRecord extends TableRecordImpl<JAttachmentRecord> implements Record7<Long, String, String, String, Long, Long, Long> {

    private static final long serialVersionUID = 1748351541;

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
     * Setter for <code>public.attachment.path</code>.
     */
    public void setPath(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.attachment.path</code>.
     */
    public String getPath() {
        return (String) get(1);
    }

    /**
     * Setter for <code>public.attachment.thumbnail_path</code>.
     */
    public void setThumbnailPath(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.attachment.thumbnail_path</code>.
     */
    public String getThumbnailPath() {
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

    // -------------------------------------------------------------------------
    // Record7 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row7<Long, String, String, String, Long, Long, Long> fieldsRow() {
        return (Row7) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row7<Long, String, String, String, Long, Long, Long> valuesRow() {
        return (Row7) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field1() {
        return JAttachment.ATTACHMENT.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return JAttachment.ATTACHMENT.PATH;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return JAttachment.ATTACHMENT.THUMBNAIL_PATH;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return JAttachment.ATTACHMENT.CONTENT_TYPE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field5() {
        return JAttachment.ATTACHMENT.PROJECT_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field6() {
        return JAttachment.ATTACHMENT.LAUNCH_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field7() {
        return JAttachment.ATTACHMENT.ITEM_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long component1() {
        return getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component2() {
        return getPath();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component3() {
        return getThumbnailPath();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component4() {
        return getContentType();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long component5() {
        return getProjectId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long component6() {
        return getLaunchId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long component7() {
        return getItemId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long value1() {
        return getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value2() {
        return getPath();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value3() {
        return getThumbnailPath();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value4() {
        return getContentType();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long value5() {
        return getProjectId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long value6() {
        return getLaunchId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long value7() {
        return getItemId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JAttachmentRecord value1(Long value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JAttachmentRecord value2(String value) {
        setPath(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JAttachmentRecord value3(String value) {
        setThumbnailPath(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JAttachmentRecord value4(String value) {
        setContentType(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JAttachmentRecord value5(Long value) {
        setProjectId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JAttachmentRecord value6(Long value) {
        setLaunchId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JAttachmentRecord value7(Long value) {
        setItemId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JAttachmentRecord values(Long value1, String value2, String value3, String value4, Long value5, Long value6, Long value7) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        return this;
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
    public JAttachmentRecord(Long id, String path, String thumbnailPath, String contentType, Long projectId, Long launchId, Long itemId) {
        super(JAttachment.ATTACHMENT);

        set(0, id);
        set(1, path);
        set(2, thumbnailPath);
        set(3, contentType);
        set(4, projectId);
        set(5, launchId);
        set(6, itemId);
    }
}