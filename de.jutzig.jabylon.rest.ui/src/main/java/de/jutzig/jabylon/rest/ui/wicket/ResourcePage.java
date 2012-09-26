/**
 * 
 */
package de.jutzig.jabylon.rest.ui.wicket;

import org.apache.wicket.request.mapper.parameter.PageParameters;

import de.jutzig.jabylon.properties.PropertyFileDescriptor;
import de.jutzig.jabylon.properties.Resolvable;
import de.jutzig.jabylon.rest.ui.wicket.panels.BreadcrumbPanel;
import de.jutzig.jabylon.rest.ui.wicket.panels.ProjectResourcePanel;
import de.jutzig.jabylon.rest.ui.wicket.panels.PropertyEditorPanel;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 *
 */
public class ResourcePage<T extends Resolvable<?, ?>> extends GenericPage<T> {

	public ResourcePage(PageParameters parameters) {
		super(parameters);
	}

	@Override
	protected void onBeforeRender() {
		addOrReplace(new BreadcrumbPanel(getModel(),getPageParameters()));
		if (getModel() instanceof PropertyFileDescriptor) {
			PropertyFileDescriptor descriptor = (PropertyFileDescriptor) getModel();
			addOrReplace(new PropertyEditorPanel(descriptor,getPageParameters()));	
		}
		else
			addOrReplace(new ProjectResourcePanel(getModel(),getPageParameters()));
		super.onBeforeRender();
	}
}
