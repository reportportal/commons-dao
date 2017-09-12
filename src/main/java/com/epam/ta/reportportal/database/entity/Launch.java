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

package com.epam.ta.reportportal.database.entity;

import com.epam.ta.reportportal.database.entity.statistics.ExecutionCounter;
import com.epam.ta.reportportal.database.entity.statistics.IssueCounter;
import com.epam.ta.reportportal.database.entity.statistics.Statistics;
import com.epam.ta.reportportal.database.search.FilterCriteria;
import com.epam.ta.reportportal.ws.model.launch.Mode;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * @author Henadzi_Vrubleuski
 */
@Document
@CompoundIndexes({ @CompoundIndex(name = "projectRef_start_time", def = "{'projectRef': 1, 'start_time': -1}", background = true) })
public class Launch implements Serializable, Interruptable {

	private static final long serialVersionUID = 7234458635123992134L;

	public static final String NAME = "name";
	public static final String PROJECT = "project";
	public static final String USER = "user";
	public static final String TAGS = "tags";
	public static final String LAUNCH = "launch";
	public static final String STATUS = "status";

	public static final String MODE_CRITERIA = "mode";

	@Id
	private String id;

	@FilterCriteria(PROJECT)
	@Indexed
	private String projectRef;

	@FilterCriteria(USER)
	private String userRef;

	@FilterCriteria("name")
	private String name;

	@FilterCriteria("description")
	private String description;

	@FilterCriteria("start_time")
	@Field("start_time")
	private Date startTime;

	@FilterCriteria("end_time")
	@Field("end_time")
	private Date endTime;

	@FilterCriteria(STATUS)
	@Indexed
	private Status status;

	@FilterCriteria(TAGS)
	@Indexed
	private Set<String> tags;

	@FilterCriteria("statistics")
	private Statistics statistics;

	@FilterCriteria("number")
	@Indexed
	private Long number;

	@LastModifiedDate
	@FilterCriteria(LAST_MODIFIED)
	@Field(LAST_MODIFIED)
	private Date lastModified;

	@FilterCriteria("mode")
	private Mode mode = Mode.DEFAULT;

	private Metadata metadata;

	private double approximateDuration;


	public Launch() {
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public void setTags(Set<String> tags) {
		this.tags = tags;
	}

	public Set<String> getTags() {
		return tags;
	}

	public void setProjectRef(String projectRef) {
		this.projectRef = projectRef;
	}

	public String getProjectRef() {
		return projectRef;
	}

	public void setUserRef(String userRef) {
		this.userRef = userRef;
	}

	public double getApproximateDuration() {
		return approximateDuration;
	}

	public void setApproximateDuration(double approximateDuration) {
		this.approximateDuration = approximateDuration;
	}

	public String getUserRef() {
		return userRef;
	}

	public Statistics getStatistics() {
		return statistics;
	}

	public void setStatistics(Statistics statistics) {
		this.statistics = statistics;
	}

	public void setNumber(Long number) {
		this.number = number;
	}

	public Metadata getMetadata() {
		return metadata;
	}

	public void setMetadata(Metadata metadata) {
		this.metadata = metadata;
	}

	public Long getNumber() {
		return number;
	}

	@Override
	public Date getLastModified() {
		return this.lastModified;
	}

	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}

	public Mode getMode() {
		return mode;
	}

	public void setMode(Mode mode) {
		this.mode = mode;
	}

	@Override
	public String toString() {
		return "Launch [id=" + id + ", projectRef=" + projectRef + ", userRef=" + userRef + ", name=" + name + ", description="
				+ description + ", startTime=" + startTime + ", endTime=" + endTime + ", status=" + status + ", tags=" + tags
				+ ", statistics=" + statistics + ", number=" + number + ", lastModified=" + lastModified + ", mode=" + mode + "]";
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
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((endTime == null) ? 0 : endTime.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((lastModified == null) ? 0 : lastModified.hashCode());
		result = prime * result + ((mode == null) ? 0 : mode.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((number == null) ? 0 : number.hashCode());
		result = prime * result + ((projectRef == null) ? 0 : projectRef.hashCode());
		result = prime * result + ((startTime == null) ? 0 : startTime.hashCode());
		result = prime * result + ((statistics == null) ? 0 : statistics.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((tags == null) ? 0 : tags.hashCode());
		result = prime * result + ((userRef == null) ? 0 : userRef.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Launch other = (Launch) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (endTime == null) {
			if (other.endTime != null)
				return false;
		} else if (!endTime.equals(other.endTime))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (lastModified == null) {
			if (other.lastModified != null)
				return false;
		} else if (!lastModified.equals(other.lastModified))
			return false;
		if (mode != other.mode)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (number == null) {
			if (other.number != null)
				return false;
		} else if (!number.equals(other.number))
			return false;
		if (projectRef == null) {
			if (other.projectRef != null)
				return false;
		} else if (!projectRef.equals(other.projectRef))
			return false;
		if (startTime == null) {
			if (other.startTime != null)
				return false;
		} else if (!startTime.equals(other.startTime))
			return false;
		if (statistics == null) {
			if (other.statistics != null)
				return false;
		} else if (!statistics.equals(other.statistics))
			return false;
		if (status != other.status)
			return false;
		if (tags == null) {
			if (other.tags != null)
				return false;
		} else if (!tags.equals(other.tags))
			return false;
		if (userRef == null) {
			if (other.userRef != null)
				return false;
		} else if (!userRef.equals(other.userRef))
			return false;
		return true;
	}

	public static class Metadata implements Serializable {

		private Long build;

		public Long getBuild() {
			return build;
		}

		public void setBuild(Long build) {
			this.build = build;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o)
				return true;
			if (o == null || getClass() != o.getClass())
				return false;

			Metadata metadata = (Metadata) o;

			return build != null ? build.equals(metadata.build) : metadata.build == null;
		}

		@Override
		public int hashCode() {
			return build != null ? build.hashCode() : 0;
		}
	}
}
