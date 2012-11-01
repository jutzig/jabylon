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
import org.apache.wicket.request.mapper.parameter.PageParameters;

import de.jutzig.jabylon.properties.Resolvable;
import de.jutzig.jabylon.rest.ui.model.EObjectModel;
import de.jutzig.jabylon.rest.ui.wicket.BasicResolvablePanel;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 *
 */
public class BreadcrumbPanel extends BasicResolvablePanel<Resolvable<?, ?>> {

	public BreadcrumbPanel(Resolvable<?, ?> object, PageParameters parameters) {
		super("breadcrumb-panel", object, parameters);
	}
	
	@Override
	protected void onBeforeRender() {

		populateBreadcrumbs();
		super.onBeforeRender();
	}
	

	private void populateBreadcrumbs() {
		final List<EObjectModel<Resolvable<?, ?>>> parents = buildParentList(getModelObject());
		ListDataProvider<EObjectModel<Resolvable<?, ?>>> provider = new ListDataProvider<EObjectModel<Resolvable<?, ?>>>(parents);

		final boolean endsOnSlash = urlEndsOnSlash();
		
		DataView<EObjectModel<Resolvable<?, ?>>> view = new DataView<EObjectModel<Resolvable<?, ?>>>("crumbs", provider) {
			@Override
			protected void populateItem(Item<EObjectModel<Resolvable<?, ?>>> item) {
				Resolvable<?, ?> element = item.getModel().getObject().getObject();
				// TODO: use EMF switch to determine label
				ExternalLink link;
				int index = parents.indexOf(item.getModel().getObject());
				int stepsUp = (parents.size() - index) - 1;

				StringBuilder path = new StringBuilder();
				if(!endsOnSlash && stepsUp==1)
				{
					//then this is the current '.'
					path.append(".");
				}
				else
				{
					if(!endsOnSlash)
						stepsUp--;
					for (int i = 0; i < stepsUp; i++) {
						path.append("../");
					}					
				}

				if (element.getParent() == null) {
					// path.append("../"); //one more to skip the 'workspace'
					// part
					link = new ExternalLink("link", path.toString(), "Home");

				} else {
					link = new ExternalLink("link", path.toString(), element.getName());
				}
				// link.setContextRelative(true);
				if (index == parents.size() - 1) {
					// sets the last crumb to 'active'
					link.add(new AttributeModifier("class", "active"));
				}
				item.add(link);
			}
		};
		add(view);

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
