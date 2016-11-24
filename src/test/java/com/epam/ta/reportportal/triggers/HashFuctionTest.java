/*
 * Copyright 2016 EPAM Systems
 * 
 * 
 * This file is part of EPAM Report Portal.
 * https://github.com/reportportal/commons-dao
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

import com.epam.ta.reportportal.database.entity.user.User;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

/**
 * Check that Guava and Spring's Crypto generate the same hash functions
 *
 * @author Andrei Varabyeu
 */
public class HashFuctionTest {

	@Test
	public void checkHashFunction() {
		String testPassword = "value to encode";
		EncodePasswordTrigger trigger = new EncodePasswordTrigger();
		User user = new User();
		user.setPassword(testPassword);

		trigger.onBeforeConvert(new BeforeConvertEvent<>(user, null));

		Assert.assertEquals(user.getPassword(), new Md5PasswordEncoder().encodePassword(testPassword, null));
	}
}