/**
 * 
 */
package de.jutzig.jabylon.ui.config.internal.project;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.osgi.service.prefs.Preferences;

import com.vaadin.data.Item;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;
import com.vaadin.ui.Form;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.NativeButton;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.Table;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Table.ColumnGenerator;
import com.vaadin.ui.VerticalLayout;

import de.jutzig.jabylon.properties.Project;
import de.jutzig.jabylon.properties.ProjectVersion;
import de.jutzig.jabylon.properties.PropertiesFactory;
import de.jutzig.jabylon.properties.PropertiesPackage;
import de.jutzig.jabylon.properties.PropertyType;
import de.jutzig.jabylon.properties.Resolvable;
import de.jutzig.jabylon.ui.applications.MainDashboard;
import de.jutzig.jabylon.ui.components.EditableTable;
import de.jutzig.jabylon.ui.components.ProgressMonitorDialog;
import de.jutzig.jabylon.ui.components.ResolvableProgressIndicator;
import de.jutzig.jabylon.ui.components.Section;
import de.jutzig.jabylon.ui.config.AbstractConfigSection;
import de.jutzig.jabylon.ui.container.EObjectItem;
import de.jutzig.jabylon.ui.container.GenericEObjectContainer;
import de.jutzig.jabylon.ui.team.TeamProvider;
import de.jutzig.jabylon.ui.util.PreferencesUtil;
import de.jutzig.jabylon.ui.util.RunnableWithProgress;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 *
 */
public class GeneralProjectConfig extends AbstractConfigSection<Project> {

	private Form form;
	private ProjectVersion version;
	private Table versionTable;
	private EditableTable table;

	/* (non-Javadoc)
	 * @see de.jutzig.jabylon.ui.config.ConfigSection#createContents()
	 */
	@Override
	public Component createContents() {
		VerticalLayout layout = new VerticalLayout();
		layout.setSpacing(true);
		form = new Form();
		form.setWriteThrough(false);
		form.setImmediate(true);
		layout.addComponent(form);
		
		Component table = createVersionTable();
		layout.addComponent(table);	
		return layout;
		
	}

	private Component createVersionTable() {
		final Section section = new Section();
		section.setTitle("Versions");
		section.setWidth(630, Section.UNITS_PIXELS);
		GridLayout layout = section.getBody();
		layout.setSpacing(true);
		table = new EditableTable(true) {
			@Override
			protected void addPressed() {
				ProjectVersion projectVersion = PropertiesFactory.eINSTANCE.createProjectVersion();
				projectVersion.setBranch("<branch>");
				getDomainObject().getVersions().add(projectVersion);
				table.setEditable(true);
				versionTable.select(projectVersion);
				
			}
			
			@Override
			protected void removePressed() {
				Object value = versionTable.getValue();
				if (value instanceof Set) {
					Set selection = (Set)value;
					getDomainObject().getVersions().removeAll(selection);
				}
				else if (value instanceof ProjectVersion) {
					ProjectVersion locale = (ProjectVersion) value;
					getDomainObject().getVersions().remove(locale);
					
				}
			}
		};
		versionTable = table.getTable();
		versionTable.setWidth(550, Table.UNITS_PIXELS);
		versionTable.setRowHeaderMode(Table.ROW_HEADER_MODE_ICON_ONLY);
		versionTable.setSelectable(true);
		
		section.getBody().addComponent(table);
		return section;
	}


	/* (non-Javadoc)
	 * @see de.jutzig.jabylon.ui.config.ConfigSection#commit(org.osgi.service.prefs.Preferences)
	 */
	@Override
	public void commit(Preferences config) {
		String name = version.getProject().getName();
		form.commit();
		String newName = version.getProject().getName();
		if(!newName.equals(name))
		{
			renameProject(name,newName);
		}
	}

	private void renameProject(String oldName, String newName) {
		URI uri = version.getProject().getWorkspace().absolutPath();
		File workspaceDir = new File(uri.toFileString());
		File projectDirectory = new File(workspaceDir,oldName);
		projectDirectory.renameTo(new File(workspaceDir, newName));
		
	}

	/* (non-Javadoc)
	 * @see de.jutzig.jabylon.ui.config.AbstractConfigSection#init(org.osgi.service.prefs.Preferences)
	 */
	@Override
	protected void init(Preferences config) {
		Project object = getDomainObject();
		version = object.getMaster();
		EObjectItem item = new EObjectItem(object);
		form.setItemDataSource(item);
			
		form.setFormFieldFactory(new DefaultFieldFactory() {
			
			@Override
			public Field createField(Item item, Object propertyId, Component uiContext) {
				if(propertyId==PropertiesPackage.Literals.PROJECT__PROPERTY_TYPE)
				{
					NativeSelect select = new NativeSelect("File Type");
					select.addItem(PropertyType.ENCODED_ISO);
					select.addItem(PropertyType.UNICODE);
					select.setNewItemsAllowed(false);
					select.setNullSelectionAllowed(false);
					return select;
				}
				Field field = super.createField(item, propertyId, uiContext);
				EStructuralFeature feature = (EStructuralFeature)propertyId;
				field.setCaption(createCaptionByPropertyId(feature.getName()));
				return field;
			}
		});
		form.setVisibleItemProperties(new Object[]{PropertiesPackage.Literals.PROJECT__NAME,PropertiesPackage.Literals.PROJECT__PROPERTY_TYPE});
	
		
		versionTable.setContainerDataSource(new ProjectVersionContainer(getDomainObject()));

		versionTable.setVisibleColumns(new Object[]{PropertiesPackage.Literals.PROJECT_VERSION__BRANCH});
		
		versionTable.addGeneratedColumn("progress", new ColumnGenerator() {
			
			@Override
			public Object generateCell(Table source, Object itemId, Object columnId) {
				return new ResolvableProgressIndicator((Resolvable) itemId);
			}
		});
		
		versionTable.addGeneratedColumn("scan", new ColumnGenerator() {
			
			@Override
			public Object generateCell(Table source, Object itemId, Object columnId) {
				final ProjectVersion version = (ProjectVersion) itemId;
				NativeButton button = new NativeButton("scan");
				button.addListener(new ClickListener() {
					
					@Override
					public void buttonClick(ClickEvent event) {
						version.fullScan(PreferencesUtil.getScanConfigForProject(getDomainObject()));
						table.getWindow().showNotification("Scan complete");
					}
				});
				return button;
			}
		});
		versionTable.addGeneratedColumn("checkout", new ColumnGenerator() {
			
			@Override
			public Object generateCell(Table source, Object itemId, Object columnId) {
				final ProjectVersion version = (ProjectVersion) itemId;
				final NativeButton button = new NativeButton("checkout");
				File directory = new File(version.absolutPath().toFileString());
				button.setEnabled(!directory.exists() && getDomainObject().eIsSet(PropertiesPackage.Literals.PROJECT__REPOSITORY_URI));
				button.addListener(new ClickListener() {
					
					@Override
					public void buttonClick(ClickEvent event) {
						
						ProgressMonitorDialog dialog = new ProgressMonitorDialog(table.getWindow());
						final TeamProvider teamProvider = MainDashboard.getCurrent().getTeamProviderForURI(getDomainObject().getRepositoryURI());
						dialog.run(true, new RunnableWithProgress() {
							
							@Override
							public void run(IProgressMonitor monitor) {
								try {
									teamProvider.checkout(version, monitor);
									button.setEnabled(false);
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						});
					}
				});
				return button;
			}
		});
		
		versionTable.setColumnHeaders(new String[]{"Branch","Completeness","Scan","Checkout"});
	}
}

class ProjectVersionContainer extends GenericEObjectContainer<ProjectVersion>
{
	
	private Project project;

	public ProjectVersionContainer(Project parent) {
		super(parent, PropertiesPackage.Literals.PROJECT__VERSIONS);
		this.project = parent;
	}
	
	@Override
	protected List<ProjectVersion> getAllItemIds() {
		List<ProjectVersion> versions = new ArrayList<ProjectVersion>();
		versions.add(project.getMaster());
		versions.addAll(project.getVersions());
		return versions;
	}
	
	@Override
	public boolean containsId(Object itemId) {
		return project.getMaster()==itemId || project.getVersions().contains(itemId);
	}
	
	@Override
	public int size() {
		return super.size() +1; //one more because we include the master version as well
	}
	
}


