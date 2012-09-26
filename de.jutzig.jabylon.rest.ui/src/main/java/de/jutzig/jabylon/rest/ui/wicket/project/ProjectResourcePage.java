/**
 * 
 */
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
import org.apache.wicket.util.string.StringValue;

import de.jutzig.jabylon.properties.ProjectLocale;
import de.jutzig.jabylon.properties.PropertiesPackage;
import de.jutzig.jabylon.properties.Resolvable;
import de.jutzig.jabylon.properties.Workspace;
import de.jutzig.jabylon.properties.util.PropertiesSwitch;
import de.jutzig.jabylon.rest.ui.Activator;
import de.jutzig.jabylon.rest.ui.model.ComplexEObjectListDataProvider;
import de.jutzig.jabylon.rest.ui.wicket.BasicResolvablePage;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 * 
 */
public class ProjectResourcePage extends BasicResolvablePage<Resolvable<?, ?>> {

	public ProjectResourcePage(PageParameters params) {
		super(params);
		// List<NamedPair> named = params.getAllNamed();
		List<String> segments = new ArrayList<String>(params.getIndexedCount());
		boolean endsOnSlash = false;
		for (StringValue value : params.getValues("segment")) {
			if (value.toString() != null && !value.toString().isEmpty())
				segments.add(value.toString());
			else
				endsOnSlash = true;
		}
		
		// segments.addAll(params.getValues("segment"));
		for (int i = 0; i < params.getIndexedCount(); i++) {
			StringValue value = params.get(i);
			if (value.toString() != null && !value.toString().isEmpty())
				segments.add(value.toString());
			else
				endsOnSlash = true;
		}
		final String lastSegment = segments.isEmpty() || endsOnSlash ? "" : segments.get(segments.size() - 1);
		// for (NamedPair namedPair : named)
		// {
		// segments.add(namedPair.getValue());
		// }

		final Resolvable<?, ?> resolvable = Activator.getDefault().getRepositoryLookup().lookup(segments);
		setDomainObject(resolvable);
		ComplexEObjectListDataProvider<Resolvable<?, ?>> provider = new ComplexEObjectListDataProvider<Resolvable<?, ?>>(resolvable,
				PropertiesPackage.Literals.RESOLVABLE__CHILDREN);
		final DataView<Resolvable<?, ?>> dataView = new DataView<Resolvable<?, ?>>("children", provider) {

			private static final long serialVersionUID = -3530355534807668227L;

			@Override
			protected void populateItem(Item<Resolvable<?, ?>> item) {
				Resolvable<?, ?> resolvable = item.getModelObject();
				String label = new LabelSwitch().doSwitch(resolvable);
				String linkTarget = lastSegment.isEmpty() ? resolvable.getName() : lastSegment + "/" + resolvable.getName();
				ExternalLink link = new ExternalLink("link", linkTarget, label);
				item.add(link);
				Label progress = new Label("progress", "");
				progress.add(new AttributeModifier("style", "width: " + resolvable.getPercentComplete() + "%"));
				item.add(progress);
			}
		};
		// dataView.setItemsPerPage(10);
		add(dataView);
	}

}

class LabelSwitch extends PropertiesSwitch<String> {
	@Override
	public <P extends Resolvable<?, ?>, C extends Resolvable<?, ?>> String caseResolvable(Resolvable<P, C> object) {
		return object.getName();
	}

	@Override
	public String caseProjectLocale(ProjectLocale object) {
		if (object.getLocale() != null)
			return object.getLocale().getDisplayName();
		return "Template";
	}

	@Override
	public String caseWorkspace(Workspace object) {
		return "Workspace";
	}
}
