package org.jabylon.rest.ui.wicket;

import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import org.jabylon.properties.Resolvable;
import org.jabylon.rest.ui.model.EObjectModel;

public class BasicResolvablePanel<T extends Resolvable<?, ?>> extends BasicPanel<T> {

    private static final long serialVersionUID = 1L;

    public BasicResolvablePanel(String id, T object, PageParameters parameters) {
        super(id,new EObjectModel<T>(object),parameters);
    }

    public BasicResolvablePanel(String id, IModel<T> model, PageParameters parameters) {
        super(id,model,parameters);
    }

}
