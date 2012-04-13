package de.jutzig.jabylon.ui.components;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.themes.Reindeer;

import de.jutzig.jabylon.ui.resources.ImageConstants;
import de.jutzig.jabylon.ui.styles.JabylonStyle;

public class ApplicationTitleBar extends CustomComponent {

	private HorizontalLayout mainLayout;

	/*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */

	/**
	 * The constructor should first build the main layout, set the
	 * composition root and then do any custom initialization.
	 *
	 * The constructor will not be automatically regenerated by the
	 * visual editor.
	 */
	public ApplicationTitleBar() {
		buildMainLayout();
		setCompositionRoot(mainLayout);

		// TODO add user code here
	}

	private HorizontalLayout buildMainLayout() {
		// common part: create layout
		mainLayout = new HorizontalLayout();
		mainLayout.setWidth(100, UNITS_PERCENTAGE);
		mainLayout.setImmediate(false);
		mainLayout.setMargin(false);
		mainLayout.setSpacing(true);
		mainLayout.setStyleName(JabylonStyle.BREADCRUMB_PANEL.getCSSName());
		// top-level component properties
		
        // Upper left logo
		Label title = new Label();
		title.setIcon(ImageConstants.IMAGE_LOGO);
//		title.setCaption("Jabylon");
		title.setWidth(150, Label.UNITS_PIXELS);
//		title.setStyleName(Reindeer.LABEL_H1);
		title.setStyleName(JabylonStyle.APPLICATION_TITLE.getCSSName());
		mainLayout.addComponent(title);
		mainLayout.setComponentAlignment(title, Alignment.TOP_LEFT);
		
		Button help = new Button("Help");
		help.setStyleName(Reindeer.BUTTON_LINK);
		mainLayout.addComponent(help);
		mainLayout.setComponentAlignment(help, Alignment.BOTTOM_RIGHT);
		mainLayout.setExpandRatio(help, 2f);
		
		Button settings = new Button("Settings");
		settings.setStyleName(Reindeer.BUTTON_LINK);
		mainLayout.addComponent(settings);
		mainLayout.setComponentAlignment(settings, Alignment.BOTTOM_RIGHT);
		
		
		Button login = new Button("Login");
		login.setStyleName(Reindeer.BUTTON_LINK);
		mainLayout.addComponent(login);
		mainLayout.setComponentAlignment(login, Alignment.BOTTOM_RIGHT);
		
		
		return mainLayout;
	}

}
