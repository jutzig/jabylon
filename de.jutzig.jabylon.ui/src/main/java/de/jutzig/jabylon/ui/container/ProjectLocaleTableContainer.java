/**
 * 
 */
package de.jutzig.jabylon.ui.container;

import java.text.MessageFormat;
import java.util.Collection;
import java.util.EnumSet;
import java.util.Locale;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;

import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.util.AbstractContainer;
import com.vaadin.terminal.Resource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Label;
import com.vaadin.ui.themes.Reindeer;

import de.jutzig.jabylon.properties.ProjectLocale;
import de.jutzig.jabylon.properties.ProjectVersion;
import de.jutzig.jabylon.properties.PropertiesPackage;
import de.jutzig.jabylon.ui.applications.MainDashboard;
import de.jutzig.jabylon.ui.components.ResolvableProgressIndicator;
import de.jutzig.jabylon.ui.container.ProjectLocaleTableContainer.LocaleProperty;
import de.jutzig.jabylon.ui.util.LocaleUtil;
import de.jutzig.jabylon.ui.util.WeakReferenceAdapter;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 *
 */
public class ProjectLocaleTableContainer extends AbstractContainer implements Container.ItemSetChangeNotifier {

	
	private ProjectVersion project;

	
	public static enum LocaleProperty{
		
		FLAG(Resource.class),LOCALE(Button.class),SUMMARY(String.class),PROGRESS(Label.class);
		
		private Class<?> type;
		
		private LocaleProperty(Class<?> type) {
			this.type = type;
		}
	}
	
	
	public ProjectLocaleTableContainer(final ProjectVersion project) {
		this.project = project;
		project.eAdapters().add(new WeakReferenceAdapter(new AdapterImpl() {
			@Override
			public void notifyChanged(Notification msg) {
				if(msg.getFeature()==PropertiesPackage.Literals.PROJECT_VERSION__LOCALES)
				{
					//TODO: can probably do this more fine grained
//					project.cdoView().reload(project);
					fireItemSetChange();
				}
					
			}
		}));
	}
	
	/* (non-Javadoc)
	 * @see com.vaadin.data.Container#getItem(java.lang.Object)
	 */
	@Override
	public Item getItem(Object itemId) {
		
		ProjectLocale locale = (ProjectLocale)itemId;
		return new ProjectLocaleRow(locale);
	}

	/* (non-Javadoc)
	 * @see com.vaadin.data.Container#getContainerPropertyIds()
	 */
	@Override
	public Collection<?> getContainerPropertyIds() {
		return EnumSet.allOf(LocaleProperty.class);
	}

	/* (non-Javadoc)
	 * @see com.vaadin.data.Container#getItemIds()
	 */
	@Override
	public Collection<?> getItemIds() {
		return project.getLocales();
	}

	/* (non-Javadoc)
	 * @see com.vaadin.data.Container#getContainerProperty(java.lang.Object, java.lang.Object)
	 */
	@Override
	public Property getContainerProperty(Object itemId, Object propertyId) {
		return getItem(itemId).getItemProperty(propertyId);
	}

	/* (non-Javadoc)
	 * @see com.vaadin.data.Container#getType(java.lang.Object)
	 */
	@Override
	public Class<?> getType(Object propertyId) {
		LocaleProperty property = (LocaleProperty)propertyId;
		return property.type;
	}

	/* (non-Javadoc)
	 * @see com.vaadin.data.Container#size()
	 */
	@Override
	public int size() {
		return project.getLocales().size();
	}

	/* (non-Javadoc)
	 * @see com.vaadin.data.Container#containsId(java.lang.Object)
	 */
	@Override
	public boolean containsId(Object itemId) {
		return project.getLocales().contains(itemId);
	}

	/* (non-Javadoc)
	 * @see com.vaadin.data.Container#addItem(java.lang.Object)
	 */
	@Override
	public Item addItem(Object itemId) throws UnsupportedOperationException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.vaadin.data.Container#addItem()
	 */
	@Override
	public Object addItem() throws UnsupportedOperationException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.vaadin.data.Container#removeItem(java.lang.Object)
	 */
	@Override
	public boolean removeItem(Object itemId) throws UnsupportedOperationException {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.vaadin.data.Container#addContainerProperty(java.lang.Object, java.lang.Class, java.lang.Object)
	 */
	@Override
	public boolean addContainerProperty(Object propertyId, Class<?> type, Object defaultValue) throws UnsupportedOperationException {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.vaadin.data.Container#removeContainerProperty(java.lang.Object)
	 */
	@Override
	public boolean removeContainerProperty(Object propertyId) throws UnsupportedOperationException {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.vaadin.data.Container#removeAllItems()
	 */
	@Override
	public boolean removeAllItems() throws UnsupportedOperationException {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public void addListener(ItemSetChangeListener listener) {
	
		super.addListener(listener);
	}
	
	@Override
	public void removeListener(ItemSetChangeListener listener) {
		super.removeListener(listener);
	}

}


class ProjectLocaleRow implements Item
{

	
	private Property flag;
	private Property locale;
	private Property summary;
	private Property progress;
	private ProjectLocale projectLocale;
	
	
	
	
	public ProjectLocaleRow(ProjectLocale locale) {
		super();
		this.projectLocale = locale;
	}
	@Override
	public Property getItemProperty(Object id) {
		LocaleProperty property = (LocaleProperty)id;
		switch (property) {
		case FLAG:
			return getFlag();

		case LOCALE:
			return getLocale();
		case PROGRESS:
			return getProgress();
		case SUMMARY:
			return getSummary();
		default:
			break;
		}
		return null;
	}
	@Override
	public Collection<?> getItemPropertyIds() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean addItemProperty(Object id, Property property) throws UnsupportedOperationException {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean removeItemProperty(Object id) throws UnsupportedOperationException {
		// TODO Auto-generated method stub
		return false;
	}
	
	public Property getFlag() {
		if(flag==null)
		{
			flag = new GenericProperty<Resource>(Resource.class, LocaleUtil.getIconForLocale(projectLocale));
		}
		return flag;
	}
	
	public Property getLocale() {
		if(locale==null)
		{
			Locale userLocale = Locale.getDefault();
			if(MainDashboard.getCurrent()!=null) //doesn't work like that if triggered from another thread (EMF notification)
			{
				userLocale = MainDashboard.getCurrent().getLocale();
			}
			String displayName = projectLocale.getLocale().getDisplayName(userLocale);
			Button button = new Button(displayName);
			button.setStyleName(Reindeer.BUTTON_LINK);
			button.addListener(new ClickListener() {
				
				@Override
				public void buttonClick(ClickEvent event) {
					
					MainDashboard.getCurrent().getBreadcrumbs().setPath(projectLocale.getProjectVersion().getProject().getName(),projectLocale.getLocale().toString());
					
				}
			});
			locale = new GenericProperty<Button>(Button.class, button);
		}
		return locale;
	}
	
	public Property getProgress() {
		if(progress==null)
		{
			progress = new GenericProperty<ResolvableProgressIndicator>(ResolvableProgressIndicator.class,new ResolvableProgressIndicator(projectLocale));
		}
		return progress;
	}
	
	public Property getSummary() {
		
		if(summary==null)
		{
			summary = new GenericProperty<String>(String.class, buildSummary(projectLocale));
		}
		return summary;
	}
	
	private String buildSummary(ProjectLocale locale) {

		int totalKeys = projectLocale.getProjectVersion().getMaster().getPropertyCount();
		int actualKeys = locale.getPropertyCount();
		if(actualKeys==totalKeys)
		{
			return "Complete";
		}
		else if(actualKeys<totalKeys)
		{

			String message = "{0} out of {1} strings need attention";
			message = MessageFormat.format(message, totalKeys-actualKeys,totalKeys);
			return message;
		}
		else
		{

			String message = "Warning: Contains {0} keys more than the template language";
			message = MessageFormat.format(message, actualKeys-totalKeys);
			return message;
		}

	}
}
