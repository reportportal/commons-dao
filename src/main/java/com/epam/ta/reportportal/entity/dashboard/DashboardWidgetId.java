/*
 * Copyright 2019 EPAM Systems
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.epam.ta.reportportal.entity.dashboard;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * @author Pavel Bortnik
 */
@Embeddable
public class DashboardWidgetId implements Serializable {

  @Column(name = "dashboard_id")
  private Long dashboardId;

  @Column(name = "widget_id")
  private Long widgetId;

  public DashboardWidgetId() {
  }

  public DashboardWidgetId(Long dashboardId, Long widgetId) {
    this.dashboardId = dashboardId;
    this.widgetId = widgetId;
  }

  public Long getDashboardId() {
    return dashboardId;
  }

  public void setDashboardId(Long dashboardId) {
    this.dashboardId = dashboardId;
  }

  public Long getWidgetId() {
    return widgetId;
  }

  public void setWidgetId(Long widgetId) {
    this.widgetId = widgetId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    DashboardWidgetId that = (DashboardWidgetId) o;

    if (dashboardId != null ? !dashboardId.equals(that.dashboardId) : that.dashboardId != null) {
      return false;
    }
    return widgetId != null ? widgetId.equals(that.widgetId) : that.widgetId == null;
  }

  @Override
  public int hashCode() {
    int result = dashboardId != null ? dashboardId.hashCode() : 0;
    result = 31 * result + (widgetId != null ? widgetId.hashCode() : 0);
    return result;
  }
}
