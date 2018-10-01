INSERT INTO project (name, additional_info, creation_date) VALUES ('default_bid', 'additional info', '2018-07-19 13:25:00');
INSERT INTO project_configuration (id, project_type, interrupt_timeout, keep_logs_interval, keep_screenshots_interval, created_on)
VALUES ((SELECT currval(pg_get_serial_sequence('project', 'id'))), 'PERSONAL', '1 day', '1 month', '2 weeks', '2018-07-19 13:25:00');

INSERT INTO user_creation_bid (email, role, default_project_id, last_modified)
VALUES ('test@email.com', 'USER_BID_ROLE',
        (SELECT currval(pg_get_serial_sequence('project', 'id'))),'2018-03-05 15:30:22');