package com.epam.ta.reportportal.commons.querygen;

import static com.epam.ta.reportportal.commons.querygen.constant.GeneralCriteriaConstant.PROJECT_ID;

public class ProjectFilter extends Filter {

	private ProjectFilter(Filter filter, Long projectId) {
		super(filter.getTarget(), filter.getFilterConditions());
		getFilterConditions().add(new FilterCondition(Condition.EQUALS, false, String.valueOf(projectId), PROJECT_ID));

	}

	public static Filter of(Filter filter, Long projectId) {
		return new ProjectFilter(filter, projectId);
	}

}
