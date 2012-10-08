package de.jutzig.jabylon.rest.ui.wicket.config.impl;

import java.io.File;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.IModel;
import org.osgi.service.prefs.Preferences;

import de.jutzig.jabylon.common.util.PreferencesUtil;
import de.jutzig.jabylon.properties.Project;
import de.jutzig.jabylon.properties.ProjectVersion;
import de.jutzig.jabylon.properties.PropertiesPackage;
import de.jutzig.jabylon.rest.ui.model.ComplexEObjectListDataProvider;
import de.jutzig.jabylon.rest.ui.model.PreferencesPropertyModel;
import de.jutzig.jabylon.rest.ui.wicket.components.ProgressPanel;
import de.jutzig.jabylon.rest.ui.wicket.config.AbstractConfigSection;

public class ProjectVersionsConfigSection extends GenericPanel<Project> {

	private static final long serialVersionUID = 1L;

	public ProjectVersionsConfigSection(String id, IModel<Project> model, Preferences config) {
		super(id, model);
		ComplexEObjectListDataProvider<ProjectVersion> provider = new ComplexEObjectListDataProvider<ProjectVersion>(model.getObject(), PropertiesPackage.Literals.RESOLVABLE__CHILDREN);
		ListView<ProjectVersion> project = new ListView<ProjectVersion>("versions",provider) {

			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(ListItem<ProjectVersion> item) {
				item.add(new Label("name",item.getModelObject().getName()));
				Button button = new Button("checkout")
				{
					private static final long serialVersionUID = 1L;

					@Override
					public void onSubmit() {
						super.onSubmit();
						
					}
				};
				ProjectVersion version = item.getModelObject();
//				File file = new File(version.absoluteFilePath().toFileString());
//				
//				button.setVisibilityAllowed(!file.exists());
				
				item.add(button);
				item.add(new ProgressPanel("progress"));
				
			}
		};
		add(project);
	}

	public static class VersionsConfig extends AbstractConfigSection<Project> {

		private static final long serialVersionUID = 1L;

		@Override
		public WebMarkupContainer createContents(String id, IModel<Project> input, Preferences prefs) {
			return new ProjectVersionsConfigSection(id, input, prefs);
		}

		@Override
		public void commit(IModel<Project> input, Preferences config) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public boolean hasFormComponents() {
			return false;
		}
	}
}