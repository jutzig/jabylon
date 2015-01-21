/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.security;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.jabylon.users.Role;
import org.jabylon.users.User;
import org.jabylon.users.UserManagement;
import org.jabylon.users.UsersPackage;

public class GroupMemberAttribute extends SubjectAttribute {

	public GroupMemberAttribute(Collection<String> groups) {
		super(UsersPackage.Literals.USER__ROLES, groups);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void applyTo(EObject eobject) {
		if (eobject instanceof User) {
			User user = (User) eobject;
			List<Role> roles = user.getRoles();
			Iterator<Role> it = roles.iterator();
			while (it.hasNext()) {
				Role role = it.next();
				if(CommonPermissions.AUTH_TYPE_LDAP.equals(role.getType()))
				{
					it.remove();
				}
			}
			Collection<String> groups = (Collection<String>) getValue();
			EObject container = user.eContainer();
			if (container instanceof UserManagement) {
				UserManagement management = (UserManagement) container;
				List<Role> allRoles = management.getRoles();
				for (Role role : allRoles) {
					if(CommonPermissions.AUTH_TYPE_LDAP.equals(role.getType()))
					{
						if(groups.contains(role.getName()))
							roles.add(role);
					}
				}

			}
		}
	}

}
