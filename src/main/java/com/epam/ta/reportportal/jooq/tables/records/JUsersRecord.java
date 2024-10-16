/*
 * This file is generated by jOOQ.
 */
package com.epam.ta.reportportal.jooq.tables.records;


import com.epam.ta.reportportal.jooq.tables.JUsers;

import java.util.UUID;

import org.jooq.JSONB;
import org.jooq.Record1;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes", "this-escape" })
public class JUsersRecord extends UpdatableRecordImpl<JUsersRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>public.users.id</code>.
     */
    public void setId(Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.users.id</code>.
     */
    public Long getId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>public.users.login</code>.
     */
    public void setLogin(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.users.login</code>.
     */
    public String getLogin() {
        return (String) get(1);
    }

    /**
     * Setter for <code>public.users.password</code>.
     */
    public void setPassword(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.users.password</code>.
     */
    public String getPassword() {
        return (String) get(2);
    }

    /**
     * Setter for <code>public.users.email</code>.
     */
    public void setEmail(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>public.users.email</code>.
     */
    public String getEmail() {
        return (String) get(3);
    }

    /**
     * Setter for <code>public.users.attachment</code>.
     */
    public void setAttachment(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>public.users.attachment</code>.
     */
    public String getAttachment() {
        return (String) get(4);
    }

    /**
     * Setter for <code>public.users.attachment_thumbnail</code>.
     */
    public void setAttachmentThumbnail(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>public.users.attachment_thumbnail</code>.
     */
    public String getAttachmentThumbnail() {
        return (String) get(5);
    }

    /**
     * Setter for <code>public.users.role</code>.
     */
    public void setRole(String value) {
        set(6, value);
    }

    /**
     * Getter for <code>public.users.role</code>.
     */
    public String getRole() {
        return (String) get(6);
    }

    /**
     * Setter for <code>public.users.type</code>.
     */
    public void setType(String value) {
        set(7, value);
    }

    /**
     * Getter for <code>public.users.type</code>.
     */
    public String getType() {
        return (String) get(7);
    }

    /**
     * Setter for <code>public.users.expired</code>.
     */
    public void setExpired(Boolean value) {
        set(8, value);
    }

    /**
     * Getter for <code>public.users.expired</code>.
     */
    public Boolean getExpired() {
        return (Boolean) get(8);
    }

    /**
     * Setter for <code>public.users.full_name</code>.
     */
    public void setFullName(String value) {
        set(9, value);
    }

    /**
     * Getter for <code>public.users.full_name</code>.
     */
    public String getFullName() {
        return (String) get(9);
    }

    /**
     * Setter for <code>public.users.metadata</code>.
     */
    public void setMetadata(JSONB value) {
        set(10, value);
    }

    /**
     * Getter for <code>public.users.metadata</code>.
     */
    public JSONB getMetadata() {
        return (JSONB) get(10);
    }

    /**
     * Setter for <code>public.users.uuid</code>.
     */
    public void setUuid(UUID value) {
        set(11, value);
    }

    /**
     * Getter for <code>public.users.uuid</code>.
     */
    public UUID getUuid() {
        return (UUID) get(11);
    }

    /**
     * Setter for <code>public.users.external_id</code>.
     */
    public void setExternalId(String value) {
        set(12, value);
    }

    /**
     * Getter for <code>public.users.external_id</code>.
     */
    public String getExternalId() {
        return (String) get(12);
    }

    /**
     * Setter for <code>public.users.active</code>.
     */
    public void setActive(Boolean value) {
        set(13, value);
    }

    /**
     * Getter for <code>public.users.active</code>.
     */
    public Boolean getActive() {
        return (Boolean) get(13);
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
     * Create a detached JUsersRecord
     */
    public JUsersRecord() {
        super(JUsers.USERS);
    }

    /**
     * Create a detached, initialised JUsersRecord
     */
    public JUsersRecord(Long id, String login, String password, String email, String attachment, String attachmentThumbnail, String role, String type, Boolean expired, String fullName, JSONB metadata, UUID uuid, String externalId, Boolean active) {
        super(JUsers.USERS);

        setId(id);
        setLogin(login);
        setPassword(password);
        setEmail(email);
        setAttachment(attachment);
        setAttachmentThumbnail(attachmentThumbnail);
        setRole(role);
        setType(type);
        setExpired(expired);
        setFullName(fullName);
        setMetadata(metadata);
        setUuid(uuid);
        setExternalId(externalId);
        setActive(active);
        resetChangedOnNotNull();
    }
}
