package de.jutzig.jabylon.rest.ui.model;

import java.io.Serializable;

import org.apache.wicket.model.IModel;

import de.jutzig.jabylon.properties.Property;

public class PropertyPair implements Serializable{

	private Property template;
	private Property translation;

	public PropertyPair(Property template, Property translation) {
		super();
		this.template = template;
		this.translation = translation;
	}

	public Property getTemplate() {
		return template;
	}
	
	public Property getTranslation() {
		return translation;
	}
	
	public String getOriginal()
	{
		if(getTemplate()==null)
			return "";
		return getTemplate().getValue();
	}
	
	public String getTranslated()
	{
		if(getTranslation()==null)
			return "";
		return getTranslation().getValue();
	}
	
	public String getOriginalComment()
	{
		if(getTemplate()==null)
			return "";
		return getTemplate().getComment();
	}
	
	public String getTranslatedComment()
	{
		if(getTranslation()==null)
			return "";
		return getTranslation().getComment();
	}
	
}
