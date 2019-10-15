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

package com.epam.ta.reportportal.dao;

import com.epam.ta.reportportal.entity.user.StoredAccessToken;
import org.springframework.stereotype.Repository;

import java.util.stream.Stream;

/**
 * @author <a href="mailto:pavel_bortnik@epam.com">Pavel Bortnik</a>
 */
@Repository
public interface OAuth2AccessTokenRepository extends ReportPortalRepository<StoredAccessToken, Long> {

	/**
	 * Find entity by token id
	 *
	 * @param tokenId token id
	 * @return {@link StoredAccessToken}
	 */
	StoredAccessToken findByTokenId(String tokenId);

	/**
	 * Find entity by authentication id
	 *
	 * @param authenticationId authentication id
	 * @return {@link StoredAccessToken}
	 */
	StoredAccessToken findByAuthenticationId(String authenticationId);

	/**
	 * Find entity by client id and username
	 *
	 * @param clientId Client id
	 * @param userName Username
	 * @return Stream of {@link StoredAccessToken}
	 */
	Stream<StoredAccessToken> findByClientIdAndUserName(String clientId, String userName);

	/**
	 * Find entity by client id
	 * @param clientId client id
	 * @return Stream of {@link StoredAccessToken}
	 */
	Stream<StoredAccessToken> findByClientId(String clientId);

}
