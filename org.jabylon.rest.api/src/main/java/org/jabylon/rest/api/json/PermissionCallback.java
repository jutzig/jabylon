package org.jabylon.rest.api.json;

import org.eclipse.emf.ecore.EObject;

public interface PermissionCallback {
	boolean isAuthorized(EObject target);
}
