/*
 * Copyright 2016 EPAM Systems
 * 
 * 
 * This file is part of EPAM Report Portal.
 * https://github.com/reportportal/commons-dao
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
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.hateoas.Identifiable;

import com.epam.ta.reportportal.database.search.FilterCriteria;

/**
 * 
 * @author Henadzi_Vrubleuski
 * 
 */
@Document
@CompoundIndexes({ @CompoundIndex(name = "testItemRef_logTime", def = "{'testItemRef': 1, 'logTime': 1}", background = true) })
public class Log implements Serializable, Modifiable, Identifiable<String> {

	private static final long serialVersionUID = -8253927638921956991L;

	public static final String TEST_ITEM_ID = "item";

	@Id
	@FilterCriteria("id")
	private String id;

	@FilterCriteria("time")
	private Date logTime;

	@FilterCriteria("message")
	private String logMsg;

	@Field("binary_content")
	@FilterCriteria("binary_content")
	private BinaryContent binaryContent;

	@FilterCriteria(TEST_ITEM_ID)
	private String testItemRef;

	@LastModifiedDate
	@FilterCriteria(LAST_MODIFIED)
	@Field(LAST_MODIFIED)
	private Date lastModified;

	@FilterCriteria("level")
	private LogLevel level;

	@Override
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getLogTime() {
		return logTime;
	}

	public void setLogTime(Date logTime) {
		this.logTime = logTime;
	}

	public String getLogMsg() {
		return logMsg;
	}

	public void setLogMsg(String logMsg) {
		this.logMsg = logMsg;
	}

	public String getTestItemRef() {
		return testItemRef;
	}

	public void setTestItemRef(String testItemRef) {
		this.testItemRef = testItemRef;
	}

	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}

	@Override
	public Date getLastModified() {
		return lastModified;
	}

	public LogLevel getLevel() {
		return level;
	}

	public void setLevel(LogLevel level) {
		this.level = level;
	}

	public void setBinaryContent(BinaryContent binaryContent) {
		this.binaryContent = binaryContent;
	}

	public BinaryContent getBinaryContent() {
		return binaryContent;
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
		result = prime * result + ((binaryContent == null) ? 0 : binaryContent.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((lastModified == null) ? 0 : lastModified.hashCode());
		result = prime * result + ((level == null) ? 0 : level.hashCode());
		result = prime * result + ((logMsg == null) ? 0 : logMsg.hashCode());
		result = prime * result + ((logTime == null) ? 0 : logTime.hashCode());
		result = prime * result + ((testItemRef == null) ? 0 : testItemRef.hashCode());
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
		Log other = (Log) obj;
		if (binaryContent == null) {
			if (other.binaryContent != null) {
				return false;
			}
		} else if (!binaryContent.equals(other.binaryContent)) {
			return false;
		}
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (lastModified == null) {
			if (other.lastModified != null) {
				return false;
			}
		} else if (!lastModified.equals(other.lastModified)) {
			return false;
		}
		if (level != other.level) {
			return false;
		}
		if (logMsg == null) {
			if (other.logMsg != null) {
				return false;
			}
		} else if (!logMsg.equals(other.logMsg)) {
			return false;
		}
		if (logTime == null) {
			if (other.logTime != null) {
				return false;
			}
		} else if (!logTime.equals(other.logTime)) {
			return false;
		}
		if (testItemRef == null) {
			if (other.testItemRef != null) {
				return false;
			}
		} else if (!testItemRef.equals(other.testItemRef)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Log{" + "id='" + id + '\'' + ", logTime=" + logTime + ", logMsg='" + logMsg + '\'' + ", binaryContent=" + binaryContent
				+ ", testItemRef='" + testItemRef + '\'' + ", lastModified=" + lastModified + ", level=" + level + '}';
	}
}