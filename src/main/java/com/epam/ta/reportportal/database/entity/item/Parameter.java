package com.epam.ta.reportportal.database.entity.item;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author Pavel Bortnik
 */
public class Parameter implements Serializable {

	private String key;

	private String value;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Parameter parameter = (Parameter) o;
		return Objects.equals(key, parameter.key) && Objects.equals(value, parameter.value);
	}

	@Override
	public int hashCode() {
		return Objects.hash(key, value);
	}

	@Override
	public String toString() {
		return "Parameter{" + "key='" + key + '\'' + ", value='" + value + '\'' + '}';
	}
}
