/**
 *
 */
package de.jutzig.jabylon.rest.ui.wicket.config;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.AbstractLink;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;

/**
 * @author jutzig.dev@googlemail.com
 *
 */
public class SettingsOverviewPanel extends GenericPanel<Void> {

    private static final long serialVersionUID = 1L;

    public SettingsOverviewPanel(String id) {
        super(id);

        List<ConfigKind> configs = new ArrayList<ConfigKind>(EnumSet.allOf(ConfigKind.class));
        ListView<ConfigKind> view = new ListView<ConfigKind>("config",configs) {

            private static final long serialVersionUID = 1L;

            @Override
            protected void populateItem(ListItem<ConfigKind> item) {
                item.add(item.getModelObject().constructLink("link"));
                item.add(new Label("description",item.getModelObject().getDescription()));

            }

        };
        add(view);
    }

}

enum ConfigKind {
    WORKSPACE("Projects","Configure the Jabylon translation Projects"){

        @Override
        public Link<Void> constructLink(String id) {
            PageParameters params = new PageParameters();
            params.set(0, "workspace");
            BookmarkablePageLink<Void> link = new BookmarkablePageLink<Void>(id, SettingsPage.class, params);
            link.setBody(Model.of("Projects"));
            return link;
        }


    },

    SYSTEM("System","Install updates, plugins and manage the bundle konfiguration") {
        @Override
        public AbstractLink constructLink(String id) {

            return new ExternalLink(id, "/system", "System");
        }
    }
    , SECURITY("Security","Manage Roles, Users, Permissions and generell security settings") {
        @Override
        public AbstractLink constructLink(String id) {
            return new ExternalLink(id, "/settings/security","Security");
        }
    };

    private String name, description;

    private ConfigKind(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
    public String getName() {
        return name;
    }
    public abstract AbstractLink constructLink(String id);

}
