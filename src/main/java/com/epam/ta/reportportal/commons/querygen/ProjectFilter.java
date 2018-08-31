package com.epam.ta.reportportal.commons.querygen;

import com.epam.ta.reportportal.commons.querygen.constant.GeneralCriteriaConstant;

public class ProjectFilter extends Filter {

	private ProjectFilter(String name, Filter filter, Long projectId) {
		super(name, filter.getTarget(), filter.getFilterConditions());
		getFilterConditions().add(new FilterCondition(
				Condition.EQUALS,
				false,
				String.valueOf(projectId),
				GeneralCriteriaConstant.PROJECT_ID
		));

	}

	public static Filter of(String name, Filter filter, Long projectId) {
		return new ProjectFilter(name, filter, projectId);
	}

}
