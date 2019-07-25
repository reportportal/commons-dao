-- Generates 12 launches in 'IN_PROGRESS' status on superadmin project and 1 launch with status 'FAILED'

CREATE OR REPLACE FUNCTION launches_init()
  RETURNS VOID AS
$$
DECLARE
  differentlaunchescounter INT = 1;
  samelaunchcounter        INT = 1;
  launchnumber             INT = 100;
BEGIN
  WHILE differentlaunchescounter < 4
  LOOP
    RAISE NOTICE 'Value: %', differentlaunchescounter;
    WHILE samelaunchcounter < 5
    LOOP
      RAISE NOTICE 'Value: %', samelaunchcounter;
      INSERT INTO public.launch (uuid, project_id, number, user_id, name, description, start_time, end_time, last_modified, mode, status)
      VALUES ('uuid ' || differentlaunchescounter || samelaunchcounter,
              1,
              launchnumber,
              1,
              'launch name ' || differentlaunchescounter,
              'description',
              now() - make_interval(days := 14),
              now() - make_interval(days := 14) + make_interval(mins := 1),
              now() - make_interval(days := 14) + make_interval(mins := 1),
              'DEFAULT',
              'IN_PROGRESS');
      launchnumber = launchnumber + 1;
      samelaunchcounter = samelaunchcounter + 1;
      IF samelaunchcounter % 4 = 0
      THEN
        INSERT INTO item_attribute (key, value, system, launch_id)
        VALUES ('key', 'value', TRUE, currval(pg_get_serial_sequence('launch', 'id')));
      ELSE
        INSERT INTO item_attribute (key, value, system, launch_id)
        VALUES ('key', 'value', FALSE, currval(pg_get_serial_sequence('launch', 'id')));
      END IF;
    END LOOP;
    samelaunchcounter = 1;
    differentlaunchescounter = differentlaunchescounter + 1;
  END LOOP;
END;
$$
LANGUAGE plpgsql;