/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.rest.ui.model;

import org.apache.wicket.model.IModel;
import org.eclipse.emf.cdo.CDOObject;
import org.eclipse.emf.ecore.EStructuralFeature;

public interface IEObjectModel<T extends CDOObject> extends IModel<T>{

    public abstract <X> IModel<X> forProperty(EStructuralFeature feature);

}
