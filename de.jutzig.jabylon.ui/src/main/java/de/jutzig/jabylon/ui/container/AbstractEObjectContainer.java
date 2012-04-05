package de.jutzig.jabylon.ui.container;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.cdo.CDOObject;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.util.AbstractContainer;

public abstract class AbstractEObjectContainer<T extends EObject> extends AbstractContainer {

	private EList<T> objects;
	private EClass eClass;
	private Map<String, EObjectItem> contents;
	
	
	
	public AbstractEObjectContainer(EClass eClass) {
		super();
		this.eClass = eClass;
		contents = new HashMap<String, EObjectItem>();
	}

	@Override
	public EObjectItem getItem(Object itemId) {
		return contents.get(itemId);
	}

	@Override
	public Collection<?> getContainerPropertyIds() {
		return eClass.getEAllStructuralFeatures();
	}

	@Override
	public Collection<?> getItemIds() {
		return Collections2.transform(objects, new Function<T, Object>() {
			public Object apply(T from) {
				if (from instanceof CDOObject) {
					CDOObject cdoObject = (CDOObject) from;
					return cdoObject.cdoID();
				}
				return EcoreUtil.getURI(from);
			};
		});
	}

	@Override
	public Property getContainerProperty(Object itemId, Object propertyId) {
		EObjectItem item = getItem(itemId);
		if(item!=null)
		{
			return item.getItemProperty(propertyId);
		}
		return null;
	}

	@Override
	public Class<?> getType(Object propertyId) {
		return eClass.getInstanceClass();
	}

	@Override
	public int size() {
		return contents.size();
	}

	@Override
	public boolean containsId(Object itemId) {
		return contents.containsKey(itemId);
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

}
