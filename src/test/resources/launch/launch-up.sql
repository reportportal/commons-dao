-- Creates 12 launches
CREATE OR REPLACE FUNCTION launches_init()
  RETURNS VOID AS
$BODY$
DECLARE   differentLaunchesCounter INT = 1;
  DECLARE sameLaunchCounter        INT = 1;
BEGIN
  WHILE differentLaunchesCounter < 4 LOOP
    raise notice 'Value: %', differentLaunchesCounter;
    WHILE sameLaunchCounter < 5 LOOP
      raise notice 'Value: %', sameLaunchCounter;
      INSERT INTO public.launch (uuid, project_id, user_id, name, description, start_time, end_time, last_modified, mode, status)
      VALUES ('uuid ' || differentLaunchesCounter || sameLaunchCounter,
              1,
              1,
              'launch name ' || differentLaunchesCounter,
              'description',
              now() - make_interval(days := 15),
              now() - make_interval(days := 15) + make_interval(mins := 1),
              now() - make_interval(days := 15) + make_interval(mins := 1),
              'DEFAULT',
              'IN_PROGRESS');
      sameLaunchCounter = sameLaunchCounter + 1;
    END LOOP;
    sameLaunchCounter = 1;
    differentLaunchesCounter = differentLaunchesCounter + 1;
  END LOOP;
END;
$BODY$
LANGUAGE plpgsql;
.;
SELECT launches_init();

DROP FUNCTION IF EXISTS launches_init();