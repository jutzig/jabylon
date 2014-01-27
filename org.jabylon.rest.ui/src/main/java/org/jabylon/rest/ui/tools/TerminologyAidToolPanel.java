/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.rest.ui.tools;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.concurrent.ExecutionException;

import javax.inject.Inject;

import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.eclipse.emf.common.util.EList;
import org.jabylon.common.resolver.URIResolver;
import org.jabylon.properties.ProjectLocale;
import org.jabylon.properties.ProjectVersion;
import org.jabylon.properties.Property;
import org.jabylon.properties.PropertyFile;
import org.jabylon.properties.PropertyFileDescriptor;
import org.jabylon.properties.Workspace;
import org.jabylon.resources.persistence.PropertyPersistenceService;
import org.jabylon.rest.ui.model.PropertyPair;
import org.jabylon.rest.ui.wicket.BasicPanel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TerminologyAidToolPanel extends BasicPanel<PropertyPair>{

	private static final long serialVersionUID = -7220757882567413172L;
	
	private static final String TERMINOLOGY_DELIMITER = " \t\n\r\f.,;:(){}\"'<>?-";
	
	private static final Map<String, Property> EMPTY_MAP = new HashMap<String, Property>(0);
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TerminologyAidToolPanel.class);

	@Inject
	private URIResolver resolver;
	
	@Inject
	private PropertyPersistenceService propertyPersistence;
	
	private static final String JS = "$(\"#terminology-terms i.icon-share\").click(function() {"
+	"var translation = $(this).prev(\"span\");"
+	"var widget = $(\"#translation\");"
+   "if(widget.attr(\"readonly\")!=='readonly') {"
+		"widget.val(widget.val() + translation.text());"
+		"markDirty();"
+	"}"
+ "});"
+ "$('#translation').change(function() {"
+ "	  var widget = this;"
+ "	  $('#terminology-terms i.icon-share').prev('span').each(function(index){"
+ "		   var result = $(widget).val().indexOf($(this).text());"
+ "		   if(result>=0) {"
+ "			   $(this).siblings('.label').show();" 		   			   
+ "		   }" 
+ "		   else {"
+ "			   $(this).siblings('.label').hide();"
+ "		   }"
+ "	   });"
+ "});";	
	public TerminologyAidToolPanel(String id, IModel<PropertyPair> model) {
		super(id, model);
		
	}
	
	@Override
	public void renderHead(IHeaderResponse response) {
		super.renderHead(response);
		response.render(OnDomReadyHeaderItem.forScript(JS));
	}
	
	@Override
	protected void construct() {
		super.construct();
		Map<String, Property> terminology = getTerminology(getModelObject().getLanguage());
		List<TerminologyTranslation> result = analyze(getModel(), terminology);
		ListView<TerminologyTranslation> list = new ListView<TerminologyTranslation>("terms", result) {
			
			private static final long serialVersionUID = 8716974286032849509L;

			@Override
			protected void populateItem(ListItem<TerminologyTranslation> item) {
				TerminologyTranslation translation = item.getModelObject();
				item.add(new Label("term", translation.getTerm()));
				Label translationLabel = new Label("translation", translation.getTranslation());
				item.add(translationLabel);
				Label label = new Label("label","OK");
				item.add(label);
				String currentTranslation = TerminologyAidToolPanel.this.getModelObject().getTranslated();
				if(currentTranslation==null || !currentTranslation.contains(translation.getTranslation()))
					label.add(new AttributeAppender("style","display: none;"));
				if(translation.getComment()!=null)
					translationLabel.add(new AttributeAppender("title", translation.getComment()));
				
			}
		};
		add(list);
		
	}

    private List<TerminologyTranslation> analyze(IModel<PropertyPair> pair, Map<String, Property> terminology) {

    	List<TerminologyTranslation> translations = new ArrayList<TerminologyTranslation>();
        if(terminology==null || terminology.isEmpty())
            return translations;
        
        Collection<String> tokens = getTokens(pair.getObject().getTemplate().getValue(), terminology);
        for (String term : tokens) {
			Property property = terminology.get(term);
			if(property==null)
				property = terminology.get(term.toLowerCase());
			if(property!=null) {
				translations.add(new TerminologyTranslation(property.getKey(), property.getValue(), property.getComment()));
			}
		}
        return translations;
     
    }
	
    private Collection<String> getTokens(String value, Map<String, Property> terminology) {
    	Collection<String> tokens = new LinkedHashSet<String>();
    	if(value==null)
    		return tokens;
    	StringTokenizer tokenizer = new StringTokenizer(value, TERMINOLOGY_DELIMITER);
    	while(tokenizer.hasMoreTokens()) {
    		tokens.add(tokenizer.nextToken());
    	}
		return tokens;
	}

	private Map<String,Property> getTerminology(Locale locale)
    {
    	Workspace workspace = (Workspace) resolver.resolve("workspace");
        ProjectVersion terminology = workspace.getTerminology();
        if(terminology==null)
            return null;
        ProjectLocale projectLocale = terminology.getProjectLocale(locale);
        if(projectLocale==null)
            return null;
        EList<PropertyFileDescriptor> descriptors = projectLocale.getDescriptors();
        if(descriptors.isEmpty())
            return null;
        PropertyFileDescriptor descriptor = descriptors.get(0);
        try {
			PropertyFile propertyFile = propertyPersistence.loadProperties(descriptor);
			return propertyFile.asMap();
		} catch (ExecutionException e) {
			LOGGER.error("Failed to load terminology project",e);
		}
        return EMPTY_MAP;
    }

	private static class TerminologyTranslation implements Serializable {

		private static final long serialVersionUID = 8661310521341467528L;
		
		private String term, translation, comment;

		public TerminologyTranslation(String term, String translation, String comment) {
			super();
			this.term = term;
			this.translation = translation;
			this.comment = comment;
		}

		public String getTerm() {
			return term;
		}

		public String getTranslation() {
			return translation;
		}
		
		public String getComment() {
			return comment;
		}
	}
}
