-- Generates 13 launches. Each contains suite, test and 4 step items with item results. Launches and items has attributes

CREATE OR REPLACE FUNCTION items_init()
  RETURNS VOID AS
$BODY$
DECLARE   launchcounter  INT = 1;
  DECLARE retriescounter INT = 1;
  DECLARE cur_suite_id   BIGINT;
  DECLARE cur_item_id    BIGINT;
  DECLARE cur_step_id    BIGINT;
  DECLARE stepcounter    INT = 1;
  DECLARE functionresult INT = 0;
BEGIN
  WHILE launchcounter < 13 LOOP
    INSERT INTO launch (id, uuid, project_id, user_id, name, start_time, number, last_modified, mode, status)
    VALUES (launchcounter, 'uuid ' || launchcounter, 1, 1, 'name ' || launchcounter, now(), 1, now(), 'DEFAULT', 'IN_PROGRESS');

    INSERT INTO item_attribute (key, value, item_id, launch_id, system)
    VALUES ('key' || launchcounter % 4, 'value' || launchcounter, NULL, launchcounter, FALSE);

    IF floor(random() * (3 - 1 + 1) + 1) = 2
    THEN INSERT INTO item_attribute (key, value, item_id, launch_id, system)
         VALUES ('systemKey', 'systemValue', NULL, launchcounter, TRUE);
    END IF;

    launchcounter = launchcounter + 1;
  END LOOP;

  launchcounter = 1;

  WHILE launchcounter < 13 LOOP
    INSERT INTO test_item (name, type, start_time, description, last_modified, unique_id, launch_id)
    VALUES ('SUITE ' || launchcounter, 'SUITE', now(), 'description', now(), 'unqIdSUITE' || launchcounter, launchcounter);
    cur_suite_id = (SELECT currval(pg_get_serial_sequence('test_item', 'item_id')));

    INSERT INTO item_attribute (key, value, item_id, launch_id, system)
    VALUES ('suite', 'value' || cur_suite_id, cur_suite_id, NULL, FALSE);

    UPDATE test_item SET path = cast(cast(cur_suite_id AS TEXT) AS LTREE) WHERE item_id = cur_suite_id;

    INSERT INTO test_item_results (result_id, status, duration, end_time) VALUES (cur_suite_id, 'FAILED', 0.35, now());
    --
    INSERT INTO test_item (name, type, start_time, description, last_modified, unique_id, launch_id, parent_id)
    VALUES ('First test', 'TEST', now(), 'description', now(), 'unqIdTEST' || launchcounter, launchcounter, cur_suite_id);
    cur_item_id = (SELECT currval(pg_get_serial_sequence('test_item', 'item_id')));

    INSERT INTO item_attribute (key, value, item_id, launch_id, system)
    VALUES ('test', 'value' || cur_item_id, cur_item_id, NULL, FALSE);

    UPDATE test_item
    SET path = cast(cur_suite_id AS TEXT) || cast(cast(cur_item_id AS TEXT) AS LTREE)
    WHERE item_id = cur_item_id;

    INSERT INTO test_item_results (result_id, status, duration, end_time) VALUES (cur_item_id, 'FAILED', 0.35, now());

    WHILE stepcounter < 4 LOOP
      --
      INSERT INTO test_item (name, type, start_time, description, last_modified, unique_id, parent_id, launch_id)
      VALUES ('Step', 'STEP', now(), 'description', now(), 'unqIdSTEP' || launchcounter, cur_item_id, launchcounter);
      cur_step_id = (SELECT currval(pg_get_serial_sequence('test_item', 'item_id')));

      INSERT INTO item_attribute (key, value, item_id, launch_id, system)
      VALUES ('step', 'value' || cur_step_id, cur_step_id, NULL, FALSE);

      IF cur_step_id = 3
      THEN
        PERFORM logs_init();
      END IF;

      UPDATE test_item
      SET path = cast(cur_suite_id AS TEXT) || cast(cast(cur_item_id AS TEXT) AS LTREE) || cast(cur_step_id AS TEXT)
      WHERE item_id = cur_step_id;

      INSERT INTO test_item_results (result_id, status, duration, end_time) VALUES (cur_step_id, 'IN_PROGRESS', 0.35, now());

      IF stepcounter = 1
      THEN
        UPDATE test_item_results SET status = 'FAILED' WHERE result_id = cur_step_id;
        INSERT INTO issue (issue_id, issue_type, auto_analyzed, issue_description) VALUES (cur_step_id, 1, FALSE, 'issue description');
      END IF;

      IF stepcounter = 2
      THEN
        UPDATE test_item SET last_modified = '2018-11-08 12:00:00' WHERE item_id = cur_step_id;
      END IF;

      IF stepcounter = 3
      THEN
        UPDATE test_item SET last_modified = now() - make_interval(days := 14) WHERE item_id = cur_step_id;
      END IF;
      stepcounter = stepcounter + 1;
    END LOOP;
    stepcounter = 1;

    launchcounter = launchcounter + 1;
  END LOOP;

  -- RETRIES --

  INSERT INTO test_item (name, type, start_time, description, last_modified, unique_id, launch_id)
  VALUES ('SUITE ' || launchcounter - 1,
          'SUITE',
          now(),
          'suite with retries',
          now(),
          'unqIdSUITE_R' || launchcounter - 1,
          launchcounter - 1);
  cur_suite_id = (SELECT currval(pg_get_serial_sequence('test_item', 'item_id')));

  UPDATE test_item SET path = cast(cast(cur_suite_id AS TEXT) AS LTREE) WHERE item_id = cur_suite_id;

  INSERT INTO test_item_results (result_id, status, duration, end_time) VALUES (cur_suite_id, 'FAILED', 0.35, now());
  --
  INSERT INTO test_item (name, type, start_time, description, last_modified, unique_id, launch_id, parent_id)
  VALUES ('First test', 'TEST', now(), 'test with retries', now(), 'unqIdTEST_R' || launchcounter - 1, launchcounter - 1, cur_suite_id);
  cur_item_id = (SELECT currval(pg_get_serial_sequence('test_item', 'item_id')));

  UPDATE test_item SET path = cast(cur_suite_id AS TEXT) || cast(cast(cur_item_id AS TEXT) AS LTREE) WHERE item_id = cur_item_id;

  INSERT INTO test_item_results (result_id, status, duration, end_time) VALUES (cur_item_id, 'FAILED', 0.35, now());

  INSERT INTO test_item (name, type, start_time, description, last_modified, unique_id, parent_id, launch_id)
  VALUES ('Step', 'STEP', now(), 'STEP WITH RETRIES', now(), 'unqIdSTEP_R' || launchcounter - 1, cur_item_id, launchcounter - 1);
  cur_step_id = (SELECT currval(pg_get_serial_sequence('test_item', 'item_id')));

  UPDATE test_item
  SET path = cast(cur_suite_id AS TEXT) || cast(cast(cur_item_id AS TEXT) AS LTREE) || cast(cur_step_id AS TEXT)
  WHERE item_id = cur_step_id;

  INSERT INTO test_item_results (result_id, status, duration, end_time) VALUES (cur_step_id, 'IN_PROGRESS', 0.35, now());

  WHILE retriescounter < 4 LOOP

    INSERT INTO test_item (name, type, start_time, description, last_modified, unique_id, parent_id, launch_id)
    VALUES ('Step',
            'STEP',
            now() - make_interval(secs := retriescounter),
            'STEP WITH RETRIES',
            now() - make_interval(secs := retriescounter),
            'unqIdSTEP_R' || launchcounter - 1,
            cur_item_id,
            launchcounter - 1);
    cur_step_id = (SELECT currval(pg_get_serial_sequence('test_item', 'item_id')));

    UPDATE test_item
    SET path = cast(cur_suite_id AS TEXT) || cast(cast(cur_item_id AS TEXT) AS LTREE) || cast(cur_step_id AS TEXT)
    WHERE item_id = cur_step_id;

    INSERT INTO test_item_results (result_id, status, duration, end_time) VALUES (cur_step_id, 'IN_PROGRESS', 0.35, now());

    functionresult := (SELECT handle_retries(cur_step_id));

    retriescounter = retriescounter + 1;

  END LOOP;

  functionresult := (SELECT retries_statistics(launchcounter - 1));
END
$BODY$
LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION logs_init()
  RETURNS VOID AS
$BODY$
DECLARE   stepid      INT = 3;
  DECLARE logscounter INT = 1;
BEGIN
  WHILE logscounter < 4 LOOP

    INSERT INTO log (log_time, log_message, item_id, last_modified, log_level, attachment, attachment_thumbnail, content_type)
    VALUES (now(), 'log', stepid, now() - make_interval(days := 14), 40000, 'attach ' || logscounter, 'attachThumb' || logscounter, 'MIME');
    logscounter = logscounter + 1;
  END LOOP;

  WHILE logscounter > 0 LOOP

    INSERT INTO log (log_time, log_message, item_id, last_modified, log_level, attachment, attachment_thumbnail, content_type)
    VALUES (now(),
            'log',
            stepid,
            now(),
            40000,
            'attach ' || logscounter || logscounter,
            'attachThumb' || logscounter || logscounter,
            'MIME');
    logscounter = logscounter - 1;
  END LOOP;
END;
$BODY$
LANGUAGE plpgsql;

SELECT items_init();

DROP FUNCTION IF EXISTS items_init();
DROP FUNCTION IF EXISTS logs_init();