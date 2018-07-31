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

	Map<Integer, Map<String, Integer>> launchStatistics(Filter filter, Map<String, List<String>> contentFields);

	List<InvestigatedStatisticsResult> investigatedStatistics(Filter filter, Map<String, List<String>> contentFields);

	PassStatisticsResult launchPassPerLaunchStatistics(Filter filter, Map<String, List<String>> contentFields, Launch launch);

	PassStatisticsResult summaryPassStatistics(Filter filter, Map<String, List<String>> contentFields);

	List<CasesTrendContent> casesTrendStatistics(Filter filter, Map<String, List<String>> contentFields);

	Map<Integer, Map<String, Integer>> bugTrendStatistics(Filter filter, Map<String, List<String>> contentFields);

	Map<Integer, Map<String, Double>> launchesComparisonStatistics(Filter filter, Map<String, List<String>> contentFields,
			Long... launchNumbers);

	List<LaunchesDurationContent> launchesDurationStatistics(Filter filter, Map<String, List<String>> contentFields);

	List<NotPassedCasesContent> notPassedCasesStatistics(Filter filter, Map<String, List<String>> contentFields);

	List<LaunchesTableContent> launchesTableStatistics(Filter filter, Map<String, List<String>> contentFields, List<String> tableColumns);
}
