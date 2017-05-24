package com.epam.ta.reportportal.database.search;

import org.springframework.data.mongodb.core.query.Criteria;

import java.util.List;

/**
 * Specifies predefined filter for cases when we cannot select using regular filters
 *
 * @author Andrei Varabyeu
 */
public class PredefinedFilter implements Queryable {

    private final Class<?> target;
    private final List<Criteria> criteriaList;

    public PredefinedFilter(Class<?> target, List<Criteria> criteriaList) {
        this.criteriaList = criteriaList;
        this.target = target;
    }

    @Override
    public List<Criteria> toCriteria() {
        return criteriaList;
    }

    @Override
    public Class<?> getTarget() {
        return target;
    }
}
