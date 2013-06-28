/**
 *
 */
package de.jutzig.jabylon.resources.persistence.internal;

import org.eclipse.emf.cdo.CDOObject;
import org.eclipse.emf.cdo.common.id.CDOID;
import org.eclipse.emf.cdo.view.CDOView;

import com.google.common.cache.CacheLoader;

import de.jutzig.jabylon.properties.PropertyFile;
import de.jutzig.jabylon.properties.PropertyFileDescriptor;

/**
 * @author jutzig.dev@googlemail.com
 *
 */
public class PropertyFileCacheLoader extends CacheLoader<CDOID, PropertyFile> {

    private CDOView view;

    public PropertyFileCacheLoader(CDOView view) {
        this.view = view;
    }


    @Override
    public PropertyFile load(CDOID key) throws Exception {
        CDOObject object = view.getObject(key);
        if (object instanceof PropertyFileDescriptor) {
            PropertyFileDescriptor descriptor = (PropertyFileDescriptor) object;
            return descriptor.loadProperties();
        }
        throw new IllegalArgumentException("Object is not a PropertyFileDescriptor: "+object);
    }

}
