/**
 * 
 */
package de.jutzig.jabylon.rest.ui.wicket.pages;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.apache.wicket.Page;

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
	
	/* (non-Javadoc)
	 * @see de.jutzig.jabylon.rest.ui.util.PageProvider#getPageClass()
	 */
	@Override
	public Class<? extends Page> getPageClass() {
		return SearchPage.class;
	}

}
