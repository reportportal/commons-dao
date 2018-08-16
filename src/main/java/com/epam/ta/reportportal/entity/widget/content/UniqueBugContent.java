package com.epam.ta.reportportal.entity.widget.content;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author Ivan Budayeu
 */
public class UniqueBugContent implements Serializable {

	@JsonProperty(value = "item_id")
	private Long testItemId;

	@JsonIgnore
	private String ticketId;

	@JsonProperty(value = "submitter")
	private String submitter;

	@JsonProperty(value = "item_name")
	private String testItemName;

	@JsonProperty(value = "url")
	private String url;

	@JsonProperty(value = "submit_date")
	private Timestamp submitDate;

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
}
