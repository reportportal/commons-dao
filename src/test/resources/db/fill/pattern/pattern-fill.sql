INSERT INTO pattern_template (name, value, type, enabled, project_id) VALUES ('name1', 'qwe', 'STRING', true, 1);
INSERT INTO pattern_template (name, value, type, enabled, project_id) VALUES ('name2', 'qw', 'STRING', true, 1);
INSERT INTO pattern_template (name, value, type, enabled, project_id) VALUES ('name3', 'qwee', 'STRING', false, 1);
INSERT INTO pattern_template (name, value, type, enabled, project_id) VALUES ('name4', '[a-z]{2,4}', 'REGEX', false, 1);
INSERT INTO pattern_template (name, value, type, enabled, project_id) VALUES ('name5_p2', '^*+', 'REGEX', true, 2);