package com.epam.ta.reportportal.entity.enums.converter;

import com.epam.ta.reportportal.entity.AnalyzeMode;
import com.epam.ta.reportportal.exception.ReportPortalException;
import com.epam.ta.reportportal.ws.model.ErrorType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * @author Ivan Budayeu
 */
@Converter(autoApply = true)
public class AnalyzerModeConverter implements AttributeConverter<AnalyzeMode, String> {
	@Override
	public String convertToDatabaseColumn(AnalyzeMode attribute) {
		return attribute.getValue();
	}

	@Override
	public AnalyzeMode convertToEntityAttribute(String dbAnalyzerModeName) {
		return AnalyzeMode.fromString(dbAnalyzerModeName)
				.orElseThrow(() -> new ReportPortalException(ErrorType.UNCLASSIFIED_REPORT_PORTAL_ERROR));
	}
}
