INSERT INTO bug_tracking_system (id, url, type, bts_project, project_id)
VALUES (1, 'http://example.com', 'JIRA', 'test_project', 1);

INSERT INTO ticket (id, ticket_id, submitter_id, submit_date, bts_id, url)
VALUES (1, 'ticket_id_1', 1, now(), 1, 'http://example.com/tickets/ticket_id_1'),
       (2, 'ticket_id_2', 1, now() - interval '2 day', 1, 'http://example.com/tickets/ticket_id_2'),
       (3, 'ticket_id_3', 1, now() - interval '4 day', 1, 'http://example.com/tickets/ticket_id_3');