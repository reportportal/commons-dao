ALTER SEQUENCE statistics_s_id_seq
  RESTART WITH 1;
DELETE
FROM statistics;
ALTER SEQUENCE statistics_field_sf_id_seq
  RESTART WITH 1;
DELETE
FROM statistics_field;