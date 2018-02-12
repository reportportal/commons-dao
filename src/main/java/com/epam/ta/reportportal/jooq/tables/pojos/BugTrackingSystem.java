/*
 * This file is generated by jOOQ.
*/
package com.epam.ta.reportportal.jooq.tables.pojos;

import com.epam.ta.reportportal.jooq.enums.BtsTypeEnum;

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
public class BugTrackingSystem implements Serializable {

    private static final long serialVersionUID = -85209833;

    private Integer     id;
    private String      url;
    private BtsTypeEnum type;

    public BugTrackingSystem() {}

    public BugTrackingSystem(BugTrackingSystem value) {
        this.id = value.id;
        this.url = value.url;
        this.type = value.type;
    }

    public BugTrackingSystem(
        Integer     id,
        String      url,
        BtsTypeEnum type
    ) {
        this.id = id;
        this.url = url;
        this.type = type;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public BtsTypeEnum getType() {
        return this.type;
    }

    public void setType(BtsTypeEnum type) {
        this.type = type;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("BugTrackingSystem (");

        sb.append(id);
        sb.append(", ").append(url);
        sb.append(", ").append(type);

        sb.append(")");
        return sb.toString();
    }
}
