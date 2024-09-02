/*
 * This file is generated by jOOQ.
 */
package com.epam.ta.reportportal.jooq.tables;


import com.epam.ta.reportportal.dao.converters.JooqInstantConverter;
import com.epam.ta.reportportal.jooq.Indexes;
import com.epam.ta.reportportal.jooq.JPublic;
import com.epam.ta.reportportal.jooq.Keys;
import com.epam.ta.reportportal.jooq.enums.JOrganizationRoleEnum;
import com.epam.ta.reportportal.jooq.tables.records.JOrganizationUserRecord;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import javax.annotation.processing.Generated;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Index;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row4;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.12.4"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class JOrganizationUser extends TableImpl<JOrganizationUserRecord> {

    private static final long serialVersionUID = -1132022965;

    /**
     * The reference instance of <code>public.organization_user</code>
     */
    public static final JOrganizationUser ORGANIZATION_USER = new JOrganizationUser();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<JOrganizationUserRecord> getRecordType() {
        return JOrganizationUserRecord.class;
    }

    /**
     * The column <code>public.organization_user.user_id</code>.
     */
    public final TableField<JOrganizationUserRecord, Long> USER_ID = createField(DSL.name("user_id"), org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>public.organization_user.organization_id</code>.
     */
    public final TableField<JOrganizationUserRecord, Long> ORGANIZATION_ID = createField(DSL.name("organization_id"), org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>public.organization_user.organization_role</code>.
     */
    public final TableField<JOrganizationUserRecord, JOrganizationRoleEnum> ORGANIZATION_ROLE = createField(DSL.name("organization_role"), org.jooq.impl.SQLDataType.VARCHAR.nullable(false).asEnumDataType(com.epam.ta.reportportal.jooq.enums.JOrganizationRoleEnum.class), this, "");

    /**
     * The column <code>public.organization_user.assigned_at</code>.
     */
    public final TableField<JOrganizationUserRecord, Instant> ASSIGNED_AT = createField(DSL.name("assigned_at"), org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.field("now()", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "", new JooqInstantConverter());

    /**
     * Create a <code>public.organization_user</code> table reference
     */
    public JOrganizationUser() {
        this(DSL.name("organization_user"), null);
    }

    /**
     * Create an aliased <code>public.organization_user</code> table reference
     */
    public JOrganizationUser(String alias) {
        this(DSL.name(alias), ORGANIZATION_USER);
    }

    /**
     * Create an aliased <code>public.organization_user</code> table reference
     */
    public JOrganizationUser(Name alias) {
        this(alias, ORGANIZATION_USER);
    }

    private JOrganizationUser(Name alias, Table<JOrganizationUserRecord> aliased) {
        this(alias, aliased, null);
    }

    private JOrganizationUser(Name alias, Table<JOrganizationUserRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> JOrganizationUser(Table<O> child, ForeignKey<O, JOrganizationUserRecord> key) {
        super(child, key, ORGANIZATION_USER);
    }

    @Override
    public Schema getSchema() {
        return JPublic.PUBLIC;
    }

    @Override
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.ORGANIZATION_USER_PK);
    }

    @Override
    public UniqueKey<JOrganizationUserRecord> getPrimaryKey() {
        return Keys.ORGANIZATION_USER_PK;
    }

    @Override
    public List<UniqueKey<JOrganizationUserRecord>> getKeys() {
        return Arrays.<UniqueKey<JOrganizationUserRecord>>asList(Keys.ORGANIZATION_USER_PK);
    }

    @Override
    public List<ForeignKey<JOrganizationUserRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<JOrganizationUserRecord, ?>>asList(Keys.ORGANIZATION_USER__ORGANIZATION_USER_USER_ID_FKEY, Keys.ORGANIZATION_USER__ORGANIZATION_USER_ORGANIZATION_ID_FKEY);
    }

    public JUsers users() {
        return new JUsers(this, Keys.ORGANIZATION_USER__ORGANIZATION_USER_USER_ID_FKEY);
    }

    public JOrganization organization() {
        return new JOrganization(this, Keys.ORGANIZATION_USER__ORGANIZATION_USER_ORGANIZATION_ID_FKEY);
    }

    @Override
    public JOrganizationUser as(String alias) {
        return new JOrganizationUser(DSL.name(alias), this);
    }

    @Override
    public JOrganizationUser as(Name alias) {
        return new JOrganizationUser(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public JOrganizationUser rename(String name) {
        return new JOrganizationUser(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public JOrganizationUser rename(Name name) {
        return new JOrganizationUser(name, null);
    }

    // -------------------------------------------------------------------------
    // Row4 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row4<Long, Long, JOrganizationRoleEnum, Instant> fieldsRow() {
        return (Row4) super.fieldsRow();
    }
}
