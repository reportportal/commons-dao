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

import com.epam.ta.reportportal.entity.ItemAttribute;
import com.epam.ta.reportportal.entity.enums.PostgreSQLEnumType;
import com.epam.ta.reportportal.entity.enums.TestItemTypeEnum;
import com.epam.ta.reportportal.entity.log.Log;
import com.epam.ta.reportportal.entity.pattern.PatternTemplateTestItem;
import com.google.common.collect.Sets;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

/**
 * @author Pavel Bortnik
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
@TypeDef(name = "pgsql_enum", typeClass = PostgreSQLEnumType.class)
@Table(name = "test_item", schema = "public")
public class TestItem implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "item_id")
	private Long itemId;

	@Column(name = "uuid")
	private String uuid;

	@Column(name = "name", length = 256)
	private String name;

	@Column(name = "code_ref")
	private String codeRef;

	@Enumerated(EnumType.STRING)
	@Type(type = "pqsql_enum")
	@Column(name = "type", nullable = false)
	private TestItemTypeEnum type;

	@Column(name = "start_time", nullable = false)
	private LocalDateTime startTime;

	@Column(name = "description")
	private String description;

	@Column(name = "launch_id", nullable = false)
	private Long launchId;

	@LastModifiedDate
	@Column(name = "last_modified", nullable = false)
	private LocalDateTime lastModified;

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "parameter", joinColumns = @JoinColumn(name = "item_id"))
	@Fetch(FetchMode.SUBSELECT)
	private Set<Parameter> parameters = Sets.newHashSet();

	@Column(name = "unique_id", nullable = false, length = 256)
	private String uniqueId;

	@Column(name = "test_case_id")
	private String testCaseId;

	@Column(name = "test_case_hash")
	private Integer testCaseHash;

	@OneToMany(mappedBy = "testItem", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
	@Fetch(FetchMode.SUBSELECT)
	private Set<ItemAttribute> attributes = Sets.newHashSet();

	@OneToMany(mappedBy = "testItem", fetch = FetchType.LAZY, orphanRemoval = true)
	private Set<Log> logs = Sets.newHashSet();

	@Column(name = "path", nullable = false, columnDefinition = "ltree")
	@Type(type = "com.epam.ta.reportportal.entity.LTreeType")
	private String path;

	@Column(name = "retry_of", precision = 64)
	private Long retryOf;

	@Column(name = "parent_id")
	private Long parentId;

	@OneToOne(cascade = CascadeType.ALL, mappedBy = "testItem")
	private TestItemResults itemResults;

	@OneToMany(mappedBy = "testItem", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@OrderBy(value = "pattern_id")
	@Fetch(FetchMode.SUBSELECT)
	private Set<PatternTemplateTestItem> patternTemplateTestItems = Sets.newLinkedHashSet();

	@Column(name = "has_children")
	private boolean hasChildren;

	@Column(name = "has_retries")
	private boolean hasRetries;

	@Column(name = "has_stats")
	private boolean hasStats;

	public TestItem() {
	}

	public TestItem(Long id) {
		this.itemId = id;
	}

	public TestItem(Long itemId, String name, TestItemTypeEnum type, LocalDateTime startTime, String description,
			LocalDateTime lastModified, String uniqueId, boolean hasChildren, boolean hasRetries, boolean hasStats) {
		this.itemId = itemId;
		this.name = name;
		this.type = type;
		this.startTime = startTime;
		this.description = description;
		this.lastModified = lastModified;
		this.uniqueId = uniqueId;
		this.hasChildren = hasChildren;
		this.hasRetries = hasRetries;
	}

	public Set<ItemAttribute> getAttributes() {
		return attributes;
	}

	public void setAttributes(Set<ItemAttribute> tags) {
		this.attributes.clear();
		this.attributes.addAll(tags);
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

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCodeRef() {
		return codeRef;
	}

	public void setCodeRef(String codeRef) {
		this.codeRef = codeRef;
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

	public Set<Parameter> getParameters() {
		return parameters;
	}

	public void setParameters(Set<Parameter> parameters) {
		this.parameters = parameters;
	}

	public String getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}

	public String getTestCaseId() {
		return testCaseId;
	}

	public void setTestCaseId(String testCaseId) {
		this.testCaseId = testCaseId;
	}

	public Integer getTestCaseHash() {
		return testCaseHash;
	}

	public void setTestCaseHash(Integer testCaseHash) {
		this.testCaseHash = testCaseHash;
	}

	public Long getLaunchId() {
		return launchId;
	}

	public void setLaunchId(Long launchId) {
		this.launchId = launchId;
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

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
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

	public Set<PatternTemplateTestItem> getPatternTemplateTestItems() {
		return patternTemplateTestItems;
	}

	public void setPatternTemplateTestItems(Set<PatternTemplateTestItem> patternTemplateTestItems) {
		this.patternTemplateTestItems = patternTemplateTestItems;
	}

	public boolean isHasRetries() {
		return hasRetries;
	}

	public void setHasRetries(boolean hasRetries) {
		this.hasRetries = hasRetries;
	}

	public boolean isHasStats() {
		return hasStats;
	}

	public void setHasStats(boolean hasStats) {
		this.hasStats = hasStats;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		TestItem testItem = (TestItem) o;
		return Objects.equals(itemId, testItem.itemId) && Objects.equals(name, testItem.name) && Objects.equals(codeRef, testItem.codeRef)
				&& type == testItem.type && Objects.equals(uniqueId, testItem.uniqueId) && Objects.equals(testCaseId, testItem.testCaseId)
				&& Objects.equals(testCaseHash, testItem.testCaseHash) && Objects.equals(path, testItem.path) && Objects.equals(retryOf,
				testItem.retryOf
		);
	}

	@Override
	public int hashCode() {
		return Objects.hash(itemId, name, codeRef, type, uniqueId, testCaseId, testCaseHash, path, retryOf);
	}
}
