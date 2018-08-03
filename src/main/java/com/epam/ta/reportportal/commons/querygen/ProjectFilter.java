package com.epam.ta.reportportal.commons.querygen;

import com.epam.ta.reportportal.commons.querygen.constant.GeneralCriteriaConstant;

public class ProjectFilter extends Filter {

	private ProjectFilter(Filter filter, String project) {
		super(filter.getTarget(), filter.getFilterConditions());
		getFilterConditions().add(new FilterCondition(Condition.EQUALS, false, project, GeneralCriteriaConstant.PROJECT));

	}

	public static Filter of(Filter filter, String project) {
		return new ProjectFilter(filter, project);
	}

}
