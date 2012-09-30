package de.jutzig.jabylon.rest.ui.wicket.panels;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.extensions.ajax.markup.html.repeater.data.table.AjaxFallbackDefaultDataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.FilterForm;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.IFilterStateLocator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.TextFilteredPropertyColumn;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.markup.html.internal.HtmlHeaderContainer;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import de.jutzig.jabylon.properties.Property;
import de.jutzig.jabylon.properties.PropertyFile;
import de.jutzig.jabylon.properties.PropertyFileDescriptor;
import de.jutzig.jabylon.rest.ui.model.EClassSortState;
import de.jutzig.jabylon.rest.ui.model.EObjectModel;
import de.jutzig.jabylon.rest.ui.model.PropertyPair;
import de.jutzig.jabylon.rest.ui.wicket.BasicResolvablePanel;

public class PropertyEditorPanel extends BasicResolvablePanel<PropertyFileDescriptor> {

	public PropertyEditorPanel(PropertyFileDescriptor object, PageParameters parameters) {
		super("content", object, parameters);
		PropertyPairDataProvider provider = new PropertyPairDataProvider(object);
		ListView<PropertyPair> properties = new ListView<PropertyPair>("properties",provider.createContents()) {
			@Override
			protected void populateItem(ListItem<PropertyPair> item) {
				SinglePropertyEditor editor = new SinglePropertyEditor("editor", item.getModel());
				item.add(editor);
				
			}
		};
//		FilterForm<String> form=new FilterForm<String>("form",provider);
//		List<IColumn<PropertyPair, EClassSortState>> columns = new ArrayList<IColumn<PropertyPair, EClassSortState>>();
//		columns.add(new TextFilteredPropertyColumn<PropertyPair, String, EClassSortState>(Model.of("Original"), "original"));
//		columns.add(new TextFilteredPropertyColumn<PropertyPair, String, EClassSortState>(Model.of("Translated"), "translated"));
//		AjaxFallbackDefaultDataTable<PropertyPair, EClassSortState> table = new AjaxFallbackDefaultDataTable<PropertyPair, EClassSortState>("table", columns, provider, 50){
//			@Override
//			protected Item<PropertyPair> newRowItem(String id, int index, IModel<PropertyPair> model) {
//				Item<PropertyPair> rowItem = super.newRowItem(id, index, model);
//				//TODO: better do this with javascript
//				PropertyPair pair = rowItem.getModel().getObject();
//				if(pair.getOriginal().isEmpty() || pair.getTranslated().isEmpty())
//					rowItem.add(new AttributeModifier("class", "error"));
//				return rowItem;
//			}
//		};
//		form.add(table);
//		add(form);
		
		add(properties);

	}
	
	@Override
	public void renderHead(HtmlHeaderContainer container) {
		// TODO Auto-generated method stub
		super.renderHead(container);
		
	}

}

class PropertyPairDataProvider extends SortableDataProvider<PropertyPair, EClassSortState> implements IFilterStateLocator<String> {

	private CompoundPropertyModel<PropertyFileDescriptor> model;
	private transient List<PropertyPair> contents;
	private String filterState;

	public PropertyPairDataProvider(PropertyFileDescriptor descriptor) {
		super();
		model = new CompoundPropertyModel<PropertyFileDescriptor>(new EObjectModel<PropertyFileDescriptor>(descriptor));
	}

	@Override
	public Iterator<? extends PropertyPair> iterator(long first, long count) {
		List<PropertyPair> contents = getList();
		return contents.subList((int) first, (int) first + (int) count).iterator();
	}

	private List<PropertyPair> getList() {
		if (contents == null) {
			contents = createContents();
		}
		return contents;
	}

	protected List<PropertyPair> createContents() {
		PropertyFileDescriptor descriptor = model.getObject();
		PropertyFileDescriptor master = descriptor.getMaster();
		Map<String, Property> translated = descriptor.loadProperties().asMap();
		PropertyFile templateFile = master.loadProperties();
		
		List<PropertyPair> contents = new ArrayList<PropertyPair>();
		for (Property property : templateFile.getProperties()) {
//			IModel<String> bind = model.bind(property.getKey());
//			bind.set
			contents.add(new PropertyPair(property, translated.remove(property.getKey())));
		}
		for (Property property : translated.values()) {
			contents.add(new PropertyPair(null, property));
		}
		return contents;
	}

	@Override
	public long size() {
		return getList().size();
	}

	@Override
	public IModel<PropertyPair> model(PropertyPair object) {
		return Model.of(object);
	}


	@Override
	public void setFilterState(String state) {
		this.filterState = state;
		
	}

	@Override
	public String getFilterState() {
		return filterState;
	}

}
