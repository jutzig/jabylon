package de.jutzig.jabylon.rest.ui.wicket.config;

import java.util.ArrayDeque;
import java.util.Deque;
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
import org.apache.wicket.request.mapper.parameter.PageParameters;
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
import de.jutzig.jabylon.properties.Workspace;
import de.jutzig.jabylon.rest.ui.model.AttachableModel;

public class ConfigTabPanel<T extends CDOObject> extends GenericPanel<T> {

	
	private static final long serialVersionUID = 1L;

	public ConfigTabPanel(String id, final List<ConfigSection<T>> sections, final IModel<T> model, final Preferences preferences) {
		super(id, model);
		Form<T> form = new Form<T>("form", model) {
			@Override
			protected void onSubmit() {
				IModel<T> model = getModel();
				CDOObject object = model.getObject();
				CDOView cdoView;
				if (model instanceof AttachableModel) {
					//it's a new object that needs attaching
					AttachableModel<T> attachable = (AttachableModel<T>) model;
					attachable.attach();
					CDOObject parent = (CDOObject) attachable.getObject().eContainer();
					cdoView = parent.cdoView();
				}
				else
					cdoView = object.cdoView();
				if (cdoView instanceof CDOTransaction) {
					CDOTransaction transaction = (CDOTransaction) cdoView;
					commit(preferences, object, transaction);
					model.detach();
				}
				else
					throw new IllegalStateException("not a transaction");
				

				super.onSubmit();
			}

			protected void commit(final Preferences preferences, CDOObject object, CDOTransaction transaction) {
				try {
					transaction.commit();
					if (object instanceof Resolvable) {
						Resolvable r = (Resolvable) object;
						if(!r.getName().equals(preferences.name()))
						{
							//FIXME: must rename preferences properly
//								preferences = PreferencesUtil.renamePreferenceNode(preferences,r.getName());
						}
						setResponsePage(SettingsPage.class, createPageParameters(r));
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
	
	private PageParameters createPageParameters(Resolvable r) {
		PageParameters params = new PageParameters();
		Deque<String> segments = new ArrayDeque<String>();
		Resolvable<?, ?> part = r;
		while(part!=null)
		{
			String name = part.getName();
			if(name!=null)
				segments.push(name);
			part = part.getParent(); 
		}
		int count = 0;
		for (String string : segments) {
			params.set(count++, string);
		}
		return params;
	}

}
