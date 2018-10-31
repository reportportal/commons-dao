/*
 *  Copyright (C) 2018 EPAM Systems
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.epam.ta.reportportal.entity.project.email;

import com.epam.ta.reportportal.commons.SendCase;
import com.epam.ta.reportportal.entity.launch.Launch;
import com.epam.ta.reportportal.entity.launch.LaunchTag;
import com.epam.ta.reportportal.entity.project.Project;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
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

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "recipients", joinColumns = @JoinColumn(name = "email_sender_case_id"))
	@Column(name = "recipient")
	private List<String> recipients;

	@Column(name = "send_case")
	private SendCase sendCase;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "email_sender_case_id")
	private Set<Launch> launches;

	@OneToMany(mappedBy = "emailSenderCase", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<LaunchTag> tags;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "project_id", nullable = false)
	private Project project;

	public EmailSenderCase() {
	}

	public EmailSenderCase(List<String> recipients, SendCase sendCase, Set<Launch> launches, Set<LaunchTag> tags) {
		this.recipients = recipients;
		this.sendCase = sendCase;
		this.launches = launches;
		this.tags = tags;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<String> getRecipients() {
		return recipients;
	}

	public void setRecipients(List<String> recipients) {
		this.recipients = recipients;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public Set<Launch> getLaunches() {
		return launches;
	}

	public void setLaunches(Set<Launch> launches) {
		this.launches = launches;
	}

	public SendCase getSendCase() {
		return sendCase;
	}

	public void setSendCase(SendCase sendCase) {
		this.sendCase = sendCase;
	}

	public Set<LaunchTag> getTags() {
		return tags;
	}

	public void setTags(Set<LaunchTag> tags) {
		this.tags = tags;
	}
}