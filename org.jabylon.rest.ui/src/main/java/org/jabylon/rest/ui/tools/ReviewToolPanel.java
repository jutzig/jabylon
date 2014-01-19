/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.rest.ui.tools;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.apache.wicket.Session;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.eclipse.emf.cdo.common.id.CDOID;
import org.eclipse.emf.cdo.util.CommitException;
import org.eclipse.emf.common.util.EList;
import org.jabylon.cdo.connector.Modification;
import org.jabylon.cdo.connector.TransactionUtil;
import org.jabylon.common.resolver.URIResolver;
import org.jabylon.properties.Project;
import org.jabylon.properties.PropertyFileDescriptor;
import org.jabylon.properties.Review;
import org.jabylon.properties.ReviewState;
import org.jabylon.properties.Severity;
import org.jabylon.rest.ui.model.EObjectModel;
import org.jabylon.rest.ui.model.PropertyPair;
import org.jabylon.rest.ui.security.CDOAuthenticatedSession;
import org.jabylon.rest.ui.wicket.BasicPanel;
import org.jabylon.security.CommonPermissions;
import org.jabylon.users.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReviewToolPanel extends BasicPanel<PropertyPair> {

	private static final long serialVersionUID = 1L;
	private static final Logger LOG = LoggerFactory.getLogger(ReviewToolPanel.class);
	
	/**
     * copies user suggestions to the translation area
     */
    private static final String JS = 
    "$(\"#review-tool-table i.icon-share\").click(function () { " +
    	"var translation = $(this).prev(\"span\");" +
    	"var widget = $(\"#translation\");" +
    	"if(widget.attr(\"readonly\")!=='readonly') {" +
    	"widget.val(translation.text());" +
    	"markDirty();};" +
    "});";

	@Inject
	private transient URIResolver resolver;

	public ReviewToolPanel(String id, IModel<PropertyPair> model) {
		super(id, model);

	}
	
    @Override
    public void renderHead(IHeaderResponse response) {
    	super.renderHead(response);
    	response.render(OnDomReadyHeaderItem.forScript(JS));
    }
	
	@Override
	protected void onBeforeRenderPanel() {
		super.onBeforeRenderPanel();
		setOutputMarkupId(true);
		PropertyFileDescriptor descriptor = getDescriptor(getModel());
		List<IModel<Review>> matchingReviews = computeMatchingReviews(descriptor);

		final boolean hasEditPermission = hasEditPermission(descriptor);
		final WebMarkupContainer container = new WebMarkupContainer("container");
		container.setOutputMarkupId(true);
		final ListView<IModel<Review>> view = new ListView<IModel<Review>>("reviews",matchingReviews) {

			
			private static final long serialVersionUID = 1L;
			
			@Override
			protected void populateItem(ListItem<IModel<Review>> item) {
				Review review = item.getModelObject().getObject();
				Label label = new Label("status", review.getReviewType());
				label.add(new AttributeAppender("class", getLabelClass(review)));
				item.add(label);
				item.add(new Label("message", review.getMessage()));
				WebMarkupContainer copy = new WebMarkupContainer("copy");
				copy.setVisibilityAllowed(hasEditPermission && Review.KIND_SUGGESTION.equals(review.getReviewType()));
				item.add(copy);
				if (Review.KIND_SUGGESTION.equals(review.getReviewType())) {
					String comment = "";
					if (!review.getComments().isEmpty()) {
						comment = ": " + review.getComments().get(0).getMessage();
					}
					item.add(new Label("notes", nls("user.review.notes", review.getUser(), comment)));
				} else
					item.add(new Label("notes", ""));
				final IModel<Review> reviewModel = new EObjectModel<Review>(review);
				// TODO: hide if no permissions
				
				AjaxFallbackLink<String> rejectButton = new AjaxFallbackLink<String>("reject", nls("reject.action")) {

					private static final long serialVersionUID = 1L;

					@Override
					public void onClick(AjaxRequestTarget target) {
						Review theReview = reviewModel.getObject();
						target.add(ReviewToolPanel.this);
						try {
							TransactionUtil.commit(theReview, new Modification<Review, Review>() {
								@Override
								public Review apply(Review object) {
									object.setState(ReviewState.INVALID);
									return object;
								}
							});
						} catch (CommitException e) {
							LOG.error("Failed to commit updated review state",e);
						}		
					}
				};
				DateFormat formatter = SimpleDateFormat.getDateTimeInstance(SimpleDateFormat.SHORT, SimpleDateFormat.SHORT, getSession().getLocale());
				item.add(rejectButton);
				String created = review.getCreated() > 0 ? formatter.format(new Date(review.getCreated())) : "";
				item.add(new Label("created", created));				
				rejectButton.setVisibilityAllowed(hasEditPermission);
			}
		};
		
		
		container.add(view);
		addOrReplace(container);

	}

	protected boolean hasEditPermission(PropertyFileDescriptor descriptor) {
		Session session = getSession();
		if (session instanceof CDOAuthenticatedSession) {
			CDOAuthenticatedSession authSession = (CDOAuthenticatedSession) session;
			User user = authSession.getUser();
			if(user!=null) {
				Project project = descriptor.getProjectLocale().getParent().getParent();
				String permission = CommonPermissions.constructPermissionName(CommonPermissions.PROJECT, project.getName(), CommonPermissions.ACTION_EDIT);
				return user.hasPermission(permission);
			}
			
		}
		return false;
	}

	private List<IModel<Review>> computeMatchingReviews(PropertyFileDescriptor descriptor) {
		 ArrayList<IModel<Review>> matchingReviews = new ArrayList<IModel<Review>>();
			
			if (descriptor != null) {
				EList<Review> reviews = descriptor.getReviews();
				for (Review review : reviews) {
					if (review.getKey().equals(getModel().getObject().getKey()) 
							&& (review.getState()==ReviewState.OPEN) || (review.getState()==ReviewState.REOPENED))
						matchingReviews.add(new EObjectModel<Review>(review));
				}
			}
		return matchingReviews;
	}

	protected String getLabelClass(Review review) {

		Severity severity = review.getSeverity();
		switch (severity) {
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

	private PropertyFileDescriptor getDescriptor(IModel<PropertyPair> pair) {
		if (getModel().getObject() != null) {
			CDOID descriptorID = getModel().getObject().getDescriptorID();
			Object object = resolver.resolve(descriptorID);
			if (object instanceof PropertyFileDescriptor) {
				PropertyFileDescriptor descriptor = (PropertyFileDescriptor) object;
				return descriptor;
			}
		}
		return null;
	}
}
