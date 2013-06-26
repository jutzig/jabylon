package de.jutzig.jabylon.rest.ui.wicket.panels;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.CallbackParameter;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.IFilterStateLocator;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.SubmitLink;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.string.StringValue;
import org.eclipse.emf.common.util.EList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import de.jutzig.jabylon.properties.Property;
import de.jutzig.jabylon.properties.PropertyFile;
import de.jutzig.jabylon.properties.PropertyFileDescriptor;
import de.jutzig.jabylon.properties.Review;
import de.jutzig.jabylon.resources.persistence.PropertyPersistenceService;
import de.jutzig.jabylon.rest.ui.model.EClassSortState;
import de.jutzig.jabylon.rest.ui.model.EObjectModel;
import de.jutzig.jabylon.rest.ui.model.PropertyPair;
import de.jutzig.jabylon.rest.ui.util.GlobalResources;
import de.jutzig.jabylon.rest.ui.util.WebContextUrlResourceReference;
import de.jutzig.jabylon.rest.ui.wicket.BasicResolvablePanel;

/**
 *
 * has been replaced with PropertyEditorSinglePanel and PropertyListPanel
 * <p>
 * Long description for PropertyEditorPanel.
 *
 * @author utzig
 */
@Deprecated
public class PropertyEditorPanel extends BasicResolvablePanel<PropertyFileDescriptor> {


	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(PropertyEditorPanel.class);
	IModel<Multimap<String, Review>> reviewModel;

	@Inject
	private transient PropertyPersistenceService propertyPersistence;

	public PropertyEditorPanel(PropertyFileDescriptor object, PageParameters parameters) {
		super("content", object, parameters);

		PropertyListMode mode = PropertyListMode.getByName(parameters.get("mode").toString("ALL"));
		addLinkList(mode);
		reviewModel = new LoadableDetachableModel<Multimap<String,Review>>() {

			private static final long serialVersionUID = 1L;

			@Override
			protected Multimap<String, Review> load() {
				return buildReviewMap(getModelObject());
			}
		};
		PropertyPairDataProvider provider = new PropertyPairDataProvider(object, mode, reviewModel);
		List<PropertyPair> contents = provider.createContents();

		ListView<PropertyPair> properties = new ListView<PropertyPair>("repeater", contents) {

			private static final long serialVersionUID = -7087485011138279358L;

			@Override
			protected void populateItem(final ListItem<PropertyPair> item) {
				IModel<PropertyPair> model = item.getModel();
				String key = model.getObject().getKey();
				Collection<Review> reviewList = reviewModel.getObject().get(key);
				item.add(new SinglePropertyEditor("row", model, false, reviewList));
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
				propertyPersistence.saveProperties(descriptor, file);
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
		form.add(new SubmitLink("properties-submit")
		{
			private static final long serialVersionUID = 1L;

			@Override
			protected String getTriggerJavaScript() {
				/* disables the default submit behaviour since we do that ourselfs in JS
				 * https://github.com/jutzig/jabylon/issues/52
				 * see propertyEditor.js
				 */
				return "";
			}
		});
		form.add(properties);

		final PropertiesEditorToolbar editorToolbar = new PropertiesEditorToolbar("properties-toolbar", getModel(), getPageParameters());
		editorToolbar.setOutputMarkupId(true);
		add(editorToolbar);


		final AbstractDefaultAjaxBehavior behave = new AbstractDefaultAjaxBehavior() {
		    protected void respond(final AjaxRequestTarget target) {
		    	editorToolbar.respond(target);

		        StringValue parameter = RequestCycle.get().getRequest().getRequestParameters().getParameterValue("key");
		        editorToolbar.setKey(parameter.toString(""));
		    }

			public CharSequence getCallbackFunction(String functionName, CallbackParameter... extraParameters)
			{
				StringBuilder sb = new StringBuilder();
				sb.append("function ");
				sb.append(functionName);
				sb.append(" (");
				boolean first = true;
				for (CallbackParameter curExtraParameter : extraParameters)
				{
					if (curExtraParameter.getFunctionParameterName() != null)
					{
						if (!first)
							sb.append(',');
						else
							first = false;
						sb.append(curExtraParameter.getFunctionParameterName());
					}
				}
				sb.append(") {\n");
				sb.append(getCallbackFunctionBody(extraParameters));
				sb.append("}\n");
				return sb;
			}

		    @Override
		    public void renderHead(Component component, IHeaderResponse response) {
		    	response.render(JavaScriptHeaderItem.forScript(getCallbackFunction("requestAid",CallbackParameter.explicit("key")), "requestAid"));
		    	super.renderHead(component, response);
		    }

		};

		add(behave);

	}

	
	@Override
	public void renderHead(IHeaderResponse response) {
		response.render(JavaScriptHeaderItem.forReference(GlobalResources.JS_JQUERY_DATATABLES));
		response.render(JavaScriptHeaderItem.forReference(GlobalResources.JS_BOOTSTRAP_DATATABLES));
		response.render(JavaScriptHeaderItem.forReference(GlobalResources.JS_SHORTCUTS));
		response.render(JavaScriptHeaderItem.forReference(new WebContextUrlResourceReference("js/propertyEditor.js")));
		super.renderHead(response);
	}

	private Multimap<String, Review> buildReviewMap(PropertyFileDescriptor object) {
		EList<Review> reviews = object.getReviews();
		Multimap<String, Review> reviewMap = ArrayListMultimap.create(reviews.size(), 2);
		for (Review review : reviews) {
			reviewMap.put(review.getKey(), review);
		}
		return reviewMap;
	}


	private void addLinkList(final PropertyListMode currentMode) {
		List<PropertyListMode> values = Arrays.asList(PropertyListMode.values());
		ListView<PropertyListMode> mode = new ListView<PropertyListMode>("view-mode", values) {

			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(ListItem<PropertyListMode> item) {

                String mode = item.getModelObject().name().toLowerCase();
                BookmarkablePageLink<Object> link = new BookmarkablePageLink<Object>("link", getPage().getClass(), new PageParameters(getPageParameters()).set("mode", mode));
                link.setBody(Model.of("Show "+ mode));
                item.add(link);
				if (item.getModelObject() == currentMode)
					item.add(new AttributeModifier("class", "active"));
			}
		};
		add(mode);

	}

}

class PropertyPairDataProvider extends SortableDataProvider<PropertyPair, EClassSortState> implements IFilterStateLocator<String> {

	private static final long serialVersionUID = 1L;
	private CompoundPropertyModel<PropertyFileDescriptor> model;
	private transient List<PropertyPair> contents;
	private String filterState;
	private PropertyListMode mode;
	private IModel<Multimap<String, Review>> reviewModel;

	public PropertyPairDataProvider(PropertyFileDescriptor descriptor, PropertyListMode mode, IModel<Multimap<String, Review>> reviewModel) {
		super();
		model = new CompoundPropertyModel<PropertyFileDescriptor>(new EObjectModel<PropertyFileDescriptor>(descriptor));
		this.mode = mode;
		this.reviewModel = reviewModel;
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
		Multimap<String, Review> reviews = reviewModel.getObject();
		PropertyFileDescriptor master = descriptor.getMaster();
		Map<String, Property> translated = descriptor.loadProperties().asMap();
		PropertyFile templateFile = master.loadProperties();

		List<PropertyPair> contents = new ArrayList<PropertyPair>();
		for (Property property : templateFile.getProperties()) {
			// IModel<String> bind = model.bind(property.getKey());
			// bind.set
			PropertyPair pair = new PropertyPair(property, translated.remove(property.getKey()),descriptor.getVariant(), descriptor.cdoID());
			String key = pair.getKey();
			if (mode.apply(pair,reviews.get(key)))
				contents.add(pair);
		}
		for (Property property : translated.values()) {
			PropertyPair pair = new PropertyPair(null, property,descriptor.getVariant(), descriptor.cdoID());
			if (mode.apply(pair,reviews.get(pair.getKey())))
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
