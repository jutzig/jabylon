package de.jutzig.jabylon.rest.ui.tools.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.IModel;
import org.eclipse.emf.cdo.common.id.CDOID;
import org.eclipse.emf.common.util.EList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.jutzig.jabylon.common.resolver.URIResolver;
import de.jutzig.jabylon.index.properties.QueryService;
import de.jutzig.jabylon.properties.PropertyFileDescriptor;
import de.jutzig.jabylon.properties.Review;
import de.jutzig.jabylon.properties.Severity;
import de.jutzig.jabylon.rest.ui.model.PropertyPair;

public class ReviewToolPanel extends GenericPanel<PropertyPair> {

	private static final long serialVersionUID = 1L;

	@Inject
	private transient QueryService queryService;
	@Inject
	private transient URIResolver resolver;

	public ReviewToolPanel(String id, IModel<PropertyPair> model) {
		super(id, model);

	}

	@Override
	protected void onBeforeRender() {

		super.onBeforeRender();
		IModel<PropertyPair> model = getModel();
		List<Review> matchingReviews = new ArrayList<Review>();
		if (model.getObject() != null) {
			CDOID descriptorID = getModel().getObject().getDescriptorID();
			Object object = resolver.resolve(descriptorID);
			if (object instanceof PropertyFileDescriptor) {
				PropertyFileDescriptor descriptor = (PropertyFileDescriptor) object;
				EList<Review> reviews = descriptor.getReviews();
				for (Review review : reviews) {
					if (review.getKey().equals(getModel().getObject().getKey()))
						matchingReviews.add(review);
				}

			}
		}
		ListView<Review> listView = new ListView<Review>("reviews", matchingReviews) {

			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(ListItem<Review> item) {
				Review review = item.getModelObject();
				Label label = new Label("status", review.getReviewType());
				label.add(new AttributeAppender("class", getLabelClass(review)));
				item.add(label);
				item.add(new Label("message", review.getMessage()));
				item.add(new Label("description", review.getReviewType()));
			}
		};
		addOrReplace(listView);
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
}
