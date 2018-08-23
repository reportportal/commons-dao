package com.epam.ta.reportportal.dao;

import org.jooq.*;
import org.jooq.conf.ParamType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.name;

/**
 * @author Ivan Budayeu
 */
@Service
public class PostgresCrosstabWrapper {

	@Autowired
	private DSLContext context;

	//@formatter:off

	public SelectJoinStep<Record> pivot(List<Field<?>> defaultFields, Select<?> raw, Select<?> crossTabValues)
	{

		//The result will contain all but the last two columns for the raw fields.

		Field<?> []rawFields = raw.fields();
		for (int i = 0; i < rawFields.length - 2; i++)
		{
			defaultFields.add(field(name(rawFields[i].getName()), Long.class));
		}

		//And then one column for each cross tab value specified
		Result<?> crossTabHeadings = context.fetch(crossTabValues);
		for (Record r : crossTabHeadings)
		{
			defaultFields.add
					(
							field
									(
											r.getValue(0).toString(),
											rawFields[rawFields.length - 1].getDataType(context.configuration())
									)
					);
		}

		//And postgres requires that the names of the resultant fields be specified
		// explicitly, using 'ct' <"Name of Field", type> pairs...
		StringBuilder ctList = new StringBuilder();
		for (int i = 0; i < defaultFields.size(); i++)
		{
			ctList.append(defaultFields.get(i).getName()).append(" ").append(defaultFields.get(i).getDataType(context.configuration())
									.getTypeName(context.configuration()));

			if (i < defaultFields.size() - 1)
			{
				ctList.append(", ");
			}
		}

		return context.select(defaultFields).from( "crosstab('"
				+ raw.getSQL(ParamType.INLINED).replace("'", "''") + "', '"
				+ crossTabValues.getSQL(ParamType.INLINED).replace("'", "''")
				+ "') as ct(" + ctList.toString() + " )");
	}
}