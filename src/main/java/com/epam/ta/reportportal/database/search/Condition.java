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

import com.epam.ta.reportportal.ws.model.ErrorType;
import org.apache.commons.lang3.BooleanUtils;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.query.Criteria;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Optional;
import java.util.regex.Pattern;

import static com.epam.ta.reportportal.commons.Predicates.equalTo;
import static com.epam.ta.reportportal.commons.Predicates.or;
import static com.epam.ta.reportportal.commons.validation.BusinessRule.expect;
import static com.epam.ta.reportportal.commons.validation.BusinessRule.fail;
import static com.epam.ta.reportportal.commons.validation.Suppliers.formattedSupplier;
import static com.epam.ta.reportportal.database.search.FilterRules.*;
import static com.epam.ta.reportportal.ws.model.ErrorType.INCORRECT_FILTER_PARAMETERS;
import static java.lang.Long.parseLong;
import static java.util.Date.from;

/**
 * Types of supported filtering
 *
 * @author Andrei Varabyeu
 */
public enum Condition {

	/**
	 * EQUALS condition
	 */
	EQUALS("eq") {
		@Override
		public void addCondition(Criteria criteria, FilterCondition filter, CriteriaHolder criteriaHolder) {
			this.validate(criteriaHolder, filter.getValue(), filter.isNegative(), INCORRECT_FILTER_PARAMETERS);
			criteria.is(this.castValue(criteriaHolder, filter.getValue(), INCORRECT_FILTER_PARAMETERS));
		}

		@Override
		public void validate(CriteriaHolder criteriaHolder, String value, boolean isNegative, ErrorType errorType) {
			expect(isNegative, equalTo(false)).verify(errorType, "Filter is incorrect. '!' can't be used with 'is' - use 'ne' instead");
		}

		@Override
		public Object castValue(CriteriaHolder criteriaHolder, String value, ErrorType errorType) {
			return criteriaHolder.castValue(value, errorType);
		}
	},

	/**
	 * Not equals condition
	 */
	NOT_EQUALS("ne") {
		@Override
		public void addCondition(Criteria criteria, FilterCondition filter, CriteriaHolder criteriaHolder) {
			criteria.ne(this.castValue(criteriaHolder, filter.getValue(), INCORRECT_FILTER_PARAMETERS));
		}

		@Override
		public void validate(CriteriaHolder criteriaHolder, String value, boolean isNegative, ErrorType errorType) {
			// object type validations is not required here
		}

		@Override
		public Object castValue(CriteriaHolder criteriaHolder, String value, ErrorType errorType) {
			return criteriaHolder.castValue(value, errorType);
		}
	},

	/**
	 * Contains operation. NON case sensitive
	 */
	CONTAINS("cnt") {
		@Override
		public void addCondition(Criteria criteria, FilterCondition filter, CriteriaHolder criteriaHolder) {
			/* Validate only strings */
			this.validate(criteriaHolder, filter.getValue(), filter.isNegative(), INCORRECT_FILTER_PARAMETERS);

			if (!ObjectId.isValid(filter.getValue())) {
				criteria.regex("(?i).*" + Pattern.quote(filter.getValue()) + ".*");
			} else {
				criteria.regex(".*" + filter.getValue() + ".*");
			}
		}

		@Override
		public void validate(CriteriaHolder criteriaHolder, String value, boolean isNegative, ErrorType errorType) {
			expect(criteriaHolder, filterForString()).verify(errorType,
					formattedSupplier("Contains condition applyable only for strings. Type of field is '{}'",
							criteriaHolder.getDataType().getSimpleName()));
		}

		@Override
		public Object castValue(CriteriaHolder criteriaHolder, String values, ErrorType errorType) {
			// values cast is not required here
			return values;
		}
	},

	/**
	 * Size operation
	 */
	SIZE("size") {
		@Override
		public void addCondition(Criteria criteria, FilterCondition filter, CriteriaHolder criteriaHolder) {
			/* Validate only numbers */
			this.validate(criteriaHolder, filter.getValue(), filter.isNegative(), INCORRECT_FILTER_PARAMETERS);
			criteria.size(Integer.parseInt(filter.getValue()));
		}

		@Override
		public void validate(CriteriaHolder criteriaHolder, String value, boolean isNegative, ErrorType errorType) {
			expect(criteriaHolder, filterForCollections()).verify(errorType,
					formattedSupplier("'Size' condition applyable only for collection data types. Type of field is '{}'",
							criteriaHolder.getDataType().getSimpleName()));
			expect(value, number()).verify(errorType, formattedSupplier("Provided value '{}' is not a number", value));
		}

		@Override
		public Object castValue(CriteriaHolder criteriaHolder, String values, ErrorType errorType) {
			// values cast is not required here
			return values;
		}
	},

	/**
	 * Exists condition
	 */
	EXISTS("ex") {
		@Override
		public void addCondition(Criteria criteria, FilterCondition filter, CriteriaHolder criteriaHolder) {
			criteria.exists(BooleanUtils.toBoolean(filter.getValue()));
		}

		@Override
		public void validate(CriteriaHolder criteriaHolder, String value, boolean isNegative, ErrorType errorType) {
			// object type validations is not required here
		}

		@Override
		public Object castValue(CriteriaHolder criteriaHolder, String values, ErrorType errorType) {
			// values cast is not required here
			return values;
		}
	},

	/**
	 * IN condition. Accepts filter value as comma-separated list
	 */
	IN("in") {
		@Override
		public void addCondition(Criteria criteria, FilterCondition filter, CriteriaHolder criteriaHolder) {
			criteria.in((Object[]) this.castValue(criteriaHolder, filter.getValue(), INCORRECT_FILTER_PARAMETERS));
		}

		@Override
		public void validate(CriteriaHolder criteriaHolder, String value, boolean isNegative, ErrorType errorType) {
			// object type validations is not required here
		}

		@Override
		public Object castValue(CriteriaHolder criteriaHolder, String value, ErrorType errorType) {
			return castArray(criteriaHolder, value, errorType);
		}
	},

	/**
	 * HAS condition. Accepts filter value as comma-separated list. Returns
	 * 'TRUE' of all provided values exist in collection<br>
	 * <b>Applicable only for collections</b>
	 */
	HAS("has") {
		@Override
		public void addCondition(Criteria criteria, FilterCondition filter, CriteriaHolder criteriaHolder) {
			/* Validate only collections */
			this.validate(criteriaHolder, filter.getValue(), filter.isNegative(), INCORRECT_FILTER_PARAMETERS);
			criteria.all((Object[]) this.castValue(criteriaHolder, filter.getValue(), INCORRECT_FILTER_PARAMETERS));
		}

		@Override
		public void validate(CriteriaHolder criteriaHolder, String value, boolean isNegative, ErrorType errorType) {
			expect(criteriaHolder, filterForCollections()).verify(errorType,
					formattedSupplier("'HAS' condition applyable only for collection data types. Type of field is '{}'",
							criteriaHolder.getDataType().getSimpleName()));
		}

		@Override
		public Object castValue(CriteriaHolder criteriaHolder, String value, ErrorType errorType) {
			return castArray(criteriaHolder, value, errorType);
		}
	},

	/**
	 * Greater than condition
	 */
	GREATER_THAN("gt") {
		@Override
		public void addCondition(Criteria criteria, FilterCondition filter, CriteriaHolder criteriaHolder) {
			/* Validate only numbers & dates */
			this.validate(criteriaHolder, filter.getValue(), filter.isNegative(), INCORRECT_FILTER_PARAMETERS);
			criteria.gt(this.castValue(criteriaHolder, filter.getValue(), INCORRECT_FILTER_PARAMETERS));
		}

		@Override
		@SuppressWarnings("unchecked")
		public void validate(CriteriaHolder criteriaHolder, String value, boolean isNegative, ErrorType errorType) {
			expect(criteriaHolder, or(filterForNumbers(), filterForDates(), filterForLogLevel())).verify(errorType,
					formattedSupplier("'Greater than' condition applyable only for positive Numbers or Dates. Type of field is '{}'",
							criteriaHolder.getDataType().getSimpleName()));
		}

		@Override
		public Object castValue(CriteriaHolder criteriaHolder, String value, ErrorType errorType) {
			return criteriaHolder.castValue(value, errorType);
		}
	},

	/**
	 * Greater than or Equals condition
	 */
	GREATER_THAN_OR_EQUALS("gte") {
		@Override
		public void addCondition(Criteria criteria, FilterCondition filter, CriteriaHolder criteriaHolder) {
			/* Validate only numbers & dates */
			this.validate(criteriaHolder, filter.getValue(), filter.isNegative(), INCORRECT_FILTER_PARAMETERS);
			criteria.gte(this.castValue(criteriaHolder, filter.getValue(), INCORRECT_FILTER_PARAMETERS));
		}

		@Override
		@SuppressWarnings("unchecked")
		public void validate(CriteriaHolder criteriaHolder, String value, boolean isNegative, ErrorType errorType) {
			expect(criteriaHolder, or(filterForNumbers(), filterForDates(), filterForLogLevel())).verify(errorType, formattedSupplier(
					"'Greater than or equals' condition applyable only for positive Numbers or Dates. Type of field is '{}'",
					criteriaHolder.getDataType().getSimpleName()));
		}

		@Override
		public Object castValue(CriteriaHolder criteriaHolder, String value, ErrorType errorType) {
			return criteriaHolder.castValue(value, errorType);
		}
	},

	/**
	 * Lower than condition
	 */
	LOWER_THAN("lt") {
		@Override
		public void addCondition(Criteria criteria, FilterCondition filter, CriteriaHolder criteriaHolder) {
			/* Validate only numbers & dates */
			this.validate(criteriaHolder, filter.getValue(), filter.isNegative(), INCORRECT_FILTER_PARAMETERS);

			Object valueToFind = ObjectId.isValid(filter.getValue()) ?
					new ObjectId(filter.getValue()) :
					this.castValue(criteriaHolder, filter.getValue(), INCORRECT_FILTER_PARAMETERS);
			criteria.lt(valueToFind);
		}

		@Override
		@SuppressWarnings("unchecked")
		public void validate(CriteriaHolder criteriaHolder, String value, boolean isNegative, ErrorType errorType) {
			expect(criteriaHolder, or(filterForNumbers(), filterForDates(), filterForLogLevel())).verify(errorType,
					formattedSupplier("'Lower than' condition applyable only for positive Numbers or Dates. Type of field is '{}'",
							criteriaHolder.getDataType().getSimpleName()));
		}

		@Override
		public Object castValue(CriteriaHolder criteriaHolder, String value, ErrorType errorType) {
			return criteriaHolder.castValue(value, errorType);
		}
	},

	/**
	 * Lower than or Equals condition
	 */
	LOWER_THAN_OR_EQUALS("lte") {
		@Override
		public void addCondition(Criteria criteria, FilterCondition filter, CriteriaHolder criteriaHolder) {
			/* Validate only numbers & dates */
			this.validate(criteriaHolder, filter.getValue(), filter.isNegative(), INCORRECT_FILTER_PARAMETERS);

			Object valueToFind = ObjectId.isValid(filter.getValue()) ?
					new ObjectId(filter.getValue()) :
					this.castValue(criteriaHolder, filter.getValue(), INCORRECT_FILTER_PARAMETERS);
			criteria.lte(valueToFind);
		}

		@Override
		@SuppressWarnings("unchecked")
		public void validate(CriteriaHolder criteriaHolder, String value, boolean isNegative, ErrorType errorType) {
			expect(criteriaHolder, or(filterForNumbers(), filterForDates(), filterForLogLevel())).verify(errorType, formattedSupplier(
					"'Lower than or equals' condition applyable only for positive Numbers or Dates. Type of field is '{}'",
					criteriaHolder.getDataType().getSimpleName()));
		}

		@Override
		public Object castValue(CriteriaHolder criteriaHolder, String value, ErrorType errorType) {
			return criteriaHolder.castValue(value, errorType);
		}
	},

	/**
	 * Between condition. Include boundaries
	 */
	BETWEEN("btw") {
		@Override
		@SuppressWarnings("unchecked")
		public void validate(CriteriaHolder criteriaHolder, String value, boolean isNegative, ErrorType errorType) {
			expect(criteriaHolder, or(filterForNumbers(), filterForDates(), filterForLogLevel())).verify(errorType, formattedSupplier(
					"'Between' condition applyable only for positive Numbers, Dates or specific TimeStamp values. "
							+ "Type of field is '{}'", criteriaHolder.getDataType().getSimpleName()));
			if (value.contains(VALUES_SEPARATOR))
				expect(value.split(VALUES_SEPARATOR), countOfValues(2)).verify(errorType,
						formattedSupplier("Incorrect between filter format. Expected='value1,value2'. Provided filter is '{}'", value));
			else if (value.contains(TIMESTAMP_SEPARATOR)) {
				final String[] values = value.split(TIMESTAMP_SEPARATOR);
				expect(values, countOfValues(3)).verify(errorType, formattedSupplier(
						"Incorrect between filter format. Expected='TIMESTAMP_CONSTANT;TimeZoneOffset'. Provided filter is '{}'", value));
				expect(values[2], zoneOffset()).verify(errorType,
						formattedSupplier("Incorrect zoneOffset. Expected='+h, +hh, +hh:mm'. Provided value is '{}'", values[2]));
				expect(values[0], timeStamp())
						.verify(errorType, formattedSupplier("Incorrect timestamp. Expected number. Provided value is '{}'", values[0]));
				expect(values[1], timeStamp())
						.verify(errorType, formattedSupplier("Incorrect timestamp. Expected number. Provided value is '{}'", values[1]));
			} else {
				fail().withError(errorType, formattedSupplier(
						"Incorrect between filter format. Filter value should be separated by ',' or ';'. Provided filter is '{}'", value));
			}
		}

		@Override
		public void addCondition(Criteria criteria, FilterCondition filter, CriteriaHolder criteriaHolder) {
			this.validate(criteriaHolder, filter.getValue(), filter.isNegative(), INCORRECT_FILTER_PARAMETERS);
			Object[] castedValues;
			if (filter.getValue().contains(",")) {
				castedValues = (Object[]) this.castValue(criteriaHolder, filter.getValue(), INCORRECT_FILTER_PARAMETERS);
			} else {
				String[] values = filter.getValue().split(";");
				ZoneOffset offset = ZoneOffset.of(values[2]);
				ZonedDateTime localDateTime = ZonedDateTime.now(offset).toLocalDate().atStartOfDay(offset);
				long start = from(localDateTime.plusMinutes(parseLong(values[0])).toInstant()).getTime();
				long end = from(localDateTime.plusMinutes(parseLong(values[1])).toInstant()).getTime();
				String newValue = start + "," + end;
				castedValues = (Object[]) this.castValue(criteriaHolder, newValue, INCORRECT_FILTER_PARAMETERS);
			}
			criteria.gte(castedValues[0]).lte(castedValues[1]);
		}

		@Override
		public Object castValue(CriteriaHolder criteriaHolder, String value, ErrorType errorType) {
			/* For linguistic dynamic date range literals */
			if (value.contains(";"))
				return value;

			else
				return castArray(criteriaHolder, value, errorType);
		}
	};

	/*
	 * Workaround. Added to be able to use as constant in annotations
	 */
	public static final String EQ = "eq.";

	public static final String CNT = "cnt.";

	public static final String VALUES_SEPARATOR = ",";
	public static final String TIMESTAMP_SEPARATOR = ";";
	public static final String NEGATIVE_MARKER = "!";

	private String marker;

	Condition(final String marker) {
		this.marker = marker;
	}

	abstract public void addCondition(Criteria criteria, FilterCondition filter, CriteriaHolder criteriaHolder);

	/**
	 * Validate condition value. This method should be overridden in all
	 * conditions which contains validations
	 *
	 * @param criteriaHolder Criteria description
	 * @param value          Value to be casted
	 * @param isNegative     Whether filter is negative
	 */
	abstract public void validate(CriteriaHolder criteriaHolder, String value, boolean isNegative, ErrorType errorType);

	/**
	 * Cast filter values according condition.
	 *
	 * @param criteriaHolder Criteria description
	 * @param values         Value to be casted
	 * @return Casted value
	 */
	abstract public Object castValue(CriteriaHolder criteriaHolder, String values, ErrorType errorType);

	public String getMarker() {
		return marker;
	}

	/**
	 * Finds condition by marker. If there is no condition with specified marker
	 * returns NULL
	 *
	 * @param marker Marker to be checked
	 * @return Condition if found or NULL
	 */
	public static Optional<Condition> findByMarker(String marker) {
		// Negative condition excluder
		if (isNegative(marker)) {
			marker = marker.substring(1);
		}
		String finalMarker = marker;
		return Arrays.stream(values()).filter(it -> it.getMarker().equals(finalMarker)).findAny();
	}

	/**
	 * Check whether condition is negative
	 *
	 * @param marker Marker to check
	 * @return TRUE of negative
	 */
	public static boolean isNegative(String marker) {
		return marker.startsWith(NEGATIVE_MARKER);
	}

	/**
	 * Makes filter marker negative
	 *
	 * @param negative Whether condition is negative
	 * @param marker   Marker to check
	 * @return TRUE of negative
	 */
	public static String makeNegative(boolean negative, String marker) {
		String result;
		if (negative) {
			result = marker.startsWith(NEGATIVE_MARKER) ? marker : NEGATIVE_MARKER.concat(marker);
		} else {
			result = marker.startsWith(NEGATIVE_MARKER) ? marker.substring(1, marker.length()) : marker;
		}
		return result;

	}

	/**
	 * Cast values for filters which have many values(filters with
	 * conditions:btw, in, etc)
	 *
	 * @param criteriaHolder Criteria description
	 * @param value          Value to be casted
	 * @return Casted value
	 */
	public Object[] castArray(CriteriaHolder criteriaHolder, String value, ErrorType errorType) {
		String[] values = value.split(VALUES_SEPARATOR);
		Object[] castedValues = new Object[values.length];
		if (!String.class.equals(criteriaHolder.getDataType())) {
			for (int index = 0; index < values.length; index++) {
				castedValues[index] = criteriaHolder.castValue(values[index].trim(), errorType);
			}
		} else {
			castedValues = values;
		}
		return castedValues;
	}
}