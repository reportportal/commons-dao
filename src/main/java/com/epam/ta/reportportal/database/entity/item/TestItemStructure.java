package com.epam.ta.reportportal.database.entity.item;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * @author Pavel Bortnik
 */
@Entity
@Table(name = "test_item_structure", schema = "public", indexes = {
		@Index(name = "test_item_structure_item_id_key", unique = true, columnList = "item_id ASC"),
		@Index(name = "test_item_structure_pk", unique = true, columnList = "id ASC") })
public class TestItemStructure implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false, precision = 64)
	private Long id;

	@Column(name = "item_id", unique = true, nullable = false, precision = 64)
	private Long itemId;

	@Column(name = "launch_id", nullable = false, precision = 64)
	private Long launchId;

	@Column(name = "parent_id", precision = 64)
	private Long parentId;

	@Column(name = "retry_of", precision = 64)
	private Long retryOf;

	@Column(name = "has_children")
	private Boolean hasChildren;

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

	public Long getLaunchId() {
		return launchId;
	}

	public void setLaunchId(Long launchId) {
		this.launchId = launchId;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Long getRetryOf() {
		return retryOf;
	}

	public void setRetryOf(Long retryOf) {
		this.retryOf = retryOf;
	}

	public Boolean getHasChildren() {
		return hasChildren;
	}

	public void setHasChildren(Boolean hasChildren) {
		this.hasChildren = hasChildren;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		TestItemStructure that = (TestItemStructure) o;
		return Objects.equals(id, that.id) && Objects.equals(itemId, that.itemId) && Objects.equals(launchId, that.launchId)
				&& Objects.equals(parentId, that.parentId) && Objects.equals(retryOf, that.retryOf) && Objects.equals(
				hasChildren, that.hasChildren);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, itemId, launchId, parentId, retryOf, hasChildren);
	}

	@Override
	public String toString() {
		return "TestItemStructure{" + "id=" + id + ", itemId=" + itemId + ", launchId=" + launchId + ", parentId=" + parentId + ", retryOf="
				+ retryOf + ", hasChildren=" + hasChildren + '}';
	}
}
