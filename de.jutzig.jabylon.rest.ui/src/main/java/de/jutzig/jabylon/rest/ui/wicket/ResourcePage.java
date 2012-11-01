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
		T object = getModelObject();
		addOrReplace(new BreadcrumbPanel(object,getPageParameters()));
		if (object instanceof PropertyFileDescriptor) {
			PropertyFileDescriptor descriptor = (PropertyFileDescriptor) object;
			addOrReplace(new PropertyEditorPanel(descriptor,getPageParameters()));	
		}
		else
			addOrReplace(new ProjectResourcePanel(object,getPageParameters()));
		super.onBeforeRender();
	}
}
