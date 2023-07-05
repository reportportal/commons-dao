/*
 * Copyright 2019 EPAM Systems
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.epam.ta.reportportal.entity.activity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

/**
 * Activity table entity
 *
 * @author Ryhor Kukharenka
 */
@Entity
@Table(name = "activity", schema = "public")
@TypeDef(name = "activityDetails", typeClass = ActivityDetails.class)
public class Activity implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", unique = true, nullable = false, precision = 64)
  private Long id;

  @Column(name = "created_at", nullable = false)
  private LocalDateTime createdAt;

  @Column(name = "action", nullable = false)
  @Enumerated(EnumType.STRING)
  private EventAction action;

  @Column(name = "event_name", nullable = false)
  private String eventName;

  @Column(name = "priority", nullable = false)
  @Enumerated(EnumType.STRING)
  private EventPriority priority;

  @Column(name = "object_id")
  private Long objectId;

  @Column(name = "object_name", nullable = false)
  private String objectName;

  @Enumerated(EnumType.STRING)
  @Column(name = "object_type", nullable = false)
  private EventObject objectType;

  @Column(name = "project_id")
  private Long projectId;

  @Column(name = "details")
  @Type(type = "activityDetails")
  private ActivityDetails details;

  @Column(name = "subject_id", precision = 32)
  private Long subjectId;

  @Column(name = "subject_name", nullable = false)
  private String subjectName;

  @Enumerated(EnumType.STRING)
  @Column(name = "subject_type", nullable = false)
  private EventSubject subjectType;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public EventAction getAction() {
    return action;
  }

  public void setAction(EventAction action) {
    this.action = action;
  }

  public String getEventName() {
    return eventName;
  }

  public void setEventName(String eventName) {
    this.eventName = eventName;
  }

  public EventPriority getPriority() {
    return priority;
  }

  public void setPriority(EventPriority priority) {
    this.priority = priority;
  }

  public Long getObjectId() {
    return objectId;
  }

  public void setObjectId(Long objectId) {
    this.objectId = objectId;
  }

  public String getObjectName() {
    return objectName;
  }

  public void setObjectName(String objectName) {
    this.objectName = objectName;
  }

  public EventObject getObjectType() {
    return objectType;
  }

  public void setObjectType(EventObject objectType) {
    this.objectType = objectType;
  }

  public Long getProjectId() {
    return projectId;
  }

  public void setProjectId(Long projectId) {
    this.projectId = projectId;
  }

  public ActivityDetails getDetails() {
    return details;
  }

  public void setDetails(ActivityDetails details) {
    this.details = details;
  }

  public Long getSubjectId() {
    return subjectId;
  }

  public void setSubjectId(Long subjectId) {
    this.subjectId = subjectId;
  }

  public String getSubjectName() {
    return subjectName;
  }

  public void setSubjectName(String subjectName) {
    this.subjectName = subjectName;
  }

  public EventSubject getSubjectType() {
    return subjectType;
  }

  public void setSubjectType(EventSubject subjectType) {
    this.subjectType = subjectType;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Activity activity = (Activity) o;
    return Objects.equals(id, activity.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  @Override
  public String toString() {
    return "Activity{" + "createdAt=" + createdAt
        + ", action=" + action + ", eventName='" + eventName + '\''
        + ", priority=" + priority + ", objectId=" + objectId
        + ", objectName='" + objectName + '\'' + ", objectType="
        + objectType + ", projectId=" + projectId + ", details="
        + details + ", subjectId=" + subjectId + ", subjectName='"
        + subjectName + '\'' + ", subjectType=" + subjectType + '}';
  }
}
