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
import java.util.Objects;
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

	private double approximateDuration;

	private Boolean hasRetries;

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

	public Boolean getHasRetries() {
		return hasRetries;
	}

	public void setHasRetries(Boolean hasRetries) {
		this.hasRetries = hasRetries;
	}

	@Override
	public String toString() {
		return "Launch{" + "id='" + id + '\'' + ", projectRef='" + projectRef + '\'' + ", userRef='" + userRef + '\'' + ", name='" + name
				+ '\'' + ", description='" + description + '\'' + ", startTime=" + startTime + ", endTime=" + endTime + ", status=" + status
				+ ", tags=" + tags + ", statistics=" + statistics + ", number=" + number + ", lastModified=" + lastModified + ", mode="
				+ mode + ", approximateDuration=" + approximateDuration + ", hasRetries=" + hasRetries + '}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Launch launch = (Launch) o;
		return Double.compare(launch.approximateDuration, approximateDuration) == 0 && Objects.equals(id, launch.id) && Objects.equals(projectRef,
				launch.projectRef
		) && Objects.equals(userRef, launch.userRef) && Objects.equals(name, launch.name) && Objects.equals(description, launch.description)
				&& Objects.equals(startTime, launch.startTime) && Objects.equals(
				endTime,
				launch.endTime
		) && status == launch.status && Objects.equals(tags, launch.tags) && Objects.equals(statistics, launch.statistics)
				&& Objects.equals(number, launch.number) && Objects.equals(lastModified, launch.lastModified) && mode == launch.mode
				&& Objects.equals(
				hasRetries,
				launch.hasRetries
		);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id,
				projectRef,
				userRef,
				name,
				description,
				startTime,
				endTime,
				status,
				tags,
				statistics,
				number,
				lastModified,
				mode,
				approximateDuration,
				hasRetries
		);
	}
}
