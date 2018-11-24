package com.epam.ta.reportportal.entity.widget.content;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.List;

import static com.epam.ta.reportportal.dao.constant.WidgetContentRepositoryConstants.*;

/**
 * @author Ivan Budayeu
 */
public class FlakyCasesTableContent implements Serializable {

	@JsonProperty(value = STATUSES)
	@Column(name = STATUSES)
	private List<String> statuses;

	@JsonProperty(value = FLAKY_COUNT)
	@Column(name = FLAKY_COUNT)
	private Long flakyCount;

	@JsonProperty(value = TOTAL)
	@Column(name = TOTAL)
	private Long total;

	@JsonProperty(value = ITEM_NAME)
	@Column(name = ITEM_NAME)
	private String itemName;

	@JsonProperty(value = UNIQUE_ID)
	@Column(name = UNIQUE_ID)
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
