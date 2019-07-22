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
import com.epam.ta.reportportal.jooq.tables.records.JUserCreationBidRecord;
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
public class JUserCreationBid extends TableImpl<JUserCreationBidRecord> {

	private static final long serialVersionUID = -701996166;

	/**
	 * The reference instance of <code>public.user_creation_bid</code>
	 */
	public static final JUserCreationBid USER_CREATION_BID = new JUserCreationBid();

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<JUserCreationBidRecord> getRecordType() {
		return JUserCreationBidRecord.class;
	}

	/**
	 * The column <code>public.user_creation_bid.uuid</code>.
	 */
	public final TableField<JUserCreationBidRecord, String> UUID = createField(
			"uuid",
			org.jooq.impl.SQLDataType.VARCHAR.nullable(false),
			this,
			""
	);

	/**
	 * The column <code>public.user_creation_bid.last_modified</code>.
	 */
	public final TableField<JUserCreationBidRecord, Timestamp> LAST_MODIFIED = createField(
			"last_modified",
			org.jooq.impl.SQLDataType.TIMESTAMP.defaultValue(org.jooq.impl.DSL.field("now()", org.jooq.impl.SQLDataType.TIMESTAMP)),
			this,
			""
	);

	/**
	 * The column <code>public.user_creation_bid.email</code>.
	 */
	public final TableField<JUserCreationBidRecord, String> EMAIL = createField(
			"email",
			org.jooq.impl.SQLDataType.VARCHAR.nullable(false),
			this,
			""
	);

	/**
	 * The column <code>public.user_creation_bid.default_project_id</code>.
	 */
	public final TableField<JUserCreationBidRecord, Long> DEFAULT_PROJECT_ID = createField(
			"default_project_id",
			org.jooq.impl.SQLDataType.BIGINT,
			this,
			""
	);

	/**
	 * The column <code>public.user_creation_bid.role</code>.
	 */
	public final TableField<JUserCreationBidRecord, String> ROLE = createField(
			"role",
			org.jooq.impl.SQLDataType.VARCHAR.nullable(false),
			this,
			""
	);

	/**
	 * Create a <code>public.user_creation_bid</code> table reference
	 */
	public JUserCreationBid() {
		this(DSL.name("user_creation_bid"), null);
	}

	/**
	 * Create an aliased <code>public.user_creation_bid</code> table reference
	 */
	public JUserCreationBid(String alias) {
		this(DSL.name(alias), USER_CREATION_BID);
	}

	/**
	 * Create an aliased <code>public.user_creation_bid</code> table reference
	 */
	public JUserCreationBid(Name alias) {
		this(alias, USER_CREATION_BID);
	}

	private JUserCreationBid(Name alias, Table<JUserCreationBidRecord> aliased) {
		this(alias, aliased, null);
	}

	private JUserCreationBid(Name alias, Table<JUserCreationBidRecord> aliased, Field<?>[] parameters) {
		super(alias, null, aliased, parameters, DSL.comment(""));
	}

	public <O extends Record> JUserCreationBid(Table<O> child, ForeignKey<O, JUserCreationBidRecord> key) {
		super(child, key, USER_CREATION_BID);
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
		return Arrays.<Index>asList(Indexes.USER_BID_PROJECT_IDX, Indexes.USER_CREATION_BID_PK);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UniqueKey<JUserCreationBidRecord> getPrimaryKey() {
		return Keys.USER_CREATION_BID_PK;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UniqueKey<JUserCreationBidRecord>> getKeys() {
		return Arrays.<UniqueKey<JUserCreationBidRecord>>asList(Keys.USER_CREATION_BID_PK);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<ForeignKey<JUserCreationBidRecord, ?>> getReferences() {
		return Arrays.<ForeignKey<JUserCreationBidRecord, ?>>asList(Keys.USER_CREATION_BID__USER_CREATION_BID_DEFAULT_PROJECT_ID_FKEY);
	}

	public JProject project() {
		return new JProject(this, Keys.USER_CREATION_BID__USER_CREATION_BID_DEFAULT_PROJECT_ID_FKEY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public JUserCreationBid as(String alias) {
		return new JUserCreationBid(DSL.name(alias), this);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public JUserCreationBid as(Name alias) {
		return new JUserCreationBid(alias, this);
	}

	/**
	 * Rename this table
	 */
	@Override
	public JUserCreationBid rename(String name) {
		return new JUserCreationBid(DSL.name(name), null);
	}

	/**
	 * Rename this table
	 */
	@Override
	public JUserCreationBid rename(Name name) {
		return new JUserCreationBid(name, null);
	}
}
