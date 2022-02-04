package com.epam.ta.reportportal.entity.log;

import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.Id;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * LogMessage - entity for storing message part of log + some additional info.
 * indexName - is only prefix, real index name in Elasticsearch will be indexName + projectId.
 */
@Document(indexName = "log_message_store-", type="log_message", createIndex = false)
public class LogMessage implements Serializable {

    @Id
    @Field(type = FieldType.Long)
    private Long id;
    @Field(type = FieldType.Date, format = DateFormat.date_optional_time)
    private LocalDateTime logTime;
    @Field(type = FieldType.Text)
    private String logMessage;
    @Field(type = FieldType.Long)
    private Long itemId;
    @Field(type = FieldType.Long)
    private Long launchId;
    @Field(type = FieldType.Long)
    private Long projectId;

    public LogMessage(Long id, LocalDateTime logTime, String logMessage, Long itemId, Long launchId, Long projectId) {
        this.id = id;
        this.logTime = logTime;
        this.logMessage = logMessage;
        this.itemId = itemId;
        this.launchId = launchId;
        this.projectId = projectId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getLogTime() {
        return logTime;
    }

    public void setLogTime(LocalDateTime logTime) {
        this.logTime = logTime;
    }

    public String getLogMessage() {
        return logMessage;
    }

    public void setLogMessage(String logMessage) {
        this.logMessage = logMessage;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Long getLaunchId() {
        return launchId;
    }

    public void setLaunchId(Long launchId) {
        this.launchId = launchId;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LogMessage that = (LogMessage) o;
        return Objects.equals(id, that.id) && Objects.equals(logTime, that.logTime)
                && Objects.equals(logMessage, that.logMessage) && Objects.equals(itemId, that.itemId)
                && Objects.equals(launchId, that.launchId) && Objects.equals(projectId, that.projectId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, logTime, logMessage, itemId, launchId, projectId);
    }
}
