package de.jutzig.jabylon.rest.ui.wicket;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.PriorityHeaderItem;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.http.flow.AbortWithHttpErrorCodeException;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.string.StringValue;

import de.jutzig.jabylon.properties.Resolvable;
import de.jutzig.jabylon.rest.ui.Activator;
import de.jutzig.jabylon.rest.ui.model.EObjectModel;
import de.jutzig.jabylon.rest.ui.model.IEObjectModel;
import de.jutzig.jabylon.rest.ui.model.RepositoryLookup;
import de.jutzig.jabylon.rest.ui.navbar.NavbarPanel;
import de.jutzig.jabylon.rest.ui.wicket.components.CustomFeedbackPanel;

public class GenericPage<T extends Resolvable<?, ?>> extends WebPage {


	private static final long serialVersionUID = 1L;
	
	private IEObjectModel<T> model;

	public GenericPage(PageParameters parameters) {
		super(parameters);
		CustomFeedbackPanel feedbackPanel = new CustomFeedbackPanel("feedbackPanel");
		add(feedbackPanel);
		T modelObject = resolveModel(parameters);
		if(modelObject==null)
		{ 
			throw new AbortWithHttpErrorCodeException(404, "Path does not exist "+parameters);
		}
		else
		{
			model = createModel(modelObject);
			add(new NavbarPanel<Resolvable<?,?>>("navbar", model.getObject(), parameters));			
		}
	}
	
	@Override
	public void renderHead(IHeaderResponse response) {
		response.render(new PriorityHeaderItem(JavaScriptHeaderItem.forReference(JabylonApplication.get().getJavaScriptLibrarySettings().getJQueryReference())));
		response.render(new PriorityHeaderItem(JavaScriptHeaderItem.forUrl("/jabylon/bootstrap/js/bootstrap.min.js")));
		super.renderHead(response);
	}
	

	
	public void setModel(IEObjectModel<T> model) {
		this.model = model;
	}

	protected IEObjectModel<T> createModel(T object) {
		return new EObjectModel<T>(object);
	}

	@SuppressWarnings("unchecked")
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
		Resolvable< ? , ? > lookup = doLookup(segments);
		return (T)lookup;

	}

	protected Resolvable<?, ?> doLookup(List<String> segments) {
		RepositoryLookup repositoryLookup = Activator.getDefault().getRepositoryLookup();
		return repositoryLookup.lookup(segments);
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
		if(model!=null)
			model.detach();
	}

}
