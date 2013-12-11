/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.rest.ui.wicket.components;

import org.apache.wicket.markup.html.link.Link;

public class PlainLink extends Link<String> {

    private CharSequence path;

    public PlainLink(String id, CharSequence path) {
        super(id);
        this.path = path;
    }

    public PlainLink(String id, String... segments) {
        this(id, createPath(segments));
    }

    private static StringBuilder createPath(String[] segments) {
        StringBuilder builder = new StringBuilder();
        if(segments==null || segments.length==0)
            return builder;
        for (String segment : segments) {
            builder.append(segment);
            builder.append("/"); //$NON-NLS-1$
        }
        builder.setLength(builder.length()-1);
        return builder;
    }

    @Override
    public void onClick() {
        // nothing to do

    }

    @Override
    protected CharSequence getURL() {
        return path;
    }

    @Override
    protected boolean getStatelessHint() {
        return true;
    }

}
