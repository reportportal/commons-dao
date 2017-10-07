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

	private Long count;

	private LinkedList<Entry> statusHistory;

	private String name;

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

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
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

	public static class Entry implements Serializable {

		private String status;

		private String issue;

		private Date time;

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

		public Date getTime() {
			return time;
		}

		public void setTime(Date time) {
			this.time = time;
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
			if (issue != null ? !issue.equals(entry.issue) : entry.issue != null) {
				return false;
			}
			return time != null ? time.equals(entry.time) : entry.time == null;
		}

		@Override
		public int hashCode() {
			int result = status != null ? status.hashCode() : 0;
			result = 31 * result + (issue != null ? issue.hashCode() : 0);
			result = 31 * result + (time != null ? time.hashCode() : 0);
			return result;
		}

		@Override
		public String toString() {
			return "Entry{" + "status='" + status + '\'' + ", issue='" + issue + '\'' + ", time=" + time + '}';
		}
	}

	@Override
	public String toString() {
		return "ItemStatusHistory{" + "id='" + id + '\'' + ", total=" + total + ", statusHistory=" + statusHistory + ", name='" + name
				+ '\'' + '}';
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
		return name != null ? name.equals(that.name) : that.name == null;
	}

	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (total != null ? total.hashCode() : 0);
		result = 31 * result + (statusHistory != null ? statusHistory.hashCode() : 0);
		result = 31 * result + (name != null ? name.hashCode() : 0);
		return result;
	}
}
