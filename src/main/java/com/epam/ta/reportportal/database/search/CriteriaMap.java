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

import static java.util.stream.Stream.of;

import java.lang.reflect.Field;
import java.util.*;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.epam.ta.reportportal.commons.Predicates;
import com.epam.ta.reportportal.commons.validation.BusinessRule;
import com.epam.ta.reportportal.ws.model.ErrorType;

/**
 * Holds criteria mappings for specified class. After initialization reads
 * specified package and stores this information in Map where key is request
 * search criteria
 * 
 * @author Andrei Varabyeu
 * 
 */
public class CriteriaMap<T> {
	public static final String SEARCH_CRITERIA_SEPARATOR = "$";
	public static final String QUERY_CRITERIA_SEPARATOR = ".";

	/**
	 * Request search criteria to criteria holder mapping
	 */
	private Map<String, CriteriaHolder> classCriterias;

	public CriteriaMap(Class<T> clazz) {
		// TODO check class is Mongo document
		classCriterias = new HashMap<>();
		lookupClass(clazz, new ArrayList<>());
	}

	private void lookupClass(Class<?> clazz, List<Field> parents) {
		for (Field f : clazz.getDeclaredFields()) {
			if (f.isAnnotationPresent(FilterCriteria.class)) {
				String searchCriteria;
				String queryCriteria;
				if (of(f.getType().getDeclaredFields()).filter(df -> df.isAnnotationPresent(FilterCriteria.class)).findFirst().isPresent()
						|| (f.getType().isAnnotationPresent(Document.class) && !f.isAnnotationPresent(DBRef.class))) {
					List<Field> currentParents = new ArrayList<>(parents);
					currentParents.add(f);
					lookupClass(f.getType(), currentParents);
				} else {
					searchCriteria = getSearchCriteria(f);
					queryCriteria = getQueryCriteria(f);
					if (parents.isEmpty()) {
						classCriterias.put(searchCriteria,
								new CriteriaHolder(searchCriteria, queryCriteria, f.getType(), f.isAnnotationPresent(DBRef.class)));
					} else {
						searchCriteria = getSearchCriteria(f, parents);
						queryCriteria = getQueryCriteria(f, parents);
						classCriterias.put(getSearchCriteria(f, parents),
								new CriteriaHolder(searchCriteria, queryCriteria, f.getType(), false));
					}
				}
			}
		}
	}

	private String getSearchCriteria(Field f) {
		return f.getAnnotation(FilterCriteria.class).value();
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
	 * @param searchCriteria
	 * @return
	 */
	public CriteriaHolder getCriteriaHolder(String searchCriteria) {
		BusinessRule.expect(classCriterias.containsKey(searchCriteria), Predicates.equalTo(Boolean.TRUE))
				.verify(ErrorType.INCORRECT_FILTER_PARAMETERS, "Criteria '" + searchCriteria + "' not defined");
		return classCriterias.get(searchCriteria);
	}

	/**
	 * Returns holder for specified request search criteria
	 * 
	 * @param searchCriteria
	 * @return
	 */
	public Optional<CriteriaHolder> getCriteriaHolderUnchecked(String searchCriteria) {
		return Optional.ofNullable(classCriterias.get(searchCriteria));
	}

	@Override
	public String toString() {
		return "CriteriaMap [classCriterias=" + classCriterias + "]";
	}
}