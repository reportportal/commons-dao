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
import com.epam.ta.reportportal.ws.model.ErrorType;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.lang.reflect.Field;
import java.util.*;

import static java.util.Optional.ofNullable;
import static java.util.stream.Stream.of;

/**
 * Holds criteria mappings for specified class. After initialization reads
 * specified package and stores this information in Map where key is request
 * search criteria
 *
 * @author Andrei Varabyeu
 */
public class CriteriaMap<T> {
	public static final String SEARCH_CRITERIA_SEPARATOR = "$";
	public static final String QUERY_CRITERIA_SEPARATOR = ".";

	/**
	 * Request search criteria to criteria holder mapping
	 */
	private Map<String, CriteriaHolder> classCriteria;

	public CriteriaMap(Class<T> clazz) {
		// TODO check class is Mongo document
		classCriteria = new HashMap<>();
		lookupClass(clazz, new ArrayList<>());
	}

	private void lookupClass(Class<?> clazz, List<Field> parents) {
		for (Field f : clazz.getDeclaredFields()) {
			if (f.isAnnotationPresent(FilterCriteria.class)) {
				boolean dynamicNestedFields = isDynamicInnerFields(f);
				String searchCriteria;
				String queryCriteria;
				if (of(f.getType().getDeclaredFields()).anyMatch(df -> df.isAnnotationPresent(FilterCriteria.class)) || (
						f.getType().isAnnotationPresent(Document.class) && !f.isAnnotationPresent(DBRef.class))) {
					List<Field> currentParents = new ArrayList<>(parents);
					searchCriteria = parents.isEmpty() ? getSearchCriteria(f) : getSearchCriteria(f, currentParents);
					queryCriteria = parents.isEmpty() ? getQueryCriteria(f) : getQueryCriteria(f, currentParents);
					classCriteria.put(searchCriteria,
							new CriteriaHolder(searchCriteria, queryCriteria, f.getType(), f.isAnnotationPresent(DBRef.class),
									dynamicNestedFields));
					currentParents.add(f);
					lookupClass(f.getType(), currentParents);
				} else {
					searchCriteria = getSearchCriteria(f);
					queryCriteria = getQueryCriteria(f);
					if (parents.isEmpty()) {
						classCriteria.put(searchCriteria,
								new CriteriaHolder(searchCriteria, queryCriteria, f.getType(), f.isAnnotationPresent(DBRef.class),
										dynamicNestedFields));
					} else {
						searchCriteria = getSearchCriteria(f, parents);
						queryCriteria = getQueryCriteria(f, parents);
						classCriteria.put(getSearchCriteria(f, parents),
								new CriteriaHolder(searchCriteria, queryCriteria, f.getType(), false, dynamicNestedFields));
					}
				}
			}
		}
	}

	private String getSearchCriteria(Field f) {
		return f.getAnnotation(FilterCriteria.class).value();
	}

	private boolean isDynamicInnerFields(Field f) {
		return Map.class.isAssignableFrom(f.getType());
	}

	private String getSearchCriteria(Field f, List<Field> parents) {
		StringBuilder criteriaBuilder = new StringBuilder();
		for (Field parent : parents) {
			criteriaBuilder.append(getSearchCriteria(parent));
			criteriaBuilder.append(SEARCH_CRITERIA_SEPARATOR);
		}
		if (criteriaBuilder.length() > 0) {
			/* Remove last separator */
			return criteriaBuilder.append(getSearchCriteria(f)).toString();
		} else {
			return "";
		}
	}

	private String getQueryCriteria(Field f, List<Field> parents) {
		StringBuilder criteriaBuilder = new StringBuilder();
		for (Field parent : parents) {
			criteriaBuilder.append(getQueryCriteria(parent));
			criteriaBuilder.append(QUERY_CRITERIA_SEPARATOR);
		}
		if (criteriaBuilder.length() > 0) {
			/* Remove last separator */
			return criteriaBuilder.append(getQueryCriteria(f)).toString();
		} else {
			return "";
		}
	}

	public static String getQueryCriteria(Field f) {
		String queryCriteria;
		if (f.isAnnotationPresent(org.springframework.data.mongodb.core.mapping.Field.class)) {
			queryCriteria = f.getAnnotation(org.springframework.data.mongodb.core.mapping.Field.class).value();
		} else {
			queryCriteria = f.getName();
		}
		return queryCriteria;
	}

	/**
	 * Returns holder for specified request search criteria
	 *
	 * @param searchCriteria Front-end search criteria
	 * @return Search criteria details
	 */
	public CriteriaHolder getCriteriaHolder(String searchCriteria) {
		Optional<CriteriaHolder> criteria = getCriteriaHolderUnchecked(searchCriteria);
		BusinessRule.expect(criteria, Predicates.isPresent())
				.verify(ErrorType.INCORRECT_FILTER_PARAMETERS, "Criteria '" + searchCriteria + "' not defined");
		return criteria.get();
	}

	/**
	 * Returns holder for specified request search criteria. If field contains dynamic part, uses 'starts with' condition
	 *
	 * @param searchCriteria Front-end search criteria
	 * @return Search criteria details
	 */
	public Optional<CriteriaHolder> getCriteriaHolderUnchecked(String searchCriteria) {
		Optional<CriteriaHolder> criteriaHolder = ofNullable(classCriteria.get(searchCriteria));
		if (!criteriaHolder.isPresent()) {
			return classCriteria.entrySet().stream().filter(it -> it.getValue().isHasDynamicPart())
					.filter(it -> searchCriteria.startsWith(it.getKey())).findAny().map(Map.Entry::getValue);
		}
		return criteriaHolder;
	}

	@Override
	public String toString() {
		return "CriteriaMap [classCriteria=" + classCriteria + "]";
	}
}