/**
 * 
 */
package de.jutzig.jabylon.rest.ui.tools;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
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

import de.jutzig.jabylon.common.resolver.URIResolver;
import de.jutzig.jabylon.index.properties.QueryService;
import de.jutzig.jabylon.index.properties.SearchResult;
import de.jutzig.jabylon.properties.PropertyFileDescriptor;
import de.jutzig.jabylon.rest.ui.model.PropertyPair;
import de.jutzig.jabylon.rest.ui.util.WicketUtil;
import de.jutzig.jabylon.rest.ui.wicket.pages.ResourcePage;

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
				item.add(new Label("locale", new Locale(match.getLocale()).getDisplayName(getLocale())));
				item.add(new Label("translation", match.getValue()));
				PageParameters params = new PageParameters();

				URI uri = URI.createURI(match.getUri());
				for (int i = 0; i < uri.segmentCount(); i++) {
					params.set(i, uri.segment(i));
				}
				params.add("key", OtherTranslationsToolPanel.this.getModel().getObject().getKey());
				item.add(new BookmarkablePageLink<Void>("link", ResourcePage.class, params));
				
				Image image = new Image("flag", WicketUtil.getIconForLocale(new Locale(match.getLocale())));
				item.add(image);

			}
		};
		addOrReplace(list);
		super.onBeforeRender();
	}

	protected List<MatchResult> doSearch(IModel<PropertyPair> model) {

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

		SearchResult result = queryService.search(query, 50);

		if (result == null)
			return Collections.emptyList();
		Collection<MatchResult> resultList = new ArrayList<OtherTranslationsToolPanel.MatchResult>();
		TopDocs topDocs = result.getTopDocs();
		ScoreDoc[] doc = topDocs.scoreDocs;
		for (ScoreDoc scoreDoc : doc) {

			try {
				Document document = result.getSearcher().doc(scoreDoc.doc);

				PropertyFileDescriptor foundDescriptor = queryService.getDescriptor(document);
				if(foundDescriptor==null)
					continue;

				String uri = foundDescriptor.getProjectLocale().getParent().getParent().getName() + "/" + foundDescriptor.getProjectLocale().getParent().getName() + "/"
						+ foundDescriptor.getProjectLocale().getName() + foundDescriptor.getLocation().toString();
				MatchResult match = new MatchResult(document.get(QueryService.FIELD_VALUE), document.get(QueryService.FIELD_LOCALE), uri);
				resultList.add(match);
			} catch (CorruptIndexException e) {
				logger.error("Failed to find other translations", e);
			} catch (IOException e) {
				logger.error("Failed to find other translations", e);
			}

		}
		try {
			result.getSearcher().close();
		} catch (IOException e) {
			logger.error("Failed to close searcher", e);
		}
		return new ArrayList<OtherTranslationsToolPanel.MatchResult>(resultList);

	}

	public static class MatchResult implements Serializable {

		private static final long serialVersionUID = 1L;
		private String uri;
		private String locale;
		private String value;

		public MatchResult(String value, String locale, String uri) {
			super();
			this.value = value;
			this.locale = locale;
			this.uri = uri;
		}
		
		public String getLocale() {
			return locale;
		}
		
		public String getUri() {
			return uri;
		}
		
		public String getValue() {
			return value;
		}

	}
}
