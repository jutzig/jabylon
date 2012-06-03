package de.jutzig.jabylon.ui.components;

import org.eclipse.emf.cdo.CDOObject;
import org.eclipse.emf.cdo.util.CommitException;

import com.vaadin.data.Item;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import de.jutzig.jabylon.cdo.connector.Modification;
import de.jutzig.jabylon.cdo.connector.TransactionUtil;
import de.jutzig.jabylon.properties.PropertiesFactory;
import de.jutzig.jabylon.properties.PropertiesPackage;
import de.jutzig.jabylon.properties.Property;
import de.jutzig.jabylon.properties.PropertyFile;
import de.jutzig.jabylon.properties.PropertyFileDescriptor;
import de.jutzig.jabylon.resources.persistence.PropertyPersistenceService;
import de.jutzig.jabylon.ui.applications.MainDashboard;
import de.jutzig.jabylon.ui.breadcrumb.CrumbTrail;
import de.jutzig.jabylon.ui.container.GenericEObjectContainer;
import de.jutzig.jabylon.ui.styles.JabylonStyle;
import de.jutzig.jabylon.ui.util.PropertyFilter;

@SuppressWarnings("serial")
public class PropertiesMasterEditor implements CrumbTrail, Table.ValueChangeListener, TextChangeListener {

	private PropertyFileDescriptor descriptor;
	private TextArea orignal;
	private TextField keyLabel;
	private PropertyFile source;
	private boolean dirty;
	private Button safeButton;
	private TextArea orignalComment;
	private VerticalLayout layout;
	private Table table;
	private GenericEObjectContainer<Property> container;

	public PropertiesMasterEditor(PropertyFileDescriptor descriptor) {
		this.descriptor = descriptor;
	}


	@Override
	public Component createContents() {
		layout = new VerticalLayout();
		layout.setSizeFull();
		layout.setSpacing(true);
		layout.setMargin(true);
		TextField filterBox = new TextField();
		filterBox.addStyleName(JabylonStyle.SEARCH_FIELD.getCSSName());
		filterBox.addListener(new TextChangeListener() {

			@Override
			public void textChange(TextChangeEvent event) {
				container.removeAllContainerFilters();
				container.addContainerFilter(new PropertyFilter(event.getText()));

			}
		});
		filterBox.setInputPrompt("Filter");
		layout.addComponent(filterBox);
		layout.setExpandRatio(filterBox, 0);

		EditableTable editableTable = new EditableTable() {
			@Override
			protected void addPressed() {
				Property property = PropertiesFactory.eINSTANCE.createProperty();
				property.setKey("enter.key");
				source.getProperties().add(property);
				
			}
		};
		editableTable.setWidth(100, EditableTable.UNITS_PERCENTAGE);
		editableTable.setSizeFull();
		table = editableTable.getTable();
		table.addStyleName(JabylonStyle.TABLE_STRIPED.getCSSName());
		table.setSizeFull();
		source = descriptor.loadProperties();

		container = new GenericEObjectContainer<Property>(source,PropertiesPackage.Literals.PROPERTY_FILE__PROPERTIES);
	
		table.setContainerDataSource(container);
		table.setVisibleColumns(new Object[]{PropertiesPackage.Literals.PROPERTY__KEY,PropertiesPackage.Literals.PROPERTY__VALUE});
		table.setWidth(100, Table.UNITS_PERCENTAGE);
//		table.addGeneratedColumn("Problems", new ColumnGenerator() {
//
//			@Override
//			public Object generateCell(Table source, Object itemId, Object columnId) {
//
//				if (reviews.containsKey(itemId)) {
//					Embedded embedded = new Embedded("", ImageConstants.IMAGE_ERROR);
//
//					Review review = reviews.get((String) itemId).iterator().next();
//					// TODO: this can't be the right way to refresh?
//					if (review.cdoInvalid()) {
//						reviews.remove(itemId, review); // the review is no
//														// longer valid
//						embedded.setIcon(ImageConstants.IMAGE_OK);
//						embedded.setDescription("");
//					} else {
//						embedded.setDescription(review.getMessage());
//					}
//
//					return embedded;
//				} else
//					return new Embedded("", ImageConstants.IMAGE_OK);
//			}
//		});

		table.setColumnHeaderMode(Table.COLUMN_HEADER_MODE_EXPLICIT);

		table.setColumnHeaders(new String[] { "Key", "Value"/*, "Problems" */});
//		table.setColumnExpandRatio(propertyPairContainer.getContainerPropertyIds().get(0), 1.0f);
//		table.setColumnExpandRatio(propertyPairContainer.getContainerPropertyIds().get(1), 1.0f);
//		table.setColumnExpandRatio("Problems", 0.0f);

		table.setEditable(false);
		table.setWriteThrough(true);

		table.setSelectable(true);
		table.setMultiSelect(false);
		table.setImmediate(true); // react at once when something is selected
		table.addListener((Table.ValueChangeListener) this);

		layout.addComponent(editableTable);
		layout.setExpandRatio(editableTable, 2);

		createEditorArea();
		return layout;
	}



	private void createEditorArea() {
		Panel editorArea = new Panel();
		
		GridLayout grid = new GridLayout(2, 2);
		grid.setSizeFull();
		grid.setSpacing(true);
		keyLabel = new TextField();
		keyLabel.setValue("No Selection");
		keyLabel.setWriteThrough(true);
		keyLabel.setImmediate(true);
		keyLabel.addListener((TextChangeListener)this);
		grid.addComponent(keyLabel, 0, 0, 0, 0);
		grid.setColumnExpandRatio(0, 0.0f);
		grid.setColumnExpandRatio(1, 0.0f);
		grid.setRowExpandRatio(0, 0);
		grid.setRowExpandRatio(1, 0);
		

		orignalComment = new TextArea();
		orignalComment.setWidth(100, TextArea.UNITS_PERCENTAGE);
		orignalComment.setRows(3);
		orignalComment.setNullRepresentation("");
		orignalComment.setInputPrompt("enter comment");
		orignalComment.addListener((TextChangeListener)this);
		orignalComment.setWriteThrough(true);
		orignalComment.setImmediate(true);
		grid.addComponent(orignalComment,0,1,0,1);
		
		orignal = new TextArea();
		orignal.setRows(5);
		orignal.setWidth(100, TextArea.UNITS_PERCENTAGE);
		orignal.setInputPrompt("enter value");
		orignal.addListener((TextChangeListener)this);
		orignal.setImmediate(true);
		orignal.setWriteThrough(true);
		orignal.setNullRepresentation("");
		
		grid.addComponent(orignal,1,0,1,1);




		safeButton = new Button();
		safeButton.setEnabled(false);
		safeButton.setCaption("Save");
		safeButton.addListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
	
				final int filledKeys = getFilledKeys(source);
				try {

					descriptor = TransactionUtil.commit(descriptor, new Modification<PropertyFileDescriptor, PropertyFileDescriptor>() {
						@Override
						public PropertyFileDescriptor apply(PropertyFileDescriptor object) {
							object.setKeys(filledKeys);
							object.updatePercentComplete();
							return object;
						}
					});
					PropertyPersistenceService propertyPersistence = MainDashboard.getCurrent().getPropertyPersistence();
					propertyPersistence.saveProperties(descriptor, source);
					setDirty(false);
					layout.getWindow().showNotification("File saved", descriptor.getLocation().lastSegment());
				} catch (CommitException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});
		editorArea.setContent(grid);
		layout.addComponent(editorArea);
		layout.setExpandRatio(editorArea, 0);
		layout.addComponent(safeButton);
		layout.setExpandRatio(safeButton, 0);

	}

	protected int getFilledKeys(PropertyFile target2) {
		int counter = 0;
		for (Property prop : target2.getProperties()) {
			if (prop.getValue() != null && prop.getValue().length() > 0)
				counter++;
		}
		return counter;
	}

	@Override
	public CrumbTrail walkTo(String path) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getTrailCaption() {
		return descriptor.getLocation().lastSegment();
	}

	@Override
	public void textChange(TextChangeEvent event) {
		setDirty(true);
//		if (currentItem == null)
//			return;
//		reviews.removeAll(currentItem.getKey());
//		translated.setComponentError(null);
//		currentItem.getTargetProperty().setValue(event.getText()); // apply
//																	// current
//																	// value
//																	// first
//		Collection<Review> reviewList = reviewService.review(descriptor, currentItem.getSourceProperty(), currentItem.getTargetProperty());
//		if (!reviewList.isEmpty()) {
//			reviews.putAll((String) currentItem.getKey(), reviewList);
//			List<ErrorMessage> errors = new ArrayList<ErrorMessage>(reviewList.size());
//			for (Review review : reviewList) {
//				UserError error = new UserError(review.getMessage(), UserError.CONTENT_TEXT, ErrorMessage.ERROR);
//				errors.add(error);
//			}
//			CompositeErrorMessage message = new CompositeErrorMessage(errors);
//			translated.setComponentError(message);
//		}
//		table.refreshRowCache();

	}

	public void setDirty(boolean dirty) {
		this.dirty = dirty;
		safeButton.setEnabled(dirty);

	}

	@Override
	public boolean isDirty() {
		return dirty;
	}

	@Override
	public CDOObject getDomainObject() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void valueChange(ValueChangeEvent event) {
		Object value = event.getProperty().getValue();
		if (value == null)
			return;
		Item theItem = container.getItem(value);

		keyLabel.setPropertyDataSource(theItem.getItemProperty(PropertiesPackage.Literals.PROPERTY__KEY));
		orignal.setPropertyDataSource(theItem.getItemProperty(PropertiesPackage.Literals.PROPERTY__VALUE));
		orignalComment.setPropertyDataSource(theItem.getItemProperty(PropertiesPackage.Literals.PROPERTY__COMMENT));
		
	}

}
