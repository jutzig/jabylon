package de.jutzig.jabylon.updatecenter.repository.ui;

import org.apache.wicket.extensions.markup.html.tabs.AbstractTab;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import de.jutzig.jabylon.updatecenter.repository.ResourceFilter;

public class UpdatecenterTab extends AbstractTab {

    private ResourceFilter filter;

    public UpdatecenterTab(IModel<String> title, ResourceFilter filter) {
        super(title);
        this.filter = filter;
    }

    private static final long serialVersionUID = 1L;

    @Override
    public WebMarkupContainer getPanel(String panelId) {
        switch (filter) {
        case INSTALLED:
            return new InstalledSoftwareTab(panelId);
        default:
            return new UpdatecenterTabContent(panelId, Model.of(filter), new PageParameters());
        }
    }

}
