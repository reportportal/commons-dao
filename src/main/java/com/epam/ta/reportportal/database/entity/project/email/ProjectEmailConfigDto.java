package com.epam.ta.reportportal.database.entity.project.email;

import java.io.Serializable;
import java.util.List;

public class ProjectEmailConfigDto implements Serializable {

    private Boolean emailEnabled;

    private String from;

    private List<EmailSenderCaseDto> emailCases;

    public ProjectEmailConfigDto() {
    }

    public ProjectEmailConfigDto(Boolean emailEnabled, String from, List<EmailSenderCaseDto> emailCases) {
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

    public List<EmailSenderCaseDto> getEmailCases() {
        return emailCases;
    }

    public void setEmailCases(List<EmailSenderCaseDto> emailCases) {
        this.emailCases = emailCases;
    }

    @Override
    public String toString() {
        return "ProjectEmailConfigDto{" +
                "emailEnabled=" + emailEnabled +
                ", from='" + from + '\'' +
                ", emailCases=" + emailCases +
                '}';
    }
}
