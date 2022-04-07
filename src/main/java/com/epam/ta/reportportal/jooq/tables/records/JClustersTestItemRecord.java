/*
 * This file is generated by jOOQ.
 */
package com.epam.ta.reportportal.jooq.tables.records;


import com.epam.ta.reportportal.jooq.tables.JClustersTestItem;

import javax.annotation.processing.Generated;

import org.jooq.Field;
import org.jooq.Record2;
import org.jooq.Row2;
import org.jooq.impl.TableRecordImpl;


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
public class JClustersTestItemRecord extends TableRecordImpl<JClustersTestItemRecord> implements Record2<Long, Long> {

    private static final long serialVersionUID = -1142553978;

    /**
     * Setter for <code>public.clusters_test_item.cluster_id</code>.
     */
    public void setClusterId(Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.clusters_test_item.cluster_id</code>.
     */
    public Long getClusterId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>public.clusters_test_item.item_id</code>.
     */
    public void setItemId(Long value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.clusters_test_item.item_id</code>.
     */
    public Long getItemId() {
        return (Long) get(1);
    }

    // -------------------------------------------------------------------------
    // Record2 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row2<Long, Long> fieldsRow() {
        return (Row2) super.fieldsRow();
    }

    @Override
    public Row2<Long, Long> valuesRow() {
        return (Row2) super.valuesRow();
    }

    @Override
    public Field<Long> field1() {
        return JClustersTestItem.CLUSTERS_TEST_ITEM.CLUSTER_ID;
    }

    @Override
    public Field<Long> field2() {
        return JClustersTestItem.CLUSTERS_TEST_ITEM.ITEM_ID;
    }

    @Override
    public Long component1() {
        return getClusterId();
    }

    @Override
    public Long component2() {
        return getItemId();
    }

    @Override
    public Long value1() {
        return getClusterId();
    }

    @Override
    public Long value2() {
        return getItemId();
    }

    @Override
    public JClustersTestItemRecord value1(Long value) {
        setClusterId(value);
        return this;
    }

    @Override
    public JClustersTestItemRecord value2(Long value) {
        setItemId(value);
        return this;
    }

    @Override
    public JClustersTestItemRecord values(Long value1, Long value2) {
        value1(value1);
        value2(value2);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached JClustersTestItemRecord
     */
    public JClustersTestItemRecord() {
        super(JClustersTestItem.CLUSTERS_TEST_ITEM);
    }

    /**
     * Create a detached, initialised JClustersTestItemRecord
     */
    public JClustersTestItemRecord(Long clusterId, Long itemId) {
        super(JClustersTestItem.CLUSTERS_TEST_ITEM);

        set(0, clusterId);
        set(1, itemId);
    }
}