INSERT INTO project (name, additional_info, creation_date) VALUES ('default_personal', 'additional info', '2018-07-19 13:25:00');
INSERT INTO project_configuration (id, project_type, interrupt_timeout, keep_logs_interval, keep_screenshots_interval, created_on)
VALUES ((SELECT currval(pg_get_serial_sequence('project', 'id'))), 'PERSONAL', '1 day', '1 month', '2 weeks', '2018-07-19 13:25:00');

INSERT INTO users (login, password, email, role, type, default_project_id, full_name, expired, metadata)
VALUES ('default', '3fde6bb0541387e4ebdadf7c2ff31123', 'defaultemail@domain.com', 'USER', 'INTERNAL',
        (SELECT currval(pg_get_serial_sequence('project', 'id'))), 'tester', false, '{"last_login":"2018-03-05 15:30:22"}');

INSERT INTO project_user (user_id, project_id, project_role)
VALUES ((SELECT currval(pg_get_serial_sequence('users', 'id'))), (SELECT currval(pg_get_serial_sequence('project', 'id'))), 'MEMBER');

INSERT INTO project (name, additional_info, creation_date) VALUES ('superadmin_personal', 'another additional info', '2018-07-19 14:25:00');
INSERT INTO project_configuration (id, project_type, interrupt_timeout, keep_logs_interval, keep_screenshots_interval, created_on)
VALUES ((SELECT currval(pg_get_serial_sequence('project', 'id'))), 'PERSONAL', '1 day', '1 month', '2 weeks', '2018-07-19 14:25:00');
INSERT INTO bug_tracking_system (url, type, bts_project, project_id) VALUES ('test.com', 'TEST TYPE', 'TEST PROJECT', (SELECT currval(pg_get_serial_sequence('project', 'id'))));

INSERT INTO public.ticket (id, ticket_id, submitter_id, submit_date, bts_id, url) VALUES (1, 'EPMRPP-322', (SELECT currval(pg_get_serial_sequence('users', 'id'))), '2018-09-28 12:38:24.374555', (SELECT currval(pg_get_serial_sequence('bug_tracking_system', 'id'))), 'epam.com');
INSERT INTO public.ticket (id, ticket_id, submitter_id, submit_date, bts_id, url) VALUES (2, 'EPMRPP-123', (SELECT currval(pg_get_serial_sequence('users', 'id'))), '2018-09-28 12:38:24.374555', (SELECT currval(pg_get_serial_sequence('bug_tracking_system', 'id'))), 'epam.com');
INSERT INTO public.ticket (id, ticket_id, submitter_id, submit_date, bts_id, url) VALUES (3, 'QWERTY-100', (SELECT currval(pg_get_serial_sequence('users', 'id'))), '2018-09-28 12:38:24.374555', (SELECT currval(pg_get_serial_sequence('bug_tracking_system', 'id'))), 'epam.com');

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
INSERT INTO filter (id, name, project_id, target, description) VALUES (3, 'test item', 1, 'com.epam.ta.reportportal.entity.item.TestItem', 'YAHOOOOO');
INSERT INTO filter (id, name, project_id, target, description) VALUES (4, 'unique bug', 1, 'com.epam.ta.reportportal.entity.bts.Ticket', 'TICKET FILTER');

INSERT INTO user_filter(id) VALUES (1);
INSERT INTO user_filter(id) VALUES (2);
INSERT INTO user_filter(id) VALUES (3);
INSERT INTO user_filter(id) VALUES (4);

INSERT INTO filter_sort(filter_id, field, direction) VALUES (2, 'creation_date', 'DESC');
INSERT INTO filter_sort(filter_id, field, direction) VALUES (1, 'statistics$defects$no_defect$ND001', 'DESC');

INSERT INTO filter_condition (id, filter_id, condition, value, field, negative) VALUES (8, 1, 'NOT_EQUALS', 'IN_PROGRESS', 'status', false);
INSERT INTO filter_condition (id, filter_id, condition, value, field, negative) VALUES (7, 1, 'EQUALS', 'DEFAULT', 'mode', false);
INSERT INTO filter_condition (id, filter_id, condition, value, field, negative) VALUES (6, 1, 'EQUALS', '1', 'project_id', false);
INSERT INTO filter_condition (id, filter_id, condition, value, field, negative) VALUES (10, 2, 'EQUALS', '1', 'project_id', false);

INSERT INTO widget (id, name, description, widget_type, items_count, project_id) VALUES (2, 'start', null, 'launch_statistics', 1000, 1);
INSERT INTO widget (id, name, description, widget_type, items_count, project_id) VALUES (4, 'start', null, 'passing_rate_per_launch', 1000, 1);
INSERT INTO widget (id, name, description, widget_type, items_count, project_id) VALUES (5, 'start', null, 'passing_rate_summary', 1000, 1);
INSERT INTO widget (id, name, description, widget_type, items_count, project_id) VALUES (6, 'start', null, 'cases_trend', 1000, 1);
INSERT INTO widget (id, name, description, widget_type, items_count, project_id) VALUES (7, 'my widget', null, 'bug_trend', 1000, 1);
INSERT INTO widget (id, name, description, widget_type, items_count, project_id) VALUES (3, 'start', null, 'investigated_trend', 1000, 1);
INSERT INTO widget (id, name, description, widget_type, items_count, project_id) VALUES (12, 'table', null, 'launches_table', 1000, 1);
INSERT INTO widget (id, name, description, widget_type, items_count, project_id) VALUES (8, 'comparison', null, 'launches_comparison_chart', 1000, 1);
INSERT INTO widget (id, name, description, widget_type, items_count, project_id) VALUES (9, 'duration', null, 'launches_duration_chart', 1000, 1);
INSERT INTO widget (id, name, description, widget_type, items_count, project_id) VALUES (10, 'not passed', null, 'not_passed', 1000, 1);
INSERT INTO widget (id, name, description, widget_type, items_count, project_id) VALUES (11, 'not passed', null, 'most_failed_test_cases', 1000, 1);
INSERT INTO widget (id, name, description, widget_type, items_count, project_id) VALUES (17, 'table', null, 'activity_stream', 1000, 1);
INSERT INTO widget (id, name, description, widget_type, items_count, project_id) VALUES (1, 'start', null, 'overall_statistics', 1000, 1);
INSERT INTO widget (id, name, description, widget_type, items_count, project_id) VALUES (18, 'unique', null, 'unique_bug_table', 1000, 1);
INSERT INTO widget (id, name, description, widget_type, items_count, project_id) VALUES (19, 'cumulative test', null, 'cumulative', 2, 1);
INSERT INTO widget (id, name, description, widget_type, items_count, project_id) VALUES (20, 'product status widget', 'description of widget', 'product_status', 2, 1);
INSERT INTO widget (id, name, description, widget_type, items_count, project_id) VALUES (21, 'most time consuming widget', 'description of widget', 'most_time_consuming', 20, 1);

INSERT INTO widget_filter(widget_id, filter_id) VALUES (2,1);
INSERT INTO widget_filter(widget_id, filter_id) VALUES (4,1);
INSERT INTO widget_filter(widget_id, filter_id) VALUES (5,1);
INSERT INTO widget_filter(widget_id, filter_id) VALUES (6,1);
INSERT INTO widget_filter(widget_id, filter_id) VALUES (7,1);
INSERT INTO widget_filter(widget_id, filter_id) VALUES (3,1);
INSERT INTO widget_filter(widget_id, filter_id) VALUES (12,1);
INSERT INTO widget_filter(widget_id, filter_id) VALUES (8,1);
INSERT INTO widget_filter(widget_id, filter_id) VALUES (9,1);
INSERT INTO widget_filter(widget_id, filter_id) VALUES (10,1);
INSERT INTO widget_filter(widget_id, filter_id) VALUES (11,1);
INSERT INTO widget_filter(widget_id, filter_id) VALUES (17,2);
INSERT INTO widget_filter(widget_id, filter_id) VALUES (1,1);
INSERT INTO widget_filter(widget_id, filter_id) VALUES (18,4);
INSERT INTO widget_filter(widget_id, filter_id) VALUES (19,1);
INSERT INTO widget_filter(widget_id, filter_id) VALUES (21,3);

INSERT INTO public.widget_option (widget_id, option, value) VALUES (1, 'filterName', 'New_filter');
INSERT INTO public.widget_option (widget_id, option, value) VALUES (2, 'filterName', 'New_filter');
INSERT INTO public.widget_option (widget_id, option, value) VALUES (3, 'filterName', 'New_filter');
INSERT INTO public.widget_option (widget_id, option, value) VALUES (5, 'filterName', 'New filter');
INSERT INTO public.widget_option (widget_id, option, value) VALUES (6, 'filterName', 'New filter');
INSERT INTO public.widget_option (widget_id, option, value) VALUES (7, 'filterName', 'New filter');
INSERT INTO public.widget_option (widget_id, option, value) VALUES (9, 'filterName', 'New filter');
INSERT INTO public.widget_option (widget_id, option, value) VALUES (10, 'filterName', 'New filter');
INSERT INTO public.widget_option (widget_id, option, value) VALUES (11, 'launch_name_filter', 'launch name');
INSERT INTO public.widget_option (widget_id, option, value) VALUES (12, 'filterName', 'New filter');
INSERT INTO public.widget_option (widget_id, option, value) VALUES (8, 'launch_name_filter', 'launch name');
INSERT INTO public.widget_option (widget_id, option, value) VALUES (4, 'launch_name_filter', 'launch name');
INSERT INTO public.widget_option (widget_id, option, value) VALUES (17, 'login', 'default');
INSERT INTO public.widget_option (widget_id, option, value) VALUES (19, 'prefix', 'build');
INSERT INTO public.widget_option (widget_id, option, value) VALUES (21, 'launch_name_filter', 'launch name');

INSERT INTO content_field (id, field) VALUES (2, 'executions');
INSERT INTO content_field (id, field) VALUES (2, 'defects');
INSERT INTO content_field (id, field) VALUES (4, 'executions');
INSERT INTO content_field (id, field) VALUES (5, 'executions');
INSERT INTO content_field (id, field) VALUES (6, 'statistics$executions$total');
INSERT INTO content_field (id, field) VALUES (7, 'defects');
INSERT INTO content_field (id, field) VALUES (8, 'executions');
INSERT INTO content_field (id, field) VALUES (9, 'executions');
INSERT INTO content_field (id, field) VALUES (10, 'executions');
INSERT INTO content_field (id, field) VALUES (12, 'executions');
INSERT INTO content_field (id, field) VALUES (12, 'defects');
INSERT INTO content_field (id, field) VALUES (8, 'groups');
INSERT INTO content_field (id, field) VALUES (12, 'columns');
INSERT INTO content_field (id, field) VALUES (17, 'activity_type');
INSERT INTO content_field (id, field) VALUES (19, 'statistics$defects$automation_bug$AB001');
INSERT INTO content_field (id, field) VALUES (19, 'statistics$defects$product_bug$PB001');
INSERT INTO content_field (id, field) VALUES (19, 'statistics$defects$no_defect$ND001');
INSERT INTO content_field (id, field) VALUES (19, 'statistics$defects$to_investigate$TI001');
INSERT INTO content_field (id, field) VALUES (19, 'statistics$defects$system_issue$SI001');

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
  WHILE counter < 50 LOOP
    INSERT INTO launch (uuid, project_id, user_id, name, description, start_time, end_time, "number", mode, status)
    VALUES ('fc51ec81-de6f-4f3b-9630-f3f3a3490def', 1, 1, 'launch name', 'Description', now(), now() + '3 hours', 1, 'DEFAULT', 'FAILED');
    cur_launch_id = (SELECT currval(pg_get_serial_sequence('launch', 'id')));

    INSERT INTO test_item (name, type, start_time, description, last_modified, unique_id, launch_id)
    VALUES ('First suite', 'SUITE', now(), 'description', now(), 'uniqueId' || cur_launch_id, cur_launch_id);
    cur_suite_id = (SELECT currval(pg_get_serial_sequence('test_item', 'item_id')));

    UPDATE test_item SET path = cast(cast(cur_suite_id as text) as ltree) where item_id = cur_suite_id;

    INSERT INTO test_item_results (result_id, status, duration, end_time)
    VALUES (cur_suite_id, 'FAILED', 0.35, now());
    --
    INSERT INTO test_item (name, type, start_time, description, last_modified, unique_id, launch_id, parent_id)
    VALUES ('First test', 'TEST', now(), 'description', now(), 'uniqueId' || cur_launch_id || cur_launch_id , cur_launch_id, cur_suite_id);
    cur_item_id = (SELECT currval(pg_get_serial_sequence('test_item', 'item_id')));

    INSERT INTO statistics (s_field, item_id, s_counter) VALUES ('statistics$defects$automation_bug$AB001', cur_item_id, 1);

    UPDATE test_item
    SET path = cast(cur_suite_id as text) || cast(cast(cur_item_id as text) as ltree)
    where item_id = cur_item_id;

    INSERT INTO test_item_results (result_id, status, duration, end_time) VALUES (cur_item_id, 'FAILED', 0.35, now());
    --
    WHILE step_counter < 30 LOOP
      rand_status = (ARRAY ['PASSED' :: STATUS_ENUM, 'SKIPPED' :: STATUS_ENUM, 'FAILED' :: STATUS_ENUM]) [floor(random() * 3) + 1];
      --
      INSERT INTO test_item (NAME, TYPE, start_time, description, last_modified, unique_id, parent_id, launch_id)
      VALUES ('Step', 'STEP', now(), 'description', now(), 'uniqueId' || cur_launch_id || cur_launch_id || cur_launch_id , cur_item_id, cur_launch_id);
      cur_step_id = (SELECT currval(pg_get_serial_sequence('test_item', 'item_id')));

      UPDATE test_item
      SET path = cast(cur_suite_id as text) || cast(cast(cur_item_id as text) as ltree) || cast(cur_step_id as text)
      where item_id = cur_step_id;
      --

      --
      INSERT INTO test_item_results (result_id, status, duration, end_time)
      VALUES (cur_step_id, rand_status, 0.35, now());
      --
      UPDATE test_item_results SET status = rand_status WHERE result_id = cur_step_id;
      --
      IF rand_status = 'FAILED'
      THEN
        INSERT INTO issue (issue_id, issue_type, issue_description)
        VALUES (cur_step_id, floor(random() * 6 + 1), 'issue description');
         INSERT INTO issue_ticket (issue_id, ticket_id)
        VALUES (cur_step_id, floor(random() * 3 + 1));
      END IF;
      --
      step_counter = step_counter + 1;
    END LOOP;
    step_counter = 0;
    counter = counter + 1;
  END LOOP;
  RETURN NULL;
END;
$BODY$
LANGUAGE plpgsql;

INSERT INTO issue_type_project (project_id, issue_type_id) VALUES (1, 1);
INSERT INTO issue_type_project (project_id, issue_type_id) VALUES (1, 2);
INSERT INTO issue_type_project (project_id, issue_type_id) VALUES (1, 3);
INSERT INTO issue_type_project (project_id, issue_type_id) VALUES (1, 4);
INSERT INTO issue_type_project (project_id, issue_type_id) VALUES (1, 5);
INSERT INTO issue_type_project (project_id, issue_type_id) VALUES (1, 6);

SELECT initSteps();

UPDATE launch SET name = 'launch name test' WHERE id = 4;

INSERT INTO filter (id, name, project_id, target, description) VALUES (5, 'product status 1', 1, 'com.epam.ta.reportportal.entity.launch.Launch', 'PROD1');
INSERT INTO filter (id, name, project_id, target, description) VALUES (6, 'product status 2', 1, 'com.epam.ta.reportportal.entity.launch.Launch', 'PROD2');
INSERT INTO filter (id, name, project_id, target, description) VALUES (7, 'product status 3', 1, 'com.epam.ta.reportportal.entity.launch.Launch', 'PROD3');

INSERT INTO user_filter(id) VALUES (5);
INSERT INTO user_filter(id) VALUES (6);
INSERT INTO user_filter(id) VALUES (7);

INSERT INTO filter_sort(filter_id, field, direction) VALUES (5, 'statistics$defects$no_defect$ND001', 'DESC');
INSERT INTO filter_sort(filter_id, field, direction) VALUES (6, 'statistics$defects$automation_bug$AB001', 'ASC');
INSERT INTO filter_sort(filter_id, field, direction) VALUES (7, 'statistics$defects$system_issue$SI001', 'DESC');

INSERT INTO filter_condition (filter_id, condition, value, field, negative) VALUES (5, 'LOWER_THAN_OR_EQUALS', '2', 'statistics$defects$automation_bug$AB001', false);
INSERT INTO filter_condition (filter_id, condition, value, field, negative) VALUES (5, 'GREATER_THAN_OR_EQUALS', '3', 'statistics$defects$system_issue$SI001', false);
INSERT INTO filter_condition (filter_id, condition, value, field, negative) VALUES (6, 'LOWER_THAN', '3', 'statistics$defects$to_investigate$TI001', false);
INSERT INTO filter_condition (filter_id, condition, value, field, negative) VALUES (7, 'GREATER_THAN', '11', 'statistics$executions$total', false);

INSERT INTO widget_filter(widget_id, filter_id) VALUES (20,5);
INSERT INTO widget_filter(widget_id, filter_id) VALUES (20,6);
INSERT INTO widget_filter(widget_id, filter_id) VALUES (20,7);

INSERT INTO public.widget_option (widget_id, option, value) VALUES (20, 'strategy', 'launch');

INSERT INTO content_field (id, field) VALUES (20, 'statistics$defects$automation_bug$AB001');
INSERT INTO content_field (id, field) VALUES (20, 'statistics$defects$product_bug$PB001');
INSERT INTO content_field (id, field) VALUES (20, 'statistics$defects$no_defect$ND001');
INSERT INTO content_field (id, field) VALUES (20, 'statistics$defects$to_investigate$TI001');
INSERT INTO content_field (id, field) VALUES (20, 'statistics$defects$system_issue$SI001');
INSERT INTO content_field (id, field) VALUES (20, 'statistics$defects$system_issue$total');
INSERT INTO content_field (id, field) VALUES (20, 'statistics$defects$no_defect$total');
INSERT INTO content_field (id, field) VALUES (20, 'statistics$defects$product_bug$total');
INSERT INTO content_field (id, field) VALUES (20, 'statistics$defects$automation_bug$total');
INSERT INTO content_field (id, field) VALUES (20, 'statistics$defects$to_investigate$total');
INSERT INTO content_field (id, field) VALUES (20, 'statistics$executions$total');
INSERT INTO content_field (id, field) VALUES (20, 'statistics$executions$failed');
INSERT INTO content_field (id, field) VALUES (20, 'statistics$executions$skipped');
INSERT INTO content_field (id, field) VALUES (20, 'statistics$executions$passed');


INSERT INTO launch_tag(value, launch_id) VALUES('build:3.10.1', 1);
INSERT INTO launch_tag(value, launch_id) VALUES('build:3.10.2', 1);
INSERT INTO launch_tag(value, launch_id) VALUES('build:3.10.3', 1);
INSERT INTO launch_tag(value, launch_id) VALUES('build:3.10.1', 2);
INSERT INTO launch_tag(value, launch_id) VALUES('build:3.10.2', 2);
INSERT INTO launch_tag(value, launch_id) VALUES('build:3.10.3', 2);
INSERT INTO launch_tag(value, launch_id) VALUES('type:qwerty', 2);
INSERT INTO launch_tag(value, launch_id) VALUES('type:qqqqqq', 2);
INSERT INTO launch_tag(value, launch_id) VALUES('build:3.10.1', 3);
INSERT INTO launch_tag(value, launch_id) VALUES('build:3.10.2', 3);
INSERT INTO launch_tag(value, launch_id) VALUES('build:3.10.3', 3);
INSERT INTO launch_tag(value, launch_id) VALUES('type:qwerty', 3);
INSERT INTO launch_tag(value, launch_id) VALUES('type:qqqqqq', 3);
INSERT INTO launch_tag(value, launch_id) VALUES('build:3.10.1', 4);
INSERT INTO launch_tag(value, launch_id) VALUES('build:3.10.2', 4);
INSERT INTO launch_tag(value, launch_id) VALUES('build:3.10.3', 4);
INSERT INTO launch_tag(value, launch_id) VALUES('type:qwerty', 4);



INSERT INTO activity(user_id, entity, action, project_id, creation_date) VALUES (1, 'LAUNCH', 'CREATE_LAUNCH', 1, '2018-08-23 15:31:00');
INSERT INTO activity(user_id, entity, action, project_id, creation_date) VALUES (1, 'LAUNCH', 'CREATE_LAUNCH', 1, '2018-08-23 15:32:00');
INSERT INTO activity(user_id, entity, action, project_id, creation_date) VALUES (1, 'LAUNCH', 'UPDATE_LAUNCH', 1, '2018-08-23 15:33:00');
INSERT INTO activity(user_id, entity, action, project_id, creation_date) VALUES (1, 'LAUNCH', 'REMOVE_LAUNCH', 1, '2018-08-23 15:34:00');
INSERT INTO activity(user_id, entity, action, project_id, creation_date) VALUES (1, 'LAUNCH', 'CREATE_LAUNCH', 1, '2018-08-23 15:35:00');

INSERT INTO activity(user_id, entity, action, project_id, creation_date) VALUES (1, 'ITEM', 'CREATE_ITEM', 1, '2018-08-23 15:31:10');
INSERT INTO activity(user_id, entity, action, project_id, creation_date) VALUES (1, 'ITEM', 'CREATE_ITEM', 1, '2018-08-23 15:31:12');
INSERT INTO activity(user_id, entity, action, project_id, creation_date) VALUES (1, 'ITEM', 'UPDATE_ITEM', 1, '2018-08-23 15:31:22');
INSERT INTO activity(user_id, entity, action, project_id, creation_date) VALUES (1, 'ITEM', 'REMOVE_ITEM', 1, '2018-08-23 15:31:25');
INSERT INTO activity(user_id, entity, action, project_id, creation_date) VALUES (1, 'ITEM', 'CREATE_ITEM', 1, '2018-08-23 15:32:10');

INSERT INTO item_tag(value, item_id) VALUES ('qwerty', 1);
INSERT INTO item_tag(value, item_id) VALUES ('qqqqqq', 1);
INSERT INTO item_tag(value, item_id) VALUES ('qqqqqq', 1);
INSERT INTO item_tag(value, item_id) VALUES ('qqqqqq', 1);
INSERT INTO item_tag(value, item_id) VALUES ('eeeeee', 1);
.;



--INSERT INTO content_field_value (id, value) VALUES (1, 'FAILED');
--INSERT INTO content_field_value (id, value) VALUES (1, 'SKIPPED');
--INSERT INTO content_field_value (id, value) VALUES (1, 'PASSED');
--INSERT INTO content_field_value (id, value) VALUES (2, 'AB001');
--INSERT INTO content_field_value (id, value) VALUES (2, 'AB002');
--INSERT INTO content_field_value (id, value) VALUES (2, 'SI001');
--INSERT INTO content_field_value (id, value) VALUES (2, 'TI001');
--INSERT INTO content_field_value (id, value) VALUES (2, 'PB001');
--INSERT INTO content_field_value (id, value) VALUES (2, 'ND001');
--INSERT INTO content_field_value (id, value) VALUES (3, 'FAILED');
--INSERT INTO content_field_value (id, value) VALUES (3, 'SKIPPED');
--INSERT INTO content_field_value (id, value) VALUES (4, 'FAILED');
--INSERT INTO content_field_value (id, value) VALUES (4, 'SKIPPED');
--INSERT INTO content_field_value (id, value) VALUES (4, 'PASSED');
--INSERT INTO content_field_value (id, value) VALUES (5, 'FAILED');
--INSERT INTO content_field_value (id, value) VALUES (5, 'SKIPPED');
--INSERT INTO content_field_value (id, value) VALUES (5, 'PASSED');
--INSERT INTO content_field_value (id, value) VALUES (6, 'AB001');
--INSERT INTO content_field_value (id, value) VALUES (6, 'PB001');
--INSERT INTO content_field_value (id, value) VALUES (6, 'SI001');
--INSERT INTO content_field_value (id, value) VALUES (8, 'FAILED');
--INSERT INTO content_field_value (id, value) VALUES (8, 'SKIPPED');
--INSERT INTO content_field_value (id, value) VALUES (8, 'PASSED');
--INSERT INTO content_field_value (id, value) VALUES (9, 'FAILED');
--INSERT INTO content_field_value (id, value) VALUES (9, 'SKIPPED');
--INSERT INTO content_field_value (id, value) VALUES (9, 'PASSED');
--INSERT INTO content_field_value (id, value) VALUES (10, 'FAILED');
--INSERT INTO content_field_value (id, value) VALUES (10, 'SKIPPED');
--INSERT INTO content_field_value (id, value) VALUES (10, 'PASSED');
--INSERT INTO content_field_value (id, value) VALUES (11, 'FAILED');
--INSERT INTO content_field_value (id, value) VALUES (11, 'SKIPPED');
--INSERT INTO content_field_value (id, value) VALUES (11, 'PASSED');
--INSERT INTO content_field_value (id, value) VALUES (12, 'AB001');
--INSERT INTO content_field_value (id, value) VALUES (12, 'AB002');
--INSERT INTO content_field_value (id, value) VALUES (12, 'PB001');
--INSERT INTO content_field_value (id, value) VALUES (12, 'TI001');
--INSERT INTO content_field_value (id, value) VALUES (12, 'ND001');
--INSERT INTO content_field_value (id, value) VALUES (12, 'SI001');
--INSERT INTO content_field_value (id, value) VALUES (3, 'PASSED');
--INSERT INTO content_field_value (id, value) VALUES (7, 'AUTOMATION_BUG');
--INSERT INTO content_field_value (id, value) VALUES (7, 'SYSTEM_ISSUE');
--INSERT INTO content_field_value (id, value) VALUES (7, 'NO_DEFECT');
--INSERT INTO content_field_value (id, value) VALUES (7, 'TO_INVESTIGATE');
--INSERT INTO content_field_value (id, value) VALUES (7, 'PRODUCT_BUG');
--INSERT INTO content_field_value (id, value) VALUES (13, 'number');
--INSERT INTO content_field_value (id, value) VALUES (13, 'last_modified');
--INSERT INTO content_field_value (id, value) VALUES (6, 'ND001');
--INSERT INTO content_field_value (id, value) VALUES (14, 'UPDATE_LAUNCH');
--INSERT INTO content_field_value (id, value) VALUES (14, 'CREATE_ITEM');


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
