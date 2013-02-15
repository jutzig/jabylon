package de.jutzig.jabylon.updatecenter.repository;

import org.osgi.framework.Bundle;

public enum BundleState {

	INSTALLED(Bundle.INSTALLED, "important"), RESOLVED(Bundle.RESOLVED, "warning"), STARTING(Bundle.STARTING,"info"), ACTIVE(Bundle.ACTIVE,"success"), UNINSTALLED(Bundle.UNINSTALLED,"inverse"); 
	
	private int state;
	private String labelClass;
	
	private BundleState(int state, String labelClass)
	{
		this.state = state;
		this.labelClass = labelClass;
	}
	
	public static BundleState fromState(int state) {
		for (BundleState bundleState : values()) {
			if(bundleState.state==state)
				return bundleState;
		}
		return null;
	}
	
	public String getLabelClass() {
		return labelClass;
	}
}
