package com.epam.ta.reportportal.dao;

import org.jooq.*;
import org.jooq.conf.ParamType;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ivan Budayeu
 */
@Service
public class PostgresWrapper {

	@Autowired
	private DSLContext context;

	//@formatter:off

	public SelectJoinStep<Record> pivot(Select<?> fieldsForSelect, Select<?> raw, Select<?> crossTabValues)
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
											rawFields[rawFields.length - 1].getDataType(context.configuration())
									)
					);
		}

		//And postgres requires that the names of the resultant fields be specified
		// explicitly, using 'ct' <"Name of Field", type> pairs...
		StringBuilder ctList = new StringBuilder();
		for (int i = 0; i < resultFields.size(); i++)
		{
			ctList.append("\"").append(resultFields.get(i).getName()).append("\" ").append(resultFields.get(i).getDataType(context.configuration())
									.getTypeName(context.configuration()));

			if (i < resultFields.size() - 1)
			{
				ctList.append(", ");
			}
		}

		fieldsForSelect.getSelect().addAll(resultFields);

		return context.select(fieldsForSelect.getSelect()).from( "crosstab('"
				+ raw.getSQL(ParamType.INLINED).replace("'", "''") + "', '"
				+ crossTabValues.getSQL(ParamType.INLINED).replace("'", "''")
				+ "') as ct(" + ctList.toString() + " )");
	}
}