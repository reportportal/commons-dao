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

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.epam.ta.reportportal.entity.AnalyzeMode;
import java.util.Arrays;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;

/**
 * @author <a href="mailto:ihar_kahadouski@epam.com">Ihar Kahadouski</a>
 */
public class AnalyzerModeConverterTest extends AttributeConverterTest {

  @BeforeEach
  void setUp() throws Exception {
    this.converter = new AnalyzerModeConverter();
    allowedValues = Arrays.stream(AnalyzeMode.values())
        .collect(Collectors.toMap(it -> it,
            it -> Arrays.asList(it.getValue(), it.getValue().toUpperCase(),
                it.getValue().toLowerCase())
        ));
  }

  @Override
  public void convertToColumnTest() {
    Arrays.stream(AnalyzeMode.values())
        .forEach(it -> assertEquals(it.getValue(), converter.convertToDatabaseColumn(it)));
  }
}