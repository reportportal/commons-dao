/*
 * Copyright 2016 EPAM Systems
 * 
 * 
 * This file is part of EPAM Report Portal.
 * https://github.com/reportportal/commons-dao
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

import com.sun.tools.javac.util.List;

import java.io.Serializable;

/**
 * DAO layer holder for table options in widget
 *
 * @author Aliaksei_Makayed
 */
public class SelectionOptions implements Serializable {

	private static final long serialVersionUID = 1L;

	List<Order> orders;

	private int pageNumber;

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		SelectionOptions that = (SelectionOptions) o;

		if (pageNumber != that.pageNumber) {
			return false;
		}
		return orders != null ? orders.equals(that.orders) : that.orders == null;
	}

	@Override
	public int hashCode() {
		int result = orders != null ? orders.hashCode() : 0;
		result = 31 * result + pageNumber;
		return result;
	}

	@Override
	public String toString() {
		return "SelectionOptions{" + "orders=" + orders + ", pageNumber=" + pageNumber + '}';
	}
}