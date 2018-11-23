INSERT INTO project (name, creation_date, project_type) VALUES ('superadmin_personal', '2018-07-19 14:25:00', 'INTERNAL');

INSERT INTO users (login, password, email, role, type, default_project_id, full_name, expired)
VALUES ('superadmin', '5d39d85bddde885f6579f8121e11eba2', 'superadminemail@domain.com', 'ADMINISTRATOR', 'INTERNAL',
        (SELECT currval(pg_get_serial_sequence('project', 'id'))), 'tester', false);

INSERT INTO project_user (user_id, project_id, project_role) VALUES
  ((SELECT currval(pg_get_serial_sequence('users', 'id'))), (SELECT currval(pg_get_serial_sequence('project', 'id'))), 'PROJECT_MANAGER');

INSERT INTO widget (id, name, description, widget_type, items_count, project_id) VALUES (2, 'start', null, 'launch_statistics', 1000, 1);
INSERT INTO widget (id, name, description, widget_type, items_count, project_id) VALUES (4, 'start1', null, 'passing_rate_per_launch', 1000, 1);
INSERT INTO widget (id, name, description, widget_type, items_count, project_id) VALUES (5, 'start2', null, 'passing_rate_summary', 1000, 1);
INSERT INTO widget (id, name, description, widget_type, items_count, project_id) VALUES (6, 'start3', null, 'cases_trend', 1000, 1);
INSERT INTO widget (id, name, description, widget_type, items_count, project_id) VALUES (7, 'my widget', null, 'bug_trend', 1000, 1);
INSERT INTO widget (id, name, description, widget_type, items_count, project_id) VALUES (3, 'start4', null, 'investigated_trend', 1000, 1);
INSERT INTO widget (id, name, description, widget_type, items_count, project_id) VALUES (12, 'table', null, 'launches_table', 1000, 1);
INSERT INTO widget (id, name, description, widget_type, items_count, project_id) VALUES (8, 'comparison', null, 'launches_comparison_chart', 1000, 1);
INSERT INTO widget (id, name, description, widget_type, items_count, project_id) VALUES (9, 'duration', null, 'launches_duration_chart', 1000, 1);
INSERT INTO widget (id, name, description, widget_type, items_count, project_id) VALUES (10, 'not passed', null, 'not_passed', 1000, 1);
INSERT INTO widget (id, name, description, widget_type, items_count, project_id) VALUES (11, 'not passed1', null, 'most_failed_test_cases', 1000, 1);
INSERT INTO widget (id, name, description, widget_type, items_count, project_id) VALUES (17, 'table1', null, 'activity_stream', 1000, 1);
INSERT INTO widget (id, name, description, widget_type, items_count, project_id) VALUES (1, 'start5', null, 'overall_statistics', 1000, 1);
INSERT INTO widget (id, name, description, widget_type, items_count, project_id) VALUES (18, 'unique', null, 'unique_bug_table', 1000, 1);
INSERT INTO widget (id, name, description, widget_type, items_count, project_id) VALUES (19, 'cumulative test', null, 'cumulative', 2, 1);
INSERT INTO widget (id, name, description, widget_type, items_count, project_id) VALUES (20, 'product status widget', 'description of widget', 'product_status', 2, 1);
INSERT INTO widget (id, name, description, widget_type, items_count, project_id) VALUES (21, 'most time consuming widget', 'description of widget', 'most_time_consuming', 20, 1);

INSERT INTO public.launch (uuid, project_id, user_id, name, description, start_time, end_time, number, last_modified, mode, status) VALUES ('aa848441-72a1-4192-a828-cd20b7fcbd3c', 1, 1, 'launch name 1', null, '2018-11-21 12:46:14.198000', '2018-11-21 12:46:14.756000', 1, '2018-11-21 15:46:18.423000', 'DEFAULT', 'FAILED');
INSERT INTO public.launch (uuid, project_id, user_id, name, description, start_time, end_time, number, last_modified, mode, status) VALUES ('aa848441-72a1-4192-a828-cd20b7fcbd3c', 1, 1, 'launch name 1', null, '2018-11-21 12:46:14.198000', '2018-11-21 12:46:14.756000', 2, '2018-11-21 15:46:18.423000', 'DEFAULT', 'FAILED');
INSERT INTO public.launch (uuid, project_id, user_id, name, description, start_time, end_time, number, last_modified, mode, status) VALUES ('aa848441-72a1-4192-a828-cd20b7fcbd3c', 1, 1, 'launch name 1', null, '2018-11-21 12:46:14.198000', '2018-11-21 12:46:14.756000', 3, '2018-11-21 15:46:18.423000', 'DEFAULT', 'FAILED');
INSERT INTO public.launch (uuid, project_id, user_id, name, description, start_time, end_time, number, last_modified, mode, status) VALUES ('aa848441-72a1-4192-a828-cd20b7fcbd3c', 1, 1, 'launch name 1', null, '2018-11-25 12:46:14.198000', '2018-11-21 12:46:14.756000', 4, '2018-11-21 15:46:18.423000', 'DEFAULT', 'FAILED');
