package com.epam.ta.reportportal.entity.tms;

import com.epam.ta.reportportal.entity.launch.Launch;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity representing TMS Manual Launch Attribute.
 */
@Entity
@Table(name = "tms_manual_launch_attribute")
@Data
@NoArgsConstructor
@AllArgsConstructor
@IdClass(TmsManualLaunchAttribute.TmsManualLaunchAttributeId.class)
public class TmsManualLaunchAttribute implements Serializable {

  @Id
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "attribute_id", nullable = false)
  private TmsAttribute attribute;

  @Id
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "launch_id", nullable = false)
  private Launch launch;

  @Column(name = "value")
  private String value;

  /**
   * Composite primary key class for TmsManualLaunchAttribute.
   */
  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  public static class TmsManualLaunchAttributeId implements Serializable {
    private Long attribute;
    private Long launch;
  }
}
