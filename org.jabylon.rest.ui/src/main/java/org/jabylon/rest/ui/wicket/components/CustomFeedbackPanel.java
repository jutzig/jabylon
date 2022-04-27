/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.rest.ui.wicket.components;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.feedback.FeedbackMessagesModel;
import org.apache.wicket.feedback.IFeedback;
import org.apache.wicket.feedback.IFeedbackMessageFilter;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.basic.MultiLineLabel;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.protocol.http.WebSession;

public class CustomFeedbackPanel extends Panel implements IFeedback {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public CustomFeedbackPanel(String id) {
        this(id, null);
    }

    private final class MessageListView extends ListView<FeedbackMessage> {
        private static final long serialVersionUID = 1L;

        /**
         * @see org.apache.wicket.Component#Component(String)
         */
        public MessageListView(final String id) {
            super(id);
            setDefaultModel(newFeedbackMessagesModel());
        }

        @Override
        protected IModel<FeedbackMessage> getListItemModel(final IModel<? extends List<FeedbackMessage>> listViewModel, final int index) {
        	return new IModel<FeedbackMessage>() {

				private static final long serialVersionUID = 1L;

                /**
                 * WICKET-4258 Feedback messages might be cleared already.
                 *
                 * @see WebSession#cleanupFeedbackMessages()
                 */
				@Override
        		public FeedbackMessage getObject() {
                    if (index >= listViewModel.getObject().size()) {
                        return null;
                    } else {
                        return listViewModel.getObject().get(index);
                    }
        		}
			};

        }

        @Override
        protected void populateItem(final ListItem<FeedbackMessage> listItem) {
            final IModel<String> replacementModel = new Model<String>() {
                private static final long serialVersionUID = 1L;

                /**
                 * Returns feedbackPanel + the message level, eg
                 * 'feedbackPanelERROR'. This is used as the class of the li /
                 * span elements.
                 *
                 * @see org.apache.wicket.model.IModel#getObject()
                 */
                @Override
                public String getObject() {
                    return getCSSClass(listItem.getModelObject());
                }
            };

            final FeedbackMessage message = listItem.getModelObject();
            message.markRendered();
            final Component label = newMessageDisplayComponent("message", message);

            final AttributeModifier levelModifier = new AttributeModifier("class", replacementModel);

            listItem.add(levelModifier);
            listItem.add(label);
            listItem.add(new Label("message-type", getMessageKind(message)));
        }
    }

    /** Message view */
    private final MessageListView messageListView;

    /**
     * @see org.apache.wicket.Component#Component(String)
     *
     * @param id
     * @param filter
     */
    public CustomFeedbackPanel(final String id, IFeedbackMessageFilter filter) {
        super(id);
        WebMarkupContainer messagesContainer = new WebMarkupContainer("feedbackul") {
            private static final long serialVersionUID = 1L;

            @Override
            protected void onConfigure() {
                super.onConfigure();
                setVisible(anyMessage());
            }
        };
        add(messagesContainer);
        messageListView = new MessageListView("messages");
        messageListView.setVersioned(false);
        messagesContainer.add(messageListView);

        if (filter != null) {
            setFilter(filter);
        }
    }

    /**
     * Search messages that this panel will render, and see if there is any
     * message of level ERROR or up. This is a convenience method; same as
     * calling 'anyMessage(FeedbackMessage.ERROR)'.
     *
     * @return whether there is any message for this panel of level ERROR or up
     */
    public final boolean anyErrorMessage() {
        return anyMessage(FeedbackMessage.ERROR);
    }

    /**
     * Search messages that this panel will render, and see if there is any
     * message.
     *
     * @return whether there is any message for this panel
     */
    public final boolean anyMessage() {
        return anyMessage(FeedbackMessage.UNDEFINED);
    }

    /**
     * Search messages that this panel will render, and see if there is any
     * message of the given level.
     *
     * @param level
     *            the level, see FeedbackMessage
     * @return whether there is any message for this panel of the given level
     */
    public final boolean anyMessage(int level) {
        List<FeedbackMessage> msgs = getCurrentMessages();

        for (FeedbackMessage msg : msgs) {
            if (msg.isLevel(level)) {
                return true;
            }
        }

        return false;
    }

    /**
     * @return Model for feedback messages on which you can install filters and
     *         other properties
     */
    public final FeedbackMessagesModel getFeedbackMessagesModel() {
        return (FeedbackMessagesModel) messageListView.getDefaultModel();
    }

    /**
     * @return The current message filter
     */
    public final IFeedbackMessageFilter getFilter() {
        return getFeedbackMessagesModel().getFilter();
    }

    /**
     * @return The current sorting comparator
     */
    public final Comparator<FeedbackMessage> getSortingComparator() {
        return getFeedbackMessagesModel().getSortingComparator();
    }

    /**
     * @see org.apache.wicket.Component#isVersioned()
     */
    @Override
    public boolean isVersioned() {
        return false;
    }

    /**
     * Sets a filter to use on the feedback messages model
     *
     * @param filter
     *            The message filter to install on the feedback messages model
     *
     * @return FeedbackPanel this.
     */
    public final CustomFeedbackPanel setFilter(IFeedbackMessageFilter filter) {
        getFeedbackMessagesModel().setFilter(filter);
        return this;
    }

    /**
     * @param maxMessages
     *            The maximum number of feedback messages that this feedback
     *            panel should show at one time
     *
     * @return FeedbackPanel this.
     */
    public final CustomFeedbackPanel setMaxMessages(int maxMessages) {
        messageListView.setViewSize(maxMessages);
        return this;
    }

    /**
     * Sets the comparator used for sorting the messages.
     *
     * @param sortingComparator
     *            comparator used for sorting the messages.
     *
     * @return FeedbackPanel this.
     */
    public final CustomFeedbackPanel setSortingComparator(Comparator<FeedbackMessage> sortingComparator) {
        getFeedbackMessagesModel().setSortingComparator(sortingComparator);
        return this;
    }

    /**
     * Gets the css class for the given message.
     *
     * @param message
     *            the message
     * @return the css class; by default, this returns alert + the
     *         message level, eg 'alert-error', but you can override this
     *         method to provide your own
     */
    protected String getCSSClass(final FeedbackMessage message) {
        int level = message.getLevel();
        switch (level) {
        case FeedbackMessage.SUCCESS:
            return "alert-success alert alert-block ";
        case FeedbackMessage.ERROR:
            return "alert-error alert alert-block ";
        case FeedbackMessage.FATAL:
            return "alert-error alert alert-block ";
        case FeedbackMessage.INFO:
            return "alert-info alert alert-block ";
        case FeedbackMessage.WARNING:
            return "alert alert-block ";
        default:
            break;
        }
        return "alert-info alert alert-block ";
    }

    protected IModel<String> getMessageKind(FeedbackMessage message) {
        int level = message.getLevel();
        String key = null;
        switch (level) {
        case FeedbackMessage.SUCCESS:
        	key = "success";
        	break;
        case FeedbackMessage.ERROR:
        	key = "error";
        	break;
        case FeedbackMessage.FATAL:
        	key = "fatal";
        	break;
        case FeedbackMessage.INFO:
        	key = "info";
        	break;
        case FeedbackMessage.WARNING:
        	key = "warning";
        	break;
        default:
            break;
        }
        return new StringResourceModel(key, this, null);

    }

    /**
     * Gets the currently collected messages for this panel.
     *
     * @return the currently collected messages for this panel, possibly empty
     */
    protected final List<FeedbackMessage> getCurrentMessages() {
        final List<FeedbackMessage> messages = messageListView.getModelObject();
        return Collections.unmodifiableList(messages);
    }

    /**
     * Gets a new instance of FeedbackMessagesModel to use.
     *
     * @return Instance of FeedbackMessagesModel to use
     */
    protected FeedbackMessagesModel newFeedbackMessagesModel() {
        return new FeedbackMessagesModel(this);
    }

    /**
     * Generates a component that is used to display the message inside the
     * feedback panel. This component must handle being attached to
     * <code>span</code> tags.
     *
     * By default a {@link Label} is used.
     *
     * Note that the created component is expected to respect feedback panel's
     * {@link #getEscapeModelStrings()} value
     *
     * @param id
     *            parent id
     * @param message
     *            feedback message
     * @return component used to display the message
     */
    protected Component newMessageDisplayComponent(String id, FeedbackMessage message) {
        Serializable serializable = message.getMessage();
		MultiLineLabel label = new MultiLineLabel(id, (serializable == null) ? "" : serializable.toString());
        label.setEscapeModelStrings(CustomFeedbackPanel.this.getEscapeModelStrings());
        return label;
    }
}
