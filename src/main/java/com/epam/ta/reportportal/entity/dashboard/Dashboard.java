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

package com.epam.ta.reportportal.entity.dashboard;

import com.epam.ta.reportportal.dao.converters.JpaInstantConverter;
import com.epam.ta.reportportal.entity.OwnedEntity;
import com.google.common.collect.Sets;
import java.io.Serializable;
import java.time.Instant;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * @author Pavel Bortnik
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Dashboard extends OwnedEntity implements Serializable {

  @Column(name = "name")
  private String name;

  @Column(name = "description")
  private String description;

  @CreatedDate
  @Column(name = "creation_date")
  @Convert(converter = JpaInstantConverter.class)
  private Instant creationDate;

  @OneToMany(fetch = FetchType.EAGER, mappedBy = "dashboard")
  @Fetch(value = FetchMode.JOIN)
  private Set<DashboardWidget> widgets = Sets.newHashSet();

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Instant getCreationDate() {
    return creationDate;
  }

  public void setCreationDate(Instant creationDate) {
    this.creationDate = creationDate;
  }

  public Set<DashboardWidget> getDashboardWidgets() {
    return widgets;
  }

  public void addWidget(DashboardWidget widget) {
    widgets.add(widget);
  }
}
