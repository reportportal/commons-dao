SELECT items_init();

INSERT INTO clusters(id, index_id, project_id, launch_id, message)
VALUES (1, 1, 1, 1, 'Message');

UPDATE log
SET cluster_id = 1
WHERE id IN (4, 5, 6)