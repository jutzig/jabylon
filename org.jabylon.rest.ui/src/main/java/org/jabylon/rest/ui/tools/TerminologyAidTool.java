/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.rest.ui.tools;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.jabylon.rest.ui.model.PropertyPair;

@Component
@Service(value=PropertyEditorTool.class)
public class TerminologyAidTool implements PropertyEditorTool {

	private static final long serialVersionUID = -2531161319722712154L;

	static final int PRECEDENCE = SimilarStringsTool.PRECEDENCE +10;
	
	@Override
	public Panel createPanel(PageParameters params, IModel<PropertyPair> input, String id) {
		return new TerminologyAidToolPanel(id,input);
	}

	@Override
	public String getName() {
		return "%terminology.name";
	}

	@Override
	public int getPrecedence() {
		return PRECEDENCE;
	}
	
}