/*
 * This file is generated by jOOQ.
 */
package com.epam.ta.reportportal.jooq.udt.records;


import com.epam.ta.reportportal.jooq.udt.JTablefuncCrosstab_3;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record4;
import org.jooq.Row4;
import org.jooq.impl.UDTRecordImpl;


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
public class JTablefuncCrosstab_3Record extends UDTRecordImpl<JTablefuncCrosstab_3Record> implements Record4<String, String, String, String> {

    private static final long serialVersionUID = -1827143986;

    /**
     * Setter for <code>public.tablefunc_crosstab_3.row_name</code>.
     */
    public void setRowName(String value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.tablefunc_crosstab_3.row_name</code>.
     */
    public String getRowName() {
        return (String) get(0);
    }

    /**
     * Setter for <code>public.tablefunc_crosstab_3.category_1</code>.
     */
    public void setCategory_1(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.tablefunc_crosstab_3.category_1</code>.
     */
    public String getCategory_1() {
        return (String) get(1);
    }

    /**
     * Setter for <code>public.tablefunc_crosstab_3.category_2</code>.
     */
    public void setCategory_2(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.tablefunc_crosstab_3.category_2</code>.
     */
    public String getCategory_2() {
        return (String) get(2);
    }

    /**
     * Setter for <code>public.tablefunc_crosstab_3.category_3</code>.
     */
    public void setCategory_3(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>public.tablefunc_crosstab_3.category_3</code>.
     */
    public String getCategory_3() {
        return (String) get(3);
    }

    // -------------------------------------------------------------------------
    // Record4 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row4<String, String, String, String> fieldsRow() {
        return (Row4) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row4<String, String, String, String> valuesRow() {
        return (Row4) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field1() {
        return JTablefuncCrosstab_3.ROW_NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return JTablefuncCrosstab_3.CATEGORY_1;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return JTablefuncCrosstab_3.CATEGORY_2;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return JTablefuncCrosstab_3.CATEGORY_3;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component1() {
        return getRowName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component2() {
        return getCategory_1();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component3() {
        return getCategory_2();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component4() {
        return getCategory_3();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value1() {
        return getRowName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value2() {
        return getCategory_1();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value3() {
        return getCategory_2();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value4() {
        return getCategory_3();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JTablefuncCrosstab_3Record value1(String value) {
        setRowName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JTablefuncCrosstab_3Record value2(String value) {
        setCategory_1(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JTablefuncCrosstab_3Record value3(String value) {
        setCategory_2(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JTablefuncCrosstab_3Record value4(String value) {
        setCategory_3(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JTablefuncCrosstab_3Record values(String value1, String value2, String value3, String value4) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached JTablefuncCrosstab_3Record
     */
    public JTablefuncCrosstab_3Record() {
        super(JTablefuncCrosstab_3.TABLEFUNC_CROSSTAB_3);
    }

    /**
     * Create a detached, initialised JTablefuncCrosstab_3Record
     */
    public JTablefuncCrosstab_3Record(String rowName, String category_1, String category_2, String category_3) {
        super(JTablefuncCrosstab_3.TABLEFUNC_CROSSTAB_3);

        set(0, rowName);
        set(1, category_1);
        set(2, category_2);
        set(3, category_3);
    }
}
