package com.epam.ta.reportportal.entity.widget.content;

import com.epam.ta.reportportal.entity.launch.Launch;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

import static com.epam.ta.reportportal.dao.constant.WidgetContentRepositoryConstants.*;

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
	private Long number;

	public Long getId() {
		return id;
	}

	public LatestLaunchContent() {
	}

	public LatestLaunchContent(Launch launch) {
		this.id = launch.getId();
		this.name = launch.getName();
		this.number = launch.getNumber();
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

	public Long getNumber() {
		return number;
	}

	public void setNumber(Long number) {
		this.number = number;
	}
}
