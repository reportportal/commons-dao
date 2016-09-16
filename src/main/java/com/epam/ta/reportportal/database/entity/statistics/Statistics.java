/*
 * Copyright 2016 EPAM Systems
 * 
 * 
 * This file is part of EPAM Report Portal.
 * https://github.com/epam/ReportPortal
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

package com.epam.ta.reportportal.database.entity.statistics;

import java.io.Serializable;

import org.springframework.data.mongodb.core.mapping.Document;

import com.epam.ta.reportportal.database.search.FilterCriteria;

//@formatter:off
/**
 *
 * Aggregated statistics for launch, test suite an test nodes of the tree data
 * model. It contains {@link ExecutionCounter} and {@link IssueCounter} statistics.
 *
 * @author Dzianis Shlychkou
 *
 */
//@formatter:on
@Document
public class Statistics implements Serializable {
	
	private static final long serialVersionUID = 9053279433571440841L;
	
	@FilterCriteria("executions")
	private ExecutionCounter executionCounter;
	
	@FilterCriteria("defects")
	private IssueCounter issueCounter;
	
	public Statistics(ExecutionCounter executionCounter, IssueCounter issueCounter) {
		this.executionCounter = executionCounter;
		this.issueCounter = issueCounter;
	}
	
	public ExecutionCounter getExecutionCounter() {
		return executionCounter;
	}
	
	public void setExecutionCounter(ExecutionCounter executionCounter) {
		this.executionCounter = executionCounter;
	}
	
	public IssueCounter getIssueCounter() {
		return issueCounter;
	}
	
	public void setIssueCounter(IssueCounter issueCounter) {
		this.issueCounter = issueCounter;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((executionCounter == null) ? 0 : executionCounter.hashCode());
		result = prime * result + ((issueCounter == null) ? 0 : issueCounter.hashCode());
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
		Statistics other = (Statistics) obj;
		if (executionCounter == null) {
			if (other.executionCounter != null)
				return false;
		} else if (!executionCounter.equals(other.executionCounter))
			return false;
		if (issueCounter == null) {
			if (other.issueCounter != null)
				return false;
		} else if (!issueCounter.equals(other.issueCounter))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TestItemStatistics [executionCounter=");
		builder.append(executionCounter.toString());
		builder.append(", issueCounter=");
		builder.append(issueCounter.toString());
		builder.append("]");
		return builder.toString();
	}
}