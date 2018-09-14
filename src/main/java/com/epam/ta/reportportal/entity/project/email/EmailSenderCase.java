/*
 * Copyright 2017 EPAM Systems
 *
 *
 * This file is part of EPAM Report Portal.
 * https://github.com/reportportal/commons-dao
 *
 * Report Portal is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Report Portal is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Report Portal.  If not, see <http://www.gnu.org/licenses/>.
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

	@ElementCollection
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