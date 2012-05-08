package de.jutzig.jabylon.ui.container;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.util.AbstractContainer;

import de.jutzig.jabylon.properties.PropertiesFactory;
import de.jutzig.jabylon.properties.PropertiesPackage;
import de.jutzig.jabylon.properties.PropertyFile;

@SuppressWarnings("serial")
public class PropertyPairContainer extends AbstractContainer {

	private static final String SOURCE_ID = "source.id";
	private static final String TARGET_ID = "target.id";
	private static final String SOURCE_COMMENT  = "source.comment";
	private static final String TARGET_COMMENT  = "target.comment";
	private static final List<String> IDS;
	private Set<String> items; 
	static {
		IDS = new ArrayList<String>();
		IDS.add(SOURCE_ID);
		IDS.add(TARGET_ID);
		IDS.add(SOURCE_COMMENT);
		IDS.add(TARGET_COMMENT);
	}
	private PropertyFile target;
	private PropertyFile source;

	public PropertyPairContainer(PropertyFile source, PropertyFile target) {
		this.target = target;
		this.source = source;
		items = fillItems(source,target);
	}

	private Set<String> fillItems(PropertyFile source2, PropertyFile target2) {
		Set<String> items = new HashSet<String>(source2.getProperties().size());
		for (de.jutzig.jabylon.properties.Property property : source2.getProperties()) {
			items.add(property.getKey());
		}
		for (de.jutzig.jabylon.properties.Property property : target2.getProperties()) {
			items.add(property.getKey());
		}
		return items;
	}

	@Override
	public Property getContainerProperty(Object itemId, Object propertyId) {
		if(propertyId==SOURCE_ID)
		{
			de.jutzig.jabylon.properties.Property property = source.getProperty((String) itemId);
			if(property==null)
			{
				property = PropertiesFactory.eINSTANCE.createProperty();
				property.setKey((String) propertyId);
			}
			return new EObjectProperty(property, PropertiesPackage.Literals.PROPERTY__VALUE);
		}
		else if(propertyId==TARGET_ID)
		{
			return new EObjectProperty(getOrCreateTargetProperty(itemId), PropertiesPackage.Literals.PROPERTY__VALUE);
		}
		else if(propertyId==SOURCE_COMMENT)
		{
			return new EObjectProperty(source.getProperty((String) itemId), PropertiesPackage.Literals.PROPERTY__COMMENT);
		}
		else
		{
			return new EObjectProperty(getOrCreateTargetProperty(itemId), PropertiesPackage.Literals.PROPERTY__COMMENT);
		}

	}


	private de.jutzig.jabylon.properties.Property getOrCreateTargetProperty(Object itemId)
	{
		de.jutzig.jabylon.properties.Property property = target.getProperty(itemId.toString());
		de.jutzig.jabylon.properties.Property sourceProperty = source.getProperty(itemId.toString());
		if(property==null)
		{
			//create a new property entry
			property = PropertiesFactory.eINSTANCE.createProperty();
			property.setKey(sourceProperty.getKey());
			target.getProperties().add(property);
		}
		return property;
	}

	@Override
	public List<String> getContainerPropertyIds() {
		return IDS;
	}

	@Override
	public Item getItem(Object itemId) {
		de.jutzig.jabylon.properties.Property sourceProp = source.getProperty(itemId.toString());
		if(sourceProp==null)
		{
			sourceProp = PropertiesFactory.eINSTANCE.createProperty();
			sourceProp.setKey((String) itemId);
		}
		de.jutzig.jabylon.properties.Property targetProp = getOrCreateTargetProperty(itemId);

		return new PropertyPairItem(sourceProp, targetProp);
	}

	@Override
	public Collection<?> getItemIds() {
		return items;
	}

	@Override
	public Class<?> getType(Object propertyId) {
		return String.class;
	}

	@Override
	public int size() {
		return items.size();
	}

	@Override
	public boolean containsId(Object itemId) {
		return items.contains(itemId);
	}

	@Override
	public Item addItem(Object itemId) throws UnsupportedOperationException {
		throw new UnsupportedOperationException("Cannot add new items to this container");
	}

	@Override
	public Object addItem() throws UnsupportedOperationException {
		throw new UnsupportedOperationException("Cannot add new items to this container");
	}

	@Override
	public boolean removeItem(Object itemId) throws UnsupportedOperationException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addContainerProperty(Object propertyId, Class<?> type,
			Object defaultValue) throws UnsupportedOperationException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeContainerProperty(Object propertyId)
			throws UnsupportedOperationException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeAllItems() throws UnsupportedOperationException {
		// TODO Auto-generated method stub
		return false;
	}


	public static class PropertyPairItem implements Item
	{

		private Property source;
		private Property target;
		private Property sourceComment;
		private Property targetComment;

		private de.jutzig.jabylon.properties.Property sourceProperty;
		private de.jutzig.jabylon.properties.Property targetProperty;


		public PropertyPairItem(de.jutzig.jabylon.properties.Property source, de.jutzig.jabylon.properties.Property target) {
			super();

			this.source = new EObjectProperty(source,PropertiesPackage.Literals.PROPERTY__VALUE);
			this.target = new EObjectProperty(target,PropertiesPackage.Literals.PROPERTY__VALUE);
			this.sourceComment = new EObjectProperty(source,PropertiesPackage.Literals.PROPERTY__COMMENT);
			this.targetComment = new EObjectProperty(target,PropertiesPackage.Literals.PROPERTY__COMMENT);
			this.sourceProperty = source;
			this.targetProperty = target;
		}

		@Override
		public Property getItemProperty(Object id) {
			if(id==SOURCE_ID)
			{
				return source;
			}
			else if(id==TARGET_ID)
			{
				return target;
			}
			else if(id==TARGET_COMMENT)
			{
				return targetComment;
			}
			else
			{
				return sourceComment;
			}
		}

		@Override
		public Collection<?> getItemPropertyIds() {
			return IDS;
		}

		@Override
		public boolean addItemProperty(Object id, Property property)
				throws UnsupportedOperationException {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean removeItemProperty(Object id)
				throws UnsupportedOperationException {
			// TODO Auto-generated method stub
			return false;
		}

		public de.jutzig.jabylon.properties.Property getSourceProperty() {
			return sourceProperty;
		}

		public de.jutzig.jabylon.properties.Property getTargetProperty() {
			return targetProperty;
		}


		public Property getTarget() {
			return target;
		}

		public Property getSource() {
			return source;
		}

		public Property getSourceComment() {
			return sourceComment;
		}

		public Property getTargetComment() {
			return targetComment;
		}

		public Object getKey() {	
			String key = getSourceProperty().getKey();
			if(key==null)
				key = getTargetProperty().getKey();
			return key;
		}

	}
}
