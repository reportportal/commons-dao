ALTER TABLE attachment ADD COLUMN creation_date TIMESTAMP;
CREATE OR REPLACE PROCEDURE fill_attachment_creation_date()
    LANGUAGE plpgsql
AS
$$
DECLARE
    prj_id BIGINT;
BEGIN

    FOR prj_id IN (SELECT id FROM project ORDER BY id)
        LOOP
            UPDATE attachment AS to_update
            SET creation_date = source.log_time
            FROM (SELECT attachment.id, log.log_time
                  FROM attachment
                           JOIN log ON attachment.id = log.attachment_id
                  WHERE attachment.project_id = prj_id
                    AND attachment.creation_date IS NULL) source
            WHERE to_update.id = source.id
              AND to_update.project_id = prj_id;

            COMMIT;
        END LOOP;

    UPDATE attachment SET creation_date = now() WHERE attachment.creation_date IS NULL;
END;
$$;