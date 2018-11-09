/*
 * Copyright (C) 2018 EPAM Systems
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

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.List;

import static com.epam.ta.reportportal.dao.constant.WidgetContentRepositoryConstants.*;

/**
 * @author Ivan Budayeu
 */
public class FlakyCasesTableContent implements Serializable {

	@JsonProperty(value = "statuses")
	@Column(name = STATUSES)
	private List<String> statuses;

	@JsonProperty(value = "flaky_count")
	@Column(name = FLAKY_COUNT)
	private Long flakyCount;

	@JsonProperty(value = "total")
	@Column(name = TOTAL)
	private Long total;

	@JsonProperty(value = "item_name")
	@Column(name = ITEM_NAME)
	private String itemName;

	@JsonProperty(value = "unique_id")
	@Column(name = UNIQUE_ID)
	private String uniqueId;

	public FlakyCasesTableContent() {
	}

	public List<String> getStatuses() {
		return statuses;
	}

	public void setStatuses(List<String> statuses) {
		this.statuses = statuses;
	}

	public Long getFlakyCount() {
		return flakyCount;
	}

	public void setFlakyCount(Long flakyCount) {
		this.flakyCount = flakyCount;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}
}
