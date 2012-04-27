/**
 *
 */
package de.jutzig.jabylon.ui.config.internal.project;

import java.util.Arrays;

import org.eclipse.emf.common.util.URI;
import org.osgi.service.prefs.Preferences;

import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.Component;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;
import com.vaadin.ui.Form;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;

import de.jutzig.jabylon.properties.Project;
import de.jutzig.jabylon.properties.PropertiesFactory;
import de.jutzig.jabylon.properties.PropertiesPackage;
import de.jutzig.jabylon.ui.beans.ProjectBean;
import de.jutzig.jabylon.ui.config.AbstractConfigSection;
import de.jutzig.jabylon.ui.config.ConfigSection;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 *
 */
@SuppressWarnings("serial")
public class ProjectRepositorySettings extends AbstractConfigSection<Project> implements ConfigSection {

	private Form form;
	private BeanItem<ProjectBean> beanItem;

	/**
	 *
	 */
	public ProjectRepositorySettings() {
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.jutzig.jabylon.ui.config.ConfigSection#createContents()
	 */
	@Override
	public Component createContents() {
		form = new Form();
		form.setWriteThrough(true);
		form.setValidationVisible(true);
		form.setImmediate(true);
		form.setFormFieldFactory(new DefaultFieldFactory() {

			@Override
			public Field createField(Item item, Object propertyId, Component uiContext) {
				if (propertyId.equals("password")) {
					PasswordField field = new PasswordField();
					field.setCaption(createCaptionByPropertyId(propertyId));
					field.setColumns(30);
					return field;
				}
				Field field = super.createField(item, propertyId, uiContext);
				if(propertyId.equals("repositoryURI"))
				{
					((TextField)field).setInputPrompt("https://github.org/example.git");
					field.setCaption("Repository URI");
				}
				if (field instanceof TextField) {
					TextField text = (TextField) field;
					text.setNullRepresentation("");
					text.setColumns(30);
				}

				return field;
			}

		});
		return form;

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
		ProjectBean bean = beanItem.getBean();
		if (bean.getRepositoryURI() != null && bean.getRepositoryURI().length() > 0) {
			URI uri = (URI) PropertiesFactory.eINSTANCE.createFromString(PropertiesPackage.Literals.URI, bean.getRepositoryURI());
			if (bean.getUsername() != null && bean.getUsername().length() > 0) {
				String userinfo = bean.getUsername();
				if (bean.getPassword() != null && bean.getPassword().length() > 0) {
					userinfo = userinfo + ":" + bean.getPassword();
				}
				userinfo = userinfo + "@"; // userinfo separator
				uri = URI.createHierarchicalURI(uri.scheme(), userinfo + uri.authority(), uri.device(), uri.segments(), uri.query(),
						uri.fragment());
			}
			getDomainObject().setRepositoryURI(uri);
		}

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
		ProjectBean projectBean = new ProjectBean();
		parseURI(projectBean,getDomainObject().getRepositoryURI());
		beanItem = new BeanItem<ProjectBean>(projectBean);
		form.setItemDataSource(beanItem);
		form.setVisibleItemProperties(Arrays.asList("repositoryURI", "username", "password"));

	}

	private void parseURI(ProjectBean projectBean, URI repositoryURI) {
		if(repositoryURI==null)
			return;

		String userInfo = repositoryURI.userInfo();
		parserUserInfo(projectBean, userInfo);
		String authority = repositoryURI.authority();
		if(authority!=null && authority.contains("@"))
			authority = authority.substring(authority.lastIndexOf('@'));
		URI cleanURI = URI.createHierarchicalURI(repositoryURI.scheme(), authority, repositoryURI.device(), repositoryURI.segments(), repositoryURI.query(), repositoryURI.fragment());
		projectBean.setRepositoryURI(cleanURI.toString());
	}

	private void parserUserInfo(ProjectBean projectBean, String userInfo) {
		if(userInfo==null || userInfo.isEmpty())
			return;
		String[] split = userInfo.split(":");
		projectBean.setUsername(split[0]);
		if(split.length>0)
			projectBean.setPassword(split[1]);

	}

}
