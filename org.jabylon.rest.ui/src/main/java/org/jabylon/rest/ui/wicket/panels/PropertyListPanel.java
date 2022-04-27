/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.rest.ui.wicket.panels;


import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.Session;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.IFilterStateLocator;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.StatelessForm;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.markup.html.link.StatelessLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.http.flow.AbortWithHttpErrorCodeException;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.cdo.util.CommitException;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.jabylon.cdo.connector.Modification;
import org.jabylon.cdo.connector.TransactionUtil;
import org.jabylon.common.resolver.URIResolver;
import org.jabylon.common.review.ReviewParticipant;
import org.jabylon.common.util.URLUtil;
import org.jabylon.properties.Comment;
import org.jabylon.properties.Project;
import org.jabylon.properties.ProjectLocale;
import org.jabylon.properties.ProjectVersion;
import org.jabylon.properties.PropertiesFactory;
import org.jabylon.properties.Property;
import org.jabylon.properties.PropertyFile;
import org.jabylon.properties.PropertyFileDescriptor;
import org.jabylon.properties.Review;
import org.jabylon.properties.ReviewState;
import org.jabylon.properties.Severity;
import org.jabylon.properties.util.PropertyResourceUtil;
import org.jabylon.resources.persistence.PropertyPersistenceService;
import org.jabylon.rest.ui.Activator;
import org.jabylon.rest.ui.model.CustomStringResourceModel;
import org.jabylon.rest.ui.model.EClassSortState;
import org.jabylon.rest.ui.model.EObjectModel;
import org.jabylon.rest.ui.model.PropertyPair;
import org.jabylon.rest.ui.security.CDOAuthenticatedSession;
import org.jabylon.rest.ui.security.RestrictedComponent;
import org.jabylon.rest.ui.util.WicketUtil;
import org.jabylon.rest.ui.wicket.BasicResolvablePanel;
import org.jabylon.rest.ui.wicket.components.ConfirmBehaviour;
import org.jabylon.rest.ui.wicket.pages.ResourcePage;
import org.jabylon.security.CommonPermissions;
import org.jabylon.users.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;


public class PropertyListPanel
    extends BasicResolvablePanel<PropertyFileDescriptor> implements RestrictedComponent
{

    private static final long serialVersionUID = 1L;
    IModel<Multimap<String, Review>> reviewModel;
    static final String OK_LABEL = "OK";

    private static final Logger logger = LoggerFactory.getLogger(PropertyPairListDataProvider.class);

    @Inject
	List<ReviewParticipant> reviewParticipants;

	@Inject
    private PropertyPersistenceService propertyPersistence;
	private PropertyListMode mode;

    public PropertyListPanel(PropertyFileDescriptor object, PageParameters parameters)
    {
        super("content", object, parameters);
    }


    @Override
    protected void construct() {
    	super.construct();

    	List<ReviewParticipant> activeReviews = PropertyListModeFactory.filterActiveReviews(getModel().getObject().getProjectLocale().getParent().getParent(),reviewParticipants);
		mode = PropertyListModeFactory.allAsMap(activeReviews).get(getPageParameters().get("mode").toString());
        addLinkList(activeReviews,mode);
        reviewModel = new LoadableDetachableModel<Multimap<String, Review>>()
        {

            private static final long serialVersionUID = 1L;


            @Override
            protected Multimap<String, Review> load()
            {
                return buildReviewMap(getModelObject());
            }
        };
        PropertyPairListDataProvider provider = new PropertyPairListDataProvider(getModelObject(), mode, reviewModel);
        List<PropertyPair> contents = provider.createContents();

        ListView<PropertyPair> properties = new ListView<PropertyPair>("repeater", contents)
        {

            private static final long serialVersionUID = -7087485011138279358L;


            @Override
            protected void populateItem(final ListItem<PropertyPair> item)
            {
                IModel<PropertyPair> model = item.getModel();
                String key = model.getObject().getKey();
                Collection<Review> reviewList = reviewModel.getObject().get(key);
                BookmarkablePageLink<Object> link = new BookmarkablePageLink<Object>("edit",getPage().getClass(),new PageParameters(getPageParameters()).add("key", key));
                item.add(link);
                link.setMarkupId(URLUtil.escapeToIdAttribute(key));
                link.setOutputMarkupId(true);
                link.add(new AttributeModifier("name", link.getMarkupId()));
                Label keyLabel = new Label("key", key);
                keyLabel.add(new AttributeModifier("title", model.getObject().getTranslated()));
                item.add(keyLabel);

                Label translationLabel = new Label("translation", model.getObject().getTranslated());
                translationLabel.add(new AttributeModifier("title", model.getObject().getTranslatedComment()));
                item.add(translationLabel);
                fillStatusColumn(model.getObject(), reviewList, item);
            }

        };
        properties.setOutputMarkupId(true);
        add(properties);

        String contextPath = WicketUtil.getContextPath();
        String href = contextPath + "/api/"+ getModelObject().toURI().appendQuery("type=file");
        ExternalLink link = new ExternalLink("download.link", href);
        File file = new File(getModelObject().absoluteFilePath().toFileString());
        boolean enabled = file.isFile();
        link.setEnabled(enabled);
        if(!enabled)
        {
            link.add(new AttributeAppender("disabled", Model.of("disabled")));
            link.add(new AttributeAppender("class", Model.of("disabled")));
        }
        add(link);

        StatelessLink<Void> deleteLink = new DeleteLink("remove.button");
		deleteLink.add(new ConfirmBehaviour(nls("confirm.remove")));
		deleteLink.setVisible(canConfigure());
		add(deleteLink);

		FileUploadForm uploadForm = new FileUploadForm("translation-upload-form", getModel());
		uploadForm.setVisible(canSuggest());
		add(uploadForm);
    }

	@Override
	public String getRequiredPermission() {
		Project project = getModel().getObject().getProjectLocale().getParent().getParent();
		return CommonPermissions.constructPermission(CommonPermissions.PROJECT, project.getName(), CommonPermissions.ACTION_VIEW);
	}

    protected void fillStatusColumn(PropertyPair propertyPair,
                                    Collection<Review> reviewCollection,
                                    MarkupContainer container)
    {
        IStatus status = calculateRowStatus(propertyPair);
        if (status.getSeverity() == IStatus.WARNING)
            container.add(new AttributeModifier("class", "warning"));
        else if (status.getSeverity() == IStatus.ERROR)
            container.add(new AttributeModifier("class", "error"));

        Collection<Review> reviews = reviewCollection;
        if (reviews == null || reviews.isEmpty())
            reviews = createInMemoryReview(propertyPair);

        RepeatingView view = new RepeatingView("reviews");
        DateFormat formatter = SimpleDateFormat.getDateTimeInstance(SimpleDateFormat.SHORT, SimpleDateFormat.SHORT,getSession().getLocale());
        for (Review review : reviews)
        {
        	if(review.getState()==ReviewState.INVALID || review.getState()==ReviewState.RESOLVED)
				continue;
            Label label = new Label(view.newChildId(), review.getReviewType());
            label.add(new AttributeAppender("class", getLabelClass(review)));
            StringBuilder title = new StringBuilder();
            if (review.getMessage() != null)
            	title.append(review.getMessage());
            if(review.getCreated()>0)
            {
            	if(title.length()>0)
            		//add a linebreak
            		title.append("\n");
            	title.append(formatter.format(new Date(review.getCreated())));
            }
            if(title.length()>0)
            	label.add(new AttributeModifier("title", title.toString()));
            view.add(label);
        }

        container.add(view);

    }


    private IStatus calculateRowStatus(PropertyPair propertyPair)
    {

        if (propertyPair.getOriginal() == null || propertyPair.getOriginal().isEmpty())
            return new Status(IStatus.ERROR, "org.jabylon.rest.ui", "");
        else if (propertyPair.getTranslated() == null || propertyPair.getTranslated().isEmpty())
            return new Status(IStatus.ERROR, "org.jabylon.rest.ui", "");
        return Status.OK_STATUS;
    }


    private List<Review> createInMemoryReview(PropertyPair pair)
    {
        if (pair.getTranslated() == null || pair.getTranslated().isEmpty())
        {
            Review review = PropertiesFactory.eINSTANCE.createReview();
            String message = new CustomStringResourceModel("review.missing.translation",this,null,pair.getKey()).getString();
            review.setMessage(MessageFormat.format(message, pair.getKey()));
            review.setReviewType(getString("review.missing.translation.type"));
            review.setSeverity(Severity.ERROR);
            return Collections.singletonList(review);
        }
        Review review = PropertiesFactory.eINSTANCE.createReview();
        review.setReviewType("OK");
        return Collections.singletonList(review);
    }


    protected String getLabelClass(Review review)
    {
        if (OK_LABEL.equals(review.getReviewType()))
            return " label-success";

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

    /**
     * checks if the user has permissions to configure this project
     * @return
     */
	private boolean canConfigure() {
		return hasPermission(getModel().getObject(), getSession(), CommonPermissions.ACTION_CONFIG);
	}

	/**
     * checks if the user has permissions to make suggestions to this project
     * @return
     */
	private boolean canSuggest() {
		return hasPermission(getModel().getObject(), getSession(), CommonPermissions.ACTION_SUGGEST);
	}

	private static boolean hasPermission(PropertyFileDescriptor model, Session session, String permission) {
		ProjectVersion version = model.getProjectLocale().getParent();
		if (version.isReadOnly())
			return false;
		if (session instanceof CDOAuthenticatedSession) {
			Project project = version.getParent();
			CDOAuthenticatedSession authSession = (CDOAuthenticatedSession) session;
			return authSession.hasPermission(CommonPermissions.constructPermission(CommonPermissions.PROJECT, project.getName(), permission));
		}
		return false;
	}


    private Multimap<String, Review> buildReviewMap(PropertyFileDescriptor object)
    {
        EList<Review> reviews = object.getReviews();
        Multimap<String, Review> reviewMap = ArrayListMultimap.create(reviews.size(), 2);
        for (Review review : reviews)
        {
        	if(review.getState()==ReviewState.OPEN || review.getState()==ReviewState.REOPENED)
        		reviewMap.put(review.getKey(), review);
        }
        return reviewMap;
    }


    private void addLinkList(List<ReviewParticipant> activeReviews, final PropertyListMode currentMode)
    {
		List<PropertyListMode> values = PropertyListModeFactory.all(activeReviews);
        ListView<PropertyListMode> mode = new ListView<PropertyListMode>("view-mode", values)
        {

            private static final long serialVersionUID = 1L;


            @Override
            protected void populateItem(ListItem<PropertyListMode> item)
            {
				String mode = item.getModelObject().name();
                BookmarkablePageLink<Object> link = new BookmarkablePageLink<Object>("link", getPage().getClass(), new PageParameters(getPageParameters()).set("mode", mode));
                ReviewParticipant participant = item.getModel().getObject().getParticipant();
                if(participant!=null)
                	link.setBody(nls(participant.getClass(),participant.getName()));
                else
                	link.setBody(new StringResourceModel(item.getModelObject().name(),item,null));
                item.add(link);
                if (item.getModelObject().equals(currentMode))
                    item.add(new AttributeModifier("class", "active"));
            }
        };
        add(mode);

    }


    private static class FileUploadForm extends StatelessForm<PropertyFileDescriptor>
    {

		private static final long serialVersionUID = -3653084217384164795L;
		private static final Logger LOG = LoggerFactory.getLogger(FileUploadForm.class);

		private final ArrayList<FileUpload> uploads;
		private FileUploadField fileUploadField;

		public FileUploadForm(String id, IModel<PropertyFileDescriptor> model) {
			super(id,model);
	           // set this form to multipart mode (always needed for uploads!)
            setMultiPart(true);
            uploads = new ArrayList<FileUpload>();

            // Add one multi-file upload field
            @SuppressWarnings({ "unchecked", "rawtypes" })
//            IModel<List<FileUpload>> fileModel = new Model<ArrayList<FileUpload>>(uploads);
			IModel<List<FileUpload>> fileModel = new Model(uploads);
            fileUploadField = new FileUploadField("upload",fileModel);
            add(fileUploadField);

            // Set maximum size to 100K for demo purposes
//            setMaxSize(Bytes.kilobytes(100));
		}

		@Override
		protected void onSubmit() {
			super.onSubmit();

			for (FileUpload fileUpload : fileUploadField.getFileUploads()) {
				PropertyFileDescriptor descriptor = getModel().getObject();
	            try {
					PropertyFile file = descriptor.loadProperties(fileUpload.getInputStream());
					persist(file);

				} catch (IOException e) {
					LOG.error("Failed to retrieve uploaded file for"+descriptor.getLocation(),e);
				}
			}
		}

		private void persist(PropertyFile file) {
			PropertyPersistenceService persistenceService = Activator.getDefault().getPersistenceService();
			try {
				PropertyFile original = persistenceService.loadProperties(getModelObject());
				PropertyFile master = persistenceService.loadProperties(getModelObject().getMaster());
				Map<String, Property> originalProperties = original.asMap();

				for(Property prop : master.getProperties())
				{
					if(!originalProperties.containsKey(prop.getKey()))
					{
						Property newProperty = PropertiesFactory.eINSTANCE.createProperty();
						newProperty.setKey(prop.getKey());
						original.getProperties().add(newProperty);
					}
				}
				originalProperties = original.asMap();
				EList<Property> newProperties = file.getProperties();
				if(hasPermission(getModelObject(), getSession(), CommonPermissions.ACTION_EDIT))
				{
					boolean hadChanges = false;
					for (Property newProp : newProperties) {
						Property origProp = originalProperties.get(newProp.getKey());
						if(origProp==null)
							continue;
						if(newProp.getValue()!=null && !newProp.getValue().isEmpty() && !newProp.getValue().equals(origProp.getValue()))
						{
							hadChanges = true;
							origProp.setValue(newProp.getValue());
							origProp.setComment(newProp.getComment());
						}
					}
					if(hadChanges)
						persistenceService.saveProperties(getModelObject(), original);
				}
				else if(hasPermission(getModelObject(), getSession(), CommonPermissions.ACTION_SUGGEST)) {
					final List<Review> reviews = new ArrayList<Review>();
					for (Property newProp : newProperties) {
						Property origProp = originalProperties.get(newProp.getKey());
						if(origProp==null)
							continue;
						if(newProp.getValue()!=null && !newProp.getValue().isEmpty() && !newProp.getValue().equals(origProp.getValue()))
						{

							final Review review = PropertiesFactory.eINSTANCE.createReview();
							review.setCreated(System.currentTimeMillis());
							review.setKey(newProp.getKey());
							review.setMessage(newProp.getValue());
							review.setSeverity(Severity.INFO);
							review.setReviewType(Review.KIND_SUGGESTION);

							String username = CommonPermissions.USER_ANONYMOUS;
							Session session = getSession();
							if (session instanceof CDOAuthenticatedSession) {
								CDOAuthenticatedSession authSession = (CDOAuthenticatedSession) session;
								User user = authSession.getUser();
								if (user != null)
									username = user.getName();

							}
							review.setUser(username);

							if (newProp.getComment() != null && !newProp.getComment().isEmpty()) {
								Comment comment = PropertiesFactory.eINSTANCE.createComment();
								comment.setUser(username);
								comment.setCreated(review.getCreated());
								comment.setMessage(newProp.getComment());
								review.getComments().add(comment);
							}
							reviews.add(review);
						}
					}
					if (!reviews.isEmpty()) {

						TransactionUtil.commit(getModelObject(), new Modification<PropertyFileDescriptor, PropertyFileDescriptor>() {
							@Override
							public PropertyFileDescriptor apply(PropertyFileDescriptor object) {

								object.getReviews().addAll(reviews);
								return object;
							}

						});

					}
				}
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {

				}
				getSession().success(new StringResourceModel("message.upload.successfull", this, null).getString());
				setResponsePage(ResourcePage.class, getPage().getPageParameters());
			} catch (ExecutionException e) {
				LOG.error("Failed to merge uploaded properties with "+getModelObject().getLocation(),e);
				getSession().success(new CustomStringResourceModel("message.upload.failed", this, null,e.getMessage()).getString());
			}
			catch (CommitException e) {
				logger.error("Commit of suggestion failed", e);
				getSession().success(new CustomStringResourceModel("message.upload.failed", this, null,e.getMessage()).getString());
			}

		}

    }


class PropertyPairListDataProvider
    extends SortableDataProvider<PropertyPair, EClassSortState>
    implements IFilterStateLocator<String>
{

    private static final long serialVersionUID = 1L;
    private CompoundPropertyModel<PropertyFileDescriptor> model;
    private transient List<PropertyPair> contents;
    private String filterState;
    private PropertyListMode mode;
    private IModel<Multimap<String, Review>> reviewModel;

    public PropertyPairListDataProvider(PropertyFileDescriptor descriptor,
                                        PropertyListMode mode,
                                        IModel<Multimap<String, Review>> reviewModel)
    {
        super();
        model = new CompoundPropertyModel<PropertyFileDescriptor>(new EObjectModel<PropertyFileDescriptor>(descriptor));
        this.mode = mode;
        this.reviewModel = reviewModel;
    }


    @Override
    public Iterator< ? extends PropertyPair> iterator(long first, long count)
    {
        List<PropertyPair> contents = getList();
        return contents.subList((int)first, (int)first + (int)count).iterator();
    }


    private List<PropertyPair> getList()
    {
        if (contents == null)
        {
            contents = createContents();
        }
        return contents;
    }


    protected List<PropertyPair> createContents()
    {
        PropertyFileDescriptor descriptor = model.getObject();
        Multimap<String, Review> reviews = reviewModel.getObject();
        PropertyFileDescriptor master = descriptor.isMaster() ? descriptor : descriptor.getMaster() ;
        Map<String, Property> translated = new HashMap<String, Property>(loadProperties(descriptor).asMap());
        PropertyFile templateFile = loadProperties(master);

        List<PropertyPair> contents = new ArrayList<PropertyPair>();
        for (Property property : templateFile.getProperties())
        {
            // IModel<String> bind = model.bind(property.getKey());
            // bind.set
            PropertyPair pair = new PropertyPair(property,
                                                 translated.remove(property.getKey()),
                                                 descriptor.getVariant(),
                                                 descriptor.cdoID());
            String key = pair.getKey();
            if (mode.apply(pair, reviews.get(key)))
                contents.add(pair);
        }
        for (Property property : translated.values())
        {
            PropertyPair pair = new PropertyPair(null, property, descriptor.getVariant(), descriptor.cdoID());
            if (mode.apply(pair, reviews.get(pair.getKey())))
                contents.add(pair);
        }
        return contents;
    }


    private PropertyFile loadProperties(PropertyFileDescriptor descriptor) {
        try {
            return propertyPersistence.loadProperties(descriptor);
        } catch (ExecutionException e) {
            logger.error("Failed to load properties for "+descriptor);
            throw new AbortWithHttpErrorCodeException(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,"Failed to load properties for "+descriptor);
        }
    }


    @Override
    public long size()
    {
        return getList().size();
    }


    @Override
    public IModel<PropertyPair> model(PropertyPair object)
    {
        return Model.of(object);
    }


    @Override
    public void setFilterState(String state)
    {
        this.filterState = state;

    }


    @Override
    public String getFilterState()
    {
        return filterState;
    }

}

}

class DeleteLink extends StatelessLink<Void>{


	private static final long serialVersionUID = 8205155656605708520L;

	@Inject
	private URIResolver resolver;

	private static final Logger LOG = LoggerFactory.getLogger(DeleteLink.class);

    public DeleteLink(String id) {
		super(id);
	}


	@Override
	public void onClick() {
		PropertyFileDescriptor descriptor = getModel(getPage().getPageParameters());
		if(descriptor!=null){
			try {
			ProjectLocale locale = TransactionUtil.commit(descriptor, new Modification<PropertyFileDescriptor, ProjectLocale>() {
				@Override
				public ProjectLocale apply(PropertyFileDescriptor object) {
					ProjectLocale locale = object.getProjectLocale();
					if(!object.isMaster())
						object = object.getMaster();
					if(object!=null)
						PropertyResourceUtil.removeDescriptor(object);
					return locale;
				};
			});
			URI uri = resolver.getURI(locale);
			PageParameters params = new PageParameters();
			for(int i=0;i<uri.segmentCount();i++) {
				params.set(i, uri.segment(i));
			}
			//go to the parent locale
			setResponsePage(ResourcePage.class, params);
		} catch (CommitException e) {
			error(e.getMessage());
			LOG.error("Failed to delete descriptor",e);
		}
		}

	}


	private PropertyFileDescriptor getModel(PageParameters pageParameters) {
		URI uri = URI.createURI("/");
		for(int i=0;i<pageParameters.getIndexedCount();i++){
			uri = uri.appendSegment(pageParameters.get(i).toString());
		}

		return (PropertyFileDescriptor) resolver.resolve(uri);
	}
}


