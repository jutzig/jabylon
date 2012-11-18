/**
 * 
 */
package de.jutzig.jabylon.log.viewer.supplier;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.apache.wicket.Page;

import de.jutzig.jabylon.log.viewer.pages.LogViewerPage;
import de.jutzig.jabylon.rest.ui.util.PageProvider;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 *
 */

@Component(immediate=true)
@Service(PageProvider.class)
public class LogViewerPageSupplier implements PageProvider {

	@Property(value="/log")
	static final String PAGE_PATH = PageProvider.MOUNT_PATH_PROPERTY;


	@Override
	public Class<? extends Page> getPageClass() {
		 return LogViewerPage.class;
	}

}
