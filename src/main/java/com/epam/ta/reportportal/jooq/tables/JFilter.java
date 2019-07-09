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
import com.epam.ta.reportportal.jooq.tables.records.JFilterRecord;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;

import javax.annotation.Generated;
import java.util.Arrays;
import java.util.List;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.11.7"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class JFilter extends TableImpl<JFilterRecord> {

    private static final long serialVersionUID = -74651700;

    /**
     * The reference instance of <code>public.filter</code>
     */
    public static final JFilter FILTER = new JFilter();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<JFilterRecord> getRecordType() {
        return JFilterRecord.class;
    }

    /**
     * The column <code>public.filter.id</code>.
     */
    public final TableField<JFilterRecord, Long> ID = createField("id", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>public.filter.name</code>.
     */
    public final TableField<JFilterRecord, String> NAME = createField("name", org.jooq.impl.SQLDataType.VARCHAR.nullable(false), this, "");

    /**
     * The column <code>public.filter.target</code>.
     */
    public final TableField<JFilterRecord, String> TARGET = createField("target", org.jooq.impl.SQLDataType.VARCHAR.nullable(false), this, "");

    /**
     * The column <code>public.filter.description</code>.
     */
    public final TableField<JFilterRecord, String> DESCRIPTION = createField("description", org.jooq.impl.SQLDataType.VARCHAR, this, "");

    /**
     * Create a <code>public.filter</code> table reference
     */
    public JFilter() {
        this(DSL.name("filter"), null);
    }

    /**
     * Create an aliased <code>public.filter</code> table reference
     */
    public JFilter(String alias) {
        this(DSL.name(alias), FILTER);
    }

    /**
     * Create an aliased <code>public.filter</code> table reference
     */
    public JFilter(Name alias) {
        this(alias, FILTER);
    }

    private JFilter(Name alias, Table<JFilterRecord> aliased) {
        this(alias, aliased, null);
    }

    private JFilter(Name alias, Table<JFilterRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> JFilter(Table<O> child, ForeignKey<O, JFilterRecord> key) {
        super(child, key, FILTER);
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
        return Arrays.<Index>asList(Indexes.FILTER_PKEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<JFilterRecord> getPrimaryKey() {
        return Keys.FILTER_PKEY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<JFilterRecord>> getKeys() {
        return Arrays.<UniqueKey<JFilterRecord>>asList(Keys.FILTER_PKEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<JFilterRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<JFilterRecord, ?>>asList(Keys.FILTER__FILTER_ID_FK);
    }

    public JShareableEntity shareableEntity() {
        return new JShareableEntity(this, Keys.FILTER__FILTER_ID_FK);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JFilter as(String alias) {
        return new JFilter(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JFilter as(Name alias) {
        return new JFilter(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public JFilter rename(String name) {
        return new JFilter(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public JFilter rename(Name name) {
        return new JFilter(name, null);
    }
}
