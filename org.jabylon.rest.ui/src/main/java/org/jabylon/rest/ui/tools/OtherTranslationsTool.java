/**
 *
 */
package org.jabylon.rest.ui.tools;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import org.jabylon.rest.ui.model.PropertyPair;

/**
 * shows how a given key has been translated in other languages.
 *
 * {@link http://github.com/jutzig/jabylon/issues/issue/71}
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 *
 */
@Component
@Service(value=PropertyEditorTool.class)
public class OtherTranslationsTool implements PropertyEditorTool {

    private static final long serialVersionUID = 1L;

    /* (non-Javadoc)
     * @see org.jabylon.rest.ui.wicket.PanelFactory#createPanel(org.apache.wicket.request.mapper.parameter.PageParameters, org.apache.wicket.model.IModel, java.lang.String)
     */
    @Override
    public Panel createPanel(PageParameters params, IModel<PropertyPair> input, String id) {
        return new OtherTranslationsToolPanel(id, input);
    }

    @Override
    public String getName() {
        return "Other Languages";
    }

    @Override
    public int getPrecedence() {
        return 110;
    }

}
