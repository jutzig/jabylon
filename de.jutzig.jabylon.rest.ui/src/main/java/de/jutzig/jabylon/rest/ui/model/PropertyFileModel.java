/**
 * 
 */
package de.jutzig.jabylon.rest.ui.model;

import java.io.Serializable;
import java.util.Map;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import com.google.common.base.Function;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;

import de.jutzig.jabylon.properties.Property;
import de.jutzig.jabylon.properties.PropertyFile;
import de.jutzig.jabylon.properties.PropertyFileDescriptor;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 *
 */
public class PropertyFileModel implements IModel<Map<String, Property>> {

	
	private Supplier<Map<String,Property>> supplier;
	private EObjectModel<PropertyFileDescriptor> model;
	
	public PropertyFileModel(PropertyFileDescriptor descriptor) {
		model = new EObjectModel<PropertyFileDescriptor>(descriptor);
		supplier = Suppliers.memoize(Suppliers.compose(new PropertyFileLoader(), Suppliers.ofInstance(model)));
	}
	
	@Override
	public void detach() {
		// nothing to do
		
	}

	@Override
	public void setObject(Map<String, Property> arg0) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public Map<String, Property> getObject() {
		return supplier.get();
	}

}

class PropertyFileLoader implements Function<IModel<PropertyFileDescriptor>, Map<String, Property>>, Serializable
{

	@Override
	public Map<String, Property> apply(IModel<PropertyFileDescriptor> from) {
		
		return from.getObject().loadProperties().asMap();
	}
	
}