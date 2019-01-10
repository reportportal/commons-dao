-- Generates 12 launches in 'IN_PROGRESS' status on superadmin project and 1 launch with status 'FAILED'

DELETE FROM launch CASCADE;
DELETE FROM test_item CASCADE;

CREATE OR REPLACE FUNCTION launches_init()
  RETURNS VOID AS $$ DECLARE   differentLaunchesCounter INT = 1; DECLARE sameLaunchCounter INT = 1;
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
              now() - make_interval(days := 14),
              now() - make_interval(days := 14) + make_interval(mins := 1),
              now() - make_interval(days := 14) + make_interval(mins := 1),
              'DEFAULT',
              'IN_PROGRESS');
      sameLaunchCounter = sameLaunchCounter + 1;
    END LOOP;
    sameLaunchCounter = 1;
    differentLaunchesCounter = differentLaunchesCounter + 1;
  END LOOP;
END; $$ LANGUAGE plpgsql;

SELECT launches_init();

DROP FUNCTION IF EXISTS launches_init();

INSERT INTO public.launch(id, uuid, project_id, user_id, name, description, start_time, end_time, last_modified, mode, status)
VALUES (100, 'uuid', 2, 2, 'finished launch', 'description', now(), now(), now(), 'DEFAULT', 'FAILED');

INSERT INTO public.test_item(item_id, type, start_time, last_modified, has_children, has_retries, parent_id, launch_id)
VALUES (1, 'STEP', now(), now(), false, false, null, 100);

INSERT INTO public.test_item_results(result_id, status, end_time, duration)
VALUES (1, 'PASSED', now(), 1);

INSERT INTO public.test_item(item_id, type, start_time, last_modified, has_children, has_retries, parent_id, launch_id)
VALUES (2, 'STEP', now(), now(), false, false, null, 100);

INSERT INTO public.test_item_results(result_id, status, end_time, duration)
VALUES (2, 'FAILED', now(), 1);

