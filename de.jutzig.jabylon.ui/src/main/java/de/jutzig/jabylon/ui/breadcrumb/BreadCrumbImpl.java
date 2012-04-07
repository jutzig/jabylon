package de.jutzig.jabylon.ui.breadcrumb;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.themes.Reindeer;

import de.jutzig.jabylon.ui.applications.MainDashboard;

public class BreadCrumbImpl extends CustomComponent implements ClickListener,
		BreadCrumb {
	HorizontalLayout layout;

	private List<Button> parts;

	public BreadCrumbImpl() {
		parts = new ArrayList<Button>();
		layout = new HorizontalLayout();
		layout.setSpacing(true);
		setCompositionRoot(layout);
		setStyleName("breadcrumbs");
		setPath((String[]) null);
	}

	public void setPath(String... segments) {
		// could be optimized: always builds path from scratch
		layout.removeAllComponents();
		CrumbTrail currentTrail = MainDashboard.getCurrent();
		// home
		Button link = addEntry(currentTrail);
		parts.clear();
		parts.add(link);
		walkTo(segments);

	}

	private Button addEntry(CrumbTrail trail) {
		if (!parts.isEmpty()) {
			layout.addComponent(new Label("&raquo;", Label.CONTENT_XHTML));
		}
		Button link = new Button();
		link.setCaption(trail.getTrailCaption());
		link.addListener(this);
		link.setStyleName(Reindeer.BUTTON_LINK);
		link.setData(trail);
		layout.addComponent(link);
		parts.add(link);
		return link;
	}

	@Override
	public void buttonClick(ClickEvent event) {
		int steps = 0;
		for (Button button : parts) {
			steps++;
			if (button == event.getButton()) {
				goBack(steps);
				break;
			}
		}

	}

	@Override
	public void goBack() {
		goBack(1);

	}

	@Override
	public void goBack(int steps) {
		List<Button> subList = new ArrayList<Button>(parts.subList(0,
				parts.size() - steps));
		if(subList.isEmpty()) //never remove the root
			return;
		layout.removeAllComponents();
		parts.clear();
		CrumbTrail trail = null;
		for (Button link : subList) {
			trail = (CrumbTrail) link.getData();
			addEntry(trail);
		}
		if(trail!=null)
			MainDashboard.getCurrent().setMainComponent(trail.getComponent());

	}

	@Override
	public void walkTo(String... steps) {
		Button link = null;
		CrumbTrail currentTrail;
		if(parts.isEmpty())
		{
			currentTrail = MainDashboard.getCurrent();
			addEntry(currentTrail);
		}
		else
		{
			currentTrail = (CrumbTrail) parts.get(parts.size()-1).getData();
			
		}
		if (steps != null) {
			for (int i = 0; i < steps.length; i++) {

				currentTrail = currentTrail.walkTo(steps[i]);
				link = addEntry(currentTrail);

			}
		}
		if (link != null) {
			link.setEnabled(false);
		}
		MainDashboard.getCurrent().setMainComponent(currentTrail.getComponent());

	}
}