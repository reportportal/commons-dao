/*
 * Copyright 2016 EPAM Systems
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

package com.epam.ta.reportportal.entity.item.issue;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

/**
 * Represents test step issue type and description (comment) on it
 *
 * @author Dzianis Shlychkou
 */
public class TestItemIssue implements Serializable {

	private static final long serialVersionUID = -7585701398836556055L;

	private String issueType;

	private String issueDescription;

	private Set<ExternalSystemIssue> externalSystemIssues;

	private boolean autoAnalyzed;

	private boolean ignoreAnalyzer;

	public TestItemIssue(String issueType, String issueDescription) {
		this.issueType = issueType;
		this.issueDescription = issueDescription;
	}

	public TestItemIssue(String issueType, String issueDescription, boolean autoAnalyzed) {
		this.issueType = issueType;
		this.issueDescription = issueDescription;
		this.autoAnalyzed = autoAnalyzed;
	}

	public TestItemIssue() {
		this(TestItemIssueType.TO_INVESTIGATE.getLocator(), null);
	}

	public static class ExternalSystemIssue implements Serializable {
		private static final long serialVersionUID = -6602651378914172041L;

		private String ticketId;

		private String submitter;

		private Long submitDate;

		private String externalSystemId;

		/* Should be @FilterCriteria search used here? */
		private String url;

		public void setTicketId(String ticketId) {
			this.ticketId = ticketId;
		}

		public String getTicketId() {
			return ticketId;
		}

		public String getSubmitter() {
			return submitter;
		}

		public void setSubmitter(String submitter) {
			this.submitter = submitter;
		}

		public Long getSubmitDate() {
			return submitDate;
		}

		public void setSubmitDate(Long submitDate) {
			this.submitDate = submitDate;
		}

		public void setExternalSystemId(String id) {
			this.externalSystemId = id;
		}

		public String getExternalSystemId() {
			return externalSystemId;
		}

		public void setUrl(String value) {
			this.url = value;
		}

		public String getUrl() {
			return url;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) {
				return true;
			}
			if (!(o instanceof ExternalSystemIssue)) {
				return false;
			}

			ExternalSystemIssue that = (ExternalSystemIssue) o;

			if (!ticketId.equals(that.ticketId)) {
				return false;
			}

			return true;
		}

		@Override
		public int hashCode() {
			return ticketId.hashCode();
		}
	}

	public boolean isAutoAnalyzed() {
		return autoAnalyzed;
	}

	public void setAutoAnalyzed(boolean autoAnalyzed) {
		this.autoAnalyzed = autoAnalyzed;
	}

	public boolean isIgnoreAnalyzer() {
		return ignoreAnalyzer;
	}

	public void setIgnoreAnalyzer(boolean ignoreAnalyzer) {
		this.ignoreAnalyzer = ignoreAnalyzer;
	}

	public Set<ExternalSystemIssue> getExternalSystemIssues() {
		return externalSystemIssues;
	}

	public void setExternalSystemIssues(Set<ExternalSystemIssue> externalSystemIssues) {
		this.externalSystemIssues = externalSystemIssues;
	}

	public String getIssueType() {
		return issueType;
	}

	public void setIssueType(String issueType) {
		this.issueType = issueType;
	}

	public String getIssueDescription() {
		return issueDescription;
	}

	public void setIssueDescription(String issueDescription) {
		this.issueDescription = issueDescription;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		TestItemIssue that = (TestItemIssue) o;
		return autoAnalyzed == that.autoAnalyzed && ignoreAnalyzer == that.ignoreAnalyzer && Objects.equals(issueType, that.issueType)
				&& Objects.equals(issueDescription, that.issueDescription) && Objects.equals(externalSystemIssues,
				that.externalSystemIssues
		);
	}

	@Override
	public int hashCode() {
		return Objects.hash(issueType, issueDescription, externalSystemIssues, autoAnalyzed, ignoreAnalyzer);
	}

	@Override
	public String toString() {
		return "TestItemIssue{" + "issueType='" + issueType + '\'' + ", issueDescription='" + issueDescription + '\''
				+ ", externalSystemIssues=" + externalSystemIssues + ", autoAnalyzed=" + autoAnalyzed + ", ignoreAnalyzer=" + ignoreAnalyzer
				+ '}';
	}
}