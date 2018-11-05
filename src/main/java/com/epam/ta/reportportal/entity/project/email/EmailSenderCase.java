/*
 *  Copyright (C) 2018 EPAM Systems
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.epam.ta.reportportal.entity.project.email;

import com.epam.ta.reportportal.entity.project.Project;
import com.google.common.collect.Sets;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * @author Ivan Budayeu
 */
@Entity
@Table(name = "email_sender_case")
public class EmailSenderCase implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToMany(mappedBy = "emailSenderCase", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
	private Set<SenderCase> senderCaseList = Sets.newHashSet();

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "project_id", nullable = false)
	private Project project;

	public EmailSenderCase() {
	}

	public EmailSenderCase(Set<SenderCase> senderCaseList) {
		this.senderCaseList = senderCaseList;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public Set<SenderCase> getSenderCaseList() {
		return senderCaseList;
	}

	public void setSenderCaseList(Set<SenderCase> senderCaseList) {
		this.senderCaseList = senderCaseList;
	}
}