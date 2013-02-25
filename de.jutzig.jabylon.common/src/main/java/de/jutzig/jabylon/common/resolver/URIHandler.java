package de.jutzig.jabylon.common.resolver;

import org.eclipse.emf.common.util.URI;

public interface URIHandler {

	boolean canHandle(URI uri);
	
	Object resolve(URI uri);
	
}
