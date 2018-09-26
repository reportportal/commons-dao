package com.epam.ta.reportportal.dao;

import com.epam.ta.reportportal.BinaryData;
import com.epam.ta.reportportal.entity.user.User;

/**
 * @author Pavel Bortnik
 */
public interface UserRepositoryCustom {

	String uploadUserPhoto(String login, BinaryData binaryData);

	String replaceUserPhoto(String login, BinaryData binaryData);

	String replaceUserPhoto(User user, BinaryData binaryData);

	BinaryData findUserPhoto(User user);

	void deleteUserPhoto(User user);


}
