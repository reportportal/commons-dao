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

import com.epam.ta.reportportal.dao.converters.JpaInstantConverter;
import com.epam.ta.reportportal.entity.Metadata;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedDate;

/**
 * @author Siarhei Hrabko
 */
@Entity
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
  @Type(Metadata.class)
  private Metadata metadata;

  @Column(name = "created_at", nullable = false)
  @CreatedDate
  @Convert(converter = JpaInstantConverter.class)
  private Instant createdAt;

}
