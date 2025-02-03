CREATE OR REPLACE FUNCTION test_group_init()
    RETURNS VOID AS
$$
DECLARE
    falcon       BIGINT;
    han_solo     BIGINT;
    chubaka      BIGINT;
    fake_chubaka BIGINT;
    rebel        BIGINT;
    ewoks        BIGINT;
BEGIN
    falcon := (SELECT p.id FROM project p WHERE name = 'millennium_falcon');
    han_solo := (SELECT u.id FROM users u WHERE login = 'han_solo');
    chubaka := (SELECT u.id FROM users u WHERE login = 'chubaka');
    fake_chubaka := (SELECT u.id FROM users u WHERE login = 'fake_chubaka');

    INSERT INTO groups (name, slug, created_by, created_at)
    VALUES ('Rebel army', 'rebel-group', 1, now());
    rebel := (SELECT currval(pg_get_serial_sequence('groups', 'id')));

    INSERT INTO groups (name, slug, created_by, created_at)
    VALUES ('Ewoks group', 'ewoks-group', 1, now());
    ewoks := (SELECT currval(pg_get_serial_sequence('groups', 'id')));

    INSERT INTO groups_users (group_id, user_id, group_role, created_at)
    VALUES (rebel, han_solo, 'ADMIN', now());

    INSERT INTO groups_users (group_id, user_id, group_role, created_at)
    VALUES (rebel, chubaka, 'MEMBER', now());

    INSERT INTO groups_users (group_id, user_id, group_role, created_at)
    VALUES (ewoks, chubaka, 'ADMIN', now());

    INSERT INTO groups_users (group_id, user_id, group_role, created_at)
    VALUES (ewoks, fake_chubaka, 'MEMBER', now());

    INSERT INTO groups_projects (group_id, project_id, project_role, created_at)
    VALUES (rebel, falcon, 'PROJECT_MANAGER', now());

    INSERT INTO groups_projects (group_id, project_id, project_role, created_at)
    VALUES (ewoks, falcon, 'MEMBER', now());

END;
$$
    LANGUAGE plpgsql;