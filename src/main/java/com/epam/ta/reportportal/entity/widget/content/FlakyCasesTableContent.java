package com.epam.ta.reportportal.entity.widget.content;

import java.io.Serializable;
import java.util.List;

/**
 * @author Ivan Budayeu
 */
public class FlakyCasesTableContent implements Serializable {

	private List<String> statuses;
	private Long flakyCount;
	private Long total;
	private String itemName;
	private String uniqueId;

	public FlakyCasesTableContent() {
	}

	public List<String> getStatuses() {
		return statuses;
	}

	public void setStatuses(List<String> statuses) {
		this.statuses = statuses;
	}

	public Long getFlakyCount() {
		return flakyCount;
	}

	public void setFlakyCount(Long flakyCount) {
		this.flakyCount = flakyCount;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}
}
