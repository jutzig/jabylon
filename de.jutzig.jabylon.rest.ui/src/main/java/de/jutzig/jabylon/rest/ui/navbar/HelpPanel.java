/**
 *
 */
package de.jutzig.jabylon.rest.ui.navbar;

import java.io.Serializable;

import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import de.jutzig.jabylon.rest.ui.wicket.BasicPanel;
import de.jutzig.jabylon.rest.ui.wicket.PanelFactory;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 *
 */
public class HelpPanel<T> extends BasicPanel<T> {

    private static final long serialVersionUID = 1L;

    public HelpPanel(String id, IModel<T> model, PageParameters parameters) {
        super(id, model, parameters);
        add(new ExternalLink("link","#help")); //$NON-NLS-1$ //$NON-NLS-2$
    }

    public static class HelpPanelFactory implements PanelFactory<Object>, Serializable
    {

        private static final long serialVersionUID = 1L;

        @Override
        public Panel createPanel(PageParameters params, IModel<Object> input, String id) {

            return new HelpPanel<Object>(id, input , params);
        }

    }
}
