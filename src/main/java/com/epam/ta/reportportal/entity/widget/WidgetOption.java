/*
 * Copyright 2017 EPAM Systems
 *
 *
 * This file is part of EPAM Report Portal.
 * https://github.com/reportportal/service-api
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

package com.epam.ta.reportportal.entity.widget;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Pavel Bortnik
 */
@Entity
@Table(name = "widget_option")
public class WidgetOption implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "widget_id")
	private Long widgetId;

	@Column(name = "option")
	private String widgetOption;

	@Column(name = "value")
	private String value;

	public WidgetOption() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getWidgetId() {
		return widgetId;
	}

	public void setWidgetId(Long widgetId) {
		this.widgetId = widgetId;
	}

	public String getWidgetOption() {
		return widgetOption;
	}

	public void setWidgetOption(String widgetOption) {
		this.widgetOption = widgetOption;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
