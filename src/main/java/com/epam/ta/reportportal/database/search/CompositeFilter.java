package com.epam.ta.reportportal.database.search;

import org.springframework.data.mongodb.core.query.Criteria;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.collect.Sets.newHashSet;

/**
 * Marks class for lookup for predefined queries
 *
 * @author Andrei Varabyeu
 */
public class CompositeFilter implements Queryable {

    private List<Queryable> filters;
    private Class<?> target;

    public CompositeFilter(List<Queryable> filters) {
        checkArgument(null != filters && !filters.isEmpty(), "Empty filter list");
        checkArgument(1 == filters.stream().map(Queryable::getTarget)
                .distinct().count(), "Different targets");
        this.filters = filters;
        this.target = filters.get(0).getTarget();
    }

    public CompositeFilter(Queryable... filters) {
        this(Arrays.asList(filters));
    }

    public void addCondition(FilterCondition filterCondition) {
        this.filters.add(new Filter(target, newHashSet(filterCondition)));
    }

    @Override
    public List<Criteria> toCriteria() {
        return filters
                .stream()
                .flatMap(c -> c.toCriteria().stream())
                .collect(Collectors.toList());
    }

    @Override
    public Class<?> getTarget() {
        return target;
    }
}
