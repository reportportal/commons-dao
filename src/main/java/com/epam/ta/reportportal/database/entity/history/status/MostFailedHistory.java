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

import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Does not a db object representation. It is the result of
 * {@link com.epam.ta.reportportal.database.dao.TestItemRepositoryCustom#getMostFailedItemHistory(List, String, int)}
 * aggregation query.
 *
 * @author Pavel Bortnik
 */
public class MostFailedHistory implements Serializable {

	@Field(value = "_id")
	private String uniqueId;

	private int total;

	private String name;

	private int failed;

	private List<HistoryEntry> statusHistory;

	public String getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getFailed() {
		return failed;
	}

	public void setFailed(int failed) {
		this.failed = failed;
	}

	public List<HistoryEntry> getStatusHistory() {
		return statusHistory;
	}

	public void setStatusHistory(List<HistoryEntry> statusHistory) {
		this.statusHistory = statusHistory;
	}

	public static class HistoryEntry implements Serializable {

		@Field("start_time")
		private Date startTime;

		//shows amount of item statistics by widget criteria
		private int criteriaAmount;

		public Date getStartTime() {
			return startTime;
		}

		public void setStartTime(Date startTime) {
			this.startTime = startTime;
		}

		public int getCriteriaAmount() {
			return criteriaAmount;
		}

		public void setCriteriaAmount(int criteriaAmount) {
			this.criteriaAmount = criteriaAmount;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) {
				return true;
			}
			if (o == null || getClass() != o.getClass()) {
				return false;
			}
			HistoryEntry that = (HistoryEntry) o;
			return criteriaAmount == that.criteriaAmount && Objects.equals(startTime, that.startTime);
		}

		@Override
		public int hashCode() {
			return Objects.hash(startTime, criteriaAmount);
		}
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		MostFailedHistory that = (MostFailedHistory) o;
		return total == that.total && failed == that.failed && Objects.equals(uniqueId, that.uniqueId) && Objects.equals(name, that.name)
				&& Objects.equals(statusHistory, that.statusHistory);
	}

	@Override
	public int hashCode() {
		return Objects.hash(uniqueId, total, name, failed, statusHistory);
	}
}
