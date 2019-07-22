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

import com.epam.ta.reportportal.jooq.JPublic;
import com.epam.ta.reportportal.jooq.tables.records.JPgpArmorHeadersRecord;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;

import javax.annotation.Generated;


/**
 * This class is generated by jOOQ.
 */
@Generated(value = { "http://www.jooq.org", "jOOQ version:3.11.11" }, comments = "This class is generated by jOOQ")
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class JPgpArmorHeaders extends TableImpl<JPgpArmorHeadersRecord> {

	private static final long serialVersionUID = 117220575;

	/**
	 * The reference instance of <code>public.pgp_armor_headers</code>
	 */
	public static final JPgpArmorHeaders PGP_ARMOR_HEADERS = new JPgpArmorHeaders();

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<JPgpArmorHeadersRecord> getRecordType() {
		return JPgpArmorHeadersRecord.class;
	}

	/**
	 * The column <code>public.pgp_armor_headers.key</code>.
	 */
	public final TableField<JPgpArmorHeadersRecord, String> KEY = createField("key", org.jooq.impl.SQLDataType.CLOB, this, "");

	/**
	 * The column <code>public.pgp_armor_headers.value</code>.
	 */
	public final TableField<JPgpArmorHeadersRecord, String> VALUE = createField("value", org.jooq.impl.SQLDataType.CLOB, this, "");

	/**
	 * Create a <code>public.pgp_armor_headers</code> table reference
	 */
	public JPgpArmorHeaders() {
		this(DSL.name("pgp_armor_headers"), null);
	}

	/**
	 * Create an aliased <code>public.pgp_armor_headers</code> table reference
	 */
	public JPgpArmorHeaders(String alias) {
		this(DSL.name(alias), PGP_ARMOR_HEADERS);
	}

	/**
	 * Create an aliased <code>public.pgp_armor_headers</code> table reference
	 */
	public JPgpArmorHeaders(Name alias) {
		this(alias, PGP_ARMOR_HEADERS);
	}

	private JPgpArmorHeaders(Name alias, Table<JPgpArmorHeadersRecord> aliased) {
		this(alias, aliased, new Field[1]);
	}

	private JPgpArmorHeaders(Name alias, Table<JPgpArmorHeadersRecord> aliased, Field<?>[] parameters) {
		super(alias, null, aliased, parameters, DSL.comment(""));
	}

	public <O extends Record> JPgpArmorHeaders(Table<O> child, ForeignKey<O, JPgpArmorHeadersRecord> key) {
		super(child, key, PGP_ARMOR_HEADERS);
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
	public JPgpArmorHeaders as(String alias) {
		return new JPgpArmorHeaders(DSL.name(alias), this, parameters);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public JPgpArmorHeaders as(Name alias) {
		return new JPgpArmorHeaders(alias, this, parameters);
	}

	/**
	 * Rename this table
	 */
	@Override
	public JPgpArmorHeaders rename(String name) {
		return new JPgpArmorHeaders(DSL.name(name), null, parameters);
	}

	/**
	 * Rename this table
	 */
	@Override
	public JPgpArmorHeaders rename(Name name) {
		return new JPgpArmorHeaders(name, null, parameters);
	}

	/**
	 * Call this table-valued function
	 */
	public JPgpArmorHeaders call(String __1) {
		return new JPgpArmorHeaders(DSL.name(getName()), null, new Field[] { DSL.val(__1, org.jooq.impl.SQLDataType.CLOB) });
	}

	/**
	 * Call this table-valued function
	 */
	public JPgpArmorHeaders call(Field<String> __1) {
		return new JPgpArmorHeaders(DSL.name(getName()), null, new Field[] { __1 });
	}
}
