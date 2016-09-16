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
 * Represents counters for test item execution events. It contains counters of the following statistics:
 * - total test steps;
 * - passed;
 * - failed;
 * - skipped;
 *
 * @author Dzianis Shlychkou
 *
 */
//@formatter:on
@Document
public class ExecutionCounter implements Serializable {
	
	private static final long serialVersionUID = 6667941920372294241L;
	
	public static final String TOTAL_CRITERIA = "statistics$executions$total";
	public static final String PASSED_CRITERIA = "statistics$executions$passed";
	public static final String FAILED_CRITERIA = "statistics$executions$failed";
	public static final String SKIPPED_CRITERIA = "statistics$executions$skipped";
	
	@FilterCriteria("total")
	private Integer total;
	
	@FilterCriteria("passed")
	private Integer passed;
	
	@FilterCriteria("failed")
	private Integer failed;
	
	@FilterCriteria("skipped")
	private Integer skipped;
	
	public ExecutionCounter(Integer total, Integer passed, Integer failed, Integer skipped) {
		this.total = total;
		this.passed = passed;
		this.failed = failed;
		this.skipped = skipped;
	}
	
	public ExecutionCounter() {
		this(0, 0, 0, 0);
	}
	
	public Integer getTotal() {
		return total;
	}
	
	public void setTotal(Integer total) {
		this.total = total;
	}
	
	public Integer getPassed() {
		return passed;
	}
	
	public void setPassed(Integer passed) {
		this.passed = passed;
	}
	
	public Integer getFailed() {
		return failed;
	}
	
	public void setFailed(Integer failed) {
		this.failed = failed;
	}
	
	public Integer getSkipped() {
		return skipped;
	}
	
	public void setSkipped(Integer skipped) {
		this.skipped = skipped;
	}
	
	public boolean isEmpty() {
		return ((total + passed + failed + skipped) == 0);
		
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((failed == null) ? 0 : failed.hashCode());
		result = prime * result + ((passed == null) ? 0 : passed.hashCode());
		result = prime * result + ((skipped == null) ? 0 : skipped.hashCode());
		result = prime * result + ((total == null) ? 0 : total.hashCode());
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
		ExecutionCounter other = (ExecutionCounter) obj;
		if (failed == null) {
			if (other.failed != null)
				return false;
		} else if (!failed.equals(other.failed))
			return false;
		if (passed == null) {
			if (other.passed != null)
				return false;
		} else if (!passed.equals(other.passed))
			return false;
		if (skipped == null) {
			if (other.skipped != null)
				return false;
		} else if (!skipped.equals(other.skipped))
			return false;
		if (total == null) {
			if (other.total != null)
				return false;
		} else if (!total.equals(other.total))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TestItemExecutionCounter [total=");
		builder.append(total);
		builder.append(", passed=");
		builder.append(passed);
		builder.append(", failed=");
		builder.append(failed);
		builder.append(", skipped=");
		builder.append(skipped);
		builder.append("]");
		return builder.toString();
	}
	
}