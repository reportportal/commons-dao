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

package com.epam.ta.reportportal.entity.item;

import com.epam.ta.reportportal.entity.enums.PostgreSQLEnumType;
import com.epam.ta.reportportal.entity.enums.StatusEnum;
import com.epam.ta.reportportal.entity.item.issue.IssueEntity;
import com.epam.ta.reportportal.entity.statistics.Statistics;
import com.google.common.collect.Sets;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

/**
 * @author Pavel Bortnik
 */
@Entity
@TypeDef(name = "pqsql_enum", typeClass = PostgreSQLEnumType.class)
@Table(name = "test_item_results", schema = "public")
public class TestItemResults implements Serializable {

	@Id
	@Column(name = "result_id", unique = true, nullable = false, precision = 64)
	private Long itemId;

	@Column(name = "status", nullable = false)
	@Enumerated(EnumType.STRING)
	@Type(type = "pqsql_enum")
	private StatusEnum status;

	@Column(name = "end_time")
	private LocalDateTime endTime;

	@Column(name = "duration")
	private Double duration;

	@PrimaryKeyJoinColumn
	@OneToOne(mappedBy = "testItemResults", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE })
	private IssueEntity issue;

	@OneToMany
	@JoinColumn(name = "item_id", insertable = false, updatable = false)
	@Fetch(FetchMode.SUBSELECT)
	private Set<Statistics> statistics = Sets.newHashSet();

	@OneToOne
	@MapsId
	@JoinColumn(name = "result_id")
	private TestItem testItem;

	public TestItemResults() {
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public StatusEnum getStatus() {
		return status;
	}

	public void setStatus(StatusEnum status) {
		this.status = status;
	}

	public LocalDateTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
	}

	public Double getDuration() {
		return duration;
	}

	public void setDuration(Double duration) {
		this.duration = duration;
	}

	public IssueEntity getIssue() {
		return issue;
	}

	public void setIssue(IssueEntity issue) {
		this.issue = issue;
	}

	public Set<Statistics> getStatistics() {
		return statistics;
	}

	public void setStatistics(Set<Statistics> statistics) {
		this.statistics = statistics;
	}

	public TestItem getTestItem() {
		return testItem;
	}

	public void setTestItem(TestItem testItem) {
		this.testItem = testItem;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		TestItemResults that = (TestItemResults) o;
		return Objects.equals(itemId, that.itemId) && status == that.status && Objects.equals(endTime, that.endTime) && Objects.equals(
				duration,
				that.duration
		);
	}

	@Override
	public int hashCode() {
		return Objects.hash(itemId, status, endTime, duration);
	}
}
