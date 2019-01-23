INSERT INTO integration_type (name, auth_flow, creation_date, group_type) --integration type id = 1
VALUES ('test integration type', 'LDAP', now(), 'AUTH');

INSERT INTO integration_type (name, auth_flow, creation_date, group_type) --integration type id = 2
VALUES ('RALLY', 'OAUTH', now(), 'BTS');

INSERT INTO integration_type (name, auth_flow, creation_date, group_type) --integration type id = 3
VALUES ('JIRA', 'BASIC', now(), 'BTS');

INSERT INTO integration_type (name, creation_date, group_type)            --integration type id = 4
VALUES ('EMAIL', now(), 'NOTIFICATION');

INSERT INTO integration (project_id, type, enabled, creation_date)        --integration id = 1
VALUES (1, 2, FALSE, now());

INSERT INTO integration (project_id, type, enabled, creation_date)        --integration id = 2
VALUES (1, 3, FALSE, now());

INSERT INTO integration (project_id, type, enabled, params)               --integration id = 3
VALUES (1, 4, false, '{"params": {"rules": [{"recipients": ["OWNER"], "fromAddress": "Auto_EPM-RPP_Notifications@epam.com", "launchStatsRule": "always"}]}}');

INSERT INTO integration (project_id, type, enabled, creation_date)        --integration id = 4
VALUES (2, 2, FALSE, now());

INSERT INTO integration (project_id, type, enabled, creation_date)        --integration id = 5
VALUES (2, 3, FALSE, now());

INSERT INTO integration (project_id, type, enabled, params)               --integration id = 6
VALUES (2, 4, false, '{"params": {"rules": [{"recipients": ["OWNER"], "fromAddress": "Auto_EPM-RPP_Notifications@epam.com", "launchStatsRule": "always"}]}}');

INSERT INTO integration (type, enabled, creation_date)                    --integration id = 7 (global email)
VALUES (4, false, now());

INSERT INTO integration (type, enabled, creation_date, params)            --integration id = 8 (global JIRA)
VALUES (3, false, now(), '{
  "params": {
    "url" : "bts.com",
    "project" : "bts_project"
  }
}');

INSERT INTO integration (project_id, type, enabled, creation_date, params)--integration id = 9 (superadmin project JIRA)
VALUES (1, 3, false, now(), '{
  "params": {
    "url" : "projectbts.com",
    "project" : "project"
  }
}');

INSERT INTO integration (project_id, type, enabled, creation_date, params)--integration id = 10 (superadmin project RALLY)
VALUES (1, 2, false, now(), '{
  "params": {
    "url" : "rallybts.com",
    "project" : "rallyproject"
  }
}');

INSERT INTO integration (type, enabled, creation_date, params)            --integration id = 11 (global RALLY)
VALUES (2, false, now(), '{
  "params": {
    "url" : "globalrally.com",
    "project" : "global_rally_project"
  }
}');
