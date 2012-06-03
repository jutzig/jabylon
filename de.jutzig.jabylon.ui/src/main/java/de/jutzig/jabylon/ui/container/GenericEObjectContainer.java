package de.jutzig.jabylon.ui.container;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;

import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.util.AbstractInMemoryContainer;
import com.vaadin.data.util.filter.UnsupportedFilterException;

import de.jutzig.jabylon.ui.util.WeakReferenceAdapter;

@SuppressWarnings("serial")
public class GenericEObjectContainer<T extends EObject> extends AbstractInMemoryContainer<T, EStructuralFeature, Item> implements Container.ItemSetChangeNotifier, Container.Filterable{

	private EObject parent;
	private EReference contentReference;
	private EList<T> contents;


	public GenericEObjectContainer(EObject parent, EReference contents) {
		super();
		this.parent = parent;
		parent.eAdapters().add(new WeakReferenceAdapter(new AdapterImpl() {
			@Override
			public void notifyChanged(Notification msg) {
				if(msg.getFeature()==contentReference)
				{
					//TODO: can probably do this more fine grained
					fireItemSetChange();
				}

			}
		}));
		this.contentReference = contents;
	}

	@Override
	public Collection<?> getContainerPropertyIds() {
		EClass clazz = (EClass) contentReference.getEType();
		return clazz.getEAllStructuralFeatures();
	}


	@Override
	protected List<T> getAllItemIds() {
		return getContents();
	}

	@SuppressWarnings("unchecked")
	private EList<T> getContents() {
		if(contents==null)
			contents = (EList<T>) parent.eGet(contentReference);
		return contents;

	}

	@Override
	public Property getContainerProperty(Object itemId, Object propertyId) {
		return getItem(itemId).getItemProperty(propertyId);
	}

	@Override
	public Class<?> getType(Object propertyId) {
		return ((EStructuralFeature)propertyId).getEType().getInstanceClass();
	}

	@Override
	public int size() {
		return getContents().size();
	}

	@Override
	public boolean containsId(Object itemId) {
		return getContents().contains(itemId);
	}

	public EObject getParent() {
		return parent;
	}

	@Override
	protected Item getUnfilteredItem(Object itemId) {
		return new EObjectItem((EObject) itemId);
	}

	@Override
	public void addContainerFilter(Filter filter) throws UnsupportedFilterException {
		super.addFilter(filter);
		
	}

	@Override
	public void removeContainerFilter(Filter filter) {
		super.removeFilter(filter);
		
	}

	@Override
	public void removeAllContainerFilters() {
		super.removeAllFilters();
		
	}

}
