-- Inserts into activities table 7 records
ALTER SEQUENCE launch_id_seq RESTART WITH 1;
DELETE FROM launch CASCADE;
DELETE FROM item_attribute;
DELETE FROM bug_tracking_system CASCADE;
DELETE FROM ticket CASCADE;
ALTER SEQUENCE test_item_item_id_seq RESTART WITH 1;
DELETE FROM test_item CASCADE;
DELETE FROM issue CASCADE;
ALTER SEQUENCE statistics_s_id_seq RESTART WITH 1;
DELETE FROM statistics CASCADE;
ALTER SEQUENCE statistics_field_sf_id_seq RESTART WITH 1;
DELETE FROM statistics_field CASCADE;
ALTER SEQUENCE activity_id_seq RESTART WITH 1;
DELETE FROM activity CASCADE;

INSERT INTO activity(user_id, project_id, entity, action, details, creation_date, object_id) VALUES
(1, 1, 'DASHBOARD', 'dashboard_update', '{
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

(1,1, 'WIDGET', 'widget_create', '{
  "type": "com.epam.ta.reportportal.entity.activity.ActivityDetails",
  "history": null,
  "objectName": "widget test"
}', now() - interval '20 day', 2),

(1, 1, 'FILTER', 'filter_create', '{
  "type": "com.epam.ta.reportportal.entity.activity.ActivityDetails",
  "history": null,
  "objectName": "filter test"
}', now() - interval '3 day', 3),
(2, 2, 'FILTER', 'filter_create', '{
  "type": "com.epam.ta.reportportal.entity.activity.ActivityDetails",
  "history": [],
  "objectName": "filter new test"
}', now() - interval '2 day', 4),

(2, 2, 'FILTER', 'filter_update', '{
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

(2, 2, 'LAUNCH', 'start_launch', '{
  "type": "com.epam.ta.reportportal.entity.activity.ActivityDetails",
  "history": [],
  "objectName": "launch test"
}', now() - interval '2 day', 5),

(2, 2, 'LAUNCH', 'finish_launch', '{
  "type": "com.epam.ta.reportportal.entity.activity.ActivityDetails",
  "history": [],
  "objectName": "launch test"
}', now() - interval '1 day', 5);