/*
 * Copyright 2016 EPAM Systems
 * 
 * 
 * This file is part of EPAM Report Portal.
 * https://github.com/epam/ReportPortal
 * 
 * Report Portal is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Report Portal is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Report Portal.  If not, see <http://www.gnu.org/licenses/>.
 */ 

package com.epam.ta.reportportal.database.entity;

import java.io.Serializable;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Field;

import com.epam.ta.reportportal.database.search.FilterCriteria;

/**
 * Binary content representation
 * 
 * @author Andrei Varabyeu
 * 
 */
public class BinaryContent implements Serializable {

	private static final long serialVersionUID = 1L;

	@Field("id")
	@FilterCriteria("id")
	@Indexed
	private String binaryDataId;

	@Field("thumbnail_id")
	@FilterCriteria("thumbnail_id")
	@Indexed
	private String thumbnailId;

	@Field("content_type")
	private String contentType;

	public BinaryContent() {

	}

	public BinaryContent(String binaryDataId, String thumbnailId, String contentType) {
		this.binaryDataId = binaryDataId;
		this.thumbnailId = thumbnailId;
		this.contentType = contentType;
	}

	/**
	 * @return the binaryDataId
	 */
	public String getBinaryDataId() {
		return binaryDataId;
	}

	/**
	 * @param binaryDataId
	 *            the binaryDataId to set
	 */
	public void setBinaryDataId(String binaryDataId) {
		this.binaryDataId = binaryDataId;
	}

	/**
	 * @return the thumbnailId
	 */
	public String getThumbnailId() {
		return thumbnailId;
	}

	/**
	 * @param thumbnailId
	 *            the thumbnailId to set
	 */
	public void setThumbnailId(String thumbnailId) {
		this.thumbnailId = thumbnailId;
	}

	/**
	 * @return the contentType
	 */
	public String getContentType() {
		return contentType;
	}

	/**
	 * @param contentType
	 *            the contentType to set
	 */
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((binaryDataId == null) ? 0 : binaryDataId.hashCode());
		result = prime * result + ((contentType == null) ? 0 : contentType.hashCode());
		result = prime * result + ((thumbnailId == null) ? 0 : thumbnailId.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		BinaryContent other = (BinaryContent) obj;
		if (binaryDataId == null) {
			if (other.binaryDataId != null) {
				return false;
			}
		} else if (!binaryDataId.equals(other.binaryDataId)) {
			return false;
		}
		if (contentType == null) {
			if (other.contentType != null) {
				return false;
			}
		} else if (!contentType.equals(other.contentType)) {
			return false;
		}
		if (thumbnailId == null) {
			if (other.thumbnailId != null) {
				return false;
			}
		} else if (!thumbnailId.equals(other.thumbnailId)) {
			return false;
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "BinaryContent [binaryDataId=" + binaryDataId + ", thumbnailId=" + thumbnailId + ", contentType=" + contentType + "]";
	}

}