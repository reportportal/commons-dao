package com.epam.ta.reportportal.database.entity.item;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Do not db object representation. It is result of
 * {@link com.epam.ta.reportportal.database.dao.TestItemRepositoryCustom#getItemStatusHistory(List)}
 * aggregation query.
 *
 * @author Pavel Bortnik
 */
public class ItemStatusHistory implements Serializable {

	private String id;

	private Long total;

	private LinkedList<Entry> statusHistory;

	private String name;

	private Date lastTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public LinkedList<Entry> getStatusHistory() {
		return statusHistory;
	}

	public void setStatusHistory(LinkedList<Entry> statusHistory) {
		this.statusHistory = statusHistory;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getLastTime() {
		return lastTime;
	}

	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}

	@Override
	public String toString() {
		return "ItemStatusHistory{" + "id='" + id + '\'' + ", total=" + total + ", statusHistory=" + statusHistory + ", name='" + name
				+ '\'' + ", lastTime=" + lastTime + '}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		ItemStatusHistory that = (ItemStatusHistory) o;

		if (id != null ? !id.equals(that.id) : that.id != null) {
			return false;
		}
		if (total != null ? !total.equals(that.total) : that.total != null) {
			return false;
		}
		if (statusHistory != null ? !statusHistory.equals(that.statusHistory) : that.statusHistory != null) {
			return false;
		}
		if (name != null ? !name.equals(that.name) : that.name != null) {
			return false;
		}
		return lastTime != null ? lastTime.equals(that.lastTime) : that.lastTime == null;
	}

	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (total != null ? total.hashCode() : 0);
		result = 31 * result + (statusHistory != null ? statusHistory.hashCode() : 0);
		result = 31 * result + (name != null ? name.hashCode() : 0);
		result = 31 * result + (lastTime != null ? lastTime.hashCode() : 0);
		return result;
	}

	public static class Entry implements Serializable {

		private String status;

		private String issue;

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public String getIssue() {
			return issue;
		}

		public void setIssue(String issue) {
			this.issue = issue;
		}

		@Override
		public String toString() {
			return "Entry{" + "status='" + status + '\'' + ", issue='" + issue + '\'' + '}';
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) {
				return true;
			}
			if (o == null || getClass() != o.getClass()) {
				return false;
			}

			Entry entry = (Entry) o;

			if (status != null ? !status.equals(entry.status) : entry.status != null) {
				return false;
			}
			return issue != null ? issue.equals(entry.issue) : entry.issue == null;
		}

		@Override
		public int hashCode() {
			int result = status != null ? status.hashCode() : 0;
			result = 31 * result + (issue != null ? issue.hashCode() : 0);
			return result;
		}
	}

}
