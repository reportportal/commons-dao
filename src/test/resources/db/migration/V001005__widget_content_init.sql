CREATE OR REPLACE FUNCTION widget_content_init()
  RETURNS VOID AS
$$
DECLARE
          launch1 BIGINT;
  DECLARE launch2 BIGINT;
  DECLARE launch3 BIGINT;
  DECLARE launch4 BIGINT;
  DECLARE itemid  BIGINT;
BEGIN

  ALTER SEQUENCE launch_id_seq
    RESTART WITH 1;
  ALTER SEQUENCE shareable_entity_id_seq
    RESTART WITH 1;
  ALTER SEQUENCE item_attribute_id_seq
    RESTART WITH 1;
  ALTER SEQUENCE test_item_item_id_seq
    RESTART WITH 1;
  ALTER SEQUENCE ticket_id_seq
    RESTART WITH 1;
  ALTER SEQUENCE activity_id_seq
    RESTART WITH 1;

  INSERT INTO public.shareable_entity (id, shared, owner, project_id) VALUES (1, FALSE, 'superadmin', 1);
  INSERT INTO public.filter (id, name, target, description) VALUES (1, 'filter name', 'Launch', 'filter for product status widget');

  INSERT INTO public.launch (uuid, project_id, owner, name, description, start_time, end_time, number, last_modified, mode, status)
  VALUES ('aa848441-72a1-4192-a828-cd20b7fcbd31',
          1,
          'superadmin',
          'launch name 1',
          NULL,
          '2018-11-22 12:46:14.198000',
          '2018-11-21 12:46:14.756000',
          1,
          '2018-11-21 15:46:18.423000',
          'DEFAULT',
          'FAILED');
  launch1 = (SELECT currval(pg_get_serial_sequence('launch', 'id')));

  INSERT INTO public.launch (uuid, project_id, owner, name, description, start_time, end_time, number, last_modified, mode, status)
  VALUES ('aa848441-72a1-4192-a828-cd20b7fcbd32',
          1,
          'superadmin',
          'launch name 1',
          NULL,
          '2018-11-23 12:46:14.198000',
          '2018-11-21 12:46:14.756000',
          2,
          '2018-11-21 15:46:18.423000',
          'DEFAULT',
          'FAILED');
  launch2 = (SELECT currval(pg_get_serial_sequence('launch', 'id')));

  INSERT INTO public.launch (uuid, project_id, owner, name, description, start_time, end_time, number, last_modified, mode, status)
  VALUES ('aa848441-72a1-4192-a828-cd20b7fcbd33',
          1,
          'superadmin',
          'launch name 1',
          NULL,
          '2018-11-24 12:46:14.198000',
          '2018-11-21 12:46:14.756000',
          3,
          '2018-11-21 15:46:18.423000',
          'DEFAULT',
          'FAILED');
  launch3 = (SELECT currval(pg_get_serial_sequence('launch', 'id')));

  INSERT INTO public.launch (uuid, project_id, owner, name, description, start_time, end_time, number, last_modified, mode, status)
  VALUES ('aa848441-72a1-4192-a828-cd20b7fcbd34',
          1,
          'superadmin',
          'launch name 1',
          NULL,
          '2018-11-25 12:46:14.198000',
          '2018-11-21 12:46:14.756000',
          4,
          '2018-11-21 15:46:18.423000',
          'DEFAULT',
          'FAILED');
  launch4 = (SELECT currval(pg_get_serial_sequence('launch', 'id')));

  INSERT INTO item_attribute (key, value, item_id, launch_id, system) VALUES ('build', '1.12.3', NULL, launch1, FALSE);
  INSERT INTO item_attribute (key, value, item_id, launch_id, system) VALUES ('level', '1', NULL, launch1, FALSE);
  INSERT INTO item_attribute (key, value, item_id, launch_id, system) VALUES ('build', '3', NULL, launch1, FALSE);
  INSERT INTO item_attribute (key, value, item_id, launch_id, system) VALUES ('build', '1.9.1', NULL, launch2, FALSE);
  INSERT INTO item_attribute (key, value, item_id, launch_id, system) VALUES ('level', '1', NULL, launch2, FALSE);
  INSERT INTO item_attribute (key, value, item_id, launch_id, system) VALUES ('level', '2', NULL, launch2, FALSE);
  INSERT INTO item_attribute (key, value, item_id, launch_id, system) VALUES ('build', 'passed', NULL, launch3, FALSE);
  INSERT INTO item_attribute (key, value, item_id, launch_id, system) VALUES ('build', '1.2.5', NULL, launch3, FALSE);
  INSERT INTO item_attribute (key, value, item_id, launch_id, system) VALUES ('level', '2', NULL, launch3, FALSE);
  INSERT INTO item_attribute (key, value, item_id, launch_id, system) VALUES ('level', '3', NULL, launch3, FALSE);
  INSERT INTO item_attribute (key, value, item_id, launch_id, system) VALUES ('build', '1', NULL, launch3, FALSE);
  INSERT INTO item_attribute (key, value, item_id, launch_id, system) VALUES ('build', '1.1.7.15.3', NULL, launch1, FALSE);
  INSERT INTO item_attribute (key, value, item_id, launch_id, system) VALUES ('build', '1.2.3', NULL, launch2, FALSE);
  INSERT INTO item_attribute (key, value, item_id, launch_id, system) VALUES ('build', '2', NULL, launch2, FALSE);
  INSERT INTO item_attribute (key, value, item_id, launch_id, system) VALUES ('build', '3.2.4.3', NULL, launch3, FALSE);
  INSERT INTO item_attribute (key, value, item_id, launch_id, system) VALUES ('build', 'skipped', NULL, launch1, FALSE);
  INSERT INTO item_attribute (key, value, item_id, launch_id, system) VALUES ('build', '1.2.3', NULL, launch4, FALSE);
  INSERT INTO item_attribute (key, value, item_id, launch_id, system) VALUES ('build', 'failed', NULL, launch2, FALSE);
  INSERT INTO item_attribute (key, value, item_id, launch_id, system) VALUES ('build', '1.3.2', NULL, launch2, FALSE);
  INSERT INTO item_attribute (key, value, item_id, launch_id, system) VALUES ('build', '1.9.1', NULL, launch3, FALSE);
  INSERT INTO item_attribute (key, value, item_id, launch_id, system) VALUES ('build', '3', NULL, launch4, FALSE);


  INSERT INTO public.ticket (id, ticket_id, submitter, submit_date, bts_url, bts_project, url)
  VALUES (1, 'EPMRPP-322', 'superadmin', '2018-09-28 12:38:24.374555', 'jira.com', 'project', 'epam.com');
  INSERT INTO public.ticket (id, ticket_id, submitter, submit_date, bts_url, bts_project, url)
  VALUES (2, 'EPMRPP-123', 'superadmin', '2018-09-28 12:38:24.374555', 'jira.com', 'project', 'epam.com');
  INSERT INTO public.ticket (id, ticket_id, submitter, submit_date, bts_url, bts_project, url)
  VALUES (3, 'QWERTY-100', 'superadmin', '2018-09-28 12:38:24.374555', 'jira.com', 'project', 'epam.com');

  INSERT INTO public.pattern_template (id, name, value, type, enabled, project_id)
  VALUES (1, 'FIRST PATTERN', 'aaaa', 'STRING', TRUE, 1);
  INSERT INTO public.pattern_template (id, name, value, type, enabled, project_id)
  VALUES (2, 'SECOND PATTERN', 'bbbb', 'STRING', TRUE, 1);

  INSERT INTO test_item (name, uuid, type, start_time, description, last_modified, unique_id, launch_id)
  VALUES ('Step', 'uuid1', 'STEP', now(), 'description', now(), 'uniqueId', launch1);
  itemid = (SELECT (currval(pg_get_serial_sequence('test_item', 'item_id'))));
  INSERT INTO test_item_results (result_id, status, duration, end_time) VALUES (itemid, 'FAILED', 0.35, now());
  INSERT INTO public.pattern_template_test_item (pattern_id, item_id) VALUES (1, itemid);
  INSERT INTO public.pattern_template_test_item (pattern_id, item_id) VALUES (2, itemid);

  INSERT INTO test_item (name, uuid, type, start_time, description, last_modified, unique_id, launch_id)
  VALUES ('Step', 'uuid2', 'STEP', now(), 'description', now(), 'uniqueId', launch1);
  itemid = (SELECT (currval(pg_get_serial_sequence('test_item', 'item_id'))));
  INSERT INTO test_item_results (result_id, status, duration, end_time) VALUES (itemid, 'FAILED', 0.35, now());
  INSERT INTO issue (issue_id, issue_type, issue_description) VALUES (itemid, floor(random() * 5 + 1), 'issue description');
  INSERT INTO issue_ticket (issue_id, ticket_id) VALUES (itemid, 3);

  INSERT INTO test_item (name, uuid, type, start_time, description, last_modified, unique_id, launch_id)
  VALUES ('Step', 'uuid3', 'STEP', now(), 'description', now(), 'uniqueId', launch1);
  itemid = (SELECT (currval(pg_get_serial_sequence('test_item', 'item_id'))));
  INSERT INTO test_item_results (result_id, status, duration, end_time) VALUES (itemid, 'FAILED', 0.35, now());
  INSERT INTO issue (issue_id, issue_type, issue_description) VALUES (itemid, floor(random() * 5 + 1), 'issue description');
  INSERT INTO issue_ticket (issue_id, ticket_id) VALUES (itemid, 2);

  INSERT INTO test_item (name, uuid, type, start_time, description, last_modified, unique_id, launch_id)
  VALUES ('Step', 'uuid4', 'STEP', now(), 'description', now(), 'uniqueId', launch1);
  itemid = (SELECT (currval(pg_get_serial_sequence('test_item', 'item_id'))));
  INSERT INTO test_item_results (result_id, status, duration, end_time) VALUES (itemid, 'FAILED', 0.35, now());
  INSERT INTO issue (issue_id, issue_type, issue_description) VALUES (itemid, floor(random() * 5 + 1), 'issue description');
  INSERT INTO issue_ticket (issue_id, ticket_id) VALUES (itemid, 1);

  INSERT INTO test_item (name, uuid, type, start_time, description, last_modified, unique_id, launch_id)
  VALUES ('Step', 'uuid5', 'STEP', now(), 'description', now(), 'uniqueId', launch4);
  itemid = (SELECT (currval(pg_get_serial_sequence('test_item', 'item_id'))));
  INSERT INTO test_item_results (result_id, status, duration, end_time) VALUES (itemid, 'FAILED', 0.35, now());
  INSERT INTO issue (issue_id, issue_type, issue_description) VALUES (itemid, floor(random() * 5 + 1), 'issue description');
  INSERT INTO issue_ticket (issue_id, ticket_id) VALUES (itemid, 2);
  INSERT INTO public.pattern_template_test_item (pattern_id, item_id) VALUES (1, itemid);
  INSERT INTO public.pattern_template_test_item (pattern_id, item_id) VALUES (2, itemid);

  INSERT INTO test_item (name, uuid, type, start_time, description, last_modified, unique_id, launch_id)
  VALUES ('Step', 'uuid6', 'STEP', now(), 'description', now(), 'uniqueId', launch4);
  itemid = (SELECT (currval(pg_get_serial_sequence('test_item', 'item_id'))));
  INSERT INTO test_item_results (result_id, status, duration, end_time) VALUES (itemid, 'FAILED', 0.35, now());
  INSERT INTO issue (issue_id, issue_type, issue_description) VALUES (itemid, floor(random() * 5 + 1), 'issue description');
  INSERT INTO issue_ticket (issue_id, ticket_id) VALUES (itemid, 3);
  INSERT INTO public.pattern_template_test_item (pattern_id, item_id) VALUES (2, itemid);

  INSERT INTO test_item (name, uuid, type, start_time, description, last_modified, unique_id, launch_id)
  VALUES ('Step', 'uuid7', 'STEP', now(), 'description', now(), 'uniqueId', launch4);
  itemid = (SELECT (currval(pg_get_serial_sequence('test_item', 'item_id'))));
  INSERT INTO test_item_results (result_id, status, duration, end_time) VALUES (itemid, 'FAILED', 0.35, now());
  INSERT INTO issue (issue_id, issue_type, issue_description) VALUES (itemid, floor(random() * 5 + 1), 'issue description');
  INSERT INTO issue_ticket (issue_id, ticket_id) VALUES (itemid, 1);

  ALTER SEQUENCE statistics_s_id_seq
    RESTART WITH 1;
  DELETE FROM statistics cascade;
  ALTER SEQUENCE statistics_field_sf_id_seq
    RESTART WITH 1;
  DELETE FROM statistics_field cascade;

  INSERT INTO statistics_field (sf_id, name) VALUES (1, 'statistics$executions$total');
  INSERT INTO statistics_field (sf_id, name) VALUES (2, 'statistics$executions$passed');
  INSERT INTO statistics_field (sf_id, name) VALUES (3, 'statistics$executions$skipped');
  INSERT INTO statistics_field (sf_id, name) VALUES (4, 'statistics$executions$failed');
  INSERT INTO statistics_field (sf_id, name) VALUES (5, 'statistics$defects$to_investigate$total');
  INSERT INTO statistics_field (sf_id, name) VALUES (6, 'statistics$defects$system_issue$total');
  INSERT INTO statistics_field (sf_id, name) VALUES (7, 'statistics$defects$automation_bug$total');
  INSERT INTO statistics_field (sf_id, name) VALUES (8, 'statistics$defects$product_bug$total');
  INSERT INTO statistics_field (sf_id, name) VALUES (9, 'statistics$defects$no_defect$total');
  INSERT INTO statistics_field (sf_id, name) VALUES (10, 'statistics$defects$to_investigate$ti001');
  INSERT INTO statistics_field (sf_id, name) VALUES (11, 'statistics$defects$system_issue$si001');
  INSERT INTO statistics_field (sf_id, name) VALUES (12, 'statistics$defects$automation_bug$ab001');
  INSERT INTO statistics_field (sf_id, name) VALUES (13, 'statistics$defects$product_bug$pb001');
  INSERT INTO statistics_field (sf_id, name) VALUES (14, 'statistics$defects$no_defect$nd001');

  -------------------------------------------------------------------------------------------------------------------<
  -------------------------------------------------------------------------------------------------------------------<
  -- LAUNCHES STATISTICS
  INSERT INTO statistics (launch_id, statistics_field_id, s_counter) VALUES (launch1, 1, 10);
  INSERT INTO statistics (launch_id, statistics_field_id, s_counter) VALUES (launch1, 2, 3);
  INSERT INTO statistics (launch_id, statistics_field_id, s_counter) VALUES (launch1, 3, 4);
  INSERT INTO statistics (launch_id, statistics_field_id, s_counter) VALUES (launch1, 4, 3);

  INSERT INTO statistics (launch_id, statistics_field_id, s_counter) VALUES (launch1, 5, 2);
  INSERT INTO statistics (launch_id, statistics_field_id, s_counter) VALUES (launch1, 6, 8);
  INSERT INTO statistics (launch_id, statistics_field_id, s_counter) VALUES (launch1, 7, 7);
  INSERT INTO statistics (launch_id, statistics_field_id, s_counter) VALUES (launch1, 8, 13);
  INSERT INTO statistics (launch_id, statistics_field_id, s_counter) VALUES (launch1, 9, 2);

  INSERT INTO statistics (launch_id, statistics_field_id, s_counter) VALUES (launch1, 10, 2);
  INSERT INTO statistics (launch_id, statistics_field_id, s_counter) VALUES (launch1, 11, 8);
  INSERT INTO statistics (launch_id, statistics_field_id, s_counter) VALUES (launch1, 12, 7);
  INSERT INTO statistics (launch_id, statistics_field_id, s_counter) VALUES (launch1, 13, 13);
  INSERT INTO statistics (launch_id, statistics_field_id, s_counter) VALUES (launch1, 14, 2);

  --

  INSERT INTO statistics (launch_id, statistics_field_id, s_counter) VALUES (launch2, 1, 11);
  INSERT INTO statistics (launch_id, statistics_field_id, s_counter) VALUES (launch2, 2, 2);
  INSERT INTO statistics (launch_id, statistics_field_id, s_counter) VALUES (launch2, 3, 3);
  INSERT INTO statistics (launch_id, statistics_field_id, s_counter) VALUES (launch2, 4, 6);

  INSERT INTO statistics (launch_id, statistics_field_id, s_counter) VALUES (launch2, 5, 3);
  INSERT INTO statistics (launch_id, statistics_field_id, s_counter) VALUES (launch2, 6, 3);
  INSERT INTO statistics (launch_id, statistics_field_id, s_counter) VALUES (launch2, 7, 1);
  INSERT INTO statistics (launch_id, statistics_field_id, s_counter) VALUES (launch2, 8, 1);
  INSERT INTO statistics (launch_id, statistics_field_id, s_counter) VALUES (launch2, 9, 2);

  INSERT INTO statistics (launch_id, statistics_field_id, s_counter) VALUES (launch2, 10, 3);
  INSERT INTO statistics (launch_id, statistics_field_id, s_counter) VALUES (launch2, 11, 3);
  INSERT INTO statistics (launch_id, statistics_field_id, s_counter) VALUES (launch2, 12, 1);
  INSERT INTO statistics (launch_id, statistics_field_id, s_counter) VALUES (launch2, 13, 1);
  INSERT INTO statistics (launch_id, statistics_field_id, s_counter) VALUES (launch2, 14, 2);

  --

  INSERT INTO statistics (launch_id, statistics_field_id, s_counter) VALUES (launch3, 1, 15);
  INSERT INTO statistics (launch_id, statistics_field_id, s_counter) VALUES (launch3, 2, 5);
  INSERT INTO statistics (launch_id, statistics_field_id, s_counter) VALUES (launch3, 3, 5);
  INSERT INTO statistics (launch_id, statistics_field_id, s_counter) VALUES (launch3, 4, 5);

  INSERT INTO statistics (launch_id, statistics_field_id, s_counter) VALUES (launch3, 5, 1);
  INSERT INTO statistics (launch_id, statistics_field_id, s_counter) VALUES (launch3, 6, 1);
  INSERT INTO statistics (launch_id, statistics_field_id, s_counter) VALUES (launch3, 7, 1);
  INSERT INTO statistics (launch_id, statistics_field_id, s_counter) VALUES (launch3, 8, 1);
  INSERT INTO statistics (launch_id, statistics_field_id, s_counter) VALUES (launch3, 9, 1);

  INSERT INTO statistics (launch_id, statistics_field_id, s_counter) VALUES (launch3, 10, 1);
  INSERT INTO statistics (launch_id, statistics_field_id, s_counter) VALUES (launch3, 11, 1);
  INSERT INTO statistics (launch_id, statistics_field_id, s_counter) VALUES (launch3, 12, 1);
  INSERT INTO statistics (launch_id, statistics_field_id, s_counter) VALUES (launch3, 13, 1);
  INSERT INTO statistics (launch_id, statistics_field_id, s_counter) VALUES (launch3, 14, 1);

  --

  INSERT INTO statistics (launch_id, statistics_field_id, s_counter) VALUES (launch4, 1, 12);
  INSERT INTO statistics (launch_id, statistics_field_id, s_counter) VALUES (launch4, 2, 3);
  INSERT INTO statistics (launch_id, statistics_field_id, s_counter) VALUES (launch4, 3, 1);
  INSERT INTO statistics (launch_id, statistics_field_id, s_counter) VALUES (launch4, 4, 8);

  INSERT INTO statistics (launch_id, statistics_field_id, s_counter) VALUES (launch4, 5, 3);
  INSERT INTO statistics (launch_id, statistics_field_id, s_counter) VALUES (launch4, 6, 4);
  INSERT INTO statistics (launch_id, statistics_field_id, s_counter) VALUES (launch4, 7, 2);
  INSERT INTO statistics (launch_id, statistics_field_id, s_counter) VALUES (launch4, 8, 2);
  INSERT INTO statistics (launch_id, statistics_field_id, s_counter) VALUES (launch4, 9, 6);

  INSERT INTO statistics (launch_id, statistics_field_id, s_counter) VALUES (launch4, 10, 3);
  INSERT INTO statistics (launch_id, statistics_field_id, s_counter) VALUES (launch4, 11, 4);
  INSERT INTO statistics (launch_id, statistics_field_id, s_counter) VALUES (launch4, 12, 2);
  INSERT INTO statistics (launch_id, statistics_field_id, s_counter) VALUES (launch4, 13, 2);
  INSERT INTO statistics (launch_id, statistics_field_id, s_counter) VALUES (launch4, 14, 6);

  INSERT INTO activity (user_id, project_id, entity, action, details, creation_date, object_id)
  VALUES (1, 1, 'LAUNCH', 'createLaunch', NULL, now(), NULL);
  INSERT INTO activity (user_id, project_id, entity, action, details, creation_date, object_id)
  VALUES (1, 1, 'LAUNCH', 'createLaunch', NULL, now(), NULL);
  INSERT INTO activity (user_id, project_id, entity, action, details, creation_date, object_id)
  VALUES (1, 1, 'LAUNCH', 'createLaunch', NULL, now(), NULL);
  INSERT INTO activity (user_id, project_id, entity, action, details, creation_date, object_id)
  VALUES (1, 1, 'LAUNCH', 'createLaunch', NULL, now(), NULL);

END;
$$
LANGUAGE plpgsql;