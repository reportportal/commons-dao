-- Inserts into activities table 7 records
INSERT INTO activity(id, user_id, project_id, entity, action, details, creation_date, object_id)
VALUES (1, 1, 1, 'dashboard', 'dashboard_update', '{
  "type": "com.epam.ta.reportportal.entity.activity.ActivityDetails",
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

       (2, 1, 1, 'widget', 'widget_create', '{
  "type": "com.epam.ta.reportportal.entity.activity.ActivityDetails",
  "history": null,
  "objectName": "widget test"
}', now() - interval '20 day', 2),

       (3, 1, 1, 'filter', 'filter_create', '{
  "type": "com.epam.ta.reportportal.entity.activity.ActivityDetails",
  "history": null,
  "objectName": "filter test"
}', now() - interval '3 day', 3),
       (4, 2, 2, 'filter', 'filter_create', '{
  "type": "com.epam.ta.reportportal.entity.activity.ActivityDetails",
  "history": [],
  "objectName": "filter new test"
}', now() - interval '2 day', 4),

       (5, 2, 2, 'filter', 'filter_update', '{
  "type": "com.epam.ta.reportportal.entity.activity.ActivityDetails",
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

       (6, 2, 2, 'launch', 'start_launch', '{
  "type": "com.epam.ta.reportportal.entity.activity.ActivityDetails",
  "history": [],
  "objectName": "launch test"
}', now() - interval '2 day', 5),

       (7, 2, 2, 'launch', 'finish_launch', '{
  "type": "com.epam.ta.reportportal.entity.activity.ActivityDetails",
  "history": [],
  "objectName": "launch test"
}', now() - interval '1 day', 5);

alter sequence activity_id_seq restart with 8;