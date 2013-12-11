/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.cdo.connector;

import org.eclipse.emf.ecore.EObject;

public interface Modification <T extends EObject, R extends EObject>{

    R apply(T object);


//	public <T> T modify(IUnitOfWork<T,P> work);
}
