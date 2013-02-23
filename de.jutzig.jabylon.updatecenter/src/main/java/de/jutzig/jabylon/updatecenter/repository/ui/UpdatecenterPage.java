package de.jutzig.jabylon.updatecenter.repository.ui;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import de.jutzig.jabylon.rest.ui.wicket.components.BootstrapTabbedPanel;
import de.jutzig.jabylon.rest.ui.wicket.pages.GenericPage;
import de.jutzig.jabylon.security.CommonPermissions;
import de.jutzig.jabylon.updatecenter.repository.OBRRepositoryService;
import de.jutzig.jabylon.updatecenter.repository.ResourceFilter;


@AuthorizeInstantiation(CommonPermissions.SYSTEM_GLOBAL_CONFIG)
public class UpdatecenterPage extends GenericPage<Serializable> {

	private static final long serialVersionUID = 1L;

	@Inject
	private transient OBRRepositoryService repositoryConnector;


	public UpdatecenterPage(PageParameters parameters) {
		super(parameters);
	}

	@Override
	protected void construct() {
		super.construct();
		BootstrapTabbedPanel<ITab> panel = new BootstrapTabbedPanel<ITab>("tabs", createTabList());
		add(panel);
	}

	private List<ITab> createTabList() {
		List<ITab> tabs = new ArrayList<ITab>();
		tabs.add(new UpdatecenterTab(Model.of("Plugins"), ResourceFilter.PLUGIN));
		tabs.add(new UpdatecenterTab(Model.of("Update"), ResourceFilter.UPDATEABLE));
		tabs.add(new UpdatecenterTab(Model.of("All"), ResourceFilter.INSTALLABLE));
		tabs.add(new UpdatecenterTab(Model.of("Installed"), ResourceFilter.INSTALLED));

		return tabs;
	}

	@Override
	protected IModel<Serializable> createModel(PageParameters params) {
		IModel<Serializable> model = Model.of();
		return model;
	}

}
