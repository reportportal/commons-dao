/*
 * This file is generated by jOOQ.
 */
package com.epam.ta.reportportal.jooq.enums;


import com.epam.ta.reportportal.jooq.JPublic;

import javax.annotation.Generated;

import org.jooq.Catalog;
import org.jooq.EnumType;
import org.jooq.Schema;


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
public enum JActivityEntityEnum implements EnumType {

    LAUNCH("LAUNCH"),

    ITEM("ITEM"),

    DASHBOARD("DASHBOARD"),

    DEFECT_TYPE("DEFECT_TYPE"),

    EMAIL_CONFIG("EMAIL_CONFIG"),

    FILTER("FILTER"),

    IMPORT("IMPORT"),

    INTEGRATION("INTEGRATION"),

    ITEM_ISSUE("ITEM_ISSUE"),

    PROJECT("PROJECT"),

    SHARING("SHARING"),

    TICKET("TICKET"),

    USER("USER"),

    WIDGET("WIDGET");

    private final String literal;

    private JActivityEntityEnum(String literal) {
        this.literal = literal;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Catalog getCatalog() {
        return getSchema() == null ? null : getSchema().getCatalog();
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
    public String getName() {
        return "activity_entity_enum";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getLiteral() {
        return literal;
    }
}
