ALTER SEQUENCE users_id_seq
  RESTART WITH 1;
DELETE
FROM users;
ALTER SEQUENCE launch_id_seq
  RESTART WITH 1;
DELETE
FROM launch;
ALTER SEQUENCE issue_type_id_seq
  RESTART WITH 1;
DELETE
FROM issue_type;

ALTER SEQUENCE issue_group_issue_group_id_seq
  RESTART WITH 1;
DELETE
FROM issue_group;
ALTER SEQUENCE project_id_seq
  RESTART WITH 1;
DELETE
FROM project;