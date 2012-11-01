package de.jutzig.jabylon.ui.pages;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.eclipse.emf.cdo.CDOObject;
import org.eclipse.emf.cdo.util.CommitException;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;

import com.vaadin.data.Container;
import com.vaadin.data.Container.Filter;
import com.vaadin.data.Container.Filterable;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeListener;
import com.vaadin.terminal.StreamResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Link;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Reindeer;

import de.jutzig.jabylon.cdo.connector.Modification;
import de.jutzig.jabylon.cdo.connector.TransactionUtil;
import de.jutzig.jabylon.properties.Project;
import de.jutzig.jabylon.properties.ProjectLocale;
import de.jutzig.jabylon.properties.PropertiesFactory;
import de.jutzig.jabylon.properties.PropertyFileDescriptor;
import de.jutzig.jabylon.ui.applications.MainDashboard;
import de.jutzig.jabylon.ui.breadcrumb.CrumbTrail;
import de.jutzig.jabylon.ui.components.PropertiesEditor;
import de.jutzig.jabylon.ui.components.ResolvableProgressIndicator;
import de.jutzig.jabylon.ui.components.Section;
import de.jutzig.jabylon.ui.components.SortableButton;
import de.jutzig.jabylon.ui.components.StaticProgressIndicator;
import de.jutzig.jabylon.ui.resources.ImageConstants;
import de.jutzig.jabylon.ui.search.SearchResultPage;
import de.jutzig.jabylon.ui.styles.JabylonStyle;

public class ProjectLocaleDashboard implements CrumbTrail, ClickListener {

	private Project project;
	private ProjectLocale locale;
	Map<PropertyFileDescriptor, PropertyFileDescriptor> masterToTransation;

	public ProjectLocaleDashboard(ProjectLocale locale) {

		this.locale = locale;
		this.project = locale.getParent().getParent();

	}

	private void createContents(VerticalLayout parent) {
		buildHeader(parent);
		Section section = new Section();
		section.setCaption(Messages.getString("ProjectLocaleDashboard_TRANSLATABLE_FILES_SECTION"));
		final Table table = new Table();
        table.setColumnReorderingAllowed(true);
        table.setColumnCollapsingAllowed(true);

		TextField filterBox = new TextField();
		section.addComponent(filterBox);
		filterBox.addStyleName(JabylonStyle.SEARCH_FIELD.getCSSName());
		filterBox.addListener(new TextChangeListener() {

			@Override
			public void textChange(TextChangeEvent event) {
				Container container = table.getContainerDataSource();
				if (container instanceof Filterable) {
					Filterable filterable = (Filterable) container;
					filterable.removeAllContainerFilters();
					filterable.addContainerFilter(new LocationFilter(event.getText()));
					
				}

			}
		});
		filterBox.setInputPrompt(Messages.getString("ProjectLocaleDashboard_FILTER_PROMPT"));
		
		table.addStyleName(JabylonStyle.TABLE_STRIPED.getCSSName());
		table.setSizeFull();
		table.addContainerProperty("location", SortableButton.class, null); //$NON-NLS-1$
		table.addContainerProperty("total", Integer.class, 0); //$NON-NLS-1$
		table.addContainerProperty("translated", Integer.class, 0); //$NON-NLS-1$
		table.addContainerProperty("fuzzy", StaticProgressIndicator.class, 0); //$NON-NLS-1$
		
		table.addContainerProperty("progress", ResolvableProgressIndicator.class, null); //$NON-NLS-1$
		table.setColumnWidth("progress", 110); //$NON-NLS-1$
		table.setColumnWidth("fuzzy", 110); //$NON-NLS-1$

		table.setColumnAlignment("total", Table.ALIGN_CENTER); //$NON-NLS-1$
		table.setColumnAlignment("translated", Table.ALIGN_CENTER); //$NON-NLS-1$
		table.setColumnAlignment("fuzzy", Table.ALIGN_CENTER); //$NON-NLS-1$
		table.setColumnAlignment("progress", Table.ALIGN_CENTER); //$NON-NLS-1$
		
		// table.setVisibleColumns(new Object[]{});
		// table.setColumnWidth(locale,400);
		for (Entry<PropertyFileDescriptor, PropertyFileDescriptor> entry : masterToTransation.entrySet()) {
			Button fileName = new SortableButton(entry.getKey().getLocation().toString());
			fileName.setIcon(entry.getValue() == null ? ImageConstants.IMAGE_NEW_PROPERTIES_FILE : ImageConstants.IMAGE_PROPERTIES_FILE);
			fileName.setStyleName(Reindeer.BUTTON_LINK);

			fileName.setData(entry);
			fileName.addListener(this);
			PropertyFileDescriptor translation = entry.getValue();
			int translated = translation == null ? 0 : translation.getKeys();
			StaticProgressIndicator progress = new ResolvableProgressIndicator(translation);
			
			int fuzzy = translation == null ? 0 : translation.getReviews().size();
			StaticProgressIndicator fuzzyIndicator = new StaticProgressIndicator();
			fuzzyIndicator.setValue(fuzzy);
			fuzzyIndicator.setInvertColors(true);
			int keys = entry.getKey().getKeys();
			int percentage = (int) ((fuzzy / (double) keys) * 100);
			percentage = Math.min(percentage, 100);
			// we can  have  multiple  reviews  per key
			fuzzyIndicator.setPercentage(percentage);
			
			table.addItem(new Object[] { fileName, keys, translated, fuzzyIndicator, progress }, entry);
		}

		table.setColumnHeaders(new String[] { Messages.getString("ProjectLocaleDashboard_LOCATION_COLUMN_HEADER"), Messages.getString("ProjectLocaleDashboard_TOTAL_KEYS_COLUMN_HEADER"), Messages.getString("ProjectLocaleDashboard_TRANSLATED_KEYS_COLUMN_HEADER"), Messages.getString("ProjectLocaleDashboard_FUZZY_KEYS_COLUMN_HEADER"), Messages.getString("ProjectLocaleDashboard_COMPLETION_COLUMN_HEADER") });
		table.setSortContainerPropertyId("location"); //$NON-NLS-1$
		section.addComponent(table);
		parent.addComponent(section);
		HorizontalLayout buttonBar = new HorizontalLayout();
		buttonBar.setSpacing(true);

		buttonBar.addComponent(new SaveToArchiveButton(locale));
		parent.addComponent(buttonBar);

	}

	private Map<PropertyFileDescriptor, PropertyFileDescriptor> associate(ProjectLocale locale) {
		ProjectLocale master = locale.getParent().getTemplate();
		Map<PropertyFileDescriptor, PropertyFileDescriptor> result = new HashMap<PropertyFileDescriptor, PropertyFileDescriptor>();
		for (PropertyFileDescriptor descriptor : master.getDescriptors()) {
			result.put(descriptor, null);
		}

		for (PropertyFileDescriptor descriptor : locale.getDescriptors()) {
			result.put(descriptor.getMaster(), descriptor);
		}
		return result;
	}

	private String buildSummary(Entry<PropertyFileDescriptor, PropertyFileDescriptor> entry) {

		PropertyFileDescriptor master = entry.getKey();
		PropertyFileDescriptor translated = entry.getValue();
		if (translated == null) {
			String message = "File missing. {0} strings to translate";
			return MessageFormat.format(message, master.getKeys());
		}
		int totalKeys = master.getKeys();
		int actualKeys = translated.getKeys();
		if (actualKeys == totalKeys) {
			return "Complete";
		} else if (actualKeys < totalKeys) {

			String message = "{0} out of {1} strings need attention";
			message = MessageFormat.format(message, totalKeys - actualKeys, totalKeys);
			return message;
		} else {

			String message = "Warning: Contains {0} keys more than the template file";
			message = MessageFormat.format(message, actualKeys - totalKeys);
			return message;
		}

	}

	private void buildHeader(Layout parent) {
		// TODO Auto-generated method stub

	}

	@Override
	public CrumbTrail walkTo(String path) {
		if (path.startsWith(SearchResultPage.SEARCH_ADDRESS)) {
			return new SearchResultPage(path.substring(SearchResultPage.SEARCH_ADDRESS.length()), locale);
		}
		return new PropertiesEditor(getDescriptor(path));
	}

	private PropertyFileDescriptor getDescriptor(String path) {
		EList<PropertyFileDescriptor> descriptors = locale.getDescriptors();
		for (PropertyFileDescriptor propertyFileDescriptor : descriptors) {
			if (path.equals(propertyFileDescriptor.getLocation().toString()))
				return propertyFileDescriptor;
		}
		return null;
	}

	@Override
	public String getTrailCaption() {
		return locale.isMaster() ? "Master" : locale.getLocale().getDisplayName(); //$NON-NLS-1$
	}

	@Override
	public void buttonClick(ClickEvent event) {
		final Entry<PropertyFileDescriptor, PropertyFileDescriptor> entry = (Entry<PropertyFileDescriptor, PropertyFileDescriptor>) event
				.getButton().getData();
		PropertyFileDescriptor target = entry.getValue();
		if (target == null) {
			// create a new one
			final PropertyFileDescriptor newTarget = PropertiesFactory.eINSTANCE.createPropertyFileDescriptor();
			target = newTarget;
			newTarget.setVariant(locale.getLocale());
			newTarget.setMaster(entry.getKey());
			newTarget.computeLocation();
			try {
				target = TransactionUtil.commit(locale, new Modification<ProjectLocale, PropertyFileDescriptor>() {
					@Override
					public PropertyFileDescriptor apply(ProjectLocale object) {

						object.getDescriptors().add(newTarget);
						return newTarget;
					}
				});
			} catch (CommitException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// change the icon
			event.getButton().setIcon(ImageConstants.IMAGE_PROPERTIES_FILE);
			entry.setValue(target);
		}
		MainDashboard.getCurrent().getBreadcrumbs().walkTo(target.getLocation().toString());

	}

	@Override
	public boolean isDirty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public CDOObject getDomainObject() {
		return locale;
	}

	@Override
	public Component createContents() {
		VerticalLayout layout = new VerticalLayout();
		layout.setMargin(true);
		layout.setSpacing(true);
		masterToTransation = associate(locale);
		createContents(layout);
		return layout;

	}

}

class SaveToArchiveButton extends Link {

	private ProjectLocale locale;

	public SaveToArchiveButton(ProjectLocale locale) {
		super();
		setCaption(Messages.getString("ProjectLocaleDashboard_DOWNLOAD_AS_ARCHIVE_BUTTON"));
		setIcon(ImageConstants.IMAGE_DOWNLOAD_ARCHIVE);
		setDescription(Messages.getString("ProjectLocaleDashboard_DOWNLOAD_AS_ARCHIVE_BUTTON_DESCRIPTION"));
		setTargetName("_blank"); //$NON-NLS-1$
		this.locale = locale;
	}

	@Override
	public void attach() {
		super.attach();

		StreamResource.StreamSource source = new StreamResource.StreamSource() {

			public InputStream getStream() {
				byte[] archive = createArchive();
				return new ByteArrayInputStream(archive);
			}

		};

		String name = locale.getParent().getParent().getName();
		name += "_"; //$NON-NLS-1$
		name += locale.getLocale().toString();
		name += ".zip"; //$NON-NLS-1$
		StreamResource resource = new StreamResource(source, name, getApplication());
		resource.setMIMEType("application/zip"); //$NON-NLS-1$
		resource.setCacheTime(0);
		setResource(resource);
	}

	private byte[] createArchive() {
		EList<PropertyFileDescriptor> descriptors = locale.getDescriptors();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ZipOutputStream zip = new ZipOutputStream(out);
		try {
			for (PropertyFileDescriptor descriptor : descriptors) {
				File file = new File(descriptor.absolutPath().toFileString());
				if (file.isFile()) {
					URI fullPath = descriptor.relativePath();
					String path = fullPath.path();
					if (path != null && path.startsWith("/")) //$NON-NLS-1$
						path = path.substring(1);
					zip.putNextEntry(new ZipEntry(path));
					store(file, zip);
				} else {
					// TODO: log?
				}

			}
			zip.closeEntry();
			zip.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return out.toByteArray();
	}

	private void store(File file, ZipOutputStream zip) throws IOException {
		InputStream in = new BufferedInputStream(new FileInputStream(file));
		byte[] buffer = new byte[1024];
		int read = 0;
		try {
			while (true) {
				read = in.read(buffer);
				if (read < 1)
					break;
				zip.write(buffer, 0, read);
			}
		} finally {
			if (in != null)
				in.close();
		}

	}
}

class LocationFilter implements Filter
{
	private String filter;

	public LocationFilter(String filter) {
		super();
		this.filter = filter;
	}

	@Override
	public boolean passesFilter(Object itemId, Item item) throws UnsupportedOperationException {
		Property property = item.getItemProperty("location"); //$NON-NLS-1$
		Object value = property.getValue();
		if (value instanceof Button) {
			Button button = (Button) value;
			String caption = button.getCaption();
			if(caption.contains(filter))
				return true;
		}
		return false;
	}

	@Override
	public boolean appliesToProperty(Object propertyId) {
		if(propertyId.equals("location")) //$NON-NLS-1$
			return true;
		return false;
	}
	
	
	
}
