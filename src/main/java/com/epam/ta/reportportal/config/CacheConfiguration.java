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

package com.epam.ta.reportportal.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.google.common.collect.ImmutableList;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.concurrent.TimeUnit;

/**
 * Caching configuration
 *
 * @author Andrei Varabyeu
 * @author Andrei_Ramanchuk
 */
@Configuration
// Could be commented as part of workaround for SPR-9182
// But this bug closed and marked as 'cannot reproduce'
@EnableCaching(mode = AdviceMode.PROXY)
@Profile("!unittest")
public class CacheConfiguration {

	public static final String USERS_CACHE = "usersCache";
	public static final String EXTERNAL_SYSTEM_TICKET_CACHE = "extSystemTicketCache";
	public static final String JIRA_PROJECT_CACHE = "jiraProjectCache";
	public static final String PROJECT_INFO_CACHE = "projectInfoCache";


	@Value("#{new Long(${rp.cache.project.size})}")
	private long projectCacheSize;

	@Value("#{new Long(${rp.cache.ticket.size})}")
	private long ticketCacheSize;

	@Value("#{new Long(${rp.cache.user.size})}")
	private long userCacheSize;

	@Value("#{new Long(${rp.cache.project.expiration})}")
	private long projectCacheExpiration;

	@Value("#{new Long(${rp.cache.ticket.expiration})}")
	private long ticketCacheExpiration;

	@Value("#{new Long(${rp.cache.user.expiration})}")
	private long userCacheExpiration;

	@Value("#{new Long(${rp.cache.project.info})}")
	private long projectInfoCacheExpiration;

	/**
	 * Global Cache Manager
	 */
	@Bean
	public CacheManager getGlobalCacheManager() {

		SimpleCacheManager cacheManager = new SimpleCacheManager();

		//@formatter:off
		CaffeineCache tickets = new CaffeineCache(EXTERNAL_SYSTEM_TICKET_CACHE, Caffeine
				.newBuilder()
					.maximumSize(ticketCacheSize)
					.softValues()
					.expireAfterAccess(ticketCacheExpiration, TimeUnit.MINUTES)
				.build());
		CaffeineCache projects = new CaffeineCache(JIRA_PROJECT_CACHE, Caffeine
				.newBuilder()
					.maximumSize(projectCacheSize)
					.softValues()
					.expireAfterAccess(projectCacheExpiration, TimeUnit.DAYS)
				.build());
		CaffeineCache users = new CaffeineCache(USERS_CACHE, Caffeine
				.newBuilder()
					.maximumSize(userCacheSize)
					.expireAfterWrite(userCacheExpiration, TimeUnit.MINUTES)
				.build());
		CaffeineCache projectInfo = new CaffeineCache(PROJECT_INFO_CACHE, Caffeine
				.newBuilder()
					.maximumSize(projectCacheSize)
					.softValues()
					.expireAfterWrite(projectInfoCacheExpiration, TimeUnit.MINUTES)
				.build());


		cacheManager.setCaches(ImmutableList.<Cache> builder()
				.add(tickets)
				.add(projects)
				.add(users)
				.add(projectInfo)
				.build());

		//@formatter:on

		return cacheManager;
	}
}
