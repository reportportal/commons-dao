INSERT INTO project (name, creation_date, project_type) VALUES ('superadmin_personal', '2018-07-19 14:25:00', 'INTERNAL');

INSERT INTO users (login, password, email, role, type, default_project_id, full_name, expired)
VALUES ('superadmin', '5d39d85bddde885f6579f8121e11eba2', 'superadminemail@domain.com', 'ADMINISTRATOR', 'INTERNAL',
        (SELECT currval(pg_get_serial_sequence('project', 'id'))), 'tester', false);

INSERT INTO project_user (user_id, project_id, project_role) VALUES
  ((SELECT currval(pg_get_serial_sequence('users', 'id'))), (SELECT currval(pg_get_serial_sequence('project', 'id'))), 'PROJECT_MANAGER');

INSERT INTO public.launch (uuid, project_id, user_id, name, description, start_time, end_time, number, last_modified, mode, status) VALUES ('aa848441-72a1-4192-a828-cd20b7fcbd3c', 1, 1, 'launch name 1', null, '2018-11-22 12:46:14.198000', '2018-11-21 12:46:14.756000', 1, '2018-11-21 15:46:18.423000', 'DEFAULT', 'FAILED');
INSERT INTO public.launch (uuid, project_id, user_id, name, description, start_time, end_time, number, last_modified, mode, status) VALUES ('aa848441-72a1-4192-a828-cd20b7fcbd3c', 1, 1, 'launch name 1', null, '2018-11-23 12:46:14.198000', '2018-11-21 12:46:14.756000', 2, '2018-11-21 15:46:18.423000', 'DEFAULT', 'FAILED');
INSERT INTO public.launch (uuid, project_id, user_id, name, description, start_time, end_time, number, last_modified, mode, status) VALUES ('aa848441-72a1-4192-a828-cd20b7fcbd3c', 1, 1, 'launch name 1', null, '2018-11-24 12:46:14.198000', '2018-11-21 12:46:14.756000', 3, '2018-11-21 15:46:18.423000', 'DEFAULT', 'FAILED');
INSERT INTO public.launch (uuid, project_id, user_id, name, description, start_time, end_time, number, last_modified, mode, status) VALUES ('aa848441-72a1-4192-a828-cd20b7fcbd3c', 1, 1, 'launch name 1', null, '2018-11-25 12:46:14.198000', '2018-11-21 12:46:14.756000', 4, '2018-11-21 15:46:18.423000', 'DEFAULT', 'FAILED');
