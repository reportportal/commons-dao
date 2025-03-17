/*
 * This file is generated by jOOQ.
 */
package com.epam.ta.reportportal.jooq.tables.records;


import com.epam.ta.reportportal.jooq.enums.JIssueGroupEnum;
import com.epam.ta.reportportal.jooq.tables.JIssueGroup;

import org.jooq.Record1;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes", "this-escape" })
public class JIssueGroupRecord extends UpdatableRecordImpl<JIssueGroupRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>public.issue_group.issue_group_id</code>.
     */
    public void setIssueGroupId(Short value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.issue_group.issue_group_id</code>.
     */
    public Short getIssueGroupId() {
        return (Short) get(0);
    }

    /**
     * Setter for <code>public.issue_group.issue_group</code>.
     */
    public void setIssueGroup(JIssueGroupEnum value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.issue_group.issue_group</code>.
     */
    public JIssueGroupEnum getIssueGroup() {
        return (JIssueGroupEnum) get(1);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<Short> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached JIssueGroupRecord
     */
    public JIssueGroupRecord() {
        super(JIssueGroup.ISSUE_GROUP);
    }

    /**
     * Create a detached, initialised JIssueGroupRecord
     */
    public JIssueGroupRecord(Short issueGroupId, JIssueGroupEnum issueGroup) {
        super(JIssueGroup.ISSUE_GROUP);

        setIssueGroupId(issueGroupId);
        setIssueGroup(issueGroup);
        resetChangedOnNotNull();
    }
}
