/**
 *
 */
package org.jabylon.rest.ui.util;

import org.apache.wicket.request.Url;
import org.apache.wicket.request.resource.UrlResourceReference;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 *
 */
public class WebContextUrlResourceReference extends UrlResourceReference {

    private static final long serialVersionUID = 1L;

    public WebContextUrlResourceReference(String webContextRelativeUrl) {
        super(Url.parse(WicketUtil.getContextPath()+"/"+webContextRelativeUrl));

    }

}
