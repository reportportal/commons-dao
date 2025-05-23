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

package com.epam.ta.reportportal.entity.enums.converter;

import com.epam.ta.reportportal.entity.enums.LogLevel;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * @author Pavel Bortnik
 */
@Converter(autoApply = true)
public class LogLevelConverter implements AttributeConverter<LogLevel, Integer> {

  @Override
  public Integer convertToDatabaseColumn(LogLevel attribute) {
    return attribute.toInt();
  }

  @Override
  public LogLevel convertToEntityAttribute(Integer dbData) {
    return LogLevel.toLevel(dbData);
  }
}
