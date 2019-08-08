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

import com.epam.ta.reportportal.ws.model.attribute.ItemAttributeResource;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

/**
 * @author Ivan Budayeu
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UniqueBugContent implements Serializable {

	@JsonProperty(value = "submitter")
	private String submitter;

	@JsonProperty(value = "url")
	private String url;

	@JsonProperty(value = "submitDate")
	private Timestamp submitDate;

	@JsonProperty(value = "items")
	private List<ItemInfo> items = Lists.newArrayList();

	public String getSubmitter() {
		return submitter;
	}

	public void setSubmitter(String submitter) {
		this.submitter = submitter;
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

	public List<ItemInfo> getItems() {
		return items;
	}

	public void setItems(List<ItemInfo> items) {
		this.items = items;
	}

	public static class ItemInfo implements Serializable {
		@JsonProperty(value = "itemId")
		private Long testItemId;

		@JsonProperty(value = "itemName")
		private String testItemName;

		@JsonProperty(value = "launchId")
		private Long launchId;

		@JsonProperty(value = "path")
		private String path;

		@JsonProperty(value = "attributes")
		private Set<ItemAttributeResource> itemAttributeResources = Sets.newHashSet();

		public Long getTestItemId() {
			return testItemId;
		}

		public void setTestItemId(Long testItemId) {
			this.testItemId = testItemId;
		}

		public String getTestItemName() {
			return testItemName;
		}

		public void setTestItemName(String testItemName) {
			this.testItemName = testItemName;
		}

		public Long getLaunchId() {
			return launchId;
		}

		public void setLaunchId(Long launchId) {
			this.launchId = launchId;
		}

		public String getPath() {
			return path;
		}

		public void setPath(String path) {
			this.path = path;
		}

		public Set<ItemAttributeResource> getItemAttributeResources() {
			return itemAttributeResources;
		}

		public void setItemAttributeResources(Set<ItemAttributeResource> itemAttributeResources) {
			this.itemAttributeResources = itemAttributeResources;
		}
	}
}
