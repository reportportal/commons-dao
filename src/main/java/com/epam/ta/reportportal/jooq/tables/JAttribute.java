/*
 * This file is generated by jOOQ.
 */
package com.epam.ta.reportportal.jooq.tables;


import com.epam.ta.reportportal.jooq.Indexes;
import com.epam.ta.reportportal.jooq.JPublic;
import com.epam.ta.reportportal.jooq.Keys;
import com.epam.ta.reportportal.jooq.tables.records.JAttributeRecord;
import java.util.Arrays;
import java.util.List;
import javax.annotation.processing.Generated;
import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.Index;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row2;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
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
public class JAttribute extends TableImpl<JAttributeRecord> {

  /**
   * The reference instance of <code>public.attribute</code>
   */
  public static final JAttribute ATTRIBUTE = new JAttribute();
  private static final long serialVersionUID = -720380676;
  /**
   * The column <code>public.attribute.id</code>.
   */
  public final TableField<JAttributeRecord, Long> ID = createField(DSL.name("id"),
      org.jooq.impl.SQLDataType.BIGINT.nullable(false).defaultValue(
          org.jooq.impl.DSL.field("nextval('attribute_id_seq'::regclass)",
              org.jooq.impl.SQLDataType.BIGINT)), this, "");
  /**
   * The column <code>public.attribute.name</code>.
   */
  public final TableField<JAttributeRecord, String> NAME = createField(DSL.name("name"),
      org.jooq.impl.SQLDataType.VARCHAR(256), this, "");

  /**
   * Create a <code>public.attribute</code> table reference
   */
  public JAttribute() {
    this(DSL.name("attribute"), null);
  }

  /**
   * Create an aliased <code>public.attribute</code> table reference
   */
  public JAttribute(String alias) {
    this(DSL.name(alias), ATTRIBUTE);
  }

  /**
   * Create an aliased <code>public.attribute</code> table reference
   */
  public JAttribute(Name alias) {
    this(alias, ATTRIBUTE);
  }

  private JAttribute(Name alias, Table<JAttributeRecord> aliased) {
    this(alias, aliased, null);
  }

  private JAttribute(Name alias, Table<JAttributeRecord> aliased, Field<?>[] parameters) {
    super(alias, null, aliased, parameters, DSL.comment(""));
  }

  public <O extends Record> JAttribute(Table<O> child, ForeignKey<O, JAttributeRecord> key) {
    super(child, key, ATTRIBUTE);
  }

  /**
   * The class holding records for this type
   */
  @Override
  public Class<JAttributeRecord> getRecordType() {
    return JAttributeRecord.class;
  }

  @Override
  public Schema getSchema() {
    return JPublic.PUBLIC;
  }

  @Override
  public List<Index> getIndexes() {
    return Arrays.<Index>asList(Indexes.ATTRIBUTE_PK);
  }

  @Override
  public Identity<JAttributeRecord, Long> getIdentity() {
    return Keys.IDENTITY_ATTRIBUTE;
  }

  @Override
  public UniqueKey<JAttributeRecord> getPrimaryKey() {
    return Keys.ATTRIBUTE_PK;
  }

  @Override
  public List<UniqueKey<JAttributeRecord>> getKeys() {
    return Arrays.<UniqueKey<JAttributeRecord>>asList(Keys.ATTRIBUTE_PK);
  }

  @Override
  public JAttribute as(String alias) {
    return new JAttribute(DSL.name(alias), this);
  }

  @Override
  public JAttribute as(Name alias) {
    return new JAttribute(alias, this);
  }

  /**
   * Rename this table
   */
  @Override
  public JAttribute rename(String name) {
    return new JAttribute(DSL.name(name), null);
  }

  /**
   * Rename this table
   */
  @Override
  public JAttribute rename(Name name) {
    return new JAttribute(name, null);
  }

  // -------------------------------------------------------------------------
  // Row2 type methods
  // -------------------------------------------------------------------------

  @Override
  public Row2<Long, String> fieldsRow() {
    return (Row2) super.fieldsRow();
  }
}
