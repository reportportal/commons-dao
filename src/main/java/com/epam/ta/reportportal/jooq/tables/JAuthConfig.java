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
import com.epam.ta.reportportal.jooq.tables.records.JAuthConfigRecord;
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
public class JAuthConfig extends TableImpl<JAuthConfigRecord> {

	private static final long serialVersionUID = 1720094233;

	/**
	 * The reference instance of <code>public.auth_config</code>
	 */
	public static final JAuthConfig AUTH_CONFIG = new JAuthConfig();

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<JAuthConfigRecord> getRecordType() {
		return JAuthConfigRecord.class;
	}

	/**
	 * The column <code>public.auth_config.id</code>.
	 */
	public final TableField<JAuthConfigRecord, String> ID = createField("id", org.jooq.impl.SQLDataType.VARCHAR.nullable(false), this, "");

	/**
	 * The column <code>public.auth_config.ldap_config_id</code>.
	 */
	public final TableField<JAuthConfigRecord, Long> LDAP_CONFIG_ID = createField(
			"ldap_config_id",
			org.jooq.impl.SQLDataType.BIGINT,
			this,
			""
	);

	/**
	 * The column <code>public.auth_config.active_directory_config_id</code>.
	 */
	public final TableField<JAuthConfigRecord, Long> ACTIVE_DIRECTORY_CONFIG_ID = createField(
			"active_directory_config_id",
			org.jooq.impl.SQLDataType.BIGINT,
			this,
			""
	);

	/**
	 * Create a <code>public.auth_config</code> table reference
	 */
	public JAuthConfig() {
		this(DSL.name("auth_config"), null);
	}

	/**
	 * Create an aliased <code>public.auth_config</code> table reference
	 */
	public JAuthConfig(String alias) {
		this(DSL.name(alias), AUTH_CONFIG);
	}

	/**
	 * Create an aliased <code>public.auth_config</code> table reference
	 */
	public JAuthConfig(Name alias) {
		this(alias, AUTH_CONFIG);
	}

	private JAuthConfig(Name alias, Table<JAuthConfigRecord> aliased) {
		this(alias, aliased, null);
	}

	private JAuthConfig(Name alias, Table<JAuthConfigRecord> aliased, Field<?>[] parameters) {
		super(alias, null, aliased, parameters, DSL.comment(""));
	}

	public <O extends Record> JAuthConfig(Table<O> child, ForeignKey<O, JAuthConfigRecord> key) {
		super(child, key, AUTH_CONFIG);
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
		return Arrays.<Index>asList(Indexes.AUTH_CONFIG_PK);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UniqueKey<JAuthConfigRecord> getPrimaryKey() {
		return Keys.AUTH_CONFIG_PK;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UniqueKey<JAuthConfigRecord>> getKeys() {
		return Arrays.<UniqueKey<JAuthConfigRecord>>asList(Keys.AUTH_CONFIG_PK);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<ForeignKey<JAuthConfigRecord, ?>> getReferences() {
		return Arrays.<ForeignKey<JAuthConfigRecord, ?>>asList(
				Keys.AUTH_CONFIG__AUTH_CONFIG_LDAP_CONFIG_ID_FKEY,
				Keys.AUTH_CONFIG__AUTH_CONFIG_ACTIVE_DIRECTORY_CONFIG_ID_FKEY
		);
	}

	public JLdapConfig ldapConfig() {
		return new JLdapConfig(this, Keys.AUTH_CONFIG__AUTH_CONFIG_LDAP_CONFIG_ID_FKEY);
	}

	public JActiveDirectoryConfig activeDirectoryConfig() {
		return new JActiveDirectoryConfig(this, Keys.AUTH_CONFIG__AUTH_CONFIG_ACTIVE_DIRECTORY_CONFIG_ID_FKEY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public JAuthConfig as(String alias) {
		return new JAuthConfig(DSL.name(alias), this);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public JAuthConfig as(Name alias) {
		return new JAuthConfig(alias, this);
	}

	/**
	 * Rename this table
	 */
	@Override
	public JAuthConfig rename(String name) {
		return new JAuthConfig(DSL.name(name), null);
	}

	/**
	 * Rename this table
	 */
	@Override
	public JAuthConfig rename(Name name) {
		return new JAuthConfig(name, null);
	}
}
