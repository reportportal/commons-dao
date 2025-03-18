/*
 * This file is generated by jOOQ.
 */
package com.epam.ta.reportportal.jooq.tables.records;


import com.epam.ta.reportportal.jooq.tables.JGroupsUsers;

import java.time.Instant;

import org.jooq.Record2;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes", "this-escape" })
public class JGroupsUsersRecord extends UpdatableRecordImpl<JGroupsUsersRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>public.groups_users.group_id</code>.
     */
    public void setGroupId(Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.groups_users.group_id</code>.
     */
    public Long getGroupId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>public.groups_users.user_id</code>.
     */
    public void setUserId(Long value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.groups_users.user_id</code>.
     */
    public Long getUserId() {
        return (Long) get(1);
    }

    /**
     * Setter for <code>public.groups_users.created_at</code>.
     */
    public void setCreatedAt(Instant value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.groups_users.created_at</code>.
     */
    public Instant getCreatedAt() {
        return (Instant) get(2);
    }

    /**
     * Setter for <code>public.groups_users.updated_at</code>.
     */
    public void setUpdatedAt(Instant value) {
        set(3, value);
    }

    /**
     * Getter for <code>public.groups_users.updated_at</code>.
     */
    public Instant getUpdatedAt() {
        return (Instant) get(3);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record2<Long, Long> key() {
        return (Record2) super.key();
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached JGroupsUsersRecord
     */
    public JGroupsUsersRecord() {
        super(JGroupsUsers.GROUPS_USERS);
    }

    /**
     * Create a detached, initialised JGroupsUsersRecord
     */
    public JGroupsUsersRecord(Long groupId, Long userId, Instant createdAt, Instant updatedAt) {
        super(JGroupsUsers.GROUPS_USERS);

        setGroupId(groupId);
        setUserId(userId);
        setCreatedAt(createdAt);
        setUpdatedAt(updatedAt);
        resetChangedOnNotNull();
    }
}
