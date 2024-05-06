/*
 * This file is generated by jOOQ.
 */
package com.epam.ta.reportportal.jooq;


import com.epam.ta.reportportal.jooq.tables.JActivity;
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

import javax.annotation.processing.Generated;

import org.jooq.Configuration;
import org.jooq.Field;
import org.jooq.Result;


/**
 * Convenience access to all tables in public
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.12.4"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Tables {

    /**
     * The table <code>public.activity</code>.
     */
    public static final JActivity ACTIVITY = JActivity.ACTIVITY;

    /**
     * The table <code>public.api_keys</code>.
     */
    public static final JApiKeys API_KEYS = JApiKeys.API_KEYS;

    /**
     * The table <code>public.attachment</code>.
     */
    public static final JAttachment ATTACHMENT = JAttachment.ATTACHMENT;

    /**
     * The table <code>public.attachment_deletion</code>.
     */
    public static final JAttachmentDeletion ATTACHMENT_DELETION = JAttachmentDeletion.ATTACHMENT_DELETION;

    /**
     * The table <code>public.attribute</code>.
     */
    public static final JAttribute ATTRIBUTE = JAttribute.ATTRIBUTE;

    /**
     * The table <code>public.clusters</code>.
     */
    public static final JClusters CLUSTERS = JClusters.CLUSTERS;

    /**
     * The table <code>public.clusters_test_item</code>.
     */
    public static final JClustersTestItem CLUSTERS_TEST_ITEM = JClustersTestItem.CLUSTERS_TEST_ITEM;

    /**
     * The table <code>public.content_field</code>.
     */
    public static final JContentField CONTENT_FIELD = JContentField.CONTENT_FIELD;

    /**
     * The table <code>public.dashboard</code>.
     */
    public static final JDashboard DASHBOARD = JDashboard.DASHBOARD;

    /**
     * The table <code>public.dashboard_widget</code>.
     */
    public static final JDashboardWidget DASHBOARD_WIDGET = JDashboardWidget.DASHBOARD_WIDGET;

    /**
     * The table <code>public.filter</code>.
     */
    public static final JFilter FILTER = JFilter.FILTER;

    /**
     * The table <code>public.filter_condition</code>.
     */
    public static final JFilterCondition FILTER_CONDITION = JFilterCondition.FILTER_CONDITION;

    /**
     * The table <code>public.filter_sort</code>.
     */
    public static final JFilterSort FILTER_SORT = JFilterSort.FILTER_SORT;

    /**
     * The table <code>public.integration</code>.
     */
    public static final JIntegration INTEGRATION = JIntegration.INTEGRATION;

    /**
     * The table <code>public.integration_type</code>.
     */
    public static final JIntegrationType INTEGRATION_TYPE = JIntegrationType.INTEGRATION_TYPE;

    /**
     * The table <code>public.issue</code>.
     */
    public static final JIssue ISSUE = JIssue.ISSUE;

    /**
     * The table <code>public.issue_group</code>.
     */
    public static final JIssueGroup ISSUE_GROUP = JIssueGroup.ISSUE_GROUP;

    /**
     * The table <code>public.issue_ticket</code>.
     */
    public static final JIssueTicket ISSUE_TICKET = JIssueTicket.ISSUE_TICKET;

    /**
     * The table <code>public.issue_type</code>.
     */
    public static final JIssueType ISSUE_TYPE = JIssueType.ISSUE_TYPE;

    /**
     * The table <code>public.issue_type_project</code>.
     */
    public static final JIssueTypeProject ISSUE_TYPE_PROJECT = JIssueTypeProject.ISSUE_TYPE_PROJECT;

    /**
     * The table <code>public.item_attribute</code>.
     */
    public static final JItemAttribute ITEM_ATTRIBUTE = JItemAttribute.ITEM_ATTRIBUTE;

    /**
     * The table <code>public.launch</code>.
     */
    public static final JLaunch LAUNCH = JLaunch.LAUNCH;

    /**
     * The table <code>public.launch_attribute_rules</code>.
     */
    public static final JLaunchAttributeRules LAUNCH_ATTRIBUTE_RULES = JLaunchAttributeRules.LAUNCH_ATTRIBUTE_RULES;

    /**
     * The table <code>public.launch_names</code>.
     */
    public static final JLaunchNames LAUNCH_NAMES = JLaunchNames.LAUNCH_NAMES;

    /**
     * The table <code>public.launch_number</code>.
     */
    public static final JLaunchNumber LAUNCH_NUMBER = JLaunchNumber.LAUNCH_NUMBER;

    /**
     * The table <code>public.log</code>.
     */
    public static final JLog LOG = JLog.LOG;

    /**
     * The table <code>public.oauth_registration</code>.
     */
    public static final JOauthRegistration OAUTH_REGISTRATION = JOauthRegistration.OAUTH_REGISTRATION;

    /**
     * The table <code>public.oauth_registration_restriction</code>.
     */
    public static final JOauthRegistrationRestriction OAUTH_REGISTRATION_RESTRICTION = JOauthRegistrationRestriction.OAUTH_REGISTRATION_RESTRICTION;

    /**
     * The table <code>public.oauth_registration_scope</code>.
     */
    public static final JOauthRegistrationScope OAUTH_REGISTRATION_SCOPE = JOauthRegistrationScope.OAUTH_REGISTRATION_SCOPE;

    /**
     * The table <code>public.organization</code>.
     */
    public static final JOrganization ORGANIZATION = JOrganization.ORGANIZATION;

    /**
     * The table <code>public.organization_user</code>.
     */
    public static final JOrganizationUser ORGANIZATION_USER = JOrganizationUser.ORGANIZATION_USER;

    /**
     * The table <code>public.owned_entity</code>.
     */
    public static final JOwnedEntity OWNED_ENTITY = JOwnedEntity.OWNED_ENTITY;

    /**
     * The table <code>public.parameter</code>.
     */
    public static final JParameter PARAMETER = JParameter.PARAMETER;

    /**
     * The table <code>public.pattern_template</code>.
     */
    public static final JPatternTemplate PATTERN_TEMPLATE = JPatternTemplate.PATTERN_TEMPLATE;

    /**
     * The table <code>public.pattern_template_test_item</code>.
     */
    public static final JPatternTemplateTestItem PATTERN_TEMPLATE_TEST_ITEM = JPatternTemplateTestItem.PATTERN_TEMPLATE_TEST_ITEM;

    /**
     * The table <code>public.pgp_armor_headers</code>.
     */
    public static final JPgpArmorHeaders PGP_ARMOR_HEADERS = JPgpArmorHeaders.PGP_ARMOR_HEADERS;

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
    public static final JProject PROJECT = JProject.PROJECT;

    /**
     * The table <code>public.project_attribute</code>.
     */
    public static final JProjectAttribute PROJECT_ATTRIBUTE = JProjectAttribute.PROJECT_ATTRIBUTE;

    /**
     * The table <code>public.project_user</code>.
     */
    public static final JProjectUser PROJECT_USER = JProjectUser.PROJECT_USER;

    /**
     * The table <code>public.recipients</code>.
     */
    public static final JRecipients RECIPIENTS = JRecipients.RECIPIENTS;

    /**
     * The table <code>public.restore_password_bid</code>.
     */
    public static final JRestorePasswordBid RESTORE_PASSWORD_BID = JRestorePasswordBid.RESTORE_PASSWORD_BID;

    /**
     * The table <code>public.sender_case</code>.
     */
    public static final JSenderCase SENDER_CASE = JSenderCase.SENDER_CASE;

    /**
     * The table <code>public.server_settings</code>.
     */
    public static final JServerSettings SERVER_SETTINGS = JServerSettings.SERVER_SETTINGS;

    /**
     * The table <code>public.shedlock</code>.
     */
    public static final JShedlock SHEDLOCK = JShedlock.SHEDLOCK;

    /**
     * The table <code>public.stale_materialized_view</code>.
     */
    public static final JStaleMaterializedView STALE_MATERIALIZED_VIEW = JStaleMaterializedView.STALE_MATERIALIZED_VIEW;

    /**
     * The table <code>public.statistics</code>.
     */
    public static final JStatistics STATISTICS = JStatistics.STATISTICS;

    /**
     * The table <code>public.statistics_field</code>.
     */
    public static final JStatisticsField STATISTICS_FIELD = JStatisticsField.STATISTICS_FIELD;

    /**
     * The table <code>public.test_item</code>.
     */
    public static final JTestItem TEST_ITEM = JTestItem.TEST_ITEM;

    /**
     * The table <code>public.test_item_results</code>.
     */
    public static final JTestItemResults TEST_ITEM_RESULTS = JTestItemResults.TEST_ITEM_RESULTS;

    /**
     * The table <code>public.ticket</code>.
     */
    public static final JTicket TICKET = JTicket.TICKET;

    /**
     * The table <code>public.user_creation_bid</code>.
     */
    public static final JUserCreationBid USER_CREATION_BID = JUserCreationBid.USER_CREATION_BID;

    /**
     * The table <code>public.user_preference</code>.
     */
    public static final JUserPreference USER_PREFERENCE = JUserPreference.USER_PREFERENCE;

    /**
     * The table <code>public.users</code>.
     */
    public static final JUsers USERS = JUsers.USERS;

    /**
     * The table <code>public.widget</code>.
     */
    public static final JWidget WIDGET = JWidget.WIDGET;

    /**
     * The table <code>public.widget_filter</code>.
     */
    public static final JWidgetFilter WIDGET_FILTER = JWidgetFilter.WIDGET_FILTER;
}
