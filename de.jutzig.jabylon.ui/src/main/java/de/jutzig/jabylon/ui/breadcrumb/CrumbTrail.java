package de.jutzig.jabylon.ui.breadcrumb;

import org.eclipse.emf.cdo.CDOObject;

import com.vaadin.ui.Component;

public interface CrumbTrail {

	CrumbTrail walkTo(String path);
	
	String getTrailCaption();
	
	boolean isDirty();
	
	CDOObject getDomainObject();
	
	Component createContents();
}
