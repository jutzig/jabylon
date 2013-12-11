/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
/**
 *
 */
package org.jabylon.resources.persistence.internal;

import org.eclipse.emf.cdo.common.id.CDOID;

import com.google.common.cache.Weigher;

import org.jabylon.properties.PropertyFile;

/**
 * @author jutzig.dev@googlemail.com
 *
 */
public class PropertySizeWeigher implements Weigher<CDOID, PropertyFile> {

    @Override
    public int weigh(CDOID key, PropertyFile value) {
        return value.getProperties().size();
    }

}
