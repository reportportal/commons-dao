/*
 * Copyright 2016 EPAM Systems
 * 
 * 
 * This file is part of EPAM Report Portal.
 * https://github.com/epam/ReportPortal
 * 
 * Report Portal is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Report Portal is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Report Portal.  If not, see <http://www.gnu.org/licenses/>.
 */ 
 
package com.epam.ta.reportportal.database.entity.filter;

import java.io.Serializable;

/**
 * DAO layer holder for table options in widget
 * 
 * @author Aliaksei_Makayed
 * 
 */
public class SelectionOptions implements Serializable {

	private static final long serialVersionUID = 1L;

	private String sortingColumnName;

	private boolean isAsc;

	private int quantity;

	private int pageNumber;

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

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (isAsc ? 1231 : 1237);
		result = prime * result + pageNumber;
		result = prime * result + quantity;
		result = prime * result + ((sortingColumnName == null) ? 0 : sortingColumnName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SelectionOptions other = (SelectionOptions) obj;
		if (isAsc != other.isAsc)
			return false;
		if (pageNumber != other.pageNumber)
			return false;
		if (quantity != other.quantity)
			return false;
		if (sortingColumnName == null) {
			if (other.sortingColumnName != null)
				return false;
		} else if (!sortingColumnName.equals(other.sortingColumnName))
			return false;
		return true;
	}
}