package org.jabylon.rest.ui;

import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;

import org.apache.wicket.protocol.http.IWebApplicationFactory;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.protocol.http.WicketFilter;
import org.jabylon.rest.ui.wicket.JabylonApplication;

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
    
    @Override
    protected String getFilterPathFromConfig(FilterConfig filterConfig) {
    	//Wicket gets confused by the servlet bridge.
    	//so we set the path manually
    	//see https://github.com/jutzig/jabylon/issues/47
    	ServletContext context = filterConfig.getServletContext();
    	String path = "";
    	if(context!=null)
    		path = context.getContextPath();
    	if(path.startsWith("/"))
    		path = path.substring(1);
    	return path;
    }
}
