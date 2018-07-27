package com.epam.ta.reportportal.dao;

import com.epam.ta.reportportal.commons.querygen.Filter;
import com.epam.ta.reportportal.entity.widget.content.MostFailedContent;
import com.epam.ta.reportportal.entity.widget.content.StatisticsContent;

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
	List<StatisticsContent> overallStatisticsContent(Filter filter, Map<String, List<String>> contentFields, boolean latestMode);

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
}
