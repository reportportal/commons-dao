CREATE OR REPLACE FUNCTION test_project_init()
  RETURNS VOID AS
$$
DECLARE
  falcon       BIGINT;
  han_solo     BIGINT;
  chubaka      BIGINT;
  fake_chubaka BIGINT;
BEGIN
  INSERT INTO project (name, project_type, creation_date) VALUES ('millennium_falcon', 'INTERNAL', now());
  falcon := (SELECT currval(pg_get_serial_sequence('project', 'id')));

  INSERT INTO users (login, password, email, role, type, full_name, expired, metadata)
  VALUES ('han_solo', '3531f6f9b0538fd347f4c95bd2af9d01', 'han_solo@domain.com', 'ADMINISTRATOR', 'INTERNAL', 'Han Solo', FALSE,
          '{"metadata": {"last_login": "2018-12-27T10:31:15.573"}}');
  han_solo := (SELECT currval(pg_get_serial_sequence('users', 'id')));

  INSERT INTO project_user (user_id, project_id, project_role) VALUES (han_solo, falcon, 'PROJECT_MANAGER');


  INSERT INTO users (login, password, email, role, type, full_name, expired)
  VALUES ('chubaka', '601c4731aeff3b84f76672ad024bb2a0', 'chybaka@domain.com', 'USER', 'INTERNAL', 'Chubaka', FALSE);
  chubaka := (SELECT currval(pg_get_serial_sequence('users', 'id')));

  INSERT INTO project_user (user_id, project_id, project_role) VALUES (chubaka, falcon, 'MEMBER');

  INSERT INTO users (login, password, email, role, type, full_name, expired)
  VALUES ('fake_chubaka', '601c4731aeff3b84f76672ad024bb2a0', 'chybakafake@domain.com', 'USER', 'INTERNAL', 'Chubaka Fake', FALSE);
  fake_chubaka := (SELECT currval(pg_get_serial_sequence('users', 'id')));

  INSERT INTO project_user (user_id, project_id, project_role) VALUES (fake_chubaka, falcon, 'MEMBER');

  INSERT INTO issue_type_project (project_id, issue_type_id)
  VALUES (falcon, 1),
         (falcon, 2),
         (falcon, 3),
         (falcon, 4),
         (falcon, 5);

  INSERT INTO project_attribute (project_id, attribute_id, value) VALUES (falcon, 1, '1 hour');
  INSERT INTO project_attribute (project_id, attribute_id, value) VALUES (falcon, 2, '2 weeks');
  INSERT INTO project_attribute (project_id, attribute_id, value) VALUES (falcon, 3, '2 weeks');
  INSERT INTO project_attribute (project_id, attribute_id, value) VALUES (falcon, 4, '2 weeks');

END;
$$
  LANGUAGE plpgsql;