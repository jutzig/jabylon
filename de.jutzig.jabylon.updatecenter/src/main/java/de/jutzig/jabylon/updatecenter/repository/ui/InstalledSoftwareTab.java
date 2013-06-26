package de.jutzig.jabylon.updatecenter.repository.ui;

import java.io.Serializable;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;

import javax.inject.Inject;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxFallbackButton;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.StatelessForm;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.FrameworkUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Function;

import de.jutzig.jabylon.rest.ui.model.ComputableModel;
import de.jutzig.jabylon.rest.ui.security.RestrictedComponent;
import de.jutzig.jabylon.rest.ui.util.GlobalResources;
import de.jutzig.jabylon.rest.ui.wicket.BasicPanel;
import de.jutzig.jabylon.updatecenter.repository.BundleState;
import de.jutzig.jabylon.updatecenter.repository.OBRRepositoryService;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 *
 */
public class InstalledSoftwareTab extends BasicPanel<String> implements RestrictedComponent{

	private static final long serialVersionUID = 1L;

	private static final EnumSet<BundleState> STOPPABLE_STATE = EnumSet.of(BundleState.ACTIVE,BundleState.STARTING);
	private static final EnumSet<BundleState> STARTABLE_STATE = EnumSet.of(BundleState.RESOLVED);
	private static final EnumSet<BundleState> CHANGEABLE_STATE; 
	static {
		EnumSet<BundleState> set = EnumSet.copyOf(STOPPABLE_STATE);
		set.addAll(STARTABLE_STATE);
		CHANGEABLE_STATE = set;
	}
	
	@Inject
	private transient OBRRepositoryService repositoryConnector;
	
	private static final Logger logger = LoggerFactory.getLogger(InstalledSoftwareTab.class);
	
	public InstalledSoftwareTab(String id) {
		super(id, Model.of(""),new PageParameters());
		
		final Form<Void> form = new StatelessForm<Void>("form");
		add(form);
		

		IModel<List<Bundle>> model = new ComputableModel<Void, List<Bundle>>(new LoadBundlesFunction(), null);
		ListView<Bundle> resourceView = new ListView<Bundle>("row", model) {
			
			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(final ListItem<Bundle> item) {
				item.setOutputMarkupId(true);
				Bundle resource = item.getModelObject();
				final long bundleId = resource.getBundleId();				
				String name = resource.getSymbolicName();
				item.add(new Label("name", name));
				item.add(new Label("version", resource.getVersion().toString()));
				int state = resource.getState();
				BundleState bundleState = BundleState.fromState(state);
				ComputableModel<Long, String> labelClassModel = new ComputableModel<Long, String>(new ComputeBundleLabelClass(), bundleId);
				ComputableModel<Long, String> labelNameModel = new ComputableModel<Long, String>(new ComputeStateFunction(), bundleId);
				Label stateLabel = new Label("state",labelNameModel);
				stateLabel.add(new AttributeAppender("class", labelClassModel));
				item.add(stateLabel);
				
				final String action = bundleState == BundleState.RESOLVED ? "start" : "stop";
				
				//TODO; use AJAX buttons to refresh labels
				Button button = new AjaxFallbackButton("action",Model.of(action),form) {
					
					private static final long serialVersionUID = 1L;

					@Override
					protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
						if(target!=null)
						{
							target.add(this);
							target.add(item);
						}
						BundleContext context = FrameworkUtil.getBundle(InstalledSoftwareTab.class).getBundleContext();
						Bundle bundle = context.getBundle(bundleId);
						BundleState state = BundleState.fromState(bundle.getState());
						if(STARTABLE_STATE.contains(state))
						{
							logger.info("Starting bundle {}", bundle.getSymbolicName());
							try {
								bundle.start();
							} catch (BundleException e) {
								String message = "Failed to start bundle "+bundle.getSymbolicName();
								getSession().error(message);
								logger.error(message,e);
							}									
						}
						
						else if(STOPPABLE_STATE.contains(state)) {
							logger.info("Stoping bundle {}", bundle.getSymbolicName());
							try {
								bundle.stop();
							} catch (BundleException e) {
								String message = "Failed to stop bundle "+bundle.getSymbolicName();
								getSession().error(message);
								logger.error(message,e);
							} 						
						}
						super.onSubmit(target, form);
					}					
				};
				button.setDefaultFormProcessing(false);
				button.add(new AttributeModifier("value", resource.getBundleId()));
				button.add(new AttributeModifier("class", "btn btn-small"));
				button.setEnabled(CHANGEABLE_STATE.contains(bundleState));
				
				item.add(button);
			}
		};
		form.add(resourceView);

		
	}
	
	@Override
	public void renderHead(IHeaderResponse response) {
		response.render(JavaScriptHeaderItem.forReference(GlobalResources.JS_JQUERY_DATATABLES));
		response.render(JavaScriptHeaderItem.forReference(GlobalResources.JS_BOOTSTRAP_DATATABLES));
		super.renderHead(response);
	}

	@Override
	public String getRequiredPermission() {
		return "System:software:config";
	}
}

class LoadBundlesFunction implements Function<Void, List<Bundle>>, Serializable {
	
	private static final long serialVersionUID = 1L;

	public List<Bundle> apply(Void nothing) {
		Bundle bundle = FrameworkUtil.getBundle(getClass());
		List<Bundle> resources = Arrays.asList(bundle.getBundleContext().getBundles());
		return resources;
	}
}


class ComputeStateFunction implements Function<Long, String>, Serializable {
	
	private static final long serialVersionUID = 1L;

	public String apply(Long value) {
		Bundle bundle = FrameworkUtil.getBundle(getClass());
		Bundle target = bundle.getBundleContext().getBundle(value);
		return BundleState.fromState(target.getState()).name();
	}
}

class ComputeBundleLabelClass implements Function<Long, String>, Serializable {
	
	private static final long serialVersionUID = 1L;

	public String apply(Long value) {
		Bundle bundle = FrameworkUtil.getBundle(getClass());
		Bundle target = bundle.getBundleContext().getBundle(value);
		return "label label-"+BundleState.fromState(target.getState()).getLabelClass();
	}
}