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

	private String fileId;

	private String thumbnailFileId;

	private String contentType;

	public BinaryDataMetaInfo() {}

	/**
	 * Object to hold information about saved file.
	 *
	 * @param fileId
	 * @param thumbnailFileId
	 */
	public BinaryDataMetaInfo(String fileId, String thumbnailFileId, String contentType) {
		this.fileId = fileId;
		this.thumbnailFileId = thumbnailFileId;
		this.contentType = contentType;
	}

	public String getFileId() {
		return fileId;
	}

	public String getThumbnailFileId() {
		return thumbnailFileId;
	}

	public String getContentType() {
		return contentType;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public void setThumbnailFileId(String thumbnailFileId) {
		this.thumbnailFileId = thumbnailFileId;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public static final class BinaryDataMetaInfoBuilder {
		private String fileId;
		private String thumbnailFileId;
		private String contentType;

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

		public BinaryDataMetaInfoBuilder withContentType(String contentType) {
			this.contentType = contentType;
			return this;
		}

		public BinaryDataMetaInfo build() {
			return new BinaryDataMetaInfo(fileId, thumbnailFileId, contentType);
		}
	}
}
