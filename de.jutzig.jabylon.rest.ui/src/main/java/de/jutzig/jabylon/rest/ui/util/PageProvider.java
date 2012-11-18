/**
 * 
 */
package de.jutzig.jabylon.rest.ui.util;

import org.apache.wicket.Page;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 *
 */
public interface PageProvider {
	
	String MOUNT_PATH_PROPERTY = "mountPath";
	
	Class<? extends Page> getPageClass();
}
