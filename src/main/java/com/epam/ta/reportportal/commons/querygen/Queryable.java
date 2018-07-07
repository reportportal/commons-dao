package com.epam.ta.reportportal.commons.querygen;

import org.jooq.Record;
import org.jooq.SelectQuery;

/**
 * Can be used to generate Mongo queries
 *
 * @author Andrei Varabyeu
 */
public interface Queryable {

	SelectQuery<? extends Record> toQuery();

}