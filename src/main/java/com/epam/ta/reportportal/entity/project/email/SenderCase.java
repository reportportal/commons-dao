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

package com.epam.ta.reportportal.entity.project.email;

import com.epam.ta.reportportal.entity.enums.PostgreSQLEnumType;
import com.epam.ta.reportportal.entity.enums.SendCase;
import com.epam.ta.reportportal.entity.project.Project;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * @author Ivan Budayeu
 */
@Entity
@Table(name = "sender_case")
@TypeDef(name = "pqsql_enum", typeClass = PostgreSQLEnumType.class)
public class SenderCase implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "recipients", joinColumns = @JoinColumn(name = "sender_case_id"))
	@Column(name = "recipient")
	private Set<String> recipients;

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "launch_names", joinColumns = @JoinColumn(name = "sender_case_id"))
	@Column(name = "launch_name")
	private Set<String> launchNames;

	@OneToMany(mappedBy = "senderCase", cascade = CascadeType.PERSIST, orphanRemoval = true, fetch = FetchType.EAGER)
	@OrderBy
	private Set<LaunchAttributeRule> launchAttributeRules;

	@Enumerated(EnumType.STRING)
	@Column(name = "send_case")
	@Type(type = "pqsql_enum")
	private SendCase sendCase;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "project_id", nullable = false)
	private Project project;

	@Column(name = "enabled")
	private boolean enabled;

	public SenderCase() {
	}

	public SenderCase(Set<String> recipients, Set<String> launchNames, Set<LaunchAttributeRule> launchAttributeRules, SendCase sendCase, boolean enabled) {
		this.recipients = recipients;
		this.launchNames = launchNames;
		this.launchAttributeRules = launchAttributeRules;
		this.sendCase = sendCase;
		this.enabled = enabled;
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

	public Set<LaunchAttributeRule> getLaunchAttributeRules() {
		return launchAttributeRules;
	}

	public void setLaunchAttributeRules(Set<LaunchAttributeRule> launchAttributeRules) {
		this.launchAttributeRules = launchAttributeRules;
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

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
}