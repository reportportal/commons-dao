alter sequence launch_id_seq restart with 1;
alter sequence test_item_item_id_seq restart with 1;

SELECT launches_init();

INSERT INTO public.launch(id, uuid, project_id, user_id, name, description, start_time, end_time, last_modified, mode, status)
VALUES (100, 'uuid', 2, 2, 'finished launch', 'description', now(), now(), now(), 'DEFAULT', 'FAILED');

INSERT INTO public.test_item(item_id, type, start_time, last_modified, has_children, has_retries, parent_id, launch_id)
VALUES (1, 'STEP', now(), now(), false, false, null, 100);

INSERT INTO public.test_item_results(result_id, status, end_time, duration)
VALUES (1, 'PASSED', now(), 1);

INSERT INTO public.test_item(item_id, type, start_time, last_modified, has_children, has_retries, parent_id, launch_id)
VALUES (2, 'STEP', now(), now(), false, true, null, 100);

INSERT INTO public.test_item_results(result_id, status, end_time, duration)
VALUES (2, 'FAILED', now(), 1);