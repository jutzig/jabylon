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
package org.jabylon.rest.ui.util;

import org.apache.wicket.request.Url;
import org.apache.wicket.request.resource.UrlResourceReference;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 *
 */
public class WebContextUrlResourceReference extends UrlResourceReference {

    private static final long serialVersionUID = 1L;

    public WebContextUrlResourceReference(String webContextRelativeUrl) {
        super(Url.parse(WicketUtil.getContextPath()+"/"+webContextRelativeUrl));

    }

}
