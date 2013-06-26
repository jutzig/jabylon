/**
 * 
 */
package de.jutzig.jabylon.rest.ui.wicket.components;

import org.apache.wicket.Page;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.request.mapper.parameter.PageParameters;

/**
 * @author jutzig.dev@googlemail.com
 *
 */
public class AnchorBookmarkablePageLink<T> extends BookmarkablePageLink<T> {

	private static final long serialVersionUID = 1L;
	private String anchor;

	public <C extends Page> AnchorBookmarkablePageLink(String id, Class<C> pageClass, PageParameters parameters, String anchor) {
		super(id, pageClass, parameters);
		this.anchor = anchor;
	}

	public <C extends Page> AnchorBookmarkablePageLink(String id, Class<C> pageClass, String anchor) {
		super(id, pageClass);
		this.anchor = anchor;
	}

	@Override
	protected CharSequence getURL() {
		CharSequence url = super.getURL();
		if(anchor!=null)
			url = url + "#" + anchor;
		return url;
	}
	
}
