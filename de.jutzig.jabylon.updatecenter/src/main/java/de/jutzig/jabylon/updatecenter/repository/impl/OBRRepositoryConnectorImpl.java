/**
 * 
 */
package de.jutzig.jabylon.updatecenter.repository.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.felix.bundlerepository.Repository;
import org.apache.felix.bundlerepository.RepositoryAdmin;
import org.apache.felix.bundlerepository.Resolver;
import org.apache.felix.bundlerepository.Resource;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;

import de.jutzig.jabylon.updatecenter.repository.OBRRepositoryService;



/**
 * @author jutzig.dev@googlemail.com
 *
 */
@Component
@Service
public class OBRRepositoryConnectorImpl implements OBRRepositoryService {

	@Reference
	private RepositoryAdmin admin;
	
	/* (non-Javadoc)
	 * @see de.jutzig.jabylon.updatecenter.repository.impl.OBRRepositoryService#listInstalledBundles()
	 */
	@Override
	public List<Resource> listInstalledBundles()
	{
		Repository local = admin.getLocalRepository();
		Repository systemRepository = admin.getSystemRepository();
		
		List<Resource> installedBundles = new ArrayList<Resource>();
		Resource[] resources = local.getResources();
		for (Resource resource : resources) {
			installedBundles.add(resource);
		}
		return installedBundles;
	}
	
}
