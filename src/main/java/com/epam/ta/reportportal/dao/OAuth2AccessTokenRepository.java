/*
 * Copyright 2018 EPAM Systems
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

	StoredAccessToken findByTokenId(String tokenId);

	StoredAccessToken findByRefreshToken(String refreshToken);

	StoredAccessToken findByAuthenticationId(String authenticationId);

	Stream<StoredAccessToken> findByUserName(String userName);

	Stream<StoredAccessToken> findByClientIdAndUserName(String clientId, String userName);

	Stream<StoredAccessToken> findByClientId(String clientId);

}
