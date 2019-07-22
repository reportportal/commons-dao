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

import com.epam.ta.reportportal.jooq.tables.JSenderCase;
import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record3;
import org.jooq.Row3;
import org.jooq.impl.UpdatableRecordImpl;

import javax.annotation.Generated;


/**
 * This class is generated by jOOQ.
 */
@Generated(value = { "http://www.jooq.org", "jOOQ version:3.11.11" }, comments = "This class is generated by jOOQ")
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class JSenderCaseRecord extends UpdatableRecordImpl<JSenderCaseRecord> implements Record3<Long, String, Long> {

	private static final long serialVersionUID = 282123215;

	/**
	 * Setter for <code>public.sender_case.id</code>.
	 */
	public void setId(Long value) {
		set(0, value);
	}

	/**
	 * Getter for <code>public.sender_case.id</code>.
	 */
	public Long getId() {
		return (Long) get(0);
	}

	/**
	 * Setter for <code>public.sender_case.send_case</code>.
	 */
	public void setSendCase(String value) {
		set(1, value);
	}

	/**
	 * Getter for <code>public.sender_case.send_case</code>.
	 */
	public String getSendCase() {
		return (String) get(1);
	}

	/**
	 * Setter for <code>public.sender_case.project_id</code>.
	 */
	public void setProjectId(Long value) {
		set(2, value);
	}

	/**
	 * Getter for <code>public.sender_case.project_id</code>.
	 */
	public Long getProjectId() {
		return (Long) get(2);
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
	// Record3 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row3<Long, String, Long> fieldsRow() {
		return (Row3) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row3<Long, String, Long> valuesRow() {
		return (Row3) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Long> field1() {
		return JSenderCase.SENDER_CASE.ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field2() {
		return JSenderCase.SENDER_CASE.SEND_CASE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Long> field3() {
		return JSenderCase.SENDER_CASE.PROJECT_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Long component1() {
		return getId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String component2() {
		return getSendCase();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Long component3() {
		return getProjectId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Long value1() {
		return getId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value2() {
		return getSendCase();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Long value3() {
		return getProjectId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public JSenderCaseRecord value1(Long value) {
		setId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public JSenderCaseRecord value2(String value) {
		setSendCase(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public JSenderCaseRecord value3(Long value) {
		setProjectId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public JSenderCaseRecord values(Long value1, String value2, Long value3) {
		value1(value1);
		value2(value2);
		value3(value3);
		return this;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached JSenderCaseRecord
	 */
	public JSenderCaseRecord() {
		super(JSenderCase.SENDER_CASE);
	}

	/**
	 * Create a detached, initialised JSenderCaseRecord
	 */
	public JSenderCaseRecord(Long id, String sendCase, Long projectId) {
		super(JSenderCase.SENDER_CASE);

		set(0, id);
		set(1, sendCase);
		set(2, projectId);
	}
}
