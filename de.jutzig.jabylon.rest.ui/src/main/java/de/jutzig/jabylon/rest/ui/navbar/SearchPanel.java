/**
 * 
 */
package de.jutzig.jabylon.rest.ui.navbar;

import java.io.Serializable;
import java.text.MessageFormat;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.form.StatelessForm;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.Url;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.eclipse.emf.common.util.URI;

import de.jutzig.jabylon.properties.Resolvable;
import de.jutzig.jabylon.rest.ui.wicket.BasicPanel;
import de.jutzig.jabylon.rest.ui.wicket.PanelFactory;
import de.jutzig.jabylon.rest.ui.wicket.pages.SearchPage;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 * 
 */
public class SearchPanel<T> extends BasicPanel<T> {

	private static final long serialVersionUID = 1L;

	public SearchPanel(String id, IModel<T> object, PageParameters parameters) {
		super(id, object, parameters);
		add(new SearchForm("form"));
		setVersioned(false);
	}



	public static class SearchPanelFactory implements PanelFactory, Serializable {
		private static final long serialVersionUID = 1L;

		@Override
		public <T> Panel createPanel(PageParameters params, IModel<T> input, String id) {

			return new SearchPanel<T>(id, input, params);

		}

	}

	public class SearchForm extends StatelessForm<Void> {
		private static final long serialVersionUID = 1L;
		private String searchString;

		public SearchForm(String id) {
			super(id);
			TextField<String> field = new TextField<String>("searchfield", new PropertyModel<String>(this, "searchString"));
			String placeholder = getPlaceholder(SearchPanel.this.getModel());
			field.add(new AttributeModifier("placeholder", placeholder));
			add(field);

		}
		public void onSubmit() {
			Url url = getRequest().getUrl();
			URI uri = URI.createURI(url.getPath(),false);

			PageParameters params = new PageParameters();
			params.add(SearchPage.SEARCH_TERM, getSearchString());
			params.add(SearchPage.SCOPE, uri);
			setResponsePage(SearchPage.class, params);
		}

		public String getSearchString() {
			return searchString;
		}

		public void setSearchString(String searchString) {
			this.searchString = searchString;
		}
		
		private String getPlaceholder(IModel<T> model) {
			if (model == null)
				return "Search";
			T object = model.getObject();
			if (object instanceof Resolvable<?, ?>) {
				Resolvable<?, ?> r = (Resolvable<?, ?>) object;
				if (r.getName() != null && !r.getName().isEmpty()) {
					String message = "Search {0}";
					return MessageFormat.format(message, r.getName());
				}
			}
			return "Search";
		}
	}
}
