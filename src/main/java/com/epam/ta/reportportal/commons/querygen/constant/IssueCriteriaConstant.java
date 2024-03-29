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

package com.epam.ta.reportportal.commons.querygen.constant;

/**
 * @author <a href="mailto:ihar_kahadouski@epam.com">Ihar Kahadouski</a>
 */
public final class IssueCriteriaConstant {

  public static final String CRITERIA_ISSUE_ID = "issueId";
  public static final String CRITERIA_ISSUE_AUTO_ANALYZED = "autoAnalyzed";
  public static final String CRITERIA_ISSUE_IGNORE_ANALYZER = "ignoreAnalyzer";
  public static final String CRITERIA_ISSUE_LOCATOR = "locator";
  public static final String CRITERIA_ISSUE_COMMENT = "issueComment";

  private IssueCriteriaConstant() {
    //static only
  }
}
