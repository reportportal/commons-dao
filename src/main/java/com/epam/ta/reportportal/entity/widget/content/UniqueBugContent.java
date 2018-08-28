package com.epam.ta.reportportal.entity.widget.content;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author Ivan Budayeu
 */
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
