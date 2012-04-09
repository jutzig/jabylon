package de.jutzig.jabylon.ui.components;

import java.io.IOException;

import org.eclipse.emf.cdo.util.CommitException;

import com.vaadin.data.Item;
import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeListener;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextArea;

import de.jutzig.jabylon.cdo.connector.Modification;
import de.jutzig.jabylon.cdo.connector.TransactionUtil;
import de.jutzig.jabylon.properties.PropertyFile;
import de.jutzig.jabylon.properties.PropertyFileDescriptor;
import de.jutzig.jabylon.properties.util.PropertiesResourceImpl;
import de.jutzig.jabylon.ui.breadcrumb.CrumbTrail;
import de.jutzig.jabylon.ui.container.PropertyPairContainer;
import de.jutzig.jabylon.ui.container.PropertyPairContainer.PropertyPairItem;

public class PropertiesEditor extends GridLayout implements CrumbTrail,
		ItemClickListener, TextChangeListener {

	private PropertyFileDescriptor descriptor;
	private TextArea orignal;
	private TextArea translated;
	private Label keyLabel;
	private PropertyFile target;
	private PropertyFile source;
	private boolean dirty;
	private Button safeButton;
	private TextArea orignalComment;
	private TextArea translatedComment;

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

		PropertyPairContainer propertyPairContainer = new PropertyPairContainer(
				source, target);
		table.setContainerDataSource(propertyPairContainer);
		table.setColumnHeaderMode(Table.COLUMN_HEADER_MODE_EXPLICIT);
		table.setVisibleColumns(propertyPairContainer.getContainerPropertyIds().subList(0, 2).toArray());
		table.setColumnHeaders(new String[] { "Original", "Translation" });
		table.setEditable(false);
		table.setWriteThrough(false);
		table.setWidth(100, UNITS_PERCENTAGE);

		table.setSelectable(true);
		table.setMultiSelect(false);
		table.setImmediate(true); // react at once when something is selected
		table.addListener(this);
		addComponent(table, 0, 0, 1, 1);

		createEditorArea();
	}

	private void createEditorArea() {
		keyLabel = new Label();
		keyLabel.setValue("No Selection");
		addComponent(keyLabel, 0, 2, 1, 2);

		orignal = new TextArea();
		orignal.setColumns(40);
		orignal.setRows(5);
		// orignal.setEnabled(false);
		orignal.setReadOnly(true);
		addComponent(orignal);
		
		translated = new TextArea();
		translated.setColumns(40);
		translated.setRows(5);
//		translated.setWidth(400, UNITS_PIXELS);
		
		translated.setNullRepresentation("");
		translated.addListener(this);
		translated.setWriteThrough(true);
		addComponent(translated);
		
		orignalComment = new TextArea();
//		orignalComment.setWidth(400, UNITS_PIXELS);
		orignalComment.setReadOnly(true);
		orignalComment.setColumns(40);
		orignalComment.setRows(3);
//		orignalComment.setHeight(30, UNITS_PIXELS);
		addComponent(orignalComment);
		
		translatedComment = new TextArea();
		translatedComment.setImmediate(true);
//		translatedComment.setWidth(400, UNITS_PIXELS);
		translatedComment.setRows(3);
		translatedComment.setColumns(40);
		translatedComment.setNullRepresentation("");
		translatedComment.addListener(this);
		translatedComment.setInputPrompt("Comment");
		translatedComment.setWriteThrough(true);
		addComponent(translatedComment);
		
		
		safeButton = new Button();
		safeButton.setEnabled(false);
		safeButton.setCaption("Save");
		safeButton.addListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				final PropertiesResourceImpl resource = new PropertiesResourceImpl(
						descriptor.absolutPath());
				resource.getContents().add(target);
				try {
					resource.save(null);
					// do it after save, because safe eliminated all empty ones
					TransactionUtil
							.commit(descriptor,
									new Modification<PropertyFileDescriptor, PropertyFileDescriptor>() {
										@Override
										public PropertyFileDescriptor apply(
												PropertyFileDescriptor object) {
											object.setKeys(resource
													.getSavedProperties());
											object.updatePercentComplete();
											return object;
										}
									});
					setDirty(false);
					getWindow().showNotification("File safed",descriptor.getLocation().lastSegment());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (CommitException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});
		addComponent(safeButton);

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
		translated.setPropertyDataSource(item.getTarget());
		orignal.setPropertyDataSource(item.getSource());
		
		translatedComment.setPropertyDataSource(item.getTargetComment());
		orignalComment.setPropertyDataSource(item.getSourceComment());

	}

	@Override
	public void textChange(TextChangeEvent event) {
		setDirty(true);

	}

	public void setDirty(boolean dirty) {
		this.dirty = dirty;
		safeButton.setEnabled(dirty);

	}

	@Override
	public boolean isDirty() {
		return dirty;
	}

}
