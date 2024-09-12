/*
 * This file is generated by jOOQ.
 */
package com.epam.ta.reportportal.jooq.tables.records;


import com.epam.ta.reportportal.jooq.tables.JUserCreationBid;

import java.time.Instant;

import javax.annotation.processing.Generated;

import org.jooq.Field;
import org.jooq.JSONB;
import org.jooq.Record1;
import org.jooq.Record7;
import org.jooq.Row7;
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
public class JUserCreationBidRecord extends UpdatableRecordImpl<JUserCreationBidRecord> implements Record7<String, Instant, String, String, Long, String, JSONB> {

    private static final long serialVersionUID = 333422022;

    /**
     * Setter for <code>public.user_creation_bid.uuid</code>.
     */
    public void setUuid(String value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.user_creation_bid.uuid</code>.
     */
    public String getUuid() {
        return (String) get(0);
    }

    /**
     * Setter for <code>public.user_creation_bid.last_modified</code>.
     */
    public void setLastModified(Instant value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.user_creation_bid.last_modified</code>.
     */
    public Instant getLastModified() {
        return (Instant) get(1);
    }

    /**
     * Setter for <code>public.user_creation_bid.email</code>.
     */
    public void setEmail(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.user_creation_bid.email</code>.
     */
    public String getEmail() {
        return (String) get(2);
    }

    /**
     * Setter for <code>public.user_creation_bid.role</code>.
     */
    public void setRole(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>public.user_creation_bid.role</code>.
     */
    public String getRole() {
        return (String) get(3);
    }

    /**
     * Setter for <code>public.user_creation_bid.inviting_user_id</code>.
     */
    public void setInvitingUserId(Long value) {
        set(4, value);
    }

    /**
     * Getter for <code>public.user_creation_bid.inviting_user_id</code>.
     */
    public Long getInvitingUserId() {
        return (Long) get(4);
    }

    /**
     * Setter for <code>public.user_creation_bid.project_name</code>.
     */
    public void setProjectName(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>public.user_creation_bid.project_name</code>.
     */
    public String getProjectName() {
        return (String) get(5);
    }

    /**
     * Setter for <code>public.user_creation_bid.metadata</code>.
     */
    public void setMetadata(JSONB value) {
        set(6, value);
    }

    /**
     * Getter for <code>public.user_creation_bid.metadata</code>.
     */
    public JSONB getMetadata() {
        return (JSONB) get(6);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<String> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record7 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row7<String, Instant, String, String, Long, String, JSONB> fieldsRow() {
        return (Row7) super.fieldsRow();
    }

    @Override
    public Row7<String, Instant, String, String, Long, String, JSONB> valuesRow() {
        return (Row7) super.valuesRow();
    }

    @Override
    public Field<String> field1() {
        return JUserCreationBid.USER_CREATION_BID.UUID;
    }

    @Override
    public Field<Instant> field2() {
        return JUserCreationBid.USER_CREATION_BID.LAST_MODIFIED;
    }

    @Override
    public Field<String> field3() {
        return JUserCreationBid.USER_CREATION_BID.EMAIL;
    }

    @Override
    public Field<String> field4() {
        return JUserCreationBid.USER_CREATION_BID.ROLE;
    }

    @Override
    public Field<Long> field5() {
        return JUserCreationBid.USER_CREATION_BID.INVITING_USER_ID;
    }

    @Override
    public Field<String> field6() {
        return JUserCreationBid.USER_CREATION_BID.PROJECT_NAME;
    }

    @Override
    public Field<JSONB> field7() {
        return JUserCreationBid.USER_CREATION_BID.METADATA;
    }

    @Override
    public String component1() {
        return getUuid();
    }

    @Override
    public Instant component2() {
        return getLastModified();
    }

    @Override
    public String component3() {
        return getEmail();
    }

    @Override
    public String component4() {
        return getRole();
    }

    @Override
    public Long component5() {
        return getInvitingUserId();
    }

    @Override
    public String component6() {
        return getProjectName();
    }

    @Override
    public JSONB component7() {
        return getMetadata();
    }

    @Override
    public String value1() {
        return getUuid();
    }

    @Override
    public Instant value2() {
        return getLastModified();
    }

    @Override
    public String value3() {
        return getEmail();
    }

    @Override
    public String value4() {
        return getRole();
    }

    @Override
    public Long value5() {
        return getInvitingUserId();
    }

    @Override
    public String value6() {
        return getProjectName();
    }

    @Override
    public JSONB value7() {
        return getMetadata();
    }

    @Override
    public JUserCreationBidRecord value1(String value) {
        setUuid(value);
        return this;
    }

    @Override
    public JUserCreationBidRecord value2(Instant value) {
        setLastModified(value);
        return this;
    }

    @Override
    public JUserCreationBidRecord value3(String value) {
        setEmail(value);
        return this;
    }

    @Override
    public JUserCreationBidRecord value4(String value) {
        setRole(value);
        return this;
    }

    @Override
    public JUserCreationBidRecord value5(Long value) {
        setInvitingUserId(value);
        return this;
    }

    @Override
    public JUserCreationBidRecord value6(String value) {
        setProjectName(value);
        return this;
    }

    @Override
    public JUserCreationBidRecord value7(JSONB value) {
        setMetadata(value);
        return this;
    }

    @Override
    public JUserCreationBidRecord values(String value1, Instant value2, String value3, String value4, Long value5, String value6, JSONB value7) {
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
     * Create a detached JUserCreationBidRecord
     */
    public JUserCreationBidRecord() {
        super(JUserCreationBid.USER_CREATION_BID);
    }

    /**
     * Create a detached, initialised JUserCreationBidRecord
     */
    public JUserCreationBidRecord(String uuid, Instant lastModified, String email, String role, Long invitingUserId, String projectName, JSONB metadata) {
        super(JUserCreationBid.USER_CREATION_BID);

        set(0, uuid);
        set(1, lastModified);
        set(2, email);
        set(3, role);
        set(4, invitingUserId);
        set(5, projectName);
        set(6, metadata);
    }
}
