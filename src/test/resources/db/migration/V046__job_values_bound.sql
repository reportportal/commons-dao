CREATE OR REPLACE FUNCTION update_job_attributes_in_bounds()
    RETURNS INTEGER
    LANGUAGE plpgsql
AS
$$
DECLARE
prj_id                 BIGINT;

    launch_job_attr_id     BIGINT;
    log_job_attr_id        BIGINT;
    attachment_job_attr_id BIGINT;

    launch_job_value       BIGINT;
    log_job_value          BIGINT;
    attachment_job_value   BIGINT;
BEGIN
    launch_job_attr_id := (SELECT id
                           FROM attribute
                           WHERE attribute.name = 'job.keepLaunches');
    log_job_attr_id := (SELECT id
                        FROM attribute
                        WHERE attribute.name = 'job.keepLogs');
    attachment_job_attr_id := (SELECT id
                               FROM attribute
                               WHERE attribute.name = 'job.keepScreenshots');

FOR prj_id IN (SELECT id FROM project ORDER BY id)
        LOOP
            launch_job_value := (SELECT value::BIGINT FROM project_attribute WHERE attribute_id = launch_job_attr_id AND project_id = prj_id);
            log_job_value := (SELECT value::BIGINT FROM project_attribute WHERE attribute_id = log_job_attr_id AND project_id = prj_id);
            attachment_job_value := (SELECT value::BIGINT FROM project_attribute WHERE attribute_id = attachment_job_attr_id AND project_id = prj_id);

            IF launch_job_value != 0
            THEN
                IF log_job_value > launch_job_value OR log_job_value = 0
                THEN
                    log_job_value := launch_job_value;
UPDATE project_attribute
SET value = log_job_value
WHERE attribute_id = log_job_attr_id
  AND project_id = prj_id;
END IF;
END IF;

            IF log_job_value != 0
            THEN
                IF attachment_job_value > log_job_value OR attachment_job_value = 0
                THEN
                    attachment_job_value := log_job_value;
UPDATE project_attribute
SET value = attachment_job_value
WHERE attribute_id = attachment_job_attr_id
  AND project_id = prj_id;
END IF;
END IF;

END LOOP;

RETURN 0;
END;
$$;

SELECT update_job_attributes_in_bounds();