package de.jutzig.jabylon.rest.ui.wicket.components;

import java.util.Map;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.AjaxRequestTarget.IJavaScriptResponse;

/**
 * Provides pages which implement {@link IAjaxFeedbackPage} the ability to display
 * page-level feedback messages after AJAX requests
 */
public class AjaxFeedbackListener implements AjaxRequestTarget.IListener {

	@Override
	public void onBeforeRespond(Map<String, Component> map,
			AjaxRequestTarget target) {
		if (target.getPage() instanceof IAjaxFeedbackPage) {
			IAjaxFeedbackPage feedbackPage = (IAjaxFeedbackPage) target.getPage();
			feedbackPage.showFeedback(target);
		}
	}

	@Override
	public void onAfterRespond(Map<String, Component> map,
			IJavaScriptResponse response) {
	}

}
