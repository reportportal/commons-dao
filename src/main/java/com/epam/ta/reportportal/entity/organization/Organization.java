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
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Siarhei Hrabko
 */
@Entity
@Table(name = "organization", schema = "public")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Organization implements Serializable {

  private static final long serialVersionUID = 6730810629133187834L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", unique = true, nullable = false, precision = 64)
  private Long id;

  @Column(name = "creation_date", nullable = false)
  private LocalDateTime creationDate;

  @Column(name = "name")
  private String name;

  @Column(name = "organization_type")
  private OrganizationType organizationType;

  @Column(name = "slug")
  private String slug;

}
