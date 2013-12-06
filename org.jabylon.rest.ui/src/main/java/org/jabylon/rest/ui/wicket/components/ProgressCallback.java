package org.jabylon.rest.ui.wicket.components;

import org.apache.wicket.ajax.AjaxRequestTarget;

import org.jabylon.rest.ui.model.ProgressionModel;

public interface ProgressCallback {

    void progressDone(AjaxRequestTarget target, ProgressionModel model);

    void progressStart(AjaxRequestTarget target, ProgressionModel model);
}
