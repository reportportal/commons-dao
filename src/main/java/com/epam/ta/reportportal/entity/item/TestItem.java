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

package com.epam.ta.reportportal.entity.item;

import com.epam.ta.reportportal.entity.enums.PostgreSQLEnumType;
import com.epam.ta.reportportal.entity.enums.TestItemTypeEnum;
import com.epam.ta.reportportal.entity.launch.Launch;
import com.epam.ta.reportportal.entity.log.Log;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.google.common.collect.Sets;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

/**
 * @author Pavel Bortnik
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
@TypeDef(name = "pgsql_enum", typeClass = PostgreSQLEnumType.class)
@Table(name = "test_item", schema = "public")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class TestItem implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "item_id")
	private Long itemId;

	@Column(name = "name", length = 256)
	private String name;

	@Enumerated(EnumType.STRING)
	@Type(type = "pqsql_enum")
	@Column(name = "type", nullable = false)
	private TestItemTypeEnum type;

	@Column(name = "start_time", nullable = false)
	private LocalDateTime startTime;

	@Column(name = "description")
	private String description;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "launch_id")
	@JsonIgnore
	private Launch launch;

	@LastModifiedDate
	@Column(name = "last_modified", nullable = false)
	private LocalDateTime lastModified;

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "parameter", joinColumns = @JoinColumn(name = "item_id"))
	private List<Parameter> parameters;

	@Column(name = "unique_id", nullable = false, length = 256)
	private String uniqueId;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	@JoinColumn(name = "item_id")
	@JsonIgnore
	private Set<TestItemTag> tags = Sets.newHashSet();


	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	@JoinColumn(name = "item_id")
	@JsonIgnore
	private Set<Log> logs = Sets.newHashSet();

	@Column(name = "path", nullable = false, columnDefinition = "ltree")
	@Type(type = "com.epam.ta.reportportal.entity.LTreeType")
	private String path;

	@Column(name = "retry_of", precision = 64)
	private Long retryOf;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_id")
	@JsonIgnore
	private TestItem parent;

	@OneToOne(cascade = CascadeType.ALL, mappedBy = "testItem")
	@JsonManagedReference("itemResults")
	private TestItemResults itemResults;

	@Column(name = "has_children")
	private boolean hasChildren;

	public TestItem() {
	}

	public TestItem(Long id) {
		this.itemId = id;
	}

	public TestItem(Long itemId, String name, TestItemTypeEnum type, LocalDateTime startTime, String description,
			LocalDateTime lastModified, String uniqueId, Boolean hasChildren) {
		this.itemId = itemId;
		this.name = name;
		this.type = type;
		this.startTime = startTime;
		this.description = description;
		this.lastModified = lastModified;
		this.uniqueId = uniqueId;
		this.hasChildren = hasChildren;
	}

	public Set<TestItemTag> getTags() {
		return tags;
	}

	public void setTags(Set<TestItemTag> tags) {
		this.tags.clear();
		this.tags.addAll(tags);
	}

	public Set<Log> getLogs() {
		return logs;
	}

	public void setLogs(Set<Log> logs) {
		this.logs.clear();
		this.logs.addAll(logs);
	}

	public void addLog(Log log) {
		logs.add(log);
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public TestItemTypeEnum getType() {
		return type;
	}

	public void setType(TestItemTypeEnum type) {
		this.type = type;
	}

	public LocalDateTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}

	public LocalDateTime getLastModified() {
		return lastModified;
	}

	public void setLastModified(LocalDateTime lastModified) {
		this.lastModified = lastModified;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Parameter> getParameters() {
		return parameters;
	}

	public void setParameters(List<Parameter> parameters) {
		this.parameters = parameters;
	}

	public String getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}

	public Launch getLaunch() {
		return launch;
	}

	public void setLaunch(Launch launch) {
		this.launch = launch;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Long getRetryOf() {
		return retryOf;
	}

	public void setRetryOf(Long retryOf) {
		this.retryOf = retryOf;
	}

	public TestItem getParent() {
		return parent;
	}

	public void setParent(TestItem parent) {
		this.parent = parent;
	}

	public TestItemResults getItemResults() {
		return itemResults;
	}

	public void setItemResults(TestItemResults itemResults) {
		this.itemResults = itemResults;
	}

	public boolean isHasChildren() {
		return hasChildren;
	}

	public void setHasChildren(boolean hasChildren) {
		this.hasChildren = hasChildren;
	}
}
