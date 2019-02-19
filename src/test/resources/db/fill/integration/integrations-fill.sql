INSERT INTO integration_type (id, name, auth_flow, creation_date, group_type)
VALUES (1, 'test integration type', 'LDAP', now(), 'AUTH'),
       (2, 'RALLY', 'OAUTH', now(), 'BTS'),
       (3, 'JIRA', 'BASIC', now(), 'BTS'),
       (4, 'EMAIL', null, now(), 'NOTIFICATION');

INSERT INTO integration (id, project_id, type, enabled, creation_date)
VALUES (1, 1, 2, FALSE, now()),
       (2, 1, 3, FALSE, now()),
       (3, 2, 2, FALSE, now()),
       (4, 2, 3, FALSE, now());

INSERT INTO integration (id, project_id, type, enabled, params)
VALUES (5, 1, 4, false, '{"params": {"rules": [{"recipients": ["OWNER"], "fromAddress": "test@epam.com", "launchStatsRule": "always"}]}}'),
       (6, 2, 4, false, '{"params": {"rules": [{"recipients": ["OWNER"], "fromAddress": "test@epam.com", "launchStatsRule": "always"}]}}');

INSERT INTO integration (id, type, enabled, creation_date, params) --integration id = 8 (global JIRA)
VALUES (7, 3, false, now(), '{
  "params": {
    "url" : "bts.com",
    "project" : "bts_project"
  }
}');

INSERT INTO integration (id, project_id, type, enabled, creation_date, params)--integration id = 9 (superadmin project JIRA)
VALUES (8, 1, 3, false, now(), '{
  "params": {
    "url" : "projectbts.com",
    "project" : "project"
  }
}');

INSERT INTO integration (id, project_id, type, enabled, creation_date, params)--integration id = 10 (superadmin project RALLY)
VALUES (9, 1, 2, false, now(), '{
  "params": {
    "url" : "rallybts.com",
    "project" : "rallyproject"
  }
}');

INSERT INTO integration (id, type, enabled, creation_date, params) --integration id = 11 (global RALLY)
VALUES (10, 2, false, now(), '{
  "params": {
    "url" : "globalrally.com",
    "project" : "global_rally_project"
  }
}');

insert into integration(id, type, enabled, creation_date) values (11, 4, true, now());

alter sequence integration_type_id_seq restart with 5;
alter sequence integration_id_seq restart with 12;
