INSERT INTO launch (id, uuid, project_id, user_id, name, start_time, number, last_modified, mode, status) VALUES
(1, 'bd788410-c7c6-4a56-a314-4fee8f913e38', 1, 1, 'Launch A', now(), 1, now(), 'DEFAULT', 'IN_PROGRESS'),
(2, '137b1a98-06ae-499f-a420-b9579ef00337', 1, 1, 'Launch B', now(), 1, now(), 'DEFAULT', 'IN_PROGRESS'),
(3, 'dab36533-9961-4e56-b833-fe5e1259bfc0', 1, 1, 'Launch C', now(), 1, now(), 'DEFAULT', 'IN_PROGRESS'),
(4, 'fc5573cd-179b-467d-afbf-5491fb829631', 1, 1, 'Launch D', now(), 1, now(), 'DEFAULT', 'IN_PROGRESS'),
(5, 'c3ebf147-7f58-480f-9fe3-23d8dd1d6d49', 1, 1, 'Launch E', now(), 1, now(), 'DEFAULT', 'IN_PROGRESS'),
(6, '8d5c2615-7afa-4522-9f20-5b906195621a', 1, 1, 'Launch F', now(), 1, now(), 'DEFAULT', 'IN_PROGRESS'),
(7, 'e7a0c9ee-2028-4d12-9d20-d46aae2926b9', 1, 1, 'Launch G', now(), 1, now(), 'DEFAULT', 'IN_PROGRESS');

INSERT INTO item_attribute (id, "key", "value", item_id, launch_id, system) VALUES
(1, 'browser', 'chrome', NULL, 1, false),
(2, 'env', 'ios', NULL, 1, false),
(3, 'skippedIssue', 'false', null, 1, true),

(4, 'browser', 'ie', null, 2, false),
(5, 'scope', 'regression', null, 2, false),
(6, 'skippedIssue', 'true', null, 2, true),

(7, 'browser', 'edge', null, 3, false),
(8, 'env', 'android', null, 3, false),
(9, 'scope', 'sanity', null, 3, false),
(10, 'skippedIssue', 'false', null, 3, true),

(11, 'browser', 'mozilla', null, 4, false),
(12, NULL, 'UI', null, 4, false),
(13, 'skippedIssue', 'true', null, 4, true),

(14, 'browser', 'chrome', null, 5, false),
(15, 'scope', 'regression', null, 5, false),
(16, 'skippedIssue', 'false', null, 5, true),

(17, 'browser', 'safari', null, 6, false),
(18, 'env', 'win', null, 6, false),
(19, 'skippedIssue', 'true', null, 6, true),

(20, 'browser', 'edge', null, 7, false),
(21, NULL, 'ws', null, 7, false),
(22, 'scope', 'cit', null, 7, false),
(23, 'skippedIssue', 'false', null, 7, true);