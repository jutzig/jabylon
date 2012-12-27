/**
 * 
 */
package de.jutzig.jabylon.rest.ui.tools;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.TreeSet;

import javax.inject.Inject;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.search.FuzzyLikeThisQuery;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.util.Version;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.behavior.AttributeAppender;
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
	private transient QueryService queryService;
	
	public SimilarStringsToolPanel(String id, IModel<PropertyPair> model) {
		super(id, model);;

	}
	
	@Override
	protected void onBeforeRender() {
		List<Similarity> result = doSearch(getModel());
		ListView<Similarity> list = new ListView<Similarity>("children",result) {
			
			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(ListItem<Similarity> item) {
				Similarity similarity = item.getModelObject();
				item.add(new Label("template",similarity.getOriginal()));
				item.add(new Label("translation",similarity.getTranslation()));
				item.add(new AttributeAppender("title", similarity.getFullPath()));
				WebMarkupContainer progress = new WebMarkupContainer("similarity");
				item.add(progress);
				progress.add(new AttributeModifier("style", "width: " + similarity.getSimilarity() + "%"));
				
			}
		};
		addOrReplace(list);
		super.onBeforeRender();
	}

	
	protected List<Similarity> doSearch(IModel<PropertyPair> model)
	{

		
		PropertyPair pair = model.getObject();
		if(pair==null || pair.getOriginal()==null)
			return Collections.emptyList();
        FuzzyLikeThisQuery query = new FuzzyLikeThisQuery(10, new StandardAnalyzer(Version.LUCENE_35));
        query.addTerms(pair.getOriginal(), QueryService.FIELD_VALUE, 0.6f, 3);
        SearchResult result = queryService.search(query,20);

        if(result==null)
            return Collections.emptyList();
        Set<Similarity> resultList = new TreeSet<SimilarStringsToolPanel.Similarity>();
        TopDocs topDocs = result.getTopDocs();
        ScoreDoc[] doc = topDocs.scoreDocs;
        for (ScoreDoc scoreDoc : doc)
        {
            if(scoreDoc.score<0.20)
                continue;
            
            try
            {
                Document document = result.getSearcher().doc(scoreDoc.doc);
                
                PropertyFileDescriptor descriptor = queryService.getDescriptor(document);

            if(descriptor==null)
                continue;
            PropertyFileDescriptor slave = getSlave(descriptor,pair.getLanguage());
            if(slave==null)
                continue;
            
            PropertyFile properties = slave.loadProperties();
            String key = document.get(QueryService.FIELD_KEY);
            
            if(slave.cdoID().equals(pair.getDescriptorID()) && pair.getKey().equals(key))
            	continue; //that means we found the current property as a similarity. Very helpful...
            Property property = properties.getProperty(key);
//            if(pair.getTranslated()!=null && pair.getTranslated().equals(property.getValue()))
//            	//the translations are identical. That won't help
//            	continue;
            
            if(property==null || property.getValue()==null)
                continue;
            
            Similarity similarity = new Similarity(document.get(QueryService.FIELD_VALUE), property.getValue(),(int)(scoreDoc.score*100), document.get(QueryService.FIELD_FULL_PATH));
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
        return new ArrayList<SimilarStringsToolPanel.Similarity>(resultList);

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

    
    public static class Similarity implements Serializable, Comparable<Similarity>
    {
        
		private static final long serialVersionUID = 1L;
		private String original;
        private String translation;
        private int similarity;
		private String fullPath;


        public Similarity(String original, String translation, int similartiy, String fullPath)
        {
            super();
            this.original = original;
            this.translation = translation;
            this.similarity = similartiy;
            this.fullPath = fullPath;
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
        
        public String getFullPath() {
			return fullPath;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((fullPath == null) ? 0 : fullPath.hashCode());
			result = prime * result + ((original == null) ? 0 : original.hashCode());
			result = prime * result + similarity;
			result = prime * result + ((translation == null) ? 0 : translation.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Similarity other = (Similarity) obj;
			if (fullPath == null) {
				if (other.fullPath != null)
					return false;
			} else if (!fullPath.equals(other.fullPath))
				return false;
			if (original == null) {
				if (other.original != null)
					return false;
			} else if (!original.equals(other.original))
				return false;
			if (similarity != other.similarity)
				return false;
			if (translation == null) {
				if (other.translation != null)
					return false;
			} else if (!translation.equals(other.translation))
				return false;
			return true;
		}

		@Override
		public int compareTo(Similarity o) {
			return o.getSimilarity() - getSimilarity();
		}
        
        
    }
}
