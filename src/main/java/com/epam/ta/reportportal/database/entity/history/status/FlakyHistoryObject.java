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

import java.util.List;

/**
 * Do not db object representation. It is result of
 * {@link com.epam.ta.reportportal.database.dao.TestItemRepositoryCustom#getFlakyItemStatusHistory(List)}
 * aggregation query.
 *
 * @author Pavel Bortnik
 */
public class FlakyHistoryObject extends ItemHistoryObject {

	private List<ItemHistoryObject.Entry> statusHistory;

	public List<Entry> getStatusHistory() {
		return statusHistory;
	}

	public void setStatusHistory(List<Entry> statusHistory) {
		this.statusHistory = statusHistory;
	}
}
