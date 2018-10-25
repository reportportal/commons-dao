/*
 * This file is generated by jOOQ.
 */
package com.epam.ta.reportportal.jooq;


import javax.annotation.Generated;

import org.jooq.Sequence;
import org.jooq.impl.SequenceImpl;


/**
 * Convenience access to all sequences in public
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.11.4"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Sequences {

    /**
     * The sequence <code>public.acl_class_id_seq</code>
     */
    public static final Sequence<Long> ACL_CLASS_ID_SEQ = new SequenceImpl<Long>("acl_class_id_seq", JPublic.PUBLIC, org.jooq.impl.SQLDataType.BIGINT.nullable(false));

    /**
     * The sequence <code>public.acl_entry_id_seq</code>
     */
    public static final Sequence<Long> ACL_ENTRY_ID_SEQ = new SequenceImpl<Long>("acl_entry_id_seq", JPublic.PUBLIC, org.jooq.impl.SQLDataType.BIGINT.nullable(false));

    /**
     * The sequence <code>public.acl_object_identity_id_seq</code>
     */
    public static final Sequence<Long> ACL_OBJECT_IDENTITY_ID_SEQ = new SequenceImpl<Long>("acl_object_identity_id_seq", JPublic.PUBLIC, org.jooq.impl.SQLDataType.BIGINT.nullable(false));

    /**
     * The sequence <code>public.acl_sid_id_seq</code>
     */
    public static final Sequence<Long> ACL_SID_ID_SEQ = new SequenceImpl<Long>("acl_sid_id_seq", JPublic.PUBLIC, org.jooq.impl.SQLDataType.BIGINT.nullable(false));

    /**
     * The sequence <code>public.activity_id_seq</code>
     */
    public static final Sequence<Long> ACTIVITY_ID_SEQ = new SequenceImpl<Long>("activity_id_seq", JPublic.PUBLIC, org.jooq.impl.SQLDataType.BIGINT.nullable(false));

    /**
     * The sequence <code>public.attribute_id_seq</code>
     */
    public static final Sequence<Long> ATTRIBUTE_ID_SEQ = new SequenceImpl<Long>("attribute_id_seq", JPublic.PUBLIC, org.jooq.impl.SQLDataType.BIGINT.nullable(false));

    /**
     * The sequence <code>public.bug_tracking_system_id_seq</code>
     */
    public static final Sequence<Long> BUG_TRACKING_SYSTEM_ID_SEQ = new SequenceImpl<Long>("bug_tracking_system_id_seq", JPublic.PUBLIC, org.jooq.impl.SQLDataType.BIGINT.nullable(false));

    /**
     * The sequence <code>public.dashboard_id_seq</code>
     */
    public static final Sequence<Integer> DASHBOARD_ID_SEQ = new SequenceImpl<Integer>("dashboard_id_seq", JPublic.PUBLIC, org.jooq.impl.SQLDataType.INTEGER.nullable(false));

    /**
     * The sequence <code>public.defect_field_allowed_value_id_seq</code>
     */
    public static final Sequence<Long> DEFECT_FIELD_ALLOWED_VALUE_ID_SEQ = new SequenceImpl<Long>("defect_field_allowed_value_id_seq", JPublic.PUBLIC, org.jooq.impl.SQLDataType.BIGINT.nullable(false));

    /**
     * The sequence <code>public.defect_form_field_id_seq</code>
     */
    public static final Sequence<Long> DEFECT_FORM_FIELD_ID_SEQ = new SequenceImpl<Long>("defect_form_field_id_seq", JPublic.PUBLIC, org.jooq.impl.SQLDataType.BIGINT.nullable(false));

    /**
     * The sequence <code>public.email_sender_case_id_seq</code>
     */
    public static final Sequence<Long> EMAIL_SENDER_CASE_ID_SEQ = new SequenceImpl<Long>("email_sender_case_id_seq", JPublic.PUBLIC, org.jooq.impl.SQLDataType.BIGINT.nullable(false));

    /**
     * The sequence <code>public.email_sender_case_project_id_seq</code>
     */
    public static final Sequence<Long> EMAIL_SENDER_CASE_PROJECT_ID_SEQ = new SequenceImpl<Long>("email_sender_case_project_id_seq", JPublic.PUBLIC, org.jooq.impl.SQLDataType.BIGINT.nullable(false));

    /**
     * The sequence <code>public.filter_condition_id_seq</code>
     */
    public static final Sequence<Long> FILTER_CONDITION_ID_SEQ = new SequenceImpl<Long>("filter_condition_id_seq", JPublic.PUBLIC, org.jooq.impl.SQLDataType.BIGINT.nullable(false));

    /**
     * The sequence <code>public.filter_id_seq</code>
     */
    public static final Sequence<Long> FILTER_ID_SEQ = new SequenceImpl<Long>("filter_id_seq", JPublic.PUBLIC, org.jooq.impl.SQLDataType.BIGINT.nullable(false));

    /**
     * The sequence <code>public.filter_sort_id_seq</code>
     */
    public static final Sequence<Long> FILTER_SORT_ID_SEQ = new SequenceImpl<Long>("filter_sort_id_seq", JPublic.PUBLIC, org.jooq.impl.SQLDataType.BIGINT.nullable(false));

    /**
     * The sequence <code>public.integration_id_seq</code>
     */
    public static final Sequence<Integer> INTEGRATION_ID_SEQ = new SequenceImpl<Integer>("integration_id_seq", JPublic.PUBLIC, org.jooq.impl.SQLDataType.INTEGER.nullable(false));

    /**
     * The sequence <code>public.integration_type_id_seq</code>
     */
    public static final Sequence<Integer> INTEGRATION_TYPE_ID_SEQ = new SequenceImpl<Integer>("integration_type_id_seq", JPublic.PUBLIC, org.jooq.impl.SQLDataType.INTEGER.nullable(false));

    /**
     * The sequence <code>public.issue_group_issue_group_id_seq</code>
     */
    public static final Sequence<Short> ISSUE_GROUP_ISSUE_GROUP_ID_SEQ = new SequenceImpl<Short>("issue_group_issue_group_id_seq", JPublic.PUBLIC, org.jooq.impl.SQLDataType.SMALLINT.nullable(false));

    /**
     * The sequence <code>public.issue_type_id_seq</code>
     */
    public static final Sequence<Long> ISSUE_TYPE_ID_SEQ = new SequenceImpl<Long>("issue_type_id_seq", JPublic.PUBLIC, org.jooq.impl.SQLDataType.BIGINT.nullable(false));

    /**
     * The sequence <code>public.item_tag_id_seq</code>
     */
    public static final Sequence<Integer> ITEM_TAG_ID_SEQ = new SequenceImpl<Integer>("item_tag_id_seq", JPublic.PUBLIC, org.jooq.impl.SQLDataType.INTEGER.nullable(false));

    /**
     * The sequence <code>public.launch_id_seq</code>
     */
    public static final Sequence<Long> LAUNCH_ID_SEQ = new SequenceImpl<Long>("launch_id_seq", JPublic.PUBLIC, org.jooq.impl.SQLDataType.BIGINT.nullable(false));

    /**
     * The sequence <code>public.launch_tag_id_seq</code>
     */
    public static final Sequence<Long> LAUNCH_TAG_ID_SEQ = new SequenceImpl<Long>("launch_tag_id_seq", JPublic.PUBLIC, org.jooq.impl.SQLDataType.BIGINT.nullable(false));

    /**
     * The sequence <code>public.ldap_synchronization_attributes_id_seq</code>
     */
    public static final Sequence<Long> LDAP_SYNCHRONIZATION_ATTRIBUTES_ID_SEQ = new SequenceImpl<Long>("ldap_synchronization_attributes_id_seq", JPublic.PUBLIC, org.jooq.impl.SQLDataType.BIGINT.nullable(false));

    /**
     * The sequence <code>public.log_id_seq</code>
     */
    public static final Sequence<Long> LOG_ID_SEQ = new SequenceImpl<Long>("log_id_seq", JPublic.PUBLIC, org.jooq.impl.SQLDataType.BIGINT.nullable(false));

    /**
     * The sequence <code>public.oauth_registration_restriction_id_seq</code>
     */
    public static final Sequence<Integer> OAUTH_REGISTRATION_RESTRICTION_ID_SEQ = new SequenceImpl<Integer>("oauth_registration_restriction_id_seq", JPublic.PUBLIC, org.jooq.impl.SQLDataType.INTEGER.nullable(false));

    /**
     * The sequence <code>public.oauth_registration_scope_id_seq</code>
     */
    public static final Sequence<Integer> OAUTH_REGISTRATION_SCOPE_ID_SEQ = new SequenceImpl<Integer>("oauth_registration_scope_id_seq", JPublic.PUBLIC, org.jooq.impl.SQLDataType.INTEGER.nullable(false));

    /**
     * The sequence <code>public.project_attribute_attribute_id_seq</code>
     */
    public static final Sequence<Long> PROJECT_ATTRIBUTE_ATTRIBUTE_ID_SEQ = new SequenceImpl<Long>("project_attribute_attribute_id_seq", JPublic.PUBLIC, org.jooq.impl.SQLDataType.BIGINT.nullable(false));

    /**
     * The sequence <code>public.project_attribute_project_id_seq</code>
     */
    public static final Sequence<Long> PROJECT_ATTRIBUTE_PROJECT_ID_SEQ = new SequenceImpl<Long>("project_attribute_project_id_seq", JPublic.PUBLIC, org.jooq.impl.SQLDataType.BIGINT.nullable(false));

    /**
     * The sequence <code>public.project_id_seq</code>
     */
    public static final Sequence<Long> PROJECT_ID_SEQ = new SequenceImpl<Long>("project_id_seq", JPublic.PUBLIC, org.jooq.impl.SQLDataType.BIGINT.nullable(false));

    /**
     * The sequence <code>public.server_settings_id_seq</code>
     */
    public static final Sequence<Short> SERVER_SETTINGS_ID_SEQ = new SequenceImpl<Short>("server_settings_id_seq", JPublic.PUBLIC, org.jooq.impl.SQLDataType.SMALLINT.nullable(false));

    /**
     * The sequence <code>public.statistics_field_sf_id_seq</code>
     */
    public static final Sequence<Long> STATISTICS_FIELD_SF_ID_SEQ = new SequenceImpl<Long>("statistics_field_sf_id_seq", JPublic.PUBLIC, org.jooq.impl.SQLDataType.BIGINT.nullable(false));

    /**
     * The sequence <code>public.statistics_s_id_seq</code>
     */
    public static final Sequence<Long> STATISTICS_S_ID_SEQ = new SequenceImpl<Long>("statistics_s_id_seq", JPublic.PUBLIC, org.jooq.impl.SQLDataType.BIGINT.nullable(false));

    /**
     * The sequence <code>public.test_item_item_id_seq</code>
     */
    public static final Sequence<Long> TEST_ITEM_ITEM_ID_SEQ = new SequenceImpl<Long>("test_item_item_id_seq", JPublic.PUBLIC, org.jooq.impl.SQLDataType.BIGINT.nullable(false));

    /**
     * The sequence <code>public.ticket_id_seq</code>
     */
    public static final Sequence<Long> TICKET_ID_SEQ = new SequenceImpl<Long>("ticket_id_seq", JPublic.PUBLIC, org.jooq.impl.SQLDataType.BIGINT.nullable(false));

    /**
     * The sequence <code>public.users_id_seq</code>
     */
    public static final Sequence<Long> USERS_ID_SEQ = new SequenceImpl<Long>("users_id_seq", JPublic.PUBLIC, org.jooq.impl.SQLDataType.BIGINT.nullable(false));

    /**
     * The sequence <code>public.widget_id_seq</code>
     */
    public static final Sequence<Long> WIDGET_ID_SEQ = new SequenceImpl<Long>("widget_id_seq", JPublic.PUBLIC, org.jooq.impl.SQLDataType.BIGINT.nullable(false));

    /**
     * The sequence <code>public.widget_option_id_seq</code>
     */
    public static final Sequence<Long> WIDGET_OPTION_ID_SEQ = new SequenceImpl<Long>("widget_option_id_seq", JPublic.PUBLIC, org.jooq.impl.SQLDataType.BIGINT.nullable(false));
}
