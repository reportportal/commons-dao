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

package com.epam.ta.reportportal.entity.bts;

import com.epam.ta.reportportal.entity.item.issue.IssueEntity;
import com.google.common.collect.Sets;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

/**
 * @author Pavel Bortnik
 */

@Entity
@Table(name = "ticket")
public class Ticket implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "ticket_id")
	private String ticketId;

	@Column(name = "submitter")
	private String submitter;

	@Column(name = "submit_date")
	private LocalDateTime submitDate;

	@Column(name = "bts_url")
	private String btsUrl;

	@Column(name = "bts_project")
	private String btsProject;

	@Column(name = "url")
	private String url;

	@ManyToMany(mappedBy = "tickets")
	private Set<IssueEntity> issues = Sets.newHashSet();

	public Ticket() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTicketId() {
		return ticketId;
	}

	public void setTicketId(String ticketId) {
		this.ticketId = ticketId;
	}

	public String getSubmitter() {
		return submitter;
	}

	public void setSubmitter(String submitter) {
		this.submitter = submitter;
	}

	public LocalDateTime getSubmitDate() {
		return submitDate;
	}

	public void setSubmitDate(LocalDateTime submitDate) {
		this.submitDate = submitDate;
	}

	public String getBtsUrl() {
		return btsUrl;
	}

	public void setBtsUrl(String btsUrl) {
		this.btsUrl = btsUrl;
	}

	public String getBtsProject() {
		return btsProject;
	}

	public void setBtsProject(String btsProject) {
		this.btsProject = btsProject;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Set<IssueEntity> getIssues() {
		return issues;
	}

	public void setIssues(Set<IssueEntity> issues) {
		this.issues = issues;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Ticket ticket = (Ticket) o;
		return Objects.equals(ticketId, ticket.ticketId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(ticketId);
	}
}
