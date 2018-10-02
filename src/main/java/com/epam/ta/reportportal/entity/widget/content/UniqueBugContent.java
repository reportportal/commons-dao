package com.epam.ta.reportportal.entity.widget.content;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import java.io.Serializable;
import java.sql.Timestamp;

import static com.epam.ta.reportportal.dao.constant.WidgetContentRepositoryConstants.DESCRIPTION;
import static com.epam.ta.reportportal.dao.constant.WidgetContentRepositoryConstants.LAUNCH_ID;

/**
 * @author Ivan Budayeu
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UniqueBugContent implements Serializable {

	@Column(name = "item_id")
	@JsonProperty(value = "item_id")
	private Long testItemId;

	@Column(name = "ticket_id")
	@JsonIgnore
	private String ticketId;

	@Column(name = "login")
	@JsonProperty(value = "submitter")
	private String submitter;

	@Column(name = "name")
	@JsonProperty(value = "item_name")
	private String testItemName;

	@Column(name = "url")
	@JsonProperty(value = "url")
	private String url;

	@Column(name = "submit_date")
	@JsonProperty(value = "submit_date")
	private Timestamp submitDate;

	@Column(name = LAUNCH_ID)
	@JsonProperty(value = LAUNCH_ID)
	private Long launchId;

	@Column(name = DESCRIPTION)
	@JsonProperty(value = DESCRIPTION)
	private String description;

	public UniqueBugContent() {
	}

	public Long getTestItemId() {
		return testItemId;
	}

	public void setTestItemId(Long testItemId) {
		this.testItemId = testItemId;
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

	public String getTestItemName() {
		return testItemName;
	}

	public void setTestItemName(String testItemName) {
		this.testItemName = testItemName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Timestamp getSubmitDate() {
		return submitDate;
	}

	public void setSubmitDate(Timestamp submitDate) {
		this.submitDate = submitDate;
	}

	public Long getLaunchId() {
		return launchId;
	}

	public void setLaunchId(Long launchId) {
		this.launchId = launchId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
