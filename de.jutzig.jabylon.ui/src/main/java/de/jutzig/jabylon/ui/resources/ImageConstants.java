package de.jutzig.jabylon.ui.resources;

import com.vaadin.terminal.Resource;
import com.vaadin.terminal.ThemeResource;

public final class ImageConstants {

	private ImageConstants() {
		// hide constructor of utility class
	}
	
	public static final int ICON_SIZE = 16;
	public static final Resource IMAGE_LOGO = new ThemeResource("img/jabylon-logo.gif");
	public static final Resource IMAGE_PROJECT = new ThemeResource("../runo/icons/"+ICON_SIZE+"/folder.png");
	public static final Resource IMAGE_NEW_PROJECT = new ThemeResource("../runo/icons/"+ICON_SIZE+"/folder-add.png");
	public static final Resource IMAGE_PROJECT_SCAN = new ThemeResource("../runo/icons/"+ICON_SIZE+"/reload.png");
	public static final Resource IMAGE_PROJECT_COMMIT = new ThemeResource("../runo/icons/"+ICON_SIZE+"/arrow-up.png");
	
	public static final Resource IMAGE_NEW_LOCALE = IMAGE_NEW_PROJECT;
	
	public static final Resource IMAGE_PROPERTIES_FILE = new ThemeResource("../runo/icons/"+ICON_SIZE+"/document-txt.png");
	public static final Resource IMAGE_NEW_PROPERTIES_FILE = new ThemeResource("../runo/icons/"+ICON_SIZE+"/document-add.png");
	
	public static final Resource IMAGE_TWISTY_EXPANDED = new ThemeResource("../runo/icons/"+ICON_SIZE+"/arrow-down.png");
	public static final Resource IMAGE_TWISTY_COLLAPSED = new ThemeResource("../runo/icons/"+ICON_SIZE+"/arrow-right.png");
	
	public static final String FOLDER_FLAGS = "img/flags/gif/";
	
}
