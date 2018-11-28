/*
 * Copyright (C) 2018 EPAM Systems
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

package com.epam.ta.reportportal.entity.bts;

import com.epam.ta.reportportal.entity.enums.AuthType;
import com.epam.ta.reportportal.exception.ReportPortalException;
import com.epam.ta.reportportal.ws.model.ErrorType;
import com.epam.ta.reportportal.ws.model.externalsystem.CreateIntegrationRQ;
import com.google.common.base.Preconditions;
import org.jasypt.util.text.BasicTextEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Pavel Bortnik
 */
@Component
public class BugTrackingSystemAuthFactory {

	@Autowired
	private BasicTextEncryptor simpleEncryptor;

	public BugTrackingSystemAuth createAuthObject(BugTrackingSystemAuth auth, CreateIntegrationRQ rq) {
		Preconditions.checkNotNull(rq, "Provided parameter can't be null");
		AuthType authType = AuthType.findByName(rq.getExternalSystemAuth());
		auth = resetFields(auth);
		auth.setAuthType(authType);
		switch (authType) {
			case APIKEY:
				if (null != rq.getAccessKey()) {
					auth.setAccessKey(rq.getAccessKey());
					return auth;
				}
				break;
			case BASIC:
				if (null != rq.getUsername() && null != rq.getPassword()) {
					auth.setUsername(rq.getUsername());
					auth.setPassword(simpleEncryptor.encrypt(rq.getPassword()));
					return auth;
				}
				break;
			case OAUTH:
				if (null != rq.getAccessKey()) {
					auth.setAccessKey(rq.getAccessKey());
					return auth;
				}
				break;
			default:
				throw new ReportPortalException(ErrorType.INCORRECT_AUTHENTICATION_TYPE);
		}
		throw new ReportPortalException(ErrorType.INCORRECT_REQUEST);
	}

	private BugTrackingSystemAuth resetFields(BugTrackingSystemAuth auth) {
		auth.setAccessKey(null);
		auth.setUsername(null);
		auth.setDomain(null);
		auth.setPassword(null);
		return auth;
	}
}
