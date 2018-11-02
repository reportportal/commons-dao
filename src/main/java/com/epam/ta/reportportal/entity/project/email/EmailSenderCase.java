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

import com.epam.ta.reportportal.commons.SendCase;
import com.epam.ta.reportportal.entity.project.Project;

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

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "recipients", joinColumns = @JoinColumn(name = "email_sender_case_id"))
	@Column(name = "recipient")
	private Set<String> recipients;

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "launch_names", joinColumns = @JoinColumn(name = "email_sender_case_id"))
	@Column(name = "launch_name")
	private Set<String> launchNames;

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "launch_tags", joinColumns = @JoinColumn(name = "email_sender_case_id"))
	@Column(name = "launch_tag")
	private Set<String> launchTags;

	@Column(name = "send_case")
	private SendCase sendCase;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "project_id", nullable = false)
	private Project project;

	public EmailSenderCase() {
	}

	public EmailSenderCase(Set<String> recipients, Set<String> launchNames, Set<String> launchTags, SendCase sendCase) {
		this.recipients = recipients;
		this.launchNames = launchNames;
		this.launchTags = launchTags;
		this.sendCase = sendCase;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Set<String> getRecipients() {
		return recipients;
	}

	public void setRecipients(Set<String> recipients) {
		this.recipients = recipients;
	}

	public Set<String> getLaunchNames() {
		return launchNames;
	}

	public void setLaunchNames(Set<String> launchNames) {
		this.launchNames = launchNames;
	}

	public Set<String> getLaunchTags() {
		return launchTags;
	}

	public void setLaunchTags(Set<String> launchTags) {
		this.launchTags = launchTags;
	}

	public SendCase getSendCase() {
		return sendCase;
	}

	public void setSendCase(SendCase sendCase) {
		this.sendCase = sendCase;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}
}