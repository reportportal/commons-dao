package com.epam.ta.reportportal.entity.enums.converter;

import com.epam.ta.reportportal.entity.user.UserType;
import com.epam.ta.reportportal.exception.ReportPortalException;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class UserTypeConverter implements AttributeConverter<UserType, String> {
	@Override
	public String convertToDatabaseColumn(UserType attribute) {
		return attribute.toString();
	}

	@Override
	public UserType convertToEntityAttribute(String dbUserTypeName) {
		return UserType.findByName(dbUserTypeName)
				.orElseThrow(() -> new ReportPortalException("Can not convert user type name from database."));
	}
}
