/**
 * 
 */
package de.jutzig.jabylon.index.properties;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.core.runtime.Status;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import de.jutzig.jabylon.cdo.server.ServerConstants;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 *
 */
public class IndexActivator extends Plugin implements BundleActivator {

	private static IndexActivator INSTANCE;
	private FSDirectory directory;
	public static final String PLUGIN_ID = "de.jutzig.jabylon.index";
	
	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		INSTANCE = this;
		
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		super.stop(context);
		if(directory!=null)
			directory.close();
		INSTANCE = null;
		directory = null;
	}
	
	public Directory getOrCreateDirectory()
	{
		if(directory==null)
		{
			try {
				directory = FSDirectory.open(new File(ServerConstants.WORKING_DIR,"lucene"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}
		return directory;
	}

	public static IndexActivator getDefault() {
		return INSTANCE;
	}
	
	
	public void log(IStatus status)
	{
		getLog().log(status);
	}
	
	public void log(String message, Throwable cause)
	{
		IStatus status = new Status(IStatus.ERROR, PLUGIN_ID, message, cause);
		log(status);
	}
	
	public void log(String message, int severity)
	{
		IStatus status = new Status(severity, PLUGIN_ID, message);
		log(status);
	}
}
