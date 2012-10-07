package de.jutzig.jabylon.rest.ui.wicket.config;

import java.util.List;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.eclipse.emf.cdo.CDOObject;
import org.eclipse.emf.cdo.transaction.CDOTransaction;
import org.eclipse.emf.cdo.util.CommitException;
import org.eclipse.emf.cdo.view.CDOView;

import de.jutzig.jabylon.cdo.connector.Modification;
import de.jutzig.jabylon.cdo.connector.TransactionUtil;
import de.jutzig.jabylon.properties.PropertiesPackage;

public class ConfigTabPanel<T extends CDOObject> extends GenericPanel<T> {

	
	private static final long serialVersionUID = 1L;

	public ConfigTabPanel(String id, final List<ConfigSection<T>> sections, final IModel<T> model) {
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
						getSession().success("Saved successfully");
					} catch (CommitException e) {
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
				WebMarkupContainer container = object.createContents("content", model);
				arg0.add(container);
			}
		};
		form.add(view);
		add(form);
	}

}
