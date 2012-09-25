package de.jutzig.jabylon.rest.ui.wicket.project;


import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.ResourceLink;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.request.Url;
import org.apache.wicket.request.mapper.parameter.INamedParameters.NamedPair;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.UrlResourceReference;

import de.jutzig.jabylon.properties.Project;
import de.jutzig.jabylon.properties.ProjectVersion;
import de.jutzig.jabylon.properties.PropertiesPackage;
import de.jutzig.jabylon.rest.ui.Activator;
import de.jutzig.jabylon.rest.ui.model.ComplexEObjectListDataProvider;
import de.jutzig.jabylon.rest.ui.wicket.BasicPage;


public class ProjectView
    extends BasicPage
{
    public ProjectView(PageParameters params)
    {
        List<NamedPair> named = params.getAllNamed();
        List<String> segments = new ArrayList<String>(named.size());
        for (NamedPair namedPair : named)
        {
            segments.add(namedPair.getValue());
        }
        Project project = Activator.getDefault().getRepositoryLookup().lookup(segments);
        ComplexEObjectListDataProvider<ProjectVersion> provider = new ComplexEObjectListDataProvider<ProjectVersion>(project, PropertiesPackage.Literals.RESOLVABLE__CHILDREN);
        final DataView<ProjectVersion> dataView = new DataView<ProjectVersion>("projectVersions", provider) {

            private static final long serialVersionUID = 6913939295181516945L;
            @Override
            protected void populateItem(Item<ProjectVersion> item)
            {
                ProjectVersion project = item.getModelObject();
                UrlResourceReference reference = new UrlResourceReference(Url.parse(project.getName()));
                reference.setContextRelative(true);
                ResourceLink<?> link = new ResourceLink<String>("link",reference);
                link.add(new Label("id", project.getName()));
                item.add(link);
                Label label = new Label("progress", "");
                label.add(new AttributeModifier("style", "width: "+project.getPercentComplete()));
                item.add(label);

            }
        };
        add(dataView);

    }
}
