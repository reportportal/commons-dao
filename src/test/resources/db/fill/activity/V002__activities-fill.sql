INSERT INTO activity(user_id, project_id, entity, action, details, creation_date, object_id) VALUES
(1, 1, 'DASHBOARD', 'dashboard_update', '{
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

(1,1, 'WIDGET', 'widget_create', '{
  "type": "com.epam.ta.reportportal.entity.ActivityDetails",
  "history": null,
  "objectName": "widget test"
}', now() - interval '20 day', 2),

(1, 1, 'FILTER', 'filter_create', '{
  "type": "com.epam.ta.reportportal.entity.ActivityDetails",
  "history": null,
  "objectName": "filter test"
}', now() - interval '3 day', 3),
(2, 2, 'FILTER', 'filter_create', '{
  "type": "com.epam.ta.reportportal.entity.ActivityDetails",
  "history": [],
  "objectName": "filter new test"
}', now() - interval '2 day', 4),

(2, 2, 'FILTER', 'filter_update', '{
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
}', now() - interval '1 day' - interval '4 hour', 4),

(2, 2, 'LAUNCH', 'start_launch', '{
  "type": "com.epam.ta.reportportal.entity.ActivityDetails",
  "history": [],
  "objectName": "launch test"
}', now() - interval '2 day', 5),

(2, 2, 'LAUNCH', 'finish_launch', '{
  "type": "com.epam.ta.reportportal.entity.ActivityDetails",
  "history": [],
  "objectName": "launch test"
}', now() - interval '1 day', 5);