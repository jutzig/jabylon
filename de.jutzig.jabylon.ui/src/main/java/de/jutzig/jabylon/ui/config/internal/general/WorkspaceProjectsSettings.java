/**
 * 
 */
package de.jutzig.jabylon.ui.config.internal.general;

import java.io.File;
import java.io.IOException;
import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.osgi.service.prefs.Preferences;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.ColumnGenerator;
import com.vaadin.ui.VerticalLayout;

import de.jutzig.jabylon.common.util.PreferencesUtil;
import de.jutzig.jabylon.properties.Project;
import de.jutzig.jabylon.properties.ProjectLocale;
import de.jutzig.jabylon.properties.ProjectVersion;
import de.jutzig.jabylon.properties.PropertiesFactory;
import de.jutzig.jabylon.properties.PropertiesPackage;
import de.jutzig.jabylon.properties.Resolvable;
import de.jutzig.jabylon.properties.Workspace;
import de.jutzig.jabylon.ui.components.EditableTable;
import de.jutzig.jabylon.ui.components.ResolvableProgressIndicator;
import de.jutzig.jabylon.ui.config.AbstractConfigSection;
import de.jutzig.jabylon.ui.config.ConfigSection;
import de.jutzig.jabylon.ui.container.GenericEObjectContainer;
import de.jutzig.jabylon.ui.styles.JabylonStyle;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 *
 */
public class WorkspaceProjectsSettings extends AbstractConfigSection<Workspace> implements ConfigSection {

	private EditableTable table;
	private Table projectTable;
	private Button createTerminology;

	/**
	 * 
	 */
	public WorkspaceProjectsSettings() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see de.jutzig.jabylon.ui.config.ConfigSection#createContents()
	 */
	@Override
	public Component createContents() {
//		EditableTable table = new EditableTable(true);
//		table.getTable().setContainerDataSource(new GenericEObjectContainer<Project>(getDomainObject(),PropertiesPackage.Literals.WORKSPACE__PROJECTS));
//		table.getTable().set
		VerticalLayout layout = new VerticalLayout();
		layout.setSpacing(true);
		layout.setSizeFull();
		table = new EditableTable(true) {
			@Override
			protected void addPressed() {
				Project project = PropertiesFactory.eINSTANCE.createProject();
				project.setName("<enter name>");
				ProjectVersion version = PropertiesFactory.eINSTANCE.createProjectVersion();
				version.setName("master");
				project.getChildren().add(version);
				getDomainObject().getChildren().add(project);
				ProjectLocale template = PropertiesFactory.eINSTANCE.createProjectLocale();
				version.getChildren().add(template);
				version.setTemplate(template);
				table.setEditable(true);
				projectTable.select(project);
				
			}
			
			@Override
			protected void removePressed() {
				Object value = projectTable.getValue();
				if (value instanceof Set) {
					Set selection = (Set)value;
					getDomainObject().getChildren().removeAll(selection);
				}
				else if (value instanceof Project) {
					Project project = (Project) value;
					getDomainObject().getChildren().remove(project);
					
				}
			}
		};
		projectTable = table.getTable();
		projectTable.addStyleName(JabylonStyle.TABLE_STRIPED.getCSSName());
		projectTable.setWidth(550, Table.UNITS_PIXELS);
//		projectTable.setRowHeaderMode(Table.ROW_HEADER_MODE_ICON_ONLY);
		projectTable.setSelectable(true);
		createTerminology =new Button("Create Terminology Project"); 
		createTerminology.setDisableOnClick(true);
		createTerminology.addListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				Workspace workspace = getDomainObject();
				Project project = PropertiesFactory.eINSTANCE.createProject();
				project.setTerminology(true);
				workspace.getChildren().add(project);
				project.setName("Terminology");
				ProjectVersion version = PropertiesFactory.eINSTANCE.createProjectVersion();
				version.setName("master");
				project.getChildren().add(version);
				
				ProjectLocale locale = PropertiesFactory.eINSTANCE.createProjectLocale();
				version.setTemplate(locale);
				version.getChildren().add(locale);
				URI path = version.absolutPath();
				File folder = new File(path.toFileString());
				folder.mkdirs();
				File properties = new File(folder,"messages.properties");
				try {
					properties.createNewFile();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				version.fullScan(PreferencesUtil.getScanConfigForProject(project));
			}
		});
		layout.addComponent(table);
		layout.addComponent(createTerminology);
		return layout;
	}

	/* (non-Javadoc)
	 * @see de.jutzig.jabylon.ui.config.ConfigSection#commit(org.osgi.service.prefs.Preferences)
	 */
	@Override
	public void commit(Preferences config) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see de.jutzig.jabylon.ui.config.AbstractConfigSection#init(org.osgi.service.prefs.Preferences)
	 */
	@Override
	protected void init(Preferences config) {
		projectTable.setContainerDataSource(new GenericEObjectContainer<Project>(getDomainObject(), PropertiesPackage.Literals.RESOLVABLE__CHILDREN));
		projectTable.setVisibleColumns(new Object[]{PropertiesPackage.Literals.RESOLVABLE__NAME});
		projectTable.setColumnHeader(PropertiesPackage.Literals.RESOLVABLE__NAME, "Projects");
		projectTable.addGeneratedColumn("progress", new ColumnGenerator() {
			
			@Override
			public Object generateCell(Table source, Object itemId, Object columnId) {
				return new ResolvableProgressIndicator((Resolvable) itemId);
			}
		});
		createTerminology.setEnabled(getDomainObject().getTerminology()==null);

	}

}
