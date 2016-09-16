/*
 * Copyright 2016 EPAM Systems
 * 
 * 
 * This file is part of EPAM Report Portal.
 * https://github.com/epam/ReportPortal
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
package com.epam.ta.reportportal.database.dao;

import static java.util.stream.Collectors.toList;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.epam.ta.reportportal.BaseDaoTest;
import com.epam.ta.reportportal.database.entity.widget.ContentOptions;
import com.epam.ta.reportportal.database.entity.widget.Widget;

public class WidgetCustomRepositoryTest extends BaseDaoTest {

	@Autowired
	private WidgetRepository widgetRepository;

	private String widgetId;

	@Before
	public void prepareData() {
		Widget widget = new Widget();
		ContentOptions contentOptions = new ContentOptions();
		List<String> contentFields = Arrays.asList("1", "2");
		contentOptions.setContentFields(contentFields);
		widget.setContentOptions(contentOptions);
		Widget save = widgetRepository.save(widget);
		widgetId = save.getId();
	}

	@Test
	public void deleteContentFieldTest() {
		widgetRepository.removeContentField(widgetId, "1");
		Widget widget = widgetRepository.findOne(widgetId);
		Assert.assertNotNull(widget.getContentOptions().getContentFields());
		Assert.assertEquals(1, widget.getContentOptions().getContentFields().size());
	}

	@Test
	public void addContentFieldTest() {
		widgetRepository.addContentField(widgetId, "field");
		Widget widget = widgetRepository.findOne(widgetId);
		Assert.assertNotNull(widget.getContentOptions().getContentFields());
		Assert.assertEquals(1,
				widget.getContentOptions().getContentFields().stream().filter(it -> it.equals("field")).collect(toList()).size());
	}

}