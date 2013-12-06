/**
 *
 */
package org.jabylon.rest.ui.wicket.panels;

import java.text.MessageFormat;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
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
import org.jabylon.properties.Workspace;
import org.jabylon.properties.util.PropertiesSwitch;
import org.jabylon.rest.ui.model.ComplexEObjectListDataProvider;
import org.jabylon.rest.ui.security.RestrictedComponent;
import org.jabylon.rest.ui.util.GlobalResources;
import org.jabylon.rest.ui.util.WicketUtil;
import org.jabylon.rest.ui.wicket.BasicResolvablePanel;
import org.jabylon.security.CommonPermissions;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 */
public class ProjectResourcePanel extends BasicResolvablePanel<Resolvable<?, ?>> implements RestrictedComponent {

    private static final long serialVersionUID = 1L;

    public ProjectResourcePanel(Resolvable<?, ?> object, PageParameters parameters) {
        super("content", object, parameters);
        add(new Label("header", new LabelSwitch().doSwitch(object)));
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

                if (resolvable instanceof ProjectLocale) {
                    // hide the template language by default
                    ProjectLocale locale = (ProjectLocale) resolvable;
                    if (locale.isMaster())
                        item.setVisible(false);

                }

                LinkTarget target = buildLinkTarget(resolvable, endsOnSlash);

                ExternalLink link = new ExternalLink("link", target.getHref(), target.getLabel());
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
                item.add(new Label("summary",new Summary().doSwitch(target.getEndPoint())));
            }

        };
        // dataView.setItemsPerPage(10);
        add(dataView);
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
                yellowWidth = (int) (reviews.size()*100/(double)keys);
                greenWidth -= yellowWidth;
            }
        }
        return new Triplet(greenWidth, yellowWidth);

    }

    private LinkTarget buildLinkTarget(Resolvable<?, ?> resolvable, boolean endsOnSlash) {
        StringBuilder hrefBuilder = new StringBuilder();
        LabelSwitch labelSwitch = new LabelSwitch();
        StringBuilder name = new StringBuilder();
        name.append(labelSwitch.doSwitch(resolvable));
        if (resolvable.getParent() == null)
            hrefBuilder.append("/");
        else if (resolvable.getParent() instanceof Workspace)
            hrefBuilder.append(endsOnSlash ? resolvable.getName() : "workspace/" + resolvable.getName());
        else
            hrefBuilder.append(endsOnSlash ? resolvable.getName() : resolvable.getParent().getName() + "/" + resolvable.getName());

        Resolvable<?, ?> folder = (Resolvable<?, ?>) resolvable;
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
        LinkTarget target = new LinkTarget(name.toString(),hrefBuilder.toString(),folder);
        return target;

    }

    @Override
    public String getRequiredPermission() {
        Resolvable<?, ?> object = getModelObject();
        while(object!=null) {
            if (object instanceof Project) {
                Project project = (Project) object;
                return CommonPermissions.constructPermission(CommonPermissions.PROJECT,project.getName(),CommonPermissions.ACTION_VIEW);
            }
            else if (object instanceof Workspace) {
                return CommonPermissions.constructPermission(CommonPermissions.WORKSPACE,CommonPermissions.ACTION_VIEW);
            }
            object = object.getParent();
        }
        return null;
    }
}

class LinkTarget
{
    private String label;
    private String href;
    private Resolvable<?, ?> endPoint;
    public LinkTarget(String label, String href, Resolvable<?, ?> endPoint) {
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

    public String getLabel() {
        return label;
    }

}

class LabelSwitch extends PropertiesSwitch<String> {
    @Override
    public <P extends Resolvable<?, ?>, C extends Resolvable<?, ?>> String caseResolvable(Resolvable<P, C> object) {
        return object.getName();
    }

    @Override
    public String caseProjectLocale(ProjectLocale object) {
        if (object.getLocale() != null)
            return object.getLocale().getDisplayName();
        return "Template";
    }

    @Override
    public String caseWorkspace(Workspace object) {
        return "Workspace";
    }
}

class Summary extends PropertiesSwitch<String> {
    @Override
    public <P extends Resolvable<?, ?>, C extends Resolvable<?, ?>> String caseResolvable(Resolvable<P, C> object) {
        return object.getPercentComplete()+"% complete";
    }

    @Override
    public String caseProjectLocale(ProjectLocale object) {
        if(object.getParent()==null && object.getParent().getTemplate()==null)
            return null;
        ProjectLocale template = object.getParent().getTemplate();
        int propertyCount = template.getPropertyCount();
        int translatedCount = object.getPropertyCount();
        String message = "{0} of {1} translated ({2}%)";
        message = MessageFormat.format(message, translatedCount,propertyCount,object.getPercentComplete());
        return message;
    }

    @Override
    public String casePropertyFileDescriptor(PropertyFileDescriptor object) {
        int propertyCount = object.getKeys();
        if(object.isMaster())
        {
            String message = "{0} keys";
            message = MessageFormat.format(message, propertyCount);
            return message;
        }
        else
        {
            int templateCount = object.getMaster().getKeys();
            String message = "{0} of {1} translated ({2}%)";
            message = MessageFormat.format(message, propertyCount, templateCount, object.getPercentComplete());
            return message;

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
        if(object.getLocale()==null)
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
