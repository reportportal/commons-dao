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

package com.epam.ta.reportportal.commons;

public class BinaryDataMetaInfo {

	private final String fileId;

	private final String thumbnailFileId;

	private final Long projectId;

	private final String itemId;

	private final String launchId;

	/**
	 * Object to hold information about saved file.
	 *
	 * @param fileId
	 * @param thumbnailFileId
	 */
	public BinaryDataMetaInfo(String fileId, String thumbnailFileId, Long projectId, String itemId, String launchId) {
		this.fileId = fileId;
		this.thumbnailFileId = thumbnailFileId;
		this.projectId = projectId;
		this.itemId = itemId;
		this.launchId = launchId;
	}

	public String getFileId() {
		return fileId;
	}

	public String getThumbnailFileId() {
		return thumbnailFileId;
	}

	public Long getProjectId() {
		return projectId;
	}

	public String getItemId() {
		return itemId;
	}

	public String getLaunchId() {
		return launchId;
	}

	public static final class BinaryDataMetaInfoBuilder {
		private String fileId;
		private String thumbnailFileId;
		private Long projectId;
		private String itemId;
		private String launchId;

		private BinaryDataMetaInfoBuilder() {
		}

		public static BinaryDataMetaInfoBuilder aBinaryDataMetaInfo() {
			return new BinaryDataMetaInfoBuilder();
		}

		public BinaryDataMetaInfoBuilder withFileId(String fileId) {
			this.fileId = fileId;
			return this;
		}

		public BinaryDataMetaInfoBuilder withThumbnailFileId(String thumbnailFileId) {
			this.thumbnailFileId = thumbnailFileId;
			return this;
		}

		public BinaryDataMetaInfoBuilder withProjectId(Long projectId) {
			this.projectId = projectId;
			return this;
		}

		public BinaryDataMetaInfoBuilder withItemId(String itemId) {
			this.itemId = itemId;
			return this;
		}

		public BinaryDataMetaInfoBuilder withLaunchId(String launchId) {
			this.launchId = launchId;
			return this;
		}

		public BinaryDataMetaInfo build() {
			return new BinaryDataMetaInfo(fileId, thumbnailFileId, projectId, itemId, launchId);
		}
	}
}
