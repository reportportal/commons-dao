/*
 * Copyright 2017 EPAM Systems
 *
 *
 * This file is part of EPAM Report Portal.
 * https://github.com/reportportal/service-api
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

package com.epam.ta.reportportal.entity.item;

import com.epam.ta.reportportal.entity.item.issue.IssueType;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Pavel Bortnik
 */
@Entity
@Table(name = "issue_statistics", schema = "public")
public class IssueStatistics implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "is_id")
	private Long id;

	@Column(name = "is_counter")
	private Integer counter;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "issue_type_id")
	private IssueType issueType;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getCounter() {
		return counter;
	}

	public void setCounter(Integer counter) {
		this.counter = counter;
	}

	public IssueType getIssueType() {
		return issueType;
	}

	public void setIssueType(IssueType issueType) {
		this.issueType = issueType;
	}

	@Override
	public String toString() {
		return "IssueStatistics{" + "id=" + id + ", counter=" + counter + ", issueType=" + issueType + '}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		IssueStatistics that = (IssueStatistics) o;

		if (id != null ? !id.equals(that.id) : that.id != null) {
			return false;
		}
		if (counter != null ? !counter.equals(that.counter) : that.counter != null) {
			return false;
		}
		return issueType != null ? issueType.equals(that.issueType) : that.issueType == null;
	}

	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (counter != null ? counter.hashCode() : 0);
		result = 31 * result + (issueType != null ? issueType.hashCode() : 0);
		return result;
	}
}
