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
package org.jabylon.rest.ui.wicket.validators;

import java.text.MessageFormat;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

import com.google.common.base.Function;

/**
 * @author jutzig.dev@googlemail.com
 *
 */
public class UniqueNameValidator implements IValidator<String> {

    private static final long serialVersionUID = 1L;

    private Set<String> usedNames;



    public UniqueNameValidator(Set<String> usedNames) {
        super();
        this.usedNames = usedNames;
    }

    /**
     * Creates a validator from the given set of objects. The names are extracted from the objects by applying the given function.
     * @param objects
     * @param nameExtractor
     * @param exclude the object to exclude from the collection (may be <code>null</code>)
     * @return
     */
    public static <F> UniqueNameValidator fromCollection(Collection<F> objects, Function<F, String> nameExtractor, F exclude) {
    	Set<String> names = new HashSet<String>();
    	for (F f : objects) {
    		if(!f.equals(exclude))
				names.add(nameExtractor.apply(f));
		}
    	return new UniqueNameValidator(names);
    }
    
    /**
     * Creates a validator from the given set of objects. The names are extracted from the EObjects by calling {@link EObject#eGet(EStructuralFeature)}.
     * The feature needs to return a String value
     * @param objects
     * @param feature
     * @param exclude the object to exclude from the collection (may be <code>null</code>)
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public static <F extends EObject> UniqueNameValidator fromCollection(Collection<F> objects, EStructuralFeature feature, F exclude) {
    	return fromCollection(objects, new EGetFunction(feature), exclude);
    }    

    @Override
    public void validate(IValidatable<String> validatable) {
        if(usedNames.contains(validatable.getValue()))
        {
            String message = "The name {0} is already used.";
            message = MessageFormat.format(message, validatable.getValue());
            validatable.error(new ValidationError(message));
        }

    }

    private static class EGetFunction<F extends EObject> implements Function<F, String> {
    	private EStructuralFeature feature;

		public EGetFunction(EStructuralFeature feature) {
			super();
			this.feature = feature;
		}
    	
    	@Override
    	public String apply(F input) {
    		
    		return (String) input.eGet(feature);
    	}
    	
    }
}
