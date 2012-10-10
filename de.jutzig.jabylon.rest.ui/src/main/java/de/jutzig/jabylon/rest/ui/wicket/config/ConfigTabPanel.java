package de.jutzig.jabylon.rest.ui.wicket.config;

import java.util.List;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.eclipse.emf.cdo.CDOObject;
import org.eclipse.emf.cdo.transaction.CDOTransaction;
import org.eclipse.emf.cdo.util.CommitException;
import org.eclipse.emf.cdo.view.CDOView;
import org.osgi.service.prefs.BackingStoreException;
import org.osgi.service.prefs.Preferences;

import de.jutzig.jabylon.cdo.connector.Modification;
import de.jutzig.jabylon.cdo.connector.TransactionUtil;
import de.jutzig.jabylon.common.util.DelegatingPreferences;
import de.jutzig.jabylon.common.util.PreferencesUtil;
import de.jutzig.jabylon.properties.PropertiesPackage;
import de.jutzig.jabylon.properties.Resolvable;

public class ConfigTabPanel<T extends CDOObject> extends GenericPanel<T> {

	
	private static final long serialVersionUID = 1L;

	public ConfigTabPanel(String id, final List<ConfigSection<T>> sections, final IModel<T> model, final Preferences preferences) {
		super(id, model);
		Form<T> form = new Form<T>("form", model) {
			@Override
			protected void onSubmit() {
				IModel<T> model = getModel();
				T object = model.getObject();
				CDOView cdoView = object.cdoView();
				if (cdoView instanceof CDOTransaction) {
					CDOTransaction transaction = (CDOTransaction) cdoView;
					try {
						transaction.commit();
						if (object instanceof Resolvable) {
							Resolvable r = (Resolvable) object;
							if(!r.getName().equals(preferences.name()))
							{
								//FIXME: must rename preferences properly
//								preferences = PreferencesUtil.renamePreferenceNode(preferences,r.getName());
							}
						}
						preferences.flush();
						getSession().success("Saved successfully");
					} catch (CommitException e) {
						// TODO Auto-generated catch block
						getSession().error(e.getMessage());
						e.printStackTrace();
					} catch (BackingStoreException e) {
						// TODO Auto-generated catch block
						getSession().error(e.getMessage());
						e.printStackTrace();
					}
					finally{
						transaction.close();
					}
				}
				else
					throw new IllegalStateException("not a transaction");

				super.onSubmit();
			}
		};
		
		ListView<ConfigSection<T>> view = new ListView<ConfigSection<T>>("sections", sections) {
			@Override
			protected void populateItem(ListItem<ConfigSection<T>> arg0) {
				ConfigSection<T> object = arg0.getModelObject();
				WebMarkupContainer container = object.createContents("content", model, preferences);
				arg0.add(container);
			}
		};
		form.add(view);
		
		boolean visible = containsFormComponents(sections);
		Button submitButton = new Button("submit-button", Model.of("Submit"));
		submitButton.setVisibilityAllowed(visible);
		form.add(submitButton);
		Button cancelButton = new Button("cancel-button", Model.of("Cancel"));
		cancelButton.setVisibilityAllowed(visible);
		form.add(cancelButton);
		
		add(form);
	}

	private boolean containsFormComponents(List<ConfigSection<T>> sections) {
		for (ConfigSection<T> configSection : sections) {
			if(configSection.hasFormComponents())
				return true;
		}
		return false;
	}

}
