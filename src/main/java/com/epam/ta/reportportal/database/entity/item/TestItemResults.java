package com.epam.ta.reportportal.database.entity.item;

import com.epam.ta.reportportal.database.entity.enums.StatusEnum;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * @author Pavel Bortnik
 */
@Entity
@Table(name = "test_item_results", schema = "public", indexes = {
		@Index(name = "test_item_results_item_id_key", unique = true, columnList = "item_id ASC"),
		@Index(name = "test_item_results_pk", unique = true, columnList = "id ASC") })
public class TestItemResults implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false, precision = 64)
	private Long id;

	@Column(name = "item_id", unique = true, nullable = false, precision = 64)
	private Long itemId;

	@Column(name = "status", nullable = false)
	private StatusEnum status;

	@Column(name = "duration", precision = 24)
	private Float duration;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public StatusEnum getStatus() {
		return status;
	}

	public void setStatus(StatusEnum status) {
		this.status = status;
	}

	public Float getDuration() {
		return duration;
	}

	public void setDuration(Float duration) {
		this.duration = duration;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		TestItemResults that = (TestItemResults) o;
		return Objects.equals(id, that.id) && Objects.equals(itemId, that.itemId) && status == that.status && Objects.equals(
				duration, that.duration);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, itemId, status, duration);
	}

	@Override
	public String toString() {
		return "TestItemResults{" + "id=" + id + ", itemId=" + itemId + ", status=" + status + ", duration=" + duration + '}';
	}
}
