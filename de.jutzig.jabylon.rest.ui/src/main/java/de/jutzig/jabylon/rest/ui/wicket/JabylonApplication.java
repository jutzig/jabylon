/**
 *
 */
package de.jutzig.jabylon.rest.ui.wicket;

import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import de.jutzig.jabylon.rest.ui.wicket.project.ProjectView;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 *
 */
public class JabylonApplication extends WebApplication {

	/* (non-Javadoc)
	 * @see org.apache.wicket.Application#getHomePage()
	 */
	@Override
	public Class<? extends Page> getHomePage() {
		return WorkspaceView.class;
	}

	@Override
	protected void init()
	{
	    super.init();
//	    mountPage("/workspace", WorkspaceView.class);
	    mountPage("/workspace/${project}", ProjectView.class);
	    mountPage("/workspace/${project}/${version}", TestView.class);
//	    mountPage("/workspace/${project}/${version}/${locale}", TestView.class);
//	    mountPage("/workspace/${project}/${version}/${locale}/${remainder}", ProjectView.class);
	}

	public class TestView extends ProjectView{

	    public TestView(PageParameters params)
	    {
	        super(params);
	        // TODO Auto-generated constructor stub
	    }

	}

}