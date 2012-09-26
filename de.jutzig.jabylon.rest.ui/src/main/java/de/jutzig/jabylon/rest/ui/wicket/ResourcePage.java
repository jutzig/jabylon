/**
 * 
 */
package de.jutzig.jabylon.rest.ui.wicket;

import org.apache.wicket.request.mapper.parameter.PageParameters;

import de.jutzig.jabylon.properties.Resolvable;
import de.jutzig.jabylon.rest.ui.wicket.panels.BreadcrumbPanel;
import de.jutzig.jabylon.rest.ui.wicket.panels.ProjectResourcePanel;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 *
 */
public class ResourcePage<T extends Resolvable<?, ?>> extends GenericPage<T> {

	public ResourcePage(PageParameters parameters) {
		super(parameters);
		add(new BreadcrumbPanel(getModel(),getPageParameters()));
		add(new ProjectResourcePanel(getModel(),getPageParameters()));
	}

	@Override
	protected void onBeforeRender() {
		
		super.onBeforeRender();
	}
}
