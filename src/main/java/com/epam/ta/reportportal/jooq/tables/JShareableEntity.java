/*
 * Copyright (C) 2018 EPAM Systems
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
import com.epam.ta.reportportal.jooq.tables.records.JShareableEntityRecord;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;

import javax.annotation.Generated;
import java.util.Arrays;
import java.util.List;

/**
 * This class is generated by jOOQ.
 */
@Generated(value = { "http://www.jooq.org", "jOOQ version:3.11.7" }, comments = "This class is generated by jOOQ")
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class JShareableEntity extends TableImpl<JShareableEntityRecord> {

	private static final long serialVersionUID = -591305562;

	/**
	 * The reference instance of <code>public.shareable_entity</code>
	 */
	public static final JShareableEntity SHAREABLE_ENTITY = new JShareableEntity();

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<JShareableEntityRecord> getRecordType() {
		return JShareableEntityRecord.class;
	}

	/**
	 * The column <code>public.shareable_entity.id</code>.
	 */
	public final TableField<JShareableEntityRecord, Long> ID = createField("id",
			org.jooq.impl.SQLDataType.BIGINT.nullable(false)
					.defaultValue(org.jooq.impl.DSL.field("nextval('shareable_entity_id_seq'::regclass)",
							org.jooq.impl.SQLDataType.BIGINT
					)),
			this,
			""
	);

	/**
	 * The column <code>public.shareable_entity.shared</code>.
	 */
	public final TableField<JShareableEntityRecord, Boolean> SHARED = createField("shared",
			org.jooq.impl.SQLDataType.BOOLEAN.nullable(false)
					.defaultValue(org.jooq.impl.DSL.field("false", org.jooq.impl.SQLDataType.BOOLEAN)),
			this,
			""
	);

	/**
	 * The column <code>public.shareable_entity.owner</code>.
	 */
	public final TableField<JShareableEntityRecord, String> OWNER = createField("owner",
			org.jooq.impl.SQLDataType.VARCHAR.nullable(false),
			this,
			""
	);

	/**
	 * The column <code>public.shareable_entity.project_id</code>.
	 */
	public final TableField<JShareableEntityRecord, Long> PROJECT_ID = createField("project_id",
			org.jooq.impl.SQLDataType.BIGINT.nullable(false),
			this,
			""
	);

	/**
	 * Create a <code>public.shareable_entity</code> table reference
	 */
	public JShareableEntity() {
		this(DSL.name("shareable_entity"), null);
	}

	/**
	 * Create an aliased <code>public.shareable_entity</code> table reference
	 */
	public JShareableEntity(String alias) {
		this(DSL.name(alias), SHAREABLE_ENTITY);
	}

	/**
	 * Create an aliased <code>public.shareable_entity</code> table reference
	 */
	public JShareableEntity(Name alias) {
		this(alias, SHAREABLE_ENTITY);
	}

	private JShareableEntity(Name alias, Table<JShareableEntityRecord> aliased) {
		this(alias, aliased, null);
	}

	private JShareableEntity(Name alias, Table<JShareableEntityRecord> aliased, Field<?>[] parameters) {
		super(alias, null, aliased, parameters, DSL.comment(""));
	}

	public <O extends Record> JShareableEntity(Table<O> child, ForeignKey<O, JShareableEntityRecord> key) {
		super(child, key, SHAREABLE_ENTITY);
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
		return Arrays.<Index>asList(Indexes.SHAREABLE_PK);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Identity<JShareableEntityRecord, Long> getIdentity() {
		return Keys.IDENTITY_SHAREABLE_ENTITY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UniqueKey<JShareableEntityRecord> getPrimaryKey() {
		return Keys.SHAREABLE_PK;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UniqueKey<JShareableEntityRecord>> getKeys() {
		return Arrays.<UniqueKey<JShareableEntityRecord>>asList(Keys.SHAREABLE_PK);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<ForeignKey<JShareableEntityRecord, ?>> getReferences() {
		return Arrays.<ForeignKey<JShareableEntityRecord, ?>>asList(Keys.SHAREABLE_ENTITY__SHAREABLE_ENTITY_PROJECT_ID_FKEY);
	}

	public JProject project() {
		return new JProject(this, Keys.SHAREABLE_ENTITY__SHAREABLE_ENTITY_PROJECT_ID_FKEY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public JShareableEntity as(String alias) {
		return new JShareableEntity(DSL.name(alias), this);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public JShareableEntity as(Name alias) {
		return new JShareableEntity(alias, this);
	}

	/**
	 * Rename this table
	 */
	@Override
	public JShareableEntity rename(String name) {
		return new JShareableEntity(DSL.name(name), null);
	}

	/**
	 * Rename this table
	 */
	@Override
	public JShareableEntity rename(Name name) {
		return new JShareableEntity(name, null);
	}
}
