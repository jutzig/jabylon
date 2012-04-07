package de.jutzig.jabylon.ui.styles;

public enum JabylonStyle {
	APPLICATION_TITLE("jabylon-application-title");
	
	
	String cssName;
	
	private JabylonStyle(String cssName) {
		this.cssName = cssName;
	}
	
	public String getCSSName()
	{
		return cssName;
	}
	
}