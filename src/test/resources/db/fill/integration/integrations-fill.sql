INSERT INTO integration (id, project_id, type, enabled, creator, creation_date)
VALUES (7, 1, 3, FALSE, 'superadmin', now()),
       (8, 1, 4, FALSE, 'superadmin', now()),
       (9, 2, 3, FALSE, 'superadmin', now()),
       (10, 2, 4, FALSE, 'superadmin', now());

INSERT INTO integration (id, project_id, type, enabled, creator, params)
VALUES (11, 1, 2, false, 'superadmin', '{
  "params": {
    "rules": [
      {
        "recipients": [
          "OWNER"
        ],
        "fromAddress": "test@epam.com",
        "launchStatsRule": "always"
      }
    ]
  }
}'),
       (12, 2, 2, false, 'superadmin', '{
         "params": {
           "rules": [
             {
               "recipients": [
                 "OWNER"
               ],
               "fromAddress": "test@epam.com",
               "launchStatsRule": "always"
             }
           ]
         }
       }');

INSERT INTO integration (id, type, enabled, creator, creation_date, params) --integration id = 8 (global JIRA)
VALUES (13, 4, false, 'superadmin', now(), '{
  "params": {
    "url" : "bts.com",
    "project" : "bts_project"
  }
}');

INSERT INTO integration (id, project_id, type, enabled, creator, creation_date, params)--integration id = 9 (superadmin project JIRA)
VALUES (14, 1, 4, false, 'superadmin', now(), '{
  "params": {
    "url" : "projectbts.com",
    "project" : "project"
  }
}');

INSERT INTO integration (id, project_id, type, enabled, creator, creation_date, params)--integration id = 10 (superadmin project RALLY)
VALUES (15, 1, 3, false, 'superadmin', now(), '{
  "params": {
    "url" : "rallybts.com",
    "project" : "rallyproject"
  }
}');

INSERT INTO integration (id, type, enabled, creator, creation_date, params) --integration id = 11 (global RALLY)
VALUES (16, 3, false, 'superadmin', now(), '{
  "params": {
    "url" : "globalrally.com",
    "project" : "global_rally_project"
  }
}');

insert into integration(id, type, enabled, creator, creation_date)
values (17, 2, true, 'superadmin', now());

alter sequence integration_id_seq restart with 18;
