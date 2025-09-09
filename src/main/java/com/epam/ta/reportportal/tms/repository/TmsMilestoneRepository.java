package com.epam.ta.reportportal.tms.repository;

import com.epam.ta.reportportal.dao.ReportPortalRepository;
import com.epam.ta.reportportal.tms.entity.TmsMilestone;
import com.epam.ta.reportportal.tms.entity.TmsTestPlan;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TmsMilestoneRepository extends ReportPortalRepository<TmsMilestone, Long> {

  @Modifying
  @Query("UPDATE TmsMilestone m SET m.testPlan = :testPlan WHERE m.id = :milestoneId")
  void attachTestPlanToMilestone(@Param("testPlan") TmsTestPlan testPlan,
      @Param("milestoneId") Long milestoneId);

  @Modifying
  @Query("UPDATE TmsMilestone m SET m.testPlan = null WHERE m.testPlan.id = :testPlanId")
  void detachTestPlanFromMilestones(@Param("testPlanId") Long testPlanId);
}
