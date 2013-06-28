package de.jutzig.jabylon.ui.resources;

import com.vaadin.terminal.Resource;
import com.vaadin.terminal.ThemeResource;

public final class ImageConstants {

    private ImageConstants() {
        // hide constructor of utility class
    }

    public static final int ICON_SIZE = 16;
    public static final Resource IMAGE_LOGO = new ThemeResource("img/jabylon-logo.gif"); //$NON-NLS-1$
    public static final Resource IMAGE_PROJECT = new ThemeResource("../runo/icons/"+ICON_SIZE+"/folder.png"); //$NON-NLS-1$ //$NON-NLS-2$
    public static final Resource IMAGE_NEW_PROJECT = new ThemeResource("../runo/icons/"+ICON_SIZE+"/folder-add.png"); //$NON-NLS-1$ //$NON-NLS-2$
    public static final Resource IMAGE_PROJECT_SCAN = new ThemeResource("../runo/icons/"+ICON_SIZE+"/reload.png"); //$NON-NLS-1$ //$NON-NLS-2$
    public static final Resource IMAGE_PROJECT_COMMIT = new ThemeResource("../runo/icons/"+ICON_SIZE+"/arrow-up.png"); //$NON-NLS-1$ //$NON-NLS-2$
    public static final Resource IMAGE_ERROR = new ThemeResource("../runo/icons/"+ICON_SIZE+"/error.png"); //$NON-NLS-1$ //$NON-NLS-2$
    public static final Resource IMAGE_OK = new ThemeResource("../runo/icons/"+ICON_SIZE+"/ok.png"); //$NON-NLS-1$ //$NON-NLS-2$
    public static final Resource IMAGE_DOWNLOAD_ARCHIVE = new ThemeResource("../runo/icons/"+ICON_SIZE+"/folder-add.png"); //$NON-NLS-1$ //$NON-NLS-2$
    public static final Resource IMAGE_SETTINGS = new ThemeResource("../runo/icons/"+ICON_SIZE+"/settings.png"); //$NON-NLS-1$ //$NON-NLS-2$
    public static final Resource IMAGE_HELP = new ThemeResource("../runo/icons/"+ICON_SIZE+"/help.png"); //$NON-NLS-1$ //$NON-NLS-2$
    public static final Resource IMAGE_LOGIN = new ThemeResource("../runo/icons/"+ICON_SIZE+"/user.png"); //$NON-NLS-1$ //$NON-NLS-2$



    public static final Resource IMAGE_NEW_LOCALE = IMAGE_NEW_PROJECT;

    public static final Resource IMAGE_PROPERTIES_FILE = new ThemeResource("../runo/icons/"+ICON_SIZE+"/document-txt.png"); //$NON-NLS-1$ //$NON-NLS-2$
    public static final Resource IMAGE_NEW_PROPERTIES_FILE = new ThemeResource("../runo/icons/"+ICON_SIZE+"/document-add.png"); //$NON-NLS-1$ //$NON-NLS-2$

    public static final Resource IMAGE_TWISTY_EXPANDED = new ThemeResource("../runo/icons/"+ICON_SIZE+"/arrow-down.png"); //$NON-NLS-1$ //$NON-NLS-2$
    public static final Resource IMAGE_TWISTY_COLLAPSED = new ThemeResource("../runo/icons/"+ICON_SIZE+"/arrow-right.png"); //$NON-NLS-1$ //$NON-NLS-2$

    public static final String FOLDER_FLAGS = "img/flags/gif/"; //$NON-NLS-1$

}
