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
import com.epam.ta.reportportal.jooq.tables.records.JUserPreferenceRecord;
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
public class JUserPreference extends TableImpl<JUserPreferenceRecord> {

	private static final long serialVersionUID = -1408267092;

	/**
	 * The reference instance of <code>public.user_preference</code>
	 */
	public static final JUserPreference USER_PREFERENCE = new JUserPreference();

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<JUserPreferenceRecord> getRecordType() {
		return JUserPreferenceRecord.class;
	}

	/**
	 * The column <code>public.user_preference.id</code>.
	 */
	public final TableField<JUserPreferenceRecord, Long> ID = createField(
			"id",
			org.jooq.impl.SQLDataType.BIGINT.nullable(false)
					.defaultValue(org.jooq.impl.DSL.field("nextval('user_preference_id_seq'::regclass)", org.jooq.impl.SQLDataType.BIGINT)),
			this,
			""
	);

	/**
	 * The column <code>public.user_preference.project_id</code>.
	 */
	public final TableField<JUserPreferenceRecord, Long> PROJECT_ID = createField(
			"project_id",
			org.jooq.impl.SQLDataType.BIGINT.nullable(false),
			this,
			""
	);

	/**
	 * The column <code>public.user_preference.user_id</code>.
	 */
	public final TableField<JUserPreferenceRecord, Long> USER_ID = createField(
			"user_id",
			org.jooq.impl.SQLDataType.BIGINT.nullable(false),
			this,
			""
	);

	/**
	 * The column <code>public.user_preference.filter_id</code>.
	 */
	public final TableField<JUserPreferenceRecord, Long> FILTER_ID = createField(
			"filter_id",
			org.jooq.impl.SQLDataType.BIGINT.nullable(false),
			this,
			""
	);

	/**
	 * Create a <code>public.user_preference</code> table reference
	 */
	public JUserPreference() {
		this(DSL.name("user_preference"), null);
	}

	/**
	 * Create an aliased <code>public.user_preference</code> table reference
	 */
	public JUserPreference(String alias) {
		this(DSL.name(alias), USER_PREFERENCE);
	}

	/**
	 * Create an aliased <code>public.user_preference</code> table reference
	 */
	public JUserPreference(Name alias) {
		this(alias, USER_PREFERENCE);
	}

	private JUserPreference(Name alias, Table<JUserPreferenceRecord> aliased) {
		this(alias, aliased, null);
	}

	private JUserPreference(Name alias, Table<JUserPreferenceRecord> aliased, Field<?>[] parameters) {
		super(alias, null, aliased, parameters, DSL.comment(""));
	}

	public <O extends Record> JUserPreference(Table<O> child, ForeignKey<O, JUserPreferenceRecord> key) {
		super(child, key, USER_PREFERENCE);
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
		return Arrays.<Index>asList(Indexes.USER_PREFERENCE_PK, Indexes.USER_PREFERENCE_UQ);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Identity<JUserPreferenceRecord, Long> getIdentity() {
		return Keys.IDENTITY_USER_PREFERENCE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UniqueKey<JUserPreferenceRecord> getPrimaryKey() {
		return Keys.USER_PREFERENCE_PK;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UniqueKey<JUserPreferenceRecord>> getKeys() {
		return Arrays.<UniqueKey<JUserPreferenceRecord>>asList(Keys.USER_PREFERENCE_PK, Keys.USER_PREFERENCE_UQ);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<ForeignKey<JUserPreferenceRecord, ?>> getReferences() {
		return Arrays.<ForeignKey<JUserPreferenceRecord, ?>>asList(
				Keys.USER_PREFERENCE__USER_PREFERENCE_PROJECT_ID_FKEY,
				Keys.USER_PREFERENCE__USER_PREFERENCE_USER_ID_FKEY,
				Keys.USER_PREFERENCE__USER_PREFERENCE_FILTER_ID_FKEY
		);
	}

	public JProject project() {
		return new JProject(this, Keys.USER_PREFERENCE__USER_PREFERENCE_PROJECT_ID_FKEY);
	}

	public JUsers users() {
		return new JUsers(this, Keys.USER_PREFERENCE__USER_PREFERENCE_USER_ID_FKEY);
	}

	public JFilter filter() {
		return new JFilter(this, Keys.USER_PREFERENCE__USER_PREFERENCE_FILTER_ID_FKEY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public JUserPreference as(String alias) {
		return new JUserPreference(DSL.name(alias), this);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public JUserPreference as(Name alias) {
		return new JUserPreference(alias, this);
	}

	/**
	 * Rename this table
	 */
	@Override
	public JUserPreference rename(String name) {
		return new JUserPreference(DSL.name(name), null);
	}

	/**
	 * Rename this table
	 */
	@Override
	public JUserPreference rename(Name name) {
		return new JUserPreference(name, null);
	}
}
