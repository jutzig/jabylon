/**
 * 
 */
package de.jutzig.jabylon.ui.tools.internal;

import java.util.Collection;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;

import org.eclipse.emf.common.util.EList;

import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.ui.AbstractSelect.ItemDescriptionGenerator;
import com.vaadin.ui.Component;
import com.vaadin.ui.Table;

import de.jutzig.jabylon.properties.ProjectLocale;
import de.jutzig.jabylon.properties.ProjectVersion;
import de.jutzig.jabylon.properties.Property;
import de.jutzig.jabylon.properties.PropertyFileDescriptor;
import de.jutzig.jabylon.properties.Review;
import de.jutzig.jabylon.properties.Workspace;
import de.jutzig.jabylon.ui.container.PropertyPairContainer.PropertyPairItem;
import de.jutzig.jabylon.ui.styles.JabylonStyle;
import de.jutzig.jabylon.ui.tools.PropertyEditorTool;
import de.jutzig.jabylon.ui.tools.SuggestionAcceptor;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 *
 */
public class TerminologyTool implements PropertyEditorTool {

	private BeanItemContainer<Property> container;
	private Map<String, Property> terminology;
	private SuggestionAcceptor acceptor;

	/**
	 * 
	 */
	public TerminologyTool() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see de.jutzig.jabylon.ui.tools.PropertyEditorTool#selectionChanged(de.jutzig.jabylon.ui.container.PropertyPairContainer.PropertyPairItem, java.util.Collection)
	 */
	@Override
	public void selectionChanged(PropertyPairItem currentSelection, Collection<Review> reviews, SuggestionAcceptor acceptor) {
		Property property = currentSelection.getSourceProperty();
		String value = property.getValue();
		container.removeAllItems();
		this.acceptor  = acceptor;
		if(terminology!=null)
			tokenize(value);
	}

	private void tokenize(String value) {
		if(value==null)
			return;
		StringTokenizer tokenizer = new StringTokenizer(value, " \t\n\r\f.,;:(){}\"'<>");
		while(tokenizer.hasMoreTokens())
		{
			String token = tokenizer.nextToken();
			Property property = terminology.get(token);
			if(property!=null)
			{
				container.addBean(property);
			}
			
		}
		
	}

	/* (non-Javadoc)
	 * @see de.jutzig.jabylon.ui.tools.PropertyEditorTool#init(de.jutzig.jabylon.properties.PropertyFileDescriptor, de.jutzig.jabylon.properties.PropertyFileDescriptor)
	 */
	@Override
	public void init(PropertyFileDescriptor template, PropertyFileDescriptor translation) {
		 terminology = null;
		 PropertyFileDescriptor descriptor = getTerminology(translation);
		 if(descriptor==null)
			 return;
		 terminology = descriptor.loadProperties().asMap();
		 
	}

	/* (non-Javadoc)
	 * @see de.jutzig.jabylon.ui.tools.PropertyEditorTool#createComponent()
	 */
	@Override
	public Component createComponent() {
        Table table = new Table();
        table.setSizeFull();
        table.addStyleName(JabylonStyle.TABLE_STRIPED.getCSSName());
        container = new BeanItemContainer<Property>(Property.class);
//        container.addContainerProperty("key", String.class, "");
//        container.addContainerProperty("value", String.class, "");
        table.setContainerDataSource(container);
        table.setDescription("Double click to copy a value to the editor");
        table.setVisibleColumns(new Object[]{"key","value"});
        table.setColumnExpandRatio("key", 1f);
        table.setColumnExpandRatio("value", 1f);
        table.setColumnHeaders(new String[]{"Term","Translation"});
        table.addListener(new ItemClickListener() {
			
			@Override
			public void itemClick(ItemClickEvent event) {
				if(event.isDoubleClick())
				{
					@SuppressWarnings("unchecked")
					BeanItem<Property> item = ((BeanItem<Property>) event.getItem());
					Property property = item.getBean();
					acceptor.append(property.getValue());
				}
				
			}
		});
        table.setItemDescriptionGenerator(new ItemDescriptionGenerator() {
			
			@Override
			public String generateDescription(Component source, Object itemId, Object propertyId) {
				if (itemId instanceof Property) {
					Property property = (Property) itemId;
					String comment = property.getComment();
					if(comment==null || comment.length()==0)
						return "Double click to copy a value to the editor";
					return comment;
				}
				return "Double click to copy a value to the editor";
			}
		});
        return table;
	}
	
	private PropertyFileDescriptor getTerminology(PropertyFileDescriptor descriptor)
	{
		Locale locale = descriptor.getProjectLocale().getLocale();
		Workspace workspace = descriptor.getProjectLocale().getProjectVersion().getProject().getWorkspace();
		ProjectVersion terminology = workspace.getTerminology();
		if(terminology==null)
			return null;
		ProjectLocale projectLocale = terminology.getProjectLocale(locale);
		if(projectLocale==null)
			return null;
		EList<PropertyFileDescriptor> descriptors = projectLocale.getDescriptors();
		if(descriptors.isEmpty())
			return null;
		return descriptors.get(0);
	}

}
