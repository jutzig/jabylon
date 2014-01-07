package org.jabylon.rest.ui.jobs;

import java.util.Map;

import org.eclipse.emf.cdo.transaction.CDOTransaction;
import org.jabylon.scheduler.JobContextUtil;
import org.jabylon.scheduler.JobExecution;

/**
 * automatically updates a project from the team provider
 * @author jutzig.dev@googlemail.com
 *
 */
public class AutoUpdateJob implements JobExecution {

	@Override
	public void run(Map<String, Object> jobContext) throws Exception {
		// TODO Auto-generated method stub
		CDOTransaction transaction = JobContextUtil.openTransaction(jobContext);

	}

	@Override
	public boolean retryOnError() {
		return false;
	}

}
