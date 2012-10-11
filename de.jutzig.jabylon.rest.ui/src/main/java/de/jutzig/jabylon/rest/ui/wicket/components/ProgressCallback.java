package de.jutzig.jabylon.rest.ui.wicket.components;

import org.apache.wicket.ajax.AjaxRequestTarget;

import de.jutzig.jabylon.rest.ui.model.ProgressionModel;

public interface ProgressCallback {
	
	void progressDone(AjaxRequestTarget target, ProgressionModel model);
	
	void progressStart(AjaxRequestTarget target, ProgressionModel model);
}
