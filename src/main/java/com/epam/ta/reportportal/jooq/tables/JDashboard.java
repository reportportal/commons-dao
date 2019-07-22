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
import com.epam.ta.reportportal.jooq.tables.records.JDashboardRecord;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;

import javax.annotation.Generated;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

/**
 * This class is generated by jOOQ.
 */
@Generated(value = { "http://www.jooq.org", "jOOQ version:3.11.11" }, comments = "This class is generated by jOOQ")
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class JDashboard extends TableImpl<JDashboardRecord> {

	private static final long serialVersionUID = 1354478626;

	/**
	 * The reference instance of <code>public.dashboard</code>
	 */
	public static final JDashboard DASHBOARD = new JDashboard();

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<JDashboardRecord> getRecordType() {
		return JDashboardRecord.class;
	}

	/**
	 * The column <code>public.dashboard.id</code>.
	 */
	public final TableField<JDashboardRecord, Long> ID = createField("id", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

	/**
	 * The column <code>public.dashboard.name</code>.
	 */
	public final TableField<JDashboardRecord, String> NAME = createField(
			"name",
			org.jooq.impl.SQLDataType.VARCHAR.nullable(false),
			this,
			""
	);

	/**
	 * The column <code>public.dashboard.description</code>.
	 */
	public final TableField<JDashboardRecord, String> DESCRIPTION = createField("description", org.jooq.impl.SQLDataType.VARCHAR, this, "");

	/**
	 * The column <code>public.dashboard.creation_date</code>.
	 */
	public final TableField<JDashboardRecord, Timestamp> CREATION_DATE = createField(
			"creation_date",
			org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false)
					.defaultValue(org.jooq.impl.DSL.field("now()", org.jooq.impl.SQLDataType.TIMESTAMP)),
			this,
			""
	);

	/**
	 * Create a <code>public.dashboard</code> table reference
	 */
	public JDashboard() {
		this(DSL.name("dashboard"), null);
	}

	/**
	 * Create an aliased <code>public.dashboard</code> table reference
	 */
	public JDashboard(String alias) {
		this(DSL.name(alias), DASHBOARD);
	}

	/**
	 * Create an aliased <code>public.dashboard</code> table reference
	 */
	public JDashboard(Name alias) {
		this(alias, DASHBOARD);
	}

	private JDashboard(Name alias, Table<JDashboardRecord> aliased) {
		this(alias, aliased, null);
	}

	private JDashboard(Name alias, Table<JDashboardRecord> aliased, Field<?>[] parameters) {
		super(alias, null, aliased, parameters, DSL.comment(""));
	}

	public <O extends Record> JDashboard(Table<O> child, ForeignKey<O, JDashboardRecord> key) {
		super(child, key, DASHBOARD);
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
		return Arrays.<Index>asList(Indexes.DASHBOARD_PKEY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UniqueKey<JDashboardRecord> getPrimaryKey() {
		return Keys.DASHBOARD_PKEY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UniqueKey<JDashboardRecord>> getKeys() {
		return Arrays.<UniqueKey<JDashboardRecord>>asList(Keys.DASHBOARD_PKEY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<ForeignKey<JDashboardRecord, ?>> getReferences() {
		return Arrays.<ForeignKey<JDashboardRecord, ?>>asList(Keys.DASHBOARD__DASHBOARD_ID_FK);
	}

	public JShareableEntity shareableEntity() {
		return new JShareableEntity(this, Keys.DASHBOARD__DASHBOARD_ID_FK);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public JDashboard as(String alias) {
		return new JDashboard(DSL.name(alias), this);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public JDashboard as(Name alias) {
		return new JDashboard(alias, this);
	}

	/**
	 * Rename this table
	 */
	@Override
	public JDashboard rename(String name) {
		return new JDashboard(DSL.name(name), null);
	}

	/**
	 * Rename this table
	 */
	@Override
	public JDashboard rename(Name name) {
		return new JDashboard(name, null);
	}
}
