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

package com.epam.ta.reportportal.commons;

import com.epam.ta.reportportal.commons.querygen.FilterCondition;
import com.epam.ta.reportportal.entity.enums.StatusEnum;
import com.epam.ta.reportportal.entity.project.ProjectRole;
import com.epam.ta.reportportal.ws.model.ErrorType;
import com.epam.ta.reportportal.ws.model.launch.Mode;
import org.apache.commons.lang3.ArrayUtils;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.StreamSupport;

import static com.epam.ta.reportportal.commons.EntityUtils.TO_LOCAL_DATE_TIME;
import static com.epam.ta.reportportal.commons.querygen.constant.LaunchCriteriaConstant.CRITERIA_LAUNCH_MODE;

/**
 * Several validation checks
 *
 * @author Andrei Varabyeu
 */
public class Preconditions {

	private Preconditions() {

	}

	/**
	 * grabbed from {@link UUID#fromString(String)}
	 */
	public static final Predicate<String> IS_UUID = uuid -> uuid.split("-").length == 5;

	public static final Predicate<Collection<?>> NOT_EMPTY_COLLECTION = t -> null != t && !t.isEmpty();

	public static final Predicate<Optional<?>> IS_PRESENT = Optional::isPresent;

	public static Predicate<Date> sameTimeOrLater(final LocalDateTime than) {
		com.google.common.base.Preconditions.checkNotNull(than, ErrorType.BAD_REQUEST_ERROR);
		return date -> {
			LocalDateTime localDateTime = TO_LOCAL_DATE_TIME.apply(date);
			return localDateTime.isAfter(than) || localDateTime.isEqual(than);
		};
	}

	public static Predicate<StatusEnum> statusIn(final StatusEnum... statuses) {
		return input -> ArrayUtils.contains(statuses, input);
	}

	public static final Predicate<FilterCondition> HAS_ANY_MODE = hasMode(null);

	public static Predicate<FilterCondition> hasMode(final Mode mode) {
		return condition -> (CRITERIA_LAUNCH_MODE.equalsIgnoreCase(condition.getSearchCriteria())) && (mode == null || mode.name()
				.equalsIgnoreCase(condition.getValue()));
	}

	/**
	 * Checks whether iterable contains elements matchers provided predicate
	 *
	 * @param filter
	 * @return
	 */
	public static <T> Predicate<Iterable<T>> contains(final Predicate<T> filter) {
		return iterable -> StreamSupport.stream(iterable.spliterator(), false).anyMatch(filter);
	}

	/**
	 * Checks whether map contains provided key
	 *
	 * @param key
	 * @return
	 */
	public static <K> Predicate<Map<K, ?>> containsKey(final K key) {
		return map -> null != map && map.containsKey(key);
	}

	/**
	 * Check whether user (principal) has enough role level
	 *
	 * @param principalRole
	 * @return
	 */
	public static Predicate<ProjectRole> isLevelEnough(final ProjectRole principalRole) {
		return principalRole::sameOrHigherThan;
	}
}
