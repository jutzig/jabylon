package de.jutzig.jabylon.ui.components;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.cdo.CDOObject;
import org.eclipse.emf.cdo.util.CommitException;
import org.eclipse.emf.common.util.EList;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.vaadin.data.Item;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeListener;
import com.vaadin.terminal.CompositeErrorMessage;
import com.vaadin.terminal.ErrorMessage;
import com.vaadin.terminal.UserError;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.Component.Listener;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.ColumnGenerator;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

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
import de.jutzig.jabylon.ui.review.ReviewUtil;
import de.jutzig.jabylon.ui.review.internal.PropertyReviewService;
import de.jutzig.jabylon.ui.util.PropertyFilter;

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
	private PropertyPairItem currentItem;
	private PropertyReviewService reviewService;
	private Table table;
	private PropertyToolArea propertyToolArea;

	public PropertiesEditor(PropertyFileDescriptor descriptor) {
		this.descriptor = descriptor;
		reviews = buildReviews(descriptor);
		reviewService = new PropertyReviewService();
	}

	private Multimap<String, Review> buildReviews(PropertyFileDescriptor descriptor) {
		Multimap<String, Review> map = ArrayListMultimap.create();
		if (descriptor == null)
			return map;
		PropertyFileReview fileReview = ReviewUtil.getReviewFor(descriptor);
		if (fileReview != null) {
			EList<Review> reviews = fileReview.getReviews();
			for (Review review : reviews) {
				map.put(review.getKey(), review);
			}
		}

		return map;
	}

	@Override
	public Component createContents() {
		HorizontalSplitPanel split = new HorizontalSplitPanel();
		split.setSizeFull();
		split.setFirstComponent(createMainArea());
		propertyToolArea = createToolArea();
		split.setSecondComponent(propertyToolArea);
		split.setSplitPosition(80);
		return split;
	}

	private PropertyToolArea createToolArea() {
		PropertyToolArea toolArea = new PropertyToolArea();
		toolArea.init(descriptor.getMaster(), descriptor);
		return toolArea;
	}

	protected Component createMainArea() {
		layout = new GridLayout();
		layout.setColumns(2);
		layout.setRows(3);
		layout.setSpacing(true);
		layout.setMargin(true);
		layout.setSizeFull();
		
		VerticalLayout tableArea = new VerticalLayout();
		TextField filterBox = new TextField();
		filterBox.addListener(new TextChangeListener() {

			@Override
			public void textChange(TextChangeEvent event) {
				propertyPairContainer.removeAllContainerFilters();
				propertyPairContainer.addContainerFilter(new PropertyFilter(event.getText()));

			}
		});
		filterBox.setInputPrompt("Filter");
		tableArea.addComponent(filterBox);

		table = new Table();
		target = descriptor.loadProperties();
		source = descriptor.getMaster().loadProperties();

		propertyPairContainer = new PropertyPairContainer(source, target);
		table.setContainerDataSource(propertyPairContainer);
		table.setVisibleColumns(propertyPairContainer.getContainerPropertyIds().subList(0, 2).toArray());
		table.setWidth(100, Table.UNITS_PERCENTAGE);
		table.addGeneratedColumn("Problems", new ColumnGenerator() {

			@Override
			public Object generateCell(Table source, Object itemId, Object columnId) {

				if (reviews.containsKey(itemId)) {
					Embedded embedded = new Embedded("", ImageConstants.IMAGE_ERROR);
					
						Review review = reviews.get((String) itemId).iterator().next();
						//TODO: this can't be the right way to refresh?
						if(review.cdoInvalid())
						{
							reviews.remove(itemId, review); //the review is no longer valid
							embedded.setIcon(ImageConstants.IMAGE_OK);
							embedded.setDescription("");
						}
						else
						{
							embedded.setDescription(review.getMessage());
						}
							
					return embedded;
				} else
					return new Embedded("", ImageConstants.IMAGE_OK);
			}
		});

		table.setColumnHeaderMode(Table.COLUMN_HEADER_MODE_EXPLICIT);

		table.setColumnHeaders(new String[] { "Original", "Translation", "Problems" });
		table.setColumnExpandRatio(propertyPairContainer.getContainerPropertyIds().get(0), 1.0f);
		table.setColumnExpandRatio(propertyPairContainer.getContainerPropertyIds().get(1), 1.0f);
		table.setColumnExpandRatio("Problems", 0.0f);
		
		table.setEditable(false);
		table.setWriteThrough(false);

		table.setSelectable(true);
		table.setMultiSelect(false);
		table.setImmediate(true); // react at once when something is selected
		table.addListener((Table.ValueChangeListener)this);

		tableArea.addComponent(table);
		layout.addComponent(tableArea, 0, 0, 1, 1);

		createEditorArea();
		return layout;
	}

	private void createEditorArea() {
		keyLabel = new Label();
		keyLabel.setValue("No Selection");
		layout.addComponent(keyLabel, 0, 2, 1, 2);
		layout.setColumnExpandRatio(0, 1.0f);
		layout.setColumnExpandRatio(1, 1.0f);
		orignal = new TextArea();
		orignal.setRows(5);
		orignal.setReadOnly(true);
		orignal.setWidth(100, TextArea.UNITS_PERCENTAGE);
		
		layout.addComponent(orignal);

		translated = new TextArea();
		translated.setRows(5);
		translated.setWidth(100, TextArea.UNITS_PERCENTAGE);

		translated.setNullRepresentation("");
		translated.addListener((TextChangeListener) this);
		translated.setWriteThrough(true);
		translated.setImmediate(true);
		layout.addComponent(translated);

		orignalComment = new TextArea();
		orignalComment.setReadOnly(true);
		orignalComment.setWidth(100, TextArea.UNITS_PERCENTAGE);
		orignalComment.setRows(3);
		orignalComment.setNullRepresentation("");
		layout.addComponent(orignalComment);
		

		translatedComment = new TextArea();
		translatedComment.setImmediate(true);
		translatedComment.setWidth(100, TextArea.UNITS_PERCENTAGE);
		translatedComment.setRows(3);
		
		translatedComment.setNullRepresentation("");
		translatedComment.addListener((TextChangeListener) this);
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
		if(currentItem==null)
			return;
		reviews.removeAll(currentItem.getKey());
		translated.setComponentError(null);
		currentItem.getTargetProperty().setValue(event.getText()); //apply current value first
		Collection<Review> reviewList = reviewService.review(descriptor, currentItem.getSourceProperty(), currentItem.getTargetProperty());
		if(!reviewList.isEmpty())
		{
			reviews.putAll((String) currentItem.getKey(), reviewList);
			List<ErrorMessage> errors = new ArrayList<ErrorMessage>(reviewList.size());
			for (Review review : reviewList) {
				UserError error = new UserError(review.getMessage(),UserError.CONTENT_TEXT,ErrorMessage.ERROR);
				errors.add(error);
			}
			CompositeErrorMessage message = new CompositeErrorMessage(errors);			
			translated.setComponentError(message);
		}
		table.refreshRowCache();

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
		Item theItem = propertyPairContainer.getItem(value);
		currentItem = (PropertyPairItem) theItem;
		propertyToolArea.selectionChanged(propertyPairContainer, reviews.get((String) currentItem.getKey()));
		currentItem.getSourceProperty();

		keyLabel.setValue(currentItem.getKey());
		translated.setPropertyDataSource(currentItem.getTarget());
		orignal.setPropertyDataSource(currentItem.getSource());

		translatedComment.setPropertyDataSource(currentItem.getTargetComment());
		orignalComment.setPropertyDataSource(currentItem.getSourceComment());
		
		translated.setComponentError(null);
	}

}
