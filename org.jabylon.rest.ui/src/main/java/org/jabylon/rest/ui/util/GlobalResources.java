/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.rest.ui.util;

import org.apache.wicket.request.resource.ResourceReference;

public class GlobalResources {

    /**
     * bootstrap integration for datatables
     */
    public static final ResourceReference JS_BOOTSTRAP_DATATABLES = new WebContextUrlResourceReference("js/datatables/dataTables.bootstrap.js");

    /**
     * JQuery portion of datatables
     */
    public static final ResourceReference JS_JQUERY_DATATABLES = new WebContextUrlResourceReference("js/datatables/jquery.datatables.min.js");

    /**
     * custom sort functions for datatables
     */
    public static final ResourceReference JS_DATATABLES_CUSTOMSORT = new WebContextUrlResourceReference("js/datatables/custom.sorting.js");

    /**
     * the javascript portion of bootstrap
     */
    public static final ResourceReference JS_BOOTSTRAP = new WebContextUrlResourceReference("bootstrap/js/bootstrap.min.js");
    
    /**
     * keyboard shortcuts JS library
     */
    public static final ResourceReference JS_SHORTCUTS = new WebContextUrlResourceReference("js/openjs/shortcuts.js");

    /**
     * shows a confirmation dialog before leaving a page with modifications
     */
    public static final ResourceReference JS_WARN_WHEN_DIRTY = new WebContextUrlResourceReference("js/warnWhenDirty.js");
    
    /**
     * automatically activates the last used tab (for ClientSideTabbedPanel
     */
    public static final ResourceReference JS_PERSISTENT_TABS = new WebContextUrlResourceReference("js/persistentTabs.js");
    
    /**
     * the main css file
     */
    public static final ResourceReference MAIN_CSS = new WebContextUrlResourceReference("css/main.css");
    
    
    public static final ResourceReference IMG_WORKSPACE_SETTINGS = new WebContextUrlResourceReference("img/icons/workspaceSettings.png");
    public static final ResourceReference IMG_SYSTEM_SETTINGS = new WebContextUrlResourceReference("img/icons/systemSettings.gif");
    public static final ResourceReference IMG_SECURITY_SETTINGS = new WebContextUrlResourceReference("img/icons/securitySettings.gif");
    public static final ResourceReference IMG_LOG_SETTINGS = new WebContextUrlResourceReference("img/icons/logSettings.png");
}
