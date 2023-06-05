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
 * Activity search criteria fields.
 *
 * @author Ivan Budaev
 */
public final class ActivityCriteriaConstant {

  public static final String CRITERIA_ACTION = "action";
  public static final String CRITERIA_LOGIN = "login";
  public static final String CRITERIA_OBJECT_ID = "objectId";
  public static final String CRITERIA_ENTITY = "entity";
  public static final String CRITERIA_CREATION_DATE = "creationDate";
  public static final String CRITERIA_OBJECT_NAME = "objectName";

  private ActivityCriteriaConstant() {
    //static only
  }
}
