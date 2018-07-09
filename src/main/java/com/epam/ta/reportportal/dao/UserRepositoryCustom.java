package com.epam.ta.reportportal.dao;

import com.epam.ta.reportportal.BinaryData;

public interface UserRepositoryCustom {

	String uploadUserPhoto(String login, BinaryData data);
}
