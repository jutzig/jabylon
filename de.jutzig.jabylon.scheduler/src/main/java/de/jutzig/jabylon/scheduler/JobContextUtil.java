/**
 * 
 */
package de.jutzig.jabylon.scheduler;

import java.util.Map;

import de.jutzig.jabylon.cdo.connector.RepositoryConnector;
import de.jutzig.jabylon.scheduler.internal.JabylonJob;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 *
 */
public class JobContextUtil {


	
	
	public static RepositoryConnector getRepositoryConnector(Map<String, Object> jobContext)
	{
		return (RepositoryConnector) jobContext.get(JabylonJob.CONNECTOR_KEY);
	}
	
}
