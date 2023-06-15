-- Inserts into activities table 7 records
insert into activity(id, action, created_at, details, object_id, object_name, object_type, priority,
 project_id, subject_id, subject_name, subject_type)
values (1, 'UPDATE', now() - interval '12 day', '{
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
  ]
}', 1, 'name', 'DASHBOARD', 'LOW', 1, 1, 'superadmin', 'USER'),

(2, 'CREATE', now() - INTERVAL '20 day', '{
  "type": "com.epam.ta.reportportal.entity.activity.ActivityDetails",
  "history": null
}', 2, 'widget test', 'WIDGET', 'LOW', 1, 1,'superadmin', 'USER'),

(3, 'CREATE', now() - INTERVAL '3 day', '{
  "type": "com.epam.ta.reportportal.entity.activity.ActivityDetails",
  "history": null
}', 3, 'filter test', 'FILTER', 'LOW', 1, 1, 'superadmin', 'USER'),

(4, 'CREATE', now() - INTERVAL '2 day', '{
  "type": "com.epam.ta.reportportal.entity.activity.ActivityDetails",
  "history": []
}', 4, 'filter new test', 'FILTER', 'LOW', 2, 2, 'user', 'USER'),

(5, 'UPDATE', now() - interval '1 day' - interval '4 hour', '{
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
  ]
}', 4, 'filter new test', 'FILTER', 'LOW', 2, 2, 'user', 'USER'),

(6, 'START', now() - interval '2 day', '{
  "type": "com.epam.ta.reportportal.entity.activity.ActivityDetails",
  "history": []
}', 5, 'launch test', 'LAUNCH', 'LOW', 2, 2, 'user', 'USER'),

(7, 'FINISH', now() - interval '1 day', '{
  "type": "com.epam.ta.reportportal.entity.activity.ActivityDetails",
  "history": []
}', 5, 'launch test', 'LAUNCH', 'LOW', 2, 2, 'user', 'USER');

alter sequence activity_id_seq restart with 8;