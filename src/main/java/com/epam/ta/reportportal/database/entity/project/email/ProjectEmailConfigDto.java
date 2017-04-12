package com.epam.ta.reportportal.database.entity.project.email;

import java.io.Serializable;
import java.util.List;

public class ProjectEmailConfigDto implements Serializable {

    private Boolean emailEnabled;

    private String from;

    private List<EmailSenderCaseDto> emailSenderCaseDtos;

    public ProjectEmailConfigDto() {
    }

    public ProjectEmailConfigDto(Boolean emailEnabled, String from, List<EmailSenderCaseDto> emailSenderCaseDtos) {
        this.emailEnabled = emailEnabled;
        this.from = from;
        this.emailSenderCaseDtos = emailSenderCaseDtos;
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

    public List<EmailSenderCaseDto> getEmailSenderCaseDtos() {
        return emailSenderCaseDtos;
    }

    public void setEmailSenderCaseDtos(List<EmailSenderCaseDto> emailSenderCaseDtos) {
        this.emailSenderCaseDtos = emailSenderCaseDtos;
    }

    @Override
    public String toString() {
        return "ProjectEmailConfigDto{" +
                "emailEnabled=" + emailEnabled +
                ", from='" + from + '\'' +
                ", emailSenderCaseDtos=" + emailSenderCaseDtos +
                '}';
    }
}
