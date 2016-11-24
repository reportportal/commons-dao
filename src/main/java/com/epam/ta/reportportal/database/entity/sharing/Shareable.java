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
import java.security.Principal;
import java.security.acl.NotOwnerException;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;

import com.epam.ta.reportportal.database.entity.sharing.AclEntry;
import com.epam.ta.reportportal.database.entity.sharing.Acl;


/**
 * Define base functionality for entries which can be shared.
 * 
 * @author Aliaksei_Makayed
 *
 */
public class Shareable implements Serializable {
	
	public static final String ID = "_id";
	public static final String ACL = "acl";
	public static final String OWNER = "acl.ownerUserId";

	public static final Sort NAME_OWNER_SORT = new Sort(new Order(Direction.ASC, "name"), new Order(Direction.ASC, Shareable.OWNER));

	private Acl acl;
	
	public Acl getAcl() {
		return acl;
	}

	public void setAcl(Acl acl) {
		this.acl = acl;
	}
	
	public void setOwner(Principal principal) {
		acl = new Acl(principal.getName());
	}
	
	public void setOwner(String owner) {
		acl = new Acl(owner);
	}

	public void addAclEntry(AclEntry aclEntry) {
		if (acl != null) {
			acl.addEntry(aclEntry);
		}
	}
	
	public void removeAclEntry(AclEntry aclEntry) throws NotOwnerException {
		if (acl != null) {
			acl.removeEntry(aclEntry);
		}
	}
	
	
}