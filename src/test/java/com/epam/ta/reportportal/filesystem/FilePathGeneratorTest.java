/*
 * Copyright (C) 2018 EPAM Systems
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

package com.epam.ta.reportportal.filesystem;

import com.epam.ta.reportportal.util.DateTimeProvider;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.File;
import java.time.LocalDateTime;

import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.when;

public class FilePathGeneratorTest {

	private FilePathGenerator filePathGenerator;

	private DateTimeProvider dateTimeProvider;

	private static final String SEPARATOR = "\\" + File.separator;

	@Before
	public void setUp() throws Exception {

		dateTimeProvider = Mockito.mock(DateTimeProvider.class);

		filePathGenerator = new FilePathGenerator(dateTimeProvider);
	}

	@Test
	public void generate_different_even_for_same_date() {

		//		given:
		LocalDateTime date = LocalDateTime.of(2018, 5, 28, 3, 3);
		when(dateTimeProvider.localDateTimeNow()).thenReturn(date);

		//		when:
		String pathOne = new FilePathGenerator(dateTimeProvider).generate();
		String pathTwo = new FilePathGenerator(dateTimeProvider).generate();

		//		then:
		assertNotEquals(pathOne, pathTwo);

		final String regex =
				"^" + date.getDayOfYear() + SEPARATOR + "\\w{2}" + SEPARATOR + "\\w{2}" + SEPARATOR + "\\w{2}" + SEPARATOR + ".*$";

		Assertions.assertThat(pathOne).matches(regex);
		Assertions.assertThat(pathTwo).matches(regex);
	}
}
