/*
 * This file is generated by jOOQ.
 */
package com.epam.ta.reportportal.jooq.tables;

import com.epam.ta.reportportal.jooq.JPublic;
import com.epam.ta.reportportal.jooq.Keys;
import com.epam.ta.reportportal.jooq.tables.records.JDefectFormFieldValueRecord;
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
        "jOOQ version:3.11.4"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class JDefectFormFieldValue extends TableImpl<JDefectFormFieldValueRecord> {

    private static final long serialVersionUID = 388601645;

    /**
     * The reference instance of <code>public.defect_form_field_value</code>
     */
    public static final JDefectFormFieldValue DEFECT_FORM_FIELD_VALUE = new JDefectFormFieldValue();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<JDefectFormFieldValueRecord> getRecordType() {
        return JDefectFormFieldValueRecord.class;
    }

    /**
     * The column <code>public.defect_form_field_value.id</code>.
     */
    public final TableField<JDefectFormFieldValueRecord, Long> ID = createField("id", org.jooq.impl.SQLDataType.BIGINT, this, "");

    /**
     * The column <code>public.defect_form_field_value.values</code>.
     */
    public final TableField<JDefectFormFieldValueRecord, String> VALUES = createField("values", org.jooq.impl.SQLDataType.VARCHAR.nullable(false), this, "");

    /**
     * Create a <code>public.defect_form_field_value</code> table reference
     */
    public JDefectFormFieldValue() {
        this(DSL.name("defect_form_field_value"), null);
    }

    /**
     * Create an aliased <code>public.defect_form_field_value</code> table reference
     */
    public JDefectFormFieldValue(String alias) {
        this(DSL.name(alias), DEFECT_FORM_FIELD_VALUE);
    }

    /**
     * Create an aliased <code>public.defect_form_field_value</code> table reference
     */
    public JDefectFormFieldValue(Name alias) {
        this(alias, DEFECT_FORM_FIELD_VALUE);
    }

    private JDefectFormFieldValue(Name alias, Table<JDefectFormFieldValueRecord> aliased) {
        this(alias, aliased, null);
    }

    private JDefectFormFieldValue(Name alias, Table<JDefectFormFieldValueRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> JDefectFormFieldValue(Table<O> child, ForeignKey<O, JDefectFormFieldValueRecord> key) {
        super(child, key, DEFECT_FORM_FIELD_VALUE);
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
    public List<ForeignKey<JDefectFormFieldValueRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<JDefectFormFieldValueRecord, ?>>asList(Keys.DEFECT_FORM_FIELD_VALUE__DEFECT_FORM_FIELD_VALUE_ID_FKEY);
    }

    public JDefectFormField defectFormField() {
        return new JDefectFormField(this, Keys.DEFECT_FORM_FIELD_VALUE__DEFECT_FORM_FIELD_VALUE_ID_FKEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JDefectFormFieldValue as(String alias) {
        return new JDefectFormFieldValue(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JDefectFormFieldValue as(Name alias) {
        return new JDefectFormFieldValue(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public JDefectFormFieldValue rename(String name) {
        return new JDefectFormFieldValue(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public JDefectFormFieldValue rename(Name name) {
        return new JDefectFormFieldValue(name, null);
    }
}
