package de.jutzig.jabylon.rest.ui.wicket.panels;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.IFilterStateLocator;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.SubmitLink;
import org.apache.wicket.markup.html.internal.HtmlHeaderContainer;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Predicate;

import de.jutzig.jabylon.properties.Property;
import de.jutzig.jabylon.properties.PropertyFile;
import de.jutzig.jabylon.properties.PropertyFileDescriptor;
import de.jutzig.jabylon.resources.persistence.PropertyPersistenceService;
import de.jutzig.jabylon.rest.ui.Activator;
import de.jutzig.jabylon.rest.ui.model.EClassSortState;
import de.jutzig.jabylon.rest.ui.model.EObjectModel;
import de.jutzig.jabylon.rest.ui.model.PropertyPair;
import de.jutzig.jabylon.rest.ui.wicket.BasicResolvablePanel;

public class PropertyEditorPanel extends BasicResolvablePanel<PropertyFileDescriptor> {

	
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(PropertyEditorPanel.class);
	private transient boolean firstExpandend = false;
	
	public PropertyEditorPanel(PropertyFileDescriptor object, PageParameters parameters) {
		super("content", object, parameters);

		PropertyListMode mode = PropertyListMode.getByName(parameters.get("mode").toString("ALL"));
		addLinkList(mode);
		PropertyPairDataProvider provider = new PropertyPairDataProvider(object, mode);
		List<PropertyPair> contents = provider.createContents();
		ListView<PropertyPair> properties = new ListView<PropertyPair>("repeater", contents) {

			private static final long serialVersionUID = -7087485011138279358L;

			@Override
			protected void populateItem(final ListItem<PropertyPair> item) {
				//automatically expand the first property that needs work done
				boolean expand = false;
				if(!firstExpandend && isFuzzy(item.getModelObject()))
				{
					firstExpandend = true;
					expand = true;
				}
				item.add(new SinglePropertyEditor("row", item.getModel(), expand));
			}

		};
		properties.setOutputMarkupId(true);

		Form<List<? extends PropertyPair>> form = new Form<List<? extends PropertyPair>>("properties-form", Model.ofList(contents)) {
			
			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit() {
				super.onSubmit();
				IModel<List<? extends PropertyPair>> model = getModel();
				List<? extends PropertyPair> list = model.getObject();
				PropertyPersistenceService service = Activator.getDefault().getPropertyPersistenceService();
				PropertyFileDescriptor descriptor = PropertyEditorPanel.this.getModelObject();
				PropertyFile file = descriptor.loadProperties();
				Map<String, Property> map = file.asMap();

				for (PropertyPair pair : list) {
					Property translation = pair.getTranslation();
					if(translation==null)
						continue;
					if (map.containsKey(translation.getKey())) {
						Property property = map.get(translation.getKey());
						property.setComment(translation.getComment());
						property.setValue(translation.getValue());
					} else
						file.getProperties().add(translation);
				}
				service.saveProperties(descriptor, file);
				getSession().info("Saved successfully");
				try {
					//TODO: this is very unclean...
					// give it some time to store the values
					Thread.sleep(500);
				} catch (InterruptedException e) {
					logger.error("Interrupted while waiting for property persistence",e);
				}
			}
		};

		add(form);
		form.add(new SubmitLink("properties-submit"));
		form.add(properties);
	}


	private boolean isFuzzy(PropertyPair pair)
	{
		return PropertyListMode.MISSING.apply(pair);
	}
	
	
	private void addLinkList(final PropertyListMode currentMode) {
		List<PropertyListMode> values = Arrays.asList(PropertyListMode.values());
		ListView<PropertyListMode> mode = new ListView<PropertyListMode>("view-mode", values) {

			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(ListItem<PropertyListMode> item) {

				String mode = item.getModelObject().name().toLowerCase();
				item.add(new ExternalLink("link", "?mode=" + mode, "Show " + mode));
				if (item.getModelObject() == currentMode)
					item.add(new AttributeModifier("class", "active"));
			}
		};
		add(mode);

	}

	@Override
	public void renderHead(HtmlHeaderContainer container) {
		// TODO Auto-generated method stub
		super.renderHead(container);

	}

}

class PropertyPairDataProvider extends SortableDataProvider<PropertyPair, EClassSortState> implements IFilterStateLocator<String> {

	private static final long serialVersionUID = 1L;
	private CompoundPropertyModel<PropertyFileDescriptor> model;
	private transient List<PropertyPair> contents;
	private String filterState;
	private PropertyListMode mode;

	public PropertyPairDataProvider(PropertyFileDescriptor descriptor, PropertyListMode mode) {
		super();
		model = new CompoundPropertyModel<PropertyFileDescriptor>(new EObjectModel<PropertyFileDescriptor>(descriptor));
		this.mode = mode;
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
			// IModel<String> bind = model.bind(property.getKey());
			// bind.set
			PropertyPair pair = new PropertyPair(property, translated.remove(property.getKey()));
			if (mode.apply(pair))
				contents.add(pair);
		}
		for (Property property : translated.values()) {
			PropertyPair pair = new PropertyPair(null, property);
			if (mode.apply(pair))
				contents.add(pair);
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

enum PropertyListMode implements Predicate<PropertyPair> {

	ALL {
		@Override
		public boolean apply(PropertyPair pair) {
			return true;
		}
	},
	MISSING

	{
		@Override
		public boolean apply(PropertyPair pair) {
			return pair.getOriginal() == null || pair.getTranslated() == null || pair.getOriginal().isEmpty()
					|| pair.getTranslated().isEmpty();
		}
	},
	FUZZY {
		@Override
		public boolean apply(PropertyPair pair) {
			// TODO Auto-generated method stub
			return false;
		}
	};

	public static PropertyListMode getByName(String name) {
		if (name == null || name.isEmpty())
			return MISSING;
		return valueOf(name.toUpperCase());
	}
}
