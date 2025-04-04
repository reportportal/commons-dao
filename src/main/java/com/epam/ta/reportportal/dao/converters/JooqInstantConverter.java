/*
 * Copyright 2024 EPAM Systems
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

package com.epam.ta.reportportal.dao.converters;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import org.jooq.impl.AbstractConverter;

public class JooqInstantConverter extends AbstractConverter<LocalDateTime, Instant> {

  public JooqInstantConverter() {
    super(LocalDateTime.class, Instant.class);
  }

  @Override
  public Instant from(LocalDateTime localDateTime) {
    return localDateTime == null ? null : localDateTime.atOffset(ZoneOffset.UTC).toInstant();
  }

  @Override
  public LocalDateTime to(Instant instant) {
    return instant == null ? null : LocalDateTime.ofInstant(instant, ZoneOffset.UTC);
  }

}
