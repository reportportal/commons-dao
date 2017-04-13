package com.epam.ta.reportportal.database.entity.project.email;

import java.io.Serializable;
import java.util.List;

public class ProjectEmailConfig implements Serializable {

    private Boolean emailEnabled;

    private String from;

    private List<EmailSenderCase> emailCases;

    public ProjectEmailConfig() {
    }

    public ProjectEmailConfig(Boolean emailEnabled, String from, List<EmailSenderCase> emailCases) {
        this.emailEnabled = emailEnabled;
        this.from = from;
        this.emailCases = emailCases;
    }

    public Boolean getEmailEnabled() {
        return emailEnabled;
    }

    public void setEmailEnabled(Boolean emailEnabled) {
        this.emailEnabled = emailEnabled;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public List<EmailSenderCase> getEmailCases() {
        return emailCases;
    }

    public void setEmailCases(List<EmailSenderCase> emailCases) {
        this.emailCases = emailCases;
    }

    @Override
    public String toString() {
        return "ProjectEmailConfig{" +
                "emailEnabled=" + emailEnabled +
                ", from='" + from + '\'' +
                ", emailCases=" + emailCases +
                '}';
    }
}
