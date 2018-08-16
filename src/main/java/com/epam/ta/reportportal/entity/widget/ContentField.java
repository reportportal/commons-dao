package com.epam.ta.reportportal.entity.widget;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

/**
 * @author Ivan Budayeu
 */
@Entity
@Table(name = "content_field")
public class ContentField implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "widget_id")
	private Long widgetId;

	@Column(name = "field", nullable = false)
	private String fieldName;

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "content_field_value", joinColumns = @JoinColumn(name = "id"))
	@Column(name = "value")
	private Set<String> values;

	public ContentField() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getWidgetId() {
		return widgetId;
	}

	public void setWidgetId(Long widgetId) {
		this.widgetId = widgetId;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public Set<String> getValues() {
		return values;
	}

	public void setValues(Set<String> values) {
		this.values = values;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		ContentField that = (ContentField) o;
		return Objects.equals(id, that.id) && Objects.equals(widgetId, that.widgetId) && Objects.equals(fieldName, that.fieldName)
				&& Objects.equals(values, that.values);
	}

	@Override
	public int hashCode() {

		return Objects.hash(id, widgetId, fieldName, values);
	}
}
