package de.jutzig.jabylon.ui.breadcrumb;

import java.util.Collection;
import java.util.Deque;
import java.util.List;

public interface BreadCrumb {
	
	void goBack();
	
	void goBack(int steps);
	
	void walkTo(String... steps);
	
	Collection<String> currentPath();
	
	CrumbTrail currentTrail();
	
	String CONFIG = "|config";
	
	void addCrumbListener(CrumbListener listener);
	
	void removeCrumbListener(CrumbListener listener);
	
}
