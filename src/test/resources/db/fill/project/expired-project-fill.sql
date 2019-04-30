insert into project(id, name, project_type, creation_date)
values (100, 'toDelete', 'UPSA', now() - make_interval(days := 14));

insert into launch(uuid, project_id, user_id, name, start_time, number, last_modified, mode, status, has_retries)
values ('uuid1', 100, 1, 'testLaunch1', now() - make_interval(days := 13), 1, now(), 'DEFAULT', 'FAILED', false),
       ('uuid2', 100, 1, 'testLaunch2', now() - make_interval(days := 12), 1, now(), 'DEFAULT', 'FAILED', false),
       ('uuid3', 100, 1, 'testLaunch3', now() - make_interval(days := 11), 1, now(), 'DEFAULT', 'FAILED', false);