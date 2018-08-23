package com.epam.ta.reportportal.dao;

import com.epam.ta.reportportal.commons.querygen.Filter;
import com.epam.ta.reportportal.entity.widget.content.*;

import java.util.List;
import java.util.Map;

/**
 * @author Pavel Bortnik
 */
public interface WidgetContentRepository {

	/**
	 * Overall statistics content loading.
	 *
	 * @param filter Filter
	 * @return List of {@link StatisticsContent}
	 */
	List<StatisticsContent> overallStatisticsContent(Filter filter, List<String> contentFields, boolean latestMode, int limit);

	/**
	 * Most failed content loading by execution status criteria.
	 *
	 * @param launchName Launch name
	 * @param criteria   Execution criteria
	 * @param limit      Results limit
	 * @return List of {@link MostFailedContent}
	 */
	List<MostFailedContent> mostFailedByExecutionCriteria(String launchName, String criteria, int limit);

	/**
	 * Most failed content loading by defect status criteria.
	 *
	 * @param launchName Launch name
	 * @param criteria   Defect criteria
	 * @param limit      Results limit
	 * @return List of {@link MostFailedContent}
	 */
	List<MostFailedContent> mostFailedByDefectCriteria(String launchName, String criteria, int limit);

	/**
	 * Launch statistics content loading
	 *
	 * @param filter        Filter
	 * @param contentFields Fields with restrictions
	 * @param limit         Results limit
	 * @return List of {@link LaunchesStatisticsContent}
	 */
	List<LaunchesStatisticsContent> launchStatistics(Filter filter, List<String> contentFields, int limit);

	/**
	 * Investigated statistics loading
	 *
	 * @param filter Filter
	 * @param limit  Results limit
	 * @return List of{@link InvestigatedStatisticsResult}
	 */
	List<InvestigatedStatisticsResult> investigatedStatistics(Filter filter, int limit);

	/**
	 * Launches passing rate result for launch or for all launches depending on the filter conditions
	 *
	 * @param filter        Filter
	 * @param limit         Results limit
	 * @return {@link PassingRateStatisticsResult}
	 */
	PassingRateStatisticsResult passingRateStatistics(Filter filter, int limit);

	/**
	 * Test cases' count trend loading
	 *
	 * @param filter        		Filter
	 * @param executionContentField Content field with table column name
	 * @param limit         		Results limit
	 * @return List of{@link CasesTrendContent}
	 */
	List<CasesTrendContent> casesTrendStatistics(Filter filter, String executionContentField, int limit);

	/**
	 * Bug trend loading
	 *
	 * @param filter        Filter
	 * @param contentFields Fields with restrictions
	 * @param limit         Results limit
	 * @return List of{@link LaunchesStatisticsContent}
	 */
	List<LaunchesStatisticsContent> bugTrendStatistics(Filter filter, List<String> contentFields, int limit);

	/**
	 * Comparison statistics content loading for launches with specified Ids
	 *
	 * @param filter        Filter
	 * @param contentFields Fields with restrictions
	 * @param limit         Results limit
	 * @return List of{@link LaunchesStatisticsContent}
	 */
	List<LaunchesStatisticsContent> launchesComparisonStatistics(Filter filter, List<String> contentFields, int limit);

	/**
	 * Launches duration content loading
	 *
	 * @param filter        Filter
	 * @param limit         Results limit
	 * @return List of{@link LaunchesDurationContent}
	 */
	List<LaunchesDurationContent> launchesDurationStatistics(Filter filter, int limit);

	/**
	 * Not passed cases content loading
	 *
	 * @param filter        Filter
	 * @param limit         Results limit
	 * @return List of{@link NotPassedCasesContent}
	 */
	List<NotPassedCasesContent> notPassedCasesStatistics(Filter filter, int limit);

	/**
	 * Launches table content loading
	 *
	 * @param filter        Filter
	 * @param contentFields Fields with restrictions
	 * @param limit         Results limit
	 * @return List of{@link LaunchesStatisticsContent}
	 */
	List<LaunchesStatisticsContent> launchesTableStatistics(Filter filter, List<String> contentFields, int limit);

	/**
	 * User activity content loading
	 *
	 * @param filter        Filter
	 * @param login         User login for activity tracking
	 * @param contentFields Fields with restrictions
	 * @param limit         Results limit
	 * @return List of{@link ActivityContent}
	 */
	List<ActivityContent> activityStatistics(Filter filter, String login, List<String> contentFields, int limit);

	/**
	 * Loading unique bugs content that was produced by Bug Tracking System
	 *
	 * @param filter Filter
	 * @param limit  Results limit
	 * @return Map grouped by ticket id as key and List of {@link UniqueBugContent} as value
	 */
	Map<String, List<UniqueBugContent>> uniqueBugStatistics(Filter filter, int limit);

	/**
	 * Loading the most "flaky" test cases content
	 *
	 * @param filter Filter
	 * @param limit  Results limit
	 * @return List of {@link FlakyCasesTableContent}
	 */
	List<FlakyCasesTableContent> flakyCasesStatistics(Filter filter, int limit);
}
