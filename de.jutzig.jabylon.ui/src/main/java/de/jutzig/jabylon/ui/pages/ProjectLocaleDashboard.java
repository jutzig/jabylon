package de.jutzig.jabylon.ui.pages;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.eclipse.emf.cdo.CDOObject;
import org.eclipse.emf.cdo.util.CommitException;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;

import com.vaadin.terminal.DownloadStream;
import com.vaadin.terminal.StreamResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Link;
import com.vaadin.ui.Table;
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

public class ProjectLocaleDashboard implements CrumbTrail, ClickListener {

	private Project project;
	private ProjectLocale locale;
	Map<PropertyFileDescriptor, PropertyFileDescriptor> masterToTransation;

	public ProjectLocaleDashboard(ProjectLocale locale) {

		this.locale = locale;

	}

	private void createContents(Layout parent) {
		buildHeader(parent);
		Section section = new Section();
		section.setTitle("Translatable Files");
		section.setSizeFull();
		final Table table = new Table();
		table.setSizeFull();
		table.addContainerProperty("location", SortableButton.class, null);
		table.addContainerProperty("summary", String.class, "");
		table.addContainerProperty("progress", ResolvableProgressIndicator.class, null);
		table.setColumnWidth("progress", 110);
//		table.setColumnWidth(locale,400);
		for (Entry<PropertyFileDescriptor, PropertyFileDescriptor> entry : masterToTransation.entrySet()) {
			Button fileName = new SortableButton(entry.getKey().getLocation().toString());
			fileName.setIcon(entry.getValue()==null ? ImageConstants.IMAGE_NEW_PROPERTIES_FILE : ImageConstants.IMAGE_PROPERTIES_FILE);
			fileName.setStyleName(Reindeer.BUTTON_LINK);
			
			fileName.setData(entry);
			fileName.addListener(this);

			PropertyFileDescriptor translation = entry.getValue();
			StaticProgressIndicator progress = new ResolvableProgressIndicator(translation);
			
			table.addItem(new Object[] {fileName,buildSummary(entry),progress}, entry.getKey().cdoID());
		}
		table.setSortContainerPropertyId("location");
		section.getBody().addComponent(table);
		parent.addComponent(section);
		parent.addComponent(new SaveToArchiveButton(locale));

	}

	private Map<PropertyFileDescriptor, PropertyFileDescriptor> associate(ProjectLocale locale) {
		ProjectLocale master = locale.getProjectVersion().getMaster();
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
		return locale.isMaster() ? "Master" : locale.getLocale().getDisplayName();
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
		setCaption("Download Archive");
		setIcon(ImageConstants.IMAGE_DOWNLOAD_ARCHIVE);
		setDescription("Download all translated properties as a zip file");
		setTargetName("_blank");
		this.locale = locale;
	}

	@Override
	public void attach() {
		super.attach(); // Must call.

		StreamResource.StreamSource source = new StreamResource.StreamSource() {

			public InputStream getStream() {
				byte[] archive = createArchive();
				return new ByteArrayInputStream(archive);
			}

		};

		String name = locale.getProjectVersion().getProject().getName();
		name += "_";
		name += locale.getLocale().toString();
		name += ".zip";
		StreamResource resource = new StreamResource(source, name, getApplication());
		resource.setMIMEType("application/zip");
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
				if(file.isFile())
				{
					URI fullPath = descriptor.relativePath();
					zip.putNextEntry(new ZipEntry(fullPath.path()));
					store(file,zip);
				}
				else
				{
					//TODO: log?
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
			while(true)
			{
				read = in.read(buffer);
				if(read<1)
					break;
				zip.write(buffer,0,read);
			}
		}
		finally{
			if(in!=null)
				in.close();
		}
		
	}
}
