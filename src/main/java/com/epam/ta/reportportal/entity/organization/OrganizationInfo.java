/*
 * Copyright 2024 EPAM Systems
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

package com.epam.ta.reportportal.entity.organization;

import com.epam.ta.reportportal.entity.enums.OrganizationType;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Siarhei Hrabko
 */
public class OrganizationInfo implements Serializable {

  public static final String USERS_QUANTITY = "usersQuantity";
  public static final String LAUNCHES_QUANTITY = "launchesQuantity";
  public static final String PROJECTS_QUANTITY = "projectsQuantity";
  public static final String LAST_RUN = "lastRun";

  private Long id;
  private LocalDateTime creationDate;
  private String name;
  private OrganizationType organizationType;
  private String slug;

  private int usersQuantity;
  private int projectsQuantity;
  private int launchesQuantity;

  private LocalDateTime lastRun;

  public OrganizationInfo() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public LocalDateTime getCreationDate() {
    return creationDate;
  }

  public void setCreationDate(LocalDateTime creationDate) {
    this.creationDate = creationDate;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public OrganizationType getOrganizationType() {
    return organizationType;
  }

  public void setOrganizationType(OrganizationType organizationType) {
    this.organizationType = organizationType;
  }

  public String getSlug() {
    return slug;
  }

  public void setSlug(String slug) {
    this.slug = slug;
  }

  public int getUsersQuantity() {
    return usersQuantity;
  }

  public void setUsersQuantity(int usersQuantity) {
    this.usersQuantity = usersQuantity;
  }

  public int getProjectsQuantity() {
    return projectsQuantity;
  }

  public void setProjectsQuantity(int projectsQuantity) {
    this.projectsQuantity = projectsQuantity;
  }

  public int getLaunchesQuantity() {
    return launchesQuantity;
  }

  public void setLaunchesQuantity(int launchesQuantity) {
    this.launchesQuantity = launchesQuantity;
  }

  public LocalDateTime getLastRun() {
    return lastRun;
  }

  public void setLastRun(LocalDateTime lastRun) {
    this.lastRun = lastRun;
  }

}
