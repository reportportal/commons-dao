package com.epam.ta.reportportal.database.entity.filter;

import java.io.Serializable;

/**
 * @author Pavel Bortnik
 */
public class SelectionOrder implements Serializable {

	private String sortingColumnName;

	private boolean isAsc;

	public SelectionOrder() {
	}

	public SelectionOrder(String sortingColumnName, boolean isAsc) {
		this.sortingColumnName = sortingColumnName;
		this.isAsc = isAsc;
	}

	public String getSortingColumnName() {
		return sortingColumnName;
	}

	public void setSortingColumnName(String sortingColumnName) {
		this.sortingColumnName = sortingColumnName;
	}

	public boolean isAsc() {
		return isAsc;
	}

	public void setIsAsc(boolean isAsc) {
		this.isAsc = isAsc;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		SelectionOrder order = (SelectionOrder) o;

		if (isAsc != order.isAsc) {
			return false;
		}
		return sortingColumnName != null ? sortingColumnName.equals(order.sortingColumnName) : order.sortingColumnName == null;
	}

	@Override
	public int hashCode() {
		int result = sortingColumnName != null ? sortingColumnName.hashCode() : 0;
		result = 31 * result + (isAsc ? 1 : 0);
		return result;
	}

	@Override
	public String toString() {
		return "Order{" + "sortingColumnName='" + sortingColumnName + '\'' + ", isAsc=" + isAsc + '}';
	}
}
