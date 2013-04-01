package de.jutzig.jabylon.rest.ui.wicket.config.sections;

import java.util.List;

import javax.inject.Inject;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.IModel;
import org.osgi.service.prefs.Preferences;

import de.jutzig.jabylon.common.review.ReviewParticipant;
import de.jutzig.jabylon.common.util.PreferencesUtil;
import de.jutzig.jabylon.properties.Project;
import de.jutzig.jabylon.rest.ui.model.BooleanPreferencesPropertyModel;
import de.jutzig.jabylon.rest.ui.wicket.config.AbstractConfigSection;
import de.jutzig.jabylon.security.CommonPermissions;

public class TranslationChecksConfigSection extends GenericPanel<Project> {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private transient List<ReviewParticipant> participants;

	public TranslationChecksConfigSection(String id, IModel<Project> model, Preferences prefs) {
		super(id, model);
		RepeatingView repeater = new RepeatingView("checks");
		if(participants!=null)
		{
			for (ReviewParticipant participant : participants) {
				WebMarkupContainer container = new WebMarkupContainer(repeater.newChildId());
				repeater.add(container);
				
				BooleanPreferencesPropertyModel propertyModel = new BooleanPreferencesPropertyModel(prefs, participant.getID(), false);
				CheckBox checkBox = new CheckBox("check",propertyModel);
				container.add(new Label("check-label",participant.getName()));
				container.add(checkBox);
				container.add(new Label("description",participant.getDescription()));
			}
		}
		add(repeater);
	}


	public static class TranslationChecksConfig extends AbstractConfigSection<Project> {

		private static final long serialVersionUID = 1L;

		@Override
		public WebMarkupContainer doCreateContents(String id, IModel<Project> input, Preferences prefs) {
			return new TranslationChecksConfigSection(id, input, prefs.node(PreferencesUtil.NODE_CHECKS));
		}

		@Override
		public void commit(IModel<Project> input, Preferences config) {
			// TODO Auto-generated method stub
			// TODO rename on filesystem

		}
		

		@Override
		public String getRequiredPermission() {
			String projectName = null;
			if(getDomainObject()!=null)
				projectName = getDomainObject().getName();
			return CommonPermissions.constructPermission(CommonPermissions.PROJECT,projectName,CommonPermissions.ACTION_EDIT);
		}

	}

}
