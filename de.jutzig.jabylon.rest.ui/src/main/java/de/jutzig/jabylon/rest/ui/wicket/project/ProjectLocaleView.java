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
import de.jutzig.jabylon.properties.Resolvable;
import de.jutzig.jabylon.rest.ui.Activator;
import de.jutzig.jabylon.rest.ui.model.ComplexEObjectListDataProvider;
import de.jutzig.jabylon.rest.ui.wicket.BasicResolvablePage;


public class ProjectLocaleView
    extends BasicResolvablePage<ProjectLocale>
{
    public ProjectLocaleView(PageParameters params)
    {
        List<NamedPair> named = params.getAllNamed();
        List<String> segments = new ArrayList<String>(named.size());
        for (NamedPair namedPair : named)
        {
            segments.add(namedPair.getValue());
        }
        final ProjectLocale projectLocale = Activator.getDefault().getRepositoryLookup().lookup(segments);
        setDomainObject(projectLocale);
        ComplexEObjectListDataProvider<Resolvable<?, ?>> provider = new ComplexEObjectListDataProvider<Resolvable<?,?>>(projectLocale, PropertiesPackage.Literals.RESOLVABLE__CHILDREN);
        final DataView<Resolvable<?, ?>> dataView = new DataView<Resolvable<?, ?>>("resources", provider) {

			private static final long serialVersionUID = -3530355534807668227L;

			@Override
            protected void populateItem(Item<Resolvable<?, ?>> item)
            {
				Resolvable<?, ?> resource = item.getModelObject();
                ExternalLink link = new ExternalLink("link", resource.getName()+"/", resource.getName());
                item.add(link);
                Label label = new Label("progress", "");
                label.add(new AttributeModifier("style", "width: "+resource.getPercentComplete()+"%"));
                item.add(label);
            }
        };
        add(dataView);

    }
}
