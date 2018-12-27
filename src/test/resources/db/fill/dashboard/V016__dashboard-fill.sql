INSERT INTO public.shareable_entity (id, shared, owner, project_id) VALUES (1, true, 'default', 2);
INSERT INTO public.shareable_entity (id, shared, owner, project_id) VALUES (2, true, 'default', 2);
INSERT INTO public.shareable_entity (id, shared, owner, project_id) VALUES (3, true, 'default', 2);
INSERT INTO public.shareable_entity (id, shared, owner, project_id) VALUES (4, true, 'default', 2);
INSERT INTO public.shareable_entity (id, shared, owner, project_id) VALUES (5, true, 'default', 2);
INSERT INTO public.shareable_entity (id, shared, owner, project_id) VALUES (6, true, 'default', 2);
INSERT INTO public.shareable_entity (id, shared, owner, project_id) VALUES (7, true, 'default', 2);
INSERT INTO public.shareable_entity (id, shared, owner, project_id) VALUES (8, true, 'default', 2);
INSERT INTO public.shareable_entity (id, shared, owner, project_id) VALUES (9, true, 'default', 2);
INSERT INTO public.shareable_entity (id, shared, owner, project_id) VALUES (10, true, 'default', 2);
INSERT INTO public.shareable_entity (id, shared, owner, project_id) VALUES (11, true, 'default', 2);
INSERT INTO public.shareable_entity (id, shared, owner, project_id) VALUES (12, true, 'default', 2);
INSERT INTO public.shareable_entity (id, shared, owner, project_id) VALUES (13, true, 'default', 2);
INSERT INTO public.shareable_entity (id, shared, owner, project_id) VALUES (14, true, 'default', 2);
INSERT INTO public.shareable_entity (id, shared, owner, project_id) VALUES (15, true, 'default', 2);
INSERT INTO public.shareable_entity (id, shared, owner, project_id) VALUES (16, true, 'default', 2);
INSERT INTO public.shareable_entity (id, shared, owner, project_id) VALUES (17, true, 'default', 2);
INSERT INTO public.shareable_entity (id, shared, owner, project_id) VALUES (18, true, 'default', 2);
INSERT INTO public.shareable_entity (id, shared, owner, project_id) VALUES (19, true, 'default', 2);


INSERT INTO public.dashboard (id, name, description, creation_date) VALUES (19, 'DEMO DASHBOARD', null, '2018-12-26 18:03:34.382000');

INSERT INTO public.widget (id, name, description, widget_type, items_count, widget_options) VALUES (2, 'LAUNCH STATISTICS', null, 'launchStatistics', 10, '{"options": {"timeline": "WEEK"}}');
INSERT INTO public.widget (id, name, description, widget_type, items_count, widget_options) VALUES (3, 'INVESTIGATED PERCENTAGE OF LAUNCHES', null, 'investigatedTrend', 10, '{"options": {"timeline": "DAY"}}');
INSERT INTO public.widget (id, name, description, widget_type, items_count, widget_options) VALUES (4, 'OVERALL STATISTICS PANEL', null, 'overallStatistics', 10, '{"options": {"latest": ""}}');
INSERT INTO public.widget (id, name, description, widget_type, items_count, widget_options) VALUES (5, 'TEST CASES GROWTH TREND CHART', null, 'casesTrend', 10, '{"options": {}}');
INSERT INTO public.widget (id, name, description, widget_type, items_count, widget_options) VALUES (6, 'LAUNCHES DURATION CHART', null, 'launchesDurationChart', 10, '{"options": {}}');
INSERT INTO public.widget (id, name, description, widget_type, items_count, widget_options) VALUES (7, 'FAILED CASES TREND CHART', null, 'bugTrend', 10, '{"options": {}}');
INSERT INTO public.widget (id, name, description, widget_type, items_count, widget_options) VALUES (8, 'LAUNCH TABLE', null, 'launchesTable', 10, '{"options": {}}');
INSERT INTO public.widget (id, name, description, widget_type, items_count, widget_options) VALUES (9, 'ACTIVITY STREAM', null, 'activityStream', 10, '{"options": {"user": "default"}}');
INSERT INTO public.widget (id, name, description, widget_type, items_count, widget_options) VALUES (10, 'FLAKY TEST CASES', null, 'flakyTestCases', 10, '{"options": {"launchNameFilter": "Demo Api Tests"}}');
INSERT INTO public.widget (id, name, description, widget_type, items_count, widget_options) VALUES (11, 'LAUNCHES COMPARISON CHART', null, 'launchesComparisonChart', 10, '{"options": {"launchNameFilter": "Demo Api Tests"}}');
INSERT INTO public.widget (id, name, description, widget_type, items_count, widget_options) VALUES (12, 'MOST TIME CONSUMING', null, 'mostTimeConsuming', 10, '{"options": {"launchNameFilter": "Demo Api Tests"}}');
INSERT INTO public.widget (id, name, description, widget_type, items_count, widget_options) VALUES (13, 'NOT PASSED', null, 'notPassed', 10, '{"options": {}}');
INSERT INTO public.widget (id, name, description, widget_type, items_count, widget_options) VALUES (14, 'PASSING RATE PER LAUNCH', null, 'passingRatePerLaunch', 10, '{"options": {"viewMode": "pie", "launchNameFilter": "Demo Api Tests"}}');
INSERT INTO public.widget (id, name, description, widget_type, items_count, widget_options) VALUES (15, 'PASSING RATE SUMMARY', null, 'passingRateSummary', 10, '{"options": {}}');
INSERT INTO public.widget (id, name, description, widget_type, items_count, widget_options) VALUES (16, 'UNIQUE BUG TABLE', null, 'uniqueBugTable', 10, '{"options": {"latest": "", "launchNameFilter": "Demo Api Tests"}}');
INSERT INTO public.widget (id, name, description, widget_type, items_count, widget_options) VALUES (17, 'TOP TEST CASES', null, 'topTestCases', 10, '{"options": {"launchNameFilter": "Demo Api Tests"}}');
INSERT INTO public.widget (id, name, description, widget_type, items_count, widget_options) VALUES (18, 'PRODUCT STATUS', null, 'productStatus', 10, '{"options": {"strategy": "launch", "customColumns": {"firstColumn": "build"}}}');

INSERT INTO public.dashboard_widget (dashboard_id, widget_id, widget_name, widget_width, widget_height, widget_position_x, widget_position_y) VALUES (19, 2, 'LAUNCH STATISTICS', 4, 4, 0, 6);
INSERT INTO public.dashboard_widget (dashboard_id, widget_id, widget_name, widget_width, widget_height, widget_position_x, widget_position_y) VALUES (19, 3, 'INVESTIGATED PERCENTAGE OF LAUNCHES', 11, 5, 0, 9);
INSERT INTO public.dashboard_widget (dashboard_id, widget_id, widget_name, widget_width, widget_height, widget_position_x, widget_position_y) VALUES (19, 4, 'OVERALL STATISTICS PANEL', 10, 6, 0, 16);
INSERT INTO public.dashboard_widget (dashboard_id, widget_id, widget_name, widget_width, widget_height, widget_position_x, widget_position_y) VALUES (19, 5, 'TEST CASES GROWTH TREND CHART', 11, 6, 2, 7);
INSERT INTO public.dashboard_widget (dashboard_id, widget_id, widget_name, widget_width, widget_height, widget_position_x, widget_position_y) VALUES (19, 6, 'LAUNCHES DURATION CHART', 5, 6, 7, 6);
INSERT INTO public.dashboard_widget (dashboard_id, widget_id, widget_name, widget_width, widget_height, widget_position_x, widget_position_y) VALUES (19, 7, 'FAILED CASES TREND CHART', 11, 6, 6, 14);
INSERT INTO public.dashboard_widget (dashboard_id, widget_id, widget_name, widget_width, widget_height, widget_position_x, widget_position_y) VALUES (19, 8, 'LAUNCH TABLE', 5, 6, 5, 15);
INSERT INTO public.dashboard_widget (dashboard_id, widget_id, widget_name, widget_width, widget_height, widget_position_x, widget_position_y) VALUES (19, 9, 'ACTIVITY STREAM', 4, 4, 0, 13);
INSERT INTO public.dashboard_widget (dashboard_id, widget_id, widget_name, widget_width, widget_height, widget_position_x, widget_position_y) VALUES (19, 10, 'FLAKY TEST CASES', 11, 5, 3, 8);
INSERT INTO public.dashboard_widget (dashboard_id, widget_id, widget_name, widget_width, widget_height, widget_position_x, widget_position_y) VALUES (19, 11, 'LAUNCHES COMPARISON CHART', 10, 4, 1, 8);
INSERT INTO public.dashboard_widget (dashboard_id, widget_id, widget_name, widget_width, widget_height, widget_position_x, widget_position_y) VALUES (19, 12, 'MOST TIME CONSUMING', 6, 4, 3, 4);
INSERT INTO public.dashboard_widget (dashboard_id, widget_id, widget_name, widget_width, widget_height, widget_position_x, widget_position_y) VALUES (19, 13, 'NOT PASSED', 10, 6, 6, 15);
INSERT INTO public.dashboard_widget (dashboard_id, widget_id, widget_name, widget_width, widget_height, widget_position_x, widget_position_y) VALUES (19, 14, 'PASSING RATE PER LAUNCH', 5, 4, 6, 6);
INSERT INTO public.dashboard_widget (dashboard_id, widget_id, widget_name, widget_width, widget_height, widget_position_x, widget_position_y) VALUES (19, 15, 'PASSING RATE SUMMARY', 8, 6, 1, 3);
INSERT INTO public.dashboard_widget (dashboard_id, widget_id, widget_name, widget_width, widget_height, widget_position_x, widget_position_y) VALUES (19, 16, 'UNIQUE BUG TABLE', 9, 6, 4, 11);
INSERT INTO public.dashboard_widget (dashboard_id, widget_id, widget_name, widget_width, widget_height, widget_position_x, widget_position_y) VALUES (19, 17, 'TOP TEST CASES', 4, 4, 0, 7);
INSERT INTO public.dashboard_widget (dashboard_id, widget_id, widget_name, widget_width, widget_height, widget_position_x, widget_position_y) VALUES (19, 18, 'PRODUCT STATUS', 4, 4, 0, 3);

INSERT INTO public.acl_sid (id, principal, sid) VALUES (1, true, 'default');

INSERT INTO public.acl_class (id, class) VALUES (1, 'com.epam.ta.reportportal.entity.filter.UserFilter');
INSERT INTO public.acl_class (id, class) VALUES (2, 'com.epam.ta.reportportal.entity.widget.Widget');
INSERT INTO public.acl_class (id, class) VALUES (3, 'com.epam.ta.reportportal.entity.dashboard.Dashboard');

INSERT INTO public.acl_object_identity (id, object_id_class, object_id_identity, parent_object, owner_sid, entries_inheriting) VALUES (1, 1, '1', null, 1, true);
INSERT INTO public.acl_object_identity (id, object_id_class, object_id_identity, parent_object, owner_sid, entries_inheriting) VALUES (2, 2, '2', null, 1, true);
INSERT INTO public.acl_object_identity (id, object_id_class, object_id_identity, parent_object, owner_sid, entries_inheriting) VALUES (3, 2, '3', null, 1, true);
INSERT INTO public.acl_object_identity (id, object_id_class, object_id_identity, parent_object, owner_sid, entries_inheriting) VALUES (4, 2, '4', null, 1, true);
INSERT INTO public.acl_object_identity (id, object_id_class, object_id_identity, parent_object, owner_sid, entries_inheriting) VALUES (5, 2, '5', null, 1, true);
INSERT INTO public.acl_object_identity (id, object_id_class, object_id_identity, parent_object, owner_sid, entries_inheriting) VALUES (6, 2, '6', null, 1, true);
INSERT INTO public.acl_object_identity (id, object_id_class, object_id_identity, parent_object, owner_sid, entries_inheriting) VALUES (7, 2, '7', null, 1, true);
INSERT INTO public.acl_object_identity (id, object_id_class, object_id_identity, parent_object, owner_sid, entries_inheriting) VALUES (8, 2, '8', null, 1, true);
INSERT INTO public.acl_object_identity (id, object_id_class, object_id_identity, parent_object, owner_sid, entries_inheriting) VALUES (9, 2, '9', null, 1, true);
INSERT INTO public.acl_object_identity (id, object_id_class, object_id_identity, parent_object, owner_sid, entries_inheriting) VALUES (10, 2, '10', null, 1, true);
INSERT INTO public.acl_object_identity (id, object_id_class, object_id_identity, parent_object, owner_sid, entries_inheriting) VALUES (11, 2, '11', null, 1, true);
INSERT INTO public.acl_object_identity (id, object_id_class, object_id_identity, parent_object, owner_sid, entries_inheriting) VALUES (12, 2, '12', null, 1, true);
INSERT INTO public.acl_object_identity (id, object_id_class, object_id_identity, parent_object, owner_sid, entries_inheriting) VALUES (13, 2, '13', null, 1, true);
INSERT INTO public.acl_object_identity (id, object_id_class, object_id_identity, parent_object, owner_sid, entries_inheriting) VALUES (14, 2, '14', null, 1, true);
INSERT INTO public.acl_object_identity (id, object_id_class, object_id_identity, parent_object, owner_sid, entries_inheriting) VALUES (15, 2, '15', null, 1, true);
INSERT INTO public.acl_object_identity (id, object_id_class, object_id_identity, parent_object, owner_sid, entries_inheriting) VALUES (16, 2, '16', null, 1, true);
INSERT INTO public.acl_object_identity (id, object_id_class, object_id_identity, parent_object, owner_sid, entries_inheriting) VALUES (17, 2, '17', null, 1, true);
INSERT INTO public.acl_object_identity (id, object_id_class, object_id_identity, parent_object, owner_sid, entries_inheriting) VALUES (18, 2, '18', null, 1, true);
INSERT INTO public.acl_object_identity (id, object_id_class, object_id_identity, parent_object, owner_sid, entries_inheriting) VALUES (19, 3, '19', null, 1, true);

INSERT INTO public.acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) VALUES (1, 1, 0, 1, 16, true, false, false);
INSERT INTO public.acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) VALUES (2, 2, 0, 1, 16, true, false, false);
INSERT INTO public.acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) VALUES (3, 3, 0, 1, 16, true, false, false);
INSERT INTO public.acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) VALUES (4, 4, 0, 1, 16, true, false, false);
INSERT INTO public.acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) VALUES (5, 5, 0, 1, 16, true, false, false);
INSERT INTO public.acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) VALUES (6, 6, 0, 1, 16, true, false, false);
INSERT INTO public.acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) VALUES (7, 7, 0, 1, 16, true, false, false);
INSERT INTO public.acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) VALUES (8, 8, 0, 1, 16, true, false, false);
INSERT INTO public.acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) VALUES (9, 9, 0, 1, 16, true, false, false);
INSERT INTO public.acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) VALUES (10, 10, 0, 1, 16, true, false, false);
INSERT INTO public.acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) VALUES (11, 11, 0, 1, 16, true, false, false);
INSERT INTO public.acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) VALUES (12, 12, 0, 1, 16, true, false, false);
INSERT INTO public.acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) VALUES (13, 13, 0, 1, 16, true, false, false);
INSERT INTO public.acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) VALUES (14, 14, 0, 1, 16, true, false, false);
INSERT INTO public.acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) VALUES (15, 15, 0, 1, 16, true, false, false);
INSERT INTO public.acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) VALUES (16, 16, 0, 1, 16, true, false, false);
INSERT INTO public.acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) VALUES (17, 17, 0, 1, 16, true, false, false);
INSERT INTO public.acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) VALUES (18, 18, 0, 1, 16, true, false, false);
INSERT INTO public.acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) VALUES (19, 19, 0, 1, 16, true, false, false);




