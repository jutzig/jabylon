package de.jutzig.jabylon.rest.ui;

import org.apache.wicket.protocol.http.IWebApplicationFactory;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.protocol.http.WicketFilter;

import de.jutzig.jabylon.rest.ui.wicket.JabylonApplication;

public class JabylonFilter extends WicketFilter implements IWebApplicationFactory{
	@Override
	protected IWebApplicationFactory getApplicationFactory() {
		return this;
	}

	@Override
	public WebApplication createApplication(WicketFilter filter) {
		return new JabylonApplication();
	}

	@Override
	public void destroy(WicketFilter filter) {
		// nothing to do
		
	}
}
