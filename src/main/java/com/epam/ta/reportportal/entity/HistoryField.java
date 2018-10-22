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

package com.epam.ta.reportportal.entity;

import java.io.Serializable;

/**
 * @author Ihar Kahadouski
 */
public class HistoryField implements Serializable {
	private String field;
	private String oldValue;
	private String newValue;

	private HistoryField(String field, String oldValue, String newValue) {
		this.field = field;
		this.oldValue = oldValue;
		this.newValue = newValue;
	}

	private HistoryField() {
	}

	public String getField() {
		return field;
	}

	public String getOldValue() {
		return oldValue;
	}

	public String getNewValue() {
		return newValue;
	}

	public static HistoryField of(String field, String oldValue, String newValue) {
		return new HistoryField(field, oldValue, newValue);
	}
}