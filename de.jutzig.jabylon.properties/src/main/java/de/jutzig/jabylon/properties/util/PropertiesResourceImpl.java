/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.jutzig.jabylon.properties.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;

import de.jutzig.jabylon.properties.PropertiesFactory;
import de.jutzig.jabylon.properties.Property;
import de.jutzig.jabylon.properties.PropertyFile;

/**
 * <!-- begin-user-doc -->
 * The <b>Resource </b> associated with the package.
 * <!-- end-user-doc -->
 * @see de.jutzig.jabylon.properties.util.PropertiesResourceFactoryImpl
 * @generated
 */
public class PropertiesResourceImpl extends ResourceImpl {
	
	
	//TODO: this is a dirty hack...
	private int savedProperties;
	
	/**
	 * Creates an instance of the resource.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param uri the URI of the new resource.
	 * @generated
	 */
	public PropertiesResourceImpl(URI uri) {
		super(uri);
	}
	
	
	@Override
	protected void doLoad(InputStream inputStream, Map<?, ?> options)
			throws IOException {
		PropertiesHelper helper = new PropertiesHelper();
		BufferedReader reader = null;
		PropertyFile file = PropertiesFactory.eINSTANCE.createPropertyFile();
		try {
			reader = new BufferedReader(new InputStreamReader(inputStream,"ISO-8859-1")); //TODO: configure charset
			Property p = null;
			while((p = helper.readProperty(reader))!=null)
			{
				file.getProperties().add(p);
			}
		} finally{
			if(reader!=null)
				reader.close();
		}
		getContents().add(file);
	}
	
	@Override
	protected void doSave(OutputStream outputStream, Map<?, ?> options)
			throws IOException {
		savedProperties = 0;
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "ISO-8859-1"));
		try {
			PropertiesHelper helper = new PropertiesHelper();
			PropertyFile file = (PropertyFile) getContents().get(0);
			Iterator<Property> it = file.getProperties().iterator();
			while (it.hasNext()) {
				Property property = (Property) it.next();
				//eliminate all empty property entries
				if(isFilled(property))
				{
					helper.writeProperty(writer, property);
					savedProperties++;
				}
					
			}
			
		}
		finally{
			writer.close();
		}
		
	}
	
	
	private boolean isFilled(Property property) {
		if(property==null)
			return false;
		if(property.getKey()==null || property.getKey().length()==0)
			return false;
		return !(property.getValue()==null || property.getValue().length()==0);
	}
	
	public int getSavedProperties() {
		return savedProperties;
	}

} //PropertiesResourceImpl
