/*
 * This file is generated by jOOQ.
 */
package com.epam.ta.reportportal.jooq.tables;


import com.epam.ta.reportportal.jooq.Indexes;
import com.epam.ta.reportportal.jooq.JPublic;
import com.epam.ta.reportportal.jooq.Keys;
import com.epam.ta.reportportal.jooq.tables.records.JContentFieldRecord;
import java.util.Arrays;
import java.util.List;
import javax.annotation.processing.Generated;
import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Index;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row2;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.12.4"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({"all", "unchecked", "rawtypes"})
public class JContentField extends TableImpl<JContentFieldRecord> {

  /**
   * The reference instance of <code>public.content_field</code>
   */
  public static final JContentField CONTENT_FIELD = new JContentField();
  private static final long serialVersionUID = 840904857;
  /**
   * The column <code>public.content_field.id</code>.
   */
  public final TableField<JContentFieldRecord, Long> ID = createField(DSL.name("id"),
      org.jooq.impl.SQLDataType.BIGINT, this, "");
  /**
   * The column <code>public.content_field.field</code>.
   */
  public final TableField<JContentFieldRecord, String> FIELD = createField(DSL.name("field"),
      org.jooq.impl.SQLDataType.VARCHAR.nullable(false), this, "");

  /**
   * Create a <code>public.content_field</code> table reference
   */
  public JContentField() {
    this(DSL.name("content_field"), null);
  }

  /**
   * Create an aliased <code>public.content_field</code> table reference
   */
  public JContentField(String alias) {
    this(DSL.name(alias), CONTENT_FIELD);
  }

  /**
   * Create an aliased <code>public.content_field</code> table reference
   */
  public JContentField(Name alias) {
    this(alias, CONTENT_FIELD);
  }

  private JContentField(Name alias, Table<JContentFieldRecord> aliased) {
    this(alias, aliased, null);
  }

  private JContentField(Name alias, Table<JContentFieldRecord> aliased, Field<?>[] parameters) {
    super(alias, null, aliased, parameters, DSL.comment(""));
  }

  public <O extends Record> JContentField(Table<O> child, ForeignKey<O, JContentFieldRecord> key) {
    super(child, key, CONTENT_FIELD);
  }

  /**
   * The class holding records for this type
   */
  @Override
  public Class<JContentFieldRecord> getRecordType() {
    return JContentFieldRecord.class;
  }

  @Override
  public Schema getSchema() {
    return JPublic.PUBLIC;
  }

  @Override
  public List<Index> getIndexes() {
    return Arrays.<Index>asList(Indexes.CONTENT_FIELD_IDX, Indexes.CONTENT_FIELD_WIDGET_IDX);
  }

  @Override
  public List<ForeignKey<JContentFieldRecord, ?>> getReferences() {
    return Arrays.<ForeignKey<JContentFieldRecord, ?>>asList(
        Keys.CONTENT_FIELD__CONTENT_FIELD_ID_FKEY);
  }

  public JWidget widget() {
    return new JWidget(this, Keys.CONTENT_FIELD__CONTENT_FIELD_ID_FKEY);
  }

  @Override
  public JContentField as(String alias) {
    return new JContentField(DSL.name(alias), this);
  }

  @Override
  public JContentField as(Name alias) {
    return new JContentField(alias, this);
  }

  /**
   * Rename this table
   */
  @Override
  public JContentField rename(String name) {
    return new JContentField(DSL.name(name), null);
  }

  /**
   * Rename this table
   */
  @Override
  public JContentField rename(Name name) {
    return new JContentField(name, null);
  }

  // -------------------------------------------------------------------------
  // Row2 type methods
  // -------------------------------------------------------------------------

  @Override
  public Row2<Long, String> fieldsRow() {
    return (Row2) super.fieldsRow();
  }
}
