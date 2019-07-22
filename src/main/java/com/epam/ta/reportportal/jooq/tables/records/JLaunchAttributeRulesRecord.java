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

import com.epam.ta.reportportal.jooq.tables.JLaunchAttributeRules;
import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record4;
import org.jooq.Row4;
import org.jooq.impl.UpdatableRecordImpl;

import javax.annotation.Generated;


/**
 * This class is generated by jOOQ.
 */
@Generated(value = { "http://www.jooq.org", "jOOQ version:3.11.11" }, comments = "This class is generated by jOOQ")
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class JLaunchAttributeRulesRecord extends UpdatableRecordImpl<JLaunchAttributeRulesRecord>
		implements Record4<Long, Long, String, String> {

	private static final long serialVersionUID = 1769473958;

	/**
	 * Setter for <code>public.launch_attribute_rules.id</code>.
	 */
	public void setId(Long value) {
		set(0, value);
	}

	/**
	 * Getter for <code>public.launch_attribute_rules.id</code>.
	 */
	public Long getId() {
		return (Long) get(0);
	}

	/**
	 * Setter for <code>public.launch_attribute_rules.sender_case_id</code>.
	 */
	public void setSenderCaseId(Long value) {
		set(1, value);
	}

	/**
	 * Getter for <code>public.launch_attribute_rules.sender_case_id</code>.
	 */
	public Long getSenderCaseId() {
		return (Long) get(1);
	}

	/**
	 * Setter for <code>public.launch_attribute_rules.key</code>.
	 */
	public void setKey(String value) {
		set(2, value);
	}

	/**
	 * Getter for <code>public.launch_attribute_rules.key</code>.
	 */
	public String getKey() {
		return (String) get(2);
	}

	/**
	 * Setter for <code>public.launch_attribute_rules.value</code>.
	 */
	public void setValue(String value) {
		set(3, value);
	}

	/**
	 * Getter for <code>public.launch_attribute_rules.value</code>.
	 */
	public String getValue() {
		return (String) get(3);
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
	public Row4<Long, Long, String, String> fieldsRow() {
		return (Row4) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row4<Long, Long, String, String> valuesRow() {
		return (Row4) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Long> field1() {
		return JLaunchAttributeRules.LAUNCH_ATTRIBUTE_RULES.ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Long> field2() {
		return JLaunchAttributeRules.LAUNCH_ATTRIBUTE_RULES.SENDER_CASE_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field3() {
		return JLaunchAttributeRules.LAUNCH_ATTRIBUTE_RULES.KEY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field4() {
		return JLaunchAttributeRules.LAUNCH_ATTRIBUTE_RULES.VALUE;
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
	public Long component2() {
		return getSenderCaseId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String component3() {
		return getKey();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String component4() {
		return getValue();
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
	public Long value2() {
		return getSenderCaseId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value3() {
		return getKey();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value4() {
		return getValue();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public JLaunchAttributeRulesRecord value1(Long value) {
		setId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public JLaunchAttributeRulesRecord value2(Long value) {
		setSenderCaseId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public JLaunchAttributeRulesRecord value3(String value) {
		setKey(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public JLaunchAttributeRulesRecord value4(String value) {
		setValue(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public JLaunchAttributeRulesRecord values(Long value1, Long value2, String value3, String value4) {
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
	 * Create a detached JLaunchAttributeRulesRecord
	 */
	public JLaunchAttributeRulesRecord() {
		super(JLaunchAttributeRules.LAUNCH_ATTRIBUTE_RULES);
	}

	/**
	 * Create a detached, initialised JLaunchAttributeRulesRecord
	 */
	public JLaunchAttributeRulesRecord(Long id, Long senderCaseId, String key, String value) {
		super(JLaunchAttributeRules.LAUNCH_ATTRIBUTE_RULES);

		set(0, id);
		set(1, senderCaseId);
		set(2, key);
		set(3, value);
	}
}
