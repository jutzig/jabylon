/**
 *
 */
package de.jutzig.jabylon.rest.ui.wicket.config;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.authorization.UnauthorizedInstantiationException;
import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.StatelessForm;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.string.StringValue;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.emf.cdo.CDOObject;
import org.eclipse.emf.cdo.CDOState;
import org.eclipse.emf.cdo.transaction.CDOTransaction;
import org.eclipse.emf.cdo.util.CommitException;
import org.eclipse.emf.cdo.view.CDOView;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;
import org.osgi.service.prefs.BackingStoreException;
import org.osgi.service.prefs.Preferences;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ArrayListMultimap;

import de.jutzig.jabylon.common.util.AttachablePreferences;
import de.jutzig.jabylon.common.util.DelegatingPreferences;
import de.jutzig.jabylon.common.util.PreferencesUtil;
import de.jutzig.jabylon.common.util.config.DynamicConfigUtil;
import de.jutzig.jabylon.properties.PropertiesPackage;
import de.jutzig.jabylon.properties.Resolvable;
import de.jutzig.jabylon.rest.ui.model.AttachableModel;
import de.jutzig.jabylon.rest.ui.model.AttachableWritableModel;
import de.jutzig.jabylon.rest.ui.security.CDOAuthenticatedSession;
import de.jutzig.jabylon.rest.ui.security.LoginPage;
import de.jutzig.jabylon.rest.ui.util.WicketUtil;
import de.jutzig.jabylon.rest.ui.wicket.components.ClientSideTabbedPanel;
import de.jutzig.jabylon.security.CommonPermissions;
import de.jutzig.jabylon.users.User;

/**
 * @author jutzig.dev@googlemail.com
 *
 */
public class SettingsPanel<T extends CDOObject> extends GenericPanel<T> {

    public static final String QUERY_PARAM_NAMESPACE = "namespace";

    private static final long serialVersionUID = 1L;

    public static final String QUERY_PARAM_CREATE = "create";

    private static final Logger logger = LoggerFactory.getLogger(SettingsPage.class);

    private List<ConfigSection<?>> allSections;

    private PageParameters params;

    @SuppressWarnings("unchecked")
    public SettingsPanel(String id, IModel<T> model, PageParameters pageParameters) {
        super(id, model);
        this.params = pageParameters;
        EClass eclass = getEClassToCreate(pageParameters);
        if(eclass!=null)
        {
            setModel(new AttachableWritableModel(eclass, getModel()));
        }

        T modelObject = getModelObject();
        boolean isNew = modelObject.cdoState()==CDOState.NEW || modelObject.cdoState()==CDOState.TRANSIENT;
        final Preferences preferences = isNew ? new AttachablePreferences() : new DelegatingPreferences(PreferencesUtil.scopeFor(modelObject));

        final List<ITab> extensions = loadTabExtensions(preferences);

        // submit section

        @SuppressWarnings({ "rawtypes", "unchecked" })
        Form form = new StatelessForm("form", getModel()) {

            private static final long serialVersionUID = 1L;

            @Override
            protected void onSubmit() {
                IModel<T> model = SettingsPanel.this.getModel();
                CDOObject object = model.getObject();
                CDOView cdoView;
                if (model instanceof AttachableModel) {
                    //it's a new object that needs attaching
                    AttachableModel<CDOObject> attachable = (AttachableModel) model;
                    attachable.attach();
                    CDOObject parent = (CDOObject) attachable.getObject().eContainer();
                    cdoView = parent.cdoView();
                }
                else
                    cdoView = object.cdoView();
                if (cdoView instanceof CDOTransaction) {
                    CDOTransaction transaction = (CDOTransaction) cdoView;
                    commit(preferences, object, transaction);
//					model.detach();
                }
                else
                    throw new IllegalStateException("not a transaction");


                super.onSubmit();
            }

            protected void commit(final Preferences preferences, CDOObject object, CDOTransaction transaction) {
                for (ConfigSection<?> section : allSections) {
                    section.commit(getModel(), preferences);
                }
                try {
                    transaction.commit();
                    if (object instanceof Resolvable) {
                        @SuppressWarnings("rawtypes")
                        Resolvable r = (Resolvable) object;
                        if(!r.getName().equals(preferences.name()))
                        {
                            //FIXME: must rename preferences properly
//								preferences = PreferencesUtil.renamePreferenceNode(preferences,r.getName());
                        }
                        setResponsePage(SettingsPage.class, WicketUtil.buildPageParametersFor(r));
                    }
                    preferences.flush();
                    getSession().success("Saved successfully");
                } catch (CommitException e) {
                    getSession().error(e.getMessage());
                    logger.error("failed to commit configuration for "+object,e);
                } catch (BackingStoreException e) {
                    getSession().error(e.getMessage());
                    logger.error("failed to commit configuration for "+object,e);
                }
                finally{
//					transaction.close();
                }
            }


        };

        ClientSideTabbedPanel<ITab> tabContainer = new ClientSideTabbedPanel<ITab>("tabs", extensions) {

            private static final long serialVersionUID = 1L;

            @Override
            public boolean isVisible() {
                boolean visible = super.isVisible();
                List<ITab> tabContents = extensions;
                for (ITab component : tabContents) {
                    if(component.isVisible())
                        return visible;
                }
                CDOAuthenticatedSession session = (CDOAuthenticatedSession) CDOAuthenticatedSession.get();
                User user = session.getUser();
                if(user==null || CommonPermissions.USER_ANONYMOUS.equals(user.getName()))
                	//user is not logged in, give him the chance
                	throw new RestartResponseAtInterceptPageException(LoginPage.class);
                //if no tab is visible, the user has no permission to be here
                throw new UnauthorizedInstantiationException(SettingsPanel.class);
            }
        };
        tabContainer.setActiveTab(getActiveTab(extensions));
        form.add(tabContainer);
//		form.add(new CustomFeedbackPanel("feedback"));

        Button submitButton = new Button("submit", Model.of("Submit"));
        form.add(submitButton);
//		Button cancelButton = new Button("cancel-button", Model.of("Cancel"));
//		form.add(cancelButton);

        add(form);

    }

    /**
     * determines which tab should be active by default
     * @param extensions
     * @return
     */
    private int getActiveTab(List<ITab> extensions) {
        PageParameters parameters = params;
        if(parameters==null)
            return 0;
        int count = parameters.getIndexedCount();
        if(count<1)
            return 0;
        String tabName = parameters.get("tab").toString();
        if(tabName==null)
            return 0;
        for(int i=0;i<extensions.size();i++) {
            //see http://github.com/jutzig/jabylon/issues/issue/100
            // if the user navigates back from e.g. /roles/admin
            // we can use the last uri segment as a hint for the active tab
            ITab tab = extensions.get(i);
            if(tabName.equalsIgnoreCase(tab.getTitle().getObject()))
                return i;
        }
        return 0;
    }


    private EClass getEClassToCreate(PageParameters pageParameters) {
        StringValue value = pageParameters.get(QUERY_PARAM_CREATE);
        if(value!=null && !value.isEmpty())
        {
            String namespace = pageParameters.get(QUERY_PARAM_NAMESPACE).toString(PropertiesPackage.eNS_URI);
            EPackage ePackage = EPackage.Registry.INSTANCE.getEPackage(namespace);
            if(ePackage!=null) {
                EClassifier eClassifier = ePackage.getEClassifier(value.toString());
                if (eClassifier instanceof EClass) {
                    EClass eclass = (EClass) eClassifier;
                    return eclass;
                }
            }
        }
        return null;

    }


    private User getUser() {
        User user = null;
        if (getSession() instanceof CDOAuthenticatedSession) {
            CDOAuthenticatedSession session = (CDOAuthenticatedSession) getSession();
            user = session.getUser();
        }
        return user;
    }

    private List<ITab> loadTabExtensions(Preferences preferences) {

        List<IConfigurationElement> configurationElements = DynamicConfigUtil.getConfigTabs();
        ArrayListMultimap<String, ConfigSection<?>> sections = ArrayListMultimap.create(configurationElements.size(), 5);
        allSections = new ArrayList<ConfigSection<?>>();
        List<IConfigurationElement> elements = DynamicConfigUtil.getApplicableElements(getModelObject(), getUser());
        for (IConfigurationElement element : elements) {
            String id = element.getAttribute("tab");
            ConfigSection<?> extension;
            try {
                extension = (ConfigSection<?>) element.createExecutableExtension("section");
                sections.put(id, extension);
            } catch (CoreException e) {
                logger.error("Failed to create executable extension: "+element, e);
            }
        }
        List<ITab> extensions = new ArrayList<ITab>();
        T modelObject = getModelObject();
        for (IConfigurationElement element : configurationElements) {
            String name = element.getAttribute("name");
            String id = element.getAttribute("tabID");
            List<ConfigSection<?>> tabSections = sections.removeAll(id);
            ConfigTab tab = new ConfigTab(name, tabSections,getModel(), preferences);
            allSections.addAll(tabSections);
            if(tab.isVisible())
                extensions.add(tab);
        }
        if(!sections.isEmpty())
        {
            logger.warn("Unmapped config sections left {}",sections);
        }
        return extensions;
    }

}
