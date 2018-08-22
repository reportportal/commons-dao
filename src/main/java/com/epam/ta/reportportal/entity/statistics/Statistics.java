package com.epam.ta.reportportal.entity.statistics;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * @author Ivan Budayeu
 */
@Entity
@Table(name = "statistics")
public class Statistics implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "s_id")
	private Long id;

	@Column(name = "s_field")
	private String field;

	@Column(name = "s_counter")
	private Integer counter;

	public Statistics() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public Integer getCounter() {
		return counter;
	}

	public void setCounter(Integer counter) {
		this.counter = counter;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Statistics that = (Statistics) o;
		return Objects.equals(id, that.id) && Objects.equals(field, that.field) && Objects.equals(counter, that.counter);
	}

	@Override
	public int hashCode() {

		return Objects.hash(id, field, counter);
	}
}
