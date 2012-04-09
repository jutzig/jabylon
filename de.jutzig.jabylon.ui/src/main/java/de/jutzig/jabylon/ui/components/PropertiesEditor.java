package de.jutzig.jabylon.ui.components;

import java.io.IOException;

import org.eclipse.emf.cdo.util.CommitException;

import com.vaadin.data.Item;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.Button.ClickEvent;

import de.jutzig.jabylon.cdo.connector.Modification;
import de.jutzig.jabylon.cdo.connector.TransactionUtil;
import de.jutzig.jabylon.properties.PropertyFile;
import de.jutzig.jabylon.properties.PropertyFileDescriptor;
import de.jutzig.jabylon.properties.util.PropertiesResourceImpl;
import de.jutzig.jabylon.ui.breadcrumb.BreadCrumb;
import de.jutzig.jabylon.ui.breadcrumb.CrumbTrail;
import de.jutzig.jabylon.ui.container.PropertyPairContainer;
import de.jutzig.jabylon.ui.container.PropertyPairContainer.PropertyPairItem;

public class PropertiesEditor extends GridLayout implements CrumbTrail, ItemClickListener {

	private PropertyFileDescriptor descriptor;
	private TextArea orignal;
	private TextArea translated;
	private Label keyLabel;
	private PropertyFile target;
	private PropertyFile source;

	public PropertiesEditor(PropertyFileDescriptor descriptor) {
		this.descriptor = descriptor;
		setColumns(2);
		setRows(3);
		createContents();
		setSpacing(true);
		setMargin(true);
	}
	
	
	
	
	
	private void createContents() {
		Table table = new Table();
		target = descriptor.loadProperties();
		source = descriptor.getMaster().loadProperties();
		
		PropertyPairContainer propertyPairContainer = new PropertyPairContainer(source,target);
		table.setContainerDataSource(propertyPairContainer);
		table.setColumnHeaderMode(Table.COLUMN_HEADER_MODE_EXPLICIT);
		table.setVisibleColumns(propertyPairContainer.getContainerPropertyIds().toArray());
		table.setColumnHeaders(new String[]{"Original","Translation"});
		table.setEditable(false);
		table.setWriteThrough(false);
		table.setWidth(100, UNITS_PERCENTAGE);
		
		table.setSelectable(true);
        table.setMultiSelect(false);
        table.setImmediate(true); // react at once when something is selected
        table.addListener(this);
        addComponent(table,0,0,1,1);
        
		
        createEditorArea();
	}





	private void createEditorArea() {
		keyLabel = new Label();
		keyLabel.setValue("No Selection");
		addComponent(keyLabel,0,2,1,2);
		
		orignal = new TextArea();
		orignal.setWidth(400, UNITS_PIXELS);
//		orignal.setEnabled(false);
		orignal.setReadOnly(true);
		
		addComponent(orignal);
		translated = new TextArea();
		translated.setWidth(400, UNITS_PIXELS);
		translated.setNullRepresentation("");
		
		addComponent(translated);
		
		Button button = new Button();
		button.setCaption("Save");
		button.addListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				final PropertiesResourceImpl resource = new PropertiesResourceImpl(descriptor.absolutPath());
				resource.getContents().add(target);
				try {
					resource.save(null);
					//do it after save, because safe eliminated all empty ones
					TransactionUtil.commit(descriptor, new Modification<PropertyFileDescriptor, PropertyFileDescriptor>() {
						@Override
						public PropertyFileDescriptor apply(PropertyFileDescriptor object) {
							object.setKeys(resource.getSavedProperties());
							return object;
						}
					});
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (CommitException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		addComponent(button);
		
	}





	@Override
	public CrumbTrail walkTo(String path) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Component getComponent() {
		return this;
	}

	@Override
	public String getTrailCaption() {
		return descriptor.getLocation().toString();
	}





	@Override
	public void itemClick(ItemClickEvent event) {
		Item theItem = event.getItem();
		PropertyPairItem item = (PropertyPairItem) theItem;
		item.getSourceProperty();
		
		keyLabel.setValue(item.getSourceProperty().getKey());
//		orignal.setValue(item.getSourceProperty().getValue());
//		
		translated.setPropertyDataSource(item.getTarget());
		translated.setWriteThrough(true);
		orignal.setPropertyDataSource(item.getSource());
		
	}



}
