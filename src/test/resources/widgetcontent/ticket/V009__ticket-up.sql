INSERT INTO bug_tracking_system (url, type, bts_project, project_id) VALUES ('test.com', 'TEST TYPE', 'TEST PROJECT', 1);

INSERT INTO public.ticket (id, ticket_id, submitter_id, submit_date, bts_id, url) VALUES (1, 'EPMRPP-322', 1, '2018-09-28 12:38:24.374555', 1, 'epam.com');
INSERT INTO public.ticket (id, ticket_id, submitter_id, submit_date, bts_id, url) VALUES (2, 'EPMRPP-123', 1, '2018-09-28 12:38:24.374555', 1, 'epam.com');
INSERT INTO public.ticket (id, ticket_id, submitter_id, submit_date, bts_id, url) VALUES (3, 'QWERTY-100', 1, '2018-09-28 12:38:24.374555', 1, 'epam.com');

INSERT INTO test_item (NAME, TYPE, start_time, description, last_modified, unique_id, launch_id)
      VALUES ('Step', 'STEP', now(), 'description', now(), 'uniqueId' , 1);
INSERT INTO test_item_results (result_id, status, duration, end_time)
      VALUES (1, 'FAILED', 0.35, now());

INSERT INTO test_item (NAME, TYPE, start_time, description, last_modified, unique_id, launch_id)
      VALUES ('Step', 'STEP', now(), 'description', now(), 'uniqueId' , 1);
INSERT INTO test_item_results (result_id, status, duration, end_time)
      VALUES (2, 'FAILED', 0.35, now());

INSERT INTO test_item (NAME, TYPE, start_time, description, last_modified, unique_id, launch_id)
      VALUES ('Step', 'STEP', now(), 'description', now(), 'uniqueId' , 1);
INSERT INTO test_item_results (result_id, status, duration, end_time)
      VALUES (3, 'FAILED', 0.35, now());

INSERT INTO test_item (NAME, TYPE, start_time, description, last_modified, unique_id, launch_id)
      VALUES ('Step', 'STEP', now(), 'description', now(), 'uniqueId' , 1);
INSERT INTO test_item_results (result_id, status, duration, end_time)
      VALUES (4, 'FAILED', 0.35, now());

INSERT INTO test_item (NAME, TYPE, start_time, description, last_modified, unique_id, launch_id)
      VALUES ('Step', 'STEP', now(), 'description', now(), 'uniqueId' , 2);
INSERT INTO test_item_results (result_id, status, duration, end_time)
      VALUES (5, 'FAILED', 0.35, now());

INSERT INTO test_item (NAME, TYPE, start_time, description, last_modified, unique_id, launch_id)
      VALUES ('Step', 'STEP', now(), 'description', now(), 'uniqueId' , 2);
INSERT INTO test_item_results (result_id, status, duration, end_time)
      VALUES (6, 'FAILED', 0.35, now());


INSERT INTO issue (issue_id, issue_type, issue_description) VALUES (1, floor(random() * 5 + 1), 'issue description');
INSERT INTO issue_ticket (issue_id, ticket_id) VALUES (1, 1);
INSERT INTO issue (issue_id, issue_type, issue_description) VALUES (2, floor(random() * 5 + 1), 'issue description');
INSERT INTO issue_ticket (issue_id, ticket_id) VALUES (2, 3);

INSERT INTO issue (issue_id, issue_type, issue_description) VALUES (3, floor(random() * 5 + 1), 'issue description');
INSERT INTO issue_ticket (issue_id, ticket_id) VALUES (3, 2);

INSERT INTO issue (issue_id, issue_type, issue_description) VALUES (4, floor(random() * 5 + 1), 'issue description');
INSERT INTO issue_ticket (issue_id, ticket_id) VALUES (4, 1);
INSERT INTO issue (issue_id, issue_type, issue_description) VALUES (5, floor(random() * 5 + 1), 'issue description');
INSERT INTO issue_ticket (issue_id, ticket_id) VALUES (5, 2);
INSERT INTO issue (issue_id, issue_type, issue_description) VALUES (6, floor(random() * 5 + 1), 'issue description');
INSERT INTO issue_ticket (issue_id, ticket_id) VALUES (6, 3);

ALTER SEQUENCE statistics_s_id_seq
  RESTART WITH 1;
DELETE
FROM statistics;
ALTER SEQUENCE statistics_field_sf_id_seq
  RESTART WITH 1;
DELETE
FROM statistics_field;