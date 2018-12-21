/*
 * Copyright 2018 EPAM Systems
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.epam.ta.reportportal.commons.querygen;

import com.epam.ta.reportportal.commons.validation.BusinessRule;
import com.epam.ta.reportportal.commons.validation.Suppliers;
import com.epam.ta.reportportal.entity.activity.Activity;
import com.epam.ta.reportportal.entity.enums.*;
import com.epam.ta.reportportal.jooq.enums.*;
import com.epam.ta.reportportal.ws.model.ErrorType;
import com.google.common.base.Preconditions;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.jooq.impl.DSL;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collection;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;

/**
 * Holds mapping between request search criteria and DB engine search criteria. Should be used for
 * conversion request query parameters to DB engine query parameters.
 *
 * @author Andrei Varabyeu
 */
public class CriteriaHolder {

	public CriteriaHolder() {
		// added for deserialization from DB
	}

	/**
	 * Criteria from search string
	 */
	private String filterCriteria;

	/**
	 * Internal Criteria to internal search be performed
	 */
	private String queryCriteria;

	/**
	 * Aggregate criteria for filtering. Only for fields that should be aggregated.
	 * If not - used default queryCriteria
	 */
	private String aggregateCriteria;

	private Class<?> dataType;

	public CriteriaHolder(String filterCriteria, String queryCriteria, String aggregateCriteria, Class<?> dataType) {
		this.filterCriteria = filterCriteria;
		this.queryCriteria = queryCriteria;
		this.aggregateCriteria = aggregateCriteria;
		this.dataType = dataType;
	}

	public CriteriaHolder(String filterCriteria, String queryCriteria, Class<?> dataType) {
		this.filterCriteria = Preconditions.checkNotNull(filterCriteria, "Filter criteria should not be null");
		this.queryCriteria = Preconditions.checkNotNull(queryCriteria, "Filter criteria should not be null");
		this.aggregateCriteria = queryCriteria;
		this.dataType = Preconditions.checkNotNull(dataType, "Data type should not be null");
	}

	public CriteriaHolder(CriteriaHolder holder) {
		this.filterCriteria = holder.getFilterCriteria();
		this.queryCriteria = holder.getQueryCriteria();
		this.dataType = holder.getDataType();
	}

	public String getFilterCriteria() {
		return filterCriteria;
	}

	public String getQueryCriteria() {
		return queryCriteria;
	}

	public String getAggregateCriteria() {
		return aggregateCriteria;
	}

	public Class<?> getDataType() {
		return dataType;
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
			castedValue = LocalDateTime.ofInstant(Instant.ofEpochMilli(Long.parseLong(oneValue)), ZoneId.systemDefault());
		} else if (boolean.class.equals(getDataType()) || Boolean.class.isAssignableFrom(getDataType())) {
			castedValue = BooleanUtils.toBoolean(oneValue);
		} else if (LogLevel.class.isAssignableFrom(getDataType())) {
			Optional<LogLevel> level = LogLevel.toLevel(oneValue);
			BusinessRule.expect(level, Optional::isPresent)
					.verify(errorType, Suppliers.formattedSupplier("Cannot convert '{}' to valid 'LogLevel'", oneValue));
			castedValue = level.get().toInt();
			BusinessRule.expect(castedValue, Objects::nonNull)
					.verify(errorType, Suppliers.formattedSupplier("Cannot convert '{}' to valid 'LogLevel'", oneValue));
		} else if (JStatusEnum.class.isAssignableFrom(getDataType())) {

			Optional<StatusEnum> status = StatusEnum.fromValue(oneValue);
			BusinessRule.expect(status, Optional::isPresent)
					.verify(errorType, Suppliers.formattedSupplier("Cannot convert '{}' to valid 'Status'", oneValue));
			castedValue = JStatusEnum.valueOf(status.get().name());

		} else if (JTestItemTypeEnum.class.isAssignableFrom(getDataType())) {

			Optional<TestItemTypeEnum> itemType = TestItemTypeEnum.fromValue(oneValue);
			BusinessRule.expect(itemType, Optional::isPresent)
					.verify(errorType, Suppliers.formattedSupplier("Cannot convert '{}' to valid 'Test item type'", oneValue));
			castedValue = JTestItemTypeEnum.valueOf(itemType.get().name());

		} else if (JLaunchModeEnum.class.isAssignableFrom(getDataType())) {

			Optional<LaunchModeEnum> launchMode = LaunchModeEnum.findByName(oneValue);
			BusinessRule.expect(launchMode, Optional::isPresent)
					.verify(errorType, Suppliers.formattedSupplier("Cannot convert '{}' to valid 'Launch mode'", oneValue));
			castedValue = JLaunchModeEnum.valueOf(launchMode.get().name());

		} else if (JActivityEntityEnum.class.isAssignableFrom(getDataType())) {

			Optional<Activity.ActivityEntityType> activityEntityType = Activity.ActivityEntityType.fromString(oneValue);
			BusinessRule.expect(activityEntityType, Optional::isPresent)
					.verify(errorType, Suppliers.formattedSupplier("Cannot convert '{}' to valid 'Activity entity'", oneValue));
			castedValue = JActivityEntityEnum.valueOf(activityEntityType.get().name());

		} else if (JIntegrationGroupEnum.class.isAssignableFrom(getDataType())) {

			Optional<IntegrationGroupEnum> integrationGroup = IntegrationGroupEnum.findByName(oneValue);
			BusinessRule.expect(integrationGroup, Optional::isPresent)
					.verify(errorType, Suppliers.formattedSupplier("Cannot convert '{}' to valid 'Integration group", oneValue));
			castedValue = JIntegrationGroupEnum.valueOf(integrationGroup.get().name());

		} else if (TestItemIssueGroup.class.isAssignableFrom(getDataType())) {
			castedValue = TestItemIssueGroup.validate(oneValue);
			BusinessRule.expect(castedValue, Objects::nonNull)
					.verify(errorType, Suppliers.formattedSupplier("Cannot convert '{}' to valid 'Issue Type'", oneValue));
		} else if (Collection.class.isAssignableFrom(getDataType())) {
			/* Collection doesn't stores objects as ObjectId */
			castedValue = oneValue;
		} else if (String.class.isAssignableFrom(getDataType())) {
			castedValue = oneValue != null ? oneValue.trim() : null;
		} else {
			castedValue = DSL.val(oneValue).cast(getDataType());
		}
		return castedValue;
	}

}