/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.rest.ui.model;

import java.io.Serializable;

import org.apache.wicket.model.IModel;

import com.google.common.base.Function;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;

import org.jabylon.common.progress.Progression;
import org.jabylon.rest.ui.Activator;

public class ProgressionModel implements IModel<Progression> {

    private static final long serialVersionUID = 1L;
    protected Supplier<Progression> modelSupplier;


    public ProgressionModel(long id)
    {
        this.modelSupplier = Suppliers.memoize(Suppliers.compose(new LookupFunction(), Suppliers.ofInstance(id)));
    }

    @Override
    public void detach() {
        //nothing to do

    }

    public void setTaskID(long id)
    {
        this.modelSupplier = Suppliers.memoize(Suppliers.compose(new LookupFunction(), Suppliers.ofInstance(id)));
    }

    @Override
    public Progression getObject() {
        return modelSupplier.get();
    }

    @Override
    public void setObject(Progression object) {
        throw new UnsupportedOperationException("Readonly model"); //$NON-NLS-1$

    }
    private static final class LookupFunction implements Serializable, Function<Long, Progression>
    {
        private static final long serialVersionUID = 1L;

        @Override
        public Progression apply(Long from)
        {
            return Activator.getDefault().getProgressService().progressionOf(from);
        }
    }

}
