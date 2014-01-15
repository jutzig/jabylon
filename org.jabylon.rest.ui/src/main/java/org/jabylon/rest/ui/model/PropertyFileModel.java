/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
/**
 *
 */
package org.jabylon.rest.ui.model;

import java.io.Serializable;
import java.util.Map;

import org.apache.wicket.model.IModel;
import org.jabylon.properties.Property;
import org.jabylon.properties.PropertyFileDescriptor;

import com.google.common.base.Function;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;

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
