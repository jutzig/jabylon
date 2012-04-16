package de.jutzig.jabylon.ui.breadcrumb;

import com.vaadin.ui.Component;

public interface CrumbTrail {

	CrumbTrail walkTo(String path);
	
	Component getComponent();
	
	String getTrailCaption();
	
	boolean isDirty();
	
	Object getDomainObject();
	
}
