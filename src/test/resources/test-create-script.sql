CREATE TYPE PROJECT_ROLE_ENUM AS ENUM ('OPERATOR', 'CUSTOMER', 'MEMBER', 'PROJECT_MANAGER');

CREATE TYPE STATUS_ENUM AS ENUM ('IN_PROGRESS', 'PASSED', 'FAILED', 'STOPPED', 'SKIPPED', 'INTERRUPTED', 'RESETED', 'CANCELLED');

CREATE TYPE LAUNCH_MODE_ENUM AS ENUM ('DEFAULT', 'DEBUG');

CREATE TYPE AUTH_TYPE_ENUM AS ENUM ('OAUTH', 'NTLM', 'APIKEY', 'BASIC');

CREATE TYPE ACCESS_TOKEN_TYPE_ENUM AS ENUM ('OAUTH', 'NTLM', 'APIKEY', 'BASIC');

CREATE TYPE ACTIVITY_ENTITY_ENUM AS ENUM ('LAUNCH', 'ITEM');

CREATE TYPE TEST_ITEM_TYPE_ENUM AS ENUM ('SUITE', 'STORY', 'TEST', 'SCENARIO', 'STEP', 'BEFORE_CLASS', 'BEFORE_GROUPS', 'BEFORE_METHOD',
  'BEFORE_SUITE', 'BEFORE_TEST', 'AFTER_CLASS', 'AFTER_GROUPS', 'AFTER_METHOD', 'AFTER_SUITE', 'AFTER_TEST');

CREATE TYPE ISSUE_GROUP_ENUM AS ENUM ('PRODUCT_BUG', 'AUTOMATION_BUG', 'SYSTEM_ISSUE', 'TO_INVESTIGATE', 'NO_DEFECT');

CREATE TYPE INTEGRATION_AUTH_FLOW_ENUM AS ENUM ('OAUTH', 'BASIC', 'TOKEN', 'FORM', 'LDAP');

CREATE TYPE INTEGRATION_GROUP_ENUM AS ENUM ('BTS', 'NOTIFICATION');

CREATE TYPE FILTER_CONDITION_ENUM AS ENUM ('EQUALS', 'NOT_EQUALS', 'CONTAINS', 'EXISTS', 'IN', 'HAS', 'GREATER_THAN', 'GREATER_THAN_OR_EQUALS',
  'LOWER_THAN', 'LOWER_THAN_OR_EQUALS', 'BETWEEN');

CREATE TYPE PASSWORD_ENCODER_TYPE AS ENUM ('PLAIN', 'SHA', 'LDAP_SHA', 'MD4', 'MD5');

CREATE TYPE SORT_DIRECTION_ENUM AS ENUM ('ASC', 'DESC');

CREATE EXTENSION ltree;

CREATE TABLE server_settings (
  id    SMALLSERIAL CONSTRAINT server_settings_id PRIMARY KEY,
  key   VARCHAR NOT NULL UNIQUE,
  value VARCHAR
);

---------------------------- Project and users ------------------------------------
CREATE TABLE project (
  id              BIGSERIAL CONSTRAINT project_pk PRIMARY KEY,
  name            VARCHAR                 NOT NULL UNIQUE,
  additional_info VARCHAR,
  creation_date   TIMESTAMP DEFAULT now() NOT NULL,
  metadata        JSONB                   NULL
);

CREATE TABLE demo_data_postfix (
  id         BIGSERIAL CONSTRAINT demo_data_postfix_pk PRIMARY KEY,
  data       VARCHAR NOT NULL,
  project_id BIGINT REFERENCES project (id) ON DELETE CASCADE
);

CREATE TABLE user_creation_bid (
  id BIGSERIAL CONSTRAINT user_creation_bid_pk PRIMARY KEY,
  last_modified TIMESTAMP DEFAULT now(),
  email VARCHAR NOT NULL UNIQUE,
  default_project_id BIGINT REFERENCES project(id) ON DELETE CASCADE,
  role VARCHAR NOT NULL
);

CREATE TABLE users (
  id                   BIGSERIAL CONSTRAINT users_pk PRIMARY KEY,
  login                VARCHAR NOT NULL UNIQUE,
  password             VARCHAR NULL,
  email                VARCHAR NOT NULL,
  attachment           VARCHAR NULL,
  attachment_thumbnail VARCHAR NULL,
  role                 VARCHAR NOT NULL,
  type                 VARCHAR NOT NULL,
  expired              BOOLEAN NOT NULL,
  default_project_id   BIGINT REFERENCES project (id) ON DELETE CASCADE,
  full_name            VARCHAR NOT NULL,
  metadata             JSONB   NULL DEFAULT '{}'
);

CREATE TABLE user_config (
  id           BIGSERIAL CONSTRAINT user_config_pk PRIMARY KEY,
  user_id      BIGINT REFERENCES users (id) ON DELETE CASCADE,
  project_id   BIGINT REFERENCES project (id) ON DELETE CASCADE,
  proposedRole VARCHAR,
  projectRole  VARCHAR
);

CREATE TABLE project_user (
  user_id      BIGINT REFERENCES users (id) ON DELETE CASCADE,
  project_id   BIGINT REFERENCES project (id) ON DELETE CASCADE,
  CONSTRAINT users_project_pk PRIMARY KEY (user_id, project_id),
  project_role PROJECT_ROLE_ENUM NOT NULL
);

CREATE TABLE oauth_access_token (
  user_id    BIGINT REFERENCES users (id) ON DELETE CASCADE,
  token      VARCHAR                NOT NULL,
  token_type ACCESS_TOKEN_TYPE_ENUM NOT NULL,
  CONSTRAINT access_tokens_pk PRIMARY KEY (user_id, token_type)
);

CREATE TABLE oauth_registration (
  id                           VARCHAR(64) PRIMARY KEY,
  client_id                    VARCHAR(128) NOT NULL UNIQUE,
  client_secret                VARCHAR(256),
  client_auth_method           VARCHAR(64)  NOT NULL,
  auth_grant_type              VARCHAR(64),
  redirect_uri_template        VARCHAR(256),

  authorization_uri            VARCHAR(256),
  token_uri                    VARCHAR(256),

  user_info_endpoint_uri       VARCHAR(256),
  user_info_endpoint_name_attr VARCHAR(256),

  jwk_set_uri                  VARCHAR(256),
  client_name                  VARCHAR(128)
);

CREATE TABLE oauth_registration_scope (
  id                    SERIAL CONSTRAINT oauth_registration_scope_pk PRIMARY KEY,
  oauth_registration_fk VARCHAR(128) REFERENCES oauth_registration (id) ON DELETE CASCADE,
  scope                 VARCHAR(256),
  CONSTRAINT oauth_registration_scope_unique UNIQUE (scope, oauth_registration_fk)
);

CREATE TABLE oauth_registration_restriction (
  id                    SERIAL CONSTRAINT oauth_registration_restriction_pk PRIMARY KEY,
  oauth_registration_fk VARCHAR(128) REFERENCES oauth_registration (id) ON DELETE CASCADE,
  type                  VARCHAR(256) NOT NULL,
  value                 VARCHAR(256) NOT NULL,
  CONSTRAINT oauth_registration_restriction_unique UNIQUE (type, value, oauth_registration_fk)
);
-----------------------------------------------------------------------------------


------------------------------ Project configurations ------------------------------
CREATE TABLE project_analyzer_configuration (
  id                  BIGSERIAL CONSTRAINT project_analyzer_configuration_pk PRIMARY KEY,
  min_doc_freq        INTEGER,
  min_term_freq       INTEGER,
  min_should_match    INTEGER,
  number_of_log_lines INTEGER,
  indexing_running    BOOLEAN,
  enabled             BOOLEAN,
  analyzerMode        VARCHAR(64)
);

CREATE TABLE project_email_configuration (
  id         BIGSERIAL CONSTRAINT project_email_configuration_pk PRIMARY KEY,
  enabled    BOOLEAN DEFAULT FALSE NOT NULL,
  email_from VARCHAR(256)          NOT NULL
);

CREATE TABLE email_sender_case (
  id                      BIGSERIAL CONSTRAINT email_sender_case_pk PRIMARY KEY,
  sendCase                VARCHAR(64),
  project_email_config_id BIGINT REFERENCES project_email_configuration (id) ON DELETE CASCADE
);

CREATE TABLE recipients (
  email_sender_case_id BIGINT REFERENCES email_sender_case (id) ON DELETE CASCADE,
  recipient            VARCHAR(256)
);

CREATE TABLE project_configuration (
  id                         BIGINT CONSTRAINT project_configuration_pk PRIMARY KEY REFERENCES project (id) ON DELETE CASCADE UNIQUE,
  project_type               VARCHAR(128)               NOT NULL,
  interrupt_timeout          VARCHAR(128)               NOT NULL,
  keep_logs_interval         VARCHAR(128)               NOT NULL,
  keep_screenshots_interval  VARCHAR(128)               NOT NULL,
  project_analyzer_config_id BIGINT REFERENCES project_analyzer_configuration (id) ON DELETE CASCADE,
  metadata                   JSONB                      NULL,
  project_email_config_id    BIGINT REFERENCES project_email_configuration (id) ON DELETE CASCADE UNIQUE,
  created_on                 TIMESTAMP DEFAULT now()    NOT NULL
);
-----------------------------------------------------------------------------------


------------------------------ Bug tracking systems ------------------------------
CREATE TABLE bug_tracking_system (
  id          BIGSERIAL CONSTRAINT bug_tracking_system_pk PRIMARY KEY,
  url         VARCHAR                                          NOT NULL,
  type        VARCHAR                                          NOT NULL,
  bts_project VARCHAR                                          NOT NULL,
  project_id  BIGINT REFERENCES project (id) ON DELETE CASCADE NOT NULL,
  CONSTRAINT unique_bts UNIQUE (url, type, bts_project, project_id)
);

CREATE TABLE defect_form_field (
  id                     BIGSERIAL CONSTRAINT defect_form_field_pk PRIMARY KEY,
  bug_tracking_system_id BIGINT REFERENCES bug_tracking_system (id) ON DELETE CASCADE,
  field_id               VARCHAR NOT NULL,
  type                   VARCHAR NOT NULL,
  required               BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE defect_field_allowed_value (
  id                BIGSERIAL CONSTRAINT defect_field_allowed_value_pk PRIMARY KEY,
  defect_form_field BIGINT REFERENCES defect_form_field (id) ON DELETE CASCADE,
  value_id          VARCHAR NOT NULL,
  value_name        VARCHAR NOT NULL
);

CREATE TABLE defect_form_field_value (
  id     BIGINT REFERENCES defect_form_field (id) ON DELETE CASCADE,
  values VARCHAR NOT NULL
);

-----------------------------------------------------------------------------------


-------------------------- Integrations -----------------------------
CREATE TABLE integration_type (
  id            SERIAL CONSTRAINT integration_type_pk PRIMARY KEY,
  name          VARCHAR(128)               NOT NULL,
  auth_flow     INTEGRATION_AUTH_FLOW_ENUM NOT NULL,
  creation_date TIMESTAMP DEFAULT now()    NOT NULL,
  group_type    INTEGRATION_GROUP_ENUM     NOT NULL,
  details       JSONB                      NULL
);

CREATE TABLE integration (
  id            SERIAL CONSTRAINT integration_pk PRIMARY KEY,
  project_id    BIGINT REFERENCES project (id) ON DELETE CASCADE,
  type          INTEGER REFERENCES integration_type (id) ON DELETE CASCADE,
  enabled       BOOLEAN,
  params        JSONB                   NULL,
  creation_date TIMESTAMP DEFAULT now() NOT NULL
);

-------------------------------- LDAP configurations ------------------------------
CREATE TABLE ldap_synchronization_attributes
(
  id        BIGSERIAL CONSTRAINT ldap_synchronization_attributes_pk PRIMARY KEY,
  email     VARCHAR(256) UNIQUE,
  full_name VARCHAR(256),
  photo     VARCHAR(128)
);

CREATE TABLE active_directory_config
(
  id                 BIGINT CONSTRAINT active_directory_config_pk PRIMARY KEY REFERENCES integration (id) ON DELETE CASCADE UNIQUE,
  url                VARCHAR(256),
  base_dn            VARCHAR(256),
  sync_attributes_id BIGINT REFERENCES ldap_synchronization_attributes (id) ON DELETE CASCADE,
  domain             VARCHAR(256)
);

CREATE TABLE ldap_config
(
  id                  BIGINT CONSTRAINT ldap_config_pk PRIMARY KEY REFERENCES integration (id) ON DELETE CASCADE UNIQUE,
  url                 VARCHAR(256),
  base_dn             VARCHAR(256),
  sync_attributes_id  BIGINT REFERENCES ldap_synchronization_attributes (id) ON DELETE CASCADE,
  user_dn_pattern     VARCHAR(256),
  user_search_filter  VARCHAR(256),
  group_search_base   VARCHAR(256),
  group_search_filter VARCHAR(256),
  password_attributes VARCHAR(256),
  manager_dn          VARCHAR(256),
  manager_password    VARCHAR(256),
  passwordEncoderType PASSWORD_ENCODER_TYPE
);

CREATE TABLE auth_config (
  id                         VARCHAR CONSTRAINT auth_config_pk PRIMARY KEY,
  ldap_config_id             BIGINT REFERENCES ldap_config (id) ON DELETE CASCADE,
  active_directory_config_id BIGINT REFERENCES active_directory_config (id) ON DELETE CASCADE
);

-----------------------------------------------------------------------------------

-------------------------- Dashboards, widgets, user filters -----------------------------

CREATE TABLE filter (
  id          BIGSERIAL CONSTRAINT filter_pk PRIMARY KEY,
  name        VARCHAR                        NOT NULL,
  project_id  BIGINT REFERENCES project (id) NOT NULL,
  target      VARCHAR                        NOT NULL,
  description VARCHAR
);

CREATE TABLE user_filter (
  id BIGINT NOT NULL CONSTRAINT user_filter_pk PRIMARY KEY CONSTRAINT user_filter_id_fk REFERENCES filter (id)
);

CREATE TABLE filter_condition (
  id        BIGSERIAL CONSTRAINT filter_condition_pk PRIMARY KEY,
  filter_id BIGINT REFERENCES user_filter (id) ON DELETE CASCADE,
  condition FILTER_CONDITION_ENUM NOT NULL,
  value     VARCHAR               NOT NULL,
  field     VARCHAR               NOT NULL,
  negative  BOOLEAN               NOT NULL
);

CREATE TABLE filter_sort (
  id        BIGSERIAL CONSTRAINT filter_sort_pk PRIMARY KEY,
  filter_id BIGINT REFERENCES user_filter (id) ON DELETE CASCADE,
  field     VARCHAR             NOT NULL,
  direction SORT_DIRECTION_ENUM NOT NULL DEFAULT 'ASC'
);

CREATE TABLE dashboard (
  id            SERIAL CONSTRAINT dashboard_pk PRIMARY KEY,
  name          VARCHAR                 NOT NULL,
  description   VARCHAR,
  project_id    INTEGER REFERENCES project (id) ON DELETE CASCADE,
  creation_date TIMESTAMP DEFAULT now() NOT NULL,
  CONSTRAINT unq_name_project UNIQUE (name, project_id)
  -- acl
);

CREATE TABLE widget (
  id          BIGSERIAL CONSTRAINT widget_id PRIMARY KEY,
  name        VARCHAR NOT NULL,
  description VARCHAR,
  widget_type VARCHAR NOT NULL,
  items_count SMALLINT,
  project_id  BIGINT REFERENCES project (id) ON DELETE CASCADE
);

CREATE TABLE content_field (
  id    BIGINT REFERENCES widget (id) ON DELETE CASCADE,
  field VARCHAR NOT NULL
);

CREATE TABLE widget_option (
  id        BIGSERIAL CONSTRAINT widget_option_pk PRIMARY KEY,
  widget_id BIGINT REFERENCES widget (id) ON DELETE CASCADE,
  option    VARCHAR NOT NULL,
  value     VARCHAR NOT NULL
);

CREATE TABLE dashboard_widget (
  dashboard_id      INTEGER REFERENCES dashboard (id) ON DELETE CASCADE,
  widget_id         INTEGER REFERENCES widget (id) ON DELETE CASCADE,
  widget_name       VARCHAR NOT NULL, -- make it as reference ??
  widget_width      INT     NOT NULL,
  widget_height     INT     NOT NULL,
  widget_position_x INT     NOT NULL,
  widget_position_y INT     NOT NULL,
  CONSTRAINT dashboard_widget_pk PRIMARY KEY (dashboard_id, widget_id),
  CONSTRAINT widget_on_dashboard_unq UNIQUE (dashboard_id, widget_name)
);

CREATE TABLE widget_filter (
  widget_id   BIGINT REFERENCES widget (id) ON DELETE CASCADE         NOT NULL,
  filter_id   BIGINT REFERENCES user_filter (id) ON DELETE CASCADE    NOT NULL,
  CONSTRAINT widget_filter_pk PRIMARY KEY (widget_id, filter_id)
);
-----------------------------------------------------------------------------------


--------------------------- Launches, items, logs --------------------------------------

CREATE TABLE launch (
  id                   BIGSERIAL CONSTRAINT launch_pk PRIMARY KEY,
  uuid                 VARCHAR                                                             NOT NULL,
  project_id           BIGINT REFERENCES project (id) ON DELETE CASCADE                    NOT NULL,
  user_id              BIGINT REFERENCES users (id) ON DELETE SET NULL,
  name                 VARCHAR(256)                                                        NOT NULL,
  description          TEXT,
  start_time           TIMESTAMP                                                           NOT NULL,
  end_time             TIMESTAMP,
  number               INTEGER                                                             NOT NULL,
  last_modified        TIMESTAMP DEFAULT now()                                             NOT NULL,
  mode                 LAUNCH_MODE_ENUM                                                    NOT NULL,
  status               STATUS_ENUM                                                         NOT NULL,
  email_sender_case_id BIGINT REFERENCES email_sender_case (id) ON DELETE CASCADE,
  CONSTRAINT unq_name_number UNIQUE (NAME, number, project_id, uuid)
);

CREATE TABLE launch_tag (
  id                   BIGSERIAL CONSTRAINT launch_tag_pk PRIMARY KEY,
  value                TEXT NOT NULL,
  email_sender_case_id BIGINT REFERENCES email_sender_case (id) ON DELETE CASCADE,
  launch_id            BIGINT REFERENCES launch (id) ON DELETE CASCADE
);

CREATE TABLE test_item (
  item_id       BIGSERIAL CONSTRAINT test_item_pk PRIMARY KEY,
  name          VARCHAR(256),
  type          TEST_ITEM_TYPE_ENUM NOT NULL,
  start_time    TIMESTAMP           NOT NULL,
  description   TEXT,
  last_modified TIMESTAMP           NOT NULL,
  path          LTREE,
  unique_id     VARCHAR(256),
  parent_id     BIGINT REFERENCES test_item (item_id) ON DELETE CASCADE,
  retry_of      BIGINT REFERENCES test_item (item_id) ON DELETE CASCADE,
  launch_id     BIGINT REFERENCES launch (id) ON DELETE CASCADE
);

CREATE TABLE test_item_results (
  result_id BIGINT CONSTRAINT test_item_results_pk PRIMARY KEY REFERENCES test_item (item_id) ON DELETE CASCADE UNIQUE,
  status    STATUS_ENUM NOT NULL,
  end_time  TIMESTAMP,
  duration  DOUBLE PRECISION
);

CREATE INDEX path_gist_idx
  ON test_item
  USING GIST (path);
CREATE INDEX path_idx
  ON test_item
  USING BTREE (path);

CREATE TABLE parameter (
  item_id BIGINT REFERENCES test_item (item_id) ON DELETE CASCADE,
  key     VARCHAR NOT NULL,
  value   VARCHAR NOT NULL
);

CREATE TABLE item_tag (
  id      SERIAL CONSTRAINT item_tag_pk PRIMARY KEY,
  value   TEXT,
  item_id BIGINT REFERENCES test_item (item_id) ON DELETE CASCADE
);


CREATE TABLE log (
  id                   BIGSERIAL CONSTRAINT log_pk PRIMARY KEY,
  log_time             TIMESTAMP                                                NOT NULL,
  log_message          TEXT                                                     NOT NULL,
  item_id              BIGINT REFERENCES test_item (item_id) ON DELETE CASCADE  NOT NULL,
  last_modified        TIMESTAMP                                                NOT NULL,
  log_level            INTEGER                                                  NOT NULL,
  attachment           TEXT,
  attachment_thumbnail TEXT,
  content_type         TEXT
);

CREATE TABLE activity (
  id            BIGSERIAL CONSTRAINT activity_pk PRIMARY KEY,
  user_id       BIGINT REFERENCES users (id) ON DELETE CASCADE           NOT NULL,
  project_id    BIGINT REFERENCES project (id) ON DELETE CASCADE         NOT NULL,
  entity        ACTIVITY_ENTITY_ENUM                                     NOT NULL,
  action        VARCHAR(128)                                             NOT NULL,
  details       JSONB                                                    NULL,
  creation_date TIMESTAMP                                                NOT NULL
);

----------------------------------------------------------------------------------------


------------------------------ Issue ticket many to many ------------------------------

CREATE TABLE issue_group (
  issue_group_id SMALLSERIAL CONSTRAINT issue_group_pk PRIMARY KEY,
  issue_group    ISSUE_GROUP_ENUM NOT NULL
);

CREATE TABLE issue_type (
  id             BIGSERIAL CONSTRAINT issue_type_pk PRIMARY KEY,
  issue_group_id SMALLINT REFERENCES issue_group (issue_group_id) ON DELETE CASCADE,
  locator        VARCHAR(64), -- issue string identifier
  issue_name     VARCHAR(256), -- issue full name
  abbreviation   VARCHAR(64), -- issue abbreviation
  hex_color      VARCHAR(7)
);

CREATE TABLE statistics (
  s_id      BIGSERIAL NOT NULL CONSTRAINT pk_statistics PRIMARY KEY,
  s_field   VARCHAR   NOT NULL,
  s_counter INT DEFAULT 0,
  item_id   BIGINT REFERENCES test_item (item_id) ON DELETE CASCADE,
  launch_id BIGINT REFERENCES launch (id) ON DELETE CASCADE,

  CONSTRAINT unique_status_item UNIQUE (s_field, item_id),
  CONSTRAINT unique_status_launch UNIQUE (s_field, launch_id),
  CHECK (statistics.s_counter >= 0)
);

CREATE TABLE issue_type_project (
  project_id BIGINT REFERENCES project(id),
  issue_type_id    BIGINT REFERENCES issue_type(id),
  CONSTRAINT issue_type_project_pk PRIMARY KEY (project_id, issue_type_id)
);
----------------------------------------------------------------------------------------


CREATE TABLE issue (
  issue_id          BIGINT CONSTRAINT issue_pk PRIMARY KEY REFERENCES test_item_results (result_id) ON DELETE CASCADE,
  issue_type        BIGINT REFERENCES issue_type (id),
  issue_description TEXT,
  auto_analyzed     BOOLEAN DEFAULT FALSE,
  ignore_analyzer   BOOLEAN DEFAULT FALSE
);

CREATE TABLE ticket (
  id           BIGSERIAL CONSTRAINT ticket_pk PRIMARY KEY,
  ticket_id    VARCHAR(64)                                                   NOT NULL UNIQUE,
  submitter_id BIGINT REFERENCES users (id)                                  NOT NULL,
  submit_date  TIMESTAMP DEFAULT now()                                       NOT NULL,
  bts_id       INTEGER REFERENCES bug_tracking_system (id) ON DELETE CASCADE NOT NULL,
  url          VARCHAR(256)                                                  NOT NULL
);

CREATE TABLE issue_ticket (
  issue_id  BIGINT REFERENCES issue (issue_id),
  ticket_id BIGINT REFERENCES ticket (id),
  CONSTRAINT issue_ticket_pk PRIMARY KEY (issue_id, ticket_id)
);

CREATE EXTENSION IF NOT EXISTS tablefunc;
------- Functions and triggers -----------------------

CREATE OR REPLACE FUNCTION has_child(path_value ltree)
  RETURNS BOOLEAN
AS $$
DECLARE
  hasChilds BOOLEAN;
BEGIN
  SELECT EXISTS(SELECT 1 FROM test_item t WHERE t.path <@ path_value
                                            AND t.path != path_value LIMIT 1) INTO hasChilds;

  RETURN hasChilds;
END;
$$
LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION merge_launch(LaunchId BIGINT)
  RETURNS INTEGER
AS $$
DECLARE TargetTestItemCursor CURSOR (id BIGINT, lvl int) FOR
  select distinct on (unique_id) unique_id, item_id
  from test_item
  where test_item.launch_id = id
    and nlevel(test_item.path) = lvl
    and has_child(test_item.path);

  DECLARE MergingTestItemCursor CURSOR (uniqueId VARCHAR, lvl int, launchId BIGINT) FOR
  select item_id, path as path_value
  from test_item
  where test_item.unique_id = uniqueId
    and nlevel(test_item.path) = lvl
    and test_item.launch_id = launchId;

  DECLARE TargetTestItemField  RECORD;
  DECLARE MergingTestItemField RECORD;
  DECLARE maxLevel             BIGINT;
  DECLARE firstItemId          VARCHAR;
  DECLARE parentItemId         BIGINT;
  DECLARE concatenated_descr   TEXT;
BEGIN
  maxLevel := (SELECT MAX(nlevel(path)) FROM test_item WHERE launch_id = LaunchId);

  FOR i IN 1..maxLevel
  loop

    OPEN TargetTestItemCursor(LaunchId, i);

    LOOP
      FETCH TargetTestItemCursor INTO TargetTestItemField;

      EXIT WHEN NOT FOUND;

      firstItemId := TargetTestItemField.unique_id;
      parentItemId := TargetTestItemField.item_id;

      EXIT WHEN firstItemId ISNULL;

      SELECT string_agg(description, chr(10)) INTO concatenated_descr
      FROM test_item
      WHERE test_item.unique_id = firstItemId
        AND nlevel(test_item.path) = i
        AND test_item.launch_id = LaunchId;

      UPDATE test_item SET description = concatenated_descr WHERE test_item.item_id = parentItemId;

      UPDATE test_item
      SET start_time = (SELECT min(start_time)
                        from test_item
                        WHERE test_item.unique_id = firstItemId
                          AND nlevel(test_item.path) = i
                          AND test_item.launch_id = LaunchId)
      WHERE test_item.item_id = parentItemId;

      UPDATE test_item_results
      SET end_time = (SELECT max(end_time)
                      from test_item
                             JOIN test_item_results result on test_item.item_id = result.result_id
                      WHERE test_item.unique_id = firstItemId
                        AND nlevel(test_item.path) = i
                        AND test_item.launch_id = LaunchId)
      WHERE test_item_results.result_id = parentItemId;

      INSERT INTO statistics (s_field, item_id, launch_id, s_counter)
      select s_field, parentItemId, null, sum(s_counter)
      from statistics
             join test_item ti on statistics.item_id = ti.item_id
      where ti.unique_id = firstItemId
      group by s_field
      ON CONFLICT ON CONSTRAINT unique_status_item
                                DO UPDATE
                                  SET
                                    s_counter = EXCLUDED.s_counter;

      IF exists(select 1
                from test_item_results
                       join test_item t on test_item_results.result_id = t.item_id
                where test_item_results.status != 'PASSED'
                  and t.unique_id = firstItemId
                  and nlevel(t.path) = i
                  and t.launch_id = LaunchId
                limit 1)
      then
        UPDATE test_item_results SET status = 'FAILED' WHERE test_item_results.result_id = parentItemId;
      end if;

      OPEN MergingTestItemCursor(TargetTestItemField.unique_id, i, LaunchId);

      LOOP

        FETCH MergingTestItemCursor INTO MergingTestItemField;

        EXIT WHEN NOT FOUND;

        IF has_child(MergingTestItemField.path_value)
        THEN

          UPDATE test_item
          SET parent_id = parentItemId
          WHERE test_item.path <@ MergingTestItemField.path_value
            AND test_item.path != MergingTestItemField.path_value
            AND nlevel(test_item.path) = i + 1;
          DELETE
          from test_item
          where test_item.path = MergingTestItemField.path_value
            and test_item.item_id != parentItemId;

        end if;

      end loop;

      CLOSE MergingTestItemCursor;

    end loop;

    CLOSE TargetTestItemCursor;

  end loop;

  INSERT INTO statistics (s_field, launch_id, s_counter)
  select s_field, LaunchId, sum(s_counter)
  from statistics
         join test_item ti on statistics.item_id = ti.item_id
  where ti.launch_id = LaunchId
  group by s_field
  ON CONFLICT ON CONSTRAINT unique_status_launch
                            DO UPDATE
                              SET
                                s_counter = EXCLUDED.s_counter;

  return 0;
END;
$$
LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION get_last_launch_number()
  RETURNS TRIGGER AS
$BODY$
BEGIN
  NEW.number = (SELECT number
                FROM launch
                WHERE name = NEW.name AND project_id = NEW.project_id
                ORDER BY number DESC
                LIMIT 1) + 1;
  NEW.number = CASE WHEN NEW.number IS NULL
    THEN 1
               ELSE NEW.number END;
  RETURN NEW;
END;
$BODY$
LANGUAGE plpgsql;

CREATE FUNCTION check_wired_tickets()
  RETURNS TRIGGER AS
$BODY$
BEGIN
  DELETE FROM ticket
  WHERE (SELECT count(issue_ticket.ticket_id)
         FROM issue_ticket
         WHERE issue_ticket.ticket_id = old.ticket_id) = 0 AND ticket.id = old.ticket_id;
  RETURN NULL;
END;
$BODY$
LANGUAGE plpgsql;


CREATE FUNCTION check_wired_widgets()
  RETURNS TRIGGER AS
$BODY$
BEGIN
  DELETE FROM widget
  WHERE (SELECT count(dashboard_widget.widget_id)
         FROM dashboard_widget
         WHERE dashboard_widget.widget_id = old.widget_id) = 0 AND widget.id = old.widget_id;
  RETURN NULL;
END;
$BODY$
LANGUAGE plpgsql;

CREATE TRIGGER after_ticket_delete
AFTER DELETE
  ON issue_ticket
FOR EACH ROW EXECUTE PROCEDURE check_wired_tickets();


CREATE TRIGGER after_widget_delete
AFTER DELETE
  ON dashboard_widget
FOR EACH ROW EXECUTE PROCEDURE check_wired_widgets();


CREATE TRIGGER last_launch_number_trigger
BEFORE INSERT
  ON launch
FOR EACH ROW
EXECUTE PROCEDURE get_last_launch_number();
.;
