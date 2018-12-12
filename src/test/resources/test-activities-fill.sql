INSERT INTO project (id, name, project_type, creation_date, metadata) VALUES (1, 'superadmin_personal', 'PERSONAL', now(), '{"metadata": {"additional_info": ""}}');

INSERT INTO users (id, login, password, email, role, type, default_project_id, full_name, expired, metadata)
VALUES (1, 'superadmin', '5d39d85bddde885f6579f8121e11eba2', 'superadminemail@domain.com', 'ADMINISTRATOR', 'INTERNAL', 1, 'tester', FALSE, '{"metadata": {"last_login": "now"}}');

INSERT INTO project_user (user_id, project_id, project_role) VALUES (1, 1, 'PROJECT_MANAGER');

INSERT INTO project (id, name, project_type, creation_date, metadata) VALUES (2, 'default_personal', 'PERSONAL', now(), '{"metadata": {"additional_info": ""}}');

INSERT INTO users (id, login, password, email, role, type, default_project_id, full_name, expired, metadata)
VALUES (2, 'default', '3fde6bb0541387e4ebdadf7c2ff31123', 'defaultemail@domain.com', 'USER', 'INTERNAL', 2, 'tester', FALSE, '{"metadata": {"last_login": "now"}}');

INSERT INTO project_user (user_id, project_id, project_role) VALUES (2, 2, 'PROJECT_MANAGER');


INSERT INTO activity(id, user_id, project_id, entity, action, details, creation_date, object_id) VALUES
(1, 1, 1, 'DASHBOARD', 'dashboard_update', '{
  "type": "com.epam.ta.reportportal.entity.ActivityDetails",
  "history": [
    {
      "field": "name",
      "newValue": "After Name",
      "oldValue": "Before Name"
    },
    {
      "field": "description",
      "newValue": "After Desc",
      "oldValue": "Before Desc"
    }
  ],
  "objectName": "name"
}', now() - INTERVAL '12 day', 1),

(2,1,1, 'WIDGET', 'widget_create', '{
  "type": "com.epam.ta.reportportal.entity.ActivityDetails",
  "history": null,
  "objectName": "widget test"
}', now() - interval '20 day', 1),

(3, 1, 1, 'FILTER', 'filter_create', '{
  "type": "com.epam.ta.reportportal.entity.ActivityDetails",
  "history": null,
  "objectName": "filter test"
}', now() - interval '3 day', 1),

(4, 2, 2, 'FILTER', 'filter_update', '{
  "type": "com.epam.ta.reportportal.entity.ActivityDetails",
  "history": [
    {
      "field": "name",
      "newValue": "filter new test",
      "oldValue": "filter test"
    },
    {
      "field": "description",
      "newValue": "new",
      "oldValue": "old"
    }
  ],
  "objectName": "filter new test"
}', '2018-10-05 17:40:03.845000', 1);