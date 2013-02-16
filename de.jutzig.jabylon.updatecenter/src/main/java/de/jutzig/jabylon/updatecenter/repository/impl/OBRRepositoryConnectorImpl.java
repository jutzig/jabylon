/**
 * 
 */
package de.jutzig.jabylon.updatecenter.repository.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.felix.bundlerepository.Repository;
import org.apache.felix.bundlerepository.RepositoryAdmin;
import org.apache.felix.bundlerepository.Resolver;
import org.apache.felix.bundlerepository.Resource;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.service.prefs.Preferences;

import de.jutzig.jabylon.common.util.PreferencesUtil;
import de.jutzig.jabylon.updatecenter.repository.OBRRepositoryService;
import de.jutzig.jabylon.updatecenter.repository.ResourceFilter;



/**
 * @author jutzig.dev@googlemail.com
 *
 */
@Component
@Service
public class OBRRepositoryConnectorImpl implements OBRRepositoryService {

	private static final String DEFAULT_REPOSITORY = "file:///"+System.getProperty("user.home").replace("\\", "/")+"/.m2/repository/repository.xml";
	@Reference
	private RepositoryAdmin admin;
	
	/* (non-Javadoc)
	 * @see de.jutzig.jabylon.updatecenter.repository.impl.OBRRepositoryService#listInstalledBundles()
	 */
	@Override
	public List<Bundle> listInstalledBundles()
	{
		Bundle bundle = FrameworkUtil.getBundle(getClass());
		List<Bundle> resources = Arrays.asList(bundle.getBundleContext().getBundles()); 
		return resources;
	}

	@Override
	public List<Resource> getAvailableResources(ResourceFilter filter) {
		List<Resource> filteredResources = new ArrayList<Resource>();
		List<Bundle> bundles = listInstalledBundles();
		try {
			
			Repository repository = getRepository();
			Resource[] resources = repository.getResources();
			for (Resource resource : resources) {
				if(applies(filter,bundles,resource))
					filteredResources.add(resource);
			}
			
		}  catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return filteredResources;
	}

	private Repository getRepository() {
		Preferences node = PreferencesUtil.workspaceScope().node("update");
		String url = node.get("update.url", DEFAULT_REPOSITORY);
		try {
			return admin.addRepository(url);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private boolean applies(ResourceFilter filter, List<Bundle> bundles,
			Resource resource) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void install(String resourceId) {
		Resource[] resources;
		try {
			resources = admin.discoverResources("(symbolicname="+resourceId+")");
			if(resources.length>0)
			{
				install(resources[0]);
				
			}
		} catch (InvalidSyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void install(Resource... resources) {
		Resolver resolver = admin.resolver();
		for (Resource resource : resources) {
			resolver.add(resource);
		}
		if(resolver.resolve())
		{
			resolver.deploy(0);
		}		
	}
	
}
