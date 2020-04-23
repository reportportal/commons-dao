/*
 * This file is generated by jOOQ.
 */
package com.epam.ta.reportportal.jooq.tables;


import com.epam.ta.reportportal.jooq.Indexes;
import com.epam.ta.reportportal.jooq.JPublic;
import com.epam.ta.reportportal.jooq.Keys;
import com.epam.ta.reportportal.jooq.enums.JProjectRoleEnum;
import com.epam.ta.reportportal.jooq.tables.records.JProjectUserRecord;

import java.util.Arrays;
import java.util.List;

import javax.annotation.processing.Generated;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Index;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row3;
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
public class JProjectUser extends TableImpl<JProjectUserRecord> {

    private static final long serialVersionUID = -877127631;

    /**
     * The reference instance of <code>public.project_user</code>
     */
    public static final JProjectUser PROJECT_USER = new JProjectUser();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<JProjectUserRecord> getRecordType() {
        return JProjectUserRecord.class;
    }

    /**
     * The column <code>public.project_user.user_id</code>.
     */
    public final TableField<JProjectUserRecord, Long> USER_ID = createField(DSL.name("user_id"), org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>public.project_user.project_id</code>.
     */
    public final TableField<JProjectUserRecord, Long> PROJECT_ID = createField(DSL.name("project_id"), org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>public.project_user.project_role</code>.
     */
    public final TableField<JProjectUserRecord, JProjectRoleEnum> PROJECT_ROLE = createField(DSL.name("project_role"), org.jooq.impl.SQLDataType.VARCHAR.nullable(false).asEnumDataType(com.epam.ta.reportportal.jooq.enums.JProjectRoleEnum.class), this, "");

    /**
     * Create a <code>public.project_user</code> table reference
     */
    public JProjectUser() {
        this(DSL.name("project_user"), null);
    }

    /**
     * Create an aliased <code>public.project_user</code> table reference
     */
    public JProjectUser(String alias) {
        this(DSL.name(alias), PROJECT_USER);
    }

    /**
     * Create an aliased <code>public.project_user</code> table reference
     */
    public JProjectUser(Name alias) {
        this(alias, PROJECT_USER);
    }

    private JProjectUser(Name alias, Table<JProjectUserRecord> aliased) {
        this(alias, aliased, null);
    }

    private JProjectUser(Name alias, Table<JProjectUserRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> JProjectUser(Table<O> child, ForeignKey<O, JProjectUserRecord> key) {
        super(child, key, PROJECT_USER);
    }

    @Override
    public Schema getSchema() {
        return JPublic.PUBLIC;
    }

    @Override
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.USERS_PROJECT_PK);
    }

    @Override
    public UniqueKey<JProjectUserRecord> getPrimaryKey() {
        return Keys.USERS_PROJECT_PK;
    }

    @Override
    public List<UniqueKey<JProjectUserRecord>> getKeys() {
        return Arrays.<UniqueKey<JProjectUserRecord>>asList(Keys.USERS_PROJECT_PK);
    }

    @Override
    public List<ForeignKey<JProjectUserRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<JProjectUserRecord, ?>>asList(Keys.PROJECT_USER__PROJECT_USER_USER_ID_FKEY, Keys.PROJECT_USER__PROJECT_USER_PROJECT_ID_FKEY);
    }

    public JUsers users() {
        return new JUsers(this, Keys.PROJECT_USER__PROJECT_USER_USER_ID_FKEY);
    }

    public JProject project() {
        return new JProject(this, Keys.PROJECT_USER__PROJECT_USER_PROJECT_ID_FKEY);
    }

    @Override
    public JProjectUser as(String alias) {
        return new JProjectUser(DSL.name(alias), this);
    }

    @Override
    public JProjectUser as(Name alias) {
        return new JProjectUser(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public JProjectUser rename(String name) {
        return new JProjectUser(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public JProjectUser rename(Name name) {
        return new JProjectUser(name, null);
    }

    // -------------------------------------------------------------------------
    // Row3 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row3<Long, Long, JProjectRoleEnum> fieldsRow() {
        return (Row3) super.fieldsRow();
    }
}
