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

package com.epam.ta.reportportal.database.entity.sharing;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Simple acl which should be used for sharing objects.<br>
 * Sharing in this situation mean set {@link AclPermissions}.READ permission for project on sharing object.
 *
 * @author Aliaksei_Makayed
 */
public class Acl implements Serializable {

	private String ownerUserId;

	private Set<AclEntry> entries = new HashSet<>();

	public Acl() {
	}

	public Acl(String ownerUserId) {
		this.ownerUserId = ownerUserId;
	}

	public String getOwnerUserId() {
		return ownerUserId;
	}

	public void setOwnerUserId(String ownerUserId) {
		this.ownerUserId = ownerUserId;
	}

	public void setEntries(Set<AclEntry> entries) {
		this.entries = entries;
	}

	public Set<AclEntry> getEntries() {
		return entries;
	}

	public boolean addEntry(AclEntry entry) {
		return entries.add(entry);
	}

	public boolean removeEntry(AclEntry entry) {
		return entries.remove(entry);
	}

}