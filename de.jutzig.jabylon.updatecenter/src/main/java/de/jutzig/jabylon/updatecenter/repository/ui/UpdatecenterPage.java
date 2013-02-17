package de.jutzig.jabylon.updatecenter.repository.ui;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import org.apache.felix.bundlerepository.Resource;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.jutzig.jabylon.rest.ui.wicket.pages.GenericPage;
import de.jutzig.jabylon.updatecenter.repository.OBRRepositoryService;
import de.jutzig.jabylon.updatecenter.repository.ResourceFilter;

//TODO: use different right for this
@AuthorizeInstantiation("ACCESS_CONFIG")
public class UpdatecenterPage extends GenericPage<Serializable> {

	private static final long serialVersionUID = 1L;

	@Inject
	private transient OBRRepositoryService repositoryConnector;

	private static final Logger logger = LoggerFactory.getLogger(InstalledSoftwarePage.class);

	protected static final String PARAM_ACTION = "action";

	protected static final String PARAM_BUNDLE = "bundle";

	public UpdatecenterPage(PageParameters parameters) {
		super(parameters);
	}

	@Override
	protected void construct() {
		super.construct();
		List<Resource> resources = repositoryConnector.getAvailableResources(ResourceFilter.ALL);
		final String targetBundleId = getPageParameters().get(PARAM_BUNDLE).toOptionalString();
		ListView<Resource> resourceView = new ListView<Resource>("row", resources) {

			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(ListItem<Resource> item) {
				Resource resource = item.getModelObject();

				if (resource.getId().equals(targetBundleId)) {
					if ("install".equals(getPageParameters().get(PARAM_ACTION).toOptionalString())) {
						logger.info("Installing Resource {}", resource.getSymbolicName());

						repositoryConnector.install(resource);
					}
				}

				String name = resource.getSymbolicName();
				item.add(new Label("name", name));
				item.add(new Label("description", resource.getPresentationName()));
				item.add(new Label("version", resource.getVersion().toString()));

				String action = "install";

				PageParameters params = new PageParameters();
				if (action != null) {
					params.set(PARAM_ACTION, action);
					params.set(PARAM_BUNDLE, resource.getId());
				}
				Link<Object> actionLink = new BookmarkablePageLink<Object>(PARAM_ACTION, getPageClass(), params);
				actionLink.setBody(Model.of(action));
				actionLink.setEnabled(action != null);
				item.add(actionLink);
			}
		};
		add(resourceView);

	}

	@Override
	protected IModel<Serializable> createModel(PageParameters params) {
		IModel<Serializable> model = Model.of();
		return model;
	}

}
