/*
 * Copyright 2017 EPAM Systems
 *
 *
 * This file is part of EPAM Report Portal.
 * https://github.com/reportportal/service-api
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
package com.epam.ta.reportportal.commons;

import com.epam.ta.reportportal.database.entity.item.TestItem;
import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Pavel Bortnik
 */
public class MoreCollectorsTest {

	@Test
	public void toLinkedMap() {
		List<TestItem> testData = getTestData(5);
		Map<String, String> collect = testData.stream().collect(MoreCollectors.toLinkedMap(TestItem::getId, TestItem::getUniqueId));
		assertTrue(collect instanceof LinkedHashMap);
		assertEquals(collect.size(), testData.size());
	}

	private List<TestItem> getTestData(final int count) {
		List<TestItem> list = new ArrayList<>(count);
		for (int i = 0; i < count; i++) {
			TestItem testItem = new TestItem();
			testItem.setId(String.valueOf(i));
			testItem.setUniqueId("unique" + i);
			list.add(testItem);
		}
		return list;
	}

}