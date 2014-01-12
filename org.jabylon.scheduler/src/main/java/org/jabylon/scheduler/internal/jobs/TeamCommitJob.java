/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.scheduler.internal.jobs;

import java.util.Map;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.emf.cdo.transaction.CDOTransaction;
import org.jabylon.common.team.TeamProvider;
import org.jabylon.common.team.TeamProviderException;
import org.jabylon.common.team.TeamProviderUtil;
import org.jabylon.properties.ProjectVersion;
import org.jabylon.scheduler.JobUtil;
import org.jabylon.scheduler.JobExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(enabled = true, immediate = true)
@Service
public class TeamCommitJob implements JobExecution {

	public static final String JOB_ID = "job.team.commit";

	private static final Logger logger = LoggerFactory.getLogger(TeamUpdateJob.class);

	@org.apache.felix.scr.annotations.Property(value = "false", name = JobExecution.PROP_JOB_ACTIVE)
	private String ACTIVE = JobExecution.PROP_JOB_ACTIVE;

	/** every hour, five minutes after the full hour */
	@Property(value = "0 5 * * * ?", name = JobExecution.PROP_JOB_SCHEDULE)
	public static final String DEFAULT_SCHEDULE = "0 5 * * * ?";

	@Property(value = "%commit.job.name", name = JobExecution.PROP_JOB_NAME)
	private String NAME = JobExecution.PROP_JOB_NAME;

	/** at 2 am every day */
	@Property(value = "%commit.job.description", name = JobExecution.PROP_JOB_DESCRIPTION)
	private String DESCRIPTION = JobExecution.PROP_JOB_DESCRIPTION;

	@Override
	public void run(IProgressMonitor monitor, Map<String, Object> jobContext) throws Exception {

		ProjectVersion version = JobUtil.getDomainObject(jobContext);
		TeamProvider provider = TeamProviderUtil.getTeamProvider(version.getParent().getTeamProvider());
		
		CDOTransaction transaction = JobUtil.openTransaction(jobContext);
		try {
			version = transaction.getObject(version);
			SubMonitor subMonitor = SubMonitor.convert(monitor, "Committing", 100);

			provider.commit(version, subMonitor.newChild(100));

		} catch (TeamProviderException e) {
			logger.error("Commit failed", e);
		}

		finally {
			transaction.close();
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
