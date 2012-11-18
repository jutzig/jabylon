/**
 * 
 */
package de.jutzig.jabylon.rest.ui.wicket.panels;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import de.jutzig.jabylon.properties.Resolvable;
import de.jutzig.jabylon.properties.Workspace;
import de.jutzig.jabylon.rest.ui.model.EObjectModel;
import de.jutzig.jabylon.rest.ui.wicket.BasicResolvablePanel;
import de.jutzig.jabylon.rest.ui.wicket.JabylonApplication;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 *
 */
public class BreadcrumbPanel extends BasicResolvablePanel<Resolvable<?, ?>> {

	private static final long serialVersionUID = 1L;
	private String rootLabel = "Home";
	private String rootURL = "/"+ JabylonApplication.CONTEXT;

	public BreadcrumbPanel(String id, IModel<Resolvable<?, ?>> object, PageParameters parameters) {
		super(id, object, parameters);
		
	}	
	
	@Override
	protected void onBeforeRender() {
		populateBreadcrumbs();
		super.onBeforeRender();
	}

	private void populateBreadcrumbs() {
		final List<EObjectModel<Resolvable<?, ?>>> parents = buildParentList(getModelObject());
		ListDataProvider<EObjectModel<Resolvable<?, ?>>> provider = new ListDataProvider<EObjectModel<Resolvable<?, ?>>>(parents);
		final StringBuilder builder = new StringBuilder(getRootURL());

		DataView<EObjectModel<Resolvable<?, ?>>> view = new DataView<EObjectModel<Resolvable<?, ?>>>("crumbs", provider) {

			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(Item<EObjectModel<Resolvable<?, ?>>> item) {
				Resolvable<?, ?> element = item.getModel().getObject().getObject();
				int index = parents.indexOf(item.getModel().getObject());
				builder.append("/");
				//TODO: should workspace#getName return "workspace"?
				if(element instanceof Workspace)
					builder.append("workspace");
				else
					builder.append(element.getName());
				ExternalLink link = new ExternalLink("link", builder.toString(), getLabel(element));
				
				
				// link.setContextRelative(true);
				if (index == parents.size() - 1) {
					// sets the last crumb to 'active'
					link.add(new AttributeModifier("class", "active"));
				}
				item.add(link);
			}

		};
		addOrReplace(view);

	}
	
	protected String getLabel(Resolvable<?, ?> element) {
		if (element instanceof Workspace) {
			return rootLabel;
		}
		return element.getName();
	}

	public void setRootLabel(String rootLabel) {
		this.rootLabel = rootLabel;
	}
	
	public void setRootURL(String rootURL) {
		this.rootURL  = rootURL;
	}
	
	public String getRootURL() {
		return rootURL;
	}
	
	private List<EObjectModel<Resolvable<?, ?>>> buildParentList(Resolvable<?, ?> domainObject) {
		Resolvable<?, ?> current = domainObject;
		List<EObjectModel<Resolvable<?, ?>>> elements = new ArrayList<EObjectModel<Resolvable<?, ?>>>();
		while (current != null) {
			elements.add(new EObjectModel<Resolvable<?, ?>>(current));
			current = current.getParent();
		}
		Collections.reverse(elements);
		return elements;
	}

}
