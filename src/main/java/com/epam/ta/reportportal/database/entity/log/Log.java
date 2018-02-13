package com.epam.ta.reportportal.database.entity.log;

import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * @author Pavel Bortnik
 */

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "log", schema = "public", indexes = { @Index(name = "log_pk", unique = true, columnList = "id ASC") })
public class Log {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false, precision = 64)
	private Long id;

	@Column(name = "log_time", nullable = false)
	private Timestamp logTime;

	@Column(name = "log_message", nullable = false)
	private String logMessage;

	@Column(name = "item_id", nullable = false, precision = 64)
	private Long itemId;

	@LastModifiedDate
	@Column(name = "last_modified", nullable = false)
	private Timestamp lastModified;

	@Column(name = "log_level", nullable = false, precision = 32)
	private Integer logLevel;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Timestamp getLogTime() {
		return logTime;
	}

	public void setLogTime(Timestamp logTime) {
		this.logTime = logTime;
	}

	public String getLogMessage() {
		return logMessage;
	}

	public void setLogMessage(String logMessage) {
		this.logMessage = logMessage;
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public Timestamp getLastModified() {
		return lastModified;
	}

	public void setLastModified(Timestamp lastModified) {
		this.lastModified = lastModified;
	}

	public Integer getLogLevel() {
		return logLevel;
	}

	public void setLogLevel(Integer logLevel) {
		this.logLevel = logLevel;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Log log = (Log) o;
		return Objects.equals(id, log.id) && Objects.equals(logTime, log.logTime) && Objects.equals(logMessage, log.logMessage)
				&& Objects.equals(itemId, log.itemId) && Objects.equals(lastModified, log.lastModified) && Objects.equals(
				logLevel, log.logLevel);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, logTime, logMessage, itemId, lastModified, logLevel);
	}

	@Override
	public String toString() {
		return "Log{" + "id=" + id + ", logTime=" + logTime + ", logMessage='" + logMessage + '\'' + ", itemId=" + itemId
				+ ", lastModified=" + lastModified + ", logLevel=" + logLevel + '}';
	}
}
