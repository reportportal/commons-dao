/*
 * This file is generated by jOOQ.
 */
package com.epam.ta.reportportal.jooq.enums;


import com.epam.ta.reportportal.jooq.JPublic;

import javax.annotation.processing.Generated;

import org.jooq.Catalog;
import org.jooq.EnumType;
import org.jooq.Schema;


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
public enum JFilterConditionEnum implements EnumType {

    EQUALS("EQUALS"),

    NOT_EQUALS("NOT_EQUALS"),

    CONTAINS("CONTAINS"),

    EXISTS("EXISTS"),

    IN("IN"),

    HAS("HAS"),

    GREATER_THAN("GREATER_THAN"),

    GREATER_THAN_OR_EQUALS("GREATER_THAN_OR_EQUALS"),

    LOWER_THAN("LOWER_THAN"),

    LOWER_THAN_OR_EQUALS("LOWER_THAN_OR_EQUALS"),

    BETWEEN("BETWEEN"),

    ANY("ANY");

    private final String literal;

    private JFilterConditionEnum(String literal) {
        this.literal = literal;
    }

    @Override
    public Catalog getCatalog() {
        return getSchema() == null ? null : getSchema().getCatalog();
    }

    @Override
    public Schema getSchema() {
        return JPublic.PUBLIC;
    }

    @Override
    public String getName() {
        return "filter_condition_enum";
    }

    @Override
    public String getLiteral() {
        return literal;
    }
}
