INSERT INTO launch (id, uuid, project_id, user_id, name, start_time, number, last_modified, mode, status) VALUES
(1, 'bd788410-c7c6-4a56-a314-4fee8f913e38', 1, 1, 'test', now(), 1, now(), 'DEFAULT', 'IN_PROGRESS'),
(2, '137b1a98-06ae-499f-a420-b9579ef00337', 1, 1, 'test1', now(), 1, now(), 'DEFAULT', 'IN_PROGRESS'),
(3, 'dab36533-9961-4e56-b833-fe5e1259bfc0', 1, 1, 'test2', now(), 1, now(), 'DEFAULT', 'IN_PROGRESS'),
(4, 'fc5573cd-179b-467d-afbf-5491fb829631', 1, 1, 'test3', now(), 1, now(), 'DEFAULT', 'IN_PROGRESS'),
(5, 'c3ebf147-7f58-480f-9fe3-23d8dd1d6d49', 1, 1, 'test4', now(), 1, now(), 'DEFAULT', 'IN_PROGRESS');

INSERT INTO item_attribute (id, "key", "value", item_id, launch_id) VALUES
(1, 'os', 'win', NULL, 1),
(2, 'os', 'linux', null, 2),
(3, 'browser', 'chrome', NULL, 1),
(4, 'browser', 'firefox', NULL, 2),
(5, 'os', 'win', null, 3),
(6, 'os', 'mac', null, 4),
(7, 'os', 'linux', null, 5);