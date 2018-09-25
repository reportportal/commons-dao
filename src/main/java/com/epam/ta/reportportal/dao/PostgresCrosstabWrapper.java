package com.epam.ta.reportportal.dao;

import org.jooq.*;
import org.jooq.conf.ParamType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.jooq.impl.DSL.*;

/**
 * @author Ivan Budayeu
 */
@Service
public class PostgresCrosstabWrapper {

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
			resultFields.add(field(name("ct", rawFields[i].getName()), Long.class));
		}

		//And then one column for each cross tab value specified
		Result<?> crossTabHeadings = context.fetch(crossTabValues);
		for (Record r : crossTabHeadings)
		{
			String value = r.getValue(0, String.class);
			resultFields.add
					(
							field
									(
											value,
											rawFields[rawFields.length - 1].getDataType(context.configuration())
									)

					);
		}

		//And postgres requires that the names of the resultant fields be specified
		// explicitly, using 'ct' <"Name of Field", type> pairs...
		StringBuilder ctList = new StringBuilder();
		for (int i = 0; i < resultFields.size(); i++)
		{
			ctList.append(resultFields.get(i).getName()).append(" ").append(resultFields.get(i).getDataType(context.configuration())
									.getTypeName(context.configuration()));

			if (i < resultFields.size() - 1)
			{
				ctList.append(", ");
			}
		}

		List<Field<?>> select = fieldsForSelect.getSelect();
		select.addAll(resultFields.stream().map(field -> coalesce(field, 0).as(field.getName())).collect(Collectors.toList()));

		return context.select(select).from( "crosstab('"
				+ raw.getSQL(ParamType.INLINED).replace("'", "''") + "', '"
				+ crossTabValues.getSQL(ParamType.INLINED).replace("'", "''")
				+ "') as ct(" + ctList.toString() + " )");
	}
}