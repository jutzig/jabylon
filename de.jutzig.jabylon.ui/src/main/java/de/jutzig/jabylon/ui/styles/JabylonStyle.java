package de.jutzig.jabylon.ui.styles;

public enum JabylonStyle {
	APPLICATION_TITLE("jabylon-application-title"),
	BREADCRUMB_PANEL("jayblon-breadcrumb-panel"),
	BIG_WARNING("big warning"),
	TABLE_STRIPED("striped"),
	SEARCH_FIELD("search"),
	PROGRESS_INDICATOR("big"),
	PROGRESS_INDICATOR_INDETERMINATE("big bar");
	
	String cssName;
	
	private JabylonStyle(String cssName) {
		this.cssName = cssName;
	}
	
	public String getCSSName()
	{
		return cssName;
	}
	
}