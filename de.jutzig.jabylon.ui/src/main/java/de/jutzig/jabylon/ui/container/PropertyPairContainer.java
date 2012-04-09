package de.jutzig.jabylon.ui.container;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.ecore.EClass;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.util.AbstractContainer;

import de.jutzig.jabylon.properties.PropertiesFactory;
import de.jutzig.jabylon.properties.PropertiesPackage;
import de.jutzig.jabylon.properties.PropertyFile;
import de.jutzig.jabylon.properties.PropertyFileDescriptor;

public class PropertyPairContainer extends AbstractContainer {

	
	private static final String SOURCE_ID = "source.id";
	private static final String TARGET_ID = "target.id";
	private static final List<String> IDS;
	static {
		IDS = new ArrayList<String>();
		IDS.add(SOURCE_ID);
		IDS.add(TARGET_ID);
		
	}
	private PropertyFile target;
	private PropertyFile source;
	
	public PropertyPairContainer(PropertyFile source, PropertyFile target) {
		this.target = target;
		this.source = source;
	}
	
	@Override
	public Property getContainerProperty(Object itemId, Object propertyId) {
		if(propertyId==SOURCE_ID)
		{
			return new EObjectProperty(source.getProperty((String) itemId), PropertiesPackage.Literals.PROPERTY__VALUE);
		}
		else
		{
			return new EObjectProperty(getOrCreateTargetProperty(itemId), PropertiesPackage.Literals.PROPERTY__VALUE);
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
	public Collection<?> getContainerPropertyIds() {
		return IDS;
	}

	@Override
	public Item getItem(Object itemId) {
		de.jutzig.jabylon.properties.Property sourceProp = source.getProperty(itemId.toString());
		de.jutzig.jabylon.properties.Property targetProp = getOrCreateTargetProperty(itemId);

		return new PropertyPairItem(sourceProp, targetProp);
	}

	@Override
	public Collection<?> getItemIds() {
		return Collections2.transform(source.getProperties(), new Function<de.jutzig.jabylon.properties.Property,String>() {
			
			@Override
			public String apply(de.jutzig.jabylon.properties.Property from) {
				return from.getKey();
			}
		});
	}

	@Override
	public Class<?> getType(Object propertyId) {
		return String.class;
	}

	@Override
	public int size() {
		return source.getProperties().size();
	}

	@Override
	public boolean containsId(Object itemId) {
		return source.getProperty((String) itemId)!=null;
	}

	@Override
	public Item addItem(Object itemId) throws UnsupportedOperationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object addItem() throws UnsupportedOperationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean removeItem(Object itemId)
			throws UnsupportedOperationException {
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
		
		private de.jutzig.jabylon.properties.Property sourceProperty;
		private de.jutzig.jabylon.properties.Property targetProperty;
		
		
		public PropertyPairItem(de.jutzig.jabylon.properties.Property source, de.jutzig.jabylon.properties.Property target) {
			super();
			
			this.source = new EObjectProperty(source,PropertiesPackage.Literals.PROPERTY__VALUE);
			this.target = new EObjectProperty(target,PropertiesPackage.Literals.PROPERTY__VALUE);
			this.sourceProperty = source;
			this.targetProperty = target;
		}

		@Override
		public Property getItemProperty(Object id) {
			if(id==SOURCE_ID)
			{
				return source;
			}
			else
			{
				return target;
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
		
	}
}
