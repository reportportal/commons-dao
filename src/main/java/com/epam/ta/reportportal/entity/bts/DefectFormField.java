/*
 * Copyright (C) 2018 EPAM Systems
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

package com.epam.ta.reportportal.entity.bts;

import com.google.common.collect.Sets;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * @author Pavel Bortnik
 */
@Entity
@Table(name = "defect_form_field", schema = "public")
public class DefectFormField implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "field_id")
	private String fieldId;

	@ManyToOne
	@JoinColumn(name = "bug_tracking_system_id", nullable = false)
	private BugTrackingSystem bugTrackingSystem;

	@Column(name = "type")
	private String type;

	@Column(name = "required")
	private boolean isRequired;

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "defect_form_field_value", joinColumns = @JoinColumn(name = "id"))
	@Column(name = "values")
	private Set<String> values;

	@OneToMany(mappedBy = "defectFormField", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
	private Set<DefectFieldAllowedValue> defectFieldAllowedValues = Sets.newHashSet();

	public DefectFormField() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFieldId() {
		return fieldId;
	}

	public void setFieldId(String fieldId) {
		this.fieldId = fieldId;
	}

	public BugTrackingSystem getBugTrackingSystem() {
		return bugTrackingSystem;
	}

	public void setBugTrackingSystem(BugTrackingSystem bugTrackingSystem) {
		this.bugTrackingSystem = bugTrackingSystem;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isRequired() {
		return isRequired;
	}

	public void setRequired(boolean required) {
		isRequired = required;
	}

	public Set<String> getValues() {
		return values;
	}

	public void setValues(Set<String> values) {
		this.values = values;
	}

	public Set<DefectFieldAllowedValue> getDefectFieldAllowedValues() {
		return defectFieldAllowedValues;
	}

	public void setDefectFieldAllowedValues(Set<DefectFieldAllowedValue> defectFieldAllowedValues) {
		this.defectFieldAllowedValues.clear();
		this.defectFieldAllowedValues.addAll(defectFieldAllowedValues);
		this.defectFieldAllowedValues.forEach(it -> it.setDefectFormField(this));
	}
}
