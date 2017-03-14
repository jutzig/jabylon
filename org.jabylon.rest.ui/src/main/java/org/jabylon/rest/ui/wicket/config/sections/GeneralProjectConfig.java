/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
/**
 *
 */
package org.jabylon.rest.ui.wicket.config.sections;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.IModel;
import org.eclipse.emf.common.util.URI;
import org.jabylon.common.util.PreferencesUtil;
import org.jabylon.properties.Project;
import org.jabylon.properties.ProjectVersion;
import org.jabylon.properties.PropertiesFactory;
import org.jabylon.properties.Property;
import org.jabylon.properties.PropertyFile;
import org.jabylon.properties.ScanConfiguration;
import org.jabylon.properties.types.impl.POHelper;
import org.jabylon.properties.util.PropertiesResourceImpl;
import org.jabylon.rest.ui.model.AttachableModel;
import org.jabylon.rest.ui.wicket.config.AbstractConfigSection;
import org.jabylon.security.CommonPermissions;
import org.osgi.service.prefs.Preferences;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 *
 */
public class GeneralProjectConfig extends AbstractConfigSection<Project> {

    private static final long serialVersionUID = 1L;
    private static final Collection<String> TERMINOLOGY_LANGUAGES = new ArrayList<String>();
    private static final Logger LOGGER = LoggerFactory.getLogger(GeneralProjectConfig.class);
    private static final String TERMINOLOGY_DOWNLOAD_URL = "http://pootle.locamotion.org/export/terminology/{0}/essential.po";
    static {
    	TERMINOLOGY_LANGUAGES.add("de");
    	TERMINOLOGY_LANGUAGES.add("fr");
    	TERMINOLOGY_LANGUAGES.add("es");
    	TERMINOLOGY_LANGUAGES.add("it");
    }

    @Override
    public WebMarkupContainer doCreateContents(String id, IModel<Project> input, Preferences prefs) {
        return new ProjectConfigSection(id, input);
    }

    @Override
    public void commit(IModel<Project> input, Preferences config) {
        // TODO rename on filesystem
    	
    	if(input instanceof AttachableModel) {
    		// newly created
    		if(input.getObject().isTerminology())
				try {
					createTerminologyProject(input.getObject());
				} catch (IOException e) {
					throw new RuntimeException("Failed to create template project",e);
				}
    	}
    	

    }

    private void createTerminologyProject(Project parent) throws IOException {
    	ProjectVersion version = PropertiesFactory.eINSTANCE.createProjectVersion();
    	version.setName("master");
    	version.setParent(parent);
    	URL url = new URL(MessageFormat.format(TERMINOLOGY_DOWNLOAD_URL, "de"));
    	createMessageFile(url.openStream(), version.absoluteFilePath().appendSegment("messages.properties"),true);
    	for (String language : TERMINOLOGY_LANGUAGES) {
    		url = new URL(MessageFormat.format(TERMINOLOGY_DOWNLOAD_URL, language));
    		createMessageFile(url.openStream(),version.absoluteFilePath().appendSegment("messages_"+language+".properties"),false);
		}
    	ScanConfiguration scanConfiguration = PreferencesUtil.getScanConfigForProject(parent);
    	version.fullScan(scanConfiguration);
		
	}

	private void createMessageFile(InputStream in, URI target, boolean isTemplate) throws IOException {
		BufferedReader reader = null; 
		try {
			reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
			Property property = null;
			POHelper helper = new POHelper();
			PropertyFile file = PropertiesFactory.eINSTANCE.createPropertyFile();
			while((property=helper.readProperty(reader))!=null)
			{
			    if(isTemplate) {
			    	property.setValue(property.getKey());
			    	//get rid of the german comment
			    	property.setComment(null);
			    }
			    file.getProperties().add(property);
			}
			PropertiesResourceImpl resource = new PropertiesResourceImpl(target);
			resource.getContents().add(file);
			resource.save(new HashMap<Object, Object>());
		} catch (UnsupportedEncodingException e) {
			LOGGER.error(e.getMessage(),e);
		} finally {
			reader.close();
			
		}
	}

	@Override
    public String getRequiredPermission() {
        String projectName = null;
        if(getDomainObject()!=null)
            projectName = getDomainObject().getName();
        return CommonPermissions.constructPermission(CommonPermissions.PROJECT,projectName,CommonPermissions.ACTION_CONFIG);
    }


}
