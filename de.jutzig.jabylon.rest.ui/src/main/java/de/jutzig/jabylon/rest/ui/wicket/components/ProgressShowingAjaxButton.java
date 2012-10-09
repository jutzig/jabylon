package de.jutzig.jabylon.rest.ui.wicket.components;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.Form;

import de.jutzig.jabylon.common.progress.RunnableWithProgress;
import de.jutzig.jabylon.rest.ui.Activator;

public class ProgressShowingAjaxButton extends AjaxButton {



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
		
		panel.start(target);
		long id = Activator.getDefault().getProgressService().schedule(runnable);
		panel.getModel().setTaskID(id);
	}
	
	

}
