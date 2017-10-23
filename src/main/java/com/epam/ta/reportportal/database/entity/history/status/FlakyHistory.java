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

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Do not db object representation. It is result of
 * {@link com.epam.ta.reportportal.database.dao.TestItemRepositoryCustom#getFlakyItemStatusHistory(List)}
 * aggregation query.
 *
 * @author Pavel Bortnik
 */
public class FlakyHistory implements Serializable {

	@Id
	private String id;

	private int total;

	private String name;

	private List<HistoryEntry> statusHistory;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public List<HistoryEntry> getStatusHistory() {
		return statusHistory;
	}

	public void setStatusHistory(List<HistoryEntry> statusHistory) {
		this.statusHistory = statusHistory;
	}

	public static class HistoryEntry implements Serializable {

		private String status;

		@Field("start_time")
		private Date startTime;

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public Date getStartTime() {
			return startTime;
		}

		public void setStartTime(Date startTime) {
			this.startTime = startTime;
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
			return Objects.equals(status, that.status) && Objects.equals(startTime, that.startTime);
		}

		@Override
		public int hashCode() {
			return Objects.hash(status, startTime);
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
		FlakyHistory that = (FlakyHistory) o;
		return total == that.total && Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(
				statusHistory, that.statusHistory);
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37).append(total).append(name).append(statusHistory).toHashCode();
	}

}
