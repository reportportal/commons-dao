INSERT INTO project (name, additional_info, creation_date) VALUES ('default_bid', 'additional info', '2018-07-19 13:25:00');

INSERT INTO user_creation_bid (uuid, email, role, default_project_id, last_modified)
VALUES ('adhakdjadhk123akjdakdj', 'test@email.com', 'USER_BID_ROLE',
        (SELECT currval(pg_get_serial_sequence('project', 'id'))),'2018-03-05 15:30:22');