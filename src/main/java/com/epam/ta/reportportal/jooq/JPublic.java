/*
 * This file is generated by jOOQ.
 */
package com.epam.ta.reportportal.jooq;


import com.epam.ta.reportportal.jooq.tables.JActivity;
import com.epam.ta.reportportal.jooq.tables.JAnalyticsData;
import com.epam.ta.reportportal.jooq.tables.JApiKeys;
import com.epam.ta.reportportal.jooq.tables.JAttachment;
import com.epam.ta.reportportal.jooq.tables.JAttachmentDeletion;
import com.epam.ta.reportportal.jooq.tables.JAttribute;
import com.epam.ta.reportportal.jooq.tables.JClusters;
import com.epam.ta.reportportal.jooq.tables.JClustersTestItem;
import com.epam.ta.reportportal.jooq.tables.JContentField;
import com.epam.ta.reportportal.jooq.tables.JDashboard;
import com.epam.ta.reportportal.jooq.tables.JDashboardWidget;
import com.epam.ta.reportportal.jooq.tables.JFilter;
import com.epam.ta.reportportal.jooq.tables.JFilterCondition;
import com.epam.ta.reportportal.jooq.tables.JFilterSort;
import com.epam.ta.reportportal.jooq.tables.JIntegration;
import com.epam.ta.reportportal.jooq.tables.JIntegrationType;
import com.epam.ta.reportportal.jooq.tables.JIssue;
import com.epam.ta.reportportal.jooq.tables.JIssueGroup;
import com.epam.ta.reportportal.jooq.tables.JIssueTicket;
import com.epam.ta.reportportal.jooq.tables.JIssueType;
import com.epam.ta.reportportal.jooq.tables.JIssueTypeProject;
import com.epam.ta.reportportal.jooq.tables.JItemAttribute;
import com.epam.ta.reportportal.jooq.tables.JLaunch;
import com.epam.ta.reportportal.jooq.tables.JLaunchAttributeRules;
import com.epam.ta.reportportal.jooq.tables.JLaunchNames;
import com.epam.ta.reportportal.jooq.tables.JLaunchNumber;
import com.epam.ta.reportportal.jooq.tables.JLog;
import com.epam.ta.reportportal.jooq.tables.JOauthRegistration;
import com.epam.ta.reportportal.jooq.tables.JOauthRegistrationRestriction;
import com.epam.ta.reportportal.jooq.tables.JOauthRegistrationScope;
import com.epam.ta.reportportal.jooq.tables.JOrganization;
import com.epam.ta.reportportal.jooq.tables.JOrganizationUser;
import com.epam.ta.reportportal.jooq.tables.JOwnedEntity;
import com.epam.ta.reportportal.jooq.tables.JParameter;
import com.epam.ta.reportportal.jooq.tables.JPatternTemplate;
import com.epam.ta.reportportal.jooq.tables.JPatternTemplateTestItem;
import com.epam.ta.reportportal.jooq.tables.JPgpArmorHeaders;
import com.epam.ta.reportportal.jooq.tables.JProject;
import com.epam.ta.reportportal.jooq.tables.JProjectAttribute;
import com.epam.ta.reportportal.jooq.tables.JProjectUser;
import com.epam.ta.reportportal.jooq.tables.JRecipients;
import com.epam.ta.reportportal.jooq.tables.JRestorePasswordBid;
import com.epam.ta.reportportal.jooq.tables.JSenderCase;
import com.epam.ta.reportportal.jooq.tables.JServerSettings;
import com.epam.ta.reportportal.jooq.tables.JShedlock;
import com.epam.ta.reportportal.jooq.tables.JStaleMaterializedView;
import com.epam.ta.reportportal.jooq.tables.JStatistics;
import com.epam.ta.reportportal.jooq.tables.JStatisticsField;
import com.epam.ta.reportportal.jooq.tables.JTestItem;
import com.epam.ta.reportportal.jooq.tables.JTestItemResults;
import com.epam.ta.reportportal.jooq.tables.JTicket;
import com.epam.ta.reportportal.jooq.tables.JUserCreationBid;
import com.epam.ta.reportportal.jooq.tables.JUserPreference;
import com.epam.ta.reportportal.jooq.tables.JUsers;
import com.epam.ta.reportportal.jooq.tables.JWidget;
import com.epam.ta.reportportal.jooq.tables.JWidgetFilter;
import com.epam.ta.reportportal.jooq.tables.records.JPgpArmorHeadersRecord;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.processing.Generated;

import org.jooq.Catalog;
import org.jooq.Configuration;
import org.jooq.Field;
import org.jooq.Result;
import org.jooq.Sequence;
import org.jooq.Table;
import org.jooq.impl.SchemaImpl;


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
public class JPublic extends SchemaImpl {

    private static final long serialVersionUID = 1294370065;

    /**
     * The reference instance of <code>public</code>
     */
    public static final JPublic PUBLIC = new JPublic();

    /**
     * The table <code>public.activity</code>.
     */
    public final JActivity ACTIVITY = com.epam.ta.reportportal.jooq.tables.JActivity.ACTIVITY;

    /**
     * The table <code>public.analytics_data</code>.
     */
    public final JAnalyticsData ANALYTICS_DATA = com.epam.ta.reportportal.jooq.tables.JAnalyticsData.ANALYTICS_DATA;

    /**
     * The table <code>public.api_keys</code>.
     */
    public final JApiKeys API_KEYS = com.epam.ta.reportportal.jooq.tables.JApiKeys.API_KEYS;

    /**
     * The table <code>public.attachment</code>.
     */
    public final JAttachment ATTACHMENT = com.epam.ta.reportportal.jooq.tables.JAttachment.ATTACHMENT;

    /**
     * The table <code>public.attachment_deletion</code>.
     */
    public final JAttachmentDeletion ATTACHMENT_DELETION = com.epam.ta.reportportal.jooq.tables.JAttachmentDeletion.ATTACHMENT_DELETION;

    /**
     * The table <code>public.attribute</code>.
     */
    public final JAttribute ATTRIBUTE = com.epam.ta.reportportal.jooq.tables.JAttribute.ATTRIBUTE;

    /**
     * The table <code>public.clusters</code>.
     */
    public final JClusters CLUSTERS = com.epam.ta.reportportal.jooq.tables.JClusters.CLUSTERS;

    /**
     * The table <code>public.clusters_test_item</code>.
     */
    public final JClustersTestItem CLUSTERS_TEST_ITEM = com.epam.ta.reportportal.jooq.tables.JClustersTestItem.CLUSTERS_TEST_ITEM;

    /**
     * The table <code>public.content_field</code>.
     */
    public final JContentField CONTENT_FIELD = com.epam.ta.reportportal.jooq.tables.JContentField.CONTENT_FIELD;

    /**
     * The table <code>public.dashboard</code>.
     */
    public final JDashboard DASHBOARD = com.epam.ta.reportportal.jooq.tables.JDashboard.DASHBOARD;

    /**
     * The table <code>public.dashboard_widget</code>.
     */
    public final JDashboardWidget DASHBOARD_WIDGET = com.epam.ta.reportportal.jooq.tables.JDashboardWidget.DASHBOARD_WIDGET;

    /**
     * The table <code>public.filter</code>.
     */
    public final JFilter FILTER = com.epam.ta.reportportal.jooq.tables.JFilter.FILTER;

    /**
     * The table <code>public.filter_condition</code>.
     */
    public final JFilterCondition FILTER_CONDITION = com.epam.ta.reportportal.jooq.tables.JFilterCondition.FILTER_CONDITION;

    /**
     * The table <code>public.filter_sort</code>.
     */
    public final JFilterSort FILTER_SORT = com.epam.ta.reportportal.jooq.tables.JFilterSort.FILTER_SORT;

    /**
     * The table <code>public.integration</code>.
     */
    public final JIntegration INTEGRATION = com.epam.ta.reportportal.jooq.tables.JIntegration.INTEGRATION;

    /**
     * The table <code>public.integration_type</code>.
     */
    public final JIntegrationType INTEGRATION_TYPE = com.epam.ta.reportportal.jooq.tables.JIntegrationType.INTEGRATION_TYPE;

    /**
     * The table <code>public.issue</code>.
     */
    public final JIssue ISSUE = com.epam.ta.reportportal.jooq.tables.JIssue.ISSUE;

    /**
     * The table <code>public.issue_group</code>.
     */
    public final JIssueGroup ISSUE_GROUP = com.epam.ta.reportportal.jooq.tables.JIssueGroup.ISSUE_GROUP;

    /**
     * The table <code>public.issue_ticket</code>.
     */
    public final JIssueTicket ISSUE_TICKET = com.epam.ta.reportportal.jooq.tables.JIssueTicket.ISSUE_TICKET;

    /**
     * The table <code>public.issue_type</code>.
     */
    public final JIssueType ISSUE_TYPE = com.epam.ta.reportportal.jooq.tables.JIssueType.ISSUE_TYPE;

    /**
     * The table <code>public.issue_type_project</code>.
     */
    public final JIssueTypeProject ISSUE_TYPE_PROJECT = com.epam.ta.reportportal.jooq.tables.JIssueTypeProject.ISSUE_TYPE_PROJECT;

    /**
     * The table <code>public.item_attribute</code>.
     */
    public final JItemAttribute ITEM_ATTRIBUTE = com.epam.ta.reportportal.jooq.tables.JItemAttribute.ITEM_ATTRIBUTE;

    /**
     * The table <code>public.launch</code>.
     */
    public final JLaunch LAUNCH = com.epam.ta.reportportal.jooq.tables.JLaunch.LAUNCH;

    /**
     * The table <code>public.launch_attribute_rules</code>.
     */
    public final JLaunchAttributeRules LAUNCH_ATTRIBUTE_RULES = com.epam.ta.reportportal.jooq.tables.JLaunchAttributeRules.LAUNCH_ATTRIBUTE_RULES;

    /**
     * The table <code>public.launch_names</code>.
     */
    public final JLaunchNames LAUNCH_NAMES = com.epam.ta.reportportal.jooq.tables.JLaunchNames.LAUNCH_NAMES;

    /**
     * The table <code>public.launch_number</code>.
     */
    public final JLaunchNumber LAUNCH_NUMBER = com.epam.ta.reportportal.jooq.tables.JLaunchNumber.LAUNCH_NUMBER;

    /**
     * The table <code>public.log</code>.
     */
    public final JLog LOG = com.epam.ta.reportportal.jooq.tables.JLog.LOG;

    /**
     * The table <code>public.oauth_registration</code>.
     */
    public final JOauthRegistration OAUTH_REGISTRATION = com.epam.ta.reportportal.jooq.tables.JOauthRegistration.OAUTH_REGISTRATION;

    /**
     * The table <code>public.oauth_registration_restriction</code>.
     */
    public final JOauthRegistrationRestriction OAUTH_REGISTRATION_RESTRICTION = com.epam.ta.reportportal.jooq.tables.JOauthRegistrationRestriction.OAUTH_REGISTRATION_RESTRICTION;

    /**
     * The table <code>public.oauth_registration_scope</code>.
     */
    public final JOauthRegistrationScope OAUTH_REGISTRATION_SCOPE = com.epam.ta.reportportal.jooq.tables.JOauthRegistrationScope.OAUTH_REGISTRATION_SCOPE;

    /**
     * The table <code>public.organization</code>.
     */
    public final JOrganization ORGANIZATION = com.epam.ta.reportportal.jooq.tables.JOrganization.ORGANIZATION;

    /**
     * The table <code>public.organization_user</code>.
     */
    public final JOrganizationUser ORGANIZATION_USER = com.epam.ta.reportportal.jooq.tables.JOrganizationUser.ORGANIZATION_USER;

    /**
     * The table <code>public.owned_entity</code>.
     */
    public final JOwnedEntity OWNED_ENTITY = com.epam.ta.reportportal.jooq.tables.JOwnedEntity.OWNED_ENTITY;

    /**
     * The table <code>public.parameter</code>.
     */
    public final JParameter PARAMETER = com.epam.ta.reportportal.jooq.tables.JParameter.PARAMETER;

    /**
     * The table <code>public.pattern_template</code>.
     */
    public final JPatternTemplate PATTERN_TEMPLATE = com.epam.ta.reportportal.jooq.tables.JPatternTemplate.PATTERN_TEMPLATE;

    /**
     * The table <code>public.pattern_template_test_item</code>.
     */
    public final JPatternTemplateTestItem PATTERN_TEMPLATE_TEST_ITEM = com.epam.ta.reportportal.jooq.tables.JPatternTemplateTestItem.PATTERN_TEMPLATE_TEST_ITEM;

    /**
     * The table <code>public.pgp_armor_headers</code>.
     */
    public final JPgpArmorHeaders PGP_ARMOR_HEADERS = com.epam.ta.reportportal.jooq.tables.JPgpArmorHeaders.PGP_ARMOR_HEADERS;

    /**
     * Call <code>public.pgp_armor_headers</code>.
     */
    public static Result<JPgpArmorHeadersRecord> PGP_ARMOR_HEADERS(Configuration configuration, String __1) {
        return configuration.dsl().selectFrom(com.epam.ta.reportportal.jooq.tables.JPgpArmorHeaders.PGP_ARMOR_HEADERS.call(__1)).fetch();
    }

    /**
     * Get <code>public.pgp_armor_headers</code> as a table.
     */
    public static JPgpArmorHeaders PGP_ARMOR_HEADERS(String __1) {
        return com.epam.ta.reportportal.jooq.tables.JPgpArmorHeaders.PGP_ARMOR_HEADERS.call(__1);
    }

    /**
     * Get <code>public.pgp_armor_headers</code> as a table.
     */
    public static JPgpArmorHeaders PGP_ARMOR_HEADERS(Field<String> __1) {
        return com.epam.ta.reportportal.jooq.tables.JPgpArmorHeaders.PGP_ARMOR_HEADERS.call(__1);
    }

    /**
     * The table <code>public.project</code>.
     */
    public final JProject PROJECT = com.epam.ta.reportportal.jooq.tables.JProject.PROJECT;

    /**
     * The table <code>public.project_attribute</code>.
     */
    public final JProjectAttribute PROJECT_ATTRIBUTE = com.epam.ta.reportportal.jooq.tables.JProjectAttribute.PROJECT_ATTRIBUTE;

    /**
     * The table <code>public.project_user</code>.
     */
    public final JProjectUser PROJECT_USER = com.epam.ta.reportportal.jooq.tables.JProjectUser.PROJECT_USER;

    /**
     * The table <code>public.recipients</code>.
     */
    public final JRecipients RECIPIENTS = com.epam.ta.reportportal.jooq.tables.JRecipients.RECIPIENTS;

    /**
     * The table <code>public.restore_password_bid</code>.
     */
    public final JRestorePasswordBid RESTORE_PASSWORD_BID = com.epam.ta.reportportal.jooq.tables.JRestorePasswordBid.RESTORE_PASSWORD_BID;

    /**
     * The table <code>public.sender_case</code>.
     */
    public final JSenderCase SENDER_CASE = com.epam.ta.reportportal.jooq.tables.JSenderCase.SENDER_CASE;

    /**
     * The table <code>public.server_settings</code>.
     */
    public final JServerSettings SERVER_SETTINGS = com.epam.ta.reportportal.jooq.tables.JServerSettings.SERVER_SETTINGS;

    /**
     * The table <code>public.shedlock</code>.
     */
    public final JShedlock SHEDLOCK = com.epam.ta.reportportal.jooq.tables.JShedlock.SHEDLOCK;

    /**
     * The table <code>public.stale_materialized_view</code>.
     */
    public final JStaleMaterializedView STALE_MATERIALIZED_VIEW = com.epam.ta.reportportal.jooq.tables.JStaleMaterializedView.STALE_MATERIALIZED_VIEW;

    /**
     * The table <code>public.statistics</code>.
     */
    public final JStatistics STATISTICS = com.epam.ta.reportportal.jooq.tables.JStatistics.STATISTICS;

    /**
     * The table <code>public.statistics_field</code>.
     */
    public final JStatisticsField STATISTICS_FIELD = com.epam.ta.reportportal.jooq.tables.JStatisticsField.STATISTICS_FIELD;

    /**
     * The table <code>public.test_item</code>.
     */
    public final JTestItem TEST_ITEM = com.epam.ta.reportportal.jooq.tables.JTestItem.TEST_ITEM;

    /**
     * The table <code>public.test_item_results</code>.
     */
    public final JTestItemResults TEST_ITEM_RESULTS = com.epam.ta.reportportal.jooq.tables.JTestItemResults.TEST_ITEM_RESULTS;

    /**
     * The table <code>public.ticket</code>.
     */
    public final JTicket TICKET = com.epam.ta.reportportal.jooq.tables.JTicket.TICKET;

    /**
     * The table <code>public.user_creation_bid</code>.
     */
    public final JUserCreationBid USER_CREATION_BID = com.epam.ta.reportportal.jooq.tables.JUserCreationBid.USER_CREATION_BID;

    /**
     * The table <code>public.user_preference</code>.
     */
    public final JUserPreference USER_PREFERENCE = com.epam.ta.reportportal.jooq.tables.JUserPreference.USER_PREFERENCE;

    /**
     * The table <code>public.users</code>.
     */
    public final JUsers USERS = com.epam.ta.reportportal.jooq.tables.JUsers.USERS;

    /**
     * The table <code>public.widget</code>.
     */
    public final JWidget WIDGET = com.epam.ta.reportportal.jooq.tables.JWidget.WIDGET;

    /**
     * The table <code>public.widget_filter</code>.
     */
    public final JWidgetFilter WIDGET_FILTER = com.epam.ta.reportportal.jooq.tables.JWidgetFilter.WIDGET_FILTER;

    /**
     * No further instances allowed
     */
    private JPublic() {
        super("public", null);
    }


    @Override
    public Catalog getCatalog() {
        return DefaultCatalog.DEFAULT_CATALOG;
    }

    @Override
    public final List<Sequence<?>> getSequences() {
        List result = new ArrayList();
        result.addAll(getSequences0());
        return result;
    }

    private final List<Sequence<?>> getSequences0() {
        return Arrays.<Sequence<?>>asList(
            Sequences.ACTIVITY_ID_SEQ,
            Sequences.ANALYTICS_DATA_ID_SEQ,
            Sequences.API_KEYS_ID_SEQ,
            Sequences.ATTACHMENT_ID_SEQ,
            Sequences.ATTRIBUTE_ID_SEQ,
            Sequences.CLUSTERS_ID_SEQ,
            Sequences.FILTER_CONDITION_ID_SEQ,
            Sequences.FILTER_SORT_ID_SEQ,
            Sequences.INTEGRATION_ID_SEQ,
            Sequences.INTEGRATION_TYPE_ID_SEQ,
            Sequences.ISSUE_GROUP_ISSUE_GROUP_ID_SEQ,
            Sequences.ISSUE_TYPE_ID_SEQ,
            Sequences.ITEM_ATTRIBUTE_ID_SEQ,
            Sequences.LAUNCH_ATTRIBUTE_RULES_ID_SEQ,
            Sequences.LAUNCH_ID_SEQ,
            Sequences.LAUNCH_NUMBER_ID_SEQ,
            Sequences.LOG_ID_SEQ,
            Sequences.OAUTH_REGISTRATION_RESTRICTION_ID_SEQ,
            Sequences.OAUTH_REGISTRATION_SCOPE_ID_SEQ,
            Sequences.ORGANIZATION_ID_SEQ,
            Sequences.PATTERN_TEMPLATE_ID_SEQ,
            Sequences.PROJECT_ATTRIBUTE_ATTRIBUTE_ID_SEQ,
            Sequences.PROJECT_ATTRIBUTE_PROJECT_ID_SEQ,
            Sequences.PROJECT_ID_SEQ,
            Sequences.SENDER_CASE_ID_SEQ,
            Sequences.SENDER_CASE_PROJECT_ID_SEQ,
            Sequences.SERVER_SETTINGS_ID_SEQ,
            Sequences.SHAREABLE_ENTITY_ID_SEQ,
            Sequences.STALE_MATERIALIZED_VIEW_ID_SEQ,
            Sequences.STATISTICS_FIELD_SF_ID_SEQ,
            Sequences.STATISTICS_S_ID_SEQ,
            Sequences.TEST_ITEM_ITEM_ID_SEQ,
            Sequences.TICKET_ID_SEQ,
            Sequences.USER_PREFERENCE_ID_SEQ,
            Sequences.USERS_ID_SEQ);
    }

    @Override
    public final List<Table<?>> getTables() {
        List result = new ArrayList();
        result.addAll(getTables0());
        return result;
    }

    private final List<Table<?>> getTables0() {
        return Arrays.<Table<?>>asList(
            JActivity.ACTIVITY,
            JAnalyticsData.ANALYTICS_DATA,
            JApiKeys.API_KEYS,
            JAttachment.ATTACHMENT,
            JAttachmentDeletion.ATTACHMENT_DELETION,
            JAttribute.ATTRIBUTE,
            JClusters.CLUSTERS,
            JClustersTestItem.CLUSTERS_TEST_ITEM,
            JContentField.CONTENT_FIELD,
            JDashboard.DASHBOARD,
            JDashboardWidget.DASHBOARD_WIDGET,
            JFilter.FILTER,
            JFilterCondition.FILTER_CONDITION,
            JFilterSort.FILTER_SORT,
            JIntegration.INTEGRATION,
            JIntegrationType.INTEGRATION_TYPE,
            JIssue.ISSUE,
            JIssueGroup.ISSUE_GROUP,
            JIssueTicket.ISSUE_TICKET,
            JIssueType.ISSUE_TYPE,
            JIssueTypeProject.ISSUE_TYPE_PROJECT,
            JItemAttribute.ITEM_ATTRIBUTE,
            JLaunch.LAUNCH,
            JLaunchAttributeRules.LAUNCH_ATTRIBUTE_RULES,
            JLaunchNames.LAUNCH_NAMES,
            JLaunchNumber.LAUNCH_NUMBER,
            JLog.LOG,
            JOauthRegistration.OAUTH_REGISTRATION,
            JOauthRegistrationRestriction.OAUTH_REGISTRATION_RESTRICTION,
            JOauthRegistrationScope.OAUTH_REGISTRATION_SCOPE,
            JOrganization.ORGANIZATION,
            JOrganizationUser.ORGANIZATION_USER,
            JOwnedEntity.OWNED_ENTITY,
            JParameter.PARAMETER,
            JPatternTemplate.PATTERN_TEMPLATE,
            JPatternTemplateTestItem.PATTERN_TEMPLATE_TEST_ITEM,
            JPgpArmorHeaders.PGP_ARMOR_HEADERS,
            JProject.PROJECT,
            JProjectAttribute.PROJECT_ATTRIBUTE,
            JProjectUser.PROJECT_USER,
            JRecipients.RECIPIENTS,
            JRestorePasswordBid.RESTORE_PASSWORD_BID,
            JSenderCase.SENDER_CASE,
            JServerSettings.SERVER_SETTINGS,
            JShedlock.SHEDLOCK,
            JStaleMaterializedView.STALE_MATERIALIZED_VIEW,
            JStatistics.STATISTICS,
            JStatisticsField.STATISTICS_FIELD,
            JTestItem.TEST_ITEM,
            JTestItemResults.TEST_ITEM_RESULTS,
            JTicket.TICKET,
            JUserCreationBid.USER_CREATION_BID,
            JUserPreference.USER_PREFERENCE,
            JUsers.USERS,
            JWidget.WIDGET,
            JWidgetFilter.WIDGET_FILTER);
    }
}
