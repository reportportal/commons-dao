INSERT INTO test_item (item_id, type, start_time, last_modified) VALUES
(1, 'SUITE', now(), now()),
(2, 'SUITE', now(), now()),
(3, 'SUITE', now(), now()),
(4, 'SUITE', now(), now()),
(5, 'SUITE', now(), now()),
(6, 'SUITE', now(), now());


INSERT INTO log (id, log_time, log_message, item_id, last_modified, log_level) VALUES
(1, now(), 'message 1', 1, now(), 40000),
(2, now(), 'message 2', 2, now(), 30000),
(3, now(), 'message 3', 3, now(), 30000),
(7, now(), 'message 3', 3, now(), 10000),
(8, now(), 'message 3', 3, now(), 50000),
(4, now(), 'message 4', 4, now(), 30000),
(9, now(), 'message 4', 4, now(), 50000),
(5, now(), 'message 5', 5, now(), 30000),
(6, now(), 'message 6', 6, now(), 50000);
