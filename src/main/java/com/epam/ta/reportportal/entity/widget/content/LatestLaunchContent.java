package com.epam.ta.reportportal.entity.widget.content;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

import static com.epam.ta.reportportal.dao.constant.WidgetContentRepositoryConstants.ID;
import static com.epam.ta.reportportal.dao.constant.WidgetContentRepositoryConstants.NAME;
import static com.epam.ta.reportportal.dao.constant.WidgetContentRepositoryConstants.NUMBER;

/**
 * @author Ivan Budaev
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LatestLaunchContent implements Serializable {

	@JsonProperty(ID)
	private Long id;

	@JsonProperty(NAME)
	private String name;

	@JsonProperty(NUMBER)
	private Integer number;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}
}
