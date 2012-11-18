/**
 * 
 */
package de.jutzig.jabylon.rest.ui.wicket.injector;

import java.lang.reflect.Field;

import org.apache.wicket.proxy.IProxyTargetLocator;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 *
 */
public class OSGiProxyTargetLocator implements IProxyTargetLocator {

	
	
	private static final long serialVersionUID = 1L;
	private String typeName;
	
	public OSGiProxyTargetLocator(Field field) {
		typeName = field.getType().getName();
	}
	
	/* (non-Javadoc)
	 * @see org.apache.wicket.proxy.IProxyTargetLocator#locateProxyTarget()
	 */
	@Override
	public Object locateProxyTarget() {
		Bundle bundle = FrameworkUtil.getBundle(OSGiProxyTargetLocator.class);
		BundleContext context = bundle.getBundleContext();
		ServiceReference<?> reference = context.getServiceReference(typeName);
		if(reference!=null)
			return context.getService(reference);
		return null;
	}

}
