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

package com.epam.ta.reportportal.entity.project;

import java.io.Serializable;
import java.time.Instant;

/**
 * Not database object. Representation of the result of project info query
 *
 * @author Pavel Bortnik
 */
public class ProjectInfo implements Serializable {

  public static final String USERS_QUANTITY = "usersQuantity";
  public static final String LAUNCHES_QUANTITY = "launchesQuantity";
  public static final String LAST_RUN = "lastRun";

  private Long id;

  private Instant creationDate;

  private String name;

  private String projectType;

  private String organization;

  private int usersQuantity;

  private int launchesQuantity;

  private Instant lastRun;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Instant getCreationDate() {
    return creationDate;
  }

  public void setCreationDate(Instant creationDate) {
    this.creationDate = creationDate;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getProjectType() {
    return projectType;
  }

  public void setProjectType(String projectType) {
    this.projectType = projectType;
  }

  public String getOrganization() {
    return organization;
  }

  public void setOrganization(String organization) {
    this.organization = organization;
  }

  public int getUsersQuantity() {
    return usersQuantity;
  }

  public void setUsersQuantity(int usersQuantity) {
    this.usersQuantity = usersQuantity;
  }

  public int getLaunchesQuantity() {
    return launchesQuantity;
  }

  public void setLaunchesQuantity(int launchesQuantity) {
    this.launchesQuantity = launchesQuantity;
  }

  public Instant getLastRun() {
    return lastRun;
  }

  public void setLastRun(Instant lastRun) {
    this.lastRun = lastRun;
  }
}
