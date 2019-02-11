package com.epam.ta.reportportal.entity.project.email;

import com.epam.ta.reportportal.entity.enums.SendCase;
import com.epam.ta.reportportal.entity.enums.PostgreSQLEnumType;
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

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "launch_attributes", joinColumns = @JoinColumn(name = "sender_case_id"))
	@Column(name = "launch_attribute")
	private Set<String> launchAttributes;

	@Enumerated(EnumType.STRING)
	@Column(name = "send_case")
	@Type(type = "pqsql_enum")
	private SendCase sendCase;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "project_id", nullable = false)
	private Project project;

	public SenderCase() {
	}

	public SenderCase(Set<String> recipients, Set<String> launchNames, Set<String> launchAttributes, SendCase sendCase) {
		this.recipients = recipients;
		this.launchNames = launchNames;
		this.launchAttributes = launchAttributes;
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

	public Set<String> getLaunchAttributes() {
		return launchAttributes;
	}

	public void setLaunchAttributes(Set<String> launchAttributes) {
		this.launchAttributes = launchAttributes;
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