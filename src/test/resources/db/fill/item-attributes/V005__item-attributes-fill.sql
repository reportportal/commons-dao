-- Generates 13 launches. Each contains suite, test and 4 step items with item results. Launches and items has attributes

CREATE OR REPLACE FUNCTION items_init()
  RETURNS VOID AS
$BODY$
DECLARE   launchCounter INT = 1;
  DECLARE cur_suite_id  BIGINT;
  DECLARE cur_item_id   BIGINT;
  DECLARE cur_step_id   BIGINT;
  DECLARE stepCounter   INT = 1;
BEGIN
  WHILE launchCounter < 13 LOOP
    INSERT INTO launch (id, uuid, project_id, user_id, name, start_time, number, last_modified, mode, status)
    VALUES (launchCounter, 'uuid ' || launchCounter, 1, 1, 'name ' || launchCounter, now(), 1, now(), 'DEFAULT', 'IN_PROGRESS');

    INSERT INTO item_attribute ("key", "value", item_id, launch_id, system)
    VALUES ('key' || launchCounter % 4, 'value' || launchCounter, null, launchCounter, false);

    IF floor(random() * (3 - 1 + 1) + 1) = 2
    THEN INSERT INTO item_attribute ("key", "value", item_id, launch_id, system)
         VALUES ('systemKey', 'systemValue', null, launchCounter, true);
    END IF;

    launchCounter = launchCounter + 1;
  END LOOP;

  launchCounter = 1;

  WHILE launchCounter < 13 LOOP
    INSERT INTO test_item (name, type, start_time, description, last_modified, unique_id, launch_id)
    VALUES ('SUITE ' || launchCounter, 'SUITE', now(), 'description', now(), 'unqIdSUITE' || launchCounter, launchCounter);
    cur_suite_id = (SELECT currval(pg_get_serial_sequence('test_item', 'item_id')));

    INSERT INTO item_attribute ("key", "value", item_id, launch_id, system)
    VALUES ('suite', 'value' || cur_suite_id, cur_suite_id, null, false);

    UPDATE test_item SET path = cast(cast(cur_suite_id as text) as ltree) where item_id = cur_suite_id;

    INSERT INTO test_item_results (result_id, status, duration, end_time) VALUES (cur_suite_id, 'FAILED', 0.35, now());
    --
    INSERT INTO test_item (name, type, start_time, description, last_modified, unique_id, launch_id, parent_id)
    VALUES ('First test', 'TEST', now(), 'description', now(), 'unqIdTEST' || launchCounter, launchCounter, cur_suite_id);
    cur_item_id = (SELECT currval(pg_get_serial_sequence('test_item', 'item_id')));

    INSERT INTO item_attribute ("key", "value", item_id, launch_id, system)
    VALUES ('test', 'value' || cur_item_id, cur_item_id, null, false);

    UPDATE test_item SET path = cast(cur_suite_id as text) || cast(cast(cur_item_id as text) as ltree) where item_id = cur_item_id;

    INSERT INTO test_item_results (result_id, status, duration, end_time) VALUES (cur_item_id, 'FAILED', 0.35, now());

    WHILE stepCounter < 4 LOOP
      --
      INSERT INTO test_item (NAME, TYPE, start_time, description, last_modified, unique_id, parent_id, launch_id)
      VALUES ('Step', 'STEP', now(), 'description', now(), 'unqIdSTEP' || launchCounter, cur_item_id, launchCounter);
      cur_step_id = (SELECT currval(pg_get_serial_sequence('test_item', 'item_id')));

      INSERT INTO item_attribute ("key", "value", item_id, launch_id, system)
      VALUES ('step', 'value' || cur_step_id, cur_step_id, null, false);

      UPDATE test_item
      SET path = cast(cur_suite_id as text) || cast(cast(cur_item_id as text) as ltree) || cast(cur_step_id as text)
      where item_id = cur_step_id;

      INSERT INTO test_item_results (result_id, status, duration, end_time) VALUES (cur_step_id, 'IN_PROGRESS', 0.35, now());

      IF stepCounter = 1
      THEN
        UPDATE test_item_results SET status = 'FAILED' WHERE result_id = cur_step_id;
        INSERT INTO issue (issue_id, issue_type, issue_description) VALUES (cur_step_id, 1, 'issue description');
      END IF;

      IF stepCounter = 2
      THEN
        UPDATE test_item SET last_modified = '2018-11-08 12:00:00' WHERE item_id = cur_step_id;
      END IF;

      IF stepCounter = 3
      THEN
        UPDATE test_item SET last_modified = now() - make_interval(days := 14) WHERE item_id = cur_step_id;
      END IF;
      stepCounter = stepCounter + 1;
    END LOOP;
    stepCounter = 1;

    launchCounter = launchCounter + 1;
  END LOOP;
END
$BODY$
LANGUAGE plpgsql;

SELECT items_init();

DROP FUNCTION IF EXISTS items_init();