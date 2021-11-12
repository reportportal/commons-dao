/*
 * Copyright 2021 EPAM Systems
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

import com.epam.ta.reportportal.entity.onboarding.Onboarding;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.Instant;

import static com.epam.ta.reportportal.jooq.tables.JOnboarding.ONBOARDING;

/**
 * @author Antonov Maksim
 */
@Repository
public class OnboardingRepository {

    private final DSLContext dsl;

    public OnboardingRepository(DSLContext dsl) {
        this.dsl = dsl;
    }

    public Onboarding findAvailableOnboardingByPage(String page) {
        return dsl.select(ONBOARDING.fields())
                .from(ONBOARDING)
                .where(ONBOARDING.PAGE.eq(page))
                .and(ONBOARDING.AVAILABLE_TO.ge(Timestamp.from(Instant.now())))
                .fetchOneInto(Onboarding.class);
    }
}
