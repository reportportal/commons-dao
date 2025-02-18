package com.epam.ta.reportportal.entity.tms;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TmsTestPlanAttributeId implements Serializable {
    
    @Column(name = "test_plan_id")
    private Long testPlanId;
    
    @Column(name = "attribute_id")
    private Long attributeId;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        TmsTestPlanAttributeId that = (TmsTestPlanAttributeId) o;
        return Objects.equals(testPlanId, that.testPlanId) && Objects.equals(attributeId, that.attributeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(testPlanId, attributeId);
    }
}
