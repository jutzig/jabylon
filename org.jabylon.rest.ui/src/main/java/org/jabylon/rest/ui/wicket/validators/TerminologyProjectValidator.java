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

import org.apache.wicket.model.IModel;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;
import org.eclipse.emf.common.util.EList;
import org.jabylon.properties.Project;
import org.jabylon.properties.Workspace;
import org.jabylon.rest.ui.model.AttachableModel;

/**
 * @author jutzig.dev@googlemail.com
 *
 */
public class TerminologyProjectValidator implements IValidator<Boolean> {

    private static final long serialVersionUID = 1L;
	private IModel<Project> project;


    public TerminologyProjectValidator(IModel<Project> project) {
        super();
        this.project = project;
    }
    @Override
    public void validate(IValidatable<Boolean> validatable) {
    	if(validatable.getValue()) {
    		Project object = project.getObject();
    		Workspace workspace = object.getParent();
    		if(workspace==null) {
    			if (project instanceof AttachableModel) {
					AttachableModel<Project> model = (AttachableModel<Project>) project;
					workspace = (Workspace) model.getParent().getObject();
				}
    		}
    		if(workspace!=null)
    		{
    			EList<Project> children = workspace.getChildren();
    			for (Project other : children) {
					if(other==object)
						continue;
					if(other.isTerminology())
					{
						//only one terminology project allowed atm
						ValidationError error = new ValidationError(this);
						error.getVariables().put("name", other.getName());
						validatable.error(error);
					}
				}
    		}
    	}
        

    }
}
