package com.epam.ta.reportportal.dao;

import com.epam.ta.reportportal.entity.Attribute.Attribute;

public interface AttributeRepository extends ReportPortalRepository<Attribute, Long> {

    Attribute findByName(String attributeName);

}
