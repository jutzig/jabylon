/**
 * 
 */
package de.jutzig.jabylon.ui.config.internal.general;

import java.util.Set;

import org.osgi.service.prefs.Preferences;

import com.vaadin.ui.Component;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.ColumnGenerator;

import de.jutzig.jabylon.properties.Project;
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

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 *
 */
public class WorkspaceProjectsSettings extends AbstractConfigSection<Workspace> implements ConfigSection {

	private EditableTable table;
	private Table projectTable;

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
		
		table = new EditableTable(true) {
			@Override
			protected void addPressed() {
				Project project = PropertiesFactory.eINSTANCE.createProject();
				project.setName("<enter name>");
				ProjectVersion version = PropertiesFactory.eINSTANCE.createProjectVersion();
				version.setBranch("master");
				project.setMaster(version);
				getDomainObject().getProjects().add(project);
				table.setEditable(true);
				projectTable.select(project);
				
			}
			
			@Override
			protected void removePressed() {
				Object value = projectTable.getValue();
				if (value instanceof Set) {
					Set selection = (Set)value;
					getDomainObject().getProjects().removeAll(selection);
				}
				else if (value instanceof Project) {
					Project project = (Project) value;
					getDomainObject().getProjects().remove(project);
					
				}
			}
		};
		projectTable = table.getTable();
		projectTable.setWidth(550, Table.UNITS_PIXELS);
//		projectTable.setRowHeaderMode(Table.ROW_HEADER_MODE_ICON_ONLY);
		projectTable.setSelectable(true);
		
		
		return table;
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
		projectTable.setContainerDataSource(new GenericEObjectContainer<Project>(getDomainObject(), PropertiesPackage.Literals.WORKSPACE__PROJECTS));
		projectTable.setVisibleColumns(new Object[]{PropertiesPackage.Literals.PROJECT__NAME});
		projectTable.setColumnHeader(PropertiesPackage.Literals.PROJECT__NAME, "Projects");
		projectTable.addGeneratedColumn("progress", new ColumnGenerator() {
			
			@Override
			public Object generateCell(Table source, Object itemId, Object columnId) {
				return new ResolvableProgressIndicator((Resolvable) itemId);
			}
		});

	}

}
