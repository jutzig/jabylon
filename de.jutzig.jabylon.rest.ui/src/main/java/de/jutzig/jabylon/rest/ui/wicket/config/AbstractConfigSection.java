/**
 *
 */
package de.jutzig.jabylon.rest.ui.wicket.config;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.IModel;
import org.osgi.service.prefs.Preferences;

import de.jutzig.jabylon.rest.ui.security.CDOAuthenticatedSession;
import de.jutzig.jabylon.users.User;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 *
 */
public abstract class AbstractConfigSection<T> implements ConfigSection<T>{


    private static final long serialVersionUID = 1L;

    private IModel<T> model;


    public T getDomainObject() {
        if(model==null)
            return null;
        return model.getObject();
    }

    public IModel<T> getModel()
    {
        return model;
    }

    @Override
    public void apply(Preferences config) {


    }

    @Override
    public boolean isVisible(IModel<T> input, Preferences config) {
        model = input;
        CDOAuthenticatedSession session = (CDOAuthenticatedSession) CDOAuthenticatedSession.get();
        User user = session.getUser();
        if(user!=null)
            return user.hasPermission(getRequiredPermission());
        else
            return session.getAnonymousUser().hasPermission(getRequiredPermission());
    }

    @Override
    public final WebMarkupContainer createContents(String id, IModel<T> input, Preferences config) {
        this.model = input;
        return doCreateContents(id,input,config);
    }

    protected abstract WebMarkupContainer doCreateContents(String id, IModel<T> input, Preferences config);
}
