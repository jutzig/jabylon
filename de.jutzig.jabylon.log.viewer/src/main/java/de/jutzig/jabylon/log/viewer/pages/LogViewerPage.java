/**
 * 
 */
package de.jutzig.jabylon.log.viewer.pages;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.Iterator;

import javax.inject.Inject;

import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.PriorityHeaderItem;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.osgi.service.log.LogEntry;
import org.osgi.service.log.LogReaderService;
import org.osgi.service.log.LogService;

import de.jutzig.jabylon.rest.ui.wicket.JabylonApplication;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 *
 */
public class LogViewerPage extends WebPage{

	private static final long serialVersionUID = 1L;
	@Inject
	private transient LogReaderService logReader;
	
	@Inject
	private transient LogService logService;
	
	public LogViewerPage() {
		
	}
	
	@Override
	public void renderHead(IHeaderResponse response) {
		response.render(new PriorityHeaderItem(JavaScriptHeaderItem.forReference(JabylonApplication.get().getJavaScriptLibrarySettings().getJQueryReference())));
		response.render(new PriorityHeaderItem(JavaScriptHeaderItem.forUrl("/jabylon/bootstrap/js/bootstrap.min.js")));
		super.renderHead(response);
	}

	
	@Override
	protected void onBeforeRender() {
		logService.log(LogService.LOG_ERROR, "doing stuff");
		final IDataProvider<LogEntry> provider = new IDataProvider<LogEntry>() {

			@Override
			public void detach() {
				// TODO Auto-generated method stub
				
			}

			@Override
			public Iterator<? extends LogEntry> iterator(long first, long count) {
				return new EnumeratorIterator(logReader.getLog());
			}

			@Override
			public long size() {
				return 100;
			}

			@SuppressWarnings({ "rawtypes", "unchecked" })
			@Override
			public IModel<LogEntry> model(LogEntry object) {
				IModel model = Model.of((Serializable)object);
				return model;
			}
			
		};
		
		final DataView<LogEntry> dataView = new DataView<LogEntry>("children", provider) {

			private static final long serialVersionUID = 1l;

			@Override
			protected void populateItem(Item<LogEntry> item) {
				LogEntry modelObject = item.getModelObject();
				item.add(new Label("bundle",modelObject.getBundle().getSymbolicName()));
				item.add(new Label("level",String.valueOf(modelObject.getLevel())));
				item.add(new Label("message",String.valueOf(modelObject.getMessage())));
			}

		};
		// dataView.setItemsPerPage(10);
		add(dataView);
		super.onBeforeRender();
	}
	
}
class EnumeratorIterator implements Iterator<LogEntry>
{

	private Enumeration enumeration;
	
	public EnumeratorIterator(Enumeration enumeration) {
		super();
		this.enumeration = enumeration;
	}

	@Override
	public boolean hasNext() {
		return enumeration.hasMoreElements();
	}

	@Override
	public LogEntry next() {
		return (LogEntry) enumeration.nextElement();
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
		
	}
	
}
