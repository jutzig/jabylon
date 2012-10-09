package de.jutzig.jabylon.rest.ui.wicket.config.impl;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.IModel;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.emf.cdo.transaction.CDOTransaction;
import org.eclipse.emf.cdo.util.CommitException;
import org.eclipse.emf.cdo.view.CDOView;
import org.osgi.service.prefs.Preferences;

import de.jutzig.jabylon.common.progress.RunnableWithProgress;
import de.jutzig.jabylon.common.team.TeamProvider;
import de.jutzig.jabylon.common.team.TeamProviderUtil;
import de.jutzig.jabylon.common.util.PreferencesUtil;
import de.jutzig.jabylon.properties.Project;
import de.jutzig.jabylon.properties.ProjectVersion;
import de.jutzig.jabylon.properties.PropertiesPackage;
import de.jutzig.jabylon.properties.PropertyFileDiff;
import de.jutzig.jabylon.properties.ScanConfiguration;
import de.jutzig.jabylon.rest.ui.Activator;
import de.jutzig.jabylon.rest.ui.model.ComplexEObjectListDataProvider;
import de.jutzig.jabylon.rest.ui.model.ProgressionModel;
import de.jutzig.jabylon.rest.ui.wicket.components.ProgressPanel;
import de.jutzig.jabylon.rest.ui.wicket.components.ProgressShowingAjaxButton;
import de.jutzig.jabylon.rest.ui.wicket.config.AbstractConfigSection;

public class ProjectVersionsConfigSection extends GenericPanel<Project> {

	private static final long serialVersionUID = 1L;

	public ProjectVersionsConfigSection(String id, IModel<Project> model, Preferences config) {
		super(id, model);
		ComplexEObjectListDataProvider<ProjectVersion> provider = new ComplexEObjectListDataProvider<ProjectVersion>(model.getObject(),
				PropertiesPackage.Literals.RESOLVABLE__CHILDREN);
		ListView<ProjectVersion> project = new ListView<ProjectVersion>("versions", provider) {

			private static final long serialVersionUID = 1L;
			private ProgressionModel progressModel;

			@Override
			protected void populateItem(ListItem<ProjectVersion> item) {
				item.setOutputMarkupId(true);
				item.add(new Label("name", item.getModelObject().getName()));

				progressModel = new ProgressionModel(-1);
				final ProgressPanel progressPanel = new ProgressPanel("progress", progressModel);
				item.add(progressPanel);

				item.add(createCheckoutAction(progressPanel, item.getModel()));
				item.add(createRescanAction(progressPanel, item.getModel()));
				item.add(createUpdateAction(progressPanel, item.getModel()));
//				item.add(createCommitAction(progressPanel, item.getModel()));

			}

		};
		project.setOutputMarkupId(true);
		add(project);
	}

	protected Component createCommitAction(ProgressPanel progressPanel, IModel<ProjectVersion> model) {
		// TODO Auto-generated method stub
		return null;
	}

	protected Component createUpdateAction(ProgressPanel progressPanel, final IModel<ProjectVersion> model) {
		RunnableWithProgress runnable = new RunnableWithProgress() {

			private static final long serialVersionUID = 1L;

			@Override
			public void run(IProgressMonitor monitor) {
				ProjectVersion version = model.getObject();
				TeamProvider provider = TeamProviderUtil.getTeamProvider(version.getParent().getTeamProvider());
				CDOTransaction transaction = Activator.getDefault().getRepositoryConnector().openTransaction();
				try {
					version = transaction.getObject(version);
					SubMonitor subMonitor = SubMonitor.convert(monitor,"Updating",100);
					Collection<PropertyFileDiff> updates = provider.update(version, subMonitor.newChild(50));
					subMonitor.setWorkRemaining(updates.size()*2);
					subMonitor.subTask("Processing updates");
					for (PropertyFileDiff updatedFile : updates) {
						version.partialScan(PreferencesUtil.getScanConfigForProject(getModelObject()), updatedFile);
						subMonitor.worked(1);
					}
					subMonitor.setTaskName("Database Sync");
					transaction.commit(subMonitor.newChild(updates.size()));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					getSession().error(e.getMessage());
					e.printStackTrace();
				} catch (CommitException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally{
					transaction.close();
				}

			}
		};
		return new ProgressShowingAjaxButton("update", progressPanel, runnable) {

			private static final long serialVersionUID = 1L;

			public boolean isVisible() {
				ProjectVersion version = model.getObject();
				TeamProvider provider = TeamProviderUtil.getTeamProvider(version.getParent().getTeamProvider());
				if (provider == null)
					return false;
				File file = new File(version.absoluteFilePath().toFileString());
				return (file.isDirectory());
			};
		};

	}

	private Component createCheckoutAction(ProgressPanel progressPanel, final IModel<ProjectVersion> model) {
		RunnableWithProgress runnable = new RunnableWithProgress() {

			private static final long serialVersionUID = 1L;

			@Override
			public void run(IProgressMonitor monitor) {
				ProjectVersion version = model.getObject();
				TeamProvider provider = TeamProviderUtil.getTeamProvider(version.getParent().getTeamProvider());
				try {
					provider.checkout(version, monitor);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					getSession().error(e.getMessage());
					e.printStackTrace();
				}

			}
		};
		return new ProgressShowingAjaxButton("checkout", progressPanel, runnable) {

			private static final long serialVersionUID = 1L;

			public boolean isVisible() {
				ProjectVersion version = model.getObject();
				TeamProvider provider = TeamProviderUtil.getTeamProvider(version.getParent().getTeamProvider());
				if (provider == null)
					return false;
				File file = new File(version.absoluteFilePath().toFileString());
				return (!file.isDirectory());
			};
		};

	}

	private Component createRescanAction(ProgressPanel progressPanel, final IModel<ProjectVersion> model) {
		RunnableWithProgress runnable = new RunnableWithProgress() {

			private static final long serialVersionUID = 1L;

			@Override
			public void run(IProgressMonitor monitor) {
				ScanConfiguration scanConfiguration = PreferencesUtil.getScanConfigForProject(getModelObject());
				ProjectVersion version = model.getObject();
				SubMonitor subMonitor = SubMonitor.convert(monitor, "Scanning", 100);
				CDOTransaction transaction = Activator.getDefault().getRepositoryConnector().openTransaction();
				version = transaction.getObject(version);
				version.fullScan(scanConfiguration, subMonitor.newChild(50));
				subMonitor.setTaskName("Database Sync");
				try {
					transaction.commit(subMonitor.newChild(50));
				} catch (CommitException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally{
					transaction.close();
				}
				monitor.done();
			}
		};
		return new ProgressShowingAjaxButton("rescan", progressPanel, runnable) {

			private static final long serialVersionUID = 1L;

			public boolean isVisible() {
				// ProjectVersion version = model.getObject();
				// File file = new
				// File(version.absoluteFilePath().toFileString());
				// return (file.isDirectory());
				return true;
			};
		};

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