/*
 * This file is generated by jOOQ.
 */
package com.epam.ta.reportportal.jooq.tables.records;


import com.epam.ta.reportportal.jooq.enums.JLaunchModeEnum;
import com.epam.ta.reportportal.jooq.enums.JStatusEnum;
import com.epam.ta.reportportal.jooq.tables.JLaunch;
import java.sql.Timestamp;
import javax.annotation.processing.Generated;
import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record15;
import org.jooq.Row15;
import org.jooq.impl.UpdatableRecordImpl;


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
public class JLaunchRecord extends UpdatableRecordImpl<JLaunchRecord> implements
    Record15<Long, String, Long, Long, String, String, Timestamp, Timestamp, Integer, Timestamp, JLaunchModeEnum, JStatusEnum, Boolean, Boolean, Double> {

  private static final long serialVersionUID = 2143234608;

  /**
   * Create a detached JLaunchRecord
   */
  public JLaunchRecord() {
    super(JLaunch.LAUNCH);
  }

  /**
   * Create a detached, initialised JLaunchRecord
   */
  public JLaunchRecord(Long id, String uuid, Long projectId, Long userId, String name,
      String description, Timestamp startTime, Timestamp endTime, Integer number,
      Timestamp lastModified, JLaunchModeEnum mode, JStatusEnum status, Boolean hasRetries,
      Boolean rerun, Double approximateDuration) {
    super(JLaunch.LAUNCH);

    set(0, id);
    set(1, uuid);
    set(2, projectId);
    set(3, userId);
    set(4, name);
    set(5, description);
    set(6, startTime);
    set(7, endTime);
    set(8, number);
    set(9, lastModified);
    set(10, mode);
    set(11, status);
    set(12, hasRetries);
    set(13, rerun);
    set(14, approximateDuration);
  }

  /**
   * Getter for <code>public.launch.id</code>.
   */
  public Long getId() {
    return (Long) get(0);
  }

  /**
   * Setter for <code>public.launch.id</code>.
   */
  public void setId(Long value) {
    set(0, value);
  }

  /**
   * Getter for <code>public.launch.uuid</code>.
   */
  public String getUuid() {
    return (String) get(1);
  }

  /**
   * Setter for <code>public.launch.uuid</code>.
   */
  public void setUuid(String value) {
    set(1, value);
  }

  /**
   * Getter for <code>public.launch.project_id</code>.
   */
  public Long getProjectId() {
    return (Long) get(2);
  }

  /**
   * Setter for <code>public.launch.project_id</code>.
   */
  public void setProjectId(Long value) {
    set(2, value);
  }

  /**
   * Getter for <code>public.launch.user_id</code>.
   */
  public Long getUserId() {
    return (Long) get(3);
  }

  /**
   * Setter for <code>public.launch.user_id</code>.
   */
  public void setUserId(Long value) {
    set(3, value);
  }

  /**
   * Getter for <code>public.launch.name</code>.
   */
  public String getName() {
    return (String) get(4);
  }

  /**
   * Setter for <code>public.launch.name</code>.
   */
  public void setName(String value) {
    set(4, value);
  }

  /**
   * Getter for <code>public.launch.description</code>.
   */
  public String getDescription() {
    return (String) get(5);
  }

  /**
   * Setter for <code>public.launch.description</code>.
   */
  public void setDescription(String value) {
    set(5, value);
  }

  /**
   * Getter for <code>public.launch.start_time</code>.
   */
  public Timestamp getStartTime() {
    return (Timestamp) get(6);
  }

  /**
   * Setter for <code>public.launch.start_time</code>.
   */
  public void setStartTime(Timestamp value) {
    set(6, value);
  }

  /**
   * Getter for <code>public.launch.end_time</code>.
   */
  public Timestamp getEndTime() {
    return (Timestamp) get(7);
  }

  /**
   * Setter for <code>public.launch.end_time</code>.
   */
  public void setEndTime(Timestamp value) {
    set(7, value);
  }

  /**
   * Getter for <code>public.launch.number</code>.
   */
  public Integer getNumber() {
    return (Integer) get(8);
  }

  /**
   * Setter for <code>public.launch.number</code>.
   */
  public void setNumber(Integer value) {
    set(8, value);
  }

  /**
   * Getter for <code>public.launch.last_modified</code>.
   */
  public Timestamp getLastModified() {
    return (Timestamp) get(9);
  }

  /**
   * Setter for <code>public.launch.last_modified</code>.
   */
  public void setLastModified(Timestamp value) {
    set(9, value);
  }

  /**
   * Getter for <code>public.launch.mode</code>.
   */
  public JLaunchModeEnum getMode() {
    return (JLaunchModeEnum) get(10);
  }

  /**
   * Setter for <code>public.launch.mode</code>.
   */
  public void setMode(JLaunchModeEnum value) {
    set(10, value);
  }

  /**
   * Getter for <code>public.launch.status</code>.
   */
  public JStatusEnum getStatus() {
    return (JStatusEnum) get(11);
  }

  /**
   * Setter for <code>public.launch.status</code>.
   */
  public void setStatus(JStatusEnum value) {
    set(11, value);
  }

  /**
   * Getter for <code>public.launch.has_retries</code>.
   */
  public Boolean getHasRetries() {
    return (Boolean) get(12);
  }

  /**
   * Setter for <code>public.launch.has_retries</code>.
   */
  public void setHasRetries(Boolean value) {
    set(12, value);
  }

  /**
   * Getter for <code>public.launch.rerun</code>.
   */
  public Boolean getRerun() {
    return (Boolean) get(13);
  }

  /**
   * Setter for <code>public.launch.rerun</code>.
   */
  public void setRerun(Boolean value) {
    set(13, value);
  }

  // -------------------------------------------------------------------------
  // Primary key information
  // -------------------------------------------------------------------------

  /**
   * Getter for <code>public.launch.approximate_duration</code>.
   */
  public Double getApproximateDuration() {
    return (Double) get(14);
  }

  // -------------------------------------------------------------------------
  // Record15 type implementation
  // -------------------------------------------------------------------------

  /**
   * Setter for <code>public.launch.approximate_duration</code>.
   */
  public void setApproximateDuration(Double value) {
    set(14, value);
  }

  @Override
  public Record1<Long> key() {
    return (Record1) super.key();
  }

  @Override
  public Row15<Long, String, Long, Long, String, String, Timestamp, Timestamp, Integer, Timestamp, JLaunchModeEnum, JStatusEnum, Boolean, Boolean, Double> fieldsRow() {
    return (Row15) super.fieldsRow();
  }

  @Override
  public Row15<Long, String, Long, Long, String, String, Timestamp, Timestamp, Integer, Timestamp, JLaunchModeEnum, JStatusEnum, Boolean, Boolean, Double> valuesRow() {
    return (Row15) super.valuesRow();
  }

  @Override
  public Field<Long> field1() {
    return JLaunch.LAUNCH.ID;
  }

  @Override
  public Field<String> field2() {
    return JLaunch.LAUNCH.UUID;
  }

  @Override
  public Field<Long> field3() {
    return JLaunch.LAUNCH.PROJECT_ID;
  }

  @Override
  public Field<Long> field4() {
    return JLaunch.LAUNCH.USER_ID;
  }

  @Override
  public Field<String> field5() {
    return JLaunch.LAUNCH.NAME;
  }

  @Override
  public Field<String> field6() {
    return JLaunch.LAUNCH.DESCRIPTION;
  }

  @Override
  public Field<Timestamp> field7() {
    return JLaunch.LAUNCH.START_TIME;
  }

  @Override
  public Field<Timestamp> field8() {
    return JLaunch.LAUNCH.END_TIME;
  }

  @Override
  public Field<Integer> field9() {
    return JLaunch.LAUNCH.NUMBER;
  }

  @Override
  public Field<Timestamp> field10() {
    return JLaunch.LAUNCH.LAST_MODIFIED;
  }

  @Override
  public Field<JLaunchModeEnum> field11() {
    return JLaunch.LAUNCH.MODE;
  }

  @Override
  public Field<JStatusEnum> field12() {
    return JLaunch.LAUNCH.STATUS;
  }

  @Override
  public Field<Boolean> field13() {
    return JLaunch.LAUNCH.HAS_RETRIES;
  }

  @Override
  public Field<Boolean> field14() {
    return JLaunch.LAUNCH.RERUN;
  }

  @Override
  public Field<Double> field15() {
    return JLaunch.LAUNCH.APPROXIMATE_DURATION;
  }

  @Override
  public Long component1() {
    return getId();
  }

  @Override
  public String component2() {
    return getUuid();
  }

  @Override
  public Long component3() {
    return getProjectId();
  }

  @Override
  public Long component4() {
    return getUserId();
  }

  @Override
  public String component5() {
    return getName();
  }

  @Override
  public String component6() {
    return getDescription();
  }

  @Override
  public Timestamp component7() {
    return getStartTime();
  }

  @Override
  public Timestamp component8() {
    return getEndTime();
  }

  @Override
  public Integer component9() {
    return getNumber();
  }

  @Override
  public Timestamp component10() {
    return getLastModified();
  }

  @Override
  public JLaunchModeEnum component11() {
    return getMode();
  }

  @Override
  public JStatusEnum component12() {
    return getStatus();
  }

  @Override
  public Boolean component13() {
    return getHasRetries();
  }

  @Override
  public Boolean component14() {
    return getRerun();
  }

  @Override
  public Double component15() {
    return getApproximateDuration();
  }

  @Override
  public Long value1() {
    return getId();
  }

  @Override
  public String value2() {
    return getUuid();
  }

  @Override
  public Long value3() {
    return getProjectId();
  }

  @Override
  public Long value4() {
    return getUserId();
  }

  @Override
  public String value5() {
    return getName();
  }

  @Override
  public String value6() {
    return getDescription();
  }

  @Override
  public Timestamp value7() {
    return getStartTime();
  }

  @Override
  public Timestamp value8() {
    return getEndTime();
  }

  @Override
  public Integer value9() {
    return getNumber();
  }

  @Override
  public Timestamp value10() {
    return getLastModified();
  }

  @Override
  public JLaunchModeEnum value11() {
    return getMode();
  }

  @Override
  public JStatusEnum value12() {
    return getStatus();
  }

  @Override
  public Boolean value13() {
    return getHasRetries();
  }

  @Override
  public Boolean value14() {
    return getRerun();
  }

  @Override
  public Double value15() {
    return getApproximateDuration();
  }

  @Override
  public JLaunchRecord value1(Long value) {
    setId(value);
    return this;
  }

  @Override
  public JLaunchRecord value2(String value) {
    setUuid(value);
    return this;
  }

  @Override
  public JLaunchRecord value3(Long value) {
    setProjectId(value);
    return this;
  }

  @Override
  public JLaunchRecord value4(Long value) {
    setUserId(value);
    return this;
  }

  @Override
  public JLaunchRecord value5(String value) {
    setName(value);
    return this;
  }

  @Override
  public JLaunchRecord value6(String value) {
    setDescription(value);
    return this;
  }

  @Override
  public JLaunchRecord value7(Timestamp value) {
    setStartTime(value);
    return this;
  }

  @Override
  public JLaunchRecord value8(Timestamp value) {
    setEndTime(value);
    return this;
  }

  @Override
  public JLaunchRecord value9(Integer value) {
    setNumber(value);
    return this;
  }

  @Override
  public JLaunchRecord value10(Timestamp value) {
    setLastModified(value);
    return this;
  }

  @Override
  public JLaunchRecord value11(JLaunchModeEnum value) {
    setMode(value);
    return this;
  }

  @Override
  public JLaunchRecord value12(JStatusEnum value) {
    setStatus(value);
    return this;
  }

  @Override
  public JLaunchRecord value13(Boolean value) {
    setHasRetries(value);
    return this;
  }

  @Override
  public JLaunchRecord value14(Boolean value) {
    setRerun(value);
    return this;
  }

  // -------------------------------------------------------------------------
  // Constructors
  // -------------------------------------------------------------------------

  @Override
  public JLaunchRecord value15(Double value) {
    setApproximateDuration(value);
    return this;
  }

  @Override
  public JLaunchRecord values(Long value1, String value2, Long value3, Long value4, String value5,
      String value6, Timestamp value7, Timestamp value8, Integer value9, Timestamp value10,
      JLaunchModeEnum value11, JStatusEnum value12, Boolean value13, Boolean value14,
      Double value15) {
    value1(value1);
    value2(value2);
    value3(value3);
    value4(value4);
    value5(value5);
    value6(value6);
    value7(value7);
    value8(value8);
    value9(value9);
    value10(value10);
    value11(value11);
    value12(value12);
    value13(value13);
    value14(value14);
    value15(value15);
    return this;
  }
}
