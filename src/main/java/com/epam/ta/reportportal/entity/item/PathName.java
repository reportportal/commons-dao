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

package com.epam.ta.reportportal.entity.item;

import java.io.Serializable;
import java.util.Map;

/**
 * @author <a href="mailto:ivan_budayeu@epam.com">Ivan Budayeu</a>
 */
public class PathName implements Serializable {

	private LaunchPathName launchPathName;
	private Map<Long, String> itemPaths;

	public PathName() {
	}

	public PathName(LaunchPathName launchPathName, Map<Long, String> itemPaths) {
		this.launchPathName = launchPathName;
		this.itemPaths = itemPaths;
	}

	public LaunchPathName getLaunchPathName() {
		return launchPathName;
	}

	public void setLaunchPathName(LaunchPathName launchPathName) {
		this.launchPathName = launchPathName;
	}

	public Map<Long, String> getItemPaths() {
		return itemPaths;
	}

	public void setItemPaths(Map<Long, String> itemPaths) {
		this.itemPaths = itemPaths;
	}
}
