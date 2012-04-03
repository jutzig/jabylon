/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.jutzig.jabylon.properties.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Map;

import org.eclipse.emf.common.util.URI;

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
			reader = new BufferedReader(new InputStreamReader(inputStream));
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
		// TODO Auto-generated method stub
		super.doSave(outputStream, options);
	}

} //PropertiesResourceImpl
