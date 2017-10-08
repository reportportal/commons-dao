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

/**
 * Do not db object representation. It is result of
 * {@link com.epam.ta.reportportal.database.dao.TestItemRepositoryCustom#getFlakyItemStatusHistory(List)}
 * aggregation query.
 *
 * @author Pavel Bortnik
 */
public class FlakyHistory implements Serializable{

	private List<HistoryEntry> statusHistory;

	public List<HistoryEntry> getStatusHistory() {
		return statusHistory;
	}

	public void setStatusHistory(List<HistoryEntry> statusHistory) {
		this.statusHistory = statusHistory;
	}

	public static class HistoryEntry implements Serializable{

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
	}
}
