INSERT INTO ticket (id, ticket_id, submitter, submit_date, bts_url, bts_project, url)
VALUES (1, 'ticket_id_1', 'superadmin', now(), 'jira.com', 'project', 'http://example.com/tickets/ticket_id_1'),
       (2, 'ticket_id_2', 'superadmin', now() - interval '2 day', 'jira.com', 'project', 'http://example.com/tickets/ticket_id_2'),
       (3, 'ticket_id_3', 'superadmin', now() - interval '4 day', 'jira.com', 'project', 'http://example.com/tickets/ticket_id_3');