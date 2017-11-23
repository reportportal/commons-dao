package com.epam.ta.reportportal.database.entity.filter;

/**
 * @author Pavel Bortnik
 */
public class SelectionOrder {

	private String sortingColumnName;

	private boolean isAsc;

	public String getSortingColumnName() {
		return sortingColumnName;
	}

	public void setSortingColumnName(String sortingColumnName) {
		this.sortingColumnName = sortingColumnName;
	}

	public boolean isAsc() {
		return isAsc;
	}

	public void setAsc(boolean asc) {
		isAsc = asc;
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
