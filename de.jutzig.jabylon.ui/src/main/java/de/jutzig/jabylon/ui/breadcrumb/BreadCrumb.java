package de.jutzig.jabylon.ui.breadcrumb;

public interface BreadCrumb {
	
	void goBack();
	
	void goBack(int steps);
	
	void walkTo(String... steps);
	
}
