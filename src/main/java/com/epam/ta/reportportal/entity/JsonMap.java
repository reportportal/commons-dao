/*
 *  Copyright (C) 2018 EPAM Systems
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.epam.ta.reportportal.entity;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Pavel Bortnik
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, property = "type")
public class JsonMap<K, V> extends LinkedHashMap<K, V> implements Serializable, Map<K, V> {

	public JsonMap() {
	}

	public JsonMap(Map<? extends K, ? extends V> m) {
		super(m);
	}

	public JsonMap(int initialCapacity, float loadFactor, boolean accessOrder) {
		super(initialCapacity, loadFactor, accessOrder);
	}

	public JsonMap(int initialCapacity, float loadFactor) {
		super(initialCapacity, loadFactor);
	}

	public JsonMap(int initialCapacity) {
		super(initialCapacity);
	}

	public static <K, V> JsonMap<K, V> ofMap(Map<K, V> map) {
		return new JsonMap<>(map);
	}
}
