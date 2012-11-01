package de.jutzig.jabylon.rest.ui.model;

import org.eclipse.emf.ecore.EStructuralFeature;

public class EClassSortState {
	private String featureName;
	private boolean descending;
	
	public EClassSortState(EStructuralFeature feature) {
		featureName = feature.getName();
	}
	
	public void setDescending(boolean descending) {
		this.descending = descending;
	}
	
	public void setFeatureName(String featureName) {
		this.featureName = featureName;
	}
	
}
