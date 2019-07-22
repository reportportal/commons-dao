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

import com.epam.ta.reportportal.jooq.tables.JSchemaMigrations;
import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record2;
import org.jooq.Row2;
import org.jooq.impl.UpdatableRecordImpl;

import javax.annotation.Generated;

/**
 * This class is generated by jOOQ.
 */
@Generated(value = { "http://www.jooq.org", "jOOQ version:3.11.11" }, comments = "This class is generated by jOOQ")
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class JSchemaMigrationsRecord extends UpdatableRecordImpl<JSchemaMigrationsRecord> implements Record2<Long, Boolean> {

	private static final long serialVersionUID = 143082958;

	/**
	 * Setter for <code>public.schema_migrations.version</code>.
	 */
	public void setVersion(Long value) {
		set(0, value);
	}

	/**
	 * Getter for <code>public.schema_migrations.version</code>.
	 */
	public Long getVersion() {
		return (Long) get(0);
	}

	/**
	 * Setter for <code>public.schema_migrations.dirty</code>.
	 */
	public void setDirty(Boolean value) {
		set(1, value);
	}

	/**
	 * Getter for <code>public.schema_migrations.dirty</code>.
	 */
	public Boolean getDirty() {
		return (Boolean) get(1);
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
	// Record2 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row2<Long, Boolean> fieldsRow() {
		return (Row2) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row2<Long, Boolean> valuesRow() {
		return (Row2) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Long> field1() {
		return JSchemaMigrations.SCHEMA_MIGRATIONS.VERSION;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Boolean> field2() {
		return JSchemaMigrations.SCHEMA_MIGRATIONS.DIRTY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Long component1() {
		return getVersion();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Boolean component2() {
		return getDirty();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Long value1() {
		return getVersion();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Boolean value2() {
		return getDirty();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public JSchemaMigrationsRecord value1(Long value) {
		setVersion(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public JSchemaMigrationsRecord value2(Boolean value) {
		setDirty(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public JSchemaMigrationsRecord values(Long value1, Boolean value2) {
		value1(value1);
		value2(value2);
		return this;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached JSchemaMigrationsRecord
	 */
	public JSchemaMigrationsRecord() {
		super(JSchemaMigrations.SCHEMA_MIGRATIONS);
	}

	/**
	 * Create a detached, initialised JSchemaMigrationsRecord
	 */
	public JSchemaMigrationsRecord(Long version, Boolean dirty) {
		super(JSchemaMigrations.SCHEMA_MIGRATIONS);

		set(0, version);
		set(1, dirty);
	}
}
