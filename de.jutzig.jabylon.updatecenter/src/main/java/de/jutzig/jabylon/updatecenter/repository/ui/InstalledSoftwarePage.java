package de.jutzig.jabylon.updatecenter.repository.ui;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import org.apache.felix.bundlerepository.Resource;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.string.StringValue;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.jutzig.jabylon.rest.ui.util.GlobalResources;
import de.jutzig.jabylon.rest.ui.wicket.pages.GenericPage;
import de.jutzig.jabylon.updatecenter.repository.BundleState;
import de.jutzig.jabylon.updatecenter.repository.OBRRepositoryService;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 *
 */
//TODO: use different right for this
@AuthorizeInstantiation("ACCESS_CONFIG")
public class InstalledSoftwarePage extends GenericPage<String> {

	private static final long serialVersionUID = 1L;

	@Inject
	private transient OBRRepositoryService repositoryConnector;
	
	private static final Logger logger = LoggerFactory.getLogger(InstalledSoftwarePage.class);
	
	public InstalledSoftwarePage(PageParameters parameters) {
		super(parameters);
		setStatelessHint(true);
	}
	
	@Override
	public void renderHead(IHeaderResponse response) {
		response.render(JavaScriptHeaderItem.forReference(GlobalResources.JS_JQUERY_DATATABLES));
		response.render(JavaScriptHeaderItem.forReference(GlobalResources.JS_BOOTSTRAP_DATATABLES));
		super.renderHead(response);
	}
	
	@Override
	protected void construct() {
		super.construct();
//		List<Resource> resources = repositoryConnector.listInstalledBundles();

		Bundle bundle = FrameworkUtil.getBundle(getClass());
		
		List<Bundle> resources = Arrays.asList(bundle.getBundleContext().getBundles()); 
		
		ListView<Bundle> resourceView = new ListView<Bundle>("row", resources) {
			
			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(ListItem<Bundle> item) {
				Bundle resource = item.getModelObject();
				String name = resource.getSymbolicName();
				item.add(new Label("name", name));
				item.add(new Label("version", resource.getVersion().toString()));
				int state = resource.getState();
				BundleState bundleState = BundleState.fromState(state);
				Label stateLabel = new Label("state",bundleState.name());
				stateLabel.add(new AttributeAppender("class", "label label-"+bundleState.getLabelClass()));
				item.add(stateLabel);
			}
		};
		add(resourceView);
	}


	@Override
	protected IModel<String> createModel(PageParameters params) {
		StringValue value = params.get("uri");
		if(value.isEmpty())
			return null;
		return Model.of(value.toString());
	}
	
	@Override
	public boolean isBookmarkable() {
		return true;
	}

}
