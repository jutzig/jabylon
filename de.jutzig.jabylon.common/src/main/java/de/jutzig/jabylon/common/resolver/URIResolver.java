package de.jutzig.jabylon.common.resolver;

import org.eclipse.emf.common.util.URI;

public interface URIResolver {
	Object resolve(URI uri);
	
	Object resolve(String path);
}
