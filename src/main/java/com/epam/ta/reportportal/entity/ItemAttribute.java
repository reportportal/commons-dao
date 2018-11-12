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

package com.epam.ta.reportportal.entity;

import javax.persistence.*;
import java.util.Objects;

/**
 * @author <a href="mailto:ihar_kahadouski@epam.com">Ihar Kahadouski</a>
 */
@Entity
@Table(name = "item_attribute")
public class ItemAttribute {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "key")
	private String key;

	@Column(name = "value")
	private String value;

	@Column(name = "item_id")
	private Long itemId;

	@Column(name = "launch_id")
	private Long launchId;

	public ItemAttribute() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public Long getLaunchId() {
		return launchId;
	}

	public void setLaunchId(Long launchId) {
		this.launchId = launchId;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		ItemAttribute that = (ItemAttribute) o;
		return Objects.equals(id, that.id) && Objects.equals(key, that.key) && Objects.equals(value, that.value) && Objects.equals(itemId,
				that.itemId
		) && Objects.equals(launchId, that.launchId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, key, value, itemId, launchId);
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("ItemAttribute{");
		sb.append("id=").append(id);
		sb.append(", key='").append(key).append('\'');
		sb.append(", value='").append(value).append('\'');
		sb.append(", itemId=").append(itemId);
		sb.append(", launchId=").append(launchId);
		sb.append('}');
		return sb.toString();
	}
}
