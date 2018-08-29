package com.epam.ta.reportportal.commons.querygen;

import com.epam.ta.reportportal.commons.querygen.constant.GeneralCriteriaConstant;

public class ProjectFilter extends Filter {

	private ProjectFilter(Filter filter, Long projectId) {
		super(filter.getTarget(), filter.getFilterConditions());
		getFilterConditions().add(new FilterCondition(
				Condition.EQUALS,
				false,
				String.valueOf(projectId),
				GeneralCriteriaConstant.PROJECT_ID
		));

	}

	public static Filter of(Filter filter, Long projectId) {
		return new ProjectFilter(filter, projectId);
	}

}
