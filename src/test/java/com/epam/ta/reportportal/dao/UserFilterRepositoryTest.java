/*
 *  Copyright (C) 2018 EPAM Systems
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.epam.ta.reportportal.dao;

import com.epam.ta.reportportal.commons.querygen.Filter;
import com.epam.ta.reportportal.commons.querygen.FilterCondition;
import com.epam.ta.reportportal.config.TestConfiguration;
import com.epam.ta.reportportal.config.util.SqlRunner;
import com.epam.ta.reportportal.entity.filter.UserFilter;
import com.google.common.collect.Sets;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Set;
import org.hsqldb.cmdline.SqlToolError;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Ivan Nikitsenka
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TestConfiguration.class)
@Transactional("transactionManager")
public class UserFilterRepositoryTest {

    @BeforeClass
    public static void init()
        throws SQLException, ClassNotFoundException, IOException, SqlToolError {
        Class.forName("org.hsqldb.jdbc.JDBCDriver");
        //TODO this scripts should be syncronized with migration scripts
        runSqlScript("/test-dropall-script.sql");
        runSqlScript("/test-create-script.sql");
        //Create test data required for only this test
        runSqlScript("/test-fill-user-filters-script.sql");
    }

    @AfterClass
    public static void destroy() throws SQLException, IOException, SqlToolError {
        runSqlScript("/test-dropall-script.sql");
    }

    private static void runSqlScript(String scriptPath)
        throws SQLException, IOException, SqlToolError {
        try (Connection connection = getConnection()) {
            new SqlRunner().runSqlScript(connection, scriptPath);
        }
    }

    @Autowired
    private UserFilterRepositoryCustom userFilterRepository;


    private static Connection getConnection() throws SQLException {
        return DriverManager
            .getConnection("jdbc:postgresql://localhost:5432/reportportal", "rpuser", "rppass");
    }

    @Test
    public void getSharedFilters() {
        Page<UserFilter> result1 = userFilterRepository
            .getSharedFilters(1l, buildDefaultFilter(), PageRequest.of(0, 3), "superadmin");

        Assert.assertEquals(2l, result1.getTotalElements());
        Assert.assertEquals(Long.valueOf(1l), result1.get().findFirst().get().getId());

        Page<UserFilter> result2 = userFilterRepository
            .getSharedFilters(1l, buildDefaultFilter(), PageRequest.of(0, 3), "default");

        Assert.assertEquals(0l, result2.getTotalElements());
    }


    @Test
    public void getPermittedFilters() {
        Page<UserFilter> result1 = userFilterRepository
            .getPermittedFilters(1l, buildDefaultFilter(), PageRequest.of(0, 3), "superadmin");

        Assert.assertEquals(2l, result1.getTotalElements());
        Assert.assertEquals(Long.valueOf(1l), result1.get().findFirst().get().getId());

        Page<UserFilter> result2 = userFilterRepository
            .getPermittedFilters(1l, buildDefaultFilter(), PageRequest.of(0, 3), "default");

        Assert.assertEquals(3l, result2.getTotalElements());
        Assert.assertEquals(Long.valueOf(1l), result2.get().findFirst().get().getId());
    }

    @Test
    public void getOwnFilters() {
        Page<UserFilter> result1 = userFilterRepository
            .getOwnFilters(1l, buildDefaultFilter(), PageRequest.of(0, 3), "superadmin");

        Assert.assertEquals(0l, result1.getTotalElements());

        Page<UserFilter> result2 = userFilterRepository
            .getOwnFilters(1l, buildDefaultFilter(), PageRequest.of(0, 3), "default");

        Assert.assertEquals(3l, result2.getTotalElements());
        Assert.assertEquals(Long.valueOf(1l), result2.get().findFirst().get().getId());
    }

    @Test
    public void getSharedFiltersPaging() {
        Page<UserFilter> result1 = userFilterRepository
            .getSharedFilters(1l, buildDefaultFilter(), PageRequest.of(0, 1), "superadmin");

        Assert.assertEquals(1l, result1.getTotalElements());
        Assert.assertEquals(Long.valueOf(1l), result1.get().findFirst().get().getId());
    }


    private Filter buildDefaultFilter() {
        Set<FilterCondition> conditionSet = Sets.newHashSet();
        return new Filter(2L, UserFilter.class, conditionSet);
    }

}