package de.jutzig.jabylon.ui.forms;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Locale;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.emf.cdo.util.CommitException;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;

import com.vaadin.data.Item;
import com.vaadin.data.Validator;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;
import com.vaadin.ui.Form;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import de.jutzig.jabylon.cdo.connector.Modification;
import de.jutzig.jabylon.cdo.connector.TransactionUtil;
import de.jutzig.jabylon.properties.Project;
import de.jutzig.jabylon.properties.ProjectLocale;
import de.jutzig.jabylon.properties.ProjectVersion;
import de.jutzig.jabylon.properties.PropertiesFactory;
import de.jutzig.jabylon.properties.PropertiesPackage;
import de.jutzig.jabylon.properties.Workspace;
import de.jutzig.jabylon.ui.applications.MainDashboard;
import de.jutzig.jabylon.ui.beans.ProjectBean;
import de.jutzig.jabylon.ui.components.ProgressMonitorDialog;
import de.jutzig.jabylon.ui.components.ProgressMonitorIndicator;
import de.jutzig.jabylon.ui.container.EObjectItem;
import de.jutzig.jabylon.ui.team.TeamProvider;
import de.jutzig.jabylon.ui.util.RunnableWithProgress;

public class NewProjectForm extends VerticalLayout {

	private Project project;
	private Workspace workspace;

	public NewProjectForm(final MainDashboard dashboard) {
		this.project = PropertiesFactory.eINSTANCE.createProject();
		EObjectItem item = new EObjectItem(project);
		this.workspace = dashboard.getWorkspace();
		setMargin(true);
		setSpacing(true);
		// Create the Form
		final Form projectForm = new Form();
		projectForm.setCaption("New Project");
		projectForm.setWriteThrough(true); // we want explicit 'apply'
		projectForm.setInvalidCommitted(false); // no invalid values in
												// datamodel

		// FieldFactory for customizing the fields and adding validators
		projectForm.setFormFieldFactory(new ProjectFieldFactory());
		ProjectBean projectBean = new ProjectBean();
		projectBean.setCheckoutImmediately(true);
		final BeanItem<ProjectBean> beanItem = new BeanItem<ProjectBean>(
				projectBean);
		projectForm.setItemDataSource(beanItem); // bind to POJO via BeanItem

		// Determines which properties are shown, and in which order:
		// projectForm.setVisibleItemProperties(Arrays.asList(new
		// EStructuralFeature[] {
		// PropertiesPackage.Literals.PROJECT__NAME}));

		projectForm.setVisibleItemProperties(new String[] { "name",
				"repositoryURI", "username", "password", "branch",
				"checkoutImmediately" });
		projectForm.setImmediate(true);

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
		buttons.addComponent(discardChanges);
		buttons.setComponentAlignment(discardChanges, Alignment.MIDDLE_LEFT);

		Button apply = new Button("Create", new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				try {
					if (!projectForm.isValid())
						return;
					project = TransactionUtil.commit(workspace,
							new Modification<Workspace, Project>() {

								@Override
								public Project apply(Workspace object) {

									ProjectBean bean = beanItem.getBean();
									project.setName(bean.getName());
									object.getProjects().add(project);
									if (bean.getRepositoryURI() != null
											&& bean.getRepositoryURI().length() > 0) {
										URI uri = (URI) PropertiesFactory.eINSTANCE
												.createFromString(
														PropertiesPackage.Literals.URI,
														bean.getRepositoryURI());
										if (bean.getUsername() != null
												&& bean.getUsername().length() > 0) {
											String userinfo = bean
													.getUsername();
											if (bean.getPassword() != null
													&& bean.getPassword()
															.length() > 0) {
												userinfo = userinfo + ":"
														+ bean.getPassword();
											}
											userinfo = userinfo + "@"; // userinfo
																		// separator
											uri = URI.createHierarchicalURI(
													uri.scheme(), userinfo
															+ uri.authority(),
													uri.device(),
													uri.segments(),
													uri.query(), uri.fragment());
										}
										project.setRepositoryURI(uri);
									}
									ProjectVersion master = PropertiesFactory.eINSTANCE
											.createProjectVersion();
									if (bean.getBranch() != null
											&& bean.getBranch().length() > 0)
										master.setBranch(bean.getBranch());
									project.setMaster(master);

									ProjectLocale masterLocale = PropertiesFactory.eINSTANCE
											.createProjectLocale();
									masterLocale.setLocale(Locale.ENGLISH);
									master.setMaster(masterLocale);

									return project;
								}
							});

					if (beanItem.getBean().isCheckoutImmediately()) {
						showProgressWindow();
					}

					MainDashboard.getCurrent().getBreadcrumbs()
							.walkTo(project.getName()); // TODO: should
														// prefix with
														// 'projects'
														// fragment, or so

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

	private void showProgressWindow() {

		ProgressMonitorDialog dialog = new ProgressMonitorDialog(getWindow());
		final TeamProvider provider = MainDashboard.getCurrent()
				.getTeamProviderForURI(project.getRepositoryURI());
		dialog.setCaption("Checking out...");
		dialog.run(true, new RunnableWithProgress() {

			@Override
			public void run(IProgressMonitor monitor) {
				try {
					final SubMonitor subMonitor = SubMonitor.convert(monitor);
					subMonitor.beginTask("Checkout", 100);
					
					provider.checkout(project.getMaster(), subMonitor.newChild(90));

					SubMonitor child = subMonitor.newChild(10);
					child.beginTask("Intial Scan", 1);
					TransactionUtil.commit(project.getMaster(),
							new Modification<ProjectVersion, ProjectVersion>() {
								@Override
								public ProjectVersion apply(
										ProjectVersion object) {
									object.fullScan();
									return object;
								}
							});
					child.worked(1);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (CommitException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				finally{
					monitor.done();
				}

			}
		});
	}

	private class ProjectFieldFactory extends DefaultFieldFactory {

		public ProjectFieldFactory() {

		}

		@Override
		public Field createField(Item item, Object propertyId,
				Component uiContext) {
			Field field = super.createField(item, propertyId, uiContext);
			field.setWidth("30em");
			if (field instanceof TextField) {
				TextField textField = (TextField) field;
				textField.setNullRepresentation("");

			}
			if ("name".equals(propertyId)) {
				field.addValidator(new ProjectNameValidator(workspace));
				field.setRequired(true);
				TextField tf = (TextField) field;

			} else if ("repositoryURI".equals(propertyId)) {
				TextField textField = (TextField) field;
				textField.setCaption("Repository URI");
				textField
						.setInputPrompt("https://github.com/example/example.git");
			} else if ("password".equals(propertyId)) {
				PasswordField passwordField = new PasswordField();
				passwordField.setCaption(createCaptionByPropertyId(propertyId));
				passwordField.setNullRepresentation("");
				field = passwordField;
			}

			return field;
		}

	}

}

class ProjectNameValidator implements Validator {

	private Workspace workspace;

	public ProjectNameValidator(Workspace workspace) {
		this.workspace = workspace;
	}

	@Override
	public void validate(Object value) throws InvalidValueException {
		if (!isValid(value)) {
			String message = "Project name ''{0}'' already exists";
			message = MessageFormat.format(message, value);
			throw new InvalidValueException(message);
		}

	}

	@Override
	public boolean isValid(Object value) {
		EList<Project> projects = workspace.getProjects();
		if (value == null)
			return false;
		for (Project project : projects) {
			if (value.equals(project.getName()))
				return false;
		}
		return true;
	}

}
