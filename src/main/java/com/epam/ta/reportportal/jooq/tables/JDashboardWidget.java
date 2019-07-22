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
import com.epam.ta.reportportal.jooq.tables.records.JDashboardWidgetRecord;
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
public class JDashboardWidget extends TableImpl<JDashboardWidgetRecord> {

	private static final long serialVersionUID = -1154576654;

	/**
	 * The reference instance of <code>public.dashboard_widget</code>
	 */
	public static final JDashboardWidget DASHBOARD_WIDGET = new JDashboardWidget();

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<JDashboardWidgetRecord> getRecordType() {
		return JDashboardWidgetRecord.class;
	}

	/**
	 * The column <code>public.dashboard_widget.dashboard_id</code>.
	 */
	public final TableField<JDashboardWidgetRecord, Long> DASHBOARD_ID = createField(
			"dashboard_id",
			org.jooq.impl.SQLDataType.BIGINT.nullable(false),
			this,
			""
	);

	/**
	 * The column <code>public.dashboard_widget.widget_id</code>.
	 */
	public final TableField<JDashboardWidgetRecord, Long> WIDGET_ID = createField(
			"widget_id",
			org.jooq.impl.SQLDataType.BIGINT.nullable(false),
			this,
			""
	);

	/**
	 * The column <code>public.dashboard_widget.widget_name</code>.
	 */
	public final TableField<JDashboardWidgetRecord, String> WIDGET_NAME = createField(
			"widget_name",
			org.jooq.impl.SQLDataType.VARCHAR.nullable(false),
			this,
			""
	);

	/**
	 * The column <code>public.dashboard_widget.widget_owner</code>.
	 */
	public final TableField<JDashboardWidgetRecord, String> WIDGET_OWNER = createField(
			"widget_owner",
			org.jooq.impl.SQLDataType.VARCHAR.nullable(false),
			this,
			""
	);

	/**
	 * The column <code>public.dashboard_widget.widget_type</code>.
	 */
	public final TableField<JDashboardWidgetRecord, String> WIDGET_TYPE = createField(
			"widget_type",
			org.jooq.impl.SQLDataType.VARCHAR.nullable(false),
			this,
			""
	);

	/**
	 * The column <code>public.dashboard_widget.widget_width</code>.
	 */
	public final TableField<JDashboardWidgetRecord, Integer> WIDGET_WIDTH = createField(
			"widget_width",
			org.jooq.impl.SQLDataType.INTEGER.nullable(false),
			this,
			""
	);

	/**
	 * The column <code>public.dashboard_widget.widget_height</code>.
	 */
	public final TableField<JDashboardWidgetRecord, Integer> WIDGET_HEIGHT = createField(
			"widget_height",
			org.jooq.impl.SQLDataType.INTEGER.nullable(false),
			this,
			""
	);

	/**
	 * The column <code>public.dashboard_widget.widget_position_x</code>.
	 */
	public final TableField<JDashboardWidgetRecord, Integer> WIDGET_POSITION_X = createField(
			"widget_position_x",
			org.jooq.impl.SQLDataType.INTEGER.nullable(false),
			this,
			""
	);

	/**
	 * The column <code>public.dashboard_widget.widget_position_y</code>.
	 */
	public final TableField<JDashboardWidgetRecord, Integer> WIDGET_POSITION_Y = createField(
			"widget_position_y",
			org.jooq.impl.SQLDataType.INTEGER.nullable(false),
			this,
			""
	);

	/**
	 * The column <code>public.dashboard_widget.is_created_on</code>.
	 */
	public final TableField<JDashboardWidgetRecord, Boolean> IS_CREATED_ON = createField(
			"is_created_on",
			org.jooq.impl.SQLDataType.BOOLEAN.nullable(false)
					.defaultValue(org.jooq.impl.DSL.field("false", org.jooq.impl.SQLDataType.BOOLEAN)),
			this,
			""
	);

	/**
	 * Create a <code>public.dashboard_widget</code> table reference
	 */
	public JDashboardWidget() {
		this(DSL.name("dashboard_widget"), null);
	}

	/**
	 * Create an aliased <code>public.dashboard_widget</code> table reference
	 */
	public JDashboardWidget(String alias) {
		this(DSL.name(alias), DASHBOARD_WIDGET);
	}

	/**
	 * Create an aliased <code>public.dashboard_widget</code> table reference
	 */
	public JDashboardWidget(Name alias) {
		this(alias, DASHBOARD_WIDGET);
	}

	private JDashboardWidget(Name alias, Table<JDashboardWidgetRecord> aliased) {
		this(alias, aliased, null);
	}

	private JDashboardWidget(Name alias, Table<JDashboardWidgetRecord> aliased, Field<?>[] parameters) {
		super(alias, null, aliased, parameters, DSL.comment(""));
	}

	public <O extends Record> JDashboardWidget(Table<O> child, ForeignKey<O, JDashboardWidgetRecord> key) {
		super(child, key, DASHBOARD_WIDGET);
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
		return Arrays.<Index>asList(Indexes.DASHBOARD_WIDGET_PK, Indexes.WIDGET_ON_DASHBOARD_UNQ);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UniqueKey<JDashboardWidgetRecord> getPrimaryKey() {
		return Keys.DASHBOARD_WIDGET_PK;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UniqueKey<JDashboardWidgetRecord>> getKeys() {
		return Arrays.<UniqueKey<JDashboardWidgetRecord>>asList(Keys.DASHBOARD_WIDGET_PK, Keys.WIDGET_ON_DASHBOARD_UNQ);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<ForeignKey<JDashboardWidgetRecord, ?>> getReferences() {
		return Arrays.<ForeignKey<JDashboardWidgetRecord, ?>>asList(
				Keys.DASHBOARD_WIDGET__DASHBOARD_WIDGET_DASHBOARD_ID_FKEY,
				Keys.DASHBOARD_WIDGET__DASHBOARD_WIDGET_WIDGET_ID_FKEY
		);
	}

	public JDashboard dashboard() {
		return new JDashboard(this, Keys.DASHBOARD_WIDGET__DASHBOARD_WIDGET_DASHBOARD_ID_FKEY);
	}

	public JWidget widget() {
		return new JWidget(this, Keys.DASHBOARD_WIDGET__DASHBOARD_WIDGET_WIDGET_ID_FKEY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public JDashboardWidget as(String alias) {
		return new JDashboardWidget(DSL.name(alias), this);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public JDashboardWidget as(Name alias) {
		return new JDashboardWidget(alias, this);
	}

	/**
	 * Rename this table
	 */
	@Override
	public JDashboardWidget rename(String name) {
		return new JDashboardWidget(DSL.name(name), null);
	}

	/**
	 * Rename this table
	 */
	@Override
	public JDashboardWidget rename(Name name) {
		return new JDashboardWidget(name, null);
	}
}
