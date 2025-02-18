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
public class TmsManualScenarioAttributeId implements Serializable {
    
    @Column(name = "manual_scenario_id")
    private Long manualScenarioId;
    
    @Column(name = "attribute_id")
    private Long attributeId;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        TmsManualScenarioAttributeId that = (TmsManualScenarioAttributeId) o;
        return Objects.equals(manualScenarioId, that.manualScenarioId) && Objects.equals(attributeId, that.attributeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(manualScenarioId, attributeId);
    }
}
