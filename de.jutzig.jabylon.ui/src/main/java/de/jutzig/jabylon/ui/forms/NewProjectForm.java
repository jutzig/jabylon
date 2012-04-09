package de.jutzig.jabylon.ui.forms;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Locale;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EStructuralFeature;

import com.vaadin.data.Item;
import com.vaadin.data.Validator;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;
import com.vaadin.ui.Form;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.BaseTheme;

import de.jutzig.jabylon.cdo.connector.Modification;
import de.jutzig.jabylon.cdo.connector.TransactionUtil;
import de.jutzig.jabylon.properties.Project;
import de.jutzig.jabylon.properties.ProjectLocale;
import de.jutzig.jabylon.properties.ProjectVersion;
import de.jutzig.jabylon.properties.PropertiesFactory;
import de.jutzig.jabylon.properties.PropertiesPackage;
import de.jutzig.jabylon.properties.Workspace;
import de.jutzig.jabylon.ui.applications.MainDashboard;
import de.jutzig.jabylon.ui.container.EObjectItem;
import de.jutzig.jabylon.ui.pages.ProjectDashboard;

public class NewProjectForm extends VerticalLayout {

	private Project project;
	private Workspace workspace;
	
	public NewProjectForm(final MainDashboard dashboard) {
		this.project = PropertiesFactory.eINSTANCE.createProject();
		EObjectItem item = new EObjectItem(project);
		this.workspace = dashboard.getWorkspace();
		
	    // Create the Form
        final Form projectForm = new Form();
        projectForm.setCaption("New Project");
        projectForm.setWriteThrough(false); // we want explicit 'apply'
        projectForm.setInvalidCommitted(false); // no invalid values in datamodel

        // FieldFactory for customizing the fields and adding validators
        projectForm.setFormFieldFactory(new ProjectFieldFactory());
        projectForm.setItemDataSource(item); // bind to POJO via BeanItem

        // Determines which properties are shown, and in which order:
        projectForm.setVisibleItemProperties(Arrays.asList(new EStructuralFeature[] {
                PropertiesPackage.Literals.PROJECT__NAME}));

        // Add form to layout
        addComponent(projectForm);

        // The cancel / apply buttons
        HorizontalLayout buttons = new HorizontalLayout();
        buttons.setSpacing(true);
        Button discardChanges = new Button("Cancel",
                new Button.ClickListener() {
                    public void buttonClick(ClickEvent event) {
                        projectForm.discard();
                    }
                });
        discardChanges.setStyleName(BaseTheme.BUTTON_LINK);
        buttons.addComponent(discardChanges);
        buttons.setComponentAlignment(discardChanges, Alignment.MIDDLE_LEFT);

        Button apply = new Button("Create", new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
                try {
//                	CDOSession session = workspace.cdoView().getSession();
//                	CDOTransaction transaction = session.openTransaction();
//                	Workspace writeableWorkspace = (Workspace) transaction.getObject(workspace.cdoID());
                    projectForm.commit();
//                    writeableWorkspace.getProjects().add(EcoreUtil.copy(project)); //a copy so our project doesn't get stale. Not sure why we need this...
//                    transaction.commit();
//                    transaction.close();
                	project = TransactionUtil.commit(workspace, new Modification<Workspace, Project>() {
                		
                		@Override
                		public Project apply(Workspace object) {
                			object.getProjects().add(project);
                			ProjectVersion master = PropertiesFactory.eINSTANCE.createProjectVersion();
                			project.setMaster(master);
                			
                			ProjectLocale masterLocale = PropertiesFactory.eINSTANCE.createProjectLocale();
                			masterLocale.setLocale(Locale.ENGLISH);
                			master.setMaster(masterLocale);
                			
                			return project;
                		}
					});
                		
					MainDashboard.getCurrent().getBreadcrumbs().walkTo(project.getName()); //TODO: should prefix with 'projects' fragment, or so
//                    dashboard.setMainComponent(new ProjectDashboard(project));
                    
                } catch (Exception e) {
                    // Ignored, we'll let the Form handle the errors
                	e.printStackTrace();
                }
            }
        });
        buttons.addComponent(apply);
        projectForm.getFooter().addComponent(buttons);
        projectForm.getFooter().setMargin(false, false, true, true);

	}
	
	private class ProjectFieldFactory extends DefaultFieldFactory {


        public ProjectFieldFactory() {

        }

        @Override
        public Field createField(Item item, Object propertyId,
                Component uiContext) {
        	EStructuralFeature feature = ((EStructuralFeature)propertyId);
            Class<?> type = item.getItemProperty(propertyId).getType();
            Field field = createFieldByPropertyType(type);
            field.addValidator(new ProjectNameValidator(workspace));
            field.setCaption(createCaptionByPropertyId(feature.getName()));
            if (PropertiesPackage.Literals.PROJECT__NAME==propertyId) {
                TextField tf = (TextField) field;
                tf.setWidth("20em");
            }

            return field;
        }

    }
	
}

class ProjectNameValidator implements Validator
{

	private Workspace workspace;
	
	public ProjectNameValidator(Workspace workspace) {
		this.workspace = workspace;
	}

	@Override
	public void validate(Object value) throws InvalidValueException {
		if(!isValid(value))
		{
			String message = "Project name ''{0}'' already exists";
			message = MessageFormat.format(message, value);
			throw new InvalidValueException(message);
		}
		
	}

	@Override
	public boolean isValid(Object value) {
		EList<Project> projects = workspace.getProjects();
		if(value==null)
			return false;
		for (Project project : projects) {
			if(value.equals(project.getName()))
				return false;
		}
		return true;
	}
	
}
