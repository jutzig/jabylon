/*
 * JSONEmitter.java
 *
 * created at 13.09.2012 by utzig <YOURMAILADDRESS>
 *
 * Copyright (c) SEEBURGER AG, Germany. All Rights Reserved.
 */
package de.jutzig.jabylon.rest.api.json;


import java.util.Collection;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;

import de.jutzig.jabylon.properties.PropertyFile;
import de.jutzig.jabylon.properties.PropertyFileDescriptor;


public class JSONEmitter
{
    public void serialize(EObject resolvable, StringBuilder result, int depth)
    {
        writeObject(resolvable, result, depth);
    }


    private void writeObject(EObject object, StringBuilder result, int depth)
    {
        if (depth == 0)
            return;
        result.append("{");
        writeAttributes(object, result, depth);
        result.append("}");
    }


    private void writeAttributes(EObject object, StringBuilder result, int depth)
    {
        EList<EStructuralFeature> eAllAttributes = object.eClass().getEAllStructuralFeatures();
        boolean didWriteSeparator = false;
        for (EStructuralFeature eAttribute : eAllAttributes)
        {
            if (eAttribute.isTransient())
                continue;
            if (!object.eIsSet(eAttribute))
                continue;
            if (eAttribute instanceof EReference)
            {
                EReference ref = (EReference)eAttribute;
                if (!ref.isContainment())
                    continue;
                if (depth == 1)
                    continue;

            }

            result.append("\"");
            result.append(eAttribute.getName());
            result.append("\"");
            result.append(":");
            if (eAttribute.isMany())
            {
                writeMany(object, eAttribute, result, depth);
                didWriteSeparator = true;
                result.append(",");
            }
            else
            {
                writeSingle(object, eAttribute, result, depth);
                didWriteSeparator = true;
                result.append(",");
            }
        }

        if (object instanceof PropertyFileDescriptor)
        {
            PropertyFileDescriptor descriptor = (PropertyFileDescriptor)object;
            PropertyFile properties = descriptor.loadProperties();
            if(!properties.getProperties().isEmpty())
            {
            	didWriteSeparator = false;
            	result.append("\"propertyFile\":");
            	writeObject(properties, result, depth-1);            	
            }
        }

        if (didWriteSeparator)
            result.setLength(result.length() - 1);

    }


    private void writeSingle(EObject object, EStructuralFeature eAttribute, StringBuilder result, int depth)
    {
        writeSingleValue(object.eGet(eAttribute), eAttribute, result, depth);

    }


    private void writeSingleValue(Object value, EStructuralFeature attribute, StringBuilder result, int depth)
    {

        if (value instanceof String)
        {
            String string = (String)value;
            result.append("\"");
            result.append(string);
            result.append("\"");

        }
        else if (value instanceof Boolean)
        {
            result.append(value);
        }
        else if (value instanceof Number)
        {
            result.append(value);
        }
        else if (attribute instanceof EAttribute)
        {
            String newValue = attribute.getEContainingClass()
                                       .getEPackage()
                                       .getEFactoryInstance()
                                       .convertToString(((EAttribute)attribute).getEAttributeType(), value);

            result.append("\"");
            result.append(newValue);
            result.append("\"");

        }
        else if (attribute instanceof EReference)
        {
            EReference ref = (EReference)attribute;
            if (!ref.isContainment())
                return;
            writeObject((EObject)value, result, depth - 1);
        }

    }


    @SuppressWarnings("rawtypes")
    private void writeMany(EObject object, EStructuralFeature eAttribute, StringBuilder result, int depth)
    {
        Object value = object.eGet(eAttribute);
        if (value instanceof Collection)
        {
            result.append("[");
            Collection values = (Collection)value;
            for (Object singleValue : values)
            {
                writeSingleValue(singleValue, eAttribute, result, depth);
                result.append(",");
            }
            if (!values.isEmpty())
                result.setLength(result.length() - 1); // remove the last ','
            result.append("]");
        }

    }
}
