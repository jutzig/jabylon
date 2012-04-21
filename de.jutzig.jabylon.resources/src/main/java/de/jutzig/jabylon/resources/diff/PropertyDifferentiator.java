package de.jutzig.jabylon.resources.diff;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.NotificationImpl;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import de.jutzig.jabylon.properties.PropertiesPackage;
import de.jutzig.jabylon.properties.Property;
import de.jutzig.jabylon.properties.PropertyFile;

public class PropertyDifferentiator {

	private PropertyFile original;

	public PropertyDifferentiator(PropertyFile original) {
		super();
		this.original = original;
	}

	public List<Notification> diff(PropertyFile modified) {
		Map<String, Property> orignalProperties = buildMap(original);
		List<Notification> noticiations = new ArrayList<Notification>();
		computeStructuralDiff(orignalProperties, modified, noticiations);
		return noticiations;
	}

	private void computeStructuralDiff(Map<String, Property> orignalProperties, PropertyFile modified, List<Notification> noticiations) {
		List<Property> additions = new ArrayList<Property>();
		List<Property> removals = new ArrayList<Property>();
		for (Property prop : modified.getProperties()) {
			Property originalProperty = orignalProperties.get(prop.getKey());
			if (originalProperty == null)
				additions.add(prop);
			else {
				orignalProperties.remove(prop.getKey());
				Notification change = computeDiff(originalProperty, prop);
				if(change!=null)
					noticiations.add(change);
			}
		}
		
		removals.addAll(orignalProperties.values());
		if(!additions.isEmpty())
		{
			Notification notification = new ENotificationImpl((InternalEObject) modified,Notification.ADD_MANY, PropertiesPackage.Literals.PROPERTY_FILE__PROPERTIES,null, additions);
			noticiations.add(notification);
		}
		
		if(!removals.isEmpty())
		{
			Notification notification = new ENotificationImpl((InternalEObject) modified,Notification.ADD_MANY, PropertiesPackage.Literals.PROPERTY_FILE__PROPERTIES,removals, null);
			noticiations.add(notification);
		}

	}

	private Notification computeDiff(Property orignalPropertery, Property modifiedProperty) {

		if (orignalPropertery.getValue() == null && modifiedProperty.getValue() == null)
			return null; // if both are null, it's all good
		if (orignalPropertery.getValue() == null && modifiedProperty.getValue() != null) {
			return new CustomNotification(Notification.SET, null, modifiedProperty.getValue());
		}
		if (modifiedProperty.getValue() == null && orignalPropertery.getValue() != null) {
			return new CustomNotification(Notification.UNSET, orignalPropertery.getValue(), null);
		}
		if(modifiedProperty.getValue().equals(orignalPropertery.getValue()))
			return null;
		return new CustomNotification(Notification.SET, orignalPropertery.getValue(), modifiedProperty.getValue());
	}

	private Map<String, Property> buildMap(PropertyFile file) {
		Map<String, Property> properties = new HashMap<String, Property>(file.getProperties().size());
		for (Property property : file.getProperties()) {
			properties.put(property.getKey(), property);
		}
		return properties;
	}

}

class CustomNotification extends NotificationImpl {

	public CustomNotification(int eventType, String oldValue, String newValue) {
		super(eventType, oldValue, newValue);
	}

	@Override
	public Object getFeature() {
		return PropertiesPackage.Literals.PROPERTY__VALUE;
	}

}
