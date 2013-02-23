/**
 * 
 */
package de.jutzig.jabylon.rest.ui.wicket.pages;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.wicket.model.IModel;
import org.apache.wicket.request.http.flow.AbortWithHttpErrorCodeException;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.string.StringValue;

import de.jutzig.jabylon.properties.Resolvable;
import de.jutzig.jabylon.rest.ui.model.EObjectModel;
import de.jutzig.jabylon.rest.ui.model.IEObjectModel;
import de.jutzig.jabylon.rest.ui.model.RepositoryLookup;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 *
 */
public class GenericResolvablePage<T  extends Resolvable<?, ?>> extends GenericPage<T> {

	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private transient RepositoryLookup lookup;
	
	public GenericResolvablePage(PageParameters parameters) {
		super(parameters);
	}

	public void setModel(IEObjectModel<T> model) {
		super.setModel(model);
	}

	protected IEObjectModel<T> createModel(T object) {
		return new EObjectModel<T>(object);
	}
	
	public IEObjectModel<T> getModel() {
		return (IEObjectModel<T>) super.getModel();
	}
	
	
	@Override
	protected void preConstruct() {
		T modelObject = getModelObject();
		if(modelObject==null)
		{ 
			throw new AbortWithHttpErrorCodeException(404, "Path does not exist "+getPageParameters());
		}
	}
	
	@Override
	protected IModel<T> createModel(PageParameters params) {
		return createModel(resolveModel(params));
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
//		RepositoryLookup repositoryLookup = Activator.getDefault().getRepositoryLookup();
		return lookup.lookup(segments);
	}
	
	public RepositoryLookup getLookup() {
		return lookup;
	}


}
