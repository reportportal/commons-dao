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

package com.epam.ta.reportportal.database.entity.item;

import com.epam.ta.reportportal.database.entity.BidirectionalTree;
import com.epam.ta.reportportal.database.entity.Interruptable;
import com.epam.ta.reportportal.database.entity.Status;
import com.epam.ta.reportportal.database.entity.item.issue.TestItemIssue;
import com.epam.ta.reportportal.database.entity.statistics.ExecutionCounter;
import com.epam.ta.reportportal.database.entity.statistics.IssueCounter;
import com.epam.ta.reportportal.database.entity.statistics.Statistics;
import com.epam.ta.reportportal.database.search.FilterCriteria;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.*;

@Document

@CompoundIndexes({ @CompoundIndex(name = "parent_start_time", def = "{'parent': 1, 'start_time': 1}", background = true), })
public class TestItem implements Serializable, BidirectionalTree, Interruptable {

	public static final String START_TIME_CRITERIA = "start_time";
	public static final String LAUNCH_CRITERIA = "launch";
	public static final String TAGS = "tags";
	public static final String TEST_ITEM = "testItem";
	public static final String ISSUE = "issue";
	public static final String EXTERNAL_SYSTEM_ISSUES = "issue$externalSystemIssues";

	private static final long serialVersionUID = -2400786349507451080L;

	@Id
	@FilterCriteria("id")
	private String id;

	@FilterCriteria("name")
	private String name;

	@FilterCriteria("type")
	private TestItemType type;

	// index on start time is redundant because almost all queries
	// use indexes on launchRef and, parent fields
	@FilterCriteria(START_TIME_CRITERIA)
	@Field(START_TIME_CRITERIA)
	private Date startTime;

	@Field("end_time")
	@FilterCriteria("end_time")
	private Date endTime;

	@FilterCriteria("status")
	private Status status;

	@FilterCriteria(TAGS)
	@Indexed
	private Set<String> tags;

	/**
	 * Test item statistics representation, {@link Statistics}
	 */
	@FilterCriteria("statistics")
	private Statistics statistics;

	/**
	 * Test item issue marker, {@link TestItemIssue}
	 */
	@FilterCriteria(ISSUE)
	private TestItemIssue issue;

	@FilterCriteria("path")
	@Indexed
	private List<String> path;

	@FilterCriteria("parent")
	private String parent;

	@FilterCriteria(LAUNCH_CRITERIA)
	// added for improving finish launch
	@Indexed(background = true)
	private String launchRef;

	@FilterCriteria("has_childs")
	@Field("has_childs")
	private boolean hasChilds;

	/**
	 * Test step item description.
	 */
	@FilterCriteria("description")
	private String itemDescription;

	@FilterCriteria("parameters")
	private List<Parameter> parameters;

	@LastModifiedDate
	@FilterCriteria(LAST_MODIFIED)
	@Field(LAST_MODIFIED)
	private Date lastModified;

	@FilterCriteria("uniqueId")
	private String uniqueId;

	private List<TestItem> retries;

	private Boolean isRetryProcessed;

	public TestItem() {
		path = new ArrayList<>();
		statistics = new Statistics(new ExecutionCounter(), new IssueCounter());
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Boolean getRetryProcessed() {
		return isRetryProcessed;
	}

	public void setRetryProcessed(Boolean retryProcessed) {
		isRetryProcessed = retryProcessed;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getItemDescription() {
		return itemDescription;
	}

	public void setItemDescription(String description) {
		this.itemDescription = description;
	}

	public List<Parameter> getParameters() {
		return parameters;
	}

	public void setParameters(List<Parameter> parameters) {
		this.parameters = parameters;
	}

	public TestItemType getType() {
		return type;
	}

	public void setType(TestItemType type) {
		this.type = type;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	@Override
	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Set<String> getTags() {
		return tags;
	}

	public void setTags(Set<String> value) {
		this.tags = value;
	}

	public List<String> getPath() {
		// null-safe getter
		if (path == null) {
			path = new ArrayList<>();
		}
		return path;
	}

	public void setPath(List<String> path) {
		this.path = path;
	}

	public void setLaunchRef(String launchRef) {
		this.launchRef = launchRef;
	}

	public String getLaunchRef() {
		return launchRef;
	}

	public Statistics getStatistics() {
		return statistics;
	}

	public void setStatistics(Statistics statistics) {
		this.statistics = statistics;
	}

	public TestItemIssue getIssue() {
		return issue;
	}

	public void setIssue(TestItemIssue issue) {
		this.issue = issue;
	}

	@Override
	public String getParent() {
		return this.parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	@Override
	public boolean hasChilds() {
		return this.hasChilds;
	}

	public void setHasChilds(boolean hasChilds) {
		this.hasChilds = hasChilds;
	}

	@Override
	public Date getLastModified() {
		return lastModified;
	}

	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}

	public String getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}

	public List<TestItem> getRetries() {
		return retries;
	}

	public void setRetries(List<TestItem> retries) {
		this.retries = retries;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		TestItem testItem = (TestItem) o;
		return hasChilds == testItem.hasChilds && Objects.equals(id, testItem.id) && Objects.equals(name, testItem.name)
				&& type == testItem.type && Objects.equals(startTime, testItem.startTime) && Objects.equals(endTime, testItem.endTime)
				&& status == testItem.status && Objects.equals(tags, testItem.tags) && Objects.equals(statistics, testItem.statistics)
				&& Objects.equals(issue, testItem.issue) && Objects.equals(path, testItem.path) && Objects.equals(parent, testItem.parent)
				&& Objects.equals(launchRef, testItem.launchRef) && Objects.equals(itemDescription, testItem.itemDescription)
				&& Objects.equals(parameters, testItem.parameters) && Objects.equals(lastModified, testItem.lastModified) && Objects.equals(
				uniqueId, testItem.uniqueId) && Objects.equals(retries, testItem.retries);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, type, startTime, endTime, status, tags, statistics, issue, path, parent, launchRef, hasChilds,
				itemDescription, parameters, lastModified, uniqueId, retries
		);
	}

	@Override
	public String toString() {
		return "TestItem{" + "id='" + id + '\'' + ", name='" + name + '\'' + ", type=" + type + ", startTime=" + startTime + ", endTime="
				+ endTime + ", status=" + status + ", tags=" + tags + ", statistics=" + statistics + ", issue=" + issue + ", path=" + path
				+ ", parent='" + parent + '\'' + ", launchRef='" + launchRef + '\'' + ", hasChilds=" + hasChilds + ", itemDescription='"
				+ itemDescription + '\'' + ", parameters=" + parameters + ", lastModified=" + lastModified + ", uniqueId='" + uniqueId
				+ '\'' + ", retries=" + retries + '}';
	}
}
