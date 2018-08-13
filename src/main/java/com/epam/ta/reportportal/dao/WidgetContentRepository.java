package com.epam.ta.reportportal.dao;

import com.epam.ta.reportportal.commons.querygen.Filter;
import com.epam.ta.reportportal.entity.launch.Launch;
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
	List<StatisticsContent> overallStatisticsContent(Filter filter, Map<String, List<String>> contentFields, boolean latestMode, int limit);

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
	 * @return List of {@link Launch}
	 */
	List<Launch> launchStatistics(Filter filter, Map<String, List<String>> contentFields, int limit);

	/**
	 * Investigated statistics loading
	 *
	 * @param filter        Filter
	 * @param contentFields Fields with restrictions
	 * @param limit         Results limit
	 * @return List of{@link InvestigatedStatisticsResult}
	 */
	List<InvestigatedStatisticsResult> investigatedStatistics(Filter filter, Map<String, List<String>> contentFields, int limit);

	/**
	 * Launch pass result for specified launch
	 *
	 * @param filter        Filter
	 * @param contentFields Fields with restrictions
	 * @param launch        Statistics' target launch
	 * @param limit         Results limit
	 * @return {@link PassStatisticsResult}
	 */
	PassStatisticsResult launchPassPerLaunchStatistics(Filter filter, Map<String, List<String>> contentFields, Launch launch, int limit);

	/**
	 * Launches pass result for all launches
	 *
	 * @param filter        Filter
	 * @param contentFields Fields with restrictions
	 * @param limit         Results limit
	 * @return {@link PassStatisticsResult}
	 */
	PassStatisticsResult summaryPassStatistics(Filter filter, Map<String, List<String>> contentFields, int limit);

	/**
	 * Test cases' count trend loading
	 *
	 * @param filter        Filter
	 * @param contentFields Fields with restrictions
	 * @param limit         Results limit
	 * @return List of{@link CasesTrendContent}
	 */
	List<CasesTrendContent> casesTrendStatistics(Filter filter, Map<String, List<String>> contentFields, int limit);

	/**
	 * Bug trend loading
	 *
	 * @param filter        Filter
	 * @param contentFields Fields with restrictions
	 * @param limit         Results limit
	 * @return List of{@link LaunchStatisticsContent}
	 */
	List<LaunchStatisticsContent> bugTrendStatistics(Filter filter, Map<String, List<String>> contentFields, int limit);

	/**
	 * Comparison statistics content loading for launches with specified Ids
	 *
	 * @param filter        Filter
	 * @param contentFields Fields with restrictions
	 * @param limit         Results limit
	 * @param launchIds     Launches' Ids for comparison statistics
	 * @return List of{@link ComparisonStatisticsContent}
	 */
	List<ComparisonStatisticsContent> launchesComparisonStatistics(Filter filter, Map<String, List<String>> contentFields, int limit,
			Long... launchIds);

	/**
	 * Launches duration content loading
	 *
	 * @param filter        Filter
	 * @param contentFields Fields with restrictions
	 * @param limit         Results limit
	 * @return List of{@link LaunchesDurationContent}
	 */
	List<LaunchesDurationContent> launchesDurationStatistics(Filter filter, Map<String, List<String>> contentFields, int limit);

	/**
	 * Not passed cases content loading
	 *
	 * @param filter        Filter
	 * @param contentFields Fields with restrictions
	 * @param limit         Results limit
	 * @return List of{@link NotPassedCasesContent}
	 */
	List<NotPassedCasesContent> notPassedCasesStatistics(Filter filter, Map<String, List<String>> contentFields, int limit);

	/**
	 * Launches table content loading
	 *
	 * @param filter        Filter
	 * @param contentFields Fields with restrictions
	 * @param limit         Results limit
	 * @return List of{@link Launch}
	 */
	List<Launch> launchesTableStatistics(Filter filter, Map<String, List<String>> contentFields, int limit);

	/**
	 * User activity content loading
	 *
	 * @param filter        Filter
	 * @param login         User login for activity tracking
	 * @param activityTypes Activities to track
	 * @param limit         Results limit
	 * @return List of{@link ActivityContent}
	 */
	List<ActivityContent> activityStatistics(Filter filter, String login, List<String> activityTypes, int limit);

	Map<String, List<UniqueBugContent>> uniqueBugStatistics(Filter filter, Map<String, List<String>> contentFields, int limit);
}
