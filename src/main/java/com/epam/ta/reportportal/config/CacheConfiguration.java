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

import static java.util.Arrays.asList;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.core.RedisTemplate;

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

	@Autowired
	private RedisTemplate redisTemplate;

	public static final String USERS_CACHE = "usersCache";

	public static final String ASSIGNED_USERS_CACHE = "assignedUsersCache";

	public static final String EXTERNAL_SYSTEM_TICKET_CACHE = "extSystemTicketCache";
	public static final String EXTERNAL_SYSTEM_TICKET_CACHE_KEY = "#system.url + #system.project + #id";

	public static final String EXTERNAL_SYSTEM_RELATED_TICKET_CACHE = "extSystemRelatedTicketCache";

	public static final String JIRA_PROJECT_CACHE = "jiraProjectCache";

	public static final String PROJECT_INFO_CACHE = "projectInfoCache";

	/**
	 * Global Cache Manager
	 */
	@Bean
	public CacheManager getGlobalCacheManager() {

		RedisCacheManager redisCacheManager = new RedisCacheManager(redisTemplate);
		redisCacheManager.setExpires(new HashMap<String, Long>() {
			{
				put(EXTERNAL_SYSTEM_TICKET_CACHE, ticketCacheSize * 60);
				put(JIRA_PROJECT_CACHE, projectCacheExpiration * 60 * 60 * 24);
				put(USERS_CACHE, userCacheExpiration * 60);
				put(PROJECT_INFO_CACHE, projectCacheSize * 60);
				put(ASSIGNED_USERS_CACHE, userCacheExpiration * 60);
			}
		});
		redisCacheManager.setDefaultExpiration(60 * 60);
		redisCacheManager.setCacheNames(
				asList(EXTERNAL_SYSTEM_TICKET_CACHE, JIRA_PROJECT_CACHE, USERS_CACHE, PROJECT_INFO_CACHE, ASSIGNED_USERS_CACHE));
		return redisCacheManager;
	}

}