/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
/**
 *
 */
package org.jabylon.rest.ui.wicket.panels;

import java.text.MessageFormat;
import java.util.Locale;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.Session;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.eclipse.emf.common.util.EList;
import org.jabylon.properties.Project;
import org.jabylon.properties.ProjectLocale;
import org.jabylon.properties.ProjectVersion;
import org.jabylon.properties.PropertiesPackage;
import org.jabylon.properties.PropertyFileDescriptor;
import org.jabylon.properties.Resolvable;
import org.jabylon.properties.ResourceFolder;
import org.jabylon.properties.Review;
import org.jabylon.properties.ReviewState;
import org.jabylon.properties.Workspace;
import org.jabylon.properties.util.PropertiesSwitch;
import org.jabylon.rest.ui.model.ComplexEObjectListDataProvider;
import org.jabylon.rest.ui.model.CustomStringResourceModel;
import org.jabylon.rest.ui.security.CDOAuthenticatedSession;
import org.jabylon.rest.ui.security.RestrictedComponent;
import org.jabylon.rest.ui.util.GlobalResources;
import org.jabylon.rest.ui.util.WicketUtil;
import org.jabylon.rest.ui.wicket.BasicResolvablePanel;
import org.jabylon.rest.ui.wicket.pages.XliffDownloadPage;
import org.jabylon.security.CommonPermissions;
import org.jabylon.users.User;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 */
public class ProjectResourcePanel extends BasicResolvablePanel<Resolvable<?, ?>> implements RestrictedComponent {

    private static final long serialVersionUID = 1L;

	public ProjectResourcePanel(Resolvable<?, ?> object, PageParameters parameters) {
        super("content", object, parameters);
        if (object instanceof Project) {
			Project project = (Project) object;
			String announcement = project.getAnnouncement();
			if(announcement!=null && !announcement.isEmpty())
				info(announcement);
		}
        add(new Label("header", new LabelSwitch(getLocale()).doSwitch(object)));
        String href = WicketUtil.getContextPath() + "/api/"+ getModelObject().toURI().appendQuery("type=file");
        ExternalLink downloadLink = new ExternalLink("download.link", href);
        downloadLink.setVisible(object != null && !(object instanceof Workspace) && !(object instanceof Project));
        add(downloadLink);

		BookmarkablePageLink<String> downloadXliff = new BookmarkablePageLink<String>(
				"link-download-xliff", XliffDownloadPage.class, parameters); //$NON-NLS-1$
		downloadXliff.setVisible(object instanceof ProjectVersion);
		add(downloadXliff);

		XliffUploadPanel panel = new XliffUploadPanel("panel-upload-xliff", getModel(), parameters);
		panel.setVisible(uploadPanelVisible(object));
		add(panel);
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        response.render(JavaScriptHeaderItem.forReference(GlobalResources.JS_JQUERY_DATATABLES));
        response.render(JavaScriptHeaderItem.forReference(GlobalResources.JS_BOOTSTRAP_DATATABLES));
        response.render(JavaScriptHeaderItem.forReference(GlobalResources.JS_DATATABLES_CUSTOMSORT));
		super.renderHead(response);
    }

    @Override
    protected void onBeforeRenderPanel() {
        ComplexEObjectListDataProvider<Resolvable<?, ?>> provider = new ComplexEObjectListDataProvider<Resolvable<?, ?>>(getModel(),
                PropertiesPackage.Literals.RESOLVABLE__CHILDREN);
        final boolean endsOnSlash = urlEndsOnSlash();
        final DataView<Resolvable<?, ?>> dataView = new DataView<Resolvable<?, ?>>("children", provider) {

            private static final long serialVersionUID = -3530355534807668227L;

            @Override
            protected void populateItem(Item<Resolvable<?, ?>> item) {
                Resolvable<?, ?> resolvable = item.getModelObject();

				item.setVisible(canView(resolvable));

                LinkTarget target = buildLinkTarget(resolvable, endsOnSlash);

                ExternalLink link = new ExternalLink("link", Model.of(target.getHref()), target.getLabel());
                item.add(link);

                Triplet widths = computeProgressBars(target.getEndPoint());
                Label progress = new Label("progress", String.valueOf(widths.getSuccess())+"%");
                progress.add(new AttributeModifier("style", "width: " + widths.getSuccess()  + "%"));
                Label warning = new Label("warning", "");
                warning.add(new AttributeModifier("style", "width: " + widths.getWarning()  + "%"));
                Label danger = new Label("danger", "");
                danger.add(new AttributeModifier("style", "width: " + widths.getDanger()  + "%"));
                item.add(progress);
                item.add(warning);
                item.add(danger);

                new ImageSwitch(item).doSwitch(target.getEndPoint());
                item.add(new Label("summary",new Summary(item).doSwitch(target.getEndPoint())));
            }

        };
        // dataView.setItemsPerPage(10);
        add(dataView);
    }

    protected boolean canView(Resolvable<?, ?> resolvable) {
    	Session session = getSession();
    	if (session instanceof CDOAuthenticatedSession) {
			CDOAuthenticatedSession authSession = (CDOAuthenticatedSession) session;
			User user = authSession.getUser();
			if(user==null)
				user = authSession.getAnonymousUser();
			if(user!=null)
				return CommonPermissions.hasViewPermission(user, resolvable);
		}
		return false;
	}

	/**
     * computes the width of the two stacked progress bars
     * @param resolvable
     * @return
     */
    protected Triplet computeProgressBars(Resolvable<?, ?> resolvable) {

        int greenWidth = resolvable.getPercentComplete();
        int yellowWidth = 0;
        if (resolvable instanceof PropertyFileDescriptor) {
            PropertyFileDescriptor descriptor = (PropertyFileDescriptor) resolvable;
            int keys = descriptor.getMaster() == null ? descriptor.getKeys() : descriptor.getMaster().getKeys();
            if(keys>0)
            {
                EList<Review> reviews = descriptor.getReviews();
                int activeReviews = 0;
                for (Review review : reviews) {
					if(review.getState()==ReviewState.OPEN || review.getState()==ReviewState.REOPENED)
						activeReviews++;
				}
                yellowWidth = (int) (activeReviews*100/(double)keys);
                greenWidth -= yellowWidth;
            }
        }
        return new Triplet(greenWidth, yellowWidth);

    }

    private LinkTarget buildLinkTarget(Resolvable<?, ?> resolvable, boolean endsOnSlash) {
        StringBuilder hrefBuilder = new StringBuilder();
        LabelSwitch labelSwitch = new LabelSwitch(getLocale());
        StringBuilder name = new StringBuilder();
        name.append(labelSwitch.doSwitch(resolvable));
        if (resolvable.getParent() == null)
            hrefBuilder.append("/");
        else if (resolvable.getParent() instanceof Workspace)
            hrefBuilder.append(endsOnSlash ? resolvable.getName() : "workspace/" + resolvable.getName());
        else
            hrefBuilder.append(endsOnSlash ? resolvable.getName() : resolvable.getParent().getName() + "/" + resolvable.getName());

        Resolvable<?, ?> folder = resolvable;
        if(folder instanceof ResourceFolder)
        {
            // if it is a folder, squash more children, if there is only one
            while (folder.getChildren().size() == 1) {
                folder = folder.getChildren().get(0);
                hrefBuilder.append("/");
                hrefBuilder.append(folder.getName());
                name.append("/");
                name.append(labelSwitch.doSwitch(folder));
            }
        }
        LinkTarget target = new LinkTarget(Model.of(name.toString()),hrefBuilder.toString(),folder);
        return target;

    }

    @Override
    public String getRequiredPermission() {
        Resolvable<?, ?> object = getModelObject();
        while(object!=null) {
            if (object instanceof Project) {
                return CommonPermissions.constructPermissionName(object, CommonPermissions.ACTION_VIEW);
            }
            else if (object instanceof Workspace) {
            	return CommonPermissions.constructPermissionName(object, CommonPermissions.ACTION_VIEW);
            }
            object = object.getParent();
        }
        return null;
    }

	/**
	 * @return true if the user is allowed to upload/import XLIFF files for this
	 *         {@link ProjectVersion}.<br>
	 */
	private boolean uploadPanelVisible(Resolvable<?, ?> object) {
		if (!(object instanceof ProjectVersion)) {
			return false;
		}

		ProjectVersion version = (ProjectVersion) object;
		if (version.isReadOnly()) {
			return false;
		}

		Session session = getSession();

		if (!(session instanceof CDOAuthenticatedSession)) {
			return false;
		}

		Project project = version.getParent();
		CDOAuthenticatedSession authSession = (CDOAuthenticatedSession) session;
		return authSession.hasPermission(CommonPermissions.constructPermission(CommonPermissions.PROJECT, project.getName(),
				CommonPermissions.ACTION_EDIT));
	}
}

class LinkTarget
{
    private IModel<String> label;
    private String href;
    private Resolvable<?, ?> endPoint;
    public LinkTarget(IModel<String> label, String href, Resolvable<?, ?> endPoint) {
        super();
        this.label = label;
        this.href = href;
        this.endPoint = endPoint;
    }

    public Resolvable<?, ?> getEndPoint() {
        return endPoint;
    }

    public String getHref() {
        return href;
    }

    public IModel<String> getLabel() {
        return label;
    }

}

class LabelSwitch extends PropertiesSwitch<String> {

	private Locale locale;



    public LabelSwitch(Locale locale) {
		super();
		this.locale = locale;
		if(locale==null)
			this.locale = Locale.getDefault();
	}

	@Override
    public <P extends Resolvable<?, ?>, C extends Resolvable<?, ?>> String caseResolvable(Resolvable<P, C> object) {
        return object.getName();
    }

    @Override
    public String caseProjectLocale(ProjectLocale object) {
        if (object.getLocale() != null)
            return object.getLocale().getDisplayName(locale);
        return "Template";
    }

    @Override
    public String caseWorkspace(Workspace object) {
        return "Workspace";
    }
}

class Summary extends PropertiesSwitch<IModel<String>> {
    private static final String NUMBER_OF_KEYS_KEY = "number.of.keys";
	private static final String TRANSLATION_PERCENTAGE_KEY = "translation.percentage";
	private static final String TRANSLATION_PERCENTAGE_SHORT_KEY = "translation.percentage.short";
	private transient Component parent;

	public Summary(Component parent) {
		this.parent = parent;
	}

	@Override
    public <P extends Resolvable<?, ?>, C extends Resolvable<?, ?>> IModel<String> caseResolvable(Resolvable<P, C> object) {
		return new CustomStringResourceModel(TRANSLATION_PERCENTAGE_SHORT_KEY, parent, null, object.getPercentComplete());
    }

    @Override
    public IModel<String> caseProjectLocale(ProjectLocale object) {
        if(object.getParent()==null && object.getParent().getTemplate()==null)
            return null;
        ProjectLocale template = object.getParent().getTemplate();
        int propertyCount = template.getPropertyCount();
        int translatedCount = object.getPropertyCount();
        return new CustomStringResourceModel(TRANSLATION_PERCENTAGE_KEY, parent, null, translatedCount,propertyCount,object.getPercentComplete());
    }

    @Override
    public IModel<String> casePropertyFileDescriptor(PropertyFileDescriptor object) {
        int propertyCount = object.getKeys();
        if(object.isMaster())
        {
            String message = NUMBER_OF_KEYS_KEY;
            message = MessageFormat.format(message, propertyCount);
            return new CustomStringResourceModel(NUMBER_OF_KEYS_KEY, parent, null, propertyCount);
        }
        else
        {
            int templateCount = object.getMaster().getKeys();
            return new CustomStringResourceModel(TRANSLATION_PERCENTAGE_KEY, parent, null, propertyCount,templateCount,object.getPercentComplete());

        }
    }

}


class ImageSwitch extends PropertiesSwitch<Item<?>> {

    private transient Item<?> item;

    public ImageSwitch(Item<?> item) {
        super();
        this.item = item;
    }

    @Override
    public Item<?> caseProject(Project object) {
        return addCSSIcon("icon-folder-close");
    }

    @Override
    public Item<?> caseProjectLocale(ProjectLocale object) {
        if(object.getLocale()==null || object.getLocale()==ProjectLocale.TEMPLATE_LOCALE)
            return addCSSIcon("icon-book");
        WebMarkupContainer markupContainer = new WebMarkupContainer("css-icon");
        item.add(markupContainer);
        markupContainer.setVisible(false);

        Image image = new Image("regular-image", WicketUtil.getIconForLocale(object.getLocale()));
        item.add(image);
        return item;
    }

    @Override
    public Item<?> caseProjectVersion(ProjectVersion object) {
        return addCSSIcon("icon-time");
    }

    @Override
    public Item<?> caseResourceFolder(ResourceFolder object) {
        return addCSSIcon("icon-folder-open");
    }

    @Override
    public <P extends Resolvable<?, ?>, C extends Resolvable<?, ?>> Item<?> caseResolvable(Resolvable<P, C> object) {
        return addCSSIcon("icon-file");
    }

    protected Item<?> addCSSIcon(String icon) {
        WebMarkupContainer markupContainer = new WebMarkupContainer("css-icon");
        item.add(markupContainer);
        markupContainer.add(new AttributeModifier("class", icon));
        Image image = new Image("regular-image", "not-there.gif");
        image.setVisible(false);
        item.add(image);
        return item;
    }
}



class Triplet
{
    int success, warning;

    public Triplet(int success, int warning)
    {
        super();
        this.success = success;
        this.warning = warning;
    }

    public int getSuccess()
    {
        return success;
    }

    public int getWarning()
    {
        return warning;
    }

    public int getDanger()
    {
        int danger = 100 - success - warning;
        //only show danger level if the remainder is <=5
//        https://github.com/jutzig/jabylon/issues/122
        if(danger<=5)
            return danger;
        return 0;
    }

}
