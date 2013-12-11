/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.rest.ui.wicket.components;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.Form;

import org.jabylon.common.progress.RunnableWithProgress;
import org.jabylon.rest.ui.Activator;
import org.jabylon.rest.ui.model.ProgressionModel;

public class ProgressShowingAjaxButton extends AjaxButton implements ProgressCallback{



    private static final long serialVersionUID = 1L;
    private ProgressPanel panel;
    private RunnableWithProgress runnable;

    public ProgressShowingAjaxButton(String id, ProgressPanel panel, RunnableWithProgress runnable) {
        super(id);
        this.panel = panel;
        this.runnable = runnable;
        setDefaultFormProcessing(false);
    }

    @Override
    protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
        super.onSubmit(target, form);

        panel.start(target, this);
        setEnabled(false);
        long id = Activator.getDefault().getProgressService().schedule(runnable);
        panel.getModel().setTaskID(id);
    }

    @Override
    public void progressStart(AjaxRequestTarget target, ProgressionModel model) {
        target.add(this);

    }

    @Override
    public void progressDone(AjaxRequestTarget target, ProgressionModel model) {
        setEnabled(true);
    }


}
