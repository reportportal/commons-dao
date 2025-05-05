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

package com.epam.ta.reportportal.config;

import com.epam.ta.reportportal.filesystem.DataEncoder;
import com.epam.ta.reportportal.util.FeatureFlagHandler;
import java.util.Set;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.quartz.QuartzAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@EnableConfigurationProperties
@EnableAutoConfiguration(exclude = QuartzAutoConfiguration.class)
@ComponentScan(basePackages = "com.epam.ta.reportportal")
@PropertySource({"classpath:application.yml"})
public class TestConfiguration {

  @Bean
  public FeatureFlagHandler featureFlagHandler(
      @Value("#{'${rp.feature.flags}'.split(',')}") Set<String> featureFlagsSet) {
    return new FeatureFlagHandler(featureFlagsSet);
  }

  @Bean
  public DataEncoder dataEncoder() {
    return new DataEncoder();
  }


}
