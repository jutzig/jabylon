package de.jutzig.jabylon.updatecenter.repository.ui;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.felix.bundlerepository.Resource;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.StatelessForm;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Function;

import de.jutzig.jabylon.rest.ui.model.ComputableModel;
import de.jutzig.jabylon.rest.ui.wicket.BasicPanel;
import de.jutzig.jabylon.updatecenter.repository.OBRRepositoryService;
import de.jutzig.jabylon.updatecenter.repository.ResourceFilter;

public class UpdatecenterTabContent extends BasicPanel<ResourceFilter> {

    private static final long serialVersionUID = 1L;
    @Inject
    private transient OBRRepositoryService repositoryConnector;
    private IModel<List<ResourceWrapper>> resources;
    private static final Logger logger = LoggerFactory.getLogger(UpdatecenterTabContent.class);

    public UpdatecenterTabContent(String id, IModel<ResourceFilter> model, PageParameters parameters) {
        super(id, model, parameters);
        resources = new ComputableModel<IModel<ResourceFilter>, List<ResourceWrapper>>(new LoadBundlesFunction(),model);
        Form<Void> form = new StatelessForm<Void>("form") {

            private static final long serialVersionUID = 1L;

            @Override
            protected void onSubmit() {
                super.onSubmit();
                List<ResourceWrapper> list = resources.getObject();
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
                repositoryConnector.install(toBeChanged.toArray(new Resource[toBeChanged.size()]));
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
                item.add(new Label("description", resource.getPresentationName()));
                item.add(new Label("version", resource.getVersion().toString()));
                item.add(new CheckBox("install",new PropertyModel<Boolean>(item.getModel(),"checked")));
            }
        };
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

    public String getId() {
        return id;
    }


}
