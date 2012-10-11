package de.jutzig.jabylon.rest.ui.wicket.components;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.AjaxSelfUpdatingTimerBehavior;
import org.apache.wicket.markup.html.WebComponent;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.time.Duration;

import de.jutzig.jabylon.common.progress.Progression;
import de.jutzig.jabylon.rest.ui.model.ProgressionModel;

public class ProgressPanel extends Panel {

	ProgressionModel model;

	public ProgressPanel(String id, ProgressionModel model) {
		super(id, model);
		this.model = model;
		WebComponent bar = new WebComponent("bar");
		bar.add(new AttributeModifier("style", getWidthModel(model)));
		add(bar);
		Label taskname = new Label("taskname", getTaskNameModel(model));
		add(taskname);
		Label subtask = new Label("subtask", getSubTaskModel(model));
		add(subtask);
		setVisible(false);
	}

	private IModel<String> getWidthModel(final IModel<Progression> model) {
		return new AbstractReadOnlyModel<String>() {

			private static final long serialVersionUID = 1L;

			@Override
			public String getObject() {
				return "width: " + model.getObject().getCompletion()+"%;";
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

	public void start(AjaxRequestTarget target, final Component... additionalTargets) {

		setVisible(true);

		add(new AjaxSelfUpdatingTimerBehavior(Duration.ONE_SECOND) {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onPostProcessTarget(AjaxRequestTarget target) {
			    if(additionalTargets!=null)
			    {
			        for (int i = 0; i < additionalTargets.length; i++)
                    {
			            target.add(additionalTargets);
                    }
			    }
				ProgressionModel model = (ProgressionModel) getDefaultModel();
				Progression progression = model.getObject();

				if (progression.isDone()) {
					// stop the self update
					stop(target);
					ProgressPanel.this.setVisible(false);
					onDone(target, additionalTargets);
				}
			}

		});
		if (getParent() != null) {
			target.add(getParent());
		} else {
			target.add(this);
		}
	}


    protected void onDone(AjaxRequestTarget target, Component[] additionalTargets)
    {


    }

	public ProgressionModel getModel() {
		return model;
	}
	/**
	 *
	 */
	private static final long serialVersionUID = 2573454585436627297L;

}
