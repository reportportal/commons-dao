/*
 * This file is generated by jOOQ.
 */
package com.epam.ta.reportportal.jooq.udt;

import com.epam.ta.reportportal.jooq.JPublic;
import com.epam.ta.reportportal.jooq.udt.records.JTablefuncCrosstab_3Record;
import org.jooq.Schema;
import org.jooq.UDTField;
import org.jooq.impl.UDTImpl;

import javax.annotation.Generated;


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
public class JTablefuncCrosstab_3 extends UDTImpl<JTablefuncCrosstab_3Record> {

    private static final long serialVersionUID = 1814815138;

    /**
     * The reference instance of <code>public.tablefunc_crosstab_3</code>
     */
    public static final JTablefuncCrosstab_3 TABLEFUNC_CROSSTAB_3 = new JTablefuncCrosstab_3();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<JTablefuncCrosstab_3Record> getRecordType() {
        return JTablefuncCrosstab_3Record.class;
    }

    /**
     * The attribute <code>public.tablefunc_crosstab_3.row_name</code>.
     */
    public static final UDTField<JTablefuncCrosstab_3Record, String> ROW_NAME = createField("row_name", org.jooq.impl.SQLDataType.CLOB, TABLEFUNC_CROSSTAB_3, "");

    /**
     * The attribute <code>public.tablefunc_crosstab_3.category_1</code>.
     */
    public static final UDTField<JTablefuncCrosstab_3Record, String> CATEGORY_1 = createField("category_1", org.jooq.impl.SQLDataType.CLOB, TABLEFUNC_CROSSTAB_3, "");

    /**
     * The attribute <code>public.tablefunc_crosstab_3.category_2</code>.
     */
    public static final UDTField<JTablefuncCrosstab_3Record, String> CATEGORY_2 = createField("category_2", org.jooq.impl.SQLDataType.CLOB, TABLEFUNC_CROSSTAB_3, "");

    /**
     * The attribute <code>public.tablefunc_crosstab_3.category_3</code>.
     */
    public static final UDTField<JTablefuncCrosstab_3Record, String> CATEGORY_3 = createField("category_3", org.jooq.impl.SQLDataType.CLOB, TABLEFUNC_CROSSTAB_3, "");

    /**
     * No further instances allowed
     */
    private JTablefuncCrosstab_3() {
        super("tablefunc_crosstab_3", null, null, false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return JPublic.PUBLIC;
    }
}
