package com.epam.ta.reportportal.entity.tms;

import com.epam.ta.reportportal.entity.item.TestItem;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "tms_test_case_execution")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TmsTestCaseExecution {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne
  @JoinColumn(name = "test_item_id")
  private TestItem testItem;

  @Column(name = "test_case_id")
  private Long testCaseId;

  @Column(name = "test_case_version_id")
  private Long testCaseVersionId;

  @JdbcTypeCode(SqlTypes.JSON)
  @Column(name = "test_case_snapshot", nullable = false, columnDefinition = "jsonb")
  private String testCaseSnapshot;
}
