/**
 * 
 */
package de.jutzig.jabylon.rest.ui.wicket.pages;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.ReferenceCardinality;
import org.apache.felix.scr.annotations.Service;
import org.apache.wicket.Page;

import de.jutzig.jabylon.index.properties.QueryService;
import de.jutzig.jabylon.rest.ui.util.PageProvider;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 *
 */
@Component
@Service
public class SearchPageProvider implements PageProvider {

	
	@Property(value="/search")
	static final String PAGE_PATH = PageProvider.MOUNT_PATH_PROPERTY;
	
	/** 
	 * not really needed, just make sure the page doesn't get
	 * mounted before the query service is available
	 */
	@Reference(cardinality=ReferenceCardinality.MANDATORY_UNARY)
	private QueryService queryService;
	
	/* (non-Javadoc)
	 * @see de.jutzig.jabylon.rest.ui.util.PageProvider#getPageClass()
	 */
	@Override
	public Class<? extends Page> getPageClass() {
		return SearchPage.class;
	}
	
	public void bindQueryService(QueryService queryService) {
		this.queryService = queryService;
	}
	
	public void unbindQueryService(QueryService queryService) {
		this.queryService = queryService;
	}

}
