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

package com.epam.ta.reportportal.entity.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:ihar_kahadouski@epam.com">Ihar Kahadouski</a>
 */
public class EnumTestHelper {

	private EnumTestHelper() {
		//staticOnly
	}

	public static List<String> permute(String input) {
		List<String> result = new ArrayList<>();
		int length = input.length();
		int max = 1 << length;

		input = input.toLowerCase();

		for (int i = 0; i < max; i++) {
			char combination[] = input.toCharArray();

			for (int j = 0; j < length; j++) {
				if (((i >> j) & 1) == 1) {
					if (Character.isAlphabetic(combination[j])) {
						combination[j] = (char) (combination[j] - 32);
					}
				}
			}
			result.add(new String(combination));
			if (result.size() >= 1000) {
				return result;
			}
		}
		return result;
	}
}
