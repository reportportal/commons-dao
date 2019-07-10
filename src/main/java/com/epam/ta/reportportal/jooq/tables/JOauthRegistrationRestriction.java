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
import com.epam.ta.reportportal.jooq.tables.records.JOauthRegistrationRestrictionRecord;
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
public class JOauthRegistrationRestriction extends TableImpl<JOauthRegistrationRestrictionRecord> {

    private static final long serialVersionUID = -1182378518;

    /**
     * The reference instance of <code>public.oauth_registration_restriction</code>
     */
    public static final JOauthRegistrationRestriction OAUTH_REGISTRATION_RESTRICTION = new JOauthRegistrationRestriction();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<JOauthRegistrationRestrictionRecord> getRecordType() {
        return JOauthRegistrationRestrictionRecord.class;
    }

    /**
     * The column <code>public.oauth_registration_restriction.id</code>.
     */
    public final TableField<JOauthRegistrationRestrictionRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.field("nextval('oauth_registration_restriction_id_seq'::regclass)", org.jooq.impl.SQLDataType.INTEGER)), this, "");

    /**
     * The column <code>public.oauth_registration_restriction.oauth_registration_fk</code>.
     */
    public final TableField<JOauthRegistrationRestrictionRecord, String> OAUTH_REGISTRATION_FK = createField("oauth_registration_fk", org.jooq.impl.SQLDataType.VARCHAR(128), this, "");

    /**
     * The column <code>public.oauth_registration_restriction.type</code>.
     */
    public final TableField<JOauthRegistrationRestrictionRecord, String> TYPE = createField("type", org.jooq.impl.SQLDataType.VARCHAR(256).nullable(false), this, "");

    /**
     * The column <code>public.oauth_registration_restriction.value</code>.
     */
    public final TableField<JOauthRegistrationRestrictionRecord, String> VALUE = createField("value", org.jooq.impl.SQLDataType.VARCHAR(256).nullable(false), this, "");

    /**
     * Create a <code>public.oauth_registration_restriction</code> table reference
     */
    public JOauthRegistrationRestriction() {
        this(DSL.name("oauth_registration_restriction"), null);
    }

    /**
     * Create an aliased <code>public.oauth_registration_restriction</code> table reference
     */
    public JOauthRegistrationRestriction(String alias) {
        this(DSL.name(alias), OAUTH_REGISTRATION_RESTRICTION);
    }

    /**
     * Create an aliased <code>public.oauth_registration_restriction</code> table reference
     */
    public JOauthRegistrationRestriction(Name alias) {
        this(alias, OAUTH_REGISTRATION_RESTRICTION);
    }

    private JOauthRegistrationRestriction(Name alias, Table<JOauthRegistrationRestrictionRecord> aliased) {
        this(alias, aliased, null);
    }

    private JOauthRegistrationRestriction(Name alias, Table<JOauthRegistrationRestrictionRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> JOauthRegistrationRestriction(Table<O> child, ForeignKey<O, JOauthRegistrationRestrictionRecord> key) {
        super(child, key, OAUTH_REGISTRATION_RESTRICTION);
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
        return Arrays.<Index>asList(Indexes.OAUTH_REGISTRATION_RESTRICTION_PK, Indexes.OAUTH_REGISTRATION_RESTRICTION_UNIQUE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<JOauthRegistrationRestrictionRecord, Integer> getIdentity() {
        return Keys.IDENTITY_OAUTH_REGISTRATION_RESTRICTION;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<JOauthRegistrationRestrictionRecord> getPrimaryKey() {
        return Keys.OAUTH_REGISTRATION_RESTRICTION_PK;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<JOauthRegistrationRestrictionRecord>> getKeys() {
        return Arrays.<UniqueKey<JOauthRegistrationRestrictionRecord>>asList(Keys.OAUTH_REGISTRATION_RESTRICTION_PK, Keys.OAUTH_REGISTRATION_RESTRICTION_UNIQUE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<JOauthRegistrationRestrictionRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<JOauthRegistrationRestrictionRecord, ?>>asList(Keys.OAUTH_REGISTRATION_RESTRICTION__OAUTH_REGISTRATION_RESTRICTION_OAUTH_REGISTRATION_FK_FKEY);
    }

    public JOauthRegistration oauthRegistration() {
        return new JOauthRegistration(this, Keys.OAUTH_REGISTRATION_RESTRICTION__OAUTH_REGISTRATION_RESTRICTION_OAUTH_REGISTRATION_FK_FKEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JOauthRegistrationRestriction as(String alias) {
        return new JOauthRegistrationRestriction(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JOauthRegistrationRestriction as(Name alias) {
        return new JOauthRegistrationRestriction(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public JOauthRegistrationRestriction rename(String name) {
        return new JOauthRegistrationRestriction(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public JOauthRegistrationRestriction rename(Name name) {
        return new JOauthRegistrationRestriction(name, null);
    }
}
