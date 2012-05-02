/**
 * 
 */
package de.jutzig.jabylon.index.properties;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import de.jutzig.jabylon.cdo.server.ServerConstants;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 *
 */
public class IndexActivator implements BundleActivator {

	private static IndexActivator INSTANCE;
	private FSDirectory directory;
	
	@Override
	public void start(BundleContext context) throws Exception {
		INSTANCE = this;
		
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		INSTANCE = null;
		if(directory!=null)
			directory.close();
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
	
}
