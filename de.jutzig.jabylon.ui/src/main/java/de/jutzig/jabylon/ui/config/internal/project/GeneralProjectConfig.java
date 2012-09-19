/**
 * 
 */
package de.jutzig.jabylon.ui.config.internal.project;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;
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

import de.jutzig.jabylon.common.util.PreferencesUtil;
import de.jutzig.jabylon.properties.Project;
import de.jutzig.jabylon.properties.ProjectLocale;
import de.jutzig.jabylon.properties.ProjectVersion;
import de.jutzig.jabylon.properties.PropertiesFactory;
import de.jutzig.jabylon.properties.PropertiesPackage;
import de.jutzig.jabylon.properties.PropertyFileDiff;
import de.jutzig.jabylon.properties.PropertyType;
import de.jutzig.jabylon.properties.Resolvable;
import de.jutzig.jabylon.ui.Activator;
import de.jutzig.jabylon.ui.components.EditableTable;
import de.jutzig.jabylon.ui.components.ProgressMonitorDialog;
import de.jutzig.jabylon.ui.components.ResolvableProgressIndicator;
import de.jutzig.jabylon.ui.config.AbstractConfigSection;
import de.jutzig.jabylon.ui.container.EObjectItem;
import de.jutzig.jabylon.ui.container.GenericEObjectContainer;
import de.jutzig.jabylon.ui.container.ProjectLocaleTableContainer;
import de.jutzig.jabylon.ui.container.ProjectLocaleTableContainer.LocaleProperty;
import de.jutzig.jabylon.ui.forms.NewLocaleForm;
import de.jutzig.jabylon.ui.styles.JabylonStyle;
import de.jutzig.jabylon.ui.team.TeamProvider;
import de.jutzig.jabylon.ui.util.RunnableWithProgress;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 * 
 */
@SuppressWarnings("serial")
public class GeneralProjectConfig extends AbstractConfigSection<Project> implements ValueChangeListener {

	private Form form;
	private Table versionTable;
	private EditableTable table;
	private Table localeTable;
	private Component slaveTable;
	private String projectName;
	private ProjectVersion version;

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.jutzig.jabylon.ui.config.ConfigSection#createContents()
	 */
	@Override
	public Component createContents() {
		GridLayout layout = new GridLayout();
		layout.setSizeFull();
		layout.setColumns(2);
		layout.setRows(3);
		layout.setSpacing(true);
		form = new Form();
		form.setWriteThrough(true);
		form.setImmediate(true);
		layout.addComponent(form, 0, 0, 1, 0);
		layout.setRowExpandRatio(0, 1);
		layout.setRowExpandRatio(1, 1);
		layout.setRowExpandRatio(2, 1);
		layout.setColumnExpandRatio(0, 1);
		layout.setColumnExpandRatio(1, 1);
		Component table = createVersionTable();
		layout.addComponent(table);

		slaveTable = createLocalesTable();
		slaveTable.setVisible(false);
		layout.addComponent(slaveTable);
		return layout;

	}

	private Component createVersionTable() {
		table = new EditableTable(true) {
			@Override
			protected void addPressed() {
				ProjectVersion projectVersion = PropertiesFactory.eINSTANCE.createProjectVersion();
				projectVersion.setName(Messages.getString("GeneralProjectConfig_NEW_BRANCH_PROMPT"));
				getDomainObject().getChildren().add(projectVersion);
				table.setEditable(true);
				versionTable.select(projectVersion);

			}

			@Override
			protected void removePressed() {
				Object value = versionTable.getValue();
				if (value instanceof Set) {
					Set selection = (Set) value;
					getDomainObject().getChildren().removeAll(selection);
				} else if (value instanceof ProjectVersion) {
					ProjectVersion locale = (ProjectVersion) value;
					getDomainObject().getChildren().remove(locale);

				}
			}
		};

		versionTable = table.getTable();
		versionTable.setImmediate(true);
		versionTable.addListener(this);
		versionTable.setRowHeaderMode(Table.ROW_HEADER_MODE_ICON_ONLY);
		versionTable.addStyleName(JabylonStyle.TABLE_STRIPED.getCSSName());
		versionTable.setSelectable(true);

		// layout.addComponent(table);
		// layout.setExpandRatio(table, 1);
		return table;
	}

	private Component createLocalesTable() {
		// final Section section = new Section();
		// section.setTitle("Locales");
		// VerticalLayout layout = section.getBody();
		// layout.setSpacing(true);

		final EditableTable table = new EditableTable() {
			@Override
			protected void addPressed() {
				Window window = new Window();
				window.setContent(new NewLocaleForm(version));
				window.setModal(true);
				window.setWidth(300, Window.UNITS_PIXELS);
				window.setHeight(300, Window.UNITS_PIXELS);
				window.setCaption(Messages.getString("GeneralProjectConfig_NEW_LOCALE_DIALOG_TITLE"));
				window.setName("Add new Locale"); //$NON-NLS-1$
				form.getWindow().addWindow(window);
			}

			@Override
			protected void removePressed() {
				Object value = localeTable.getValue();
				if (value instanceof Set) {
					Set selection = (Set) value;
					version.getChildren().removeAll(selection);
				} else if (value instanceof ProjectLocale) {
					ProjectLocale locale = (ProjectLocale) value;
					version.getChildren().remove(locale);

				}
			}
		};

		localeTable = table.getTable();
		localeTable.addStyleName(JabylonStyle.TABLE_STRIPED.getCSSName());
		localeTable.setMultiSelect(true);
		localeTable.setSelectable(true);
		// layout.addComponent(table);

		return table;
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
		form.commit();
		String newName = getDomainObject().getName();
		if (!newName.equals(projectName)) {
			renameProject(projectName, newName);
		}
	}

	private void renameProject(String oldName, String newName) {
		URI uri = getDomainObject().getParent().absolutPath();
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
		final Project object = getDomainObject();
		projectName = object.getName();
//		version = object.getChildren().get(0);
		EObjectItem item = new EObjectItem(object);
		form.setItemDataSource(item);

		form.setFormFieldFactory(new DefaultFieldFactory() {

			@Override
			public Field createField(Item item, Object propertyId, Component uiContext) {
				if (propertyId == PropertiesPackage.Literals.PROJECT__PROPERTY_TYPE) {
					NativeSelect select = new NativeSelect(Messages.getString("GeneralProjectConfig_PROPERTY_TYPE_CHOICE_CAPTION"));
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
		form.setVisibleItemProperties(new Object[] { PropertiesPackage.Literals.RESOLVABLE__NAME,
				PropertiesPackage.Literals.PROJECT__PROPERTY_TYPE });

		versionTable.setContainerDataSource(new ProjectVersionContainer(getDomainObject()));

		versionTable.setVisibleColumns(new Object[] { PropertiesPackage.Literals.RESOLVABLE__NAME });

		versionTable.addGeneratedColumn(Messages.getString("GeneralProjectConfig_PROGRESS_COLUMN_CAPTION"), new ColumnGenerator() {

			@Override
			public Object generateCell(Table source, Object itemId, Object columnId) {
				return new ResolvableProgressIndicator((Resolvable) itemId);
			}
		});

		versionTable.addGeneratedColumn(Messages.getString("GeneralProjectConfig_SCAN_COLUMN_HEADER"), new ColumnGenerator() {

			@Override
			public Object generateCell(Table source, Object itemId, Object columnId) {
				final ProjectVersion version = (ProjectVersion) itemId;
				NativeButton button = new NativeButton(Messages.getString("GeneralProjectConfig_SCAN_BUTTON_CAPTION"));
				button.addListener(new ClickListener() {

					@Override
					public void buttonClick(ClickEvent event) {
						version.fullScan(PreferencesUtil.getScanConfigForProject(getDomainObject()));
						table.getWindow().showNotification(Messages.getString("GeneralProjectConfig_SCAN_COMPLETE_NOTIFICATION"));
					}
				});
				return button;
			}
		});
		versionTable.addGeneratedColumn(Messages.getString("GeneralProjectConfig_CHECKOUT_COLUMN_HEADER"), new ColumnGenerator() {

			@Override
			public Object generateCell(Table source, Object itemId, Object columnId) {
				final ProjectVersion version = (ProjectVersion) itemId;
				final NativeButton button = new NativeButton(Messages.getString("GeneralProjectConfig_CHECKOUT_BUTTON"));
				File directory = new File(version.absolutPath().toFileString());
				button.setEnabled(!directory.exists() && getDomainObject().eIsSet(PropertiesPackage.Literals.PROJECT__REPOSITORY_URI));
				button.addListener(new ClickListener() {

					@Override
					public void buttonClick(ClickEvent event) {

						ProgressMonitorDialog dialog = new ProgressMonitorDialog(table.getWindow());
						dialog.setCaption(Messages.getString("GeneralProjectConfig_CHECKOUT_DIALOG_TITLE"));
						final TeamProvider teamProvider = Activator.getDefault().getTeamProviderFor(getDomainObject());
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
		
		versionTable.addGeneratedColumn("Update", new ColumnGenerator() {

			@Override
			public Object generateCell(Table source, Object itemId, Object columnId) {
				final ProjectVersion version = (ProjectVersion) itemId;
				final NativeButton button = new NativeButton("Update");
				File directory = new File(version.absolutPath().toFileString());
				button.setEnabled(getDomainObject().eIsSet(PropertiesPackage.Literals.PROJECT__REPOSITORY_URI));
				button.addListener(new ClickListener() {

					@Override
					public void buttonClick(ClickEvent event) {

						ProgressMonitorDialog dialog = new ProgressMonitorDialog(table.getWindow());
						dialog.setCaption("Update");
						final TeamProvider teamProvider = Activator.getDefault().getTeamProviderFor(getDomainObject());
						dialog.run(true, new RunnableWithProgress() {

							@Override
							public void run(IProgressMonitor monitor) {
								try {
									SubMonitor subMonitor = SubMonitor.convert(monitor,"Updating",100);
									Collection<PropertyFileDiff> updates = teamProvider.update(version, subMonitor.newChild(80));
									subMonitor.setWorkRemaining(updates.size());
									subMonitor.subTask("Processing updates");
									for (PropertyFileDiff updatedFile : updates) {
										version.partialScan(PreferencesUtil.getScanConfigForProject(object), updatedFile);
										subMonitor.worked(1);
									}
									
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}finally{
									monitor.done();
								}
							}
						});
					}
				});
				return button;
			}
		});
		
		
		versionTable.addGeneratedColumn("Commit", new ColumnGenerator() {

			@Override
			public Object generateCell(Table source, Object itemId, Object columnId) {
				final ProjectVersion version = (ProjectVersion) itemId;
				final NativeButton button = new NativeButton("Commit");
				File directory = new File(version.absolutPath().toFileString());
				button.setEnabled(getDomainObject().eIsSet(PropertiesPackage.Literals.PROJECT__REPOSITORY_URI));
				button.addListener(new ClickListener() {

					@Override
					public void buttonClick(ClickEvent event) {

						ProgressMonitorDialog dialog = new ProgressMonitorDialog(table.getWindow());
						dialog.setCaption("Commit");
						final TeamProvider teamProvider = Activator.getDefault().getTeamProviderFor(getDomainObject());
						dialog.run(true, new RunnableWithProgress() {

							@Override
							public void run(IProgressMonitor monitor) {
								try {
									teamProvider.commit(version, monitor);
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}finally{
									monitor.done();
								}
							}
						});
					}
				});
				return button;
			}
		});

		versionTable.setColumnHeaders(new String[] { Messages.getString("GeneralProjectConfig_BRANCH_COLUMN_HEADER"),
				Messages.getString("GeneralProjectConfig_COMPLETION_COLUMN_HEADER"),
				Messages.getString("GeneralProjectConfig_SCAN_COLUMN_HEADER"),
				Messages.getString("GeneralProjectConfig_CHECKOUT_COLUMN_HEADER"), "Update", "Commit" });

	}

	@Override
	public void valueChange(ValueChangeEvent event) {
		if (event.getProperty().getValue() instanceof ProjectVersion) {
			version = (ProjectVersion) event.getProperty().getValue();
			localeTable.setContainerDataSource(new ProjectLocaleTableContainer(version));
			localeTable.setVisibleColumns(EnumSet.of(LocaleProperty.LOCALE).toArray());
			localeTable.setItemIconPropertyId(LocaleProperty.FLAG);
			localeTable.setRowHeaderMode(Table.ROW_HEADER_MODE_ICON_ONLY);
			slaveTable.setVisible(true);
		} else
			slaveTable.setVisible(false);

	}
}

class ProjectVersionContainer extends GenericEObjectContainer<ProjectVersion> {

	private Project project;

	public ProjectVersionContainer(Project parent) {
		super(parent, PropertiesPackage.Literals.RESOLVABLE__CHILDREN);
		this.project = parent;
	}

	@Override
	protected List<ProjectVersion> getAllItemIds() {
		List<ProjectVersion> versions = new ArrayList<ProjectVersion>();
		versions.addAll(project.getChildren());
		return versions;
	}

	@Override
	public boolean containsId(Object itemId) {
		return project.getChildren().contains(itemId);
	}

	@Override
	public int size() {
		return super.size();
	}

}
