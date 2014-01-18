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
package org.jabylon.rest.ui.navbar;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.jabylon.common.util.config.DynamicConfigUtil;
import org.jabylon.properties.Resolvable;
import org.jabylon.properties.Workspace;
import org.jabylon.rest.ui.util.WicketUtil;
import org.jabylon.rest.ui.wicket.BasicPanel;
import org.jabylon.rest.ui.wicket.PanelFactory;
import org.jabylon.rest.ui.wicket.config.SettingsPage;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 * 
 */
public class SettingsNavBarPanel<T> extends BasicPanel<T> {

	private static final long serialVersionUID = 1L;

	@SuppressWarnings("rawtypes")
	public SettingsNavBarPanel(String id, IModel<T> object, PageParameters parameters) {
		super(id, object, parameters);
				
		PageParameters params = parameters;
		if (object != null && object.getObject() instanceof Resolvable && !(object.getObject() instanceof Workspace)) {
			// workspace config isn't all that interesting. Show the overview by
			// default instead
			Resolvable r = (Resolvable) object.getObject();
			params = WicketUtil.buildPageParametersFor(r);
		}
		List<?> applicable = Collections.emptyList();
		if(object!=null) {
			T domain = object.getObject();
			applicable = DynamicConfigUtil.getApplicableElements(domain);
			if (domain instanceof Resolvable) {
				//traverse up until we find something configurable
				//https://github.com/jutzig/jabylon/issues/82
				Resolvable resolvable = (Resolvable) domain;
				while (resolvable != null && applicable.isEmpty()) {
					resolvable = resolvable.getParent();
					applicable = DynamicConfigUtil.getApplicableElements(resolvable);
				}
				if(resolvable!=domain)
					params = WicketUtil.buildPageParametersFor(resolvable);
			}				
		}
		
		BookmarkablePageLink<String> link = new BookmarkablePageLink<String>("link", SettingsPage.class, params); //$NON-NLS-1$

		link.setEnabled(!applicable.isEmpty());
		add(link);
	}

	public static class SettingsPanelFactory implements PanelFactory<Object>, Serializable {

		private static final long serialVersionUID = 1L;

		@Override
		public Panel createPanel(PageParameters params, IModel<Object> input, String id) {

			return new SettingsNavBarPanel<Object>(id, input, params);
		}

	}
}
