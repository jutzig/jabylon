/**
 * 
 */
package de.jutzig.jabylon.review.standard.cleanup;

import java.util.Iterator;
import java.util.Map;

import org.eclipse.emf.cdo.view.CDOView;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.resource.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.jutzig.jabylon.cdo.server.ServerConstants;
import de.jutzig.jabylon.properties.Project;
import de.jutzig.jabylon.properties.ProjectLocale;
import de.jutzig.jabylon.properties.ProjectVersion;
import de.jutzig.jabylon.properties.Property;
import de.jutzig.jabylon.properties.PropertyFile;
import de.jutzig.jabylon.properties.PropertyFileDescriptor;
import de.jutzig.jabylon.properties.Workspace;
import de.jutzig.jabylon.resources.persistence.PropertyPersistenceService;
import de.jutzig.jabylon.review.standard.ReviewActivator;
import de.jutzig.jabylon.scheduler.JobContextUtil;
import de.jutzig.jabylon.scheduler.JobExecution;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 *
 */
public class TranslationCleanupJob implements JobExecution {

	
	private static final Logger logger = LoggerFactory.getLogger(TranslationCleanupJob.class);
	
	/**
	 * 
	 */
	public TranslationCleanupJob() {
	}

	/* (non-Javadoc)
	 * @see de.jutzig.jabylon.scheduler.JobExecution#run(java.util.Map)
	 */
	@Override
	public void run(Map<String, Object> jobContext) throws Exception {
		logger.info("Starting translation cleanup job");
		CDOView view = JobContextUtil.openView(jobContext);
		try {
			Resource resource = view.getResource(ServerConstants.WORKSPACE_RESOURCE);
			Workspace workspace = (Workspace) resource.getContents().get(0);
			cleanup(workspace);
			logger.info("Translation cleanup job finished successfully");
		} catch (Exception e) {
			logger.error("Internal Error during translation cleanup",e);
		}
		finally {
			if(view!=null)
				view.close();
		}

	}

	private void cleanup(Workspace workspace) {
		EList<Project> projects = workspace.getChildren();
		for (Project project : projects) {
			cleanup(project);
		}
		
	}

	private void cleanup(Project project) {
		for (ProjectVersion version : project.getChildren()) {
			cleanup(version);
		}
		
	}

	private void cleanup(ProjectVersion version) {
		for (ProjectLocale locale : version.getChildren()) {
			cleanup(locale);
		}
		
		
	}

	private void cleanup(ProjectLocale locale) {
		for (PropertyFileDescriptor descriptor : locale.getDescriptors()) {
			cleanup(descriptor);
		}
		
	}

	private void cleanup(PropertyFileDescriptor descriptor) {
		PropertyFile masterProperties = descriptor.getMaster().loadProperties();
		Map<String, Property> map = masterProperties.asMap();
		PropertyFile properties = descriptor.loadProperties();
		Iterator<Property> iterator = properties.getProperties().iterator();
		boolean hadDeletes = false;
		while (iterator.hasNext()) {
			Property property = (Property) iterator.next();
			if(!map.containsKey(property.getKey()))
			{
				iterator.remove();
				logger.info("Removed unused translation {} in {}",property.getKey(), descriptor.fullPath());
				hadDeletes = true;
			}
		}
		if(hadDeletes)
		{
			PropertyPersistenceService propertyPersistenceService = ReviewActivator.getDefault().getPersistenceService();
			propertyPersistenceService.saveProperties(descriptor, properties);
		}
	}

	/* (non-Javadoc)
	 * @see de.jutzig.jabylon.scheduler.JobExecution#retryOnError()
	 */
	@Override
	public boolean retryOnError() {
		return false;
	}

}
