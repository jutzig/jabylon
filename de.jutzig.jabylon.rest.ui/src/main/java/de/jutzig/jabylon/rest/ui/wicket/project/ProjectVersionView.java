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
import de.jutzig.jabylon.properties.ProjectLocale;
import de.jutzig.jabylon.properties.ProjectVersion;
import de.jutzig.jabylon.properties.PropertiesPackage;
import de.jutzig.jabylon.rest.ui.Activator;
import de.jutzig.jabylon.rest.ui.model.ComplexEObjectListDataProvider;
import de.jutzig.jabylon.rest.ui.wicket.BasicResolvablePage;


public class ProjectVersionView
    extends BasicResolvablePage<ProjectVersion>
{
    public ProjectVersionView(PageParameters params)
    {
        List<NamedPair> named = params.getAllNamed();
        List<String> segments = new ArrayList<String>(named.size());
        for (NamedPair namedPair : named)
        {
            segments.add(namedPair.getValue());
        }
        final ProjectVersion projectVersion = Activator.getDefault().getRepositoryLookup().lookup(segments);
        setDomainObject(projectVersion);
        ComplexEObjectListDataProvider<ProjectLocale> provider = new ComplexEObjectListDataProvider<ProjectLocale>(projectVersion, PropertiesPackage.Literals.RESOLVABLE__CHILDREN);
        final DataView<ProjectLocale> dataView = new DataView<ProjectLocale>("projectLocales", provider) {

			private static final long serialVersionUID = -3530355534807668227L;

			@Override
            protected void populateItem(Item<ProjectLocale> item)
            {
            	ProjectLocale locale = item.getModelObject();
            	String language = locale.getLocale() == null ? "Template" : locale.getLocale().getDisplayName();
                ExternalLink link = new ExternalLink("link", locale.getName()+"/", language);
                item.add(link);
                Label label = new Label("progress", "");
                label.add(new AttributeModifier("style", "width: "+locale.getPercentComplete()+"%"));
                item.add(label);
            }
        };
//        dataView.setItemsPerPage(10);
        add(dataView);

    }
}
