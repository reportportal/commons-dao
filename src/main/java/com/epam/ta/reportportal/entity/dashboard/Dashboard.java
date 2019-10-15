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

import com.epam.ta.reportportal.entity.ShareableEntity;
import com.google.common.collect.Sets;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * @author Pavel Bortnik
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Dashboard extends ShareableEntity implements Serializable {

	@Column(name = "name")
	private String name;

	@Column(name = "description")
	private String description;

	@CreatedDate
	@Column(name = "creation_date")
	private LocalDateTime creationDate;

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

	public LocalDateTime getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(LocalDateTime creationDate) {
		this.creationDate = creationDate;
	}

	public Set<DashboardWidget> getDashboardWidgets() {
		return widgets;
	}

	public void addWidget(DashboardWidget widget) {
		widgets.add(widget);
	}
}
