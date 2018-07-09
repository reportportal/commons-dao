package com.epam.ta.reportportal.dao;

import com.epam.ta.reportportal.BinaryData;
import com.epam.ta.reportportal.filesystem.DataStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserCustomrepositoryImpl implements UserRepositoryCustom {

	@Autowired
	private DataStore dataStore;

	@Override
	public String uploadUserPhoto(String login, BinaryData data) {
		return dataStore.save(login, data.getInputStream());
	}
}
