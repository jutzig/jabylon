/**
 *
 */
package org.jabylon.resources.persistence.internal;

import org.eclipse.emf.cdo.common.id.CDOID;

import com.google.common.cache.Weigher;

import org.jabylon.properties.PropertyFile;

/**
 * @author jutzig.dev@googlemail.com
 *
 */
public class PropertySizeWeigher implements Weigher<CDOID, PropertyFile> {

    @Override
    public int weigh(CDOID key, PropertyFile value) {
        return value.getProperties().size();
    }

}
