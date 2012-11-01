package de.jutzig.jabylon.rest.ui.wicket.components;

import java.util.List;

import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.extensions.markup.html.tabs.TabbedPanel;
import org.apache.wicket.model.IModel;

public class BootstrapTabbedPanel<T extends ITab> extends TabbedPanel<T> {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BootstrapTabbedPanel(String id, List<T> tabs) {
        super(id, tabs);

        commonInit();
    }

    public BootstrapTabbedPanel(String id, List<T> tabs, IModel<Integer> model) {
        super(id, tabs, model);

        commonInit();
    }

    private void commonInit() {
//        add(new BootstrapBaseBehavior(),
//            new CssClassNameAppender("tabbable"),
//            new AssertTagNameBehavior("div"));
    }

    @Override
    protected String getSelectedTabCssClass() {
        return "active";
    }

    @Override
    protected String getLastTabCssClass() {
        return "";
    }
}