package com.epam.ta.reportportal.dao;

import org.jooq.*;
import org.jooq.conf.ParamType;
import org.jooq.impl.DSL;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ivan Budayeu
 */
public class PostgresWrapper {

	//@formatter:off

	public static SelectJoinStep<Record> pivot(DSLContext context, Select<?> raw, Select<?> crossTabValues)
	{
		List<Field<?>> resultFields = new ArrayList<>();

		//The result will contain all but the last two columns for the raw fields.

		Field<?> []rawFields = raw.fields();
		for (int i = 0; i < rawFields.length - 2; i++)
		{
			resultFields.add(rawFields[i]);
		}

		//And then one column for each cross tab value specified
		Result<?> crossTabHeadings = context.fetch(crossTabValues);
		for (Record r : crossTabHeadings)
		{
			resultFields.add
					(
							DSL.field
									(
											r.getValue(0).toString(),
											rawFields[rawFields.length - 2].getDataType(context.configuration())
									)
					);
		}

		//And postgres requires that the names of the resultant fields be specified
		// explicitly, using 'ct' <"Name of Field", type> pairs...
		StringBuffer ctList = new StringBuffer();
		for (int i = 0; i < resultFields.size(); i++)
		{
			ctList.append
					(
							"\"" + resultFields.get(i).getName() + "\" "
									+ resultFields.get(i).getDataType(context.configuration())
									.getTypeName(context.configuration())
					);

			if (i < resultFields.size() - 1)
			{
				ctList.append(", ");
			}
		}

		return context.select().from( "crosstab('"
				+ raw.getSQL(ParamType.INLINED).replace("'", "''") + "', '"
				+ crossTabValues.getSQL(ParamType.INLINED).replace("'", "''")
				+ "') as ct(" + ctList.toString() + " )");
	}
}