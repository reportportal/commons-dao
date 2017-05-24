package com.epam.ta.reportportal.database.search;

import org.springframework.data.mongodb.core.query.Criteria;

import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

/**
 * Specifies predefined filter for cases when we cannot select using regular filters
 *
 * @author Andrei Varabyeu
 */
public class PredefinedFilter implements Queryable {

    private final Class<?> target;
    private final List<Criteria> criteriaList;

    public PredefinedFilter(Class<?> target, Criteria criteria) {
        this(target, null == criteria ? emptyList() : singletonList(criteria));
    }

    public PredefinedFilter(Class<?> target, List<Criteria> criteriaList) {
        checkArgument(null != target, "Target should not be null");
        checkArgument(null != criteriaList, "Criteria should not be null");
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
