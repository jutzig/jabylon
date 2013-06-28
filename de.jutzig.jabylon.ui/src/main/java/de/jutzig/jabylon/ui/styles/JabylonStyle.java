package de.jutzig.jabylon.ui.styles;

public enum JabylonStyle {
    APPLICATION_TITLE("jabylon-application-title"), //$NON-NLS-1$
    BREADCRUMB_PANEL("jayblon-breadcrumb-panel"), //$NON-NLS-1$
    BIG_WARNING("big warning"), //$NON-NLS-1$
    TABLE_STRIPED("striped"), //$NON-NLS-1$
    SEARCH_FIELD("search"), //$NON-NLS-1$
    PROGRESS_INDICATOR("big"), //$NON-NLS-1$
    PROGRESS_INDICATOR_INDETERMINATE("big bar"); //$NON-NLS-1$

    String cssName;

    private JabylonStyle(String cssName) {
        this.cssName = cssName;
    }

    public String getCSSName()
    {
        return cssName;
    }

}
