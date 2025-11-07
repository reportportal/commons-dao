package com.epam.ta.reportportal.entity.tms;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Entity representing a comment for test case execution.
 */
@Entity
@Table(name = "tms_test_case_execution_comment")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "id")
public class TmsTestCaseExecutionComment implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne
  @JoinColumn(name = "execution_id", nullable = false, unique = true)
  @ToString.Exclude
  private TmsTestCaseExecution execution;

  @Column(name = "comment", columnDefinition = "text")
  private String comment;

  @OneToMany(mappedBy = "executionComment", fetch = FetchType.LAZY)
  @ToString.Exclude
  private Set<TmsTestCaseExecutionCommentAttachment> attachments;
}
