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
/*
 * This file is part of Report Portal.
 *
 * Report Portal is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Report Portal is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Report Portal.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.epam.ta.reportportal.database.search;

import com.epam.ta.reportportal.commons.Preconditions;
import com.epam.ta.reportportal.commons.validation.BusinessRule;
import com.epam.ta.reportportal.commons.validation.Suppliers;
import com.epam.ta.reportportal.database.entity.statistics.IssueCounter;
import com.epam.ta.reportportal.ws.model.ErrorType;
import com.google.common.base.Strings;
import org.bson.Document;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;

/**
 * MongoDB query builder. Constructs MongoDB
 * {@link org.springframework.data.mongodb.core.query.Query} by provided filters <br>
 * <p>
 * TODO Some interface for QueryBuilder should be created to avoid problems with possible changing
 * of DB engine
 *
 * @author Andrei Varabyeu
 * @author Andrei_Ramanchuk
 */
public class QueryBuilder {

    /**
     * MongoDB postfix to be applied to reference fields
     */
    private static final String REFERENCE_POSTFIX = ".$id";

    /**
     * MongoDB query representation
     */
    private Query query;

    private static CriteriaMapFactory criteriaMapFactory = CriteriaMapFactory.DEFAULT_INSTANCE_SUPPLIER.get();

    private QueryBuilder() {
        query = new Query();
    }

    public static QueryBuilder newBuilder() {
        return new QueryBuilder();
    }

    public static List<Criteria> toCriteriaList(Filter filter) {
        /* Get map of defined @FilterCriteria fields */
        CriteriaMap<?> map = criteriaMapFactory.getCriteriaMap(filter.getTarget());
        final Function<FilterCondition, Criteria> transformer = filterConverter(
                map);

        return filter.getFilterConditions().stream()
                .map(transformer)
                .collect(toList());
    }

    /**
     * Adds {@link com.epam.ta.reportportal.database.search.Filter} using 'AND' condition.
     *
     * @param filter
     * @return QueryBuilder
     */
    public QueryBuilder with(Filter filter) {
        toCriteriaList(filter)
                .forEach(criteriaDef -> query.addCriteria(criteriaDef));

        return this;
    }

    /**
     * Adds {@link org.springframework.data.domain.Pageable} conditions
     *
     * @param p
     * @return QueryBuilder
     */
    public QueryBuilder with(Pageable p) {
        query.with(p);
        return this;
    }

    /**
     * Add limit
     *
     * @param limit
     * @return QueryBuilder
     */
    public QueryBuilder with(int limit) {
        query.limit(limit);
        return this;
    }

    /**
     * Add sorting {@link Sort}
     *
     * @param sort
     * @return QueryBuilder
     */
    public QueryBuilder with(Sort sort) {
        query.with(sort);
        return this;
    }

    /**
     * Builds query
     *
     * @return Query
     */
    public Query build() {
        return query;
    }

    /* Temporary! */
    public static String criteriaRebuilder(String input, String extention) {
        StringBuilder builder = new StringBuilder(input);
		/* add total suffix if defects$ type is missed */
        if (Strings.isNullOrEmpty(extention))
            builder.append(".").append(IssueCounter.GROUP_TOTAL);
        else
            builder.append(".").append(extention);
        return builder.toString();
    }

    /**
     * Processing of searchCriteria string from
     * {@link com.epam.ta.reportportal.database.search.FilterCondition}<br>
     * <p>
     * If searchCriteria contains dynamic extension field for DB query criteria, then dynamic
     * extension is separated from specified filter criteria.
     *
     * @param filterSearchCriteria - initial searchCriteria parameter from FilterCondition object
     * @return ComplexSearchCriteria -
     * {@see com.epam.ta.reportportal.database.search.ComplexSearchCriteria}
     */
    public static ComplexSearchCriteria filterSearchCriteriaPreProcessor(String filterSearchCriteria) {
        String[] split = filterSearchCriteria.split("\\$");
        if (filterSearchCriteria.contains(IssueCounter.DEFECTS_FOR_FILTER) && (split.length > 3)) {
            StringJoiner key = new StringJoiner("$");
            for (int i = 0; i < 3; i++) {
                key.add(split[i]);
            }
            return new ComplexSearchCriteria(key.toString(), split[3]);
        } else {
            return new ComplexSearchCriteria(filterSearchCriteria, null);
        }
    }

    /**
     * Particular representation of searchCriteria with complex or dynamic fields.<br>
     * Current implementation cover defects dynamic sub types filtering.<br>
     * <b>globalSearchCriteria</b> - main search criteria for MongoDB fields from {@link Document}
     * annotated classes marked with {@link FilterCriteria}
     * <p>
     * <b>extension</b> - additional dynamic part. Currently it represent a sub-types for
     * issueCounter, created by users.
     *
     * @author Andrei_Ramanchuk
     */
    public static class ComplexSearchCriteria {
        private String globalSearchCriteria;
        private String extension;

        public ComplexSearchCriteria(String criteria, String value) {
            this.globalSearchCriteria = criteria;
            this.extension = value;
        }

        public String getGlobalSearchCriteria() {
            return globalSearchCriteria;
        }

        public String getExtension() {
            return extension;
        }

        @Override
        public String toString() {
            return "ComplexSearchCriteria [globalSearchCriteria=" + globalSearchCriteria + ", extension=" + extension
                    + "]";
        }
    }

    public static Function<FilterCondition, Criteria> filterConverter(CriteriaMap<?> map) {
        return new Function<FilterCondition, Criteria>() {

            @Nullable
            @Override
            public Criteria apply(@Nullable FilterCondition filterCondition) {
                boolean reload = false;
                ComplexSearchCriteria filterCriteria = filterSearchCriteriaPreProcessor(
                        filterCondition.getSearchCriteria());
                Optional<CriteriaHolder> criteriaHolder = map
                        .getCriteriaHolderUnchecked(filterCriteria.getGlobalSearchCriteria());
                BusinessRule.expect(criteriaHolder, Preconditions.IS_PRESENT)
                        .verify(ErrorType.INCORRECT_FILTER_PARAMETERS,
                                Suppliers.formattedSupplier("Filter parameter {} is not defined",
                                        filterCondition.getSearchCriteria()));

                Criteria searchCriteria;
                CriteriaHolder updated = null;
                if (criteriaHolder.get().isReference()) {
                    searchCriteria = Criteria.where(criteriaHolder.get().getQueryCriteria().concat(REFERENCE_POSTFIX));
                } else {
                    if (criteriaHolder.get().getQueryCriteria().contains(IssueCounter.DEFECTS_FOR_DB)) {
                        String rebuilded = criteriaRebuilder(criteriaHolder.get().getQueryCriteria(),
                                filterCriteria.getExtension());
                        searchCriteria = Criteria.where(rebuilded);
                        updated = new CriteriaHolder(criteriaHolder.get().getFilterCriteria(), rebuilded, Integer.class,
                                criteriaHolder.get().isReference());
                        reload = true;
                    } else {
                        searchCriteria = Criteria.where(criteriaHolder.get().getQueryCriteria());
                    }
                }

			/* Does FilterCondition contains negative=true? */
                if (filterCondition.isNegative()) {
                    searchCriteria = searchCriteria.not();
                }

                if (reload) {
                    filterCondition.getCondition().addCondition(searchCriteria, filterCondition, updated);
                } else {
                    filterCondition.getCondition().addCondition(searchCriteria, filterCondition, criteriaHolder.get());
                }
                return searchCriteria;
            }
        };
    }
}
