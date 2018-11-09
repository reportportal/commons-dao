CREATE OR REPLACE FUNCTION user_project_init()
  RETURNS VOID AS
$BODY$
DECLARE
  defaultProject    BIGINT;
  superadminProject BIGINT;
  defaultId         BIGINT;
  superadmin        BIGINT;
BEGIN
  INSERT INTO issue_group (issue_group_id, issue_group) VALUES (1, 'TO_INVESTIGATE');
  INSERT INTO issue_group (issue_group_id, issue_group) VALUES (2, 'AUTOMATION_BUG');
  INSERT INTO issue_group (issue_group_id, issue_group) VALUES (3, 'PRODUCT_BUG');
  INSERT INTO issue_group (issue_group_id, issue_group) VALUES (4, 'NO_DEFECT');
  INSERT INTO issue_group (issue_group_id, issue_group) VALUES (5, 'SYSTEM_ISSUE');

  INSERT INTO issue_type (issue_group_id, locator, issue_name, abbreviation, hex_color)
  VALUES (1, 'ti001', 'To Investigate', 'TI', '#ffb743');
  INSERT INTO issue_type (issue_group_id, locator, issue_name, abbreviation, hex_color)
  VALUES (2, 'ab001', 'Automation Bug', 'AB', '#f7d63e');
  INSERT INTO issue_type (issue_group_id, locator, issue_name, abbreviation, hex_color)
  VALUES (3, 'pb001', 'Product Bug', 'PB', '#ec3900');
  INSERT INTO issue_type (issue_group_id, locator, issue_name, abbreviation, hex_color)
  VALUES (4, 'nd001', 'No Defect', 'ND', '#777777');
  INSERT INTO issue_type (issue_group_id, locator, issue_name, abbreviation, hex_color)
  VALUES (5, 'si001', 'System Issue', 'SI', '#0274d1');

  INSERT INTO attribute (name) VALUES ('job.interruptJobTime');
  INSERT INTO attribute (name) VALUES ('job.keepLogs');
  INSERT INTO attribute (name) VALUES ('job.keepScreenshots');
  INSERT INTO attribute (name) VALUES ('analyzer.minDocFreq');
  INSERT INTO attribute (name) VALUES ('analyzer.minTermFreq');
  INSERT INTO attribute (name) VALUES ('analyzer.minShouldMatch');
  INSERT INTO attribute (name) VALUES ('analyzer.numberOfLogLines');
  INSERT INTO attribute (name) VALUES ('analyzer.indexingRunning');
  INSERT INTO attribute (name) VALUES ('analyzer.isAutoAnalyzerEnabled');
  INSERT INTO attribute (name) VALUES ('analyzer.autoAnalyzerMode');
  INSERT INTO attribute (name) VALUES ('email.enabled');
  INSERT INTO attribute (name) VALUES ('email.from');

  -- Superadmin project and user
  INSERT INTO project (name, project_type, additional_info, creation_date)
  VALUES ('superadmin_personal', 'PERSONAL', 'another additional info', now());
  superadminProject := (SELECT currval(pg_get_serial_sequence('project', 'id')));

  INSERT INTO users (login, password, email, role, type, default_project_id, full_name, expired)
  VALUES ('superadmin',
          '5d39d85bddde885f6579f8121e11eba2',
          'superadminemail@domain.com',
          'ADMINISTRATOR',
          'INTERNAL',
          superadminProject,
          'tester',
          FALSE);
  superadmin := (SELECT currval(pg_get_serial_sequence('users', 'id')));

  INSERT INTO project_user (user_id, project_id, project_role)
  VALUES (superadmin, superadminProject, 'PROJECT_MANAGER');

  -- Default project and user
  INSERT INTO project (name, project_type, additional_info, creation_date)
  VALUES ('default_personal', 'PERSONAL', 'additional info', now());
  defaultProject := (SELECT currval(pg_get_serial_sequence('project', 'id')));

  INSERT INTO users (login, password, email, role, type, default_project_id, full_name, expired)
  VALUES ('default', '3fde6bb0541387e4ebdadf7c2ff31123', 'defaultemail@domain.com', 'USER', 'INTERNAL', defaultProject, 'tester', FALSE);
  defaultId := (SELECT currval(pg_get_serial_sequence('users', 'id')));

  INSERT INTO project_user (user_id, project_id, project_role)
  VALUES (defaultId, defaultProject, 'PROJECT_MANAGER');

  -- Project configurations

  INSERT INTO issue_type_project (project_id, issue_type_id)
  VALUES (superadminProject, 1),
         (superadminProject, 2),
         (superadminProject, 3),
         (superadminProject, 4),
         (superadminProject, 5),
         (defaultProject, 1),
         (defaultProject, 2),
         (defaultProject, 3),
         (defaultProject, 4),
         (defaultProject, 5);

  INSERT INTO project_attribute(project_id, attribute_id, value) VALUES (1, 1, '1 hour');
  INSERT INTO project_attribute(project_id, attribute_id, value) VALUES (1, 2, '2 weeks');
  INSERT INTO project_attribute(project_id, attribute_id, value) VALUES (1, 3, '2 weeks');
  INSERT INTO project_attribute(project_id, attribute_id, value) VALUES (2, 1, '1 hour');
  INSERT INTO project_attribute(project_id, attribute_id, value) VALUES (2, 2, '2 weeks');
  INSERT INTO project_attribute(project_id, attribute_id, value) VALUES (2, 3, '2 weeks');
END;
$BODY$
LANGUAGE plpgsql;
-- .;

SELECT user_project_init();

DROP FUNCTION IF EXISTS user_project_init();