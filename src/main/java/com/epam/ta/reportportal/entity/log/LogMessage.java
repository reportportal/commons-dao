package com.epam.ta.reportportal.entity.log;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

public class LogMessage implements Serializable {

  private Long id;
  private LocalDateTime logTime;
  private String logMessage;
  private Long itemId;
  private Long launchId;
  private Long projectId;

  public LogMessage(Long id, LocalDateTime logTime, String logMessage, Long itemId, Long launchId,
      Long projectId) {
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
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
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
