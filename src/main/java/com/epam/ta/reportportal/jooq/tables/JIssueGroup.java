/*
 * This file is generated by jOOQ.
 */
package com.epam.ta.reportportal.jooq.tables;


import com.epam.ta.reportportal.jooq.Indexes;
import com.epam.ta.reportportal.jooq.JPublic;
import com.epam.ta.reportportal.jooq.Keys;
import com.epam.ta.reportportal.jooq.enums.JIssueGroupEnum;
import com.epam.ta.reportportal.jooq.tables.records.JIssueGroupRecord;
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
public class JIssueGroup extends TableImpl<JIssueGroupRecord> {

  /**
   * The reference instance of <code>public.issue_group</code>
   */
  public static final JIssueGroup ISSUE_GROUP = new JIssueGroup();
  private static final long serialVersionUID = -124161300;
  /**
   * The column <code>public.issue_group.issue_group_id</code>.
   */
  public final TableField<JIssueGroupRecord, Short> ISSUE_GROUP_ID = createField(
      DSL.name("issue_group_id"), org.jooq.impl.SQLDataType.SMALLINT.nullable(false).defaultValue(
          org.jooq.impl.DSL.field("nextval('issue_group_issue_group_id_seq'::regclass)",
              org.jooq.impl.SQLDataType.SMALLINT)), this, "");
  /**
   * The column <code>public.issue_group.issue_group</code>.
   */
  public final TableField<JIssueGroupRecord, JIssueGroupEnum> ISSUE_GROUP_ = createField(
      DSL.name("issue_group"), org.jooq.impl.SQLDataType.VARCHAR.nullable(false)
          .asEnumDataType(com.epam.ta.reportportal.jooq.enums.JIssueGroupEnum.class), this, "");

  /**
   * Create a <code>public.issue_group</code> table reference
   */
  public JIssueGroup() {
    this(DSL.name("issue_group"), null);
  }

  /**
   * Create an aliased <code>public.issue_group</code> table reference
   */
  public JIssueGroup(String alias) {
    this(DSL.name(alias), ISSUE_GROUP);
  }

  /**
   * Create an aliased <code>public.issue_group</code> table reference
   */
  public JIssueGroup(Name alias) {
    this(alias, ISSUE_GROUP);
  }

  private JIssueGroup(Name alias, Table<JIssueGroupRecord> aliased) {
    this(alias, aliased, null);
  }

  private JIssueGroup(Name alias, Table<JIssueGroupRecord> aliased, Field<?>[] parameters) {
    super(alias, null, aliased, parameters, DSL.comment(""));
  }

  public <O extends Record> JIssueGroup(Table<O> child, ForeignKey<O, JIssueGroupRecord> key) {
    super(child, key, ISSUE_GROUP);
  }

  /**
   * The class holding records for this type
   */
  @Override
  public Class<JIssueGroupRecord> getRecordType() {
    return JIssueGroupRecord.class;
  }

  @Override
  public Schema getSchema() {
    return JPublic.PUBLIC;
  }

  @Override
  public List<Index> getIndexes() {
    return Arrays.<Index>asList(Indexes.ISSUE_GROUP_PK);
  }

  @Override
  public Identity<JIssueGroupRecord, Short> getIdentity() {
    return Keys.IDENTITY_ISSUE_GROUP;
  }

  @Override
  public UniqueKey<JIssueGroupRecord> getPrimaryKey() {
    return Keys.ISSUE_GROUP_PK;
  }

  @Override
  public List<UniqueKey<JIssueGroupRecord>> getKeys() {
    return Arrays.<UniqueKey<JIssueGroupRecord>>asList(Keys.ISSUE_GROUP_PK);
  }

  @Override
  public JIssueGroup as(String alias) {
    return new JIssueGroup(DSL.name(alias), this);
  }

  @Override
  public JIssueGroup as(Name alias) {
    return new JIssueGroup(alias, this);
  }

  /**
   * Rename this table
   */
  @Override
  public JIssueGroup rename(String name) {
    return new JIssueGroup(DSL.name(name), null);
  }

  /**
   * Rename this table
   */
  @Override
  public JIssueGroup rename(Name name) {
    return new JIssueGroup(name, null);
  }

  // -------------------------------------------------------------------------
  // Row2 type methods
  // -------------------------------------------------------------------------

  @Override
  public Row2<Short, JIssueGroupEnum> fieldsRow() {
    return (Row2) super.fieldsRow();
  }
}
