package de.jutzig.jabylon.rest.ui.security;

import org.apache.wicket.authroles.authentication.panel.SignInPanel;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import de.jutzig.jabylon.properties.Resolvable;
import de.jutzig.jabylon.rest.ui.wicket.pages.GenericPage;

public class LoginPage extends GenericPage<Resolvable<?, ?>> {

    public LoginPage(PageParameters parameters) {
        super(parameters);
        SignInPanel panel = new SignInPanel("sign-in", true);
        panel.addOrReplace(new Label("feedback",""));
        add(panel);
    }

    @Override
    protected IModel<Resolvable<?, ?>> createModel(PageParameters params) {
        return null;
    }
}
