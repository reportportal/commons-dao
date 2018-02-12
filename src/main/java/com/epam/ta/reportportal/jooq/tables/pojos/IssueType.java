/*
 * This file is generated by jOOQ.
*/
package com.epam.ta.reportportal.jooq.tables.pojos;

import com.epam.ta.reportportal.jooq.enums.IssueGroupEnum;

import javax.annotation.Generated;
import java.io.Serializable;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.4"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class IssueType implements Serializable {

    private static final long serialVersionUID = -964649437;

    private Integer        id;
    private IssueGroupEnum issueGroup;
    private String         locator;
    private String         longName;
    private String         shortName;
    private String         hexColor;

    public IssueType() {}

    public IssueType(IssueType value) {
        this.id = value.id;
        this.issueGroup = value.issueGroup;
        this.locator = value.locator;
        this.longName = value.longName;
        this.shortName = value.shortName;
        this.hexColor = value.hexColor;
    }

    public IssueType(
        Integer        id,
        IssueGroupEnum issueGroup,
        String         locator,
        String         longName,
        String         shortName,
        String         hexColor
    ) {
        this.id = id;
        this.issueGroup = issueGroup;
        this.locator = locator;
        this.longName = longName;
        this.shortName = shortName;
        this.hexColor = hexColor;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public IssueGroupEnum getIssueGroup() {
        return this.issueGroup;
    }

    public void setIssueGroup(IssueGroupEnum issueGroup) {
        this.issueGroup = issueGroup;
    }

    public String getLocator() {
        return this.locator;
    }

    public void setLocator(String locator) {
        this.locator = locator;
    }

    public String getLongName() {
        return this.longName;
    }

    public void setLongName(String longName) {
        this.longName = longName;
    }

    public String getShortName() {
        return this.shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getHexColor() {
        return this.hexColor;
    }

    public void setHexColor(String hexColor) {
        this.hexColor = hexColor;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("IssueType (");

        sb.append(id);
        sb.append(", ").append(issueGroup);
        sb.append(", ").append(locator);
        sb.append(", ").append(longName);
        sb.append(", ").append(shortName);
        sb.append(", ").append(hexColor);

        sb.append(")");
        return sb.toString();
    }
}
