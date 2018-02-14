package com.epam.ta.reportportal.database.entity.launch;

import java.util.Objects;

/**
 * @author Pavel Bortnik
 */
public class ExecutionStatistics {

	private Integer total;

	private Integer passed;

	private Integer failed;

	private Integer skipped;

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Integer getPassed() {
		return passed;
	}

	public void setPassed(Integer passed) {
		this.passed = passed;
	}

	public Integer getFailed() {
		return failed;
	}

	public void setFailed(Integer failed) {
		this.failed = failed;
	}

	public Integer getSkipped() {
		return skipped;
	}

	public void setSkipped(Integer skipped) {
		this.skipped = skipped;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		ExecutionStatistics that = (ExecutionStatistics) o;
		return Objects.equals(total, that.total) && Objects.equals(passed, that.passed) && Objects.equals(failed, that.failed)
				&& Objects.equals(skipped, that.skipped);
	}

	@Override
	public int hashCode() {
		return Objects.hash(total, passed, failed, skipped);
	}

	@Override
	public String toString() {
		return "ExecutionStatistics{" + "total=" + total + ", passed=" + passed + ", failed=" + failed + ", skipped=" + skipped + '}';
	}
}
