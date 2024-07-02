/*
 * Copyright 2024 EPAM Systems
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

package com.epam.ta.reportportal.entity.organization;

import com.epam.ta.reportportal.entity.enums.OrganizationType;
import java.io.Serializable;
import java.time.Instant;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Siarhei Hrabko
 */
@Getter
@Setter
@NoArgsConstructor
public class OrganizationUserFilter implements Serializable {

  public static final String PROJECTS_QUANTITY = "projectsQuantity";
  public static final String LAST_LOGIN = "lastLogin";
  public static final String ORGANIZATION_ID = "organization_id";

  private String fullName;
  private String email;
  private int projectsQuantity;
  private Instant lastLogin;
  private String organizationId;


}
