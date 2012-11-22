/**
 * 
 */
package de.jutzig.jabylon.rest.ui.wicket.panels;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.search.ScoreDoc;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.eclipse.emf.common.util.URI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.jutzig.jabylon.index.properties.QueryService;
import de.jutzig.jabylon.index.properties.SearchResult;
import de.jutzig.jabylon.rest.ui.wicket.pages.ResourcePage;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 * 
 */
public class SearchResultPanel<T> extends GenericPanel<T> {

	private static final long serialVersionUID = 1L;

	@Inject
	private transient QueryService queryService;

	private static final Logger logger = LoggerFactory.getLogger(SearchResultPanel.class);

	public SearchResultPanel(String id, final SearchResult result, PageParameters parameters) {
		super(id);

		List<ScoreDoc> list = Arrays.asList(result.getTopDocs().scoreDocs);
		ListView<ScoreDoc> repeater = new ListView<ScoreDoc>("results", list) {

			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(ListItem<ScoreDoc> item) {
				ScoreDoc doc = item.getModelObject();
				Document document;
				try {
					
					document = result.getSearcher().doc(doc.doc);
					item.add(new Label("value", document.get(QueryService.FIELD_VALUE)));
					item.add(new Label("key", document.get(QueryService.FIELD_KEY)));
					String projectLabel = "{0} ({1})";
					String projectName = document.get(QueryService.FIELD_PROJECT);
					String projectVersion = document.get(QueryService.FIELD_VERSION);
					String projectLocale = document.get(QueryService.FIELD_LOCALE);
					String descriptorURI = document.get(QueryService.FIELD_URI);
					PageParameters params = new PageParameters();
					params.set(0, projectName);
					params.set(1, projectVersion);
					params.set(2, projectLocale);
					URI uri = URI.createURI(descriptorURI);
					
					int startParam = 3;
					for (int i = startParam; i < uri.segmentCount()+startParam; i++) {
						params.set(i, uri.segment(i-startParam));
					}
					BookmarkablePageLink<Void> link = new BookmarkablePageLink<Void>("link", ResourcePage.class, params);
					link.add(new Label("title", uri.lastSegment()));
					item.add(link);
					projectLabel = MessageFormat.format(projectLabel, projectName, projectVersion);
					item.add(new Label("project", projectLabel));
					item.add(new Label("comment", document.get(QueryService.FIELD_COMMENT)));

				} catch (CorruptIndexException e) {
					error(e.getMessage());
					logger.error("Search failed", e);
				} catch (IOException e) {
					error(e.getMessage());
					logger.error("Search failed", e);
				}

			}

		};
		add(repeater);
	}

	public QueryService getQueryService() {
		return queryService;
	}

}
