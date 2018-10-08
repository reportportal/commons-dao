package com.epam.ta.reportportal.dao;

import com.epam.ta.reportportal.entity.attribute.Attribute;
import com.epam.ta.reportportal.entity.enums.ProjectAttributeEnum;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.Set;

import static com.epam.ta.reportportal.dao.util.RecordMappers.ATTRIBUTE_MAPPER;
import static com.epam.ta.reportportal.jooq.tables.JAttribute.ATTRIBUTE;

/**
 * @author Ivan Budaev
 */
@Repository
public class AttributeRepositoryCustomImpl implements AttributeRepositoryCustom {

	private final DSLContext dsl;

	@Autowired
	public AttributeRepositoryCustomImpl(DSLContext dsl) {
		this.dsl = dsl;
	}

	@Override
	public Set<Attribute> getDefaultProjectAttributes() {
		return dsl.select()
				.from(ATTRIBUTE)
				.where(ATTRIBUTE.NAME.in(Arrays.stream(ProjectAttributeEnum.values())
						.map(ProjectAttributeEnum::getAttribute)
						.toArray(String[]::new)))
				.fetchSet(ATTRIBUTE_MAPPER);

	}
}
