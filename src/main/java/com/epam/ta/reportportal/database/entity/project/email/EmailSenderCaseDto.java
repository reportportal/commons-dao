package com.epam.ta.reportportal.database.entity.project.email;

import java.io.Serializable;
import java.util.List;

public class EmailSenderCaseDto implements Serializable{

    private List<String> recipients;

    private String sendCase;

    private List<String> launchNames;

    private List<String> tags;

    public EmailSenderCaseDto() {
    }

    public EmailSenderCaseDto(List<String> recipients, String sendCase, List<String> launchNames, List<String> tags) {
        this.recipients = recipients;
        this.sendCase = sendCase;
        this.launchNames = launchNames;
        this.tags = tags;
    }

    public List<String> getRecipients() {
        return recipients;
    }

    public void setRecipients(List<String> recipients) {
        this.recipients = recipients;
    }

    public String getSendCase() {
        return sendCase;
    }

    public void setSendCase(String sendCase) {
        this.sendCase = sendCase;
    }

    public List<String> getLaunchNames() {
        return launchNames;
    }

    public void setLaunchNames(List<String> launchNames) {
        this.launchNames = launchNames;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EmailSenderCaseDto that = (EmailSenderCaseDto) o;

        if (recipients != null ? !recipients.equals(that.recipients) : that.recipients != null) return false;
        if (sendCase != null ? !sendCase.equals(that.sendCase) : that.sendCase != null) return false;
        if (launchNames != null ? !launchNames.equals(that.launchNames) : that.launchNames != null) return false;
        return tags != null ? tags.equals(that.tags) : that.tags == null;
    }

    @Override
    public int hashCode() {
        int result = recipients != null ? recipients.hashCode() : 0;
        result = 31 * result + (sendCase != null ? sendCase.hashCode() : 0);
        result = 31 * result + (launchNames != null ? launchNames.hashCode() : 0);
        result = 31 * result + (tags != null ? tags.hashCode() : 0);
        return result;
    }
}
