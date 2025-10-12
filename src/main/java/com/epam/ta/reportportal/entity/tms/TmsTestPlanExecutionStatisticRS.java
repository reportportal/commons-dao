package com.epam.ta.reportportal.entity.tms;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class TmsTestPlanExecutionStatisticRS {

  private Long covered;
  private Long total;

  public TmsTestPlanExecutionStatisticRS(Number total, Number covered) {
    this.total = total != null ? total.longValue() : 0L;
    this.covered = covered != null ? covered.longValue() : 0L;
  }
}
