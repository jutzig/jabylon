package de.jutzig.jabylon.rest.ui.wicket;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.string.StringValue;

import de.jutzig.jabylon.properties.Resolvable;
import de.jutzig.jabylon.rest.ui.Activator;
import de.jutzig.jabylon.rest.ui.model.EObjectModel;
import de.jutzig.jabylon.rest.ui.navbar.NavbarPanel;
import de.jutzig.jabylon.rest.ui.wicket.components.CustomFeedbackPanel;

public class GenericPage<T extends Resolvable<?, ?>> extends WebPage {

	
	private EObjectModel<T> model;

	public GenericPage(PageParameters parameters) {
		super(parameters);
		CustomFeedbackPanel feedbackPanel = new CustomFeedbackPanel("feedbackPanel");
		add(feedbackPanel);
		model = new EObjectModel<T>(resolveModel(parameters));
		add(new NavbarPanel<Resolvable<?,?>>("navbar", model.getObject(), parameters));
	}
	
	private final T resolveModel(PageParameters params) {
		List<String> segments = new ArrayList<String>(params.getIndexedCount());
		for (StringValue value : params.getValues("segment")) {
			if (value.toString() != null && !value.toString().isEmpty())
				segments.add(value.toString());
		}
		
		for (int i = 0; i < params.getIndexedCount(); i++) {
			StringValue value = params.get(i);
			if (value.toString() != null && !value.toString().isEmpty())
				segments.add(value.toString());
		}
		return Activator.getDefault().getRepositoryLookup().lookup(segments);
		
	}

	
	public T getModelObject() {
		return model.getObject();
	}
	
	public IModel<T> getModel()
	{
		return model;
	}
	
	@Override
	protected void onBeforeRender() {

		setStatelessHint(true);
		super.onBeforeRender();
	}
	
	@Override
	public void detachModels() {
		super.detachModels();
		model.detach();
	}
	
}
