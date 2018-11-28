DELETE
FROM issue_ticket;
DELETE
FROM issue;
ALTER SEQUENCE test_item_item_id_seq
  RESTART WITH 1;
DELETE
FROM test_item;
ALTER SEQUENCE ticket_id_seq
  RESTART WITH 1;
DELETE
FROM ticket;
ALTER SEQUENCE bug_tracking_system_id_seq
  RESTART WITH 1;
DELETE
FROM bug_tracking_system;