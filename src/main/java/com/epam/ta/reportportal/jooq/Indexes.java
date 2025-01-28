/*
 * This file is generated by jOOQ.
 */
package com.epam.ta.reportportal.jooq;


import com.epam.ta.reportportal.jooq.tables.JActivity;
import com.epam.ta.reportportal.jooq.tables.JApiKeys;
import com.epam.ta.reportportal.jooq.tables.JAttachment;
import com.epam.ta.reportportal.jooq.tables.JClusters;
import com.epam.ta.reportportal.jooq.tables.JClustersTestItem;
import com.epam.ta.reportportal.jooq.tables.JContentField;
import com.epam.ta.reportportal.jooq.tables.JFilterCondition;
import com.epam.ta.reportportal.jooq.tables.JFilterSort;
import com.epam.ta.reportportal.jooq.tables.JIntegration;
import com.epam.ta.reportportal.jooq.tables.JIssue;
import com.epam.ta.reportportal.jooq.tables.JIssueType;
import com.epam.ta.reportportal.jooq.tables.JItemAttribute;
import com.epam.ta.reportportal.jooq.tables.JLaunch;
import com.epam.ta.reportportal.jooq.tables.JLaunchAttributeRules;
import com.epam.ta.reportportal.jooq.tables.JLaunchNames;
import com.epam.ta.reportportal.jooq.tables.JLog;
import com.epam.ta.reportportal.jooq.tables.JOwnedEntity;
import com.epam.ta.reportportal.jooq.tables.JParameter;
import com.epam.ta.reportportal.jooq.tables.JPatternTemplateTestItem;
import com.epam.ta.reportportal.jooq.tables.JRecipients;
import com.epam.ta.reportportal.jooq.tables.JSenderCase;
import com.epam.ta.reportportal.jooq.tables.JStaleMaterializedView;
import com.epam.ta.reportportal.jooq.tables.JStatistics;
import com.epam.ta.reportportal.jooq.tables.JTestItem;
import com.epam.ta.reportportal.jooq.tables.JTicket;

import org.jooq.Index;
import org.jooq.OrderField;
import org.jooq.impl.DSL;
import org.jooq.impl.Internal;


/**
 * A class modelling indexes of tables in public.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes", "this-escape" })
public class Indexes {

    // -------------------------------------------------------------------------
    // INDEX definitions
    // -------------------------------------------------------------------------

    public static final Index ACTIVITY_CREATED_AT_IDX = Internal.createIndex(DSL.name("activity_created_at_idx"), JActivity.ACTIVITY, new OrderField[] { JActivity.ACTIVITY.CREATED_AT }, false);
    public static final Index ACTIVITY_OBJECT_IDX = Internal.createIndex(DSL.name("activity_object_idx"), JActivity.ACTIVITY, new OrderField[] { JActivity.ACTIVITY.OBJECT_ID }, false);
    public static final Index ACTIVITY_PROJECT_IDX = Internal.createIndex(DSL.name("activity_project_idx"), JActivity.ACTIVITY, new OrderField[] { JActivity.ACTIVITY.PROJECT_ID }, false);
    public static final Index ATT_ITEM_IDX = Internal.createIndex(DSL.name("att_item_idx"), JAttachment.ATTACHMENT, new OrderField[] { JAttachment.ATTACHMENT.ITEM_ID }, false);
    public static final Index ATT_LAUNCH_IDX = Internal.createIndex(DSL.name("att_launch_idx"), JAttachment.ATTACHMENT, new OrderField[] { JAttachment.ATTACHMENT.LAUNCH_ID }, false);
    public static final Index ATT_PROJECT_IDX = Internal.createIndex(DSL.name("att_project_idx"), JAttachment.ATTACHMENT, new OrderField[] { JAttachment.ATTACHMENT.PROJECT_ID }, false);
    public static final Index ATTACHMENT_PROJECT_ID_CREATION_TIME_IDX = Internal.createIndex(DSL.name("attachment_project_id_creation_time_idx"), JAttachment.ATTACHMENT, new OrderField[] { JAttachment.ATTACHMENT.PROJECT_ID, JAttachment.ATTACHMENT.CREATION_DATE }, false);
    public static final Index CLUSTER_INDEX_ID_IDX = Internal.createIndex(DSL.name("cluster_index_id_idx"), JClusters.CLUSTERS, new OrderField[] { JClusters.CLUSTERS.INDEX_ID }, false);
    public static final Index CLUSTER_ITEM_CLUSTER_IDX = Internal.createIndex(DSL.name("cluster_item_cluster_idx"), JClustersTestItem.CLUSTERS_TEST_ITEM, new OrderField[] { JClustersTestItem.CLUSTERS_TEST_ITEM.CLUSTER_ID }, false);
    public static final Index CLUSTER_ITEM_ITEM_IDX = Internal.createIndex(DSL.name("cluster_item_item_idx"), JClustersTestItem.CLUSTERS_TEST_ITEM, new OrderField[] { JClustersTestItem.CLUSTERS_TEST_ITEM.ITEM_ID }, false);
    public static final Index CLUSTER_LAUNCH_IDX = Internal.createIndex(DSL.name("cluster_launch_idx"), JClusters.CLUSTERS, new OrderField[] { JClusters.CLUSTERS.LAUNCH_ID }, false);
    public static final Index CLUSTER_PROJECT_IDX = Internal.createIndex(DSL.name("cluster_project_idx"), JClusters.CLUSTERS, new OrderField[] { JClusters.CLUSTERS.PROJECT_ID }, false);
    public static final Index CONTENT_FIELD_IDX = Internal.createIndex(DSL.name("content_field_idx"), JContentField.CONTENT_FIELD, new OrderField[] { JContentField.CONTENT_FIELD.FIELD }, false);
    public static final Index CONTENT_FIELD_WIDGET_IDX = Internal.createIndex(DSL.name("content_field_widget_idx"), JContentField.CONTENT_FIELD, new OrderField[] { JContentField.CONTENT_FIELD.ID }, false);
    public static final Index FILTER_COND_FILTER_IDX = Internal.createIndex(DSL.name("filter_cond_filter_idx"), JFilterCondition.FILTER_CONDITION, new OrderField[] { JFilterCondition.FILTER_CONDITION.FILTER_ID }, false);
    public static final Index FILTER_SORT_FILTER_IDX = Internal.createIndex(DSL.name("filter_sort_filter_idx"), JFilterSort.FILTER_SORT, new OrderField[] { JFilterSort.FILTER_SORT.FILTER_ID }, false);
    public static final Index HASH_API_KEYS_IDX = Internal.createIndex(DSL.name("hash_api_keys_idx"), JApiKeys.API_KEYS, new OrderField[] { JApiKeys.API_KEYS.HASH }, false);
    public static final Index INTEGR_PROJECT_IDX = Internal.createIndex(DSL.name("integr_project_idx"), JIntegration.INTEGRATION, new OrderField[] { JIntegration.INTEGRATION.PROJECT_ID }, false);
    public static final Index ISSUE_IT_IDX = Internal.createIndex(DSL.name("issue_it_idx"), JIssue.ISSUE, new OrderField[] { JIssue.ISSUE.ISSUE_TYPE }, false);
    public static final Index ISSUE_TYPE_GROUP_IDX = Internal.createIndex(DSL.name("issue_type_group_idx"), JIssueType.ISSUE_TYPE, new OrderField[] { JIssueType.ISSUE_TYPE.ISSUE_GROUP_ID }, false);
    public static final Index ITEM_ATTR_LAUNCH_IDX = Internal.createIndex(DSL.name("item_attr_launch_idx"), JItemAttribute.ITEM_ATTRIBUTE, new OrderField[] { JItemAttribute.ITEM_ATTRIBUTE.LAUNCH_ID }, false);
    public static final Index ITEM_ATTR_TI_IDX = Internal.createIndex(DSL.name("item_attr_ti_idx"), JItemAttribute.ITEM_ATTRIBUTE, new OrderField[] { JItemAttribute.ITEM_ATTRIBUTE.ITEM_ID }, false);
    public static final Index ITEM_TEST_CASE_ID_LAUNCH_ID_IDX = Internal.createIndex(DSL.name("item_test_case_id_launch_id_idx"), JTestItem.TEST_ITEM, new OrderField[] { JTestItem.TEST_ITEM.TEST_CASE_ID, JTestItem.TEST_ITEM.LAUNCH_ID }, false);
    public static final Index L_ATTR_RL_SEND_CASE_IDX = Internal.createIndex(DSL.name("l_attr_rl_send_case_idx"), JLaunchAttributeRules.LAUNCH_ATTRIBUTE_RULES, new OrderField[] { JLaunchAttributeRules.LAUNCH_ATTRIBUTE_RULES.SENDER_CASE_ID }, false);
    public static final Index LAUNCH_PROJECT_START_TIME_IDX = Internal.createIndex(DSL.name("launch_project_start_time_idx"), JLaunch.LAUNCH, new OrderField[] { JLaunch.LAUNCH.PROJECT_ID, JLaunch.LAUNCH.START_TIME }, false);
    public static final Index LAUNCH_USER_IDX = Internal.createIndex(DSL.name("launch_user_idx"), JLaunch.LAUNCH, new OrderField[] { JLaunch.LAUNCH.USER_ID }, false);
    public static final Index LN_SEND_CASE_IDX = Internal.createIndex(DSL.name("ln_send_case_idx"), JLaunchNames.LAUNCH_NAMES, new OrderField[] { JLaunchNames.LAUNCH_NAMES.SENDER_CASE_ID }, false);
    public static final Index LOG_ATTACH_ID_IDX = Internal.createIndex(DSL.name("log_attach_id_idx"), JLog.LOG, new OrderField[] { JLog.LOG.ATTACHMENT_ID }, false);
    public static final Index LOG_CLUSTER_IDX = Internal.createIndex(DSL.name("log_cluster_idx"), JLog.LOG, new OrderField[] { JLog.LOG.CLUSTER_ID }, false);
    public static final Index LOG_LAUNCH_ID_IDX = Internal.createIndex(DSL.name("log_launch_id_idx"), JLog.LOG, new OrderField[] { JLog.LOG.LAUNCH_ID }, false);
    public static final Index LOG_PROJECT_ID_LOG_TIME_IDX = Internal.createIndex(DSL.name("log_project_id_log_time_idx"), JLog.LOG, new OrderField[] { JLog.LOG.PROJECT_ID, JLog.LOG.LOG_TIME }, false);
    public static final Index LOG_PROJECT_IDX = Internal.createIndex(DSL.name("log_project_idx"), JLog.LOG, new OrderField[] { JLog.LOG.PROJECT_ID }, false);
    public static final Index LOG_TI_IDX = Internal.createIndex(DSL.name("log_ti_idx"), JLog.LOG, new OrderField[] { JLog.LOG.ITEM_ID }, false);
    public static final Index PARAMETER_TI_IDX = Internal.createIndex(DSL.name("parameter_ti_idx"), JParameter.PARAMETER, new OrderField[] { JParameter.PARAMETER.ITEM_ID }, false);
    public static final Index PATH_GIST_IDX = Internal.createIndex(DSL.name("path_gist_idx"), JTestItem.TEST_ITEM, new OrderField[] { JTestItem.TEST_ITEM.PATH }, false);
    public static final Index PATTERN_ITEM_ITEM_ID_IDX = Internal.createIndex(DSL.name("pattern_item_item_id_idx"), JPatternTemplateTestItem.PATTERN_TEMPLATE_TEST_ITEM, new OrderField[] { JPatternTemplateTestItem.PATTERN_TEMPLATE_TEST_ITEM.ITEM_ID }, false);
    public static final Index RCPNT_SEND_CASE_IDX = Internal.createIndex(DSL.name("rcpnt_send_case_idx"), JRecipients.RECIPIENTS, new OrderField[] { JRecipients.RECIPIENTS.SENDER_CASE_ID }, false);
    public static final Index SENDER_CASE_PROJECT_IDX = Internal.createIndex(DSL.name("sender_case_project_idx"), JSenderCase.SENDER_CASE, new OrderField[] { JSenderCase.SENDER_CASE.PROJECT_ID }, false);
    public static final Index SHARED_ENTITY_OWNERX = Internal.createIndex(DSL.name("shared_entity_ownerx"), JOwnedEntity.OWNED_ENTITY, new OrderField[] { JOwnedEntity.OWNED_ENTITY.OWNER }, false);
    public static final Index SHARED_ENTITY_PROJECT_IDX = Internal.createIndex(DSL.name("shared_entity_project_idx"), JOwnedEntity.OWNED_ENTITY, new OrderField[] { JOwnedEntity.OWNED_ENTITY.PROJECT_ID }, false);
    public static final Index STALE_MV_CREATION_DATE_IDX = Internal.createIndex(DSL.name("stale_mv_creation_date_idx"), JStaleMaterializedView.STALE_MATERIALIZED_VIEW, new OrderField[] { JStaleMaterializedView.STALE_MATERIALIZED_VIEW.CREATION_DATE }, false);
    public static final Index STATISTICS_LAUNCH_IDX = Internal.createIndex(DSL.name("statistics_launch_idx"), JStatistics.STATISTICS, new OrderField[] { JStatistics.STATISTICS.LAUNCH_ID }, false);
    public static final Index STATISTICS_TI_IDX = Internal.createIndex(DSL.name("statistics_ti_idx"), JStatistics.STATISTICS, new OrderField[] { JStatistics.STATISTICS.ITEM_ID }, false);
    public static final Index TEST_CASE_HASH_LAUNCH_ID_IDX = Internal.createIndex(DSL.name("test_case_hash_launch_id_idx"), JTestItem.TEST_ITEM, new OrderField[] { JTestItem.TEST_ITEM.TEST_CASE_HASH, JTestItem.TEST_ITEM.LAUNCH_ID }, false);
    public static final Index TEST_ITEM_UNIQUE_ID_LAUNCH_ID_IDX = Internal.createIndex(DSL.name("test_item_unique_id_launch_id_idx"), JTestItem.TEST_ITEM, new OrderField[] { JTestItem.TEST_ITEM.UNIQUE_ID, JTestItem.TEST_ITEM.LAUNCH_ID }, false);
    public static final Index TI_LAUNCH_IDX = Internal.createIndex(DSL.name("ti_launch_idx"), JTestItem.TEST_ITEM, new OrderField[] { JTestItem.TEST_ITEM.LAUNCH_ID }, false);
    public static final Index TI_PARENT_IDX = Internal.createIndex(DSL.name("ti_parent_idx"), JTestItem.TEST_ITEM, new OrderField[] { JTestItem.TEST_ITEM.PARENT_ID }, false);
    public static final Index TI_RETRY_OF_IDX = Internal.createIndex(DSL.name("ti_retry_of_idx"), JTestItem.TEST_ITEM, new OrderField[] { JTestItem.TEST_ITEM.RETRY_OF }, false);
    public static final Index TICKET_ID_IDX = Internal.createIndex(DSL.name("ticket_id_idx"), JTicket.TICKET, new OrderField[] { JTicket.TICKET.TICKET_ID }, false);
    public static final Index TICKET_SUBMITTER_IDX = Internal.createIndex(DSL.name("ticket_submitter_idx"), JTicket.TICKET, new OrderField[] { JTicket.TICKET.SUBMITTER }, false);
    public static final Index UNIQUE_GLOBAL_INTEGRATION_NAME = Internal.createIndex(DSL.name("unique_global_integration_name"), JIntegration.INTEGRATION, new OrderField[] { JIntegration.INTEGRATION.NAME, JIntegration.INTEGRATION.TYPE }, true);
    public static final Index UNIQUE_PROJECT_INTEGRATION_NAME = Internal.createIndex(DSL.name("unique_project_integration_name"), JIntegration.INTEGRATION, new OrderField[] { JIntegration.INTEGRATION.NAME, JIntegration.INTEGRATION.TYPE, JIntegration.INTEGRATION.PROJECT_ID }, true);
    public static final Index UNIQUE_RULE_NAME_PER_PROJECT_RULE_TYPE = Internal.createIndex(DSL.name("unique_rule_name_per_project_rule_type"), JSenderCase.SENDER_CASE, new OrderField[] { JSenderCase.SENDER_CASE.RULE_NAME, JSenderCase.SENDER_CASE.PROJECT_ID, JSenderCase.SENDER_CASE.RULE_TYPE }, true);
}
