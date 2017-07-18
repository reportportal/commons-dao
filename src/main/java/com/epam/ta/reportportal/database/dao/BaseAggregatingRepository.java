/*
 * Copyright 2017 EPAM Systems
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
package com.epam.ta.reportportal.database.dao;

import com.epam.reportportal.commons.template.TemplateEngine;
import com.mongodb.AggregationOutput;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import org.springframework.data.mongodb.core.MongoOperations;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Strings.isNullOrEmpty;

/**
 * Repository interface for test items
 *
 * @author Andrei Varabyeu
 */
abstract class BaseAggregatingRepository {

    private final TemplateEngine templateEngine;
    private final MongoOperations mongoOperations;
    private final String collection;

    public BaseAggregatingRepository(Class<?> entityClass, MongoOperations mongoOperations,
            TemplateEngine templateEngine) {
        this.templateEngine = checkNotNull(templateEngine);
        this.mongoOperations = checkNotNull(mongoOperations);
        this.collection = checkNotNull(mongoOperations.getCollectionName(entityClass));
    }

    /**
     * Performs aggregation using provided script
     *
     * @param script Script (aggregation pipeline)
     * @param clazz  Result type
     * @param <R>    Result type
     * @return Aggregation pipeline results
     */
    public <R> List<R> aggregate(String script, Class<R> clazz) {
        checkArgument(!isNullOrEmpty(script), "Empty aggregation script");
        final Object pipeline = JSON.parse(script);
        checkArgument(pipeline instanceof List, "Incorrect aggregation script");

        @SuppressWarnings("unchecked")
        final AggregationOutput aggOut = mongoOperations.getCollection(this.collection)
                .aggregate((List<? extends DBObject>) pipeline);

        return StreamSupport.stream(aggOut.results().spliterator(), false)
                .map(dbo -> mongoOperations.getConverter().read(clazz, dbo))
                .collect(Collectors.toList());
    }

    /**
     * Performs aggregation. Merging script template before execution
     *
     * @param template Template name (should be present in classpath) to merge
     * @param params   Data to merge into the template
     * @param clazz    Result type
     * @param <R>      Result type
     * @return Aggregation pipeline results
     */
    public <R> List<R> aggregate(String template, Map<String, Object> params, Class<R> clazz) {
        final String aggregation = templateEngine.merge(template, params);
        return aggregate(aggregation, clazz);
    }

    protected MongoOperations getMongoOperations() {
        return mongoOperations;
    }

}
