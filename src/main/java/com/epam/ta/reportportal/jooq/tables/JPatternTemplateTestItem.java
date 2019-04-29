/*
 * This file is generated by jOOQ.
 */
package com.epam.ta.reportportal.jooq.tables;

import com.epam.ta.reportportal.jooq.JPublic;
import com.epam.ta.reportportal.jooq.Keys;
import com.epam.ta.reportportal.jooq.tables.records.JPatternTemplateTestItemRecord;
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
public class JPatternTemplateTestItem extends TableImpl<JPatternTemplateTestItemRecord> {

    private static final long serialVersionUID = -1995352225;

    /**
     * The reference instance of <code>public.pattern_template_test_item</code>
     */
    public static final JPatternTemplateTestItem PATTERN_TEMPLATE_TEST_ITEM = new JPatternTemplateTestItem();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<JPatternTemplateTestItemRecord> getRecordType() {
        return JPatternTemplateTestItemRecord.class;
    }

    /**
     * The column <code>public.pattern_template_test_item.pattern_id</code>.
     */
    public final TableField<JPatternTemplateTestItemRecord, Long> PATTERN_ID = createField("pattern_id", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>public.pattern_template_test_item.item_id</code>.
     */
    public final TableField<JPatternTemplateTestItemRecord, Long> ITEM_ID = createField("item_id", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * Create a <code>public.pattern_template_test_item</code> table reference
     */
    public JPatternTemplateTestItem() {
        this(DSL.name("pattern_template_test_item"), null);
    }

    /**
     * Create an aliased <code>public.pattern_template_test_item</code> table reference
     */
    public JPatternTemplateTestItem(String alias) {
        this(DSL.name(alias), PATTERN_TEMPLATE_TEST_ITEM);
    }

    /**
     * Create an aliased <code>public.pattern_template_test_item</code> table reference
     */
    public JPatternTemplateTestItem(Name alias) {
        this(alias, PATTERN_TEMPLATE_TEST_ITEM);
    }

    private JPatternTemplateTestItem(Name alias, Table<JPatternTemplateTestItemRecord> aliased) {
        this(alias, aliased, null);
    }

    private JPatternTemplateTestItem(Name alias, Table<JPatternTemplateTestItemRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> JPatternTemplateTestItem(Table<O> child, ForeignKey<O, JPatternTemplateTestItemRecord> key) {
        super(child, key, PATTERN_TEMPLATE_TEST_ITEM);
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
    public List<ForeignKey<JPatternTemplateTestItemRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<JPatternTemplateTestItemRecord, ?>>asList(Keys.PATTERN_TEMPLATE_TEST_ITEM__PATTERN_TEMPLATE_TEST_ITEM_PATTERN_ID_FKEY, Keys.PATTERN_TEMPLATE_TEST_ITEM__PATTERN_TEMPLATE_TEST_ITEM_ITEM_ID_FKEY);
    }

    public JPatternTemplate patternTemplate() {
        return new JPatternTemplate(this, Keys.PATTERN_TEMPLATE_TEST_ITEM__PATTERN_TEMPLATE_TEST_ITEM_PATTERN_ID_FKEY);
    }

    public JTestItem testItem() {
        return new JTestItem(this, Keys.PATTERN_TEMPLATE_TEST_ITEM__PATTERN_TEMPLATE_TEST_ITEM_ITEM_ID_FKEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JPatternTemplateTestItem as(String alias) {
        return new JPatternTemplateTestItem(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JPatternTemplateTestItem as(Name alias) {
        return new JPatternTemplateTestItem(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public JPatternTemplateTestItem rename(String name) {
        return new JPatternTemplateTestItem(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public JPatternTemplateTestItem rename(Name name) {
        return new JPatternTemplateTestItem(name, null);
    }
}