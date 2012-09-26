package de.jutzig.jabylon.rest.ui.wicket;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.Page;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.string.StringValue;

import de.jutzig.jabylon.properties.Resolvable;
import de.jutzig.jabylon.rest.ui.Activator;
import de.jutzig.jabylon.rest.ui.wicket.panels.ProjectResourcePanel;

public class GenericPage<T extends Resolvable<?, ?>> extends WebPage {

	
	private transient T model;

	public GenericPage(PageParameters parameters) {
		super(parameters);
		model = resolveModel(parameters);
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

	
	public T getModel() {
		return model;
	}
	
}
