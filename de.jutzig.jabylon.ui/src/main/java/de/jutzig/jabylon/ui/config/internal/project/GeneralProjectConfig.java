/**
 * 
 */
package de.jutzig.jabylon.ui.config.internal.project;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.osgi.service.prefs.Preferences;

import com.vaadin.data.Item;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;
import com.vaadin.ui.Form;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.NativeButton;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.ColumnGenerator;
import com.vaadin.ui.Window;

import de.jutzig.jabylon.properties.Project;
import de.jutzig.jabylon.properties.ProjectLocale;
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
import de.jutzig.jabylon.ui.container.ProjectLocaleTableContainer;
import de.jutzig.jabylon.ui.container.ProjectLocaleTableContainer.LocaleProperty;
import de.jutzig.jabylon.ui.forms.NewLocaleForm;
import de.jutzig.jabylon.ui.team.TeamProvider;
import de.jutzig.jabylon.ui.util.PreferencesUtil;
import de.jutzig.jabylon.ui.util.RunnableWithProgress;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 * 
 */
@SuppressWarnings("serial")
public class GeneralProjectConfig extends AbstractConfigSection<Project> implements ValueChangeListener{

	private Form form;
	private ProjectVersion version;
	private Table versionTable;
	private EditableTable table;
	private Table localeTable;
	private Component slaveTable;

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.jutzig.jabylon.ui.config.ConfigSection#createContents()
	 */
	@Override
	public Component createContents() {
		GridLayout layout = new GridLayout();
		layout.setColumns(2);
		layout.setRows(3);
		layout.setSpacing(true);
		form = new Form();
		form.setWriteThrough(false);
		form.setImmediate(true);
		layout.addComponent(form,0,0,1,0);

		Component table = createVersionTable();
		layout.addComponent(table);

		slaveTable = createLocalesTable();
		slaveTable.setVisible(false);
		layout.addComponent(slaveTable);
		return layout;

	}

	private Component createVersionTable() {
		final Section section = new Section();
		section.setTitle("Versions");
		section.setWidth(500, Section.UNITS_PIXELS);
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
					Set selection = (Set) value;
					getDomainObject().getVersions().removeAll(selection);
				} else if (value instanceof ProjectVersion) {
					ProjectVersion locale = (ProjectVersion) value;
					getDomainObject().getVersions().remove(locale);

				}
			}
		};
		
		versionTable = table.getTable();
		versionTable.setImmediate(true);
		versionTable.addListener(this);
		versionTable.setWidth(550, Table.UNITS_PIXELS);
		versionTable.setRowHeaderMode(Table.ROW_HEADER_MODE_ICON_ONLY);
		versionTable.setSelectable(true);

		section.getBody().addComponent(table);

		return section;
	}

	private Component createLocalesTable() {
		final Section section = new Section();
		section.setTitle("Locales");
		section.setWidth(400, Section.UNITS_PIXELS);
		GridLayout layout = section.getBody();
		layout.setSpacing(true);

		final EditableTable table = new EditableTable() {
			@Override
			protected void addPressed() {
				Window window = new Window();
				window.setContent(new NewLocaleForm(version));
				window.setModal(true);
				window.setWidth(300, Window.UNITS_PIXELS);
				window.setHeight(300, Window.UNITS_PIXELS);
				window.setCaption("Add new Locale");
				window.setName("Add new Locale");
				section.getWindow().addWindow(window);
			}

			@Override
			protected void removePressed() {
				Object value = localeTable.getValue();
				if (value instanceof Set) {
					Set selection = (Set) value;
					version.getLocales().removeAll(selection);
				} else if (value instanceof ProjectLocale) {
					ProjectLocale locale = (ProjectLocale) value;
					version.getLocales().remove(locale);

				}
			}
		};

		localeTable = table.getTable();
		localeTable.setWidth(300, Table.UNITS_PIXELS);
		localeTable.setRowHeaderMode(Table.ROW_HEADER_MODE_ICON_ONLY);
		localeTable.setMultiSelect(true);
		localeTable.setSelectable(true);
		layout.addComponent(table);

		return section;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.jutzig.jabylon.ui.config.ConfigSection#commit(org.osgi.service.prefs
	 * .Preferences)
	 */
	@Override
	public void commit(Preferences config) {
		String name = version.getProject().getName();
		form.commit();
		String newName = version.getProject().getName();
		if (!newName.equals(name)) {
			renameProject(name, newName);
		}
	}

	private void renameProject(String oldName, String newName) {
		URI uri = version.getProject().getWorkspace().absolutPath();
		File workspaceDir = new File(uri.toFileString());
		File projectDirectory = new File(workspaceDir, oldName);
		projectDirectory.renameTo(new File(workspaceDir, newName));

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.jutzig.jabylon.ui.config.AbstractConfigSection#init(org.osgi.service
	 * .prefs.Preferences)
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
				if (propertyId == PropertiesPackage.Literals.PROJECT__PROPERTY_TYPE) {
					NativeSelect select = new NativeSelect("File Type");
					select.addItem(PropertyType.ENCODED_ISO);
					select.addItem(PropertyType.UNICODE);
					select.setNewItemsAllowed(false);
					select.setNullSelectionAllowed(false);
					return select;
				}
				Field field = super.createField(item, propertyId, uiContext);
				EStructuralFeature feature = (EStructuralFeature) propertyId;
				field.setCaption(createCaptionByPropertyId(feature.getName()));
				return field;
			}
		});
		form.setVisibleItemProperties(new Object[] { PropertiesPackage.Literals.PROJECT__NAME,
				PropertiesPackage.Literals.PROJECT__PROPERTY_TYPE });

		versionTable.setContainerDataSource(new ProjectVersionContainer(getDomainObject()));

		versionTable.setVisibleColumns(new Object[] { PropertiesPackage.Literals.PROJECT_VERSION__BRANCH });

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
						final TeamProvider teamProvider = MainDashboard.getCurrent().getTeamProviderForURI(
								getDomainObject().getRepositoryURI());
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

		versionTable.setColumnHeaders(new String[] { "Branch", "Completeness", "Scan", "Checkout" });
		
		

		
	}

	@Override
	public void valueChange(ValueChangeEvent event) {
		if (event.getProperty().getValue() instanceof ProjectVersion) {
			version = (ProjectVersion) event.getProperty().getValue();
			localeTable.setContainerDataSource(new ProjectLocaleTableContainer(version));
			localeTable.setVisibleColumns(EnumSet.of(LocaleProperty.LOCALE).toArray());
			localeTable.setItemIconPropertyId(LocaleProperty.FLAG);
			slaveTable.setVisible(true);
		}
		else
			slaveTable.setVisible(false);
		
	}
}

class ProjectVersionContainer extends GenericEObjectContainer<ProjectVersion> {

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
		return project.getMaster() == itemId || project.getVersions().contains(itemId);
	}

	@Override
	public int size() {
		return super.size() + 1; // one more because we include the master
									// version as well
	}

}
