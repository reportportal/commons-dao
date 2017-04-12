package com.epam.ta.reportportal.database.entity.project.email;

import java.util.List;

public class ProjectEmailConfig {

    private Boolean emailEnabled;

    private String from;

    private List<EmailSenderCase> emailSenderCases;

    public ProjectEmailConfig() {
    }

    public ProjectEmailConfig(Boolean emailEnabled, String from, List<EmailSenderCase> emailSenderCases) {
        this.emailEnabled = emailEnabled;
        this.from = from;
        this.emailSenderCases = emailSenderCases;
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

    public List<EmailSenderCase> getEmailSenderCases() {
        return emailSenderCases;
    }

    public void setEmailSenderCases(List<EmailSenderCase> emailSenderCases) {
        this.emailSenderCases = emailSenderCases;
    }

    @Override
    public String toString() {
        return "ProjectEmailConfig{" +
                "emailEnabled=" + emailEnabled +
                ", from='" + from + '\'' +
                ", emailSenderCases=" + emailSenderCases +
                '}';
    }
}
