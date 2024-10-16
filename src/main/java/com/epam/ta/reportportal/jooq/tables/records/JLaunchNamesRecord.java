/*
 * This file is generated by jOOQ.
 */
package com.epam.ta.reportportal.jooq.tables.records;


import com.epam.ta.reportportal.jooq.tables.JLaunchNames;

import org.jooq.impl.TableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes", "this-escape" })
public class JLaunchNamesRecord extends TableRecordImpl<JLaunchNamesRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>public.launch_names.sender_case_id</code>.
     */
    public void setSenderCaseId(Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.launch_names.sender_case_id</code>.
     */
    public Long getSenderCaseId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>public.launch_names.launch_name</code>.
     */
    public void setLaunchName(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.launch_names.launch_name</code>.
     */
    public String getLaunchName() {
        return (String) get(1);
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached JLaunchNamesRecord
     */
    public JLaunchNamesRecord() {
        super(JLaunchNames.LAUNCH_NAMES);
    }

    /**
     * Create a detached, initialised JLaunchNamesRecord
     */
    public JLaunchNamesRecord(Long senderCaseId, String launchName) {
        super(JLaunchNames.LAUNCH_NAMES);

        setSenderCaseId(senderCaseId);
        setLaunchName(launchName);
        resetChangedOnNotNull();
    }
}
