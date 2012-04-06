package de.jutzig.jabylon.ui.breadcrumb;

import com.vaadin.Application;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.themes.Reindeer;

import de.jutzig.jabylon.ui.applications.MainDashboard;

public class BreadCrumbs extends CustomComponent implements ClickListener {
	HorizontalLayout layout;

	public BreadCrumbs() {
		layout = new HorizontalLayout();
		layout.setSpacing(true);
		setCompositionRoot(layout);
		setStyleName("breadcrumbs");
		setPath(null);
	}

	public void setPath(String path) {
		// could be optimized: always builds path from scratch
		layout.removeAllComponents();
		CrumbTrail currentTrail = MainDashboard.getCurrent();
		// home
		// Reindeer.BUTTON_LINK
		Button link = createEntry(currentTrail);
		layout.addComponent(link);

		if (path != null && !"".equals(path)) {
			String parts[] = path.split("/");
			for (int i = 0; i < parts.length; i++) {
				layout.addComponent(new Label("&raquo;", Label.CONTENT_XHTML));
				CrumbTrail next = currentTrail.walkTo(path);
				link = createEntry(next);
				layout.addComponent(link);
			}
			if (link != null) {
				link.setEnabled(false);
			}
		}

	}

	private Button createEntry(CrumbTrail trail) {
		Button link = new Button();
		link.setCaption(trail.getTrailCaption());
		link.addListener(this);
		link.setStyleName(Reindeer.BUTTON_LINK);
		link.setData(trail);
		return link;
	}

	@Override
	public void buttonClick(ClickEvent event) {
		Application application = getApplication();
		if (application instanceof MainDashboard) {
			MainDashboard dashboard = (MainDashboard) application;
			CrumbTrail trail = (CrumbTrail) event.getButton().getData();
			dashboard.setMainComponent(trail.getComponent());
		}

	}
}