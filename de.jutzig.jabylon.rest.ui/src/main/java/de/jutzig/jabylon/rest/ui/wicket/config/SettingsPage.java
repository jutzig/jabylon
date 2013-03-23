/**
 * 
 */
package de.jutzig.jabylon.rest.ui.wicket.config;

import java.util.List;

import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.eclipse.emf.cdo.CDOObject;
import org.eclipse.emf.cdo.transaction.CDOTransaction;
import org.eclipse.emf.cdo.view.CDOView;

import de.jutzig.jabylon.rest.ui.model.IEObjectModel;
import de.jutzig.jabylon.rest.ui.model.WritableEObjectModel;
import de.jutzig.jabylon.rest.ui.security.RestrictedComponent;
import de.jutzig.jabylon.rest.ui.wicket.pages.GenericResolvablePage;
import de.jutzig.jabylon.security.CommonPermissions;


/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 * 
 */
public class SettingsPage extends GenericResolvablePage<CDOObject> implements RestrictedComponent{

	private static final long serialVersionUID = 1L;

	public SettingsPage(PageParameters parameters) {
		super(parameters);
	}
	
	@Override
	protected void construct() {
		super.construct();
	
//		BreadcrumbPanel breadcrumbPanel = new BreadcrumbPanel("breadcrumb-panel", getModel(),getPageParameters());
//		breadcrumbPanel.setRootLabel("Settings");
//		
//		breadcrumbPanel.setRootURL(WicketUtil.getContextPath()+"/settings");
//		add(breadcrumbPanel);
	}

	@Override
	protected void onBeforeRenderPage() {
		if(getPageParameters().getIndexedCount()>0)
		{
			addOrReplace(new SettingsPanel<CDOObject>("content", getModel(), getPageParameters()));
		}
		else
		{
			addOrReplace(new SettingsOverviewPanel("content"));	
		}
		super.onBeforeRenderPage();
	}
	
	protected CDOObject doLookup(List<String> segments) {

		CDOObject resolvable = super.doLookup(segments);
		CDOView cdoView = resolvable.cdoView();
		if (cdoView instanceof CDOTransaction) {
			return resolvable;
		}
		return cdoView.getSession().openTransaction().getObject(resolvable);
	}	

	
	@Override
	protected IEObjectModel<CDOObject> createModel(CDOObject object) {
		return new WritableEObjectModel<CDOObject>(object);
	}

	@Override
	public String getRequiredPermission() {
	      return CommonPermissions.SYSTEM_GENERAL_CONFIG;
	}
	
}
