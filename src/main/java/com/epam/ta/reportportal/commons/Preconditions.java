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
 
package com.epam.ta.reportportal.commons;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.StreamSupport;


import org.apache.commons.lang3.ArrayUtils;

import com.epam.ta.reportportal.database.entity.HasStatus;
import com.epam.ta.reportportal.database.entity.Launch;
import com.epam.ta.reportportal.database.entity.ProjectRole;
import com.epam.ta.reportportal.database.entity.Project.UserConfig;
import com.epam.ta.reportportal.database.entity.Status;
import com.epam.ta.reportportal.database.entity.item.TestItem;
import com.epam.ta.reportportal.database.entity.item.TestItemType;
import com.epam.ta.reportportal.database.entity.sharing.Acl;
import com.epam.ta.reportportal.database.entity.sharing.AclEntry;
import com.epam.ta.reportportal.database.entity.sharing.AclPermissions;
import com.epam.ta.reportportal.database.search.FilterCondition;
import com.epam.ta.reportportal.ws.model.FinishExecutionRQ;
import com.epam.ta.reportportal.ws.model.StartTestItemRQ;
import com.epam.ta.reportportal.ws.model.launch.Mode;

/**
 * Several validation checks
 * 
 * @author Andrei Varabyeu
 * 
 */
public class Preconditions {

	private Preconditions() {

	}

	/** grabbed from {@link UUID#fromString(String)} */
	public static final Predicate<String> IS_UUID = uuid -> uuid.split("-").length == 5;

	public static final Predicate<Collection<?>> NOT_EMPTY_COLLECTION = t -> null != t && !t.isEmpty();

	public static final Predicate<Optional<?>> IS_PRESENT = Optional::isPresent;

	public static final Predicate<HasStatus> IN_PROGRESS = hasStatus -> Status.IN_PROGRESS.equals(hasStatus.getStatus());

	public static final Predicate<Launch> LAUNCH_FINISHED = launch -> null != launch.getEndTime();

	public static final Predicate<TestItem> TEST_ITEM_FINISHED = item -> null != item.getEndTime();

	public static final Predicate<List<TestItem>> HAS_IN_PROGRESS_ITEMS = descendants -> descendants.stream()
			.anyMatch(descendant -> Status.IN_PROGRESS.equals(descendant.getStatus()));
 
	public static Predicate<AclEntry> hasACLPermission(final String projectId, final AclPermissions permissions) {
		return input -> input != null && input.getProjectId().equals(projectId) && (permissions == null || input.getPermissions()
				.contains(permissions));
	}
	
	public static Predicate<Acl> isOwner(final String ownerId) {
		return input -> ownerId.equals(input.getOwnerUserId());
	}
	
	public static Predicate<Acl> isSharedTo(final String projectName) {
		return input -> input.getEntries().stream().anyMatch(hasACLPermission(projectName, AclPermissions.READ));
	}

	public static final Predicate<FilterCondition> HAS_ANY_MODE = hasMode(null);

	public static Predicate<FilterCondition> hasMode(final Mode mode) {
		return condition -> (Launch.MODE_CRITERIA.equalsIgnoreCase(condition.getSearchCriteria())) && (mode == null || mode.name()
				.equalsIgnoreCase(condition.getValue()));
	}

	public static Predicate<FinishExecutionRQ> finishSameTimeOrLater(final Date startTime) {
		return input -> input.getEndTime().getTime() >= startTime.getTime();
	}

	/**
	 * Start time of item to be creates is later than provided start time
	 * 
	 * @param startTime
	 * @return
	 */
	public static Predicate<StartTestItemRQ> startSameTimeOrLater(final Date startTime) {
		return input -> input.getStartTime() != null && input.getStartTime().getTime() >= startTime.getTime();
	}

	public static Predicate<Status> statusIn(final Status... statuses) {
		return input -> ArrayUtils.contains(statuses, input);
	}

	public static Predicate<TestItem> hasSameParent(final String parentId) {
		return input -> parentId.equals(input.getParent());
	}

	public static Predicate<TestItem> hasSameLaunch(final String launchId) {
		return input -> launchId.equals(input.getLaunchRef());
	}
	
	/**
	 * {@link UserConfig} should have one of input roles.
	 * @param roles
	 * @return
	 */
	public static Predicate<UserConfig> hasProjectRoles(final List<ProjectRole> roles) {
		return input -> roles.contains(input.getProjectRole());
	}

	public static final Predicate<TestItem> IS_SUITE = itemType(TestItemType.SUITE);

	public static Predicate<TestItem> itemType(final TestItemType itemType) {
		return testItem -> itemType.equals(testItem.getType());
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
