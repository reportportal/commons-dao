/*
 * Copyright 2019 EPAM Systems
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.epam.ta.reportportal.dao.util;

import com.epam.ta.reportportal.commons.querygen.FilterTarget;
import com.epam.ta.reportportal.jooq.tables.JAclEntry;
import com.epam.ta.reportportal.jooq.tables.JAclObjectIdentity;
import com.epam.ta.reportportal.jooq.tables.JAclSid;
import com.epam.ta.reportportal.jooq.tables.JShareableEntity;
import org.jooq.Condition;
import org.jooq.impl.DSL;

/**
 * @author <a href="mailto:pavel_bortnik@epam.com">Pavel Bortnik</a>
 */
public class ShareableUtils {

	private ShareableUtils() {
		//static only
	}

	/**
	 * Condition that retrieves entities only shared entities.
	 *
	 * @param userName Username
	 * @return Condition for shared entities
	 */
	public static Condition sharedCondition(String userName) {
		return permittedCondition(userName).and(JShareableEntity.SHAREABLE_ENTITY.SHARED);
	}

	/**
	 * Condition that retrieves entities permitted (shared + own) entities.
	 *
	 * @param userName Username
	 * @return Condition for permitted entities
	 */
	public static Condition permittedCondition(String userName) {
		return JAclEntry.ACL_ENTRY.SID.in(DSL.select(JAclSid.ACL_SID.ID).from(JAclSid.ACL_SID).where(JAclSid.ACL_SID.SID.eq(userName)));
	}

	/**
	 * Condition that retrieves entities of {@link FilterTarget} class only own entities.
	 *
	 * @param userName Username
	 * @return Condition for own entities
	 */
	public static Condition ownCondition(String userName) {
		return JAclObjectIdentity.ACL_OBJECT_IDENTITY.OWNER_SID.in(DSL.select(JAclSid.ACL_SID.ID)
				.from(JAclSid.ACL_SID)
				.where(JAclSid.ACL_SID.SID.eq(userName)));
	}

}
