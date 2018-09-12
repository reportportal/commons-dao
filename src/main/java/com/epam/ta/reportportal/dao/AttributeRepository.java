package com.epam.ta.reportportal.dao;

/**
 * @author Andrey Plisunov
 */

import com.epam.ta.reportportal.entity.Attribute.Attribute;

public interface AttributeRepository extends ReportPortalRepository<Attribute, Long> {

    Attribute findByName(String attributeName);

}
