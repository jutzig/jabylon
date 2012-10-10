/**
 * 
 */
package de.jutzig.jabylon.rest.ui.wicket.config.impl;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.osgi.service.prefs.Preferences;

import de.jutzig.jabylon.properties.Project;
import de.jutzig.jabylon.properties.PropertiesPackage;
import de.jutzig.jabylon.properties.Workspace;
import de.jutzig.jabylon.rest.ui.model.ComplexEObjectListDataProvider;
import de.jutzig.jabylon.rest.ui.wicket.config.SettingsPage;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 *
 */
public class WorkspaceConfigSection extends GenericPanel<Workspace> {

	private static final long serialVersionUID = -5358263608301930488L;

	public WorkspaceConfigSection(String id, IModel<Workspace> object, Preferences prefs) {
		super(id, object);
		add(buildAddNewLink(object));
		ComplexEObjectListDataProvider<Project> provider = new ComplexEObjectListDataProvider<Project>(object.getObject(), PropertiesPackage.Literals.RESOLVABLE__CHILDREN);
		ListView<Project> project = new ListView<Project>("projects",provider) {

			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(ListItem<Project> item) {
				
				item.add(new ExternalLink("edit","workspace/"+item.getModelObject().getName()));
				item.add(new Label("project-name",item.getModelObject().getName()));
			}
		};
		add(project);
	}

	private Component buildAddNewLink(IModel<Workspace> model) {
		PageParameters params = new PageParameters();
		params.add(SettingsPage.QUERY_PARAM_CREATE, PropertiesPackage.Literals.PROJECT.getName());
		return new BookmarkablePageLink<Void>("addNew", SettingsPage.class, params);
	}
}
