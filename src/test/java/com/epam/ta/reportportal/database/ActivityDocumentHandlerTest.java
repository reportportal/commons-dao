/*
 * Copyright 2017 EPAM Systems
 *
 *
 * This file is part of EPAM Report Portal.
 * https://github.com/reportportal/service-dao
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
package com.epam.ta.reportportal.database;

import com.epam.ta.reportportal.ws.model.widget.ChartObject;
import com.google.common.collect.ImmutableMap;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Date;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Map;

/**
 * @author Pavel Bortnik
 */
public class ActivityDocumentHandlerTest {
    @Test
    public void processDocument() throws Exception {
        ActivityDocumentHandler documentHandler = new ActivityDocumentHandler();
        documentHandler.processDocument(dbObject());
        ChartObject result = documentHandler.getResult().get(0);
        ChartObject expectedResult = expectedResult();
        Assert.assertEquals(expectedResult.getId(), result.getId());
        Assert.assertEquals(expectedResult.getValues(), result.getValues());
    }

    private ChartObject expectedResult() {
        ChartObject chartObject = new ChartObject();
        chartObject.setId("599feab9f2a28708ed6afdc3");
        ImmutableMap<String, String> results = ImmutableMap.<String, String>builder()
                .put("actionType", "update_project")
                .put("userRef", "default")
                .put("keepScreenshots$oldValue", "1 week")
                .put("keepScreenshots$newValue", "2 weeks")
                .put("last_modified", "1502165103000")
                .put("projectRef", "default_personal")
                .put("objectType", "project")
                .put("name", "default_personal")
                .build();
        chartObject.setValues(results);
        return chartObject;
    }
    
    private DBObject dbObject() {
        DBObject dbObject = new BasicDBObject();
        dbObject.put("_id" , "599feab9f2a28708ed6afdc3");
        dbObject.put("userRef" , "default");
        dbObject.put("last_modified" , Date.from(Instant.ofEpochMilli(1502165103000L)));
        dbObject.put("objectType" , "project");
        dbObject.put("actionType" , "update_project");
        dbObject.put("projectRef" , "default_personal");
        dbObject.put("name", "default_personal");
        BasicDBList history = new BasicDBList();
        Map innerHistoryObject = ImmutableMap.<String, String>builder()
                .put("field", "keepScreenshots")
                .put("oldValue", "1 week")
                .put("newValue", "2 weeks").build();
        history.add(innerHistoryObject);
        dbObject.put("history", history);
        return dbObject;
    }

}
