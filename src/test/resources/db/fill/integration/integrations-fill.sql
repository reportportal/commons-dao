INSERT INTO integration (id, project_id, type, enabled, creation_date)
VALUES (7, 1, 3, FALSE, now()),
       (8, 1, 4, FALSE, now()),
       (9, 2, 3, FALSE, now()),
       (10, 2, 4, FALSE, now());

INSERT INTO integration (id, project_id, type, enabled, params)
VALUES (11, 1, 2, false, '{"params": {"rules": [{"recipients": ["OWNER"], "fromAddress": "test@epam.com", "launchStatsRule": "always"}]}}'),
       (12, 2, 2, false, '{"params": {"rules": [{"recipients": ["OWNER"], "fromAddress": "test@epam.com", "launchStatsRule": "always"}]}}');

INSERT INTO integration (id, type, enabled, creation_date, params) --integration id = 8 (global JIRA)
VALUES (13, 4, false, now(), '{
  "params": {
    "url" : "bts.com",
    "project" : "bts_project"
  }
}');

INSERT INTO integration (id, project_id, type, enabled, creation_date, params)--integration id = 9 (superadmin project JIRA)
VALUES (14, 1, 4, false, now(), '{
  "params": {
    "url" : "projectbts.com",
    "project" : "project"
  }
}');

INSERT INTO integration (id, project_id, type, enabled, creation_date, params)--integration id = 10 (superadmin project RALLY)
VALUES (15, 1, 3, false, now(), '{
  "params": {
    "url" : "rallybts.com",
    "project" : "rallyproject"
  }
}');

INSERT INTO integration (id, type, enabled, creation_date, params) --integration id = 11 (global RALLY)
VALUES (16, 3, false, now(), '{
  "params": {
    "url" : "globalrally.com",
    "project" : "global_rally_project"
  }
}');

insert into integration(id, type, enabled, creation_date) values (17, 2, true, now());

alter sequence integration_id_seq restart with 18;
