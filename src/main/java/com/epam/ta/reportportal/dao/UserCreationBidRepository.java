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

import com.epam.ta.reportportal.entity.user.UserCreationBid;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.Optional;

/**
 * @author Ivan Budaev
 */
public interface UserCreationBidRepository extends ReportPortalRepository<UserCreationBid, String>, UserCreationBidRepositoryCustom {

	Optional<UserCreationBid> findByEmail(String email);

	@Modifying
	@Query(value = "DELETE FROM UserCreationBid u WHERE  u.lastModified < :date")
	int expireBidsOlderThan(@Param("date") Date date);
}
