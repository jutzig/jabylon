package de.jutzig.jabylon.ui.pages;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.emf.cdo.util.CommitException;
import org.eclipse.emf.common.util.EList;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Reindeer;

import de.jutzig.jabylon.cdo.connector.Modification;
import de.jutzig.jabylon.cdo.connector.TransactionUtil;
import de.jutzig.jabylon.properties.Project;
import de.jutzig.jabylon.properties.ProjectLocale;
import de.jutzig.jabylon.properties.PropertiesFactory;
import de.jutzig.jabylon.properties.PropertyFileDescriptor;
import de.jutzig.jabylon.ui.applications.MainDashboard;
import de.jutzig.jabylon.ui.breadcrumb.CrumbTrail;
import de.jutzig.jabylon.ui.components.ResolvableProgressIndicator;
import de.jutzig.jabylon.ui.components.PropertiesEditor;
import de.jutzig.jabylon.ui.components.StaticProgressIndicator;
import de.jutzig.jabylon.ui.resources.ImageConstants;

public class ProjectLocaleDashboard extends Panel implements CrumbTrail, ClickListener {

	private Project project;
	private ProjectLocale locale;
	Map<PropertyFileDescriptor, PropertyFileDescriptor> masterToTransation;

	public ProjectLocaleDashboard(ProjectLocale locale) {
		this.locale = locale;
		VerticalLayout layout = new VerticalLayout();
		setContent(layout);
		layout.setMargin(true);
		layout.setSpacing(true);
		masterToTransation = associate(locale);
		createContents(layout);

	}


	private void createContents(Layout parent) {
		buildHeader(parent);
		
		final Table table = new Table();
		table.addContainerProperty("location", Button.class, null);
		table.addContainerProperty("progress", ResolvableProgressIndicator.class, null);
		table.setColumnWidth("progress", 110);
//		table.setColumnWidth(locale,400);
		for (Entry<PropertyFileDescriptor, PropertyFileDescriptor> entry : masterToTransation.entrySet()) {
			Button fileName = new Button(entry.getKey().getLocation().toString());
			fileName.setIcon(entry.getValue()==null ? ImageConstants.IMAGE_NEW_PROPERTIES_FILE : ImageConstants.IMAGE_PROPERTIES_FILE);
			fileName.setStyleName(Reindeer.BUTTON_LINK);
			
			fileName.setData(entry);
			fileName.addListener(this);

			PropertyFileDescriptor translation = entry.getValue();
			StaticProgressIndicator progress = new ResolvableProgressIndicator(translation);
			
			table.addItem(new Object[] {fileName,progress}, entry.getKey().cdoID());
		}
		
		addComponent(table);

	}

	private Map<PropertyFileDescriptor, PropertyFileDescriptor> associate(
			ProjectLocale locale) {
		ProjectLocale master = locale.getProjectVersion().getMaster();
		Map<PropertyFileDescriptor, PropertyFileDescriptor> result = new HashMap<PropertyFileDescriptor, PropertyFileDescriptor>();
		for (PropertyFileDescriptor descriptor : master.getDescriptors()) {
			result.put(descriptor, null);
		}
		
		for (PropertyFileDescriptor descriptor : locale.getDescriptors()) {
			result.put(descriptor.getMaster(), descriptor);
		}
		return result;
	}


	private void buildHeader(Layout parent) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public CrumbTrail walkTo(String path) {
		return new PropertiesEditor(getDescriptor(path));
	}

	private PropertyFileDescriptor getDescriptor(String path) {
		EList<PropertyFileDescriptor> descriptors = locale.getDescriptors();
		for (PropertyFileDescriptor propertyFileDescriptor : descriptors) {
			if(path.equals(propertyFileDescriptor.getLocation().toString()))
				return propertyFileDescriptor;
		}
		return null;
	}


	@Override
	public Component getComponent() {
		return this;
	}

	@Override
	public String getTrailCaption() {
		return locale.isMaster() ? "Master" : locale.getLocale().getDisplayName();
	}

	@Override
	public void buttonClick(ClickEvent event) {
		final Entry<PropertyFileDescriptor, PropertyFileDescriptor> entry = (Entry<PropertyFileDescriptor, PropertyFileDescriptor>) event.getButton().getData();
		PropertyFileDescriptor target = entry.getValue();
		if(target==null)
		{
			//create a new one
			final PropertyFileDescriptor newTarget = PropertiesFactory.eINSTANCE.createPropertyFileDescriptor();
			target = newTarget;
			newTarget.setVariant(locale.getLocale());
			newTarget.setMaster(entry.getKey());
			newTarget.computeLocation();
			try {
				target = TransactionUtil.commit(locale, new Modification<ProjectLocale, PropertyFileDescriptor>() {
					@Override
					public PropertyFileDescriptor apply(ProjectLocale object) {

						object.getDescriptors().add(newTarget);
						return newTarget;
					}
				});
			} catch (CommitException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//change the icon
			event.getButton().setIcon(ImageConstants.IMAGE_PROPERTIES_FILE);
			entry.setValue(target);
		}
		MainDashboard.getCurrent().getBreadcrumbs().walkTo(target.getLocation().toString());
		
	}


	@Override
	public boolean isDirty() {
		// TODO Auto-generated method stub
		return false;
	}
	
}
