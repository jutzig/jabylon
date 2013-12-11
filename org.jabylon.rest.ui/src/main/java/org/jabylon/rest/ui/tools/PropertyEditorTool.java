/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.rest.ui.tools;

import java.io.Serializable;

import org.jabylon.rest.ui.model.PropertyPair;
import org.jabylon.rest.ui.wicket.PanelFactory;

public interface PropertyEditorTool extends PanelFactory<PropertyPair>, Serializable{


    String getName();

    int getPrecedence();

}
