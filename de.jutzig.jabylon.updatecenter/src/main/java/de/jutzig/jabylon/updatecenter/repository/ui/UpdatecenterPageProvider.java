package de.jutzig.jabylon.updatecenter.repository.ui;

import org.apache.felix.bundlerepository.RepositoryAdmin;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.ReferenceCardinality;
import org.apache.felix.scr.annotations.Service;
import org.apache.wicket.Page;

import de.jutzig.jabylon.rest.ui.util.PageProvider;
import de.jutzig.jabylon.rest.ui.wicket.pages.SearchPage;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 *
 */
@Component
@Service
public class UpdatecenterPageProvider implements PageProvider {

	
	@Property(value="/update")
	static final String PAGE_PATH = PageProvider.MOUNT_PATH_PROPERTY;
	
	/** 
	 * not really needed, just make sure the page doesn't get
	 * mounted before the query service is available
	 */
	@Reference(cardinality=ReferenceCardinality.MANDATORY_UNARY)
	private RepositoryAdmin service;
	
	/* (non-Javadoc)
	 * @see de.jutzig.jabylon.rest.ui.util.PageProvider#getPageClass()
	 */
	@Override
	public Class<? extends Page> getPageClass() {
		return InstalledSoftwarePage.class;
	}
	
	public void bindQueryService(RepositoryAdmin service) {
		this.service = service;
	}
	
	public void unbindQueryService(RepositoryAdmin service) {
		this.service = service;
	}

}
