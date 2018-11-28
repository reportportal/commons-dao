INSERT INTO project (name, creation_date, project_type) VALUES ('superadmin_personal', '2018-07-19 14:25:00', 'INTERNAL');

INSERT INTO users (login, password, email, role, type, default_project_id, full_name, expired)
VALUES ('superadmin', '5d39d85bddde885f6579f8121e11eba2', 'superadminemail@domain.com', 'ADMINISTRATOR', 'INTERNAL',
        (SELECT currval(pg_get_serial_sequence('project', 'id'))), 'tester', false);

INSERT INTO project_user (user_id, project_id, project_role) VALUES
  ((SELECT currval(pg_get_serial_sequence('users', 'id'))), (SELECT currval(pg_get_serial_sequence('project', 'id'))), 'PROJECT_MANAGER');

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

INSERT INTO public.launch (uuid, project_id, user_id, name, description, start_time, end_time, number, last_modified, mode, status) VALUES ('aa848441-72a1-4192-a828-cd20b7fcbd3c', 1, 1, 'launch name 1', null, '2018-11-22 12:46:14.198000', '2018-11-21 12:46:14.756000', 1, '2018-11-21 15:46:18.423000', 'DEFAULT', 'FAILED');
INSERT INTO public.launch (uuid, project_id, user_id, name, description, start_time, end_time, number, last_modified, mode, status) VALUES ('aa848441-72a1-4192-a828-cd20b7fcbd3c', 1, 1, 'launch name 1', null, '2018-11-23 12:46:14.198000', '2018-11-21 12:46:14.756000', 2, '2018-11-21 15:46:18.423000', 'DEFAULT', 'FAILED');
INSERT INTO public.launch (uuid, project_id, user_id, name, description, start_time, end_time, number, last_modified, mode, status) VALUES ('aa848441-72a1-4192-a828-cd20b7fcbd3c', 1, 1, 'launch name 1', null, '2018-11-24 12:46:14.198000', '2018-11-21 12:46:14.756000', 3, '2018-11-21 15:46:18.423000', 'DEFAULT', 'FAILED');
INSERT INTO public.launch (uuid, project_id, user_id, name, description, start_time, end_time, number, last_modified, mode, status) VALUES ('aa848441-72a1-4192-a828-cd20b7fcbd3c', 1, 1, 'launch name 1', null, '2018-11-25 12:46:14.198000', '2018-11-21 12:46:14.756000', 4, '2018-11-21 15:46:18.423000', 'DEFAULT', 'FAILED');
