/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.rest.ui.wicket.components;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.jabylon.rest.ui.model.ProgressionModel;

public interface ProgressCallback {

    void progressDone(AjaxRequestTarget target, ProgressionModel model);

    void progressStart(AjaxRequestTarget target, ProgressionModel model);
}
