INSERT INTO project(name, creation_date)
VALUES ('test_project_1', '2018-07-19 13:25:00');

INSERT INTO users(login, password, email, role, type, default_project_id, full_name, expired)
VALUES ('test_user_1',
        '3fde6bb0541387e4ebdadf7c2ff31123',
        'testemail@domain.com',
        'USER',
        'INTERNAL',
        (SELECT currval(pg_get_serial_sequence('project', 'id'))),
        'Test User',
        FALSE);

INSERT INTO project_user(user_id, project_id, project_role)
VALUES ((SELECT currval(pg_get_serial_sequence('users', 'id'))), (SELECT currval(pg_get_serial_sequence('project', 'id'))), 'MEMBER');

INSERT INTO users(login, password, email, role, type, default_project_id, full_name, expired)
VALUES ('test_user_2',
        '3fde6bb0541387e4ebdadf7c2ff31123',
        'testemail@domain.com',
        'USER',
        'INTERNAL',
        (SELECT currval(pg_get_serial_sequence('project', 'id'))),
        'Test User',
        FALSE);

INSERT INTO project_user(user_id, project_id, project_role)
VALUES ((SELECT currval(pg_get_serial_sequence('users', 'id'))),
        (SELECT currval(pg_get_serial_sequence('project', 'id'))),
        'PROJECT_MANAGER');