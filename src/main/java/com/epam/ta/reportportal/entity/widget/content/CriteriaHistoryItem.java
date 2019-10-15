/*
 * Copyright 2019 EPAM Systems
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.epam.ta.reportportal.entity.widget.content;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import static com.epam.ta.reportportal.dao.constant.WidgetContentRepositoryConstants.*;

/**
 * @author Ivan Budaev
 */
public class CriteriaHistoryItem implements Serializable {

	@Column(name = UNIQUE_ID)
	private String uniqueId;

	@Column(name = "name")
	private String name;

	@Column(name = TOTAL)
	private Long total;

	@Column(name = CRITERIA)
	private Long criteria;

	@Column(name = STATUS_HISTORY)
	private List<Boolean> status;

	@Column(name = START_TIME_HISTORY)
	private List<Date> startTime;

	public String getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public Long getCriteria() {
		return criteria;
	}

	public void setCriteria(Long criteria) {
		this.criteria = criteria;
	}

	public List<Boolean> getStatus() {
		return status;
	}

	public void setStatus(List<Boolean> status) {
		this.status = status;
	}

	public List<Date> getStartTime() {
		return startTime;
	}

	public void setStartTime(List<Date> startTime) {
		this.startTime = startTime;
	}
}
