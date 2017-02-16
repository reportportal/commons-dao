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

package com.epam.ta.reportportal.database.search;

import com.epam.ta.reportportal.commons.Predicates;
import com.epam.ta.reportportal.commons.validation.BusinessRule;
import com.epam.ta.reportportal.commons.validation.Suppliers;
import com.epam.ta.reportportal.database.entity.LogLevel;
import com.epam.ta.reportportal.database.entity.Status;
import com.epam.ta.reportportal.database.entity.item.issue.TestItemIssueType;
import com.epam.ta.reportportal.exception.ReportPortalException;
import com.epam.ta.reportportal.ws.model.ErrorType;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.bson.types.ObjectId;

import java.util.Collection;
import java.util.Date;
import java.util.Objects;

/**
 * Holds mapping between request search criteria and DB engine search criteria. Should be used for
 * conversion request query parameters to DB engine query parameters.
 *
 * @author Andrei Varabyeu
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

	private boolean hasDynamicPart;

	public CriteriaHolder(String filterCriteria, String queryCriteria, Class<?> dataType, boolean reference, boolean hasDynamicPart) {
		this.filterCriteria = Preconditions.checkNotNull(filterCriteria, "Filter criteria should not be null");
		this.queryCriteria = Preconditions.checkNotNull(queryCriteria, "Filter criteria should not be null");
		this.dataType = Preconditions.checkNotNull(dataType, "Data type should not be null");
		this.reference = reference;
		this.hasDynamicPart = hasDynamicPart;
	}

	public CriteriaHolder(CriteriaHolder holder) {
		this.filterCriteria = holder.getFilterCriteria();
		this.queryCriteria = holder.getQueryCriteria();
		this.reference = holder.isReference();
		this.dataType = holder.getDataType();
		this.hasDynamicPart = holder.isHasDynamicPart();
	}

	public String getFilterCriteria() {
		return filterCriteria;
	}

	/**
	 * Deprecated in favor of {@link #getQueryCriteria(String)}
	 */
	@Deprecated
	public String getQueryCriteria() {
		return queryCriteria;
	}

	/**
	 * Returns query criteria. In case of field may contain dynamic part, builds query criteria based on provided front-end filter
	 *
	 * @param filter
	 * @return
	 */
	public String getQueryCriteria(String filter) {
		if (this.hasDynamicPart) {
			//criteria now has format: "{staticPart}{dynamicPart}". Trim the static part
			String dynamicPart = filter.substring(this.filterCriteria.length(), filter.length());
			return this.queryCriteria + dynamicPart.replace(CriteriaMap.SEARCH_CRITERIA_SEPARATOR, CriteriaMap.QUERY_CRITERIA_SEPARATOR);
		} else {
			return this.queryCriteria;
		}

	}

	public boolean isReference() {
		return reference;
	}

	public Class<?> getDataType() {
		return dataType;
	}

	public boolean isHasDynamicPart() {
		return hasDynamicPart;
	}

	public Object castValue(String oneValue) {
		return this.castValue(oneValue, ErrorType.INCORRECT_FILTER_PARAMETERS);
	}

	/**
	 * Casting provided criteriaHolder by specified {@link Class} for specified value.
	 * <p>
	 * NOTE:<br>
	 * errorType - error which should be thrown when unable cast value
	 *
	 * @param oneValue  Value to cast
	 * @param errorType ErrorType in case of error
	 * @return Casted value
	 */
	public Object castValue(String oneValue, ErrorType errorType) {
		Object castedValue;
		if (Number.class.isAssignableFrom(getDataType())) {
			/* Verify correct number */
			Long parsedLong = NumberUtils.toLong(oneValue, -1);
			BusinessRule.expect(parsedLong, FilterRules.numberIsPositive())
					.verify(errorType, Suppliers.formattedSupplier("Cannot convert '{}' to valid positive number", oneValue));
			castedValue = parsedLong;
		} else if (Date.class.isAssignableFrom(getDataType())) {
			/* Verify correct date */
			BusinessRule.expect(oneValue, FilterRules.dateInMillis())
					.verify(errorType, Suppliers.formattedSupplier("Cannot convert '{}' to valid date", oneValue));
			castedValue = new Date(Long.parseLong(oneValue));
		} else if (boolean.class.equals(getDataType()) || Boolean.class.isAssignableFrom(getDataType())) {
			castedValue = BooleanUtils.toBoolean(oneValue);
		} else if (LogLevel.class.isAssignableFrom(getDataType())) {
			castedValue = LogLevel.toLevel(oneValue);
			BusinessRule.expect(castedValue, Predicates.notNull())
					.verify(errorType, Suppliers.formattedSupplier("Cannot convert '{}' to valid 'LogLevel'", oneValue));
		} else if (Status.class.isAssignableFrom(getDataType())) {
			castedValue = Status.fromValue(oneValue).orElseThrow(() -> new ReportPortalException(errorType,
					Suppliers.formattedSupplier("Cannot convert '{}' to valid 'Status'", oneValue)));
		} else if (TestItemIssueType.class.isAssignableFrom(getDataType())) {
			castedValue = TestItemIssueType.validate(oneValue);
			BusinessRule.expect(castedValue, Predicates.notNull())
					.verify(errorType, Suppliers.formattedSupplier("Cannot convert '{}' to valid 'Issue Type'", oneValue));
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
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		CriteriaHolder that = (CriteriaHolder) o;
		return reference == that.reference && hasDynamicPart == that.hasDynamicPart && Objects.equals(filterCriteria, that.filterCriteria)
				&& Objects.equals(queryCriteria, that.queryCriteria) && Objects.equals(dataType, that.dataType);
	}

	@Override
	public int hashCode() {
		return Objects.hash(filterCriteria, queryCriteria, dataType, reference, hasDynamicPart);
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this).add("filterCriteria", filterCriteria).add("queryCriteria", queryCriteria)
				.add("dataType", dataType).add("reference", reference).add("hasDynamicPart", hasDynamicPart).toString();
	}
}