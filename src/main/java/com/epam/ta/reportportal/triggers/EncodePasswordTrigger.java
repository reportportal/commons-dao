/*
 * Copyright 2016 EPAM Systems
 * 
 * 
 * This file is part of EPAM Report Portal.
 * https://github.com/epam/ReportPortal
 * 
 * Report Portal is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Report Portal is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Report Portal.  If not, see <http://www.gnu.org/licenses/>.
 */ 

package com.epam.ta.reportportal.triggers;

import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.util.Assert;

import com.epam.ta.reportportal.database.entity.user.User;
import com.google.common.base.Charsets;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

/**
 * Password encode trigger
 *
 * @author Andrei Varabyeu
 */
//@Component
public class EncodePasswordTrigger extends AbstractMongoEventListener<User> {

	private HashFunction hashFunction = Hashing.md5();

	@Override
	public void onBeforeConvert(BeforeConvertEvent<User> event) {
		User user = event.getSource();
		Assert.notNull(user.getPassword(), "Password must not be null");
		user.setPassword(hashFunction.hashString(user.getPassword(), Charsets.UTF_8).toString());
		super.onBeforeConvert(event);
	}
}