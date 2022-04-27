/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.updatecenter.repository.ui;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.felix.bundlerepository.Resource;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.jabylon.rest.ui.wicket.BasicPanel;
import org.jabylon.updatecenter.repository.OBRException;
import org.jabylon.updatecenter.repository.OBRRepositoryService;
import org.jabylon.updatecenter.repository.ResourceFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Function;

public class UpdatecenterTabContent extends BasicPanel<ResourceFilter> {

    private static final long serialVersionUID = 1L;
    @Inject
    private OBRRepositoryService repositoryConnector;
    private IModel<List<ResourceWrapper>> resources;
    private static final Logger logger = LoggerFactory.getLogger(UpdatecenterTabContent.class);

    public UpdatecenterTabContent(String id, IModel<ResourceFilter> model, PageParameters parameters) {
        super(id, model, parameters);
        resources = Model.ofList(new LoadBundlesFunction().apply(model));
        Form<Void> form = new Form<Void>("form") {

            private static final long serialVersionUID = 1L;

            @Override
            protected void onSubmit() {
                super.onSubmit();
                List<? extends ResourceWrapper> list = resources.getObject();
                List<Resource> toBeChanged = new ArrayList<Resource>();
                for (ResourceWrapper wrapper : list) {
                    if(!wrapper.isChecked())
                        continue;
                    Resource[] resources = repositoryConnector.findResources(wrapper.getId());
                    for (Resource resource2 : resources) {
                        logger.info("Installing Resource {}", resource2.getSymbolicName());
                        toBeChanged.add(resource2);
                    }
                }
                try {
					repositoryConnector.install(toBeChanged.toArray(new Resource[toBeChanged.size()]));
				} catch (OBRException e) {
					if(e.getCause()!=null)
						getSession().error(e.getCause().getMessage());
					else
						getSession().error(e.getMessage());
				}
                if(!toBeChanged.isEmpty()) {
                	//get rid of the page version to get a full refresh
                	PageParameters parameters = new PageParameters();
                	parameters.set(0, "settings");
                	setResponsePage(getPage().getClass(),parameters);
                }
            }
        };
        add(form);
        ListView<ResourceWrapper> resourceView = new ListView<ResourceWrapper>("row", resources) {

            private static final long serialVersionUID = 1L;

            @Override
            protected void populateItem(ListItem<ResourceWrapper> item) {
                ResourceWrapper resourceWrapper = item.getModelObject();

                Resource resource = resourceWrapper.getResource();
                String name = resource.getSymbolicName();
                item.add(new Label("name", name));
                String description = (String) resource.getProperties().get(Resource.DESCRIPTION);
                if(description==null)
                	description = resource.getPresentationName();
                item.add(new Label("description", description));
                item.add(new Label("version", resource.getVersion().toString()));
                item.add(new CheckBox("install",new PropertyModel<Boolean>(item.getModel(),"checked")));
            }
        };
        resourceView.setReuseItems(true);
        form.add(resourceView);
    }

    private class LoadBundlesFunction implements Function<IModel<ResourceFilter>, List<ResourceWrapper>>, Serializable {

        private static final long serialVersionUID = 1L;

        public List<ResourceWrapper> apply(IModel<ResourceFilter> model) {
            List<Resource> list = repositoryConnector.getAvailableResources(model.getObject());
            List<ResourceWrapper> result = new ArrayList<ResourceWrapper>(list.size());
            for (Resource resource : list) {
                result.add(new ResourceWrapper(resource));
            }
            return result;
        }
    }
}


class ResourceWrapper implements Serializable
{

    private static final long serialVersionUID = 1L;
    private transient Resource resource;
    private boolean checked;
    private String name;
    private String version;
    private String id;
    public ResourceWrapper(Resource resource) {
        super();
        this.resource = resource;
        this.name = resource.getSymbolicName();
        this.version = resource.getVersion().toString();
        this.id = resource.getId();
    }

    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }

    public Resource getResource() {
        return resource;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
		this.checked = checked;
	}

    public String getId() {
        return id;
    }


}
