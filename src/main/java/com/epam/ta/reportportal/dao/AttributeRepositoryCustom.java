package com.epam.ta.reportportal.dao;

import com.epam.ta.reportportal.entity.attribute.Attribute;

import java.util.Set;

/**
 * @author Ivan Budaev
 */
public interface AttributeRepositoryCustom {

	Set<Attribute> getDefaultProjectAttributes();
}
