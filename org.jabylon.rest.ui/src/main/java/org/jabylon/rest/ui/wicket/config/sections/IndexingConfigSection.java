/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.rest.ui.wicket.config.sections;

import java.io.IOException;

import javax.inject.Inject;

import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.store.Directory;
import org.apache.wicket.Component;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.StringResourceModel;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.jabylon.cdo.connector.RepositoryConnector;
import org.jabylon.common.progress.RunnableWithProgress;
import org.jabylon.index.properties.IndexActivator;
import org.jabylon.index.properties.QueryService;
import org.jabylon.properties.Workspace;
import org.jabylon.rest.ui.Activator;
import org.jabylon.rest.ui.model.ProgressionModel;
import org.jabylon.rest.ui.wicket.components.ProgressPanel;
import org.jabylon.rest.ui.wicket.components.ProgressShowingAjaxButton;
import org.jabylon.rest.ui.wicket.config.AbstractConfigSection;
import org.jabylon.security.CommonPermissions;
import org.osgi.service.prefs.Preferences;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IndexingConfigSection extends GenericPanel<Workspace> {

    private static final long serialVersionUID = 1L;
    
    @Inject
    private transient QueryService queryService;
    
    @Inject
    private transient RepositoryConnector connector;
    
    private static final Logger LOGGER = LoggerFactory.getLogger(IndexingConfigSection.class);

	private ProgressionModel progressModel;

    public IndexingConfigSection(String id, IModel<Workspace> model, Preferences config) {
        super(id, model);
        setOutputMarkupId(true);
        progressModel = new ProgressionModel(-1);
        add(new Label("summary", new StringResourceModel("index.size.summary", this, null, getIndexSize())));
        final ProgressPanel progressPanel = new ProgressPanel("progress", progressModel);
        add(progressPanel);
        add(createUpdateIndexAction(progressPanel));
    }
    
  
	private long getIndexSize() {
		Directory directory = IndexActivator.getDefault().getOrCreateDirectory();
		long size = 0;
		try {
			if (directory != null) {
				String[] files;
				files = directory.listAll();
				for (String file : files) {
					size += directory.fileLength(file);
				}
			}
		} catch (IOException e) {
			LOGGER.error("Failed to compute index size",e);
		}
		return size / 1024;
	}


	protected Component createUpdateIndexAction(ProgressPanel progressPanel) {
		RunnableWithProgress runnable = new RunnableWithProgress() {

			private static final long serialVersionUID = 1L;

			@Override
			public IStatus run(IProgressMonitor monitor) {
				Activator.getDefault().getRepositoryConnector();
				try {
					queryService.rebuildIndex(monitor);
				} catch (CorruptIndexException e) {
					return new Status(IStatus.ERROR, Activator.BUNDLE_ID, "Failed to rebuild index",e);
				} catch (IOException e) {
					return new Status(IStatus.ERROR, Activator.BUNDLE_ID, "Failed to rebuild index",e);
				}
				return Status.OK_STATUS;

			}
		};
		return new ProgressShowingAjaxButton("update-index", progressPanel, runnable);
	}  

    public static class IndexingConfig extends AbstractConfigSection<Workspace> {

        private static final long serialVersionUID = 1L;

        @Override
        public WebMarkupContainer doCreateContents(String id, IModel<Workspace> input, Preferences prefs) {
            return new IndexingConfigSection(id, input, prefs);
        }

        @Override
        public void commit(IModel<Workspace> input, Preferences config) {
            // nothing to do

        }


        @Override
        public String getRequiredPermission() {
            String projectName = null;
            if(getDomainObject()!=null)
                projectName = getDomainObject().getName();
            return CommonPermissions.constructPermission(CommonPermissions.WORKSPACE,projectName,CommonPermissions.ACTION_CONFIG);
        }
    }
}
