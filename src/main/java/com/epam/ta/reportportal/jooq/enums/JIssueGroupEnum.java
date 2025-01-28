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
public enum JIssueGroupEnum implements EnumType {

    PRODUCT_BUG("PRODUCT_BUG"),

    AUTOMATION_BUG("AUTOMATION_BUG"),

    SYSTEM_ISSUE("SYSTEM_ISSUE"),

    TO_INVESTIGATE("TO_INVESTIGATE"),

    NO_DEFECT("NO_DEFECT");

    private final String literal;

    private JIssueGroupEnum(String literal) {
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
        return "issue_group_enum";
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
    public static JIssueGroupEnum lookupLiteral(String literal) {
        return EnumType.lookupLiteral(JIssueGroupEnum.class, literal);
    }
}
