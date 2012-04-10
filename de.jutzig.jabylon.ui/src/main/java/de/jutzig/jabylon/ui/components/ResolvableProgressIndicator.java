package de.jutzig.jabylon.ui.components;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;

import de.jutzig.jabylon.properties.PropertiesPackage;
import de.jutzig.jabylon.properties.Resolvable;

public class ResolvableProgressIndicator extends StaticProgressIndicator implements Adapter {

	private Resolvable resolvable;

	public ResolvableProgressIndicator(Resolvable resolvable) {
		super();
		this.resolvable = resolvable;
		if(resolvable==null)
			setPercentage(0);
		else
		{
			setPercentage(resolvable.getPercentComplete());
		}
	}

	@Override
	public void notifyChanged(Notification notification) {
		if(notification.getFeature()==PropertiesPackage.Literals.RESOLVABLE__PERCENT_COMPLETE)
			setPercentage(resolvable.getPercentComplete()); //don't use the notification value because CDO might not send it
	}

	@Override
	public Notifier getTarget() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setTarget(Notifier newTarget) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isAdapterForType(Object type) {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	@Override
	public void detach() {
		super.detach();
		if(resolvable!=null)
			resolvable.eAdapters().remove(this);
	}
	
	@Override
	public void attach() {
		if(resolvable!=null)
		{
			resolvable.eAdapters().add(this);
			setPercentage(resolvable.getPercentComplete());
		}
	}
	
}
