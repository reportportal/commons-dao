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

package com.epam.ta.reportportal.database.entity.history.status;

import com.epam.ta.reportportal.database.entity.history.ItemHistoryObject;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Do not db object representation. It is result of
 * {@link com.epam.ta.reportportal.database.dao.TestItemRepositoryCustom#getMostFailedItemHistory(List, String)}
 * aggregation query.
 *
 * @author Pavel Bortnik
 */
public class MostFailedHistoryObject extends ItemHistoryObject {

	private int amount;

	private List<Entry> statusHistory;

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public List<Entry> getStatusHistory() {
		return statusHistory;
	}

	public void setStatusHistory(List<Entry> statusHistory) {
		this.statusHistory = statusHistory;
	}

	public static class Entry implements Serializable {

		private String status;

		private String issue;

		private Date time;

		private Integer criteria;

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public String getIssue() {
			return issue;
		}

		public void setIssue(String issue) {
			this.issue = issue;
		}

		public Date getTime() {
			return time;
		}

		public void setTime(Date time) {
			this.time = time;
		}

		public Integer getCriteria() {
			return criteria;
		}

		public void setCriteria(Integer criteria) {
			this.criteria = criteria;
		}

		@Override
		public String toString() {
			return "Entry{" + "status='" + status + '\'' + ", issue='" + issue + '\'' + ", time=" + time + ", criteria=" + criteria + '}';
		}
	}
}
