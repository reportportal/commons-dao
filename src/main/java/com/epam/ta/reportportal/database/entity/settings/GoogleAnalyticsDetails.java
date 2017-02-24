package com.epam.ta.reportportal.database.entity.settings;

import com.google.common.base.MoreObjects;

import java.io.Serializable;

public class GoogleAnalyticsDetails implements Serializable {

    private String id;

    private Boolean enabled;

    static final String TYPE = "google";

    public GoogleAnalyticsDetails() {
    }

    public GoogleAnalyticsDetails(String id, Boolean enabled) {
        this.id = id;
        this.enabled = enabled;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("id", id).add("type", TYPE)
                .add("enabled", enabled).toString();
    }
}
