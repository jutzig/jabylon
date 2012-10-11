/**
 * 
 */
package de.jutzig.jabylon.rest.ui.wicket.config.sections;

import java.io.File;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.ajax.markup.html.IndicatingAjaxButton;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.eclipse.emf.cdo.transaction.CDOTransaction;
import org.eclipse.emf.cdo.util.CommitException;
import org.osgi.service.prefs.Preferences;

import de.jutzig.jabylon.common.util.FileUtil;
import de.jutzig.jabylon.properties.Project;
import de.jutzig.jabylon.properties.PropertiesPackage;
import de.jutzig.jabylon.properties.Workspace;
import de.jutzig.jabylon.rest.ui.Activator;
import de.jutzig.jabylon.rest.ui.model.ComplexEObjectListDataProvider;
import de.jutzig.jabylon.rest.ui.wicket.config.SettingsPage;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 *
 */
public class WorkspaceConfigSection extends GenericPanel<Workspace> {

	private static final long serialVersionUID = -5358263608301930488L;

	public WorkspaceConfigSection(String id, IModel<Workspace> object, Preferences prefs) {
		super(id, object);
		add(buildAddNewLink(object));
		ComplexEObjectListDataProvider<Project> provider = new ComplexEObjectListDataProvider<Project>(object.getObject(), PropertiesPackage.Literals.RESOLVABLE__CHILDREN);
		ListView<Project> project = new ListView<Project>("projects",provider) {

			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(ListItem<Project> item) {
				
				item.add(new ExternalLink("edit","workspace/"+item.getModelObject().getName()));
				item.add(new Label("project-name",item.getModelObject().getName()));
				item.add(createDeleteAction(item.getModel()));
			}
		};
		add(project);
	}

	private Component buildAddNewLink(IModel<Workspace> model) {
		PageParameters params = new PageParameters();
		params.add(SettingsPage.QUERY_PARAM_CREATE, PropertiesPackage.Literals.PROJECT.getName());
		return new BookmarkablePageLink<Void>("addNew", SettingsPage.class, params);
	}
	
	protected Component createDeleteAction(final IModel<Project> model) {

		Button button = new IndicatingAjaxButton("delete") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onAfterSubmit(AjaxRequestTarget target, Form<?> form) {

				Project project = model.getObject();
				CDOTransaction transaction = Activator.getDefault().getRepositoryConnector().openTransaction();
				project = transaction.getObject(project);

				try {
					File directory = new File(project.absolutPath().toFileString());
					FileUtil.delete(directory);
					project.getParent().getChildren().remove(project);
					transaction.commit();
					setResponsePage(SettingsPage.class);
				} catch (CommitException e) {
					// TODO Auto-generated catch block
					getSession().error(e.getMessage());
					e.printStackTrace();
				} finally {
					transaction.close();
				}
			}


		};
//		button.add(new AttributeModifier("onclick", "return confirm('Are you sure you want to delete this version?');"));
		button.setDefaultFormProcessing(false);
		return button;
	}

}
