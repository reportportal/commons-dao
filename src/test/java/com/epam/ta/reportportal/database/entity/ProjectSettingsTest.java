package com.epam.ta.reportportal.database.entity;

import org.junit.Assert;
import org.junit.Test;

import com.epam.ta.reportportal.database.entity.statistics.StatisticSubType;

public class ProjectSettingsTest {

	@Test
	public void notIssue() {
		final Project.Configuration projectSettings = new Project.Configuration();
		final StatisticSubType not_issue = projectSettings.getByLocator("NOT_IsSUE");
		Assert.assertNull(not_issue);
	}

	@Test
	public void toInvestigate() {
		final Project.Configuration projectSettings = new Project.Configuration();
		final StatisticSubType subType = projectSettings.getByLocator("Ti001");
		Assert.assertNotNull(subType);
		Assert.assertEquals("TO_INVESTIGATE", subType.getTypeRef());
	}

	@Test
	public void incorrectLocator() {
		final Project.Configuration projectSettings = new Project.Configuration();
		final StatisticSubType incorrect = projectSettings.getByLocator("incorrect");
		Assert.assertNull(incorrect);
	}

}