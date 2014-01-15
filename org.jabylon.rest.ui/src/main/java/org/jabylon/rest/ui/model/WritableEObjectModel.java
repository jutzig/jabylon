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

import org.eclipse.emf.cdo.CDOObject;
import org.eclipse.emf.cdo.common.id.CDOID;
import org.eclipse.emf.cdo.common.id.CDOIDUtil;
import org.eclipse.emf.cdo.transaction.CDOTransaction;
import org.jabylon.rest.ui.Activator;

import com.google.common.base.Function;
import com.google.common.base.Suppliers;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 *
 */
public class WritableEObjectModel<T extends CDOObject> extends EObjectModel<T> {


    private static final long serialVersionUID = 1L;

    private transient CDOTransaction transaction;

    public WritableEObjectModel(T model) {
        super(model);
    }

    @Override
    protected T getDomainObject() {
        T result = super.getDomainObject();
        if (result.cdoView() instanceof CDOTransaction) {
            transaction = (CDOTransaction) result.cdoView();
        }
        return result;
    }

    @Override
    public void detach() {

        this.modelSupplier = Suppliers.memoize(Suppliers.compose(new LookupFunction<T>(), Suppliers.ofInstance(id)));
        if(transaction!=null)
            transaction.close();

    }

    private static final class LookupFunction<X> implements Serializable, Function<Long, X> {

        /** field <code>serialVersionUID</code> */
        private static final long serialVersionUID = -7243181664341900603L;

        @SuppressWarnings("unchecked")
        @Override
        public X apply(Long from) {
        	CDOID id = CDOIDUtil.createLong(from);
            CDOObject cdoObject = Activator.getDefault().getRepositoryLookup().resolveWithTransaction(id);
            return (X) cdoObject;
        }

    }
}
