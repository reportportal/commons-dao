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
/*
 * This file is part of Report Portal.
 *
 * Report Portal is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Report Portal is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Report Portal.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.epam.ta.reportportal.database.entity.statistics;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.springframework.data.mongodb.core.mapping.Document;

import com.epam.ta.reportportal.database.entity.item.issue.TestItemIssueType;
import com.epam.ta.reportportal.database.search.FilterCriteria;

//@formatter:off
/**
 * Represents counters for test step issue resolutions. It contains counters of the following statistics:
 * - product bugs amount,
 * - automation bugs,
 * - system issues,
 * - to investigate,
 * - no defect
 *
 * @author Dzianis Shlychkou
 * @author Andrei_Ramanchuk
*/
//@formatter:on
@Document
public class IssueCounter implements Serializable {
	private static final long serialVersionUID = 5715814904323790771L;

	public static final String DEFECTS_FOR_DB = "statistics.issueCounter";
	public static final String DEFECTS_FOR_FILTER = "statistics$defects";
	public static final String GROUP_TOTAL = "total";

	public static final String PRODUCT_BUG_CRITERIA = "statistics$defects$product_bug";
	public static final String AUTOMATION_BUG_CRITERIA = "statistics$defects$automation_bug";
	public static final String SYSTEM_ISSUE_CRITERIA = "statistics$defects$system_issue";
	public static final String TO_INVESTIGATE_CRITERIA = "statistics$defects$to_investigate";
	public static final String NO_DEFECT_CRITERIA = "statistics$defects$no_defect";

	@FilterCriteria("product_bug")
	private Map<String, Integer> productBug;

	@FilterCriteria("automation_bug")
	private Map<String, Integer> automationBug;

	@FilterCriteria("system_issue")
	private Map<String, Integer> systemIssue;

	@FilterCriteria("to_investigate")
	private Map<String, Integer> toInvestigate;

	@FilterCriteria("no_defect")
	private Map<String, Integer> noDefect;

	public IssueCounter(Map<String, Integer> productBug, Map<String, Integer> automationBug, Map<String, Integer> systemIssue,
			Map<String, Integer> toInvestigate, Map<String, Integer> noDefect) {
		this.productBug = productBug;
		this.automationBug = automationBug;
		this.systemIssue = systemIssue;
		this.toInvestigate = toInvestigate;
		this.noDefect = noDefect;
	}

	@SuppressWarnings("serial")
	public IssueCounter() {
		this.productBug = new HashMap<String, Integer>() {
			{
				put(TestItemIssueType.PRODUCT_BUG.getLocator(), 0);
				put(GROUP_TOTAL, 0);
			}
		};
		this.automationBug = new HashMap<String, Integer>() {
			{
				put(TestItemIssueType.AUTOMATION_BUG.getLocator(), 0);
				put(GROUP_TOTAL, 0);
			}
		};
		this.systemIssue = new HashMap<String, Integer>() {
			{
				put(TestItemIssueType.SYSTEM_ISSUE.getLocator(), 0);
				put(GROUP_TOTAL, 0);
			}
		};
		this.noDefect = new HashMap<String, Integer>() {
			{
				put(TestItemIssueType.NO_DEFECT.getLocator(), 0);
				put(GROUP_TOTAL, 0);
			}
		};
		this.toInvestigate = new HashMap<String, Integer>() {
			{
				put(TestItemIssueType.TO_INVESTIGATE.getLocator(), 0);
				put(GROUP_TOTAL, 0);
			}
		};
	}

	/* ============== PRODUCT BUG ============= */
	public Integer getProductBugTotal() {
		return productBug.get(GROUP_TOTAL);
	}

	public Integer getProductBug(String locator) {
		return null != productBug.get(locator) ? productBug.get(locator) : 0;
	}

	public Map<String, Integer> getProductBug() {
		return productBug;
	}

	public void setProductBug(Map<String, Integer> productBug) {
		this.productBug = productBug;
	}

	public void setProductBug(String locator, Integer value) {
		this.productBug.put(locator, value);
	}

	/* ============ AUTOMATION BUG ============ */
	public Integer getAutomationBugTotal() {
		return automationBug.get(GROUP_TOTAL);
	}

	public Integer getAutomationBug(String locator) {
		return null != automationBug.get(locator) ? automationBug.get(locator) : 0;
	}

	public Map<String, Integer> getAutomationBug() {
		return automationBug;
	}

	public void setAutomationBug(Map<String, Integer> automationBug) {
		this.automationBug = automationBug;
	}

	public void setAutomationBug(String locator, Integer value) {
		this.automationBug.put(locator, value);
	}

	/* ============ SYSTEM ISSUE ============== */
	public Integer getSystemIssueTotal() {
		return systemIssue.get(GROUP_TOTAL);
	}

	public Integer getSystemIssue(String locator) {
		return null != systemIssue.get(locator) ? systemIssue.get(locator) : 0;
	}

	public Map<String, Integer> getSystemIssue() {
		return systemIssue;
	}

	public void setSystemIssue(Map<String, Integer> systemIssue) {
		this.systemIssue = systemIssue;
	}

	public void setSystemIssue(String locator, Integer value) {
		this.systemIssue.put(locator, value);
	}

	/* ============ TO INVESTIGATE =========== */
	public Integer getToInvestigateTotal() {
		return toInvestigate.get(GROUP_TOTAL);
	}

	public Integer getToInvestigate(String locator) {
		return null != toInvestigate.get(locator) ? toInvestigate.get(locator) : 0;
	}

	public Map<String, Integer> getToInvestigate() {
		return toInvestigate;
	}

	public void setToInvestigate(Map<String, Integer> toInvestigate) {
		this.toInvestigate = toInvestigate;
	}

	public void setToInvestigate(String locator, Integer value) {
		this.toInvestigate.put(locator, value);
	}

	/* ============== NO DEFECT ============= */
	public Integer getNoDefectTotal() {
		return noDefect.get(GROUP_TOTAL);
	}

	public Integer getNoDefect(String locator) {
		return null != noDefect.get(locator) ? noDefect.get(locator) : 0;
	}

	public Map<String, Integer> getNoDefect() {
		return noDefect;
	}

	public void setNoDefect(Map<String, Integer> noDefect) {
		this.noDefect = noDefect;
	}

	public void setNoDefect(String locator, Integer value) {
		this.noDefect.put(locator, value);
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public boolean isEmpty() {
		int pb = productBug.values().stream().mapToInt(Integer::intValue).sum();
		int ab = automationBug.values().stream().mapToInt(Integer::intValue).sum();
		int si = systemIssue.values().stream().mapToInt(Integer::intValue).sum();
		int nd = noDefect.values().stream().mapToInt(Integer::intValue).sum();
		int ti = toInvestigate.values().stream().mapToInt(Integer::intValue).sum();
		return ((pb + ab + si + nd + ti) == 0);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((automationBug == null) ? 0 : automationBug.hashCode());
		result = prime * result + ((noDefect == null) ? 0 : noDefect.hashCode());
		result = prime * result + ((productBug == null) ? 0 : productBug.hashCode());
		result = prime * result + ((systemIssue == null) ? 0 : systemIssue.hashCode());
		result = prime * result + ((toInvestigate == null) ? 0 : toInvestigate.hashCode());
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
		IssueCounter other = (IssueCounter) obj;
		if (automationBug == null) {
			if (other.automationBug != null)
				return false;
		} else if (!automationBug.equals(other.automationBug))
			return false;
		if (noDefect == null) {
			if (other.noDefect != null)
				return false;
		} else if (!noDefect.equals(other.noDefect))
			return false;
		if (productBug == null) {
			if (other.productBug != null)
				return false;
		} else if (!productBug.equals(other.productBug))
			return false;
		if (systemIssue == null) {
			if (other.systemIssue != null)
				return false;
		} else if (!systemIssue.equals(other.systemIssue))
			return false;
		if (toInvestigate == null) {
			if (other.toInvestigate != null)
				return false;
		} else if (!toInvestigate.equals(other.toInvestigate))
			return false;
		return true;
	}

	@Override
	// TODO update in readable view of maps!
	public String toString() {
		return "IssueCounter [productBug=" + productBug + ", automationBug=" + automationBug + ", systemIssue=" + systemIssue
				+ ", toInvestigate=" + toInvestigate + ", noDefect=" + noDefect + "]";
	}
}