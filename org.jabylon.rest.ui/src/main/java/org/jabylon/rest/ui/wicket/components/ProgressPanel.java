/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.rest.ui.wicket.components;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.AjaxSelfUpdatingTimerBehavior;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebComponent;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.time.Duration;
import org.eclipse.core.runtime.IStatus;
import org.jabylon.common.progress.Progression;
import org.jabylon.rest.ui.Activator;
import org.jabylon.rest.ui.model.ProgressionModel;

public class ProgressPanel extends Panel {

    private ProgressionModel model;
    private boolean started;
    private CustomFeedbackPanel feedbackPanel;
    private WebMarkupContainer container;

    public ProgressPanel(String id, ProgressionModel model) {
        super(id, model);
        this.model = model;
        feedbackPanel = new CustomFeedbackPanel("feedbackPanel"); //$NON-NLS-1$
        add(feedbackPanel);
        
        container = new WebMarkupContainer("container"); //$NON-NLS-1$
        add(container);
        WebComponent bar = new WebComponent("bar"); //$NON-NLS-1$
        bar.add(new AttributeModifier("style", getWidthModel(model))); //$NON-NLS-1$
        container.add(bar);
        Label taskname = new Label("taskname", getTaskNameModel(model)); //$NON-NLS-1$
        container.add(taskname);
        Label subtask = new Label("subtask", getSubTaskModel(model)); //$NON-NLS-1$
        container.add(subtask);
        setVisible(false);
        container.add(new AjaxLink<Void>("cancel") {

			private static final long serialVersionUID = 3623515953111747368L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				Activator.getDefault().getProgressService().cancel(ProgressPanel.this.model.getId());
				
			}
        	
		});
    }

    private IModel<String> getWidthModel(final IModel<Progression> model) {
        return new AbstractReadOnlyModel<String>() {

            private static final long serialVersionUID = 1L;

            @Override
            public String getObject() {
            	int width = model.getObject() == null ? 0 : model.getObject().getCompletion();
                return "width: " + width + "%;"; //$NON-NLS-1$ //$NON-NLS-2$
            }
        };
    }

    private IModel<String> getTaskNameModel(final IModel<Progression> model) {
        return new AbstractReadOnlyModel<String>() {

            private static final long serialVersionUID = 1L;

            @Override
            public String getObject() {
            	if(model.getObject()!=null)
            		return model.getObject().getTaskName();
            	return "";
            }
        };
    }

    private IModel<String> getSubTaskModel(final IModel<Progression> model) {
        return new AbstractReadOnlyModel<String>() {

            private static final long serialVersionUID = 1L;

            @Override
            public String getObject() {
            	if(model.getObject()!=null)
            		return model.getObject().getSubTaskName();
            	return "";
            }
        };
    }

    public void start(AjaxRequestTarget target, final ProgressCallback callback) {

        setVisible(true);
        container.setVisible(true);
        add(new AjaxSelfUpdatingTimerBehavior(Duration.ONE_SECOND) {

            private static final long serialVersionUID = 1L;

            @Override
            protected void onPostProcessTarget(AjaxRequestTarget target) {
                if (!started && callback != null) {
                    callback.progressStart(target, getModel());
                    started = true;
                }
                ProgressionModel model = getModel();
                callback.progressStart(target, model);
                Progression progression = model.getObject();

                if (progression==null || progression.isDone()) {
                    // stop the self update
                    stop(target);
                    if (progression!=null && !progression.getStatus().isOK()) {
                        addFeedbackMessage(progression.getStatus());
                        container.setVisible(false);

                    } else
                        ProgressPanel.this.setVisible(false);
                    if (callback != null)
                        callback.progressDone(target, getModel());
                }
            }

        });
        if (getParent() != null) {
            target.add(getParent());
        } else if(target!=null){
            target.add(this);
        }
    }

    public void start() {
        setVisible(true);
        container.setVisible(true);
        add(new AjaxSelfUpdatingTimerBehavior(Duration.ONE_SECOND) {

            private static final long serialVersionUID = 1L;

            @Override
            protected void onPostProcessTarget(AjaxRequestTarget target) {
                started = true;
                ProgressionModel model = getModel();
                Progression progression = model.getObject();

                if (progression==null || progression.isDone()) {
                    // stop the self update
                    stop(target);
                    if (!progression.getStatus().isOK()) {
                        addFeedbackMessage(progression.getStatus());
                        container.setVisible(false);

                    } else
                        ProgressPanel.this.setVisible(false);
                }
            }

        });
		
	}

	protected void addFeedbackMessage(IStatus status) {
        if (status == null)
            return;
        String message = status.getMessage();
        if (status.getException() != null && status.getException().getMessage() != null) {
            if (message == null || message.isEmpty())
                message = status.getException().getMessage();
            else
                message += " : " + status.getException().getMessage(); //$NON-NLS-1$
        }
        if (message == null)
            return;
        switch (status.getSeverity()) {
        case IStatus.INFO:
            feedbackPanel.info(message);
            break;
        case IStatus.ERROR:
            feedbackPanel.error(message);
            break;
        case IStatus.WARNING:
            feedbackPanel.warn(message);
            break;
        case IStatus.OK:
            feedbackPanel.success(message);
            break;
        default:
            break;
        }
    }

    public ProgressionModel getModel() {
        return model;
    }

    /**
     *
     */
    private static final long serialVersionUID = 2573454585436627297L;

}
