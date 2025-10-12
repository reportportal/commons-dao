package com.epam.ta.reportportal.dao.tms;

import com.epam.ta.reportportal.dao.ReportPortalRepository;
import com.epam.ta.reportportal.entity.tms.TmsTestCaseExecution;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TmsTestCaseExecutionRepository extends
    ReportPortalRepository<TmsTestCaseExecution, Long> {

  @Query("""
      SELECT e
      FROM TmsTestCaseExecution e
      JOIN TestItem ti ON ti.itemId = e.testItem.itemId
      JOIN Launch l ON l.id = ti.launchId
      JOIN TmsTestPlan tp ON tp.id = l.testPlanId
      WHERE e.testCaseId IN :testCaseIds
        AND tp.id = :testPlanId
      ORDER BY ti.startTime DESC
      """)
  List<TmsTestCaseExecution> findByTestCaseIdsAndTestPlanId(
      @Param("testCaseIds") List<Long> testCaseIds,
      @Param("testPlanId") Long testPlanId
  );

  @Query("""
      SELECT e
      FROM TmsTestCaseExecution e
      JOIN TestItem ti ON ti.itemId = e.testItem.itemId
      JOIN Launch l ON l.id = ti.launchId
      WHERE e.testCaseId IN :testCaseIds
      ORDER BY ti.startTime DESC
      """)
  List<TmsTestCaseExecution> findByTestCaseIds(
      @Param("testCaseIds") List<Long> testCaseIds
  );

  @Query("""
      SELECT e
      FROM TmsTestCaseExecution e
      JOIN TestItem ti ON ti.itemId = e.testItem.itemId
      JOIN Launch l ON l.id = ti.launchId
      JOIN TmsTestPlan tp ON tp.id = l.testPlanId
      WHERE e.testCaseId = :testCaseId
            AND tp.id = :testPlanId
      ORDER BY ti.startTime DESC
      """)
  List<TmsTestCaseExecution> findByTestCaseIdAndTestPlanId(
      @Param("testCaseId") Long testCaseId,
      @Param("testPlanId") Long testPlanId
  );

  @Query("""
      SELECT e
      FROM TmsTestCaseExecution e
      JOIN TestItem ti ON ti.itemId = e.testItem.itemId
      JOIN Launch l ON l.id = ti.launchId
      WHERE e.testCaseId = :testCaseId
      ORDER BY ti.startTime DESC
      """)
  List<TmsTestCaseExecution> findByTestCaseId(
      @Param("testCaseId") Long testCaseId
  );

  @Query(value = """
      SELECT DISTINCT ON (e.test_case_id) e.*
      FROM tms_test_case_execution e
      JOIN test_item ti ON ti.item_id = e.test_item_id
      JOIN launch l ON l.id = ti.launch_id
      JOIN tms_test_plan tp ON tp.id = l.test_plan_id
      WHERE e.test_case_id IN :testCaseIds
        AND tp.id = :testPlanId
      ORDER BY e.test_case_id, ti.start_time DESC
      """, nativeQuery = true)
  List<TmsTestCaseExecution> findLastExecutionsByTestCaseIdsAndTestPlanId(
      @Param("testCaseIds") List<Long> testCaseIds,
      @Param("testPlanId") Long testPlanId
  );

  @Query(value = """
      SELECT DISTINCT ON (e.test_case_id) e.*
      FROM tms_test_case_execution e
      JOIN test_item ti ON ti.item_id = e.test_item_id
      JOIN launch l ON l.id = ti.launch_id
      WHERE e.test_case_id IN :testCaseIds
      ORDER BY e.test_case_id, ti.start_time DESC
      """, nativeQuery = true)
  List<TmsTestCaseExecution> findLastExecutionsByTestCaseIds(
      @Param("testCaseIds") List<Long> testCaseIds
  );

  @Query(value = """
      SELECT e.*
      FROM tms_test_case_execution e
      JOIN test_item ti ON ti.item_id = e.test_item_id
      JOIN launch l ON l.id = ti.launch_id
      WHERE e.test_case_id = :testCaseId
      ORDER BY e.test_case_id, ti.start_time DESC
      LIMIT 1      
      """, nativeQuery = true)
  Optional<TmsTestCaseExecution> findLastExecutionByTestCaseId(
      @Param("testCaseId") Long testCaseId
  );

  @Query(value = """
      SELECT e.*
      FROM tms_test_case_execution e
      JOIN test_item ti ON ti.item_id = e.test_item_id
      JOIN launch l ON l.id = ti.launch_id
      JOIN tms_test_plan tp ON tp.id = l.test_plan_id
      WHERE e.test_case_id = :testCaseId
        AND tp.id = :testPlanId
      ORDER BY e.test_case_id, ti.start_time DESC
      LIMIT 1      
      """, nativeQuery = true)
  Optional<TmsTestCaseExecution> findLastExecutionByTestCaseIdAndTestPlanId(
      @Param("testCaseId") Long testCaseId,
      @Param("testPlanId") Long testPlanId
  );
}
