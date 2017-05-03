/*
 * Copyright 2016 EPAM Systems
 * 
 * 
 * This file is part of EPAM Report Portal.
 * https://github.com/reportportal/commons-dao
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
 
package com.epam.ta.reportportal.triggers;

import com.epam.ta.reportportal.database.entity.Project;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

import static com.epam.ta.reportportal.commons.EntityUtils.normalizeId;

/**
 * Normalizes data for {@link Project} entity to be stored into Mongo
 * 
 * @author Andrei Varabyeu
 * 
 */
@Component
class NormalizeProjectTrigger extends AbstractMongoEventListener<Project> {

	/**
	 * Username only in lower-case
	 */
	@Override
	public void onBeforeConvert(BeforeConvertEvent<Project> event) {
		Project source = event.getSource();
		source.setName(normalizeId(source.getName()));
		super.onBeforeConvert(event);
	}

}
