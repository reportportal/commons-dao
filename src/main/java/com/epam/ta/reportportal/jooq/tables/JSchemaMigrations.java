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
package com.epam.ta.reportportal.jooq.tables;

import com.epam.ta.reportportal.jooq.Indexes;
import com.epam.ta.reportportal.jooq.JPublic;
import com.epam.ta.reportportal.jooq.Keys;
import com.epam.ta.reportportal.jooq.tables.records.JSchemaMigrationsRecord;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;

import javax.annotation.Generated;
import java.util.Arrays;
import java.util.List;

/**
 * This class is generated by jOOQ.
 */
@Generated(value = { "http://www.jooq.org", "jOOQ version:3.11.11" }, comments = "This class is generated by jOOQ")
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class JSchemaMigrations extends TableImpl<JSchemaMigrationsRecord> {

	private static final long serialVersionUID = 1893152244;

	/**
	 * The reference instance of <code>public.schema_migrations</code>
	 */
	public static final JSchemaMigrations SCHEMA_MIGRATIONS = new JSchemaMigrations();

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<JSchemaMigrationsRecord> getRecordType() {
		return JSchemaMigrationsRecord.class;
	}

	/**
	 * The column <code>public.schema_migrations.version</code>.
	 */
	public final TableField<JSchemaMigrationsRecord, Long> VERSION = createField(
			"version",
			org.jooq.impl.SQLDataType.BIGINT.nullable(false),
			this,
			""
	);

	/**
	 * The column <code>public.schema_migrations.dirty</code>.
	 */
	public final TableField<JSchemaMigrationsRecord, Boolean> DIRTY = createField(
			"dirty",
			org.jooq.impl.SQLDataType.BOOLEAN.nullable(false),
			this,
			""
	);

	/**
	 * Create a <code>public.schema_migrations</code> table reference
	 */
	public JSchemaMigrations() {
		this(DSL.name("schema_migrations"), null);
	}

	/**
	 * Create an aliased <code>public.schema_migrations</code> table reference
	 */
	public JSchemaMigrations(String alias) {
		this(DSL.name(alias), SCHEMA_MIGRATIONS);
	}

	/**
	 * Create an aliased <code>public.schema_migrations</code> table reference
	 */
	public JSchemaMigrations(Name alias) {
		this(alias, SCHEMA_MIGRATIONS);
	}

	private JSchemaMigrations(Name alias, Table<JSchemaMigrationsRecord> aliased) {
		this(alias, aliased, null);
	}

	private JSchemaMigrations(Name alias, Table<JSchemaMigrationsRecord> aliased, Field<?>[] parameters) {
		super(alias, null, aliased, parameters, DSL.comment(""));
	}

	public <O extends Record> JSchemaMigrations(Table<O> child, ForeignKey<O, JSchemaMigrationsRecord> key) {
		super(child, key, SCHEMA_MIGRATIONS);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Schema getSchema() {
		return JPublic.PUBLIC;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Index> getIndexes() {
		return Arrays.<Index>asList(Indexes.SCHEMA_MIGRATIONS_PKEY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UniqueKey<JSchemaMigrationsRecord> getPrimaryKey() {
		return Keys.SCHEMA_MIGRATIONS_PKEY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UniqueKey<JSchemaMigrationsRecord>> getKeys() {
		return Arrays.<UniqueKey<JSchemaMigrationsRecord>>asList(Keys.SCHEMA_MIGRATIONS_PKEY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public JSchemaMigrations as(String alias) {
		return new JSchemaMigrations(DSL.name(alias), this);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public JSchemaMigrations as(Name alias) {
		return new JSchemaMigrations(alias, this);
	}

	/**
	 * Rename this table
	 */
	@Override
	public JSchemaMigrations rename(String name) {
		return new JSchemaMigrations(DSL.name(name), null);
	}

	/**
	 * Rename this table
	 */
	@Override
	public JSchemaMigrations rename(Name name) {
		return new JSchemaMigrations(name, null);
	}
}
