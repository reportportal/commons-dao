package com.epam.ta.reportportal.dao;

/**
 * @author Andrey Plisunov
 */

import com.epam.ta.reportportal.entity.attribute.Attribute;

public interface AttributeRepository extends ReportPortalRepository<Attribute, Long>, AttributeRepositoryCustom {

    Attribute findByName(String attributeName);

}
