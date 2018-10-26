INSERT INTO project(id, name, creation_date) VALUES (1, 'test_user_1_personal', '2018-07-19 13:25:00');
INSERT INTO project(id, name, creation_date) VALUES (2, 'test_user_2_personal', '2018-07-19 13:25:00');
INSERT INTO project(id, name, creation_date) VALUES (3, 'test_common_project_1', '2018-07-19 13:25:00');

INSERT INTO users(id, login, password, email, role, type, default_project_id, full_name, expired)
VALUES (1,
        'test_user_1',
        '3fde6bb0541387e4ebdadf7c2ff31123',
        'testemail@domain.com',
        'USER',
        'INTERNAL',
        1,
        'Test User',
        FALSE);

INSERT INTO project_user(user_id, project_id, project_role) VALUES (1, 1, 'PROJECT_MANAGER');
INSERT INTO project_user(user_id, project_id, project_role) VALUES (1, 3, 'MEMBER');

INSERT INTO users(id, login, password, email, role, type, default_project_id, full_name, expired)
VALUES (2,
        'test_user_2',
        '3fde6bb0541387e4ebdadf7c2ff31123',
        'testemail2@domain.com',
        'USER',
        'INTERNAL',
        2,
        'Test User',
        FALSE);

INSERT INTO project_user(user_id, project_id, project_role) VALUES (2, 2, 'PROJECT_MANAGER');
INSERT INTO project_user(user_id, project_id, project_role) VALUES (2, 3, 'MEMBER');