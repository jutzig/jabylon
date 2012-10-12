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
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.ResourceReference;
import org.apache.wicket.request.resource.UrlResourceReference;

import de.jutzig.jabylon.properties.Project;
import de.jutzig.jabylon.properties.ProjectLocale;
import de.jutzig.jabylon.properties.ProjectVersion;
import de.jutzig.jabylon.properties.PropertiesPackage;
import de.jutzig.jabylon.properties.Resolvable;
import de.jutzig.jabylon.properties.ResourceFolder;
import de.jutzig.jabylon.properties.Workspace;
import de.jutzig.jabylon.properties.util.PropertiesSwitch;
import de.jutzig.jabylon.rest.ui.model.ComplexEObjectListDataProvider;
import de.jutzig.jabylon.rest.ui.wicket.BasicResolvablePanel;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 */
public class ProjectResourcePanel extends BasicResolvablePanel<Resolvable<?, ?>> {

	public ProjectResourcePanel(Resolvable<?, ?> object, PageParameters parameters) {
		super("content", object, parameters);
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
				item.add(new Label("summary",new Summary().doSwitch(resolvable)));
				if (resolvable instanceof ProjectLocale) {
					// hide the template language by default
					ProjectLocale locale = (ProjectLocale) resolvable;
					if (locale.isMaster())
						item.setVisible(false);

				}

				LinkTarget target = buildLinkTarget(resolvable, endsOnSlash);

				ExternalLink link = new ExternalLink("link", target.getHref(), target.getLabel());
				item.add(link);
				Label progress = new Label("progress", "");
				progress.add(new AttributeModifier("style", "width: " + resolvable.getPercentComplete() + "%"));
				item.add(progress);
				new ImageSwitch(item).doSwitch(target.getEndPoint());
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
		
		Image image = new Image("regular-image", getIconForLocale(object.getLocale()));
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

	public ResourceReference getIconForLocale(Locale locale)
	{
		if(locale==null)
			return null;
		String iconName = "";
		if(locale.getCountry()!=null && locale.getCountry().length()>0)
		{
			iconName = locale.getCountry().toLowerCase();
		}
		else
		{
			iconName = derriveCountry(locale);
		}
		UrlResourceReference ref = new UrlResourceReference(Url.parse("/VAADIN/themes/jabylon/img/flags/gif/"+iconName+".gif"));
		return ref;
	}

	private String derriveCountry(Locale locale) {
		String language = locale.getLanguage();
		if("da".equals(language)) //denmark
			return "dk";
		
		else if("ja".equals(language)) //japanese
			return "jp";
		else if("uk".equals(language)) //ukraine
			return "ua";
		else if("eu".equals(language)) //basque
			return null; //don't have this one yet
		else if("he".equals(language)) //hebrew
			return "il"; //isreal
		else if("iw".equals(language)) //old hebrew code, still used in java
			return "il"; //isreal		
		else if("el".equals(language)) //greek
			return "gr"; 
		else if("ko".equals(language)) //korean
			return "kr";
		else if("te".equals(language)) //telegu
			return "in"; //india
		else if("ca".equals(language)) //catalan
			return "catalonia"; //official language in andorra (AD), but also other places. Use catalonia for now 
		
		
		return language.toLowerCase(); //this works in many cases, but is wrong in some
	}
	
}
