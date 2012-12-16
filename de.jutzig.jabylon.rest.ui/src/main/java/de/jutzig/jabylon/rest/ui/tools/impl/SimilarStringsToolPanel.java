/**
 * 
 */
package de.jutzig.jabylon.rest.ui.tools.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.search.FuzzyLikeThisQuery;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.util.Version;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.IModel;
import org.eclipse.emf.common.util.EList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.jutzig.jabylon.index.properties.QueryService;
import de.jutzig.jabylon.index.properties.SearchResult;
import de.jutzig.jabylon.properties.Property;
import de.jutzig.jabylon.properties.PropertyFile;
import de.jutzig.jabylon.properties.PropertyFileDescriptor;
import de.jutzig.jabylon.rest.ui.model.PropertyPair;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 *
 */
public class SimilarStringsToolPanel extends GenericPanel<PropertyPair> {

	private static final long serialVersionUID = 1L;
	private static Logger logger = LoggerFactory.getLogger(SimilarStringsToolPanel.class);
	@Inject
	private QueryService queryService;
	
	public SimilarStringsToolPanel(String id, IModel<PropertyPair> model) {
		super(id, model);
		ListView<Similarity> list = new ListView<Similarity>("children") {
			
			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(ListItem<Similarity> item) {
				Similarity similarity = item.getModelObject();
				item.add(new Label("template",similarity.getOriginal()));
				item.add(new Label("translation",similarity.getTranslation()));
				WebMarkupContainer progress = new WebMarkupContainer("similarity");
				item.add(progress);
				progress.add(new AttributeModifier("style", "width: " + similarity.getSimilarity() + "%"));
				
			}
		};
		add(list);
	}
	
	protected List<Similarity> doSearch(IModel<PropertyPair> model)
	{

		PropertyPair pair = model.getObject();
        FuzzyLikeThisQuery query = new FuzzyLikeThisQuery(10, new StandardAnalyzer(Version.LUCENE_29));
        query.addTerms(pair.getOriginal(), QueryService.FIELD_VALUE, 0.6f, 3);
        SearchResult result = queryService.search(query,20);

        if(result==null)
            return Collections.emptyList();
        List<Similarity> resultList = new ArrayList<SimilarStringsToolPanel.Similarity>();
        TopDocs topDocs = result.getTopDocs();
        ScoreDoc[] doc = topDocs.scoreDocs;
        for (ScoreDoc scoreDoc : doc)
        {
            if(scoreDoc.score<0.20)
                continue;
            
            try
            {
                Document document = result.getSearcher().doc(scoreDoc.doc);
                
                PropertyFileDescriptor descriptor = queryService.getDescriptor(document, pair.getTemplate().cdoView());

            if(descriptor==null)
                continue;
            PropertyFileDescriptor slave = getSlave(descriptor,pair.getLanguage());
            if(slave==null)
                continue;
            
            PropertyFile properties = slave.loadProperties();
            String key = document.get(QueryService.FIELD_KEY);
//            if(slave==translation && pair.getKey().equals(key))
//            	continue; //that means we found the current property as a similarity. Very helpful...
            Property property = properties.getProperty(key);
            
            if(property==null || property.getValue()==null)
                continue;
            
            Similarity similarity = new Similarity(document.get(QueryService.FIELD_VALUE), property.getValue(),(int)(scoreDoc.score*100));
            resultList.add(similarity);
            }
            catch (CorruptIndexException e)
            {
            	logger.error("Failed to find similar strings", e);
            }
            catch (IOException e)
            {
            	logger.error("Failed to find similar strings", e);
            }

        }
        try
        {
            result.getSearcher().close();
        }
        catch (IOException e)
        {
        	logger.error("Failed to close searcher", e);
        }
        return resultList;

	}
	
    private PropertyFileDescriptor getSlave(PropertyFileDescriptor descriptor, Locale language)
    {
    	EList<PropertyFileDescriptor> translations = descriptor.getDerivedDescriptors();
    	for (PropertyFileDescriptor translation : translations) {
			if(language.equals(translation.getVariant()))
				return translation;
		}
    	return null;
    }

    
    public static class Similarity
    {
        private String original;
        private String translation;
        private int similarity;




        public Similarity(String original, String translation, int similartiy)
        {
            super();
            this.original = original;
            this.translation = translation;
            this.similarity = similartiy;
        }

        public String getOriginal()
        {
            return original;
        }

        public String getTranslation()
        {
            return translation;
        }

        public int getSimilarity()
        {
            return similarity;
        }
    }
}
