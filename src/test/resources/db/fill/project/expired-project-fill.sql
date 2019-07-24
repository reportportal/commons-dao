INSERT INTO project (id, name, project_type, creation_date)
VALUES (100, 'toDelete', 'UPSA', now() - make_interval(days := 14));

INSERT INTO launch (uuid, project_id, owner, name, start_time, number, last_modified, mode, status, has_retries)
VALUES ('uuid1', 100, 'default', 'testLaunch1', now() - make_interval(days := 13), 1, now(), 'DEFAULT', 'FAILED', FALSE),
       ('uuid2', 100, 'default', 'testLaunch2', now() - make_interval(days := 12), 2, now(), 'DEFAULT', 'FAILED', FALSE),
       ('uuid3', 100, 'default', 'testLaunch3', now() - make_interval(days := 11), 3, now(), 'DEFAULT', 'FAILED', FALSE);