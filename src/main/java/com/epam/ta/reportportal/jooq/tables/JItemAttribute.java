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
import com.epam.ta.reportportal.jooq.tables.records.JItemAttributeRecord;
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
public class JItemAttribute extends TableImpl<JItemAttributeRecord> {

	private static final long serialVersionUID = -804671087;

	/**
	 * The reference instance of <code>public.item_attribute</code>
	 */
	public static final JItemAttribute ITEM_ATTRIBUTE = new JItemAttribute();

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<JItemAttributeRecord> getRecordType() {
		return JItemAttributeRecord.class;
	}

	/**
	 * The column <code>public.item_attribute.id</code>.
	 */
	public final TableField<JItemAttributeRecord, Long> ID = createField(
			"id",
			org.jooq.impl.SQLDataType.BIGINT.nullable(false)
					.defaultValue(org.jooq.impl.DSL.field("nextval('item_attribute_id_seq'::regclass)", org.jooq.impl.SQLDataType.BIGINT)),
			this,
			""
	);

	/**
	 * The column <code>public.item_attribute.key</code>.
	 */
	public final TableField<JItemAttributeRecord, String> KEY = createField("key", org.jooq.impl.SQLDataType.VARCHAR, this, "");

	/**
	 * The column <code>public.item_attribute.value</code>.
	 */
	public final TableField<JItemAttributeRecord, String> VALUE = createField(
			"value",
			org.jooq.impl.SQLDataType.VARCHAR.nullable(false),
			this,
			""
	);

	/**
	 * The column <code>public.item_attribute.item_id</code>.
	 */
	public final TableField<JItemAttributeRecord, Long> ITEM_ID = createField("item_id", org.jooq.impl.SQLDataType.BIGINT, this, "");

	/**
	 * The column <code>public.item_attribute.launch_id</code>.
	 */
	public final TableField<JItemAttributeRecord, Long> LAUNCH_ID = createField("launch_id", org.jooq.impl.SQLDataType.BIGINT, this, "");

	/**
	 * The column <code>public.item_attribute.system</code>.
	 */
	public final TableField<JItemAttributeRecord, Boolean> SYSTEM = createField(
			"system",
			org.jooq.impl.SQLDataType.BOOLEAN.defaultValue(org.jooq.impl.DSL.field("false", org.jooq.impl.SQLDataType.BOOLEAN)),
			this,
			""
	);

	/**
	 * Create a <code>public.item_attribute</code> table reference
	 */
	public JItemAttribute() {
		this(DSL.name("item_attribute"), null);
	}

	/**
	 * Create an aliased <code>public.item_attribute</code> table reference
	 */
	public JItemAttribute(String alias) {
		this(DSL.name(alias), ITEM_ATTRIBUTE);
	}

	/**
	 * Create an aliased <code>public.item_attribute</code> table reference
	 */
	public JItemAttribute(Name alias) {
		this(alias, ITEM_ATTRIBUTE);
	}

	private JItemAttribute(Name alias, Table<JItemAttributeRecord> aliased) {
		this(alias, aliased, null);
	}

	private JItemAttribute(Name alias, Table<JItemAttributeRecord> aliased, Field<?>[] parameters) {
		super(alias, null, aliased, parameters, DSL.comment(""));
	}

	public <O extends Record> JItemAttribute(Table<O> child, ForeignKey<O, JItemAttributeRecord> key) {
		super(child, key, ITEM_ATTRIBUTE);
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
		return Arrays.<Index>asList(Indexes.ITEM_ATTR_LAUNCH_IDX, Indexes.ITEM_ATTR_TI_IDX, Indexes.ITEM_ATTRIBUTE_PK);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Identity<JItemAttributeRecord, Long> getIdentity() {
		return Keys.IDENTITY_ITEM_ATTRIBUTE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UniqueKey<JItemAttributeRecord> getPrimaryKey() {
		return Keys.ITEM_ATTRIBUTE_PK;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UniqueKey<JItemAttributeRecord>> getKeys() {
		return Arrays.<UniqueKey<JItemAttributeRecord>>asList(Keys.ITEM_ATTRIBUTE_PK);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<ForeignKey<JItemAttributeRecord, ?>> getReferences() {
		return Arrays.<ForeignKey<JItemAttributeRecord, ?>>asList(
				Keys.ITEM_ATTRIBUTE__ITEM_ATTRIBUTE_ITEM_ID_FKEY,
				Keys.ITEM_ATTRIBUTE__ITEM_ATTRIBUTE_LAUNCH_ID_FKEY
		);
	}

	public JTestItem testItem() {
		return new JTestItem(this, Keys.ITEM_ATTRIBUTE__ITEM_ATTRIBUTE_ITEM_ID_FKEY);
	}

	public JLaunch launch() {
		return new JLaunch(this, Keys.ITEM_ATTRIBUTE__ITEM_ATTRIBUTE_LAUNCH_ID_FKEY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public JItemAttribute as(String alias) {
		return new JItemAttribute(DSL.name(alias), this);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public JItemAttribute as(Name alias) {
		return new JItemAttribute(alias, this);
	}

	/**
	 * Rename this table
	 */
	@Override
	public JItemAttribute rename(String name) {
		return new JItemAttribute(DSL.name(name), null);
	}

	/**
	 * Rename this table
	 */
	@Override
	public JItemAttribute rename(Name name) {
		return new JItemAttribute(name, null);
	}
}
