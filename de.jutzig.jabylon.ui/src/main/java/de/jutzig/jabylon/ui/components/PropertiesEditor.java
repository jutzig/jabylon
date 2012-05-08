package de.jutzig.jabylon.ui.components;

import org.eclipse.emf.cdo.CDOObject;
import org.eclipse.emf.cdo.eresource.CDOResource;
import org.eclipse.emf.cdo.util.CommitException;
import org.eclipse.emf.cdo.view.CDOView;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.vaadin.data.Item;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.ColumnGenerator;
import com.vaadin.ui.TextArea;

import de.jutzig.jabylon.cdo.connector.Modification;
import de.jutzig.jabylon.cdo.connector.TransactionUtil;
import de.jutzig.jabylon.properties.Property;
import de.jutzig.jabylon.properties.PropertyFile;
import de.jutzig.jabylon.properties.PropertyFileDescriptor;
import de.jutzig.jabylon.resources.persistence.PropertyPersistenceService;
import de.jutzig.jabylon.review.PropertyFileReview;
import de.jutzig.jabylon.review.Review;
import de.jutzig.jabylon.ui.applications.MainDashboard;
import de.jutzig.jabylon.ui.breadcrumb.CrumbTrail;
import de.jutzig.jabylon.ui.container.PropertyPairContainer;
import de.jutzig.jabylon.ui.container.PropertyPairContainer.PropertyPairItem;
import de.jutzig.jabylon.ui.resources.ImageConstants;

@SuppressWarnings("serial")
public class PropertiesEditor implements CrumbTrail, Table.ValueChangeListener, TextChangeListener {

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
	private GridLayout layout;
	private PropertyPairContainer propertyPairContainer;
	private Multimap<String, Review> reviews;

	public PropertiesEditor(PropertyFileDescriptor descriptor) {
		this.descriptor = descriptor;
		reviews = buildReviews(descriptor);
	}

	private Multimap<String, Review> buildReviews(PropertyFileDescriptor descriptor) {
		Multimap<String, Review> map = ArrayListMultimap.create();
		URI fullPath = descriptor.fullPath();
		CDOView view = descriptor.cdoView();
		if(view.hasResource("review/"+fullPath.toString()))
		{
			CDOResource resource = view.getResource("review/"+fullPath.toString());
			EObject object = resource.getContents().get(0);
			if (object instanceof PropertyFileReview) {
				PropertyFileReview fileReview = (PropertyFileReview) object;
				EList<Review> reviews = fileReview.getReviews();
				for (Review review : reviews) {
					map.put(review.getKey(), review);
				}
				
			}
		}
		return map;
	}

	@Override
	public Component createContents() {
		layout = new GridLayout();
		layout.setColumns(2);
		layout.setRows(3);
		layout.setSpacing(true);
		layout.setMargin(true);
		Table table = new Table();
		target = descriptor.loadProperties();
		source = descriptor.getMaster().loadProperties();

		propertyPairContainer = new PropertyPairContainer(source, target);
		table.setContainerDataSource(propertyPairContainer);
		table.setVisibleColumns(propertyPairContainer.getContainerPropertyIds().subList(0, 2).toArray());
		table.addGeneratedColumn("Problems", new ColumnGenerator() {
			
			@Override
			public Object generateCell(Table source, Object itemId, Object columnId) {

				if(reviews.containsKey(itemId))
				{
					Embedded embedded = new Embedded("", ImageConstants.IMAGE_ERROR);					
					Review review = reviews.get((String) itemId).iterator().next();
					embedded.setDescription(review.getMessage());
					return embedded;
				}
				else
					return new Embedded("", ImageConstants.IMAGE_OK);
			}
		});
		
		table.setColumnHeaderMode(Table.COLUMN_HEADER_MODE_EXPLICIT);
		
		table.setColumnHeaders(new String[] { "Original", "Translation","Problems"});
		table.setColumnWidth(propertyPairContainer.getContainerPropertyIds().get(0), 420);
		table.setColumnWidth(propertyPairContainer.getContainerPropertyIds().get(1), 440);
		table.setEditable(false);
		table.setWriteThrough(false);
		table.setWidth(100, Component.UNITS_PERCENTAGE);

		table.setSelectable(true);
		table.setMultiSelect(false);
		table.setImmediate(true); // react at once when something is selected
		table.addListener(this);

		layout.addComponent(table, 0, 0, 1, 1);

		createEditorArea();
		return layout;
	}

	private void createEditorArea() {
		keyLabel = new Label();
		keyLabel.setValue("No Selection");
		layout.addComponent(keyLabel, 0, 2, 1, 2);

		orignal = new TextArea();
		// orignal.setWidth(400, UNITS_PIXELS);
		orignal.setColumns(40);
		orignal.setRows(5);
		orignal.setReadOnly(true);
		layout.addComponent(orignal);

		translated = new TextArea();
		translated.setColumns(40);
		translated.setRows(5);
		// translated.setWidth(400, UNITS_PIXELS);

		translated.setNullRepresentation("");
		translated.addListener((TextChangeListener)this);
		translated.setWriteThrough(true);
		layout.addComponent(translated);

		orignalComment = new TextArea();
		// orignalComment.setWidth(400, UNITS_PIXELS);
		orignalComment.setReadOnly(true);
		orignalComment.setColumns(40);
		orignalComment.setRows(3);
		orignalComment.setNullRepresentation("");
		// orignalComment.setHeight(30, UNITS_PIXELS);
		layout.addComponent(orignalComment);

		translatedComment = new TextArea();
		translatedComment.setImmediate(true);
		// translatedComment.setWidth(400, UNITS_PIXELS);
		translatedComment.setRows(3);
		translatedComment.setColumns(40);
		translatedComment.setNullRepresentation("");
		translatedComment.addListener((TextChangeListener)this);
		translatedComment.setInputPrompt("Comment");
		translatedComment.setWriteThrough(true);
		layout.addComponent(translatedComment);

		safeButton = new Button();
		safeButton.setEnabled(false);
		safeButton.setCaption("Save");
		safeButton.addListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				PropertyPersistenceService propertyPersistence = MainDashboard.getCurrent().getPropertyPersistence();
				propertyPersistence.saveProperties(descriptor, target);
				final int filledKeys = getFilledKeys(target);
				try {

					// do it after save, because safe eliminated all empty ones
					descriptor = TransactionUtil.commit(descriptor, new Modification<PropertyFileDescriptor, PropertyFileDescriptor>() {
						@Override
						public PropertyFileDescriptor apply(PropertyFileDescriptor object) {
							object.setKeys(filledKeys);
							object.updatePercentComplete();
							return object;
						}
					});
					setDirty(false);
					layout.getWindow().showNotification("File saved", descriptor.getLocation().lastSegment());
				} catch (CommitException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});
		layout.addComponent(safeButton);

	}

	protected int getFilledKeys(PropertyFile target2) {
		int counter = 0;
		for (Property prop : target2.getProperties()) {
			if(prop.getValue()!=null && prop.getValue().length()>0)
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
		if(value==null)
			return;
		Item theItem = propertyPairContainer.getItem(value);
		PropertyPairItem item = (PropertyPairItem) theItem;
		item.getSourceProperty();

		
		keyLabel.setValue(item.getKey());
		translated.setPropertyDataSource(item.getTarget());
		orignal.setPropertyDataSource(item.getSource());

		translatedComment.setPropertyDataSource(item.getTargetComment());
		orignalComment.setPropertyDataSource(item.getSourceComment());

	}

}
