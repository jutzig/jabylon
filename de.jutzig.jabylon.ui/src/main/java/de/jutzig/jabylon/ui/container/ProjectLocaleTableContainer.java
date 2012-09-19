/**
 * 
 */
package de.jutzig.jabylon.ui.container;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;

import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.util.AbstractInMemoryContainer;
import com.vaadin.terminal.Resource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Label;
import com.vaadin.ui.themes.Reindeer;

import de.jutzig.jabylon.properties.Project;
import de.jutzig.jabylon.properties.ProjectLocale;
import de.jutzig.jabylon.properties.ProjectVersion;
import de.jutzig.jabylon.properties.PropertiesPackage;
import de.jutzig.jabylon.ui.applications.MainDashboard;
import de.jutzig.jabylon.ui.components.ResolvableProgressIndicator;
import de.jutzig.jabylon.ui.components.SortableButton;
import de.jutzig.jabylon.ui.container.ProjectLocaleTableContainer.LocaleProperty;
import de.jutzig.jabylon.ui.util.LocaleUtil;
import de.jutzig.jabylon.ui.util.WeakReferenceAdapter;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 * 
 */
public class ProjectLocaleTableContainer extends AbstractInMemoryContainer<ProjectLocale, LocaleProperty, Item> implements
		Container.ItemSetChangeNotifier, Container.Sortable {

	private ProjectVersion project;
	private Map<ProjectLocale, ProjectLocaleRow> itemCache;
	private List<ProjectLocale> sortableList;

	public static enum LocaleProperty {

		FLAG(Resource.class), LOCALE(Button.class), SUMMARY(String.class), PROGRESS(Label.class);
		private Class<?> type;

		private LocaleProperty(Class<?> type) {
			this.type = type;
		}
	}

	public ProjectLocaleTableContainer(final ProjectVersion project) {
		this.project = project;
		itemCache = new HashMap<ProjectLocale, ProjectLocaleRow>();
		project.eAdapters().add(new WeakReferenceAdapter(new AdapterImpl() {
			@Override
			public void notifyChanged(Notification msg) {
				if (msg.getFeature() == PropertiesPackage.Literals.RESOLVABLE__CHILDREN) {
					// TODO: can probably do this more fine grained
					project.cdoReload();
					itemCache.clear();
					sortableList = new ArrayList<ProjectLocale>(project.getChildren());
					fireItemSetChange();
				}

			}
		}));
		sortableList = new ArrayList<ProjectLocale>(project.getChildren());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.vaadin.data.Container#getContainerPropertyIds()
	 */
	@Override
	public Collection<?> getContainerPropertyIds() {
		return EnumSet.allOf(LocaleProperty.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.vaadin.data.Container#getContainerProperty(java.lang.Object,
	 * java.lang.Object)
	 */
	@Override
	public Property getContainerProperty(Object itemId, Object propertyId) {
		return getItem(itemId).getItemProperty(propertyId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.vaadin.data.Container#getType(java.lang.Object)
	 */
	@Override
	public Class<?> getType(Object propertyId) {
		LocaleProperty property = (LocaleProperty) propertyId;
		return property.type;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.vaadin.data.Container#size()
	 */
	@Override
	public int size() {
		return project.getChildren().size();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.vaadin.data.Container#containsId(java.lang.Object)
	 */
	@Override
	public boolean containsId(Object itemId) {
		return project.getChildren().contains(itemId);
	}

	@Override
	protected Item getUnfilteredItem(Object itemId) {
		ProjectLocale locale = (ProjectLocale) itemId;
		ProjectLocaleRow row = itemCache.get(itemId);
		if (row == null) {
			row = new ProjectLocaleRow(locale);
			itemCache.put(locale, row);
		}
		return row;
	}

	@Override
	protected List<ProjectLocale> getAllItemIds() {
		return sortableList;
	}

	@Override
	public void sort(Object[] propertyId, boolean[] ascending) {
		sortContainer(propertyId, ascending);

	}

	@Override
	public Collection<?> getSortableContainerPropertyIds() {
		return getContainerPropertyIds();
	}

}

class ProjectLocaleRow implements Item {

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
		LocaleProperty property = (LocaleProperty) id;
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
		if (flag == null) {
			flag = new GenericProperty<Resource>(Resource.class, LocaleUtil.getIconForLocale(projectLocale));
		}
		return flag;
	}

	public Property getLocale() {
		if (locale == null) {
			Locale userLocale = Locale.getDefault();
			if (MainDashboard.getCurrent() != null) // doesn't work like that if
													// triggered from another
													// thread (EMF notification)
			{
				userLocale = MainDashboard.getCurrent().getLocale();
			}
			String displayName = projectLocale.getLocale().getDisplayName(userLocale);
			Button button = new SortableButton(displayName);
			button.setStyleName(Reindeer.BUTTON_LINK);
			button.addListener(new ClickListener() {

				@Override
				public void buttonClick(ClickEvent event) {

					ProjectVersion projectVersion = projectLocale.getParent();
					Project project = projectVersion.getParent();

					String version = "?" + projectVersion.getName();
					MainDashboard.getCurrent().getBreadcrumbs().setPath(project.getName(), version, projectLocale.getLocale().toString());

				}
			});
			locale = new GenericProperty<Button>(Button.class, button);
		}
		return locale;
	}

	public Property getProgress() {
		if (progress == null) {
			progress = new GenericProperty<ResolvableProgressIndicator>(ResolvableProgressIndicator.class, new ResolvableProgressIndicator(
					projectLocale));
		}
		return progress;
	}

	public Property getSummary() {

		if (summary == null) {
			summary = new GenericProperty<String>(String.class, buildSummary(projectLocale));
		}
		return summary;
	}

	private String buildSummary(ProjectLocale locale) {

		// TODO: how can this happen?
		if (projectLocale == null || projectLocale.getParent() == null || projectLocale.getParent().getTemplate() == null)
			return "";
		int totalKeys = projectLocale.getParent().getTemplate().getPropertyCount();
		int actualKeys = locale.getPropertyCount();
		if (actualKeys == totalKeys) {
			return "Complete";
		} else if (actualKeys < totalKeys) {

			String message = "{0} out of {1} strings need attention";
			message = MessageFormat.format(message, totalKeys - actualKeys, totalKeys);
			return message;
		} else {

			String message = "Warning: Contains {0} keys more than the template language";
			message = MessageFormat.format(message, actualKeys - totalKeys);
			return message;
		}

	}
}
