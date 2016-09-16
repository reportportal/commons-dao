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

package com.epam.ta.reportportal.database.search;

import java.util.Collection;
import java.util.Date;

import org.apache.commons.lang3.BooleanUtils;
import org.bson.types.ObjectId;

import com.epam.ta.reportportal.commons.Predicates;
import com.epam.ta.reportportal.commons.validation.BusinessRule;
import com.epam.ta.reportportal.commons.validation.Suppliers;
import com.epam.ta.reportportal.database.entity.LogLevel;
import com.epam.ta.reportportal.database.entity.Status;
import com.epam.ta.reportportal.database.entity.item.issue.TestItemIssueType;
import com.epam.ta.reportportal.exception.ReportPortalException;
import com.epam.ta.reportportal.ws.model.ErrorType;
import com.google.common.base.Preconditions;

/**
 * Holds mapping between request search criterias and DB engine search criterias. Should be used for
 * conversion request query parameters to DB engine query parameters.
 * 
 * @author Andrei Varabyeu
 * 
 */
public class CriteriaHolder {

	// added for deserialization from DB
	public CriteriaHolder() {

	}

	/**
	 * Criteria from search string
	 */
	private String filterCriteria;

	/**
	 * Internal Criteria to internal search be performed
	 */
	private String queryCriteria;

	private Class<?> dataType;

	/**
	 * Is criteria links to the related item
	 */
	private boolean reference;

	public CriteriaHolder(String filterCriteria, String queryCriteria, Class<?> dataType, boolean reference) {
		this.filterCriteria = Preconditions.checkNotNull(filterCriteria, "Filter criteria should not be null");
		this.queryCriteria = Preconditions.checkNotNull(queryCriteria, "Filter criteria should not be null");
		this.dataType = Preconditions.checkNotNull(dataType, "Data type should not be null");
		this.reference = reference;
	}

	public CriteriaHolder(CriteriaHolder holder) {
		this.filterCriteria = holder.getFilterCriteria();
		this.queryCriteria = holder.getQueryCriteria();
		this.reference = holder.isReference();
		this.dataType = holder.getDataType();
	}

	public String getFilterCriteria() {
		return filterCriteria;
	}

	public String getQueryCriteria() {
		return queryCriteria;
	}

	public boolean isReference() {
		return reference;
	}

	public Class<?> getDataType() {
		return dataType;
	}

	public Object castValue(String oneValue) {
		return this.castValue(oneValue, ErrorType.INCORRECT_FILTER_PARAMETERS);
	}

	/**
	 * Casting provided criteriaHolder by specified Class<?> for specified value.
	 * 
	 * NOTE:<br>
	 * errorType - error which should be thrown when unable cast value
	 * 
	 * @param oneValue
	 * @param errorType
	 * @return
	 */
	public Object castValue(String oneValue, ErrorType errorType) {
		Object castedValue;
		if (Number.class.isAssignableFrom(getDataType())) {
			/* Verify correct number */
			BusinessRule.expect(oneValue, FilterRules.number()).verify(errorType,
					Suppliers.formattedSupplier("Cannot convert '{}' to valid number", oneValue));
			Long parsedLong = Long.parseLong(oneValue);
			BusinessRule.expect(parsedLong, FilterRules.numberIsPositive()).verify(errorType,
					Suppliers.formattedSupplier("Numeric values shouldn't be empty.'{}' is not acceptable, parameters", oneValue));
			castedValue = parsedLong;
		} else if (Date.class.isAssignableFrom(getDataType())) {
			/* Verify correct date */
			BusinessRule.expect(oneValue, FilterRules.dateInMillis()).verify(errorType,
					Suppliers.formattedSupplier("Cannot convert '{}' to valid date", oneValue));
			castedValue = new Date(Long.parseLong(oneValue));
		} else if (boolean.class.equals(getDataType()) || Boolean.class.isAssignableFrom(getDataType())) {
			castedValue = BooleanUtils.toBoolean(oneValue);
		} else if (LogLevel.class.isAssignableFrom(getDataType())) {
			castedValue = LogLevel.toLevel(oneValue);
			BusinessRule.expect(castedValue, Predicates.notNull()).verify(errorType,
					Suppliers.formattedSupplier("Cannot convert '{}' to valid 'LogLevel'", oneValue));
		} else if (Status.class.isAssignableFrom(getDataType())) {
			castedValue = Status.fromValue(oneValue).orElseThrow(() -> new ReportPortalException(errorType,
					Suppliers.formattedSupplier("Cannot convert '{}' to valid 'Status'", oneValue)));
		} else if (TestItemIssueType.class.isAssignableFrom(getDataType())) {
			castedValue = TestItemIssueType.validate(oneValue);
			BusinessRule.expect(castedValue, Predicates.notNull()).verify(errorType,
					Suppliers.formattedSupplier("Cannot convert '{}' to valid 'Issue Type'", oneValue));
		} else if (Collection.class.isAssignableFrom(getDataType())) {
			/* Collection doesn't stores objects as ObjectId */
			castedValue = oneValue;
		} else if (String.class.isAssignableFrom(getDataType())) {
			castedValue = oneValue != null ? oneValue.trim() : null;
		} else {
			castedValue = ObjectId.isValid(oneValue) ? new ObjectId(oneValue) : oneValue;
		}
		return castedValue;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((queryCriteria == null) ? 0 : queryCriteria.hashCode());
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
		CriteriaHolder other = (CriteriaHolder) obj;
		if (queryCriteria == null) {
			if (other.queryCriteria != null)
				return false;
		} else if (!queryCriteria.equals(other.queryCriteria))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CriteriaHolder [filterCriteria = " + filterCriteria + ", queryCriteria = " + queryCriteria + ", reference = " + reference
				+ "]";
	}
}