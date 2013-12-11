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
import java.util.Set;

import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;

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



    @Override
    public void validate(IValidatable<String> validatable) {
        if(usedNames.contains(validatable.getValue()))
        {
            String message = "The name {0} is already used.";
            message = MessageFormat.format(message, validatable.getValue());
            validatable.error(new ValidationError(message));
        }

    }

}
