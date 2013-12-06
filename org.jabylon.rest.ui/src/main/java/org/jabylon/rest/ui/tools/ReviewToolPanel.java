package org.jabylon.rest.ui.tools;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.IModel;
import org.eclipse.emf.cdo.common.id.CDOID;
import org.eclipse.emf.common.util.EList;

import org.jabylon.common.resolver.URIResolver;
import org.jabylon.index.properties.QueryService;
import org.jabylon.properties.PropertyFileDescriptor;
import org.jabylon.properties.Review;
import org.jabylon.properties.Severity;
import org.jabylon.rest.ui.model.PropertyPair;

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
        
        DateFormat formatter = SimpleDateFormat.getDateTimeInstance(SimpleDateFormat.SHORT, SimpleDateFormat.SHORT,getSession().getLocale());

        RepeatingView view = new RepeatingView("reviews");
        for (Review review : matchingReviews) {
            WebMarkupContainer c = new WebMarkupContainer(view.newChildId());
            Label label = new Label("status", review.getReviewType());
            label.add(new AttributeAppender("class", getLabelClass(review)));
            c.add(label);
            c.add(new Label("message", review.getMessage()));
            c.add(new Label("description", review.getReviewType()));
            String created = review.getCreated() > 0 ? formatter.format(new Date(review.getCreated())) : "";
            c.add(new Label("created", created));
            view.add(c);
        }
        addOrReplace(view);
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
