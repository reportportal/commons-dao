-- Generate 3 test user filters

INSERT INTO public.shareable_entity(id, shared, owner, project_id) VALUES (1, false, 'bla', 1);
INSERT INTO public.shareable_entity(id, shared, owner, project_id) VALUES (2, false, 'bla', 1);
INSERT INTO public.shareable_entity(id, shared, owner, project_id) VALUES (3, false, 'bla', 1);

INSERT INTO public.filter (id, name, target, description) VALUES (1, 'New_filter', 'Launch', null);
INSERT INTO public.filter (id, name, target, description) VALUES (2, 'New_filter', 'Launch', null);
INSERT INTO public.filter (id, name, target, description) VALUES (3, 'New_filter', 'Launch', null);

INSERT INTO public.filter_sort (id, filter_id, field, direction) VALUES (1, 1, 'name', 'ASC');
INSERT INTO public.filter_sort (id, filter_id, field, direction) VALUES (2, 2, 'name', 'ASC');
INSERT INTO public.filter_sort (id, filter_id, field, direction) VALUES (3, 3, 'name', 'ASC');

INSERT INTO public.filter_condition (id, filter_id, condition, value, search_criteria, negative) VALUES (1, 1, 'CONTAINS', 'Kek', 'name1', false);
INSERT INTO public.filter_condition (id, filter_id, condition, value, search_criteria, negative) VALUES (2, 1, 'CONTAINS', 'Kek', 'name2', false);
INSERT INTO public.filter_condition (id, filter_id, condition, value, search_criteria, negative) VALUES (3, 2, 'CONTAINS', 'Kek', 'name1', false);
INSERT INTO public.filter_condition (id, filter_id, condition, value, search_criteria, negative) VALUES (4, 2, 'CONTAINS', 'Kek', 'name2', false);
INSERT INTO public.filter_condition (id, filter_id, condition, value, search_criteria, negative) VALUES (5, 3, 'CONTAINS', 'Kek', 'name1', false);
INSERT INTO public.filter_condition (id, filter_id, condition, value, search_criteria, negative) VALUES (6, 3, 'CONTAINS', 'Kek', 'name2', false);

INSERT INTO public.acl_sid (id, principal, sid) VALUES (1, true, 'default');
INSERT INTO public.acl_sid (id, principal, sid) VALUES (2, true, 'superadmin');

INSERT INTO public.acl_class (id, class) VALUES (1, 'com.epam.ta.reportportal.entity.filter.UserFilter');

INSERT INTO public.acl_object_identity (id, object_id_class, object_id_identity, parent_object, owner_sid, entries_inheriting) VALUES (3, 1, '3', null, 1, true);
INSERT INTO public.acl_object_identity (id, object_id_class, object_id_identity, parent_object, owner_sid, entries_inheriting) VALUES (1, 1, '1', null, 1, true);
INSERT INTO public.acl_object_identity (id, object_id_class, object_id_identity, parent_object, owner_sid, entries_inheriting) VALUES (2, 1, '2', null, 1, true);

INSERT INTO public.acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) VALUES (3, 3, 0, 1, 1, true, false, false);
INSERT INTO public.acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) VALUES (4, 1, 0, 2, 1, true, false, false);
INSERT INTO public.acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) VALUES (5, 1, 1, 1, 1, true, false, false);
INSERT INTO public.acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) VALUES (6, 2, 0, 2, 1, true, false, false);
INSERT INTO public.acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) VALUES (7, 2, 1, 1, 1, true, false, false);
