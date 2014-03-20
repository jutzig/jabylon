/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.rest.ui.wicket.components;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.model.IModel;

/**
 * adds a javascript confirmation dialog to a button
 * @author jutzig.dev@googlemail.com
 *
 */
public class ConfirmBehaviour extends AttributeModifier {
    
	private static final long serialVersionUID = -5412738309570376201L;

	public ConfirmBehaviour(String event, IModel<String> msg) {
        super(event, msg);
    }
	
	/**
	 * adds confirmation for onclick
	 * @param msg
	 */
	public ConfirmBehaviour(IModel<String> msg) {
        super("onclick", msg);
    }
 
    protected String newValue(final String currentValue, final String replacementValue) {
        String prefix = "var conf = confirm('" + replacementValue + "'); " +
            "if (!conf) return false; ";
        String result = prefix;
        if (currentValue != null) {
            result = prefix + currentValue;
        }
        return result;
    }
}