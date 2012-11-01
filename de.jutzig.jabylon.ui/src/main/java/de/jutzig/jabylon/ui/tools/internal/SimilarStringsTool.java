/*
 * SimilarStringsTool.java
 *
 * created at 15.05.2012 by utzig <YOURMAILADDRESS>
 *
 * Copyright (c) SEEBURGER AG, Germany. All Rights Reserved.
 */
package de.jutzig.jabylon.ui.tools.internal;

import java.io.IOException;
import java.util.Collection;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.search.FuzzyLikeThisQuery;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.util.Version;
import org.eclipse.emf.common.util.EList;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Component;
import com.vaadin.ui.Table;

import de.jutzig.jabylon.index.properties.QueryService;
import de.jutzig.jabylon.index.properties.SearchResult;
import de.jutzig.jabylon.properties.ProjectLocale;
import de.jutzig.jabylon.properties.ProjectVersion;
import de.jutzig.jabylon.properties.Property;
import de.jutzig.jabylon.properties.PropertyFile;
import de.jutzig.jabylon.properties.PropertyFileDescriptor;
import de.jutzig.jabylon.properties.Review;
import de.jutzig.jabylon.ui.Activator;
import de.jutzig.jabylon.ui.container.PropertyPairContainer.PropertyPairItem;
import de.jutzig.jabylon.ui.styles.JabylonStyle;
import de.jutzig.jabylon.ui.tools.PropertyEditorTool;
import de.jutzig.jabylon.ui.tools.SuggestionAcceptor;


/**
 * TODO short description for SimilarStringsTool.
 * <p>
 * Long description for SimilarStringsTool.
 *
 * @author utzig
 */
public class SimilarStringsTool
    implements PropertyEditorTool
{

    private PropertyFileDescriptor translation;
    private BeanItemContainer<Similarity> container;


    /**
     *
     */
    public SimilarStringsTool()
    {
        // TODO Auto-generated constructor stub
    }


    /**
     * @see de.jutzig.jabylon.ui.tools.PropertyEditorTool#selectionChanged(de.jutzig.jabylon.ui.container.PropertyPairContainer.PropertyPairItem, java.util.Collection)
     */
    @Override
    public void selectionChanged(PropertyPairItem currentSelection, Collection<Review> reviews, SuggestionAcceptor acceptor)
    {
        QueryService service = Activator.getDefault().getQueryService();
        container.removeAllItems();

//        BooleanQuery query = new BooleanQuery();
        FuzzyLikeThisQuery query = new FuzzyLikeThisQuery(10, new StandardAnalyzer(Version.LUCENE_29));
        query.addTerms(currentSelection.getSourceProperty().getValue(), QueryService.FIELD_VALUE, 0.6f, 3);
        SearchResult result = service.search(query,20);

        if(result==null)
            return;
        TopDocs topDocs = result.getTopDocs();
        ScoreDoc[] doc = topDocs.scoreDocs;
        for (ScoreDoc scoreDoc : doc)
        {
            if(scoreDoc.score<0.20)
                continue;
            PropertyFileDescriptor descriptor;
            try
            {
                Document document = result.getSearcher().doc(scoreDoc.doc);
                descriptor = service.getDescriptor(document, translation.cdoView());

            if(descriptor==null)
                continue;
            PropertyFileDescriptor slave = getSlave(descriptor);
            if(slave==null)
                continue;
            
            PropertyFile properties = slave.loadProperties();
            String key = document.get(QueryService.FIELD_KEY);
            if(slave==translation && currentSelection.getKey().equals(key))
            	continue; //that means we found the current property as a similarity. Very helpful...
            Property property = properties.getProperty(key);
            
            if(property==null || property.getValue()==null)
                continue;
            Similarity similarity = new Similarity(document.get(QueryService.FIELD_VALUE), property.getValue(),(int)(scoreDoc.score*100));
            container.addBean(similarity);
            }
            catch (CorruptIndexException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            catch (IOException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
        try
        {
            result.getSearcher().close();
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }


    private PropertyFileDescriptor getSlave(PropertyFileDescriptor descriptor)
    {
        ProjectVersion version = descriptor.getProjectLocale().getParent();
        ProjectLocale locale = version.getProjectLocale(translation.getVariant());
        if(locale==null)
            return null;
        EList<PropertyFileDescriptor> descriptors = locale.getDescriptors();
        for (PropertyFileDescriptor propertyFileDescriptor : descriptors)
        {
//            if(propertyFileDescriptor==translation)
//                return null;
            if(propertyFileDescriptor.getMaster()==descriptor)
                return propertyFileDescriptor;
        }
        return null;
    }


    /**
     * @see de.jutzig.jabylon.ui.tools.PropertyEditorTool#init(de.jutzig.jabylon.properties.PropertyFileDescriptor, de.jutzig.jabylon.properties.PropertyFileDescriptor)
     */
    @Override
    public void init(PropertyFileDescriptor template, PropertyFileDescriptor translation)
    {
        this.translation = translation;

    }


    /**
     * @see de.jutzig.jabylon.ui.tools.PropertyEditorTool#createComponent()
     */
    @Override
    public Component createComponent()
    {
        Table table = new Table();
        table.setSizeFull();
        table.addStyleName(JabylonStyle.TABLE_STRIPED.getCSSName());
        container = new BeanItemContainer<Similarity>(Similarity.class);
        table.setContainerDataSource(container);
        table.setVisibleColumns(new Object[]{"original","translation","similarity"});
        table.setColumnExpandRatio("original", 1f);
        table.setColumnExpandRatio("translation", 1f);
        table.setColumnExpandRatio("similarity", 0f);
        return table;
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



