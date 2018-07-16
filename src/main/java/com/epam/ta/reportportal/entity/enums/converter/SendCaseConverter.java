package com.epam.ta.reportportal.entity.enums.converter;

import com.epam.ta.reportportal.commons.SendCase;
import com.epam.ta.reportportal.exception.ReportPortalException;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * @author Ivan Budayeu
 */
@Converter(autoApply = true)
public class SendCaseConverter implements AttributeConverter<SendCase, String> {
	@Override
	public String convertToDatabaseColumn(SendCase attribute) {
		return attribute.getCaseString();
	}

	@Override
	public SendCase convertToEntityAttribute(String dbSendCaseName) {
		return SendCase.findByName(dbSendCaseName).orElseThrow(() -> new ReportPortalException("Can not convert send case name from database."));
	}
}
