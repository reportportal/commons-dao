/*
 * Copyright 2023 EPAM Systems
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

package com.epam.ta.reportportal.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalQueries;

/**
 * Utility class for date and time
 *
 * @author Siarhei Hrabko
 */
public final class DateTimeUtils {

  private DateTimeUtils() {
  }

  private LocalDateTime parseDateTimeWithOffset(String timestamp) {
    DateTimeFormatter formatter = new DateTimeFormatterBuilder()
        .appendOptional(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZ"))
        .appendOptional(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ"))
        .appendOptional(DateTimeFormatter.RFC_1123_DATE_TIME)
        .appendOptional(DateTimeFormatter.ISO_OFFSET_DATE_TIME)
        .appendOptional(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        .toFormatter();

    TemporalAccessor temporalAccessor = formatter.parse(timestamp);
    if (isParsedTimeStampHasOffset(temporalAccessor)) {
      return ZonedDateTime.from(temporalAccessor)
          .withZoneSameInstant(ZoneId.systemDefault())
          .toLocalDateTime();
    } else {
      return LocalDateTime.from(temporalAccessor);
    }
  }

  private boolean isParsedTimeStampHasOffset(TemporalAccessor temporalAccessor) {
    return temporalAccessor.query(TemporalQueries.offset()) != null;
  }

}
