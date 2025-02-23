/*
 * This file is generated by jOOQ.
 */
package com.epam.ta.reportportal.jooq.enums;


import com.epam.ta.reportportal.jooq.JPublic;

import org.jooq.Catalog;
import org.jooq.EnumType;
import org.jooq.Schema;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes", "this-escape" })
public enum JStatusEnum implements EnumType {

    CANCELLED("CANCELLED"),

    FAILED("FAILED"),

    INTERRUPTED("INTERRUPTED"),

    IN_PROGRESS("IN_PROGRESS"),

    PASSED("PASSED"),

    RESETED("RESETED"),

    SKIPPED("SKIPPED"),

    STOPPED("STOPPED"),

    INFO("INFO"),

    WARN("WARN");

    private final String literal;

    private JStatusEnum(String literal) {
        this.literal = literal;
    }

    @Override
    public Catalog getCatalog() {
        return getSchema().getCatalog();
    }

    @Override
    public Schema getSchema() {
        return JPublic.PUBLIC;
    }

    @Override
    public String getName() {
        return "status_enum";
    }

    @Override
    public String getLiteral() {
        return literal;
    }

    /**
     * Lookup a value of this EnumType by its literal. Returns
     * <code>null</code>, if no such value could be found, see {@link
     * EnumType#lookupLiteral(Class, String)}.
     */
    public static JStatusEnum lookupLiteral(String literal) {
        return EnumType.lookupLiteral(JStatusEnum.class, literal);
    }
}
