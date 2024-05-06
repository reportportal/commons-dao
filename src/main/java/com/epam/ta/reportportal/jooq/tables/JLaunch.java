/*
 * This file is generated by jOOQ.
 */
package com.epam.ta.reportportal.jooq.tables;


import com.epam.ta.reportportal.jooq.Indexes;
import com.epam.ta.reportportal.jooq.JPublic;
import com.epam.ta.reportportal.jooq.Keys;
import com.epam.ta.reportportal.jooq.enums.JLaunchModeEnum;
import com.epam.ta.reportportal.jooq.enums.JRetentionPolicyEnum;
import com.epam.ta.reportportal.jooq.enums.JStatusEnum;
import com.epam.ta.reportportal.jooq.tables.records.JLaunchRecord;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import javax.annotation.processing.Generated;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.Index;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row16;
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
public class JLaunch extends TableImpl<JLaunchRecord> {

    private static final long serialVersionUID = -1044821285;

    /**
     * The reference instance of <code>public.launch</code>
     */
    public static final JLaunch LAUNCH = new JLaunch();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<JLaunchRecord> getRecordType() {
        return JLaunchRecord.class;
    }

    /**
     * The column <code>public.launch.id</code>.
     */
    public final TableField<JLaunchRecord, Long> ID = createField(DSL.name("id"), org.jooq.impl.SQLDataType.BIGINT.nullable(false).defaultValue(org.jooq.impl.DSL.field("nextval('launch_id_seq'::regclass)", org.jooq.impl.SQLDataType.BIGINT)), this, "");

    /**
     * The column <code>public.launch.uuid</code>.
     */
    public final TableField<JLaunchRecord, String> UUID = createField(DSL.name("uuid"), org.jooq.impl.SQLDataType.VARCHAR(36).nullable(false), this, "");

    /**
     * The column <code>public.launch.project_id</code>.
     */
    public final TableField<JLaunchRecord, Long> PROJECT_ID = createField(DSL.name("project_id"), org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>public.launch.user_id</code>.
     */
    public final TableField<JLaunchRecord, Long> USER_ID = createField(DSL.name("user_id"), org.jooq.impl.SQLDataType.BIGINT, this, "");

    /**
     * The column <code>public.launch.name</code>.
     */
    public final TableField<JLaunchRecord, String> NAME = createField(DSL.name("name"), org.jooq.impl.SQLDataType.VARCHAR(256).nullable(false), this, "");

    /**
     * The column <code>public.launch.description</code>.
     */
    public final TableField<JLaunchRecord, String> DESCRIPTION = createField(DSL.name("description"), org.jooq.impl.SQLDataType.CLOB, this, "");

    /**
     * The column <code>public.launch.start_time</code>.
     */
    public final TableField<JLaunchRecord, Timestamp> START_TIME = createField(DSL.name("start_time"), org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false), this, "");

    /**
     * The column <code>public.launch.end_time</code>.
     */
    public final TableField<JLaunchRecord, Timestamp> END_TIME = createField(DSL.name("end_time"), org.jooq.impl.SQLDataType.TIMESTAMP, this, "");

    /**
     * The column <code>public.launch.number</code>.
     */
    public final TableField<JLaunchRecord, Integer> NUMBER = createField(DSL.name("number"), org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>public.launch.last_modified</code>.
     */
    public final TableField<JLaunchRecord, Timestamp> LAST_MODIFIED = createField(DSL.name("last_modified"), org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.field("now()", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * The column <code>public.launch.mode</code>.
     */
    public final TableField<JLaunchRecord, JLaunchModeEnum> MODE = createField(DSL.name("mode"), org.jooq.impl.SQLDataType.VARCHAR.nullable(false).asEnumDataType(com.epam.ta.reportportal.jooq.enums.JLaunchModeEnum.class), this, "");

    /**
     * The column <code>public.launch.status</code>.
     */
    public final TableField<JLaunchRecord, JStatusEnum> STATUS = createField(DSL.name("status"), org.jooq.impl.SQLDataType.VARCHAR.nullable(false).asEnumDataType(com.epam.ta.reportportal.jooq.enums.JStatusEnum.class), this, "");

    /**
     * The column <code>public.launch.has_retries</code>.
     */
    public final TableField<JLaunchRecord, Boolean> HAS_RETRIES = createField(DSL.name("has_retries"), org.jooq.impl.SQLDataType.BOOLEAN.nullable(false).defaultValue(org.jooq.impl.DSL.field("false", org.jooq.impl.SQLDataType.BOOLEAN)), this, "");

    /**
     * The column <code>public.launch.rerun</code>.
     */
    public final TableField<JLaunchRecord, Boolean> RERUN = createField(DSL.name("rerun"), org.jooq.impl.SQLDataType.BOOLEAN.nullable(false).defaultValue(org.jooq.impl.DSL.field("false", org.jooq.impl.SQLDataType.BOOLEAN)), this, "");

    /**
     * The column <code>public.launch.approximate_duration</code>.
     */
    public final TableField<JLaunchRecord, Double> APPROXIMATE_DURATION = createField(DSL.name("approximate_duration"), org.jooq.impl.SQLDataType.DOUBLE.defaultValue(org.jooq.impl.DSL.field("0.0", org.jooq.impl.SQLDataType.DOUBLE)), this, "");

    /**
     * The column <code>public.launch.retention_policy</code>.
     */
    public final TableField<JLaunchRecord, JRetentionPolicyEnum> RETENTION_POLICY = createField(DSL.name("retention_policy"), org.jooq.impl.SQLDataType.VARCHAR.defaultValue(org.jooq.impl.DSL.field("'REGULAR'::retention_policy_enum", org.jooq.impl.SQLDataType.VARCHAR)).asEnumDataType(com.epam.ta.reportportal.jooq.enums.JRetentionPolicyEnum.class), this, "");

    /**
     * Create a <code>public.launch</code> table reference
     */
    public JLaunch() {
        this(DSL.name("launch"), null);
    }

    /**
     * Create an aliased <code>public.launch</code> table reference
     */
    public JLaunch(String alias) {
        this(DSL.name(alias), LAUNCH);
    }

    /**
     * Create an aliased <code>public.launch</code> table reference
     */
    public JLaunch(Name alias) {
        this(alias, LAUNCH);
    }

    private JLaunch(Name alias, Table<JLaunchRecord> aliased) {
        this(alias, aliased, null);
    }

    private JLaunch(Name alias, Table<JLaunchRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> JLaunch(Table<O> child, ForeignKey<O, JLaunchRecord> key) {
        super(child, key, LAUNCH);
    }

    @Override
    public Schema getSchema() {
        return JPublic.PUBLIC;
    }

    @Override
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.LAUNCH_PK, Indexes.LAUNCH_PROJECT_START_TIME_IDX, Indexes.LAUNCH_USER_IDX, Indexes.LAUNCH_UUID_KEY, Indexes.UNQ_NAME_NUMBER);
    }

    @Override
    public Identity<JLaunchRecord, Long> getIdentity() {
        return Keys.IDENTITY_LAUNCH;
    }

    @Override
    public UniqueKey<JLaunchRecord> getPrimaryKey() {
        return Keys.LAUNCH_PK;
    }

    @Override
    public List<UniqueKey<JLaunchRecord>> getKeys() {
        return Arrays.<UniqueKey<JLaunchRecord>>asList(Keys.LAUNCH_PK, Keys.LAUNCH_UUID_KEY, Keys.UNQ_NAME_NUMBER);
    }

    @Override
    public List<ForeignKey<JLaunchRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<JLaunchRecord, ?>>asList(Keys.LAUNCH__LAUNCH_PROJECT_ID_FKEY, Keys.LAUNCH__LAUNCH_USER_ID_FKEY);
    }

    public JProject project() {
        return new JProject(this, Keys.LAUNCH__LAUNCH_PROJECT_ID_FKEY);
    }

    public JUsers users() {
        return new JUsers(this, Keys.LAUNCH__LAUNCH_USER_ID_FKEY);
    }

    @Override
    public JLaunch as(String alias) {
        return new JLaunch(DSL.name(alias), this);
    }

    @Override
    public JLaunch as(Name alias) {
        return new JLaunch(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public JLaunch rename(String name) {
        return new JLaunch(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public JLaunch rename(Name name) {
        return new JLaunch(name, null);
    }

    // -------------------------------------------------------------------------
    // Row16 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row16<Long, String, Long, Long, String, String, Timestamp, Timestamp, Integer, Timestamp, JLaunchModeEnum, JStatusEnum, Boolean, Boolean, Double, JRetentionPolicyEnum> fieldsRow() {
        return (Row16) super.fieldsRow();
    }
}
