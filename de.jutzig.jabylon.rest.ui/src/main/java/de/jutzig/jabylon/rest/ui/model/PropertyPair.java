package de.jutzig.jabylon.rest.ui.model;

import java.io.Serializable;

import org.apache.wicket.model.IModel;

import de.jutzig.jabylon.properties.PropertiesFactory;
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
			return ""; //$NON-NLS-1$
		return getTemplate().getValue();
	}
	
	public String getTranslated()
	{
		if(getTranslation()==null)
			return ""; //$NON-NLS-1$
		return getTranslation().getValue();
	}
	
	public void setTranslated(String translated)
	{
		getOrCreateTranslation().setValue(translated);
	}
	
	public String getOriginalComment()
	{
		if(getTemplate()==null)
			return ""; //$NON-NLS-1$
		return getTemplate().getComment();
	}
	
	public void setOriginalComment(String comment){
		//TODO: should this be implemented?
	}
	
	public void setOriginal(String comment)
	{
		//TODO: should this be implemented?		
	}
	
	public String getTranslatedComment()
	{
		if(getTranslation()==null)
			return ""; //$NON-NLS-1$
		return getTranslation().getComment();
	}
	
	public void setTranslatedComment(String comment)
	{
		getOrCreateTranslation().setComment(comment);
	}
	
	
	
	public Property getOrCreateTranslation()
	{
		Property property = getTranslation();
		if(property==null)
		{
			property = PropertiesFactory.eINSTANCE.createProperty();
			property.setKey(getTemplate().getKey());
			this.translation = property;
		}
		return property;
	}
	
	public String getKey()
	{
		if(template!=null)
			return template.getKey();
		if(translation!=null)
			return translation.getKey();
		return null;
	}
}
