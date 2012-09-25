package de.jutzig.jabylon.rest.ui.wicket;

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
import de.jutzig.jabylon.rest.ui.model.EObjectModel;

public class BasicResolvablePage<T extends Resolvable<?, ?>> extends BasicPage {

	private transient T domainObject;
	
	public BasicResolvablePage() {
		super();
	}

	public BasicResolvablePage(IModel<?> model) {
		super(model);
	}

	public BasicResolvablePage(PageParameters parameters) {
		super(parameters);
	}

	public void setDomainObject(T domainObject) {
		this.domainObject = domainObject;
	}
	
	public T getDomainObject() {
		return domainObject;
	}
	
	@Override
	protected void onBeforeRender() {
		
		populateBreadcrumbs();
		super.onBeforeRender();
	}

	private void populateBreadcrumbs() {
		final List<EObjectModel<Resolvable<?, ?>>>  parents = buildParentList(getDomainObject());
		ListDataProvider<EObjectModel<Resolvable<?, ?>>> provider = new ListDataProvider<EObjectModel<Resolvable<?,?>>>(parents);
		
		DataView<EObjectModel<Resolvable<?, ?>>> view = new DataView<EObjectModel<Resolvable<?,?>>>("crumbs",provider) {
			@Override
			protected void populateItem(Item<EObjectModel<Resolvable<?, ?>>> item) {
				Resolvable<?, ?> element = item.getModel().getObject().getObject();
				//TODO: use EMF switch to determine label
				ExternalLink link; 
				
				if(element.getParent()==null)
				{
					link = new ExternalLink("link", "/", "Home");
				}
				else
				{
					link = new ExternalLink("link", element.getParent().getName()+"/"+element.getName(), element.getName());
				}
				link.setContextRelative(true);
				if(item.getModel().getObject()==parents.get(parents.size()-1))
				{
					//sets the last crumb to 'active'
					link.add(new AttributeModifier("class", "active"));					
				}
				item.add(link);
			}
		};
		add(view);
		
	}

	private List<EObjectModel<Resolvable<?, ?>>> buildParentList(T domainObject) {
		Resolvable<?, ?> current = domainObject;
		List<EObjectModel<Resolvable<?, ?>>> elements = new ArrayList<EObjectModel<Resolvable<?, ?>>>();
		while(current!=null)
		{
			elements.add(new EObjectModel<Resolvable<?,?>>(current));
			current = current.getParent();
		}
		Collections.reverse(elements);
		return elements;
	}
	
}
