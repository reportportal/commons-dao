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

package com.epam.ta.reportportal.database.search;

import static com.epam.ta.reportportal.database.entity.item.issue.TestItemIssueType.*;
import static com.epam.ta.reportportal.database.entity.statistics.IssueCounter.GROUP_TOTAL;

import org.springframework.data.mongodb.core.query.Update;

import com.epam.ta.reportportal.database.entity.Status;
import com.epam.ta.reportportal.database.entity.item.TestItem;
import com.epam.ta.reportportal.database.entity.statistics.IssueCounter;
import com.epam.ta.reportportal.database.entity.statistics.StatisticSubType;
import com.epam.ta.reportportal.database.entity.statistics.Statistics;

import java.util.StringJoiner;

/**
 * MongoDB statistics builder {@link Update}. Allows to update\reset\delete
 * 
 * @author Andrei_Ramanchuk
 */
public class UpdateStatisticsQueryBuilder {

	/* MongoDB execution statistic object */
	private static final String EXECUTION_COUNTER = "statistics.executionCounter";

	/* MongoDB issue statistic object */
	private static final String ISSUE_COUNTER = "statistics.issueCounter";

	private UpdateStatisticsQueryBuilder() {
	}

	public static Update fromItemStatusAware(final Status status, final int totalCounter, final int statusCounter) {
		Update updateStatusAware = new Update().inc(EXECUTION_COUNTER + ".total", totalCounter);
		Status providedStatus = status != null ? status : Status.FAILED;
		if (providedStatus.awareStatisticsField() != null && !providedStatus.awareStatisticsField().isEmpty()) {
			updateStatusAware = updateStatusAware.inc(EXECUTION_COUNTER + "." + status.awareStatisticsField(), statusCounter);
		}
		return updateStatusAware;
	}

	public static Update fromItemStatusAware(final TestItem item, boolean isReset) {
		Statistics current = item.getStatistics();
		int iTotal = current.getExecutionCounter().getPassed() + current.getExecutionCounter().getFailed()
				+ current.getExecutionCounter().getSkipped();
		int iPassed = current.getExecutionCounter().getPassed();
		int iFailed = current.getExecutionCounter().getFailed();
		int iSkipped = current.getExecutionCounter().getSkipped();
		Update updateStatusAware = new Update().inc(EXECUTION_COUNTER + ".total", isReset ? iTotal * -1 : iTotal);
		if (item.getStatus().awareStatisticsField() != null && !item.getStatus().awareStatisticsField().isEmpty()) {
			updateStatusAware = updateStatusAware.inc(EXECUTION_COUNTER + "." + Status.PASSED.awareStatisticsField(),
					isReset ? iPassed * -1 : iPassed);
			updateStatusAware = updateStatusAware.inc(EXECUTION_COUNTER + "." + Status.FAILED.awareStatisticsField(),
					isReset ? iFailed * -1 : iFailed);
			updateStatusAware = updateStatusAware.inc(EXECUTION_COUNTER + "." + Status.SKIPPED.awareStatisticsField(),
					isReset ? iSkipped * -1 : iSkipped);
		}
		return updateStatusAware;
	}

	/**
	 * Aware issue statistic field is specified, or to to_investigate field
	 * instead.
	 * 
	 * @param subType
	 *            - <b>null</b> is cannot be specified
	 * @param issueTypeCounter
	 *            - count of issues for specified issue counter field (or not
	 *            specified)
	 * @return Update - MongoDB update object
	 */
	/* RESET methods in old statistics */
	public static Update fromIssueTypeAware(final StatisticSubType subType, int issueTypeCounter) {
		Update issueTypeAware = new Update();
		issueTypeAware
				.inc(ISSUE_COUNTER + "."
						+ (subType != null ? valueOf(subType.getTypeRef()).awareStatisticsField() + "." + subType.getLocator()
								: TO_INVESTIGATE.awareStatisticsField() + "." + TO_INVESTIGATE.getLocator()),
						issueTypeCounter)
				.inc(ISSUE_COUNTER + "." + (subType != null ? valueOf(subType.getTypeRef()).awareStatisticsField() + "." + GROUP_TOTAL
						: TO_INVESTIGATE.awareStatisticsField() + "." + GROUP_TOTAL), issueTypeCounter);
		return issueTypeAware;
	}

	/**
	 * Remove specified custom sub-type for issue statistic of applied element
	 * 
	 * @param subType
	 * @return MongoDB
	 */
	public static Update dropIssueTypeAware(final StatisticSubType subType) {
		return new Update().unset(ISSUE_COUNTER + "." + valueOf(subType.getTypeRef()).awareStatisticsField() + "." + subType.getLocator());
	}

	/**
	 * Complex update operator for MongoDB with positive or negative increment
	 * for all defined issue sub-types of specified test item.
	 *
     * upd: since there are different methods for incrementing, this is used
     * only for deleting item's statistics.
     *
	 * @param item
	 * @return Update
	 */
	/* DELETE methods in old statistics */
	public static Update fromIssueTypeAware(final TestItem item) {
		IssueCounter issueCounter = item.getStatistics().getIssueCounter();
		/* MongoDB Update object instance initialization */
		Update issueStatusAware = new Update();
		issueCounter.getAutomationBug().forEach((k, v) -> decreaseIssueCounter(issueStatusAware, AUTOMATION_BUG.awareStatisticsField(), k, v));
		issueCounter.getProductBug().forEach((k, v) -> decreaseIssueCounter(issueStatusAware, PRODUCT_BUG.awareStatisticsField(), k, v));
		issueCounter.getSystemIssue().forEach((k, v) -> decreaseIssueCounter(issueStatusAware, SYSTEM_ISSUE.awareStatisticsField(), k, v));
		issueCounter.getNoDefect().forEach((k, v) -> decreaseIssueCounter(issueStatusAware, NO_DEFECT.awareStatisticsField(), k, v));
		issueCounter.getToInvestigate().forEach((k, v) -> decreaseIssueCounter(issueStatusAware, TO_INVESTIGATE.awareStatisticsField(), k, v));
		return issueStatusAware;
	}

	private static void decreaseIssueCounter(Update issueStatusAware, String statisticsField, String defectField, int value) {
	    int negative = value * -1;
        StringJoiner joiner = new StringJoiner(".");
        String key = joiner.add(ISSUE_COUNTER).add(statisticsField).add(defectField).toString();
        issueStatusAware.inc(key, negative);
    }
}