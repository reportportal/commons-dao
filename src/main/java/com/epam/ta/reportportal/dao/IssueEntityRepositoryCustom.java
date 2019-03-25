/*
 * Copyright 2019 EPAM Systems
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.epam.ta.reportportal.dao;

/**
 * @author <a href="mailto:ivan_budayeu@epam.com">Ivan Budayeu</a>
 */
public interface IssueEntityRepositoryCustom {

	/**
	 * @param itemId           {@link com.epam.ta.reportportal.entity.item.TestItem#itemId}
	 * @param issueTypeId      {@link com.epam.ta.reportportal.entity.item.issue.IssueType#id}
	 * @param description      {@link com.epam.ta.reportportal.entity.item.issue.IssueEntity#issueDescription}
	 * @param isAutoAnalyzed   {@link com.epam.ta.reportportal.entity.item.issue.IssueEntity#autoAnalyzed}
	 * @param isIgnoreAnalyzer {@link com.epam.ta.reportportal.entity.item.issue.IssueEntity#ignoreAnalyzer}
	 * @return 1 if inserted, otherwise 0
	 */
	int insertByItemIdAndIssueTypeId(Long itemId, Long issueTypeId, String description, boolean isAutoAnalyzed, boolean isIgnoreAnalyzer);
}
