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

package com.epam.ta.reportportal.entity.log;

import com.epam.ta.reportportal.entity.attachment.Attachment;
import com.epam.ta.reportportal.entity.item.TestItem;
import com.epam.ta.reportportal.entity.launch.Launch;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

public class LogFull implements Serializable {

	private Long id;
	private String uuid;
	private LocalDateTime logTime;
	private String logMessage;
	private LocalDateTime lastModified;
	private Integer logLevel;
	private TestItem testItem;
	private Launch launch;
	private Long projectId;
	private Long clusterId;

	private Attachment attachment;

	public LogFull(Long id, LocalDateTime logTime, String logMessage, LocalDateTime lastModified, Integer logLevel, TestItem testItem,
				   Attachment attachment) {
		this.id = id;
		this.logTime = logTime;
		this.logMessage = logMessage;
		this.lastModified = lastModified;
		this.logLevel = logLevel;
		this.testItem = testItem;
		this.attachment = attachment;
	}

	public LogFull() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public TestItem getTestItem() {
		return testItem;
	}

	public Launch getLaunch() {
		return launch;
	}

	public void setLaunch(Launch launch) {
		this.launch = launch;
	}

	public void setTestItem(TestItem testItem) {
		this.testItem = testItem;
	}

	public LocalDateTime getLogTime() {
		return logTime;
	}

	public void setLogTime(LocalDateTime logTime) {
		this.logTime = logTime;
	}

	public LocalDateTime getLastModified() {
		return lastModified;
	}

	public void setLastModified(LocalDateTime lastModified) {
		this.lastModified = lastModified;
	}

	public String getLogMessage() {
		return logMessage;
	}

	public void setLogMessage(String logMessage) {
		this.logMessage = logMessage;
	}

	public Integer getLogLevel() {
		return logLevel;
	}

	public void setLogLevel(Integer logLevel) {
		this.logLevel = logLevel;
	}

	public Attachment getAttachment() {
		return attachment;
	}

	public void setAttachment(Attachment attachment) {
		this.attachment = attachment;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public Long getClusterId() {
		return clusterId;
	}

	public void setClusterId(Long clusterId) {
		this.clusterId = clusterId;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		LogFull log = (LogFull) o;
		return Objects.equals(id, log.id) && Objects.equals(logTime, log.logTime) && Objects.equals(logMessage, log.logMessage)
				&& Objects.equals(lastModified, log.lastModified) && Objects.equals(logLevel, log.logLevel) && Objects.equals(testItem,
				log.testItem
		) && Objects.equals(launch, log.launch) && Objects.equals(projectId, log.projectId) && Objects.equals(clusterId, log.clusterId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, logTime, logMessage, lastModified, logLevel, testItem, launch, projectId, clusterId);
	}
}
