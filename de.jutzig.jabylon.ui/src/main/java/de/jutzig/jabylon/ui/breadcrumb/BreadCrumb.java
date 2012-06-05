package de.jutzig.jabylon.ui.breadcrumb;

import java.util.Collection;

public interface BreadCrumb {
	
	void goBack();
	
	void goBack(int steps);
	
	void walkTo(String... steps);
	
	void setPath(String...steps);
	
	Collection<String> currentPath();
	
	CrumbTrail currentTrail();
	
	String CONFIG = "|config"; //$NON-NLS-1$
	
	void addCrumbListener(CrumbListener listener);
	
	void removeCrumbListener(CrumbListener listener);
	
}
