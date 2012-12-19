/*
 * PropertiesEditorToolbar.java
 *
 * created at 07.12.2012 by utzig <YOURMAILADDRESS>
 *
 * Copyright (c) SEEBURGER AG, Germany. All Rights Reserved.
 */
package de.jutzig.jabylon.rest.ui.wicket.panels;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import de.jutzig.jabylon.properties.PropertyFile;
import de.jutzig.jabylon.properties.PropertyFileDescriptor;
import de.jutzig.jabylon.rest.ui.model.PropertyPair;
import de.jutzig.jabylon.rest.ui.tools.PropertyEditorTool;
import de.jutzig.jabylon.rest.ui.tools.PropertyToolTab;
import de.jutzig.jabylon.rest.ui.wicket.BasicResolvablePanel;
import de.jutzig.jabylon.rest.ui.wicket.components.ClientSideTabbedPanel;


/**
 * TODO short description for PropertiesEditorToolbar.
 * <p>
 * Long description for PropertiesEditorToolbar.
 *
 * @author utzig
 */
public class PropertiesEditorToolbar extends BasicResolvablePanel<PropertyFileDescriptor>
{
    /** field <code>serialVersionUID</code> */
    private static final long serialVersionUID = 1L;
    
    private String currentKey;
    
    private boolean initialized;
    
    @Inject
    private List<PropertyEditorTool> tools;

	private ClientSideTabbedPanel<PropertyToolTab> tabContainer;

	private List<PropertyToolTab> extensions;
    
    

    public PropertiesEditorToolbar(String id, IModel<PropertyFileDescriptor> model, PageParameters pageParameters) {
    	super(id, model, pageParameters);
	}

	@Override
    protected void onBeforeRender() {
    	if(!initialized)
    	{
    		extensions = createExtensions();
    		tabContainer = new ClientSideTabbedPanel<PropertyToolTab>("tabs", extensions);
    		add(tabContainer);
    		initialized = true;
    	}
    	super.onBeforeRender();
    }
    
    
    

    private List<PropertyToolTab> createExtensions() {
    	List<PropertyToolTab> extensions = new ArrayList<PropertyToolTab>(tools.size());
    	Collections.sort(tools,new Comparator<PropertyEditorTool>() {
    		@Override
    		public int compare(PropertyEditorTool o1, PropertyEditorTool o2) {
    			return o1.getPrecedence() - o2.getPrecedence();
    		}
    	});
    	for (PropertyEditorTool tool : tools) {
			extensions.add(new PropertyToolTab(tool));
		}
		return extensions;
	}


	public void setKey(String key)
    {
		if(key!=null && !key.isEmpty() && !key.equals(currentKey))
		{
			PropertyFileDescriptor translation = getModel().getObject();
			PropertyFile properties = translation.loadProperties();
			properties.getProperty(key);
			PropertyFileDescriptor master = translation.getMaster();
			PropertyFile masterFile = master.loadProperties();
		 
			PropertyPair pair = new PropertyPair(masterFile.getProperty(key),properties.getProperty(key), translation.getVariant(),translation.cdoID());
//			int selected = tabContainer.getSelectedTab();
			for (PropertyToolTab tool : extensions) {
				tool.setModel(Model.of(pair));
			}
			
		}
		this.currentKey = key;
    }

	public void respond(AjaxRequestTarget target) {
		List<WebMarkupContainer> tabContents = tabContainer.getTabContents();
		for (WebMarkupContainer webMarkupContainer : tabContents) {
			target.add(webMarkupContainer);
		}
		
	}

}



