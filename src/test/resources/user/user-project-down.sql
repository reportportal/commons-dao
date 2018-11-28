ALTER SEQUENCE users_id_seq
  RESTART WITH 1;
DELETE
FROM users CASCADE;
DELETE
FROM issue_type_project CASCADE;
ALTER SEQUENCE issue_type_id_seq
  RESTART WITH 1;
DELETE
FROM issue_type CASCADE;
DELETE
FROM project_attribute;
ALTER SEQUENCE attribute_id_seq
  RESTART WITH 1;
DELETE
FROM attribute;
ALTER SEQUENCE project_id_seq
  RESTART WITH 1;
DELETE
FROM project;
ALTER SEQUENCE issue_group_issue_group_id_seq
  RESTART WITH 1;
DELETE
FROM issue_group CASCADE;
ALTER SEQUENCE attribute_id_seq
  RESTART WITH 1;
