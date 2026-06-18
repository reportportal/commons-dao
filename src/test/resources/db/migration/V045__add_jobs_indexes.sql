CREATE INDEX IF NOT EXISTS log_project_id_log_time_idx
    ON log (project_id, log_time);

CREATE INDEX IF NOT EXISTS attachment_project_id_creation_time_idx
    ON attachment (project_id, creation_date);

DROP INDEX IF EXISTS launch_project_idx;
CREATE INDEX IF NOT EXISTS launch_project_start_time_idx ON launch (project_id, start_time);