INSERT INTO launch (id, uuid, project_id, user_id, name, start_time, last_modified, mode, status)
VALUES (1, 'uuid', 1, 1, 'test launch', now(), now(), 'DEFAULT', 'IN_PROGRESS');

INSERT INTO test_item (item_id, type, start_time, last_modified, launch_id)
VALUES (1, 'STEP', now(), now(), 1);

INSERT INTO test_item_results(result_id, status) VALUES (1, 'IN_PROGRESS');

CREATE OR REPLACE FUNCTION logs_init()
  RETURNS VOID AS
$BODY$
DECLARE   stepId      INT = 1;
  DECLARE logsCounter INT = 1;
BEGIN
  WHILE logsCounter < 4 LOOP

    INSERT INTO log (log_time, log_message, item_id, last_modified, log_level, attachment, attachment_thumbnail, content_type)
    VALUES (now(), 'log', stepId, now() - make_interval(days := 14), 40000, 'attach ' || logsCounter, 'attachThumb' || logsCounter, 'MIME');
    logsCounter = logsCounter + 1;
  END LOOP;

  WHILE logsCounter > 0 LOOP

    INSERT INTO log (log_time, log_message, item_id, last_modified, log_level, attachment, attachment_thumbnail, content_type)
    VALUES (now(),
            'log',
            stepId,
            now(),
            40000,
            'attach ' || logsCounter || logsCounter,
            'attachThumb' || logsCounter || logsCounter,
            'MIME');
    logsCounter = logsCounter - 1;
  END LOOP;
END;
$BODY$
LANGUAGE plpgsql;

SELECT logs_init();

DROP FUNCTION IF EXISTS logs_init();