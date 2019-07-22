/*
 * Copyright 2019 EPAM Systems
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/*
 * This file is generated by jOOQ.
 */
package com.epam.ta.reportportal.jooq.tables.records;

import com.epam.ta.reportportal.jooq.enums.JStatusEnum;
import com.epam.ta.reportportal.jooq.tables.JTestItemResults;
import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record4;
import org.jooq.Row4;
import org.jooq.impl.UpdatableRecordImpl;

import javax.annotation.Generated;
import java.sql.Timestamp;


/**
 * This class is generated by jOOQ.
 */
@Generated(value = { "http://www.jooq.org", "jOOQ version:3.11.11" }, comments = "This class is generated by jOOQ")
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class JTestItemResultsRecord extends UpdatableRecordImpl<JTestItemResultsRecord>
		implements Record4<Long, JStatusEnum, Timestamp, Double> {

	private static final long serialVersionUID = -1740494873;

	/**
	 * Setter for <code>public.test_item_results.result_id</code>.
	 */
	public void setResultId(Long value) {
		set(0, value);
	}

	/**
	 * Getter for <code>public.test_item_results.result_id</code>.
	 */
	public Long getResultId() {
		return (Long) get(0);
	}

	/**
	 * Setter for <code>public.test_item_results.status</code>.
	 */
	public void setStatus(JStatusEnum value) {
		set(1, value);
	}

	/**
	 * Getter for <code>public.test_item_results.status</code>.
	 */
	public JStatusEnum getStatus() {
		return (JStatusEnum) get(1);
	}

	/**
	 * Setter for <code>public.test_item_results.end_time</code>.
	 */
	public void setEndTime(Timestamp value) {
		set(2, value);
	}

	/**
	 * Getter for <code>public.test_item_results.end_time</code>.
	 */
	public Timestamp getEndTime() {
		return (Timestamp) get(2);
	}

	/**
	 * Setter for <code>public.test_item_results.duration</code>.
	 */
	public void setDuration(Double value) {
		set(3, value);
	}

	/**
	 * Getter for <code>public.test_item_results.duration</code>.
	 */
	public Double getDuration() {
		return (Double) get(3);
	}

	// -------------------------------------------------------------------------
	// Primary key information
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Record1<Long> key() {
		return (Record1) super.key();
	}

	// -------------------------------------------------------------------------
	// Record4 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row4<Long, JStatusEnum, Timestamp, Double> fieldsRow() {
		return (Row4) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row4<Long, JStatusEnum, Timestamp, Double> valuesRow() {
		return (Row4) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Long> field1() {
		return JTestItemResults.TEST_ITEM_RESULTS.RESULT_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<JStatusEnum> field2() {
		return JTestItemResults.TEST_ITEM_RESULTS.STATUS;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Timestamp> field3() {
		return JTestItemResults.TEST_ITEM_RESULTS.END_TIME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Double> field4() {
		return JTestItemResults.TEST_ITEM_RESULTS.DURATION;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Long component1() {
		return getResultId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public JStatusEnum component2() {
		return getStatus();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Timestamp component3() {
		return getEndTime();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Double component4() {
		return getDuration();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Long value1() {
		return getResultId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public JStatusEnum value2() {
		return getStatus();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Timestamp value3() {
		return getEndTime();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Double value4() {
		return getDuration();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public JTestItemResultsRecord value1(Long value) {
		setResultId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public JTestItemResultsRecord value2(JStatusEnum value) {
		setStatus(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public JTestItemResultsRecord value3(Timestamp value) {
		setEndTime(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public JTestItemResultsRecord value4(Double value) {
		setDuration(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public JTestItemResultsRecord values(Long value1, JStatusEnum value2, Timestamp value3, Double value4) {
		value1(value1);
		value2(value2);
		value3(value3);
		value4(value4);
		return this;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached JTestItemResultsRecord
	 */
	public JTestItemResultsRecord() {
		super(JTestItemResults.TEST_ITEM_RESULTS);
	}

	/**
	 * Create a detached, initialised JTestItemResultsRecord
	 */
	public JTestItemResultsRecord(Long resultId, JStatusEnum status, Timestamp endTime, Double duration) {
		super(JTestItemResults.TEST_ITEM_RESULTS);

		set(0, resultId);
		set(1, status);
		set(2, endTime);
		set(3, duration);
	}
}
