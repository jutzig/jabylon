/**
 * 
 */
package de.jutzig.jabylon.rest.ui.wicket.panels;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import de.jutzig.jabylon.properties.ProjectLocale;
import de.jutzig.jabylon.properties.PropertiesPackage;
import de.jutzig.jabylon.properties.Resolvable;
import de.jutzig.jabylon.properties.Workspace;
import de.jutzig.jabylon.properties.util.PropertiesSwitch;
import de.jutzig.jabylon.rest.ui.model.ComplexEObjectListDataProvider;
import de.jutzig.jabylon.rest.ui.wicket.BasicResolvablePanel;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 * 
 */
public class ProjectResourcePanel extends BasicResolvablePanel<Resolvable<?, ?>> {



	public ProjectResourcePanel(Resolvable<?, ?> object, PageParameters parameters) {
		super("ProjectResourcePanel", object, parameters);
	}

	@Override
	protected void onBeforeRender() {
		ComplexEObjectListDataProvider<Resolvable<?, ?>> provider = new ComplexEObjectListDataProvider<Resolvable<?, ?>>(getModelObject(),
				PropertiesPackage.Literals.RESOLVABLE__CHILDREN);
		final DataView<Resolvable<?, ?>> dataView = new DataView<Resolvable<?, ?>>("children", provider) {

			private static final long serialVersionUID = -3530355534807668227L;

			@Override
			protected void populateItem(Item<Resolvable<?, ?>> item) {
				Resolvable<?, ?> resolvable = item.getModelObject();
				String label = new LabelSwitch().doSwitch(resolvable);
				String linkTarget;
				if(resolvable.getParent()==null)
					linkTarget = "/";
				else if(resolvable.getParent() instanceof Workspace)
					linkTarget= urlEndsOnSlash() ? resolvable.getName() : "workspace/" + resolvable.getName();
				else
					linkTarget= urlEndsOnSlash() ? resolvable.getName() : resolvable.getParent().getName() + "/" + resolvable.getName();
				ExternalLink link = new ExternalLink("link", linkTarget, label);
				item.add(link);
				Label progress = new Label("progress", "");
				progress.add(new AttributeModifier("style", "width: " + resolvable.getPercentComplete() + "%"));
				item.add(progress);
			}
		};
		// dataView.setItemsPerPage(10);
		add(dataView);
		super.onBeforeRender();
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
