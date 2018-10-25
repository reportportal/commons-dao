/*
 * This file is generated by jOOQ.
 */
package com.epam.ta.reportportal.jooq.tables;


import com.epam.ta.reportportal.jooq.Indexes;
import com.epam.ta.reportportal.jooq.JPublic;
import com.epam.ta.reportportal.jooq.Keys;
import com.epam.ta.reportportal.jooq.tables.records.JStatisticsRecord;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.Index;
import org.jooq.Name;
import org.jooq.Record;
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
        "jOOQ version:3.11.4"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class JStatistics extends TableImpl<JStatisticsRecord> {

    private static final long serialVersionUID = 1129965131;

    /**
     * The reference instance of <code>public.statistics</code>
     */
    public static final JStatistics STATISTICS = new JStatistics();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<JStatisticsRecord> getRecordType() {
        return JStatisticsRecord.class;
    }

    /**
     * The column <code>public.statistics.s_id</code>.
     */
    public final TableField<JStatisticsRecord, Long> S_ID = createField("s_id", org.jooq.impl.SQLDataType.BIGINT.nullable(false).defaultValue(org.jooq.impl.DSL.field("nextval('statistics_s_id_seq'::regclass)", org.jooq.impl.SQLDataType.BIGINT)), this, "");

    /**
     * The column <code>public.statistics.s_counter</code>.
     */
    public final TableField<JStatisticsRecord, Integer> S_COUNTER = createField("s_counter", org.jooq.impl.SQLDataType.INTEGER.defaultValue(org.jooq.impl.DSL.field("0", org.jooq.impl.SQLDataType.INTEGER)), this, "");

    /**
     * The column <code>public.statistics.launch_id</code>.
     */
    public final TableField<JStatisticsRecord, Long> LAUNCH_ID = createField("launch_id", org.jooq.impl.SQLDataType.BIGINT, this, "");

    /**
     * The column <code>public.statistics.item_id</code>.
     */
    public final TableField<JStatisticsRecord, Long> ITEM_ID = createField("item_id", org.jooq.impl.SQLDataType.BIGINT, this, "");

    /**
     * The column <code>public.statistics.statistics_field_id</code>.
     */
    public final TableField<JStatisticsRecord, Long> STATISTICS_FIELD_ID = createField("statistics_field_id", org.jooq.impl.SQLDataType.BIGINT, this, "");

    /**
     * Create a <code>public.statistics</code> table reference
     */
    public JStatistics() {
        this(DSL.name("statistics"), null);
    }

    /**
     * Create an aliased <code>public.statistics</code> table reference
     */
    public JStatistics(String alias) {
        this(DSL.name(alias), STATISTICS);
    }

    /**
     * Create an aliased <code>public.statistics</code> table reference
     */
    public JStatistics(Name alias) {
        this(alias, STATISTICS);
    }

    private JStatistics(Name alias, Table<JStatisticsRecord> aliased) {
        this(alias, aliased, null);
    }

    private JStatistics(Name alias, Table<JStatisticsRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> JStatistics(Table<O> child, ForeignKey<O, JStatisticsRecord> key) {
        super(child, key, STATISTICS);
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
        return Arrays.<Index>asList(Indexes.STATISTICS_PK, Indexes.UNIQUE_STATS_ITEM, Indexes.UNIQUE_STATS_LAUNCH);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<JStatisticsRecord, Long> getIdentity() {
        return Keys.IDENTITY_STATISTICS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<JStatisticsRecord> getPrimaryKey() {
        return Keys.STATISTICS_PK;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<JStatisticsRecord>> getKeys() {
        return Arrays.<UniqueKey<JStatisticsRecord>>asList(Keys.STATISTICS_PK, Keys.UNIQUE_STATS_LAUNCH, Keys.UNIQUE_STATS_ITEM);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<JStatisticsRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<JStatisticsRecord, ?>>asList(Keys.STATISTICS__STATISTICS_LAUNCH_ID_FKEY, Keys.STATISTICS__STATISTICS_ITEM_ID_FKEY, Keys.STATISTICS__STATISTICS_STATISTICS_FIELD_ID_FKEY);
    }

    public JLaunch launch() {
        return new JLaunch(this, Keys.STATISTICS__STATISTICS_LAUNCH_ID_FKEY);
    }

    public JTestItem testItem() {
        return new JTestItem(this, Keys.STATISTICS__STATISTICS_ITEM_ID_FKEY);
    }

    public JStatisticsField statisticsField() {
        return new JStatisticsField(this, Keys.STATISTICS__STATISTICS_STATISTICS_FIELD_ID_FKEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JStatistics as(String alias) {
        return new JStatistics(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JStatistics as(Name alias) {
        return new JStatistics(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public JStatistics rename(String name) {
        return new JStatistics(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public JStatistics rename(Name name) {
        return new JStatistics(name, null);
    }
}
