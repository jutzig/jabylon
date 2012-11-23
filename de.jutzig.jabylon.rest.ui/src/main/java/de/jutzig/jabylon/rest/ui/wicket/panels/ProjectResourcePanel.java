/**
 *
 */
package de.jutzig.jabylon.rest.ui.wicket.panels;

import java.text.MessageFormat;
import java.util.Locale;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.request.Url;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.ResourceReference;
import org.apache.wicket.request.resource.UrlResourceReference;

import de.jutzig.jabylon.properties.Project;
import de.jutzig.jabylon.properties.ProjectLocale;
import de.jutzig.jabylon.properties.ProjectVersion;
import de.jutzig.jabylon.properties.PropertiesPackage;
import de.jutzig.jabylon.properties.PropertyFileDescriptor;
import de.jutzig.jabylon.properties.Resolvable;
import de.jutzig.jabylon.properties.ResourceFolder;
import de.jutzig.jabylon.properties.Workspace;
import de.jutzig.jabylon.properties.util.PropertiesSwitch;
import de.jutzig.jabylon.rest.ui.Activator;
import de.jutzig.jabylon.rest.ui.model.ComplexEObjectListDataProvider;
import de.jutzig.jabylon.rest.ui.util.WicketUtil;
import de.jutzig.jabylon.rest.ui.wicket.BasicResolvablePanel;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 */
public class ProjectResourcePanel extends BasicResolvablePanel<Resolvable<?, ?>> {

	private static final long serialVersionUID = 1L;

	public ProjectResourcePanel(Resolvable<?, ?> object, PageParameters parameters) {
		super("content", object, parameters);
		add(new Label("header", new LabelSwitch().doSwitch(object)));
	}

	@Override
	protected void onBeforeRender() {
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
				Label progress = new Label("progress", String.valueOf(resolvable.getPercentComplete())+"%");
				progress.add(new AttributeModifier("style", "width: " + resolvable.getPercentComplete() + "%"));
				item.add(progress);
				new ImageSwitch(item).doSwitch(target.getEndPoint());
				item.add(new Label("summary",new Summary().doSwitch(target.getEndPoint())));
			}

		};
		// dataView.setItemsPerPage(10);
		add(dataView);
		super.onBeforeRender();
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
