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

package com.epam.ta.reportportal.entity.analytics;

import com.epam.ta.reportportal.entity.Metadata;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.springframework.data.annotation.CreatedDate;

/**
 * @author Siarhei Hrabko
 */
@Entity
@TypeDef(name = "meta", typeClass = Metadata.class)
@Table(name = "analytics_data", schema = "public")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AnalyticsData implements Serializable {

  private static final long serialVersionUID = 923392982L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", unique = true, nullable = false, precision = 64)
  private Long id;

  @Column(name = "type")
  private String type;

  @Column(name = "metadata")
  @Type(type = "meta")
  private Metadata metadata;

  @Column(name = "created_at", nullable = false)
  @CreatedDate
  private Instant createdAt;

}
