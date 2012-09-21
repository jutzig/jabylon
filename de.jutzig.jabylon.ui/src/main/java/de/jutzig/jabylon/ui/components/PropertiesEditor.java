package de.jutzig.jabylon.ui.components;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.cdo.CDOObject;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.util.EcoreUtil;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.vaadin.data.Item;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeListener;
import com.vaadin.terminal.CompositeErrorMessage;
import com.vaadin.terminal.ErrorMessage;
import com.vaadin.terminal.UserError;
import com.vaadin.ui.AbstractTextField.TextChangeEventMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.ColumnGenerator;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.VerticalSplitPanel;

import de.jutzig.jabylon.properties.PropertyFile;
import de.jutzig.jabylon.properties.PropertyFileDescriptor;
import de.jutzig.jabylon.properties.Review;
import de.jutzig.jabylon.resources.persistence.PropertyPersistenceService;
import de.jutzig.jabylon.ui.applications.MainDashboard;
import de.jutzig.jabylon.ui.breadcrumb.BreadCrumb;
import de.jutzig.jabylon.ui.breadcrumb.CrumbTrail;
import de.jutzig.jabylon.ui.container.PropertyPairContainer;
import de.jutzig.jabylon.ui.container.PropertyPairContainer.PropertyPairItem;
import de.jutzig.jabylon.ui.resources.ImageConstants;
import de.jutzig.jabylon.ui.review.internal.PropertyReviewService;
import de.jutzig.jabylon.ui.styles.JabylonStyle;
import de.jutzig.jabylon.ui.tools.SuggestionAcceptor;
import de.jutzig.jabylon.ui.util.PropertyFilter;
import de.jutzig.jabylon.ui.util.UntranslatedFilter;

@SuppressWarnings("serial")
public class PropertiesEditor implements CrumbTrail, Table.ValueChangeListener, TextChangeListener, SuggestionAcceptor {

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
	private VerticalLayout layout;
	private PropertyPairContainer propertyPairContainer;
	private Multimap<String, Review> reviews;
	private PropertyPairItem currentItem;
	private PropertyReviewService reviewService;
	private Table table;
	private PropertyToolArea propertyToolArea;
	private PropertyFilter propertyFilter = new PropertyFilter(""); //$NON-NLS-1$
	private UntranslatedFilter untranslatedFilter = new UntranslatedFilter();

	public PropertiesEditor(PropertyFileDescriptor descriptor) {
		this.descriptor = descriptor;
		reviews = buildReviews(descriptor);
		reviewService = new PropertyReviewService();
	}

	private Multimap<String, Review> buildReviews(PropertyFileDescriptor descriptor) {
		Multimap<String, Review> map = ArrayListMultimap.create();
		if (descriptor == null)
			return map;

		EList<Review> reviews = descriptor.getReviews();
		for (Review review : reviews) {
			map.put(review.getKey(), review);
		}

		return map;
	}

	@Override
	public Component createContents() {
		VerticalSplitPanel split = new VerticalSplitPanel();
		split.setSizeFull();
		propertyToolArea = createToolArea();
		split.setFirstComponent(createMainArea());
		split.setSecondComponent(propertyToolArea);
		split.setSplitPosition(70);
		return split;
	}

	private PropertyToolArea createToolArea() {
		PropertyToolArea toolArea = new PropertyToolArea();
		toolArea.init(descriptor.getMaster(), descriptor);
		return toolArea;
	}

	protected Component createMainArea() {
		layout = new VerticalLayout();

		layout.setSpacing(true);
		layout.setMargin(true);
		layout.setSizeFull();

		HorizontalLayout filterLine = new HorizontalLayout();

		TextField filterBox = new TextField();
		filterBox.addStyleName(JabylonStyle.SEARCH_FIELD.getCSSName());
		filterBox.addListener(new TextChangeListener() {

			@Override
			public void textChange(TextChangeEvent event) {
				propertyFilter.setFilterText(event.getText());
				propertyPairContainer.addContainerFilter(propertyFilter);

			}
		});
		filterBox.setInputPrompt(Messages.getString("PropertiesEditor_FILTER_INPUT_PROMPT")); //$NON-NLS-1$
		filterLine.addComponent(filterBox);

		final CheckBox untranslatedBox = new CheckBox(Messages.getString("PropertiesEditor_SHOW_ONLY_UNTRANSLATED_BUTTON_CAPTION")); //$NON-NLS-1$
		untranslatedBox.addListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				propertyPairContainer.removeContainerFilter(untranslatedFilter);
				if (untranslatedBox.getValue().equals(Boolean.TRUE))
					propertyPairContainer.addContainerFilter(untranslatedFilter);
			}
		});
		untranslatedBox.setImmediate(true);
		filterLine.addComponent(untranslatedBox);

		layout.addComponent(filterLine);
		layout.setExpandRatio(filterLine, 0);

		table = new Table();
		table.addStyleName(JabylonStyle.TABLE_STRIPED.getCSSName());
		table.setSizeFull();
		target = descriptor.loadProperties();
		source = descriptor.getMaster().loadProperties();

		propertyPairContainer = new PropertyPairContainer(source, target);
		table.setContainerDataSource(propertyPairContainer);
		table.setVisibleColumns(propertyPairContainer.getContainerPropertyIds().subList(0, 2).toArray());
		table.setWidth(100, Table.UNITS_PERCENTAGE);
		table.addGeneratedColumn(Messages.getString("PropertiesEditor_PROBLEMS_COLUMN_HEADER"), new ColumnGenerator() { //$NON-NLS-1$

					@Override
					public Object generateCell(Table source, Object itemId, Object columnId) {

						if (reviews.containsKey(itemId)) {
							Embedded embedded = new Embedded("", ImageConstants.IMAGE_ERROR); //$NON-NLS-1$

							Review review = reviews.get((String) itemId).iterator().next();
							// TODO: this can't be the right way to refresh?
							if (review.cdoInvalid()) {
								reviews.remove(itemId, review); // the review is
																// no
																// longer valid
								embedded.setIcon(ImageConstants.IMAGE_OK);
								embedded.setDescription(""); //$NON-NLS-1$
							} else {
								embedded.setDescription(review.getMessage());
							}

							return embedded;
						} else
							return new Embedded("", ImageConstants.IMAGE_OK); //$NON-NLS-1$
					}
				});

		table.setColumnHeaderMode(Table.COLUMN_HEADER_MODE_EXPLICIT);

		table.setColumnHeaders(new String[] {
				Messages.getString("PropertiesEditor_ORIGINAL_COLUMN_HEADER"), Messages.getString("PropertiesEditor_TRANSLATED_COLUMN_HEADER"), Messages.getString("PropertiesEditor_PROBLEMS_COLUMN_HEADER") }); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		table.setColumnExpandRatio(propertyPairContainer.getContainerPropertyIds().get(0), 1.0f);
		table.setColumnExpandRatio(propertyPairContainer.getContainerPropertyIds().get(1), 1.0f);
		table.setColumnExpandRatio(Messages.getString("PropertiesEditor_PROBLEMS_COLUMN_HEADER"), 0.0f); //$NON-NLS-1$

		table.setEditable(false);
		table.setWriteThrough(false);

		table.setSelectable(true);
		table.setMultiSelect(false);
		table.setImmediate(true); // react at once when something is selected
		table.addListener(this);

		layout.addComponent(table);

		layout.setExpandRatio(table, 2);
		createEditorArea();
		return layout;
	}

	private void createEditorArea() {
		Panel editorArea = new Panel();

		GridLayout grid = new GridLayout(2, 3);
		grid.setSizeFull();
		grid.setSpacing(true);
		keyLabel = new Label();
		keyLabel.setValue(Messages.getString("PropertiesEditor_NO_SELECTION_LABEL")); //$NON-NLS-1$
		grid.addComponent(keyLabel, 0, 0, 1, 0);
		grid.setColumnExpandRatio(0, 1.0f);
		grid.setColumnExpandRatio(1, 1.0f);
		orignal = new TextArea();
		orignal.setRows(3);
		orignal.setReadOnly(true);
		orignal.setWidth(100, TextArea.UNITS_PERCENTAGE);

		grid.addComponent(orignal);

		translated = new TextArea();
		translated.setRows(3);
		translated.setInvalidCommitted(true);
		translated.setWidth(100, TextArea.UNITS_PERCENTAGE);

		translated.setNullRepresentation(""); //$NON-NLS-1$
		translated.addListener((TextChangeListener) this);
		translated.setWriteThrough(true);
		translated.setImmediate(true);
        translated.setTextChangeEventMode(TextChangeEventMode.LAZY);
        translated.setTextChangeTimeout(500);

		grid.addComponent(translated);

		orignalComment = new TextArea();
		orignalComment.setReadOnly(true);
		orignalComment.setWidth(100, TextArea.UNITS_PERCENTAGE);
		orignalComment.setRows(3);
		orignalComment.setNullRepresentation(""); //$NON-NLS-1$
		grid.addComponent(orignalComment);

		translatedComment = new TextArea();
		translatedComment.setImmediate(true);
		translatedComment.setWidth(100, TextArea.UNITS_PERCENTAGE);
		translatedComment.setRows(3);

		translatedComment.setNullRepresentation(""); //$NON-NLS-1$
		translatedComment.setInputPrompt(Messages.getString("PropertiesEditor_COMMENT_INPUT_PROMPT")); //$NON-NLS-1$
		translatedComment.setWriteThrough(true);
		translatedComment.addListener((TextChangeListener) this);
		grid.addComponent(translatedComment);

		safeButton = new Button();
		safeButton.setEnabled(false);
		safeButton.setCaption(Messages.getString("PropertiesEditor_SAVE_BUTTON_CAPTION")); //$NON-NLS-1$
		safeButton.addListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {

				setDirty(false);
				PropertyPersistenceService propertyPersistence = MainDashboard.getCurrent().getPropertyPersistence();
				propertyPersistence.saveProperties(descriptor, target);
				layout.getWindow().showNotification(
						Messages.getString("PropertiesEditor_SAVED_CONFIRMATION_DIALOG_TITLE"), descriptor.getLocation().lastSegment()); //$NON-NLS-1$

			}
		});
		editorArea.setContent(grid);

		HorizontalLayout buttonArea = new HorizontalLayout();
		buttonArea.setSpacing(true);
		layout.addComponent(editorArea);
		layout.setExpandRatio(editorArea, 0);
		buttonArea.addComponent(safeButton);

		Button editTemplate = new Button(Messages.getString("PropertiesEditor_EDIT_TEMPLATE_BUTTON_CAPTION")); //$NON-NLS-1$
		editTemplate.addListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				BreadCrumb crumb = MainDashboard.getCurrent().getBreadcrumbs();
				crumb.walkTo("?master"); //$NON-NLS-1$

			}
		});
		buttonArea.addComponent(editTemplate);
		layout.addComponent(buttonArea);
		layout.setExpandRatio(buttonArea, 0);

	}

	@Override
	public CrumbTrail walkTo(String path) {
		if (path.equals("?master")) //$NON-NLS-1$
			return new PropertiesMasterEditor(descriptor.getMaster());
		return null;
	}

	@Override
	public String getTrailCaption() {
		return descriptor.getLocation().lastSegment();
	}

	@Override
	public void textChange(TextChangeEvent event) {
		setDirty(true);
		if (event.getComponent()!=translated || currentItem == null)
			return;
		
		de.jutzig.jabylon.properties.Property copy = EcoreUtil.copy(currentItem.getTargetProperty());
		copy.setValue(event.getText());
		applyValidation(copy);
	}

	protected void applyValidation(de.jutzig.jabylon.properties.Property property) {
		Collection<Review> reviewList = reviewService.review(descriptor, currentItem.getSourceProperty(), property);
		reviews.removeAll(currentItem.getKey());
		if (reviewList.isEmpty()) {
			if(translated.getComponentError()!=null)
				translated.setComponentError(null);
		}
		else{
			reviews.putAll((String) currentItem.getKey(), reviewList);
			List<ErrorMessage> errors = new ArrayList<ErrorMessage>(reviewList.size());
			for (Review review : reviewList) {
				UserError error = new UserError(review.getMessage(), UserError.CONTENT_TEXT, ErrorMessage.ERROR);
				errors.add(error);
			}
			CompositeErrorMessage message = new CompositeErrorMessage(errors);
			translated.setComponentError(message);
		}
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
		//a clone, so a change event won't mess the table selection
		PropertyPairItem clone = new PropertyPairItem(currentItem.getSourceProperty(), currentItem.getTargetProperty());
		propertyToolArea.selectionChanged(clone, reviews.get((String) clone.getKey()), this);
		

		keyLabel.setValue(clone.getKey());
		translated.setPropertyDataSource(clone.getTarget());
		orignal.setPropertyDataSource(clone.getSource());

		translatedComment.setPropertyDataSource(clone.getTargetComment());
		orignalComment.setPropertyDataSource(clone.getSourceComment());

		applyValidation(clone.getTargetProperty());
	}

	@Override
	public void append(String suggestion) {
		setDirty(true);
		String value = (String) translated.getValue();
		if (value == null)
			translated.setValue(suggestion);
		else {
			if (value.endsWith(" ")) //$NON-NLS-1$
				translated.setValue(value + suggestion);
			else
				translated.setValue(value + " " + suggestion); //$NON-NLS-1$
		}

	}

	@Override
	public void replace(String suggestion) {
		setDirty(true);
		translated.setValue(suggestion);

	}

}
