/**
 * 
 */
package de.jutzig.jabylon.rest.ui.wicket;

import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 *
 */
public interface PanelFactory <T>{
	Panel createPanel(PageParameters params, IModel<T> input, String id);
}
