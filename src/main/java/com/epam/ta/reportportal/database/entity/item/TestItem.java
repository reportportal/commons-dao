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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

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
	//@Indexed(background = true)
	private String uniqueId;

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

	/*
         * (non-Javadoc)
         *
         * @see java.lang.Object#hashCode()
         */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((endTime == null) ? 0 : endTime.hashCode());
		result = prime * result + (hasChilds ? 1231 : 1237);
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((issue == null) ? 0 : issue.hashCode());
		result = prime * result + ((itemDescription == null) ? 0 : itemDescription.hashCode());
		result = prime * result + ((lastModified == null) ? 0 : lastModified.hashCode());
		result = prime * result + ((launchRef == null) ? 0 : launchRef.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((parent == null) ? 0 : parent.hashCode());
		result = prime * result + ((path == null) ? 0 : path.hashCode());
		result = prime * result + ((startTime == null) ? 0 : startTime.hashCode());
		result = prime * result + ((statistics == null) ? 0 : statistics.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + ((parameters == null) ? 0 : parameters.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		TestItem other = (TestItem) obj;
		if (endTime == null) {
			if (other.endTime != null) {
				return false;
			}
		} else if (!endTime.equals(other.endTime)) {
			return false;
		}
		if (hasChilds != other.hasChilds) {
			return false;
		}
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (issue == null) {
			if (other.issue != null) {
				return false;
			}
		} else if (!issue.equals(other.issue)) {
			return false;
		}
		if (itemDescription == null) {
			if (other.itemDescription != null) {
				return false;
			}
		} else if (!itemDescription.equals(other.itemDescription)) {
			return false;
		}
		if (lastModified == null) {
			if (other.lastModified != null) {
				return false;
			}
		} else if (!lastModified.equals(other.lastModified)) {
			return false;
		}
		if (launchRef == null) {
			if (other.launchRef != null) {
				return false;
			}
		} else if (!launchRef.equals(other.launchRef)) {
			return false;
		}
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		if (parent == null) {
			if (other.parent != null) {
				return false;
			}
		} else if (!parent.equals(other.parent)) {
			return false;
		}
		if (path == null) {
			if (other.path != null) {
				return false;
			}
		} else if (!path.equals(other.path)) {
			return false;
		}
		if (startTime == null) {
			if (other.startTime != null) {
				return false;
			}
		} else if (!startTime.equals(other.startTime)) {
			return false;
		}
		if (statistics == null) {
			if (other.statistics != null) {
				return false;
			}
		} else if (!statistics.equals(other.statistics)) {
			return false;
		}
		if (status != other.status) {
			return false;
		}
		if (type != other.type) {
			return false;
		}
		if (parameters == null) {
			if (other.parameters != null) {
				return false;
			}
		}else if (!parameters.equals(other.parameters)) {
			return  false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "TestItem{" + "id='" + id + '\'' + ", name='" + name + '\'' + ", type=" + type + ", startTime=" + startTime + ", endTime="
				+ endTime + ", status=" + status + ", tags=" + tags + ", statistics=" + statistics + ", issue=" + issue + ", path=" + path
				+ ", parent='" + parent + '\'' + ", launchRef='" + launchRef + '\'' + ", hasChilds=" + hasChilds + ", itemDescription='"
				+ itemDescription + '\'' + ", lastModified=" + lastModified + ", parameters=" + parameters + '}';
	}
}
