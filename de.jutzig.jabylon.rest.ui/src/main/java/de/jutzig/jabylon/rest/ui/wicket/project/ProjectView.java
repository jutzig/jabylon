package de.jutzig.jabylon.rest.ui.wicket.project;


import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.request.mapper.parameter.INamedParameters.NamedPair;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import de.jutzig.jabylon.properties.Project;
import de.jutzig.jabylon.properties.ProjectVersion;
import de.jutzig.jabylon.properties.PropertiesPackage;
import de.jutzig.jabylon.rest.ui.Activator;
import de.jutzig.jabylon.rest.ui.model.ComplexEObjectListDataProvider;
import de.jutzig.jabylon.rest.ui.wicket.BasicResolvablePage;


public class ProjectView
    extends BasicResolvablePage<Project>
{
    public ProjectView(PageParameters params)
    {
        List<NamedPair> named = params.getAllNamed();
        List<String> segments = new ArrayList<String>(named.size());
        for (NamedPair namedPair : named)
        {
            segments.add(namedPair.getValue());
        }
        final Project project = Activator.getDefault().getRepositoryLookup().lookup(segments);
        setDomainObject(project);
        ComplexEObjectListDataProvider<ProjectVersion> provider = new ComplexEObjectListDataProvider<ProjectVersion>(project, PropertiesPackage.Literals.RESOLVABLE__CHILDREN);
        final DataView<ProjectVersion> dataView = new DataView<ProjectVersion>("projectVersions", provider) {

            private static final long serialVersionUID = 6913939295181516945L;
            @Override
            protected void populateItem(Item<ProjectVersion> item)
            {
                ProjectVersion version = item.getModelObject();
                ExternalLink link = new ExternalLink("link", version.getName()+"/", version.getName());
                item.add(link);
                Label label = new Label("progress", "");
                label.add(new AttributeModifier("style", "width: "+version.getPercentComplete()+"%"));
                item.add(label);

            }
        };
        add(dataView);

    }
}
