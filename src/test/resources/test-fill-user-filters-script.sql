INSERT INTO public.project (id, name, additional_info, project_type, creation_date, metadata) VALUES (1, 'default_personal', 'additional info', 'PERSONAL', '2018-11-02 10:35:57.411779', null);
INSERT INTO public.project (id, name, additional_info, project_type, creation_date, metadata) VALUES (2, 'superadmin_personal', 'another additional info', 'PERSONAL', '2018-11-02 10:35:57.411779', null);

INSERT INTO public.users (id, login, password, email, attachment, attachment_thumbnail, role, type, expired, default_project_id, full_name, metadata) VALUES (1, 'default', '3fde6bb0541387e4ebdadf7c2ff31123', 'defaultemail@domain.com', null, null, 'USER', 'INTERNAL', false, 1, 'tester', null);
INSERT INTO public.users (id, login, password, email, attachment, attachment_thumbnail, role, type, expired, default_project_id, full_name, metadata) VALUES (2, 'superadmin', '5d39d85bddde885f6579f8121e11eba2', 'superadminemail@domain.com', null, null, 'ADMINISTRATOR', 'INTERNAL', false, 2, 'tester', null);

INSERT INTO public.filter (id, name, project_id, target, description) VALUES (1, 'New_filter', 1, 'Launch', null);
INSERT INTO public.filter (id, name, project_id, target, description) VALUES (2, 'New_filter', 1, 'Launch', null);
INSERT INTO public.filter (id, name, project_id, target, description) VALUES (3, 'New_filter', 1, 'Launch', null);

INSERT INTO public.user_filter (id) VALUES (1);
INSERT INTO public.user_filter (id) VALUES (2);
INSERT INTO public.user_filter (id) VALUES (3);

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
