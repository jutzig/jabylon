package de.jutzig.jabylon.rest.ui.wicket.components;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.AjaxSelfUpdatingTimerBehavior;
import org.apache.wicket.markup.html.WebComponent;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.time.Duration;
import org.eclipse.core.runtime.IStatus;

import de.jutzig.jabylon.common.progress.Progression;
import de.jutzig.jabylon.rest.ui.model.ProgressionModel;

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
    }

    private IModel<String> getWidthModel(final IModel<Progression> model) {
        return new AbstractReadOnlyModel<String>() {

            private static final long serialVersionUID = 1L;

            @Override
            public String getObject() {
                return "width: " + model.getObject().getCompletion() + "%;"; //$NON-NLS-1$ //$NON-NLS-2$
            }
        };
    }

    private IModel<String> getTaskNameModel(final IModel<Progression> model) {
        return new AbstractReadOnlyModel<String>() {

            private static final long serialVersionUID = 1L;

            @Override
            public String getObject() {
                return model.getObject().getTaskName();
            }
        };
    }

    private IModel<String> getSubTaskModel(final IModel<Progression> model) {
        return new AbstractReadOnlyModel<String>() {

            private static final long serialVersionUID = 1L;

            @Override
            public String getObject() {
                return model.getObject().getSubTaskName();
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

                if (progression.isDone()) {
                    // stop the self update
                    stop(target);
                    if (!progression.getStatus().isOK()) {
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
        } else {
            target.add(this);
        }
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
