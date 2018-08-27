/*
 * Copyright 2017 EPAM Systems
 *
 *
 * This file is part of EPAM Report Portal.
 * https://github.com/reportportal/service-api
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

package com.epam.ta.reportportal.dao;

import com.epam.ta.reportportal.commons.querygen.Filter;
import com.epam.ta.reportportal.commons.querygen.QueryBuilder;
import com.epam.ta.reportportal.entity.enums.LaunchModeEnum;
import com.epam.ta.reportportal.entity.launch.Launch;
import com.epam.ta.reportportal.jooq.enums.JLaunchModeEnum;
import com.epam.ta.reportportal.jooq.enums.JStatusEnum;
import com.epam.ta.reportportal.jooq.tables.JLaunch;
import com.epam.ta.reportportal.jooq.tables.JProject;
import com.epam.ta.reportportal.jooq.tables.JUsers;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.RecordMapper;
import org.jooq.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.function.Function;

import static com.epam.ta.reportportal.dao.constant.WidgetContentRepositoryConstants.LAUNCHES;
import static com.epam.ta.reportportal.jooq.Tables.*;

/**
 * @author Pavel Bortnik
 */
@Repository
public class LaunchRepositoryCustomImpl implements LaunchRepositoryCustom {

	private static final RecordMapper<? super Record, Launch> LAUNCH_RECORD_MAPPER = r -> {
		Launch launch = r.into(Launch.class);
		return launch;
	};

	private static final Function<Result<? extends Record>, List<Launch>> LAUNCH_FETCHER = result -> {
		Map<Long, Launch> res = new HashMap<>();
		result.forEach(r -> {
			Long launchId = r.get(LAUNCH.ID);
			if (res.containsKey(launchId)) {
				Launch launch = res.get(launchId);
				res.replace(launchId, launch);
			} else {
				Launch launch = LAUNCH_RECORD_MAPPER.map(r);
				res.put(launchId, launch);
			}
		});
		return new ArrayList<>(res.values());
	};

	@Autowired
	private DSLContext dsl;

	@Override
	public Boolean identifyStatus(Long launchId) {
		return dsl.fetchExists(dsl.selectOne()
				.from(TEST_ITEM)
				.join(TEST_ITEM_RESULTS)
				.on(TEST_ITEM.ITEM_ID.eq(TEST_ITEM_RESULTS.RESULT_ID))
				.where(TEST_ITEM.LAUNCH_ID.eq(launchId)
						.and(TEST_ITEM_RESULTS.STATUS.eq(JStatusEnum.FAILED).or(TEST_ITEM_RESULTS.STATUS.eq(JStatusEnum.SKIPPED)))));
	}

	public List<Launch> findByFilter(Filter filter) {
		return LAUNCH_FETCHER.apply(dsl.fetch(QueryBuilder.newBuilder(filter).build()));
	}

	public Page<Launch> findByFilter(Filter filter, Pageable pageable) {
		return PageableExecutionUtils.getPage(
				LAUNCH_FETCHER.apply(dsl.fetch(QueryBuilder.newBuilder(filter).with(pageable).build())),
				pageable,
				() -> dsl.fetchCount(QueryBuilder.newBuilder(filter).build())
		);
	}

	@Override
	public List<String> getLaunchNames(Long projectId, String value, LaunchModeEnum mode) {

		JLaunch l = LAUNCH.as("l");
		JProject p = PROJECT.as("p");

		return dsl.select().from(l).leftJoin(p).on(l.PROJECT_ID.eq(p.ID)).where(p.ID.eq(projectId)).and(l.NAME.like(value)).fetch(l.NAME);
	}

	@Override
	public List<String> getOwnerNames(Long projectId, String value, String mode) {

		JLaunch l = LAUNCH.as("l");
		JProject p = PROJECT.as("p");
		JUsers u = USERS.as("u");

		return dsl.selectDistinct()
				.from(l)
				.leftJoin(p)
				.on(l.PROJECT_ID.eq(p.ID))
				.leftJoin(u)
				.on(l.USER_ID.eq(u.ID))
				.where(p.ID.eq(projectId))
				.and(u.FULL_NAME.like("%" + value + "%"))
				.and(l.MODE.eq(JLaunchModeEnum.valueOf(mode)))
				.fetch(u.FULL_NAME);
	}

	@Override
	public Map<String, String> getStatuses(Long projectId, Long[] ids) {

		JLaunch l = LAUNCH.as("l");
		JProject p = PROJECT.as("p");

		//		return dsl.select()
		//				.from(l)
		//				.leftJoin(p)
		//				.on(l.PROJECT_ID.eq(p.ID))
		//				.where(p.ID.eq(projectId))
		//				.and(l.ID.in(ids))
		//				.fetch(LAUNCH_MAPPER)
		//				.stream()
		//				.collect(Collectors.toMap(launch -> String.valueOf(launch.getId()), launch -> launch.getStatus().toString()));
		return Collections.emptyMap();
	}

	@Override
	public Optional<Launch> findLatestByNameAndFilter(String launchName, Filter filter) {
		return Optional.ofNullable(dsl.with(LAUNCHES)
				.as(QueryBuilder.newBuilder(filter).build())
				.select()
				.distinctOn(LAUNCH.NAME)
				.from(LAUNCH)
				.where(LAUNCH.NAME.eq(launchName))
				.orderBy(LAUNCH.NAME, LAUNCH.NUMBER.desc())
				.fetchOne()
				.into(Launch.class));
	}
}
