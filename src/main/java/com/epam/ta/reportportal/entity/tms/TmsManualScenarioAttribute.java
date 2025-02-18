package com.epam.ta.reportportal.entity.tms;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "tms_manual_scenario_attribute")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TmsManualScenarioAttribute implements Serializable {
    
    @EmbeddedId
    private TmsManualScenarioAttributeId id;
    
    @ManyToOne
    @MapsId(value = "manualScenarioId")
    @JoinColumn(name = "manual_scenario_id")
    private TmsManualScenario manualScenario;
    
    @ManyToOne
    @MapsId(value = "attributeId")
    @JoinColumn(name = "attribute_id")
    private TmsAttribute attribute;
    
    @Column(name = "value")
    private String value;
    
    //TODO: override equals and hashCode methods
}
