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

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "project_email_config")
public class ProjectEmailConfig implements Serializable {

	@Id
	@GeneratedValue
	private Long id;

	@Column(name = "email_enabled")
	private Boolean emailEnabled;

	@Column(name = "from")
	private String from;

	@OneToMany(mappedBy = "projectEmailConfig")
	private List<EmailSenderCase> emailCases;

	public ProjectEmailConfig() {
	}

	public ProjectEmailConfig(Boolean emailEnabled, String from, List<EmailSenderCase> emailCases) {
		this.emailEnabled = emailEnabled;
		this.from = from;
		this.emailCases = emailCases;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getEmailEnabled() {
		return emailEnabled;
	}

	public void setEmailEnabled(Boolean emailEnabled) {
		this.emailEnabled = emailEnabled;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public List<EmailSenderCase> getEmailCases() {
		return emailCases;
	}

	public void setEmailCases(List<EmailSenderCase> emailCases) {
		this.emailCases = emailCases;
	}

	@Override
	public String toString() {
		return "ProjectEmailConfig{" + "id=" + id + ", emailEnabled=" + emailEnabled + ", from='" + from + '\'' + ", emailCases="
				+ emailCases + '}';
	}
}