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
import com.epam.ta.reportportal.jooq.tables.records.JServerSettingsRecord;
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
public class JServerSettings extends TableImpl<JServerSettingsRecord> {

	private static final long serialVersionUID = 1988112761;

	/**
	 * The reference instance of <code>public.server_settings</code>
	 */
	public static final JServerSettings SERVER_SETTINGS = new JServerSettings();

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<JServerSettingsRecord> getRecordType() {
		return JServerSettingsRecord.class;
	}

	/**
	 * The column <code>public.server_settings.id</code>.
	 */
	public final TableField<JServerSettingsRecord, Short> ID = createField(
			"id",
			org.jooq.impl.SQLDataType.SMALLINT.nullable(false)
					.defaultValue(org.jooq.impl.DSL.field(
							"nextval('server_settings_id_seq'::regclass)",
							org.jooq.impl.SQLDataType.SMALLINT
					)),
			this,
			""
	);

	/**
	 * The column <code>public.server_settings.key</code>.
	 */
	public final TableField<JServerSettingsRecord, String> KEY = createField(
			"key",
			org.jooq.impl.SQLDataType.VARCHAR.nullable(false),
			this,
			""
	);

	/**
	 * The column <code>public.server_settings.value</code>.
	 */
	public final TableField<JServerSettingsRecord, String> VALUE = createField("value", org.jooq.impl.SQLDataType.VARCHAR, this, "");

	/**
	 * Create a <code>public.server_settings</code> table reference
	 */
	public JServerSettings() {
		this(DSL.name("server_settings"), null);
	}

	/**
	 * Create an aliased <code>public.server_settings</code> table reference
	 */
	public JServerSettings(String alias) {
		this(DSL.name(alias), SERVER_SETTINGS);
	}

	/**
	 * Create an aliased <code>public.server_settings</code> table reference
	 */
	public JServerSettings(Name alias) {
		this(alias, SERVER_SETTINGS);
	}

	private JServerSettings(Name alias, Table<JServerSettingsRecord> aliased) {
		this(alias, aliased, null);
	}

	private JServerSettings(Name alias, Table<JServerSettingsRecord> aliased, Field<?>[] parameters) {
		super(alias, null, aliased, parameters, DSL.comment(""));
	}

	public <O extends Record> JServerSettings(Table<O> child, ForeignKey<O, JServerSettingsRecord> key) {
		super(child, key, SERVER_SETTINGS);
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
		return Arrays.<Index>asList(Indexes.SERVER_SETTINGS_ID, Indexes.SERVER_SETTINGS_KEY_KEY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Identity<JServerSettingsRecord, Short> getIdentity() {
		return Keys.IDENTITY_SERVER_SETTINGS;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UniqueKey<JServerSettingsRecord> getPrimaryKey() {
		return Keys.SERVER_SETTINGS_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UniqueKey<JServerSettingsRecord>> getKeys() {
		return Arrays.<UniqueKey<JServerSettingsRecord>>asList(Keys.SERVER_SETTINGS_ID, Keys.SERVER_SETTINGS_KEY_KEY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public JServerSettings as(String alias) {
		return new JServerSettings(DSL.name(alias), this);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public JServerSettings as(Name alias) {
		return new JServerSettings(alias, this);
	}

	/**
	 * Rename this table
	 */
	@Override
	public JServerSettings rename(String name) {
		return new JServerSettings(DSL.name(name), null);
	}

	/**
	 * Rename this table
	 */
	@Override
	public JServerSettings rename(Name name) {
		return new JServerSettings(name, null);
	}
}
