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
package org.jabylon.rest.ui.tools;

import java.io.IOException;
import java.io.Serializable;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.eclipse.emf.cdo.common.id.CDOID;
import org.eclipse.emf.common.util.URI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.jabylon.common.resolver.URIResolver;
import org.jabylon.index.properties.QueryService;
import org.jabylon.index.properties.SearchResult;
import org.jabylon.properties.PropertiesFactory;
import org.jabylon.properties.PropertiesPackage;
import org.jabylon.properties.PropertyFileDescriptor;
import org.jabylon.rest.ui.model.PropertyPair;
import org.jabylon.rest.ui.util.WicketUtil;
import org.jabylon.rest.ui.wicket.pages.ResourcePage;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 *
 */
public class OtherTranslationsToolPanel extends GenericPanel<PropertyPair> {

    private static final long serialVersionUID = 1L;
    private static Logger logger = LoggerFactory.getLogger(OtherTranslationsToolPanel.class);
    @Inject
    private transient QueryService queryService;

    @Inject
    private transient URIResolver resolver;

    public OtherTranslationsToolPanel(String id, IModel<PropertyPair> model) {
        super(id, model);
        ;

    }

    @Override
    protected void onBeforeRender() {
        List<MatchResult> result = doSearch(getModel());
        ListView<MatchResult> list = new ListView<MatchResult>("children", result) {

            private static final long serialVersionUID = 1L;

            @Override
            protected void populateItem(ListItem<MatchResult> item) {
                MatchResult match = item.getModelObject();
                item.add(new Label("locale", match.getLocale().getDisplayName(getLocale())));
                item.add(new Label("translation", match.getValue()));
                PageParameters params = new PageParameters();

                URI uri = URI.createURI(match.getUri());
                for (int i = 0; i < uri.segmentCount(); i++) {
                    params.set(i, uri.segment(i));
                }
                params.add("key", OtherTranslationsToolPanel.this.getModel().getObject().getKey());
                item.add(new BookmarkablePageLink<Void>("link", ResourcePage.class, params));

                Image image = new Image("flag", WicketUtil.getIconForLocale(match.getLocale()));
                item.add(image);

            }
        };
        addOrReplace(list);
        super.onBeforeRender();
    }

    protected List<MatchResult> doSearch(IModel<PropertyPair> model) {
        long time = System.currentTimeMillis();
        PropertyPair pair = model.getObject();
        if (pair == null || pair.getOriginal() == null)
            return Collections.emptyList();

        CDOID descriptorID = model.getObject().getDescriptorID();
        PropertyFileDescriptor descriptor = (PropertyFileDescriptor) resolver.resolve(descriptorID);
        if(descriptor.getMaster()==null)
            return Collections.emptyList();
        BooleanQuery query = new BooleanQuery();

        query.add(new TermQuery(new Term(QueryService.FIELD_TEMPLATE_LOCATION, descriptor.getMaster().getLocation().toString())), Occur.MUST);
        query.add(new TermQuery(new Term(QueryService.FIELD_KEY, pair.getKey())), Occur.MUST);

        // exclude all masters from the search
        query.add(new TermQuery(new Term(QueryService.FIELD_LOCALE, QueryService.MASTER)), Occur.MUST_NOT);
        // exclude the current language
        query.add(new TermQuery(new Term(QueryService.FIELD_LOCALE, descriptor.getProjectLocale().getLocale().toString())), Occur.MUST_NOT);

        SearchResult result = queryService.search(query, 50);

        if (result == null)
            return Collections.emptyList();
        List<MatchResult> resultSet = new ArrayList<OtherTranslationsToolPanel.MatchResult>();
        TopDocs topDocs = result.getTopDocs();
        ScoreDoc[] doc = topDocs.scoreDocs;
        for (ScoreDoc scoreDoc : doc) {

            try {
                Document document = result.getSearcher().doc(scoreDoc.doc);

                PropertyFileDescriptor foundDescriptor = queryService.getDescriptor(document);
                if(foundDescriptor==null)
                    continue;

                String uri = foundDescriptor.toURI().toString();
                Locale locale = (Locale) PropertiesFactory.eINSTANCE.createFromString(PropertiesPackage.Literals.LOCALE, document.get(QueryService.FIELD_LOCALE));
                MatchResult match = new MatchResult(document.get(QueryService.FIELD_VALUE), locale, uri);
                resultSet.add(match);
            } catch (CorruptIndexException e) {
                logger.error("Failed to find other translations", e);
            } catch (IOException e) {
                logger.error("Failed to find other translations", e);
            }

        }
        try {
            result.getSearcher().close();
            logger.debug("Finding other translations took {} ms",System.currentTimeMillis()-time);
        } catch (IOException e) {
            logger.error("Failed to close searcher", e);
        }
        Collections.sort(resultSet, new MatchResultComparator(getLocale()));
        return resultSet;

    }

    public static class MatchResult implements Serializable {

        private static final long serialVersionUID = 1L;
        private String uri;
        private Locale locale;
        private String value;

        public MatchResult(String value, Locale locale, String uri) {
            super();
            this.value = value;
            this.locale = locale;
            this.uri = uri;
        }

        public Locale getLocale() {
            return locale;
        }

        public String getUri() {
            return uri;
        }

        public String getValue() {
            return value;
        }
    }

    private static class MatchResultComparator implements Comparator<MatchResult> {

        private Collator collator;
        private Locale locale;

        public MatchResultComparator(Locale locale) {
            collator = Collator.getInstance(locale);
            this.locale = locale;
        }

        @Override
        public int compare(MatchResult o1, MatchResult o2) {
//			System.out.print(o2.getLocale());
//			System.out.print(" -> ");
//			System.out.print(o1.getLocale());
//			System.out.print(" = ");
//			System.out.println(collator.compare(o1.getLocale(),o2.getLocale()));
            return collator.compare(o1.getLocale().getDisplayLanguage(locale),o2.getLocale().getDisplayLanguage(locale));
        }

    }
}
