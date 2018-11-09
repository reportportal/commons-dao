CREATE OR REPLACE FUNCTION logs_init()
  RETURNS VOID AS
$BODY$
DECLARE   stepId      INT = 5;
  DECLARE logsCounter INT = 1;
BEGIN
  WHILE logsCounter < 4 LOOP

    INSERT INTO log (log_time, log_message, item_id, last_modified, log_level, attachment, attachment_thumbnail, content_type)
    VALUES (now(), 'log', stepId, now() - make_interval(days := 14), 40000, 'attach ' || logsCounter, 'attachThumb' || logsCounter, 'MIME');
    logsCounter = logsCounter + 1;
  END LOOP;

  WHILE logsCounter > 0 LOOP

    INSERT INTO log (log_time, log_message, item_id, last_modified, log_level, attachment, attachment_thumbnail, content_type)
    VALUES (now(), 'log', stepId, now(), 40000, 'attach ' || logsCounter || logsCounter, 'attachThumb' || logsCounter || logsCounter, 'MIME');
    logsCounter = logsCounter - 1;
  END LOOP;
END;
$BODY$
LANGUAGE plpgsql;
-- .;
SELECT logs_init();

DROP FUNCTION IF EXISTS logs_init();