INSERT INTO statistics_field (sf_id, name) VALUES (1, 'statistics$executions$total');
INSERT INTO statistics_field (sf_id, name) VALUES (2, 'statistics$executions$passed');
INSERT INTO statistics_field (sf_id, name) VALUES (3, 'statistics$executions$skipped');
INSERT INTO statistics_field (sf_id, name) VALUES (4, 'statistics$executions$failed');
INSERT INTO statistics_field (sf_id, name) VALUES (5, 'statistics$defects$to_investigate$total');
INSERT INTO statistics_field (sf_id, name) VALUES (6, 'statistics$defects$system_issue$total');
INSERT INTO statistics_field (sf_id, name) VALUES (7, 'statistics$defects$automation_bug$total');
INSERT INTO statistics_field (sf_id, name) VALUES (8, 'statistics$defects$product_bug$total');
INSERT INTO statistics_field (sf_id, name) VALUES (9, 'statistics$defects$no_defect$total');
INSERT INTO statistics_field (sf_id, name) VALUES (10, 'statistics$defects$to_investigate$ti001');
INSERT INTO statistics_field (sf_id, name) VALUES (11, 'statistics$defects$system_issue$si001');
INSERT INTO statistics_field (sf_id, name) VALUES (12, 'statistics$defects$automation_bug$ab001');
INSERT INTO statistics_field (sf_id, name) VALUES (13, 'statistics$defects$product_bug$pb001');
INSERT INTO statistics_field (sf_id, name) VALUES (14, 'statistics$defects$no_defect$nd001');

-------------------------------------------------------------------------------------------------------------------<
-------------------------------------------------------------------------------------------------------------------<
-- LAUNCHES STATISTICS
INSERT INTO statistics(launch_id, statistics_field_id, s_counter) VALUES(1, 1, 10);
INSERT INTO statistics(launch_id, statistics_field_id, s_counter) VALUES(1, 2, 3);
INSERT INTO statistics(launch_id, statistics_field_id, s_counter) VALUES(1, 3, 4);
INSERT INTO statistics(launch_id, statistics_field_id, s_counter) VALUES(1, 4, 3);

INSERT INTO statistics(launch_id, statistics_field_id, s_counter) VALUES(1, 5, 2);
INSERT INTO statistics(launch_id, statistics_field_id, s_counter) VALUES(1, 6, 8);
INSERT INTO statistics(launch_id, statistics_field_id, s_counter) VALUES(1, 7, 7);
INSERT INTO statistics(launch_id, statistics_field_id, s_counter) VALUES(1, 8, 13);
INSERT INTO statistics(launch_id, statistics_field_id, s_counter) VALUES(1, 9, 2);

INSERT INTO statistics(launch_id, statistics_field_id, s_counter) VALUES(1, 10, 2);
INSERT INTO statistics(launch_id, statistics_field_id, s_counter) VALUES(1, 11, 8);
INSERT INTO statistics(launch_id, statistics_field_id, s_counter) VALUES(1, 12, 7);
INSERT INTO statistics(launch_id, statistics_field_id, s_counter) VALUES(1, 13, 13);
INSERT INTO statistics(launch_id, statistics_field_id, s_counter) VALUES(1, 14, 2);

--

INSERT INTO statistics(launch_id, statistics_field_id, s_counter) VALUES(2, 1, 11);
INSERT INTO statistics(launch_id, statistics_field_id, s_counter) VALUES(2, 2, 2);
INSERT INTO statistics(launch_id, statistics_field_id, s_counter) VALUES(2, 3, 3);
INSERT INTO statistics(launch_id, statistics_field_id, s_counter) VALUES(2, 4, 6);

INSERT INTO statistics(launch_id, statistics_field_id, s_counter) VALUES(2, 5, 3);
INSERT INTO statistics(launch_id, statistics_field_id, s_counter) VALUES(2, 6, 3);
INSERT INTO statistics(launch_id, statistics_field_id, s_counter) VALUES(2, 7, 1);
INSERT INTO statistics(launch_id, statistics_field_id, s_counter) VALUES(2, 8, 1);
INSERT INTO statistics(launch_id, statistics_field_id, s_counter) VALUES(2, 9, 2);

INSERT INTO statistics(launch_id, statistics_field_id, s_counter) VALUES(2, 10, 3);
INSERT INTO statistics(launch_id, statistics_field_id, s_counter) VALUES(2, 11, 3);
INSERT INTO statistics(launch_id, statistics_field_id, s_counter) VALUES(2, 12, 1);
INSERT INTO statistics(launch_id, statistics_field_id, s_counter) VALUES(2, 13, 1);
INSERT INTO statistics(launch_id, statistics_field_id, s_counter) VALUES(2, 14, 2);

--

INSERT INTO statistics(launch_id, statistics_field_id, s_counter) VALUES(3, 1, 15);
INSERT INTO statistics(launch_id, statistics_field_id, s_counter) VALUES(3, 2, 5);
INSERT INTO statistics(launch_id, statistics_field_id, s_counter) VALUES(3, 3, 5);
INSERT INTO statistics(launch_id, statistics_field_id, s_counter) VALUES(3, 4, 5);

INSERT INTO statistics(launch_id, statistics_field_id, s_counter) VALUES(3, 5, 1);
INSERT INTO statistics(launch_id, statistics_field_id, s_counter) VALUES(3, 6, 1);
INSERT INTO statistics(launch_id, statistics_field_id, s_counter) VALUES(3, 7, 1);
INSERT INTO statistics(launch_id, statistics_field_id, s_counter) VALUES(3, 8, 1);
INSERT INTO statistics(launch_id, statistics_field_id, s_counter) VALUES(3, 9, 1);

INSERT INTO statistics(launch_id, statistics_field_id, s_counter) VALUES(3, 10, 1);
INSERT INTO statistics(launch_id, statistics_field_id, s_counter) VALUES(3, 11, 1);
INSERT INTO statistics(launch_id, statistics_field_id, s_counter) VALUES(3, 12, 1);
INSERT INTO statistics(launch_id, statistics_field_id, s_counter) VALUES(3, 13, 1);
INSERT INTO statistics(launch_id, statistics_field_id, s_counter) VALUES(3, 14, 1);

--

INSERT INTO statistics(launch_id, statistics_field_id, s_counter) VALUES(4, 1, 12);
INSERT INTO statistics(launch_id, statistics_field_id, s_counter) VALUES(4, 2, 3);
INSERT INTO statistics(launch_id, statistics_field_id, s_counter) VALUES(4, 3, 1);
INSERT INTO statistics(launch_id, statistics_field_id, s_counter) VALUES(4, 4, 8);

INSERT INTO statistics(launch_id, statistics_field_id, s_counter) VALUES(4, 5, 3);
INSERT INTO statistics(launch_id, statistics_field_id, s_counter) VALUES(4, 6, 4);
INSERT INTO statistics(launch_id, statistics_field_id, s_counter) VALUES(4, 7, 2);
INSERT INTO statistics(launch_id, statistics_field_id, s_counter) VALUES(4, 8, 2);
INSERT INTO statistics(launch_id, statistics_field_id, s_counter) VALUES(4, 9, 6);

INSERT INTO statistics(launch_id, statistics_field_id, s_counter) VALUES(4, 10, 3);
INSERT INTO statistics(launch_id, statistics_field_id, s_counter) VALUES(4, 11, 4);
INSERT INTO statistics(launch_id, statistics_field_id, s_counter) VALUES(4, 12, 2);
INSERT INTO statistics(launch_id, statistics_field_id, s_counter) VALUES(4, 13, 2);
INSERT INTO statistics(launch_id, statistics_field_id, s_counter) VALUES(4, 14, 6);