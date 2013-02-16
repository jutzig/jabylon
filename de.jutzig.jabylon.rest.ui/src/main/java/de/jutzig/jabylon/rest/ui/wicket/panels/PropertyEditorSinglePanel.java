package de.jutzig.jabylon.rest.ui.wicket.panels;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IFormSubmitter;
import org.apache.wicket.markup.html.form.StatelessForm;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.http.flow.AbortWithHttpErrorCodeException;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.eclipse.emf.common.util.EList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import de.jutzig.jabylon.properties.PropertiesFactory;
import de.jutzig.jabylon.properties.Property;
import de.jutzig.jabylon.properties.PropertyFile;
import de.jutzig.jabylon.properties.PropertyFileDescriptor;
import de.jutzig.jabylon.properties.Review;
import de.jutzig.jabylon.resources.persistence.PropertyPersistenceService;
import de.jutzig.jabylon.rest.ui.model.PropertyPair;
import de.jutzig.jabylon.rest.ui.wicket.BasicResolvablePanel;

public class PropertyEditorSinglePanel extends BasicResolvablePanel<PropertyFileDescriptor> {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(PropertyEditorSinglePanel.class);
	IModel<Multimap<String, Review>> reviewModel;
	IModel<PropertyPair> previousModel;
	IModel<PropertyPair> mainModel;
	IModel<PropertyPair> nextModel;

	@Inject
	private transient PropertyPersistenceService propertyPersistence;

	public PropertyEditorSinglePanel(PropertyFileDescriptor object, PageParameters parameters) {
		super("content", object, parameters);

		PropertyListMode mode = PropertyListMode.getByName(parameters.get("mode").toString("ALL"));
		String targetKey = parameters.get("key").toString(null);
		addLinkList(mode);

		reviewModel = new LoadableDetachableModel<Multimap<String, Review>>() {

			private static final long serialVersionUID = 1L;

			@Override
			protected Multimap<String, Review> load() {
				return buildReviewMap(getModelObject());
			}
		};
		createModels(getModel(), targetKey, mode);

	}

	private void createModels(IModel<PropertyFileDescriptor> model, String targetKey, PropertyListMode mode) {

		PropertyFileDescriptor descriptor = model.getObject();
		Multimap<String, Review> reviews = reviewModel.getObject();
		PropertyFileDescriptor master = descriptor.getMaster();
		Map<String, Property> translated = descriptor.loadProperties().asMap();
		PropertyFile templateFile = master.loadProperties();

		PropertyPair previous = null;
		PropertyPair main = null;
		PropertyPair next = null;

		for (Property property : templateFile.getProperties()) {
			Property translation = translated.remove(property.getKey());
			if (translation == null)
				translation = PropertiesFactory.eINSTANCE.createProperty();
			translation.setKey(property.getKey());
			PropertyPair pair = new PropertyPair(property, translation, descriptor.getVariant(), descriptor.cdoID());
			String key = pair.getKey();
			if (mode.apply(pair, reviews.get(key))) {
				if (main != null) {
					// we already found a hit, this is to compute the next
					next = pair;
					break;
				} else if (targetKey == null || (key != null && key.equals(targetKey))) {
					// this is the one we need to edit
					main = pair;
				} else
					// remember the last one
					previous = pair;
			}

		}
		// we didn't find a next yet, so keep searching in the ones missing in
		// the template
		if (next == null) {
			for (Property property : translated.values()) {
				PropertyPair pair = new PropertyPair(null, property, descriptor.getVariant(), descriptor.cdoID());
				if (mode.apply(pair, reviews.get(pair.getKey()))) {
					if (main != null) {
						// we already found a hit, this is to compute the next
						next = pair;
						break;
					} else if (targetKey == null || (pair.getKey() != null && pair.getKey().equals(targetKey))) {
						// this is the one we need to edit
						main = pair;
					} else
						// remember the last one
						previous = pair;
				}
			}
		}
		if (previous != null)
			previousModel = Model.of(previous);
		if (main != null)
			mainModel = Model.of(main);
		else {
			String message = "Property with key {0} was not found in mode {1}";
			message = MessageFormat.format(message, targetKey, mode);
			logger.info(message);
			throw new AbortWithHttpErrorCodeException(HttpServletResponse.SC_NOT_FOUND, message);
		}
		if (next != null)
			nextModel = Model.of(next);
		buildComponentTree(previous, main, next);
	}

	private void buildComponentTree(PropertyPair previous, PropertyPair main, PropertyPair next) {

		Form<Property> pairForm = new StatelessForm<Property>("properties-form", Model.of(main.getTranslation())) {

			private static final long serialVersionUID = 1L;

			protected void onSubmit() {

				PropertyFileDescriptor descriptor = PropertyEditorSinglePanel.this.getModelObject();
				PropertyFile file = descriptor.loadProperties();
				Map<String, Property> map = file.asMap();
				boolean hasChanged = false;
				Property translation = getModelObject();
				if (translation != null) {
					if (map.containsKey(translation.getKey())) {
						Property property = map.get(translation.getKey());
						hasChanged = hasChanged(translation, property);
						property.setComment(translation.getComment());
						property.setValue(translation.getValue());
					} else if (!isEmpty(translation.getValue())) {
						hasChanged = true;
						file.getProperties().add(translation);
					}
				}
				if (hasChanged) {
					propertyPersistence.saveProperties(descriptor, file);
					getSession().info("Saved successfully");
				}
				IFormSubmitter submitter = findSubmittingButton();
				if (submitter instanceof Button) {
					Button button = (Button) submitter;
					if (button.getId().equals("next")) {
						if (nextModel != null && nextModel.getObject() != null)
							setResponsePage(getPage().getClass(), getPageParameters().set("key", nextModel.getObject().getKey()));
					} else {
						if (previousModel != null && previousModel.getObject() != null)
							setResponsePage(getPage().getClass(), getPageParameters().set("key", previousModel.getObject().getKey()));
					}
				}
			}

			private boolean isEmpty(String string) {
				return string == null || string.isEmpty();
			}

		};
		add(pairForm);
		Button nextButton = new Button("next");
		Button previousButton = new Button("previous");

		Property template = main.getTemplate();
		String key = null;
		if (template != null)
			key = main.getTemplate().getKey();
		else
			key = main.getTranslation().getKey();

		final Label icon = new Label("icon");

		icon.setOutputMarkupId(true);
		pairForm.add(icon);

		final WebMarkupContainer templatePanel = new WebMarkupContainer("template-area");
		templatePanel.setOutputMarkupId(true);
		pairForm.add(templatePanel);
		final WebMarkupContainer translationPanel = new WebMarkupContainer("translation-area");
		translationPanel.setOutputMarkupId(true);
		pairForm.add(translationPanel);

		final Label translationLabel = new Label("translation-label", new PropertyModel<PropertyPair>(main, "translated"));

		pairForm.add(new Label("key-label", key));
		pairForm.add(translationLabel);

		TextArea<PropertyPair> textArea = new TextArea<PropertyPair>("template", new PropertyModel<PropertyPair>(main, "original"));
		templatePanel.add(textArea);

		textArea = new TextArea<PropertyPair>("template-comment", new PropertyModel<PropertyPair>(main, "originalComment"));
		templatePanel.add(textArea);

		textArea = new TextArea<PropertyPair>("translation-comment", new PropertyModel<PropertyPair>(main, "translatedComment"));
		translationPanel.add(textArea);

		textArea = new TextArea<PropertyPair>("translation", new PropertyModel<PropertyPair>(main, "translated"));
		translationPanel.add(textArea);

		previousButton.setEnabled(previous != null);
		translationPanel.add(nextButton);
		templatePanel.add(previousButton);

		PropertiesTools tools = new PropertiesTools("tools", mainModel, new PageParameters());
		add(tools);
	}

	private boolean hasChanged(Property prop1, Property prop2) {
		if (prop1 == null)
			return !(prop2 == null || prop2.getValue().isEmpty());
		if (!safeEquals(prop1.getValue(), prop2.getValue()))
			return true;
		if (!safeEquals(prop1.getComment(), prop2.getComment()))
			return true;
		if (!safeEquals(prop1.getKey(), prop2.getKey()))
			return true;
		return false;
	}

	private boolean safeEquals(String s1, String s2) {
		if (s1 == null)
			return s2 == null || s2.isEmpty();
		return s1.equals(s2);
	}

	private Multimap<String, Review> buildReviewMap(PropertyFileDescriptor object) {
		EList<Review> reviews = object.getReviews();
		Multimap<String, Review> reviewMap = ArrayListMultimap.create(reviews.size(), 2);
		for (Review review : reviews) {
			reviewMap.put(review.getKey(), review);
		}
		return reviewMap;
	}

	private void addLinkList(final PropertyListMode currentMode) {
		List<PropertyListMode> values = Arrays.asList(PropertyListMode.values());
		ListView<PropertyListMode> mode = new ListView<PropertyListMode>("view-mode", values) {

			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(ListItem<PropertyListMode> item) {

				String mode = item.getModelObject().name().toLowerCase();
				BookmarkablePageLink<Object> link = new BookmarkablePageLink<Object>("link", getPage().getClass(), new PageParameters(getPageParameters()).clearNamed().set("mode", mode));
				link.setBody(Model.of("Show " + mode));
				item.add(link);
				link.add(new AttributeModifier("onclick", "return confirmAction()"));
				if (item.getModelObject() == currentMode)
					item.add(new AttributeModifier("class", "active"));
			}
		};
		add(mode);

	}

}
