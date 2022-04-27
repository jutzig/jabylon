/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.rest.ui.wicket.config.sections;

import java.io.File;
import java.util.Collection;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.AjaxCallListener;
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes;
import org.apache.wicket.extensions.ajax.markup.html.IndicatingAjaxButton;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.emf.cdo.CDOState;
import org.eclipse.emf.cdo.transaction.CDOTransaction;
import org.eclipse.emf.cdo.util.CommitException;
import org.jabylon.common.progress.RunnableWithProgress;
import org.jabylon.common.team.TeamProvider;
import org.jabylon.common.team.TeamProviderException;
import org.jabylon.common.team.TeamProviderUtil;
import org.jabylon.common.util.FileUtil;
import org.jabylon.common.util.PreferencesUtil;
import org.jabylon.properties.Project;
import org.jabylon.properties.ProjectVersion;
import org.jabylon.properties.PropertiesPackage;
import org.jabylon.properties.PropertyFileDiff;
import org.jabylon.properties.ScanConfiguration;
import org.jabylon.resources.persistence.PropertyPersistenceService;
import org.jabylon.rest.ui.Activator;
import org.jabylon.rest.ui.model.ComplexEObjectListDataProvider;
import org.jabylon.rest.ui.model.CustomStringResourceModel;
import org.jabylon.rest.ui.model.ProgressionModel;
import org.jabylon.rest.ui.util.WicketUtil;
import org.jabylon.rest.ui.wicket.BasicPanel;
import org.jabylon.rest.ui.wicket.components.ProgressPanel;
import org.jabylon.rest.ui.wicket.components.ProgressShowingAjaxButton;
import org.jabylon.rest.ui.wicket.config.AbstractConfigSection;
import org.jabylon.rest.ui.wicket.config.SettingsPage;
import org.jabylon.rest.ui.wicket.config.SettingsPanel;
import org.jabylon.security.CommonPermissions;
import org.osgi.service.prefs.BackingStoreException;
import org.osgi.service.prefs.Preferences;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProjectVersionsConfigSection extends BasicPanel<Project> {

    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(ProjectVersionsConfigSection.class);

    public ProjectVersionsConfigSection(String id, IModel<Project> model, Preferences config) {
        super(id, model);
        add(buildAddNewLink(model));
        ComplexEObjectListDataProvider<ProjectVersion> provider = new ComplexEObjectListDataProvider<ProjectVersion>(model,
                PropertiesPackage.Literals.RESOLVABLE__CHILDREN);
        ListView<ProjectVersion> project = new ListView<ProjectVersion>("versions", provider) {

            private static final long serialVersionUID = 1L;
            private ProgressionModel progressModel;

            @Override
            protected void populateItem(ListItem<ProjectVersion> item) {
                item.setOutputMarkupId(true);
                item.add(new Label("name", item.getModelObject().getName()));
                item.add(new Label("summary", new CustomStringResourceModel("ProjectVersionsConfigSection.summary", item, null, item.getModel().getObject().getChildren().size())));
                progressModel = new ProgressionModel("");
                final ProgressPanel progressPanel = new ProgressPanel("progress", progressModel);

                item.add(progressPanel);
                ProjectVersion projectVersion = item.getModelObject();
                item.add(new BookmarkablePageLink<Void>("edit",SettingsPage.class,WicketUtil.buildPageParametersFor(projectVersion)));
                item.add(createCheckoutAction(progressPanel, item.getModel()));
                item.add(createRescanAction(progressPanel, item.getModel()));
                item.add(createUpdateAction(progressPanel, item.getModel()));
                item.add(createResetAction(progressPanel, item.getModel()));
                item.add(createCommitAction(progressPanel, item.getModel()));
                item.add(createDeleteAction(progressPanel, item.getModel()));

            }

        };
        project.setReuseItems(true);
        project.setOutputMarkupId(true);
        add(project);
    }

    private Component buildAddNewLink(IModel<Project> model) {
        Project project = model.getObject();
        if (project.cdoState() == CDOState.NEW || project.cdoState() == CDOState.TRANSIENT) {
            // it's a new project, we can't add anything yet
            Button link = new Button("addNew");
            link.setEnabled(false);
            return link;
        }
        PageParameters parameters = WicketUtil.buildPageParametersFor(project);
        parameters.add(SettingsPanel.QUERY_PARAM_CREATE, PropertiesPackage.Literals.PROJECT_VERSION.getName());
        return new BookmarkablePageLink<Void>("addNew", SettingsPage.class, parameters);
    }

    protected Component createDeleteAction(ProgressPanel progressPanel, final IModel<ProjectVersion> model) {

        Button button = new DeleteAction("delete", model, nls("ProjectVersionsConfigSection.delete.action.confirmation", model.getObject().getName()));
        button.setDefaultFormProcessing(false);
        return button;
    }


    protected Component createUpdateAction(ProgressPanel progressPanel, final IModel<ProjectVersion> model) {
        RunnableWithProgress runnable = new RunnableWithProgress() {

            private static final long serialVersionUID = 1L;

            @Override
            public IStatus run(IProgressMonitor monitor) {
                ProjectVersion version = model.getObject();
                TeamProvider provider = TeamProviderUtil.getTeamProvider(version.getParent().getTeamProvider());
                CDOTransaction transaction = Activator.getDefault().getRepositoryConnector().openTransaction();
                try {
                    version = transaction.getObject(version);
                    SubMonitor subMonitor = SubMonitor.convert(monitor, "Updating", 100);
                    Collection<PropertyFileDiff> updates = provider.update(version, subMonitor.newChild(50));
                    subMonitor.setWorkRemaining(updates.size() * 2);
                    subMonitor.subTask("Processing updates");
                    for (PropertyFileDiff updatedFile : updates) {
                        version.partialScan(PreferencesUtil.getScanConfigForProject(getModelObject()), updatedFile);
                        subMonitor.worked(1);
                    }
                    subMonitor.setTaskName("Database Sync");
                    transaction.commit(subMonitor.newChild(updates.size()));
                } catch (TeamProviderException e) {
                    logger.error("Update failed",e);
                    return new Status(IStatus.ERROR, Activator.BUNDLE_ID, "Update failed",e);
                } catch (CommitException e) {
                    logger.error("Failed to commit the transaction",e);
                    return new Status(IStatus.ERROR, Activator.BUNDLE_ID, "Failed to commit the transaction",e);
                } finally {
                	transaction.close();
                	PropertyPersistenceService persistenceService = Activator.getDefault().getPersistenceService();
                	if(persistenceService!=null)
                		persistenceService.clearCache();
                	else
                		logger.error("Could not obtain property persistence service");
                }
                return Status.OK_STATUS;

            }
        };
        return new ProgressShowingAjaxButton("update", progressPanel, runnable, nls("update.version.job.label",getModelObject().getName())) {

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

    protected Component createResetAction(ProgressPanel progressPanel, final IModel<ProjectVersion> model) {
        RunnableWithProgress runnable = new RunnableWithProgress() {

            private static final long serialVersionUID = 1L;

            @Override
            public IStatus run(IProgressMonitor monitor) {
                ProjectVersion version = model.getObject();
                TeamProvider provider = TeamProviderUtil.getTeamProvider(version.getParent().getTeamProvider());
                CDOTransaction transaction = Activator.getDefault().getRepositoryConnector().openTransaction();
                try {
                    version = transaction.getObject(version);
                    SubMonitor subMonitor = SubMonitor.convert(monitor, "Resetting", 100);
                    Collection<PropertyFileDiff> updates = provider.reset(version, subMonitor.newChild(50));
                    subMonitor.setWorkRemaining(updates.size() * 2);
                    subMonitor.subTask("Processing diff");
                    for (PropertyFileDiff updatedFile : updates) {
                        version.partialScan(PreferencesUtil.getScanConfigForProject(getModelObject()), updatedFile);
                        subMonitor.worked(1);
                    }
                    subMonitor.setTaskName("Database Sync");
                    transaction.commit(subMonitor.newChild(updates.size()));
                } catch (TeamProviderException e) {
                    logger.error("Update failed",e);
                    return new Status(IStatus.ERROR, Activator.BUNDLE_ID, "Reset failed",e);
                } catch (CommitException e) {
                    logger.error("Failed to commit the transaction",e);
                    return new Status(IStatus.ERROR, Activator.BUNDLE_ID, "Failed to commit the transaction",e);
                } finally {
                	transaction.close();
                	PropertyPersistenceService persistenceService = Activator.getDefault().getPersistenceService();
                	if(persistenceService!=null)
                		persistenceService.clearCache();
                	else
                		logger.error("Could not obtain property persistence service");
                }
                return Status.OK_STATUS;

            }
        };
        return new ProgressShowingAjaxButton("reset", progressPanel, runnable, nls("reset.version.job.label",getModelObject().getName())) {

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

    protected Component createCommitAction(ProgressPanel progressPanel, final IModel<ProjectVersion> model)
    {
        RunnableWithProgress runnable = new RunnableWithProgress()
        {
            private static final long serialVersionUID = 1L;

            @Override
            public IStatus run(IProgressMonitor monitor)
            {

                ProjectVersion version = model.getObject();
                TeamProvider provider = TeamProviderUtil.getTeamProvider(version.getParent()
                                                                                .getTeamProvider());
                CDOTransaction transaction = Activator.getDefault()
                                                      .getRepositoryConnector()
                                                      .openTransaction();
                try
                {
                    version = transaction.getObject(version);
                    SubMonitor subMonitor = SubMonitor.convert(monitor, "Committing", 100);

                    provider.commit(version, subMonitor.newChild(100));

                }
                catch (TeamProviderException e)
                {
                    logger.error("Commit failed", e);
                    return new Status(IStatus.ERROR, Activator.BUNDLE_ID, "Commit Failed", e);
                }

                finally
                {
                    transaction.close();
                }

                return Status.OK_STATUS;
            }
        };
        return new ProgressShowingAjaxButton("commit", progressPanel, runnable, nls("commit.version.job.label",getModelObject().getName()))
        {

            private static final long serialVersionUID = 1L;


            public boolean isVisible()
            {
                ProjectVersion version = model.getObject();
                TeamProvider provider = TeamProviderUtil.getTeamProvider(version.getParent()
                                                                                .getTeamProvider());
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
            public IStatus run(IProgressMonitor monitor) {
                CDOTransaction transaction = Activator.getDefault().getRepositoryConnector().openTransaction();
                try {
                	SubMonitor subMonitor = SubMonitor.convert(monitor,100);
                    ProjectVersion version = model.getObject();
                    version = transaction.getObject(version);
                    TeamProvider provider = TeamProviderUtil.getTeamProvider(version.getParent().getTeamProvider());
                    provider.checkout(version, subMonitor.newChild(75));
                    rescanProject(subMonitor.newChild(25), model);
                } catch (TeamProviderException e) {
                    logger.error("Checkout failed",e);
                    return new Status(IStatus.ERROR, Activator.BUNDLE_ID, "Checkout failed",e);
                } catch (CommitException e) {
                	return new Status(IStatus.ERROR, Activator.BUNDLE_ID, "Transaction commit failed",e);
				}
                finally{
                    try {
                        transaction.commit();
                    } catch (CommitException e) {
                        logger.error("Failed to commit the transaction",e);
                        return new Status(IStatus.ERROR, Activator.BUNDLE_ID, "Failed to commit the transaction",e);
                    }
                    transaction.close();
                }
                return Status.OK_STATUS;
            }
        };
        return new ProgressShowingAjaxButton("checkout", progressPanel, runnable, nls("checkout.version.job.label",getModelObject().getName())) {

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
            public IStatus run(IProgressMonitor monitor) {
            	try {
					rescanProject(monitor, model);
				} catch (CommitException e) {
					return new Status(IStatus.ERROR, Activator.BUNDLE_ID, "Transaction commit failed",e);
				}
                return Status.OK_STATUS;
            }
        };
        return new ProgressShowingAjaxButton("rescan", progressPanel, runnable, nls("rescan.version.job.label",getModelObject().getName())) {

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

    private void rescanProject(IProgressMonitor monitor, final IModel<ProjectVersion> model) throws CommitException {
        ScanConfiguration scanConfiguration = PreferencesUtil.getScanConfigForProject(getModelObject());
        ProjectVersion version = model.getObject();
        SubMonitor subMonitor = SubMonitor.convert(monitor, "Scanning", 100);
        CDOTransaction transaction = Activator.getDefault().getRepositoryConnector().openTransaction();
        version = transaction.getObject(version);
        version.fullScan(scanConfiguration, subMonitor.newChild(50));
        subMonitor.setTaskName("Database Sync");
        try {
            transaction.commit(subMonitor.newChild(50));
        } finally {
        	transaction.close();
        	PropertyPersistenceService persistenceService = Activator.getDefault().getPersistenceService();
        	if(persistenceService!=null)
        		persistenceService.clearCache();
        	else
        		logger.error("Could not obtain property persistence service");
        }
        monitor.done();
    }


	public static class VersionsConfig extends AbstractConfigSection<Project> {

        private static final long serialVersionUID = 1L;

        @Override
        public WebMarkupContainer doCreateContents(String id, IModel<Project> input, Preferences prefs) {
            return new ProjectVersionsConfigSection(id, input, prefs);
        }

        @Override
        public void commit(IModel<Project> input, Preferences config) {
            // TODO Auto-generated method stub

        }


        @Override
        public String getRequiredPermission() {
            String projectName = null;
            if(getDomainObject()!=null)
                projectName = getDomainObject().getName();
            return CommonPermissions.constructPermission(CommonPermissions.PROJECT,projectName,CommonPermissions.ACTION_CONFIG);
        }
    }

    static class DeleteAction extends IndicatingAjaxButton {

    	private IModel<ProjectVersion> model;
    	private IModel<String> confirmationText;
    	private static final long serialVersionUID = 1L;

    	public DeleteAction(String id, IModel<ProjectVersion> model, IModel<String> confirmationText) {
    		super(id);
    		this.model = model;
    		this.confirmationText = confirmationText;
    	}

    	@Override
    	protected void onAfterSubmit(AjaxRequestTarget target) {

    		ProjectVersion projectVersion = model.getObject();
    		CDOTransaction transaction = Activator.getDefault().getRepositoryConnector().openTransaction();
    		projectVersion = transaction.getObject(projectVersion);
    		Preferences preferences = PreferencesUtil.scopeFor(projectVersion);
    		try {
    			PreferencesUtil.deleteNode(preferences);
    			File directory = new File(projectVersion.absolutPath().toFileString());
    			FileUtil.delete(directory);
    			projectVersion.getParent().getChildren().remove(projectVersion);
    			transaction.commit();
    			setResponsePage(SettingsPage.class, getPage().getPageParameters());
    		} catch (CommitException e) {
    			logger.error("Commit failed",e);
    			getSession().error(e.getMessage());
    		} catch (BackingStoreException e) {
    			logger.error("Failed to delete project preferences",e);
    			getSession().error(e.getMessage());
    		} finally {
    			transaction.close();
    		}
    	}

    	@Override
    	protected void updateAjaxAttributes( AjaxRequestAttributes attributes )
    	{
    		super.updateAjaxAttributes( attributes );

    		AjaxCallListener ajaxCallListener = new AjaxCallListener();
    		ajaxCallListener.onPrecondition( "return confirm('" + confirmationText.getObject() + "');" );
    		attributes.getAjaxCallListeners().add( ajaxCallListener );
    	}

    }
}
