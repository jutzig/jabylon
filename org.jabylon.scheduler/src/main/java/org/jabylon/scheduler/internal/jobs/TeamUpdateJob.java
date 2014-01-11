/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.scheduler.internal.jobs;

import java.util.Collection;
import java.util.Map;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.ReferenceCardinality;
import org.apache.felix.scr.annotations.ReferencePolicy;
import org.apache.felix.scr.annotations.Service;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.emf.cdo.transaction.CDOTransaction;
import org.eclipse.emf.cdo.util.CommitException;
import org.jabylon.common.team.TeamProvider;
import org.jabylon.common.team.TeamProviderException;
import org.jabylon.common.team.TeamProviderUtil;
import org.jabylon.common.util.PreferencesUtil;
import org.jabylon.properties.ProjectVersion;
import org.jabylon.properties.PropertyFileDiff;
import org.jabylon.resources.persistence.PropertyPersistenceService;
import org.jabylon.scheduler.JobUtil;
import org.jabylon.scheduler.JobExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(enabled = true, immediate = true)
@Service
public class TeamUpdateJob implements JobExecution {


	public static final String JOB_ID = "job.team.update";

	private static final Logger logger = LoggerFactory.getLogger(TeamUpdateJob.class);

	@org.apache.felix.scr.annotations.Property(value = "false", name = JobExecution.PROP_JOB_ACTIVE)
	private String ACTIVE = JobExecution.PROP_JOB_ACTIVE;

	/** every hour */
	@Property(value = "0 0 * * * ?", name = JobExecution.PROP_JOB_SCHEDULE)
	public String DEFAULT_SCHEDULE = "0 0 * * * ?";

	@Property(value = "%update.job.name", name = JobExecution.PROP_JOB_NAME)
	private String NAME = JobExecution.PROP_JOB_NAME;

	/** at 2 am every day */
	@Property(value = "%update.job.description", name = JobExecution.PROP_JOB_DESCRIPTION)
	private String DESCRIPTION = JobExecution.PROP_JOB_DESCRIPTION;
	
	@Reference(cardinality=ReferenceCardinality.OPTIONAL_UNARY,policy=ReferencePolicy.DYNAMIC)
	private PropertyPersistenceService persistenceService;

	public void bindPersistenceService(PropertyPersistenceService persistenceService)
	{
		this.persistenceService = persistenceService;
	}
	
	public void unbindPersistenceService(PropertyPersistenceService persistenceService)
	{
		if(persistenceService==this.persistenceService)
			persistenceService = null;
	}
	
	@Override
	public void run(IProgressMonitor monitor, Map<String, Object> jobContext) throws Exception {
		
        ProjectVersion version = JobUtil.getDomainObject(jobContext);
        TeamProvider provider = TeamProviderUtil.getTeamProvider(version.getParent().getTeamProvider());
        if(provider==null)
        {
        	logger.error("Team Provider "+version.getParent().getTeamProvider()+" was not available. Update canceled");
        	return;
        }
        CDOTransaction transaction = JobUtil.openTransaction(jobContext);
        try {
            version = transaction.getObject(version);
            SubMonitor subMonitor = SubMonitor.convert(monitor, "Updating", 100);
            Collection<PropertyFileDiff> updates = provider.update(version, subMonitor.newChild(50));
            subMonitor.setWorkRemaining(updates.size() * 2);
            subMonitor.subTask("Processing updates");
            for (PropertyFileDiff updatedFile : updates) {
                version.partialScan(PreferencesUtil.getScanConfigForProject(version.getParent()), updatedFile);
                subMonitor.worked(1);
            }
            subMonitor.setTaskName("Database Sync");
            transaction.commit(subMonitor.newChild(updates.size()));
        } catch (TeamProviderException e) {
            logger.error("Update failed",e);
        } catch (CommitException e) {
            logger.error("Failed to commit the transaction",e);
        } finally {
        	transaction.close();
        	if(persistenceService!=null)
        		persistenceService.clearCache();
        	else
        		logger.error("Could not obtain property persistence service");
        }
	}
	
	@Override
	public boolean retryOnError() {
		return false;
	}

	@Override
	public String getID() {
		return JOB_ID;
	}

}
