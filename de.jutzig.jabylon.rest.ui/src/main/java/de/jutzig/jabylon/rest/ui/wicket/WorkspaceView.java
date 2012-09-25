/**
 *
 */
package de.jutzig.jabylon.rest.ui.wicket;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.ResourceLink;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.request.Url;
import org.apache.wicket.request.resource.UrlResourceReference;

import de.jutzig.jabylon.properties.Project;
import de.jutzig.jabylon.properties.PropertiesPackage;
import de.jutzig.jabylon.properties.Workspace;
import de.jutzig.jabylon.rest.ui.Activator;
import de.jutzig.jabylon.rest.ui.model.ComplexEObjectListDataProvider;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 *
 */
public class WorkspaceView extends BasicPage {
	public WorkspaceView() {
		// add(new Label("message", "Hello World!"));
	    Workspace workspace = Activator.getDefault().getRepositoryLookup().lookup("");

		ComplexEObjectListDataProvider<Project> provider = new ComplexEObjectListDataProvider<Project>(workspace, PropertiesPackage.Literals.RESOLVABLE__CHILDREN);
		final DataView<Project> dataView = new DataView<Project>("projects", provider) {

			private static final long serialVersionUID = 6913939295181516945L;
            @Override
            protected void populateItem(Item<Project> item)
            {
                Project project = item.getModelObject();
                ResourceLink<?> link = new ResourceLink<String>("link",new UrlResourceReference(Url.parse("workspace/"+project.getName())));
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
