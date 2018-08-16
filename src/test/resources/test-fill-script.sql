INSERT INTO project (name, additional_info, creation_date) VALUES ('default_personal', 'additional info', '2018-07-19 13:25:00');
INSERT INTO project_configuration (id, project_type, interrupt_timeout, keep_logs_interval, keep_screenshots_interval, created_on)
VALUES ((SELECT currval(pg_get_serial_sequence('project', 'id'))), 'PERSONAL', '1 day', '1 month', '2 weeks', '2018-07-19 13:25:00');

INSERT INTO users (login, password, email, role, type, default_project_id, full_name, expired)
VALUES ('default', '3fde6bb0541387e4ebdadf7c2ff31123', 'defaultemail@domain.com', 'USER', 'INTERNAL',
        (SELECT currval(pg_get_serial_sequence('project', 'id'))), 'tester', false);

INSERT INTO project_user (user_id, project_id, project_role)
VALUES ((SELECT currval(pg_get_serial_sequence('users', 'id'))), (SELECT currval(pg_get_serial_sequence('project', 'id'))), 'MEMBER');

INSERT INTO project (name, additional_info, creation_date) VALUES ('superadmin_personal', 'another additional info', '2018-07-19 14:25:00');
INSERT INTO project_configuration (id, project_type, interrupt_timeout, keep_logs_interval, keep_screenshots_interval, created_on)
VALUES ((SELECT currval(pg_get_serial_sequence('project', 'id'))), 'PERSONAL', '1 day', '1 month', '2 weeks', '2018-07-19 14:25:00');

INSERT INTO users (login, password, email, role, type, default_project_id, full_name, expired)
VALUES ('superadmin', '5d39d85bddde885f6579f8121e11eba2', 'superadminemail@domain.com', 'ADMINISTRATOR', 'INTERNAL',
        (SELECT currval(pg_get_serial_sequence('project', 'id'))), 'tester', false);

INSERT INTO project_user (user_id, project_id, project_role) VALUES
  ((SELECT currval(pg_get_serial_sequence('users', 'id'))), (SELECT currval(pg_get_serial_sequence('project', 'id'))), 'PROJECT_MANAGER');

INSERT INTO integration_type(
	name, auth_flow, creation_date, group_type)
	VALUES ('test integration type', 'LDAP', '2018-07-19 13:25:00', 'NOTIFICATION');

INSERT INTO integration(
	project_id, type, enabled, creation_date)
	VALUES ((SELECT currval(pg_get_serial_sequence('project', 'id'))), (SELECT currval(pg_get_serial_sequence('integration_type', 'id'))), true, '2018-07-19 13:25:00');

INSERT INTO ldap_synchronization_attributes(
	email, full_name, photo)
	VALUES ('mail', 'displayName', 'thumbnailPhoto');

INSERT INTO filter (id, name, project_id, target, description) VALUES (1, 'launch name', 1, 'com.epam.ta.reportportal.entity.launch.Launch', null);
INSERT INTO filter (id, name, project_id, target, description) VALUES (2, 'launch_name_filter', 1, 'com.epam.ta.reportportal.entity.Activity', null);

INSERT INTO user_filter(id) VALUES (1);
INSERT INTO user_filter(id) VALUES (2);

INSERT INTO filter_condition (id, filter_id, condition, value, field, negative) VALUES (8, 1, 'NOT_EQUALS', 'IN_PROGRESS', 'status', false);
INSERT INTO filter_condition (id, filter_id, condition, value, field, negative) VALUES (7, 1, 'EQUALS', 'DEFAULT', 'mode', false);
INSERT INTO filter_condition (id, filter_id, condition, value, field, negative) VALUES (6, 1, 'EQUALS', '1', 'project_id', false);
INSERT INTO filter_condition (id, filter_id, condition, value, field, negative) VALUES (10, 2, 'EQUALS', '1', 'project_id', false);

INSERT INTO widget (id, name, description, widget_type, items_count, project_id, filter_id) VALUES (2, 'start', null, 'launch_statistics', 1000, 1, 1);
INSERT INTO widget (id, name, description, widget_type, items_count, project_id, filter_id) VALUES (4, 'start', null, 'passing_rate_per_launch', 1000, 1, 1);
INSERT INTO widget (id, name, description, widget_type, items_count, project_id, filter_id) VALUES (5, 'start', null, 'passing_rate_summary', 1000, 1, 1);
INSERT INTO widget (id, name, description, widget_type, items_count, project_id, filter_id) VALUES (6, 'start', null, 'cases_trend', 1000, 1, 1);
INSERT INTO widget (id, name, description, widget_type, items_count, project_id, filter_id) VALUES (7, 'my widget', null, 'bug_trend', 1000, 1, 1);
INSERT INTO widget (id, name, description, widget_type, items_count, project_id, filter_id) VALUES (3, 'start', null, 'investigated_trend', 1000, 1, 1);
INSERT INTO widget (id, name, description, widget_type, items_count, project_id, filter_id) VALUES (12, 'table', null, 'launches_table', 1000, 1, 1);
INSERT INTO widget (id, name, description, widget_type, items_count, project_id, filter_id) VALUES (8, 'comparison', null, 'launches_comparison_chart', 1000, 1, 1);
INSERT INTO widget (id, name, description, widget_type, items_count, project_id, filter_id) VALUES (9, 'duration', null, 'launches_duration_chart', 1000, 1, 1);
INSERT INTO widget (id, name, description, widget_type, items_count, project_id, filter_id) VALUES (10, 'not passed', null, 'not_passed', 1000, 1, 1);
INSERT INTO widget (id, name, description, widget_type, items_count, project_id, filter_id) VALUES (11, 'not passed', null, 'most_failed_test_cases', 1000, 1, 1);
INSERT INTO widget (id, name, description, widget_type, items_count, project_id, filter_id) VALUES (17, 'table', null, 'activity_stream', 1000, 1, 2);
INSERT INTO widget (id, name, description, widget_type, items_count, project_id, filter_id) VALUES (1, 'start', null, 'overall_statistics', 1000, 1, 1);
INSERT INTO widget (id, name, description, widget_type, items_count, project_id, filter_id) VALUES (18, 'unique', null, 'unique_bug_table', 1000, 1, 2);

INSERT INTO public.widget_option (id, widget_id, option, value) VALUES (1, 1, 'filterName', 'New_filter');
INSERT INTO public.widget_option (id, widget_id, option, value) VALUES (2, 2, 'filterName', 'New_filter');
INSERT INTO public.widget_option (id, widget_id, option, value) VALUES (3, 3, 'filterName', 'New_filter');
INSERT INTO public.widget_option (id, widget_id, option, value) VALUES (5, 5, 'filterName', 'New filter');
INSERT INTO public.widget_option (id, widget_id, option, value) VALUES (6, 6, 'filterName', 'New filter');
INSERT INTO public.widget_option (id, widget_id, option, value) VALUES (7, 7, 'filterName', 'New filter');
INSERT INTO public.widget_option (id, widget_id, option, value) VALUES (9, 9, 'filterName', 'New filter');
INSERT INTO public.widget_option (id, widget_id, option, value) VALUES (10, 10, 'filterName', 'New filter');
INSERT INTO public.widget_option (id, widget_id, option, value) VALUES (11, 11, 'launch_name_filter', 'launch name');
INSERT INTO public.widget_option (id, widget_id, option, value) VALUES (12, 12, 'filterName', 'New filter');
INSERT INTO public.widget_option (id, widget_id, option, value) VALUES (8, 8, 'launch_name_filter', 'launch name');
INSERT INTO public.widget_option (id, widget_id, option, value) VALUES (4, 4, 'launch_name_filter', 'launch name');
INSERT INTO public.widget_option (id, widget_id, option, value) VALUES (17, 17, 'login', 'default');

INSERT INTO content_field (id, widget_id, field) VALUES (1, 2, 'executions');
INSERT INTO content_field (id, widget_id, field) VALUES (2, 2, 'defects');
INSERT INTO content_field (id, widget_id, field) VALUES (3, 4, 'executions');
INSERT INTO content_field (id, widget_id, field) VALUES (4, 5, 'executions');
INSERT INTO content_field (id, widget_id, field) VALUES (5, 6, 'executions');
INSERT INTO content_field (id, widget_id, field) VALUES (6, 7, 'defects');
INSERT INTO content_field (id, widget_id, field) VALUES (8, 8, 'executions');
INSERT INTO content_field (id, widget_id, field) VALUES (9, 9, 'executions');
INSERT INTO content_field (id, widget_id, field) VALUES (10, 10, 'executions');
INSERT INTO content_field (id, widget_id, field) VALUES (11, 12, 'executions');
INSERT INTO content_field (id, widget_id, field) VALUES (12, 12, 'defects');
INSERT INTO content_field (id, widget_id, field) VALUES (7, 8, 'groups');
INSERT INTO content_field (id, widget_id, field) VALUES (13, 12, 'columns');
INSERT INTO content_field (id, widget_id, field) VALUES (14, 17, 'activity_type');

INSERT INTO content_field_value (id, value) VALUES (1, 'FAILED');
INSERT INTO content_field_value (id, value) VALUES (1, 'SKIPPED');
INSERT INTO content_field_value (id, value) VALUES (1, 'PASSED');
INSERT INTO content_field_value (id, value) VALUES (2, 'AB001');
INSERT INTO content_field_value (id, value) VALUES (2, 'AB002');
INSERT INTO content_field_value (id, value) VALUES (2, 'SI001');
INSERT INTO content_field_value (id, value) VALUES (2, 'TI001');
INSERT INTO content_field_value (id, value) VALUES (2, 'PB001');
INSERT INTO content_field_value (id, value) VALUES (2, 'ND001');
INSERT INTO content_field_value (id, value) VALUES (3, 'FAILED');
INSERT INTO content_field_value (id, value) VALUES (3, 'SKIPPED');
INSERT INTO content_field_value (id, value) VALUES (4, 'FAILED');
INSERT INTO content_field_value (id, value) VALUES (4, 'SKIPPED');
INSERT INTO content_field_value (id, value) VALUES (4, 'PASSED');
INSERT INTO content_field_value (id, value) VALUES (5, 'FAILED');
INSERT INTO content_field_value (id, value) VALUES (5, 'SKIPPED');
INSERT INTO content_field_value (id, value) VALUES (5, 'PASSED');
INSERT INTO content_field_value (id, value) VALUES (6, 'AB001');
INSERT INTO content_field_value (id, value) VALUES (6, 'PB001');
INSERT INTO content_field_value (id, value) VALUES (6, 'SI001');
INSERT INTO content_field_value (id, value) VALUES (8, 'FAILED');
INSERT INTO content_field_value (id, value) VALUES (8, 'SKIPPED');
INSERT INTO content_field_value (id, value) VALUES (8, 'PASSED');
INSERT INTO content_field_value (id, value) VALUES (9, 'FAILED');
INSERT INTO content_field_value (id, value) VALUES (9, 'SKIPPED');
INSERT INTO content_field_value (id, value) VALUES (9, 'PASSED');
INSERT INTO content_field_value (id, value) VALUES (10, 'FAILED');
INSERT INTO content_field_value (id, value) VALUES (10, 'SKIPPED');
INSERT INTO content_field_value (id, value) VALUES (10, 'PASSED');
INSERT INTO content_field_value (id, value) VALUES (11, 'FAILED');
INSERT INTO content_field_value (id, value) VALUES (11, 'SKIPPED');
INSERT INTO content_field_value (id, value) VALUES (11, 'PASSED');
INSERT INTO content_field_value (id, value) VALUES (12, 'AB001');
INSERT INTO content_field_value (id, value) VALUES (12, 'AB002');
INSERT INTO content_field_value (id, value) VALUES (12, 'PB001');
INSERT INTO content_field_value (id, value) VALUES (12, 'TI001');
INSERT INTO content_field_value (id, value) VALUES (12, 'ND001');
INSERT INTO content_field_value (id, value) VALUES (12, 'SI001');
INSERT INTO content_field_value (id, value) VALUES (3, 'PASSED');
INSERT INTO content_field_value (id, value) VALUES (7, 'AUTOMATION_BUG');
INSERT INTO content_field_value (id, value) VALUES (7, 'SYSTEM_ISSUE');
INSERT INTO content_field_value (id, value) VALUES (7, 'NO_DEFECT');
INSERT INTO content_field_value (id, value) VALUES (7, 'TO_INVESTIGATE');
INSERT INTO content_field_value (id, value) VALUES (7, 'PRODUCT_BUG');
INSERT INTO content_field_value (id, value) VALUES (13, 'number');
INSERT INTO content_field_value (id, value) VALUES (13, 'last_modified');
INSERT INTO content_field_value (id, value) VALUES (6, 'ND001');
INSERT INTO content_field_value (id, value) VALUES (14, 'UPDATE_LAUNCH');
INSERT INTO content_field_value (id, value) VALUES (14, 'CREATE_ITEM');


INSERT INTO issue_group(issue_group_id, issue_group) VALUES (1, 'TO_INVESTIGATE');
INSERT INTO issue_group(issue_group_id, issue_group) VALUES (2, 'AUTOMATION_BUG');
INSERT INTO issue_group(issue_group_id, issue_group) VALUES (3, 'PRODUCT_BUG');
INSERT INTO issue_group(issue_group_id, issue_group) VALUES (4, 'NO_DEFECT');
INSERT INTO issue_group(issue_group_id, issue_group) VALUES (5, 'SYSTEM_ISSUE');

INSERT INTO issue_type (issue_group_id, locator, issue_name, abbreviation, hex_color)
VALUES (1, 'TI001', 'To Investigate', 'TI', '#ffb743');
INSERT INTO issue_type (issue_group_id, locator, issue_name, abbreviation, hex_color)
VALUES (2, 'AB001', 'Automation Bug', 'AB', '#f7d63e');
INSERT INTO issue_type (issue_group_id, locator, issue_name, abbreviation, hex_color)
VALUES (3, 'PB001', 'Product Bug', 'PB', '#ec3900');
INSERT INTO issue_type (issue_group_id, locator, issue_name, abbreviation, hex_color)
VALUES (4, 'ND001', 'No Defect', 'ND', '#777777');
INSERT INTO issue_type (issue_group_id, locator, issue_name, abbreviation, hex_color)
VALUES (5, 'SI001', 'System Issue', 'SI', '#0274d1');
INSERT INTO issue_type (issue_group_id, locator, issue_name, abbreviation, hex_color)
VALUES (2, 'AB002', 'My custom automation', 'CA', '#0276d1');

CREATE OR REPLACE FUNCTION initSteps()
  RETURNS INT8 AS
$BODY$
DECLARE   counter       INT = 0;
  DECLARE step_counter  INT = 0;
  DECLARE cur_launch_id BIGINT;
  DECLARE cur_suite_id  BIGINT;
  DECLARE cur_item_id   BIGINT;
  DECLARE cur_step_id   BIGINT;
  DECLARE rand_status   STATUS_ENUM;
BEGIN
  WHILE counter < 20 LOOP
    INSERT INTO launch (uuid, project_id, user_id, name, description, start_time, end_time, "number", mode, status)
    VALUES
      ('fc51ec81-de6f-4f3b-9630-f3f3a3490def', 1, 1, 'launch name', 'Description', now(), now(), counter+1, 'DEFAULT',
       'FAILED');
    cur_launch_id = (SELECT currval(pg_get_serial_sequence('launch', 'id')));

    INSERT INTO test_item_structure (launch_id) VALUES (cur_launch_id);
    cur_suite_id = (SELECT currval(pg_get_serial_sequence('test_item_structure', 'structure_id')));
    INSERT INTO test_item (item_id, name, type, start_time, description, last_modified, unique_id)
    VALUES (cur_suite_id, 'First suite', 'SUITE', now(), 'description', now(), 'uniqueId1');
    INSERT INTO test_item_results (result_id, status, duration, end_time) VALUES (cur_suite_id, 'FAILED', 0.35, now());

    INSERT INTO test_item_structure (parent_id, launch_id) VALUES (cur_suite_id, cur_launch_id);
    cur_item_id = (SELECT currval(pg_get_serial_sequence('test_item_structure', 'structure_id')));
    INSERT INTO test_item (item_id, name, type, start_time, description, last_modified, unique_id)
    VALUES (cur_item_id, 'First test', 'TEST', now(), 'description', now(), 'uniqueId2');
    INSERT INTO test_item_results (result_id, status, duration, end_time) VALUES (cur_item_id, 'FAILED', 0.35, now());

    WHILE step_counter < 30 LOOP
      rand_status = (ARRAY ['PASSED' :: STATUS_ENUM, 'SKIPPED' :: STATUS_ENUM, 'FAILED' :: STATUS_ENUM]) [floor(random() * 3) + 1];

      INSERT INTO test_item_structure (parent_id, launch_id) VALUES (cur_item_id, cur_launch_id);
      cur_step_id = (SELECT currval(pg_get_serial_sequence('test_item_structure', 'structure_id')));

      INSERT INTO test_item (item_id, NAME, TYPE, start_time, description, last_modified, unique_id)
      VALUES (cur_step_id, 'Step', 'STEP', now(), 'description', now(), 'uniqueId3');

      INSERT INTO test_item_results (result_id, status, duration, end_time) VALUES (cur_step_id, rand_status, 0.35, now());

      UPDATE test_item_results
      SET status = rand_status
      WHERE result_id = cur_step_id;

      IF rand_status = 'FAILED'
      THEN
        INSERT INTO issue (issue_id, issue_type, issue_description) VALUES (cur_step_id, floor(random() * 6 + 1), 'issue description');
      END IF;

      step_counter = step_counter + 1;
    END LOOP;
    step_counter = 0;
    counter = counter + 1;
  END LOOP;
  RETURN NULL;
END;
$BODY$
LANGUAGE plpgsql;

INSERT INTO issue_type_project_configuration (configuration_id, issue_type_id) VALUES (1, 1);
INSERT INTO issue_type_project_configuration (configuration_id, issue_type_id) VALUES (1, 2);
INSERT INTO issue_type_project_configuration (configuration_id, issue_type_id) VALUES (1, 3);
INSERT INTO issue_type_project_configuration (configuration_id, issue_type_id) VALUES (1, 4);
INSERT INTO issue_type_project_configuration (configuration_id, issue_type_id) VALUES (1, 5);
INSERT INTO issue_type_project_configuration (configuration_id, issue_type_id) VALUES (1, 6);

SELECT initSteps();


INSERT INTO statistics(launch_id, s_name, s_counter) VALUES(1, 'statisitcs$executions$total', 10);
INSERT INTO statistics(launch_id, s_name, s_counter) VALUES(1, 'statisitcs$executions$passed', 3);
INSERT INTO statistics(launch_id, s_name, s_counter) VALUES(1, 'statisitcs$executions$skipped', 4);
INSERT INTO statistics(launch_id, s_name, s_counter) VALUES(1, 'statisitcs$executions$failed', 3);
INSERT INTO statistics(launch_id, s_name, s_counter) VALUES(1, 'statisitcs$defects$total', 32);
INSERT INTO statistics(launch_id, s_name, s_counter) VALUES(1, 'statisitcs$defects$to_investigate$TI001', 2);
INSERT INTO statistics(launch_id, s_name, s_counter) VALUES(1, 'statisitcs$defects$system_issue$SI001', 8);
INSERT INTO statistics(launch_id, s_name, s_counter) VALUES(1, 'statisitcs$defects$automation_bug$AB001', 7);
INSERT INTO statistics(launch_id, s_name, s_counter) VALUES(1, 'statisitcs$defects$product_bug$PB001', 13);
INSERT INTO statistics(launch_id, s_name, s_counter) VALUES(1, 'statisitcs$defects$no_defect$ND001', 2);

INSERT INTO statistics(launch_id, s_name, s_counter) VALUES(2, 'statisitcs$executions$total', 11);
INSERT INTO statistics(launch_id, s_name, s_counter) VALUES(2, 'statisitcs$executions$passed', 2);
INSERT INTO statistics(launch_id, s_name, s_counter) VALUES(2, 'statisitcs$executions$skipped', 3);
INSERT INTO statistics(launch_id, s_name, s_counter) VALUES(2, 'statisitcs$executions$failed', 6);
INSERT INTO statistics(launch_id, s_name, s_counter) VALUES(2, 'statisitcs$defects$total', 10);
INSERT INTO statistics(launch_id, s_name, s_counter) VALUES(2, 'statisitcs$defects$to_investigate$TI001', 3);
INSERT INTO statistics(launch_id, s_name, s_counter) VALUES(2, 'statisitcs$defects$system_issue$SI001', 3);
INSERT INTO statistics(launch_id, s_name, s_counter) VALUES(2, 'statisitcs$defects$automation_bug$AB001', 1);
INSERT INTO statistics(launch_id, s_name, s_counter) VALUES(2, 'statisitcs$defects$product_bug$PB001', 1);
INSERT INTO statistics(launch_id, s_name, s_counter) VALUES(2, 'statisitcs$defects$no_defect$ND001', 2);

INSERT INTO statistics(launch_id, s_name, s_counter) VALUES(3, 'statisitcs$executions$total', 15);
INSERT INTO statistics(launch_id, s_name, s_counter) VALUES(3, 'statisitcs$executions$passed', 5);
INSERT INTO statistics(launch_id, s_name, s_counter) VALUES(3, 'statisitcs$executions$skipped', 5);
INSERT INTO statistics(launch_id, s_name, s_counter) VALUES(3, 'statisitcs$executions$failed', 5);
INSERT INTO statistics(launch_id, s_name, s_counter) VALUES(3, 'statisitcs$defects$total', 5);
INSERT INTO statistics(launch_id, s_name, s_counter) VALUES(3, 'statisitcs$defects$to_investigate$TI001', 1);
INSERT INTO statistics(launch_id, s_name, s_counter) VALUES(3, 'statisitcs$defects$system_issue$SI001', 1);
INSERT INTO statistics(launch_id, s_name, s_counter) VALUES(3, 'statisitcs$defects$automation_bug$AB001', 1);
INSERT INTO statistics(launch_id, s_name, s_counter) VALUES(3, 'statisitcs$defects$product_bug$PB001', 1);
INSERT INTO statistics(launch_id, s_name, s_counter) VALUES(3, 'statisitcs$defects$no_defect$ND001', 1);

INSERT INTO statistics(launch_id, s_name, s_counter) VALUES(4, 'statisitcs$executions$total', 12);
INSERT INTO statistics(launch_id, s_name, s_counter) VALUES(4, 'statisitcs$executions$passed', 3);
INSERT INTO statistics(launch_id, s_name, s_counter) VALUES(4, 'statisitcs$executions$skipped', 1);
INSERT INTO statistics(launch_id, s_name, s_counter) VALUES(4, 'statisitcs$executions$failed', 8);
INSERT INTO statistics(launch_id, s_name, s_counter) VALUES(4, 'statisitcs$defects$total', 17);
INSERT INTO statistics(launch_id, s_name, s_counter) VALUES(4, 'statisitcs$defects$to_investigate$TI001', 3);
INSERT INTO statistics(launch_id, s_name, s_counter) VALUES(4, 'statisitcs$defects$system_issue$SI001', 4);
INSERT INTO statistics(launch_id, s_name, s_counter) VALUES(4, 'statisitcs$defects$automation_bug$AB001', 2);
INSERT INTO statistics(launch_id, s_name, s_counter) VALUES(4, 'statisitcs$defects$product_bug$PB001', 2);
INSERT INTO statistics(launch_id, s_name, s_counter) VALUES(4, 'statisitcs$defects$no_defect$ND001', 6);
.;


--INSERT INTO launch(
--  uuid, project_id, user_id, name, description, start_time, end_time, number, last_modified, mode, status)
--VALUES ('asdas-qwert-12345', 1, 1, 'test launch', 'it is a test launch', '2018-05-25 13:12:32', '2018-05-25 13:12:34', 1, '2018-05-24 14:32:05', 'DEFAULT', 'FAILED');
--INSERT INTO launch(
--  uuid, project_id, user_id, name, description, start_time, end_time, number, last_modified, mode, status)
--VALUES ('asdas-qwert-12345', 1, 1, 'test launch', 'it is a test launch', '2018-05-25 13:12:32', '2018-05-25 13:12:34', 2, '2018-05-24 14:32:05', 'DEFAULT', 'FAILED');
--INSERT INTO launch(
--  uuid, project_id, user_id, name, description, start_time, end_time, number, last_modified, mode, status)
--VALUES ('asdas-qwert-12345', 1, 1, 'test launch', 'it is a test launch', '2018-05-25 13:12:32', '2018-05-25 13:12:34', 3, '2018-05-24 14:32:05', 'DEFAULT', 'FAILED');
--INSERT INTO launch(
--  uuid, project_id, user_id, name, description, start_time, end_time, number, last_modified, mode, status)
--VALUES ('asdas-qwert-12345', 1, 1, 'test launch', 'it is a test launch', '2018-05-25 13:12:32', '2018-05-25 13:12:34', 4, '2018-05-24 14:32:05', 'DEFAULT', 'FAILED');
--INSERT INTO launch(
--  uuid, project_id, user_id, name, description, start_time, end_time, number, last_modified, mode, status)
--VALUES ('asdas-qwert-12345', 1, 1, 'test launch', 'it is a test launch', '2018-05-25 13:12:32', '2018-05-25 13:12:34', 5, '2018-05-24 14:32:05', 'DEFAULT', 'FAILED');
--INSERT INTO launch(
--  uuid, project_id, user_id, name, description, start_time, end_time, number, last_modified, mode, status)
--VALUES ('asdas-qwert-12345', 1, 1, 'test launch', 'it is a test launch', '2018-05-25 13:12:32', '2018-05-25 13:12:34', 6, '2018-05-24 14:32:05', 'DEFAULT', 'FAILED');
--INSERT INTO launch(
--  uuid, project_id, user_id, name, description, start_time, end_time, number, last_modified, mode, status)
--VALUES ('asdas-qwert-12345', 1, 1, 'test launch', 'it is a test launch', '2018-05-25 13:12:32', '2018-05-25 13:12:34', 7, '2018-05-24 14:32:05', 'DEFAULT', 'FAILED');
--INSERT INTO launch(
--  uuid, project_id, user_id, name, description, start_time, end_time, number, last_modified, mode, status)
--VALUES ('asdas-qwert-12345', 1, 1, 'test launch', 'it is a test launch', '2018-05-25 13:12:32', '2018-05-25 13:12:34', 8, '2018-05-24 14:32:05', 'DEFAULT', 'FAILED');
--INSERT INTO launch(
--  uuid, project_id, user_id, name, description, start_time, end_time, number, last_modified, mode, status)
--VALUES ('asdas-qwert-12345', 1, 1, 'test launch', 'it is a test launch', '2018-05-25 13:12:32', '2018-05-25 13:12:34', 9, '2018-05-24 14:32:05', 'DEFAULT', 'FAILED');
--INSERT INTO launch(
--  uuid, project_id, user_id, name, description, start_time, end_time, number, last_modified, mode, status)
--VALUES ('asdas-qwert-12345', 1, 1, 'test launch', 'it is a test launch', '2018-05-25 13:12:32', '2018-05-25 13:12:34', 10, '2018-05-24 14:32:05', 'DEFAULT', 'FAILED');
--INSERT INTO launch(
--  uuid, project_id, user_id, name, description, start_time, end_time, number, last_modified, mode, status)
--VALUES ('asdas-qwert-12345', 1, 1, 'test launch', 'it is a test launch', '2018-05-25 13:12:32', '2018-05-25 13:12:34', 11, '2018-05-24 14:32:05', 'DEFAULT', 'FAILED');
--INSERT INTO launch(
--  uuid, project_id, user_id, name, description, start_time, end_time, number, last_modified, mode, status)
--VALUES ('asdas-qwert-12345', 1, 1, 'test launch', 'it is a test launch', '2018-05-25 13:12:32', '2018-05-25 13:12:34', 12, '2018-05-24 14:32:05', 'DEFAULT', 'FAILED');
--INSERT INTO launch(
--  uuid, project_id, user_id, name, description, start_time, end_time, number, last_modified, mode, status)
--VALUES ('asdas-qwert-12345', 1, 1, 'test launch', 'it is a test launch', '2018-05-25 13:12:32', '2018-05-25 13:12:34', 13, '2018-05-24 14:32:05', 'DEFAULT', 'FAILED');
--INSERT INTO launch(
--  uuid, project_id, user_id, name, description, start_time, end_time, number, last_modified, mode, status)
--VALUES ('asdas-qwert-12345', 1, 1, 'test launch', 'it is a test launch', '2018-05-25 13:12:32', '2018-05-25 13:12:34', 14, '2018-05-24 14:32:05', 'DEFAULT', 'FAILED');
--INSERT INTO launch(
--  uuid, project_id, user_id, name, description, start_time, end_time, number, last_modified, mode, status)
--VALUES ('asdas-qwert-12345', 1, 1, 'test launch', 'it is a test launch', '2018-05-25 13:12:32', '2018-05-25 13:12:34', 15, '2018-05-24 14:32:05', 'DEFAULT', 'FAILED');



--INSERT INTO issue_statistics(
--  issue_type_id, is_counter, item_id, launch_id)
--VALUES (1, 15, null, 1);
--INSERT INTO issue_statistics(
--  issue_type_id, is_counter, item_id, launch_id)
--VALUES (2, 16, null, 1);
--INSERT INTO issue_statistics(
--  issue_type_id, is_counter, item_id, launch_id)
--VALUES (3, 17, null, 1);
--INSERT INTO issue_statistics(
--  issue_type_id, is_counter, item_id, launch_id)
--VALUES (4, 18, null, 1);
--INSERT INTO issue_statistics(
--  issue_type_id, is_counter, item_id, launch_id)
--VALUES (5, 19, null, 1);
--INSERT INTO issue_statistics(
--  issue_type_id, is_counter, item_id, launch_id)
--VALUES (6, 20, null, 1);
--
--INSERT INTO issue_statistics(
--  issue_type_id, is_counter, item_id, launch_id)
--VALUES (1, 22, null, 2);
--INSERT INTO issue_statistics(
--  issue_type_id, is_counter, item_id, launch_id)
--VALUES (2, 23, null, 2);
--INSERT INTO issue_statistics(
--  issue_type_id, is_counter, item_id, launch_id)
--VALUES (3, 24, null, 2);
--INSERT INTO issue_statistics(
--  issue_type_id, is_counter, item_id, launch_id)
--VALUES (4, 25, null, 2);
--INSERT INTO issue_statistics(
--  issue_type_id, is_counter, item_id, launch_id)
--VALUES (5, 26, null, 2);
--INSERT INTO issue_statistics(
--  issue_type_id, is_counter, item_id, launch_id)
--VALUES (6, 27, null, 2);
--
--INSERT INTO issue_statistics(
--  issue_type_id, is_counter, item_id, launch_id)
--VALUES (1, 31, null, 3);
--INSERT INTO issue_statistics(
--  issue_type_id, is_counter, item_id, launch_id)
--VALUES (2, 32, null, 3);
--INSERT INTO issue_statistics(
--  issue_type_id, is_counter, item_id, launch_id)
--VALUES (3, 33, null, 3);
--INSERT INTO issue_statistics(
--  issue_type_id, is_counter, item_id, launch_id)
--VALUES (4, 33, null, 3);
--INSERT INTO issue_statistics(
--  issue_type_id, is_counter, item_id, launch_id)
--VALUES (5, 34, null, 3);
--INSERT INTO issue_statistics(
--  issue_type_id, is_counter, item_id, launch_id)
--VALUES (6, 35, null, 3);
--
--INSERT INTO issue_statistics(
--  issue_type_id, is_counter, item_id, launch_id)
--VALUES (1, 43, null, 4);
--INSERT INTO issue_statistics(
--  issue_type_id, is_counter, item_id, launch_id)
--VALUES (2, 44, null, 4);
--INSERT INTO issue_statistics(
--  issue_type_id, is_counter, item_id, launch_id)
--VALUES (3, 45, null, 4);
--INSERT INTO issue_statistics(
--  issue_type_id, is_counter, item_id, launch_id)
--VALUES (4, 46, null, 4);
--INSERT INTO issue_statistics(
--  issue_type_id, is_counter, item_id, launch_id)
--VALUES (5, 47, null, 4);
--INSERT INTO issue_statistics(
--  issue_type_id, is_counter, item_id, launch_id)
--VALUES (6, 48, null, 4);
--
--INSERT INTO issue_statistics(
--  issue_type_id, is_counter, item_id, launch_id)
--VALUES (1, 54, null, 5);
--INSERT INTO issue_statistics(
--  issue_type_id, is_counter, item_id, launch_id)
--VALUES (2, 55, null, 5);
--INSERT INTO issue_statistics(
--  issue_type_id, is_counter, item_id, launch_id)
--VALUES (3, 56, null, 5);
--INSERT INTO issue_statistics(
--  issue_type_id, is_counter, item_id, launch_id)
--VALUES (4, 57, null, 5);
--INSERT INTO issue_statistics(
--  issue_type_id, is_counter, item_id, launch_id)
--VALUES (5, 58, null, 5);
--INSERT INTO issue_statistics(
--  issue_type_id, is_counter, item_id, launch_id)
--VALUES (6, 59, null, 5);
