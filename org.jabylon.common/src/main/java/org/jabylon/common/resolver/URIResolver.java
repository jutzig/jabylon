package org.jabylon.common.resolver;

import org.eclipse.emf.cdo.CDOObject;
import org.eclipse.emf.cdo.common.id.CDOID;
import org.eclipse.emf.common.util.URI;

public interface URIResolver {
    Object resolve(URI uri);

    Object resolve(String path);

    CDOObject resolve(CDOID id);

    CDOObject resolveWithTransaction(CDOID id);


}
