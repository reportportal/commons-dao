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

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "email_sender_case")
public class EmailSenderCase implements Serializable {

	private List<String> recipients;

	private String sendCase;

	private List<String> launchNames;

	private List<String> tags;

	public EmailSenderCase() {
	}

	public EmailSenderCase(List<String> recipients, String sendCase, List<String> launchNames, List<String> tags) {
		this.recipients = recipients;
		this.sendCase = sendCase;
		this.launchNames = launchNames;
		this.tags = tags;
	}

	public List<String> getRecipients() {
		return recipients;
	}

	public void setRecipients(List<String> recipients) {
		this.recipients = recipients;
	}

	public String getSendCase() {
		return sendCase;
	}

	public void setSendCase(String sendCase) {
		this.sendCase = sendCase;
	}

	public List<String> getLaunchNames() {
		return launchNames;
	}

	public void setLaunchNames(List<String> launchNames) {
		this.launchNames = launchNames;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		EmailSenderCase that = (EmailSenderCase) o;

		if (recipients != null ? !recipients.equals(that.recipients) : that.recipients != null) {
			return false;
		}
		if (sendCase != null ? !sendCase.equals(that.sendCase) : that.sendCase != null) {
			return false;
		}
		if (launchNames != null ? !launchNames.equals(that.launchNames) : that.launchNames != null) {
			return false;
		}
		return tags != null ? tags.equals(that.tags) : that.tags == null;
	}

	@Override
	public int hashCode() {
		int result = recipients != null ? recipients.hashCode() : 0;
		result = 31 * result + (sendCase != null ? sendCase.hashCode() : 0);
		result = 31 * result + (launchNames != null ? launchNames.hashCode() : 0);
		result = 31 * result + (tags != null ? tags.hashCode() : 0);
		return result;
	}
}