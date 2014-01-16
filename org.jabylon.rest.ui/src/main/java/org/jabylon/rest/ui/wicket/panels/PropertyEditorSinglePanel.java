/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.rest.ui.wicket.panels;

import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.Session;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IFormSubmitter;
import org.apache.wicket.markup.html.form.StatelessForm;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.http.flow.AbortWithHttpErrorCodeException;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.jabylon.common.util.URLUtil;
import org.jabylon.properties.Project;
import org.jabylon.properties.ProjectVersion;
import org.jabylon.properties.PropertiesFactory;
import org.jabylon.properties.Property;
import org.jabylon.properties.PropertyFile;
import org.jabylon.properties.PropertyFileDescriptor;
import org.jabylon.properties.Review;
import org.jabylon.properties.Severity;
import org.jabylon.resources.persistence.PropertyPersistenceService;
import org.jabylon.rest.ui.model.PropertyPair;
import org.jabylon.rest.ui.security.CDOAuthenticatedSession;
import org.jabylon.rest.ui.security.RestrictedComponent;
import org.jabylon.rest.ui.util.GlobalResources;
import org.jabylon.rest.ui.util.WebContextUrlResourceReference;
import org.jabylon.rest.ui.wicket.BasicResolvablePanel;
import org.jabylon.rest.ui.wicket.components.AnchorBookmarkablePageLink;
import org.jabylon.security.CommonPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;


public class PropertyEditorSinglePanel extends BasicResolvablePanel<PropertyFileDescriptor> implements RestrictedComponent{

    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(PropertyEditorSinglePanel.class);
    IModel<Multimap<String, Review>> reviewModel;
    IModel<PropertyPair> previousModel;
    IModel<PropertyPair> mainModel;
    IModel<PropertyPair> nextModel;
    IModel<Boolean> modified;
    boolean readOnly = false;

    @Inject
    private transient PropertyPersistenceService propertyPersistence;
    private int index, total;
	private PropertyListMode mode;
	private String targetKey;

    public PropertyEditorSinglePanel(PropertyFileDescriptor object, PageParameters parameters) {
        super("content", object, parameters);
        mode = PropertyListMode.getByName(parameters.get("mode").toString("ALL"));
        targetKey = parameters.get("key").toString(null);
        
    }
    
    @Override
    protected void construct() {
    	super.construct();
        readOnly = isReadOnly();
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

    private boolean isReadOnly() {
    	ProjectVersion version = getModel().getObject().getProjectLocale().getParent();
    	if(version.isReadOnly())
    		return true;
    	Session session = getSession();
    	if (session instanceof CDOAuthenticatedSession) {
    		Project project = version.getParent();
			CDOAuthenticatedSession authSession = (CDOAuthenticatedSession) session;
			return !authSession.hasPermission(CommonPermissions.constructPermission(CommonPermissions.PROJECT,project.getName(),CommonPermissions.ACTION_EDIT));
		}
		return true;
	}

	@Override
    public void renderHead(IHeaderResponse response)
    {
        response.render(JavaScriptHeaderItem.forReference(GlobalResources.JS_WARN_WHEN_DIRTY));
        response.render(JavaScriptHeaderItem.forReference(GlobalResources.JS_SHORTCUTS));
        response.render(JavaScriptHeaderItem.forReference(new WebContextUrlResourceReference("js/singlePropertyEditor.js")));
        super.renderHead(response);
    }

    private void createModels(IModel<PropertyFileDescriptor> model, String targetKey, PropertyListMode mode) {

        modified = Model.of(Boolean.valueOf(false));

        PropertyFileDescriptor descriptor = model.getObject();
        Multimap<String, Review> reviews = reviewModel.getObject();
        PropertyFileDescriptor master = descriptor.getMaster();
        Map<String, Property> translated = loadProperties(descriptor).asMap();
        PropertyFile templateFile = loadProperties(master);
        total = templateFile.getProperties().size();

        PropertyPair previous = null;
        PropertyPair main = null;
        PropertyPair next = null;
        index = 1;
        for (Property property : templateFile.getProperties()) {
            Property translation = translated.remove(property.getKey());
            if (translation == null)
                translation = PropertiesFactory.eINSTANCE.createProperty();
            translation.setKey(property.getKey());
            PropertyPair pair = new PropertyPair(EcoreUtil.copy(property), EcoreUtil.copy(translation), descriptor.getVariant(), descriptor.cdoID());
            String key = pair.getKey();
            if (mode.apply(pair, reviews.get(key))) {
                if (main != null) {
                    // we already found a hit, this is to compute the next
                    next = pair;
                    break;
                } else if (targetKey == null || (key != null && key.equals(targetKey))) {
                    // this is the one we need to edit
                    main = pair;
                } else {
                    // remember the last one
                    previous = pair;
                }
            }
            if (main == null)
                index++;

        }
        // we didn't find a next yet, so keep searching in the ones missing in
        // the template
        if (next == null) {
            for (Property property : translated.values()) {
                PropertyPair pair = new PropertyPair(null, EcoreUtil.copy(property), descriptor.getVariant(), descriptor.cdoID());
                if (mode.apply(pair, reviews.get(pair.getKey()))) {
                    if (main != null) {
                        // we already found a hit, this is to compute the next
                        next = pair;
                        break;
                    } else if (targetKey == null || (pair.getKey() != null && pair.getKey().equals(targetKey))) {
                        // this is the one we need to edit
                        main = pair;
                    } else {
                        // remember the last one
                        previous = pair;
                    }
                }
                if(main==null)
                    index++;
            }
        }
        if (previous != null)
            previousModel = Model.of(previous);
        if (main != null)
        {
            mainModel = Model.of(main);
        }
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

    private PropertyFile loadProperties(PropertyFileDescriptor descriptor) {
        try {
            PropertyFile propertyFile = propertyPersistence.loadProperties(descriptor);
            return propertyFile;
        } catch (ExecutionException e) {
            logger.error("Failed to load property file for "+descriptor,e);
            throw new AbortWithHttpErrorCodeException(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to load property file for "+descriptor);
        }
    }

    private void buildComponentTree(PropertyPair previous, final PropertyPair main, PropertyPair next) {

        Form<Property> pairForm = new StatelessForm<Property>("properties-form", Model.of(main.getTranslation())) {

            private static final long serialVersionUID = 1L;

            protected void onSubmit() {
            	IFormSubmitter submitter = findSubmittingButton();
            	if(submitter instanceof Button && ((Button)submitter).getId().equals("reset")) {
                    setResponsePage(getPage().getClass(), getPageParameters().set("key", mainModel.getObject().getKey()));
                    return;
            	}
            	
                if(!readOnly && modified.getObject()) {
                	PropertyFileDescriptor descriptor = PropertyEditorSinglePanel.this.getModelObject();
                	PropertyFile file = loadProperties(descriptor);
                	Map<String, Property> map = file.asMap();
                    Property translation = getModelObject();
                    if (translation != null) {
                        if (map.containsKey(translation.getKey())) {
                            Property property = map.get(translation.getKey());
                            property.setComment(translation.getComment());
                            property.setValue(translation.getValue());
                        } else if (!isEmpty(translation.getValue())) {
                            file.getProperties().add(translation);
                        }
                    }

                    propertyPersistence.saveProperties(descriptor, file);
                    /*
                     * see https://github.com/jutzig/jabylon/issues/112
                     * for now we deactivate the successful message to not bother the user
                     * getSession().info("Saved successfully");
                     */
                }

                if (submitter instanceof Button) {
                    Button button = (Button) submitter;
                    if (button.getId().equals("next"))
                    {
                        if (nextModel != null && nextModel.getObject() != null)
                            setResponsePage(getPage().getClass(), getPageParameters().set("key", nextModel.getObject().getKey()));
                        else
                        {
                        	// there is no next. go to overview
                        	setResponsePage(getPage().getClass(), getPageParameters().set("key", null));
                        }

                    } else if (button.getId().equals("previous")) {
                    	if (previousModel != null && previousModel.getObject() != null)
                    		setResponsePage(getPage().getClass(), getPageParameters().set("key", previousModel.getObject().getKey()));
                    	else
                        {
                        	// there is no next. go to overview
                        	setResponsePage(getPage().getClass(), getPageParameters().set("key", null));
                        }
                    } else {
                        setResponsePage(getPage().getClass(), getPageParameters().set("key", mainModel.getObject().getKey()));
                    }

                }
            }

            private boolean isEmpty(String string) {
                return string == null || string.isEmpty();
            }

        };

        //this will let us know if the user actually changed anything
        CheckBox modifiedIndicator = new CheckBox("modify-indicator", modified);
        modifiedIndicator.setDefaultModelObject(false);
        pairForm.add(modifiedIndicator);

        add(pairForm);

        Button saveButton = new Button("save");
        pairForm.add(saveButton);
        Button resetButton = new Button("reset");
        pairForm.add(resetButton);

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
        textArea.setEnabled(!readOnly);

        textArea = new TextArea<PropertyPair>("template-comment", new PropertyModel<PropertyPair>(main, "originalComment"));
        templatePanel.add(textArea);

        textArea = new TextArea<PropertyPair>("translation-comment", new PropertyModel<PropertyPair>(main, "translatedComment"));
        translationPanel.add(textArea);
        if(readOnly)
        	textArea.add(new AttributeModifier("readonly", "readonly"));

        textArea = new TextArea<PropertyPair>("translation", new PropertyModel<PropertyPair>(main, "translated"));
        textArea.add(new AttributeModifier("lang", new AbstractReadOnlyModel<String>() {
            private static final long serialVersionUID = 1L;

            @Override
            public String getObject() {
                // see http://www.ietf.org/rfc/bcp/bcp47.txt
                return main.getLanguage().toString().replace('_', '-');
            }
        }));
        textArea.add(new AttributeModifier("translate", "no"));
        if(readOnly)
        	textArea.add(new AttributeModifier("readonly", "readonly"));
        translationPanel.add(textArea);

        pairForm.add(nextButton);
        pairForm.add(previousButton);

        String progressLabel = new StringResourceModel("translation.progress", this, null, index, total).getObject();
        progressLabel = MessageFormat.format(progressLabel, index, total);
        Label progress = new Label("progress", String.valueOf(progressLabel));
        double actualIndex = Math.max(1, index);
        int percent = (int) ((actualIndex/(double)total) * 100);
        progress.add(new AttributeModifier("style", "width: " + percent + "%"));
        pairForm.add(progress);

        fillReviewsColumn(mainModel, pairForm);

        PropertiesTools tools = new PropertiesTools("tools", mainModel, new PageParameters());
        add(tools);

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
                String anchor = URLUtil.escapeToIdAttribute(mainModel.getObject().getKey());
                PageParameters pageParams = new PageParameters(getPageParameters()).clearNamed().set("mode", mode);
                BookmarkablePageLink<Object> link = new AnchorBookmarkablePageLink<Object>("link", getPage().getClass(), pageParams, anchor);
                
                link.setBody(new StringResourceModel(item.getModelObject().name(),item,null));
                item.add(link);
                link.add(new AttributeModifier("onclick", "return confirmAction()"));
                if (item.getModelObject() == currentMode)
                    item.add(new AttributeModifier("class", "active"));
            }
        };
        add(mode);

    }

    @Override
    public String getRequiredPermission() {
        Project project = getModel().getObject().getProjectLocale().getParent().getParent();
        return CommonPermissions.constructPermission(CommonPermissions.PROJECT,project.getName(),CommonPermissions.ACTION_VIEW);
    }



	protected void fillReviewsColumn(IModel<PropertyPair> propertyPair, MarkupContainer container)
	{
		RepeatingView view = new RepeatingView("reviews");
		container.add(view);
		if(propertyPair==null || propertyPair.getObject()==null)
			return;
		Collection<Review> reviews = reviewModel.getObject().get(propertyPair.getObject().getKey());
		DateFormat formatter = SimpleDateFormat.getDateTimeInstance(SimpleDateFormat.SHORT, SimpleDateFormat.SHORT, getSession().getLocale());
		for (Review review : reviews) {
			Label label = new Label(view.newChildId(), review.getReviewType());
			label.add(new AttributeAppender("class", getLabelClass(review)));
			StringBuilder title = new StringBuilder();
			if (review.getMessage() != null)
				title.append(review.getMessage());
			if (review.getCreated() > 0) {
				if (title.length() > 0)
					// add a linebreak
					title.append("\n");
				title.append(formatter.format(new Date(review.getCreated())));
			}
			if (title.length() > 0)
				label.add(new AttributeModifier("title", title.toString()));
			view.add(label);
		}



	}



    protected String getLabelClass(Review review)
    {
        Severity severity = review.getSeverity();
        switch (severity)
        {
            case ERROR:
                return " label-important";
            case INFO:
                return " label-info";
            case WARNING:
                return " label-warning";
            default:
                return "";
        }
    }
}
