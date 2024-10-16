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

package com.epam.ta.reportportal.entity.project.email;

import com.epam.ta.reportportal.entity.enums.LogicalOperator;
import com.epam.ta.reportportal.entity.enums.SendCase;
import com.epam.ta.reportportal.entity.project.Project;
import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcType;
import org.hibernate.annotations.Type;
import org.hibernate.dialect.PostgreSQLEnumJdbcType;


/**
 * @author Ivan Budayeu
 */
@Entity
@Table(name = "sender_case")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SenderCase implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "rule_name", nullable = false, length = 55)
  private String ruleName;

  @ElementCollection(fetch = FetchType.EAGER)
  @CollectionTable(name = "recipients", joinColumns = @JoinColumn(name = "sender_case_id"))
  @Column(name = "recipient")
  private Set<String> recipients;

  @ElementCollection(fetch = FetchType.EAGER)
  @CollectionTable(name = "launch_names", joinColumns = @JoinColumn(name = "sender_case_id"))
  @Column(name = "launch_name")
  private Set<String> launchNames;

  @OneToMany(mappedBy = "senderCase", cascade = {CascadeType.PERSIST,
      CascadeType.MERGE}, orphanRemoval = true, fetch = FetchType.EAGER)
  @OrderBy
  private Set<LaunchAttributeRule> launchAttributeRules;

  @Column(name = "send_case")
  @Enumerated(EnumType.STRING)
  @JdbcType(PostgreSQLEnumJdbcType.class)
  private SendCase sendCase;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "project_id", nullable = false)
  private Project project;

  @Column(name = "enabled")
  private boolean enabled;

  @Column(name = "rule_type")
  private String type;

  @Type(SenderCaseOptions.class)
  @Column(name = "rule_details")
  private SenderCaseOptions ruleDetails;

  @Enumerated(EnumType.STRING)
  @JdbcType(PostgreSQLEnumJdbcType.class)
  @Column(name = "attributes_operator")
  private LogicalOperator attributesOperator;

}
